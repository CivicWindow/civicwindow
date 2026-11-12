# Continuous integration workflows

**Status:** Proposed. Four workflows and seven jobs are defined; none has run. The first run is the first run in CI after the repository is published, and every local command below is **not yet run**.

Create workflows only after actual commands, build profiles, secrets policy and artifact retention are defined. A workflow that merely exits successfully is not validation.

## The workflows

All files are in `.github/workflows/`.

| File | Workflow name | Jobs |
|---|---|---|
| [`android.yml`](android.yml) | Android | `android-build`, `android-unit-tests`, `android-static-analysis` |
| [`python.yml`](python.yml) | Python | `python-checks` |
| [`repo-checks.yml`](repo-checks.yml) | Repository checks | `commit-messages`, `commit-format` |
| [`secret-scan.yml`](secret-scan.yml) | Secret scan | `secret-scan` |

No workflow signs, publishes or deploys anything. Continuous integration builds the debug type only.

## The checks

A job's id and its `name:` are the same string, and each name appears once in the repository: GitHub warns that the same job name in several workflows can give ambiguous status check results ([about protected branches](https://docs.github.com/en/repositories/configuring-branches-and-merges-in-your-repository/managing-protected-branches/about-protected-branches)).

| Job | File | Required | What it proves |
|---|---|---|---|
| `android-build` | [`android.yml`](android.yml) | yes | the debug build and the device-test build compile |
| `android-unit-tests` | [`android.yml`](android.yml) | yes | the unit tests pass, including the `build-logic` test, and the coverage report is made |
| `android-static-analysis` | [`android.yml`](android.yml) | yes | ktlint and Android Lint pass |
| `python-checks` | [`python.yml`](python.yml) | yes | the lock file is current; Ruff and pytest pass |
| `commit-messages` | [`repo-checks.yml`](repo-checks.yml) | yes | only the closing-keyword rule: a commit message carries no closing keyword followed by an issue number ([branching policy](../../governance/BRANCHING_POLICY.md), rule 4; [CONTRIBUTING](../../CONTRIBUTING.md)) |
| `secret-scan` | [`secret-scan.yml`](secret-scan.yml) | yes | no secret is found in the history or the working tree |
| `commit-format` | [`repo-checks.yml`](repo-checks.yml) | **no, advisory** | the format rules of CONTRIBUTING: the type list, the first-line length, the body wrapping |

The six required checks are the six jobs marked "yes". `commit-format` is listed apart: it reports, and it does not block a merge.

**Selecting the required checks.** The project owner selects the six names in the repository settings after publication, once each job has run once. That GitHub shows a check under its job name is confirmed at that first run.

**Before the first run can pass.** Three sets of files are made by a tool and checked in; they are not written by hand: the Gradle wrapper files (the `android-*` jobs call `./gradlew`), the Gradle lock and verification files if the build needs them, and `ml/uv.lock` (`uv sync --locked` fails without it). The commands that make them are in [`gradle/README.md`](../../gradle/README.md) and [`ml/README.md`](../../ml/README.md). Until they exist, the three `android-*` jobs and `python-checks` are expected to fail, so they cannot yet be selected as required checks.

## Triggers

Every workflow file has the same triggers:

- `pull_request` into `dev` and `main`;
- `push` to `dev` and `main`;
- `workflow_dispatch` (a manual start).

No workflow has a path filter, a branch-ignore filter or an `if:` on a job that could skip a required job. GitHub leaves a required check whose workflow is skipped in the "Pending" state, and that blocks merging ([troubleshooting required status checks](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/collaborating-on-repositories-with-code-quality-features/troubleshooting-required-status-checks)); a saving in run time is not worth a blocked merge. No workflow uses `pull_request_target`, `workflow_run` or `schedule`.

## Permissions, limits and pinning

Standard: GitHub Actions, [Secure use reference](https://docs.github.com/en/actions/reference/security/secure-use), and the [workflow syntax](https://docs.github.com/en/actions/writing-workflows/workflow-syntax-for-github-actions).

| Rule | Value |
|---|---|
| Token permissions | `permissions: contents: read` at the top of each file. A job that needs more says so in its own `permissions` block and in a comment; none needs more now |
| Time limit | `timeout-minutes` on every job: 40 for the three `android-*` jobs, 20 for `python-checks`, 10 for `commit-messages` and `commit-format`, 15 for `secret-scan` (the project's choice; no standard covers the values) |
| Concurrency | one group per workflow and ref; an earlier run of the same group is cancelled only for pull requests |
| Pinning | every `uses:` line is pinned by the full 40-character commit id, with the release in a comment on the same line, so that Dependabot can update the comment with the pin |
| Runner | `ubuntu-latest`, checked again at the first run |
| Artifacts | only the coverage report of `android-unit-tests` is uploaded, kept for 7 days (the project's choice); it is not a release artifact |

Pinning by commit id is one of the practices GitHub's guidance lists, next to least-privilege token permissions. It does not replace them or the review of an action before it is added. The same guidance describes dependency lockfiles for workflows as a further protection that a repository can adopt; that is a repository setting, and it is not enabled here.

[`.github/dependabot.yml`](../dependabot.yml) proposes updates to the pinned actions as pull requests into `dev`.

## Secrets

No workflow uses a secret: there is no `secrets.` reference, and no workflow runs with `pull_request_target`. A job has only the default token, with read access to repository contents. No workflow echoes a pull request's title, body or branch name into a shell command. Any secret is created at publication by the project owner; no workflow needs one yet ([security decisions](../../docs/security/README.md)).

The secret scan runs the Gitleaks command-line program and not the packaged action, because the action needs a licence key for an organization account. The workflow downloads Gitleaks v8.30.1 and compares the archive's SHA-256 with the digest of the release before it runs the program. Its configuration is [`.gitleaks.toml`](../../.gitleaks.toml): the program's default rules, and no allowlist. A finding is shown redacted.

## Commands to run locally

Windows 10 and 11 with PowerShell 7 ([ADR-0002](../../docs/adr/0002-build-toolchain.md)). Every command is **not yet run**. Run the Gradle commands from the repository root.

| Job | Command |
|---|---|
| `android-build` | `./gradlew assembleDebug assembleDebugAndroidTest` |
| `android-unit-tests` | `./gradlew testDebugUnitTest :build-logic:test createDebugUnitTestCoverageReport` |
| `android-static-analysis` | `./gradlew ktlintCheck lintDebug` |
| `python-checks` | from the `ml` folder: `uv sync --locked`, then `uv run ruff check . ../scripts`, `uv run ruff format --check . ../scripts`, `uv run pytest --cov --cov-branch --cov-report=term-missing` and `uv run pytest ../scripts/validation/tests` |
| `commit-messages`, `commit-format` | [`scripts/validation/check_commit_messages.py`](../../scripts/validation/check_commit_messages.py), run as `python scripts/validation/check_commit_messages.py --rule closing-keyword <base> <head>` or `python scripts/validation/check_commit_messages.py --rule format <base> <head>`: the rule is `--rule closing-keyword` or `--rule format`, then the commit before the range (`<base>`) and the last commit of the range (`<head>`); the exit code is 0 when every commit passes, 1 when a commit fails, and 2 when the range cannot be read |
| `secret-scan` | from the repository root, with the same Gitleaks release installed for your system: `gitleaks git --config .gitleaks.toml --redact --no-banner .` and `gitleaks dir --config .gitleaks.toml --redact --no-banner .` |

## Checked again at the first run

These names and forms are not confirmed by the documentation read for this file: the task `:build-logic:test`; the command `uv run pytest ../scripts/validation/tests`; the Gradle task names `testDebugUnitTest`, `createDebugUnitTestCoverageReport`, `ktlintCheck` and `lintDebug`; the input names of each action; the runner image; the Gitleaks download address, subcommands and flags; the keyword list of the closing-keyword rule; the commit range of each event; and the folder of the coverage report.

Enforcement: GitHub runs these workflows on every pull request once the repository is published; review is the check until the first CI run.
