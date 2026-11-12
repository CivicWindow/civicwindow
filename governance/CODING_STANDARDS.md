# Coding standards

**State:** Shared engineering principles, with the decision record, the tool and the command that check each rule. No build or check has run yet: every check below is Proposed, and the first run is the first CI run after the repository is published.

Keep modules focused on declared responsibilities. Validate inputs at boundaries, report failures explicitly, preserve evidence links and avoid silent dependency substitution. Tests should fail under prohibited behavior, including missing prerequisites, incompatible contracts and a substitute being used for a real release dependency. Update consumers and fixtures when public interfaces change.

A written standard alone is not an executable check, so each rule below is paired with the check that enforces it. A rule whose check has not run says "Proposed; check not yet run". A rule that no tool checks says "review is the check".

All commands are **not yet run**. Gradle commands run from the repository root, and Python commands from the `ml` folder, on Windows 10 and 11 with PowerShell 7. The set-up of the tools is in the [development environment guide](../docs/operations/DEV_ENVIRONMENT.md#what-to-install).

## Kotlin and Android

| Rule | Decision record | Tool | Command | State |
|---|---|---|---|---|
| Kotlin code follows the Kotlin coding conventions and the Android Kotlin style guide, and is formatted by ktlint | [ADR-0006](../docs/adr/0006-kotlin-android-style.md) | ktlint, reading `.editorconfig` | `./gradlew ktlintCheck` (CI job `android-static-analysis`; the task name is open: confirmed at the first run) | Proposed; check not yet run |
| Android code passes Android Lint, including the checks on the SDK levels | [ADR-0006](../docs/adr/0006-kotlin-android-style.md), [ADR-0003](../docs/adr/0003-android-sdk-levels.md) | Android Lint, configured in `config/lint/lint.xml` | `./gradlew lintDebug` (CI job `android-static-analysis`; the task name is open: confirmed at the first run) | Proposed; check not yet run |
| A KDoc comment on every public class and function | [ADR-0006](../docs/adr/0006-kotlin-android-style.md) | none yet | none: review is the check | review is the check |
| Static analysis with detekt | [ADR-0006](../docs/adr/0006-kotlin-android-style.md) | detekt is added later | none; no workflow or command names it now | not yet part of the build |
| Unit tests pass | [ADR-0009](../docs/adr/0009-test-libraries-and-coverage.md) | JUnit 4 with Kotlin test assertions | `./gradlew testDebugUnitTest :build-logic:test` (CI job `android-unit-tests`; both task names are open: confirmed at the first run) | Proposed; check not yet run |
| Device tests compile; they run on a physical device | [ADR-0009](../docs/adr/0009-test-libraries-and-coverage.md) | AndroidX Test and Compose UI test | `./gradlew assembleDebugAndroidTest` (CI job `android-build`); the run itself is by hand on a device | Proposed; check not yet run |
| Coverage is measured and reported, with no failing threshold | [ADR-0009](../docs/adr/0009-test-libraries-and-coverage.md) | JaCoCo through the Android Gradle Plugin | `./gradlew createDebugUnitTestCoverageReport`; the task name is open: confirmed at the first run | Proposed; check not yet run |
| The app permits no cleartext traffic | [security decisions](../docs/security/README.md) | the app's network security configuration, `network_security_config.xml` | none: review is the check | review is the check |

## Python

| Rule | Decision record | Tool | Command | State |
|---|---|---|---|---|
| One interpreter, Python 3.12, for every Python file | [ADR-0005](../docs/adr/0005-python-environment.md) | `.python-version`, `requires-python` in `ml/pyproject.toml`, and the environment test | `uv run pytest` | Proposed; check not yet run |
| Style follows PEP 8 | [ADR-0005](../docs/adr/0005-python-environment.md) | Ruff, with one shared `ruff.toml` | `uv run ruff check . ../scripts` | Proposed; check not yet run |
| Formatting follows Ruff's formatter | [ADR-0005](../docs/adr/0005-python-environment.md) | Ruff | `uv run ruff format --check . ../scripts` | Proposed; check not yet run |
| A docstring on every public function, following PEP 257 | [ADR-0005](../docs/adr/0005-python-environment.md) | Ruff's docstring rules | `uv run ruff check . ../scripts` | Proposed; check not yet run |
| Exact library versions are pinned in the lock file | [ADR-0008](../docs/adr/0008-python-lock-tool.md) | uv | `uv sync --locked` (CI job `python-checks`) | Proposed; check not yet run |
| Tests pass; coverage is reported, with no failing threshold | [ADR-0009](../docs/adr/0009-test-libraries-and-coverage.md) | pytest, `coverage` and `pytest-cov` | `uv run pytest --cov --cov-branch --cov-report=term-missing` (CI job `python-checks`) | Proposed; check not yet run |

## Gradle

| Rule | Decision record | Tool | Command | State |
|---|---|---|---|---|
| A dependency or plugin version is written once, in `gradle/libs.versions.toml` | [ADR-0007](../docs/adr/0007-module-and-build-structure.md), rule 1 | the Gradle build fails on an alias the catalog does not declare; a version written elsewhere is caught by review | `./gradlew assembleDebug` | Proposed; check not yet run; review is the check on a version written outside the catalog |
| Shared build rules live in convention plugins in `build-logic`, not in copies | [ADR-0007](../docs/adr/0007-module-and-build-structure.md), rule 2 | the Gradle build | `./gradlew assembleDebug` | Proposed; check not yet run |
| Dependency locking and dependency verification are used | [ADR-0007](../docs/adr/0007-module-and-build-structure.md), rule 3 | Gradle | the first-run commands in [`gradle/README.md`](../gradle/README.md) make the files; none exists yet | not yet made |
| A role build selects its projects explicitly and fails when one is missing | [ADR-0007](../docs/adr/0007-module-and-build-structure.md), rule 4 | `settings.gradle.kts` and `role-configs/` | `./gradlew -Pcivicwindow.role=<ai-ml\|android\|data-platform> <task>` | Proposed; check not yet run |

## Across the repository

| Rule | Where it is set | Tool | CI job | State |
|---|---|---|---|---|
| No secret is committed | [security decisions](../docs/security/README.md) | the Gitleaks command-line program, with `.gitleaks.toml` | `secret-scan` | Proposed; check not yet run |
| A commit message carries no closing keyword | [branching policy](BRANCHING_POLICY.md), rule 4 | `scripts/validation/check_commit_messages.py` | `commit-messages` | Proposed; check not yet run |
| A commit message follows the format of [CONTRIBUTING.md](../CONTRIBUTING.md) | [CONTRIBUTING.md](../CONTRIBUTING.md) | the same script | `commit-format` (advisory) | Proposed; check not yet run |

The CI jobs and their triggers are listed in the [workflows README](../.github/workflows/README.md).

## Line lengths

| Language | Limit | Reason | Where it is set |
|---|---|---|---|
| Kotlin | 100 columns | The [Android Kotlin style guide](https://developer.android.com/kotlin/style-guide), which [ADR-0006](../docs/adr/0006-kotlin-android-style.md) adopts, sets a column limit of 100 characters. | `max_line_length = 100` for `*.kt` and `*.kts` in `.editorconfig`; ktlint checks it |
| Python | 88 columns | 88 is Ruff's default ([the line-too-long rule](https://docs.astral.sh/ruff/rules/line-too-long/)). [PEP 8](https://peps.python.org/pep-0008/) lets a project's own guide take precedence for that project, and the project keeps the tool's default so that the configuration stays small. That choice is the project's; no standard covers it. | `line-length = 88` in `ruff.toml`; Ruff checks it |

## Adding a tool, package or dependency

A pull request that adds a tool, a package or a dependency names four things in its description:

1. its **official source**, as a link;
2. its **licence**;
3. its **exact version**;
4. the **role or module it is isolated to**.

**Where it goes**
- A Gradle dependency goes in the version catalog, `gradle/libs.versions.toml`.
- A Python dependency goes in the lock file, `ml/uv.lock`; the project file `ml/pyproject.toml` declares it and `uv` writes the lock file.
- A tool that only one role needs goes only in that role's modules and in that role's table of the [development environment guide](../docs/operations/DEV_ENVIRONMENT.md#what-to-install), and in no other role's build.

**How it is checked**
- Code-owner review: `.github/CODEOWNERS` names who reviews a change.
- The version catalog and the lock files: the Gradle build fails on an alias the catalog does not declare, and `uv sync --locked` fails when the lock file is out of date with `ml/pyproject.toml`. `ml/uv.lock` is made by the first `uv sync`.
- Dependabot: `.github/dependabot.yml` proposes updates to what the catalog and the lock file pin.
- The licence: review is the check.

The choice of the rule, rather than of any tool, is the project's; no standard covers it.

Enforcement: each rule above names its check. The checks first run on the first pull request after the repository is published, and until then review is the check.
