# Enforcement: pytest runs the tests of this script and Ruff checks it, both in the
# Python job of CI; the script itself runs in the two jobs of the repository checks
# workflow. Review is the check for these rules until the first run.
"""Check commit messages against the Commit messages rules of CONTRIBUTING.md.

The script reads the commits in a range with ``git log`` and checks each message
against one of two rule groups, chosen with ``--rule``:

``closing-keyword``
    Refuse a closing keyword followed by an issue number. A closing keyword is
    written only in the integration pull request into ``dev`` (the branching policy).
    This group is the required one.
``format``
    Check the Conventional Commits 1.0.0 format with the project's limits: the type
    list, a lower case summary without a full stop, a first line of at most 72
    characters, and a body of lines of at most 72 characters after one blank line.
    This group is advisory. Whether the summary is imperative is left to review.

Merge commits are skipped. Each failing commit is printed with the rule it breaks.
The exit code is 0 when every commit passes, 1 when a commit fails, and 2 when the
commit range cannot be read.
"""

import argparse
import re
import subprocess
import sys
from collections.abc import Sequence

COMMIT_TYPES = ("feat", "fix", "docs", "refactor", "test", "build", "ci", "chore")
MAX_LINE_LENGTH = 72
RULE_CLOSING_KEYWORD = "closing-keyword"
RULE_FORMAT = "format"
RULES = (RULE_CLOSING_KEYWORD, RULE_FORMAT)

# The first line: <type>(<scope>): <summary>, with an optional scope and optional "!".
HEADER = re.compile(
    r"""
    (?P<type>[A-Za-z]+)
    (?:\((?P<scope>[^()]+)\))?
    (?P<breaking>!)?
    :[ ](?P<summary>.+)
    """,
    re.VERBOSE,
)

# A closing keyword and an issue number, such as "Closes #12" or "fixes: org/repo#3".
CLOSING_KEYWORD = re.compile(
    r"\b(?:close[sd]?|fix(?:e[sd])?|resolve[sd]?)\b:?[ \t]+(?:[\w.-]+/[\w.-]+)?#\d+",
    re.IGNORECASE,
)


def check_closing_keyword(message: str) -> list[str]:
    """Return the problems found when a message carries a closing keyword."""
    match = CLOSING_KEYWORD.search(message)
    if match is None:
        return []
    text = match.group(0)
    return [f"closing-keyword: '{text}' closes an issue; use 'Refs #<n>' instead"]


def check_format(message: str) -> list[str]:
    """Return the problems found in the Conventional Commits format of a message."""
    lines = message.rstrip().split("\n")
    return _check_first_line(lines[0]) + _check_body(lines[1:])


def _check_first_line(line: str) -> list[str]:
    """Return the problems found in the first line of a message."""
    problems = []
    if len(line) > MAX_LINE_LENGTH:
        problems.append(f"format: first line is over {MAX_LINE_LENGTH} characters")
    match = HEADER.fullmatch(line)
    if match is None:
        problems.append("format: first line is not '<type>(<scope>): <summary>'")
        return problems
    if match["type"] not in COMMIT_TYPES:
        types = ", ".join(COMMIT_TYPES)
        problems.append(f"format: type '{match['type']}' is not one of {types}")
    summary = match["summary"]
    if summary.endswith("."):
        problems.append("format: summary ends with a full stop")
    if summary[0].isupper():
        problems.append("format: summary does not start in lower case")
    return problems


def _check_body(rest: list[str]) -> list[str]:
    """Return the problems found in the lines after the first line."""
    if not rest:
        return []
    problems = []
    if rest[0].strip():
        problems.append("format: the line after the first line is not blank")
    for number, line in enumerate(rest, start=2):
        if len(line) > MAX_LINE_LENGTH:
            message = f"format: line {number} is over {MAX_LINE_LENGTH} characters"
            problems.append(message)
    return problems


CHECKS = {
    RULE_CLOSING_KEYWORD: check_closing_keyword,
    RULE_FORMAT: check_format,
}


def parse_log(output: str) -> list[tuple[str, str]]:
    """Split the output of ``git log`` into (hash, message) pairs."""
    commits = []
    for record in output.split("\x1e"):
        if "\x1f" not in record:
            continue
        sha, _, message = record.partition("\x1f")
        commits.append((sha.strip(), message.replace("\r\n", "\n")))
    return commits


def read_commits(base: str, head: str) -> list[tuple[str, str]]:
    """Return the (hash, message) pairs of the non-merge commits in base..head.

    An empty base, or a base of zeros as a push of a new branch reports, reads only
    the head commit.
    """
    if not base or set(base) == {"0"}:
        revisions = ["-n", "1", head]
    else:
        revisions = [f"{base}..{head}"]
    command = ["git", "log", "--no-merges", "--format=%H%x1f%B%x1e", *revisions]
    result = subprocess.run(
        command, capture_output=True, text=True, encoding="utf-8", check=False
    )
    if result.returncode != 0:
        raise RuntimeError(result.stderr.strip() or "git log failed")
    return parse_log(result.stdout)


def _first_line(message: str) -> str:
    """Return the first line of a message, or an empty string."""
    lines = message.strip().splitlines()
    return lines[0] if lines else ""


def main(argv: Sequence[str] | None = None) -> int:
    """Run the chosen rule group over a commit range and return the exit code."""
    parser = argparse.ArgumentParser(description="Check commit messages.")
    parser.add_argument("--rule", required=True, choices=RULES)
    parser.add_argument("base", help="commit before the range; empty reads head only")
    parser.add_argument("head", help="the last commit of the range")
    args = parser.parse_args(argv)
    try:
        commits = read_commits(args.base, args.head)
    except RuntimeError as error:
        print(f"error: cannot read the commits: {error}", file=sys.stderr)
        return 2
    check = CHECKS[args.rule]
    failed = 0
    for sha, message in commits:
        problems = check(message)
        if problems:
            failed += 1
            print(f"commit {sha[:12]}: {_first_line(message)}")
            for problem in problems:
                print(f"  {problem}")
    print(f"{failed} of {len(commits)} commits break the {args.rule} rule")
    return 1 if failed else 0


if __name__ == "__main__":
    sys.exit(main())
