# Enforcement: pytest runs this file and Ruff checks it, both in the Python job of CI;
# review is the check until the first run.
"""Tests for the commit-message check in ``scripts/validation``.

Each test fails under the defect it names: a rule that stops refusing what
CONTRIBUTING.md refuses, or that starts refusing what it allows. The script is
loaded by its path, so the tests do not depend on how pytest sets up imports.
"""

import importlib.util
from pathlib import Path

SCRIPT = Path(__file__).resolve().parents[1] / "check_commit_messages.py"
spec = importlib.util.spec_from_file_location("check_commit_messages", SCRIPT)
checker = importlib.util.module_from_spec(spec)
spec.loader.exec_module(checker)

# The example of the Commit messages section of CONTRIBUTING.md.
EXAMPLE = (
    "feat(discovery): show the last refresh date on the results list\n"
    "\n"
    "Show the date of the last successful refresh above the list so that\n"
    "stale data is visible.\n"
    "\n"
    "Refs #123\n"
)


def test_example_of_the_contributing_guide_passes_both_rules() -> None:
    """Fail when the example commit message of CONTRIBUTING.md is refused."""
    assert checker.check_closing_keyword(EXAMPLE) == []
    assert checker.check_format(EXAMPLE) == []


def test_closing_keyword_with_issue_number_is_refused() -> None:
    """Fail when a message ending in ``Closes #12`` is accepted."""
    message = "feat(discovery): add a results list\n\nCloses #12\n"
    assert checker.check_closing_keyword(message) != []


def test_every_closing_keyword_form_is_refused() -> None:
    """Fail when a form of closing keyword that GitHub accepts gets through."""
    forms = ["Closes #1", "closed #1", "Fixes #2", "fixed: #2", "Resolves #3"]
    forms += ["resolved #3", "Fixes org/repo#4", "CLOSE #5"]
    for form in forms:
        message = f"fix(ml): handle an empty list\n\n{form}\n"
        assert checker.check_closing_keyword(message) != [], form


def test_refs_link_is_accepted() -> None:
    """Fail when a message that links its issue with ``Refs #12`` is refused."""
    message = "fix(ml): handle an empty list\n\nRefs #12\n"
    assert checker.check_closing_keyword(message) == []


def test_closing_word_without_issue_number_is_accepted() -> None:
    """Fail when the word ``close`` with no issue number is refused."""
    message = "fix(ml): close the file after reading it\n"
    assert checker.check_closing_keyword(message) == []


def test_closing_keyword_rule_ignores_a_badly_formatted_message() -> None:
    """Fail when the required rule refuses a message for its format."""
    message = "Updated the list.\n\nRefs #1\n"
    assert checker.check_closing_keyword(message) == []


def test_format_rule_ignores_a_closing_keyword() -> None:
    """Fail when the advisory rule refuses a message for its closing keyword."""
    message = "fix(ml): handle an empty list\n\nCloses #3\n"
    assert checker.check_format(message) == []


def test_type_outside_the_list_fails_the_format_rule() -> None:
    """Fail when a type that is not in the CONTRIBUTING list is accepted."""
    message = "update(discovery): show the date of the last refresh\n"
    assert checker.check_format(message) != []


def test_every_type_in_the_list_passes_the_format_rule() -> None:
    """Fail when a type of the CONTRIBUTING list is refused."""
    for commit_type in checker.COMMIT_TYPES:
        assert checker.check_format(f"{commit_type}: change a thing\n") == []


def test_first_line_without_a_type_fails_the_format_rule() -> None:
    """Fail when a first line with no ``<type>:`` prefix is accepted."""
    assert checker.check_format("show the date of the last refresh\n") != []


def test_scope_and_breaking_mark_pass_the_format_rule() -> None:
    """Fail when a scope or the ``!`` before the colon is refused."""
    assert checker.check_format("feat(ml)!: drop the old label file\n") == []


def test_first_line_of_72_characters_passes_the_format_rule() -> None:
    """Fail when a first line of exactly 72 characters is refused."""
    line = "feat: " + "a" * 66
    assert len(line) == 72
    assert checker.check_format(line) == []


def test_first_line_of_73_characters_fails_the_format_rule() -> None:
    """Fail when a first line of 73 characters is accepted."""
    line = "feat: " + "a" * 67
    assert len(line) == 73
    assert checker.check_format(line) != []


def test_summary_with_a_full_stop_fails_the_format_rule() -> None:
    """Fail when a summary that ends with a full stop is accepted."""
    assert checker.check_format("fix: handle an empty list.\n") != []


def test_summary_in_upper_case_fails_the_format_rule() -> None:
    """Fail when a summary that starts with a capital letter is accepted."""
    assert checker.check_format("fix: Handle an empty list\n") != []


def test_body_line_of_72_characters_passes_the_format_rule() -> None:
    """Fail when a body line of exactly 72 characters is refused."""
    body = "b" * 72
    assert checker.check_format(f"fix: handle an empty list\n\n{body}\n") == []


def test_body_line_of_73_characters_fails_the_format_rule() -> None:
    """Fail when a body line of 73 characters is accepted."""
    body = "b" * 73
    assert checker.check_format(f"fix: handle an empty list\n\n{body}\n") != []


def test_body_without_a_blank_line_fails_the_format_rule() -> None:
    """Fail when a body that follows the first line directly is accepted."""
    assert checker.check_format("fix: handle an empty list\nit failed\n") != []


def test_parse_log_returns_the_hash_and_the_message_of_each_commit() -> None:
    """Fail when the git log output is not split into hash and message pairs."""
    output = "aaa\x1ffix: one\n\nRefs #1\n\x1e\nbbb\x1ffix: two\n\x1e\n"
    assert checker.parse_log(output) == [
        ("aaa", "fix: one\n\nRefs #1\n"),
        ("bbb", "fix: two\n"),
    ]


def test_main_exits_with_1_when_a_commit_breaks_the_rule(monkeypatch, capsys) -> None:
    """Fail when a refused commit does not make the script exit with 1."""
    commits = [("a" * 40, "fix: handle an empty list\n\nCloses #3\n")]
    monkeypatch.setattr(checker, "read_commits", lambda base, head: commits)
    assert checker.main(["--rule", "closing-keyword", "base", "head"]) == 1
    assert "closing-keyword" in capsys.readouterr().out


def test_main_exits_with_0_when_every_commit_passes(monkeypatch) -> None:
    """Fail when a range of accepted commits does not make the script exit with 0."""
    commits = [("a" * 40, EXAMPLE)]
    monkeypatch.setattr(checker, "read_commits", lambda base, head: commits)
    assert checker.main(["--rule", "closing-keyword", "base", "head"]) == 0
    assert checker.main(["--rule", "format", "base", "head"]) == 0


def test_main_exits_with_2_when_the_range_cannot_be_read(monkeypatch) -> None:
    """Fail when an unreadable commit range does not make the script exit with 2."""

    def unreadable(base: str, head: str) -> list[tuple[str, str]]:
        raise RuntimeError("bad revision")

    monkeypatch.setattr(checker, "read_commits", unreadable)
    assert checker.main(["--rule", "format", "base", "head"]) == 2
