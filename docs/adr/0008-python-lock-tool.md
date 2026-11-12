# ADR-0008: Python environment and lock tool

- Status: Proposed
- Date: 2026-11-10
- Decision-makers: AI/ML lead, Android/Product lead, Data Integration/Platform lead
- Owner: AI/ML lead (see [ownership and coverage](../operations/OWNERSHIP_AND_COVERAGE.md))
- Supersedes: none
- Superseded by: none

## Context and problem statement

[ADR-0005](0005-python-environment.md) fixes one Python minor version, 3.12, and says that exact library versions are pinned in a lock file. It leaves one point open:

```text
Open, to be decided when the environment is created: the environment and lock tool, and type checking.
```

The [Software Design Document](../design/SOFTWARE_DESIGN_DOCUMENT.md), section 5, states the reproducibility rule that the tool must serve:

```text
Setup records the exact versions of the runtime, Git, build tooling, JDK and Android SDK, and the Python/ML environment, so any member can reproduce the build.
```

This record picks the tool that creates the Python environment and writes the lock file. The members work on Windows 10 and 11 (the Design Doc proposes it), so the tool has to run there.

## Considered options

The criteria are the four below. Each is judged only from the tool's own documentation; where the documentation read for this record does not confirm a criterion, the table says "not confirmed by the saved documentation", which is not the same as "no".

| Tool (version read) | Windows | Lock file with hashes | PEP 621 metadata | One command to reproduce |
|---|---|---|---|---|
| **uv 0.13.7** | Supported; the minimum is Windows 10 ([platform policy](https://docs.astral.sh/uv/reference/policies/platforms/)) | `uv.lock`, checked into version control, with the exact resolved versions ([project layout](https://docs.astral.sh/uv/concepts/projects/layout/)). On hashes the documentation says only that when an index advertises several hashes for a distribution, uv records a single one ([indexes](https://docs.astral.sh/uv/concepts/indexes/)); that every entry carries a hash is not confirmed by the saved documentation | Yes: `project.dependencies` follows PEP 621 ([dependencies](https://docs.astral.sh/uv/concepts/projects/dependencies/)) | `uv sync` installs from the lock file; `--locked` makes it fail when the lock file is out of date ([syncing](https://docs.astral.sh/uv/concepts/projects/sync/)) |
| **pip-tools 7.6.2** | A documented form for Windows (`py -X.Y -m piptools compile`) ([documentation](https://pip-tools.pypa.io/en/stable/)) | `pip-compile` writes pinned output; `--generate-hashes` adds hashes | `project.dependencies` is supported | Not confirmed by the saved documentation: the pages read show the compile command, not one command that installs from the pinned file |
| **Poetry 2.5.1** | Yes ([documentation](https://python-poetry.org/docs/)) | `poetry.lock`, committed for applications ([basic usage](https://python-poetry.org/docs/basic-usage/)); that it carries hashes is not confirmed by the saved documentation | Yes ([pyproject](https://python-poetry.org/docs/pyproject/)) | With a lock file present, the command line uses the exact versions from it ([commands](https://python-poetry.org/docs/cli/)) |
| **conda 26.10.0** | Yes: lock files recreate environments on Windows, Linux and macOS ([managing environments](https://docs.conda.io/projects/conda/en/stable/user-guide/tasks/manage-environments.html)) | Lock files (from conda 26.5) list the exact packages, versions, builds and channels; explicit lock files may carry an MD5 or SHA256 hash | Not confirmed by the saved documentation | An explicit lock file recreates an environment without invoking the solver |

pip-tools, Poetry and conda are real candidates, and none is excluded by a failed criterion: each is held back by a criterion that its documentation, as read, does not confirm. No standard covers the choice of a lock tool; it is the project's, made on the criteria above and the product documents.

## Decision outcome

**uv.** It meets three of the four criteria in its own documentation (Windows 10 and later, PEP 621 metadata, one command to reproduce with a failure on a stale lock). On hashes it is partial, and that is stated above; the first time the lock file is made, its entries are checked for hashes. The lock file is made by uv and never edited by hand.

- The environment is described by `ml/pyproject.toml` (PEP 621 `project` table) and locked in **`ml/uv.lock`**, which is checked into version control.
- Python is 3.12 (ADR-0005). The tool versions are proposed baselines, checked again at the first CI run after publication: uv 0.13.7, Ruff 0.17.4 and pytest 9.2.0 (pytest 9 needs Python 3.10 or later, so 3.12 fits).
- A member reproduces the environment with `uv sync`. CI uses `uv sync --locked`, so a change to `ml/pyproject.toml` without a new `ml/uv.lock` fails.
- Ruff keeps one shared configuration at the repository root (`ruff.toml`), read by `ml/` and `scripts/`, with a line length of 88 columns (Ruff's default; [PEP 8](https://peps.python.org/pep-0008/) lets a project's own guide take precedence).
- **Type checking is not adopted at the boilerplate stage.** ADR-0005 left it open and nothing in the boilerplate needs it. It is revisited, with a new record, when `ml/` holds its first training or conversion code, so that a checker can be tried on real code.

## Consequences

- Good: one lock file, one command to install from it, and one command that fails on a stale lock.
- Good: the lock tool reads standard PEP 621 metadata, so a later move to another tool changes the lock file and not the project description.
- Bad: uv records the hash only in the case its documentation names; the first lock file is checked for hashes, and if some entries lack them a stricter option (such as pip-tools with `--generate-hashes`) is reconsidered in a new record.
- Bad: uv is young compared with pip-tools and Poetry; its version is pinned in the setup table and checked again at each upgrade.
- Re-checked: the tool version and the hash point at the first CI run after publication, and the type-checking question when `ml/` holds its first code.

## Affected modules and contracts

`ml/`, `scripts/` (through the shared `ruff.toml`), `docs/ai-ml/`, `.github/` (the Python job and Dependabot's `uv` ecosystem for `ml/`).

## Enforcement

`uv sync --locked` in the `python-checks` job of CI fails when `ml/uv.lock` is stale, and Dependabot's `uv` ecosystem for `/ml` proposes updates (`.github/dependabot.yml`). Neither has run yet: the job first runs on the first pull request after the repository is published, and no environment has been created, so `ml/uv.lock` must be made and checked in before the job can pass. Until then review is the check, and the first CI run after publication checks the lock file.

## More information

- [uv documentation](https://docs.astral.sh/uv/), [pip-tools](https://pip-tools.pypa.io/en/stable/), [Poetry](https://python-poetry.org/docs/), [conda](https://docs.conda.io/projects/conda/en/stable/).
- [PEP 621](https://peps.python.org/pep-0621/), [PEP 8](https://peps.python.org/pep-0008/).
- Related: [ADR-0005](0005-python-environment.md), [ADR-0007](0007-module-and-build-structure.md).
