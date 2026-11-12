# ADR-0005: Python environment and standards

- Status: Accepted
- Date: 2026-11-10
- Decision-makers: AI/ML lead, Android/Product lead, Data Integration/Platform lead
- Owner: AI/ML lead (see [ownership and coverage](../operations/OWNERSHIP_AND_COVERAGE.md))
- Supersedes: none
- Superseded by: none

## Context and problem statement

Python code in `ml/` and `scripts/` needs one interpreter version, one set of pinned libraries and one style, so any member can reproduce the work. The [Software Design Document](../design/SOFTWARE_DESIGN_DOCUMENT.md), section 5, names the version:

```text
Python 3.12, with the training libraries pinned in a lock file at setup
```

Section 5 states the reproducibility rule:

```text
Setup records the exact versions of the runtime, Git, build tooling, JDK and Android SDK, and the Python/ML environment, so any member can reproduce the build.
```

The coding-standards proposal in the same document sets the style:

```text
Python code would follow PEP 8, formatted and linted by Ruff.
```

and a documentation rule: "a docstring on every public Python function".

## Considered options

1. **Python 3.12.** The only version any project document names.
2. **An older or newer minor version.** Not pursued. Python 3.12 receives security fixes only, and its end of life is scheduled for October 2028 ([PEP 693](https://peps.python.org/pep-0693/); [Python's version status](https://devguide.python.org/versions/)), so it holds for the project's span. The exact patch release is checked again when the environment is first created.
3. **Ruff alone for lint and format, or several tools.** Ruff alone is the proposal.

## Decision outcome

- One Python minor version, 3.12, for every Python file in the repo (`ml/`, `scripts/`, and any test helper). No project document states this reach beyond the Python/ML environment; it is proposed here so that one interpreter serves the whole repo.
- Exact library versions are pinned in a lock file.
- Style follows [PEP 8](https://peps.python.org/pep-0008/).
- Docstrings follow [PEP 257](https://peps.python.org/pep-0257/), with the Design Doc's rule of a docstring on every public Python function.
- [Ruff](https://docs.astral.sh/ruff/) lints and formats, with one shared configuration.

Open, to be decided when the environment is created: the environment and lock tool, and type checking.

## Consequences

- Good: one interpreter and one style for all Python, reproducible from the lock file.
- Bad: moving to another Python minor version needs a new record.
- Re-checked: the Python version when the environment is first created.

## Affected modules and contracts

`ml/`, `scripts/`, helpers under `tests/`, and `docs/ai-ml/`.

## Enforcement

Ruff and the lock file check this decision, wired in with the Python configuration, and the `python-checks` job runs the same commands in CI. The job first runs on the first pull request after the repository is published; until then review is the check.

## More information

- [PEP 8](https://peps.python.org/pep-0008/), [PEP 257](https://peps.python.org/pep-0257/), [Ruff](https://docs.astral.sh/ruff/).
- Related: [ADR-0004](0004-inference-runtime.md), [ADR-0007](0007-module-and-build-structure.md).
