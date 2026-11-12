# Validation strategy

**Status:** Planned checks; the build files, the test libraries and two example tests exist, but no build or check has run (the first CI run after publication is the first run). Nothing is claimed beyond the example tests until that run.

Role checks cover owned behavior and public contracts with declared inputs. Producer/consumer tests exercise both real implementations and controlled substitutes. Workflow tests cover fresh sparse setup, absent/altered dependencies, selective ticket promotion, candidate changes, dirty-work preservation and fixed release identity. Full integration tests use real selected modules. Device tests cover model loading, inference, offline search, retrieval recovery and notification permissions.

A green test is evidence only for the property it would actually fail under a prohibited behavior. Record exact source, inputs, environment and result. Missing or unrun checks stay visible; mocks cannot qualify a release.

## Test libraries

[ADR-0009](../adr/0009-test-libraries-and-coverage.md) chooses the libraries. Their versions are written once, in `gradle/libs.versions.toml`, and are checked again at the first CI run after publication. A module declares none of them itself: one build plugin applies them.

- **Unit tests (Kotlin):** JUnit 4 with Kotlin test assertions. One framework serves unit tests and device tests, because the Android test runner runs JUnit 4 tests.
- **Controlled substitutes:** MockK. A test that uses a substitute says so in its name or a comment, and substitute-only success cannot qualify a release (see the [definition of done](../../governance/DEFINITION_OF_DONE.md)).
- **Flow and coroutine code:** Turbine and kotlinx-coroutines-test (`runTest`).
- **Device tests:** AndroidX Test and the Compose UI test libraries. Device tests run on a physical Android device; CI compiles them and does not run them. An emulator-only result does not establish device readiness ([Software Design Document](../design/SOFTWARE_DESIGN_DOCUMENT.md), section 2.1).
- **Python:** pytest ([ADR-0008](../adr/0008-python-lock-tool.md)).
- **Not used:** Robolectric, JUnit 5 and the JUnit Vintage engine.
- **Not chosen yet:** Room and WorkManager testing libraries. Each joins the catalog together with its runtime library.

## Naming rules

Three rules follow. The Kotlin rule rests on the [Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html), which allow backticked names in tests only, and the Python rule on pytest's discovery rules. Where the folders and wording go beyond those, no standard covers this; it is the project's choice.

### Test files and folders

- Kotlin unit tests: `src/test/kotlin/` in each project. The unit tests of `build-logic` are in `build-logic/src/test/kotlin/`.
- Kotlin device tests: `src/androidTest/kotlin/` in the two Android projects (`:app` and `:modules:android:discovery`).
- Checks across roles: `tests/<kind>/`, where the kind is `contracts`, `end-to-end`, `integration`, `performance` or `workflow`. Shared inputs live in `tests/fixtures/` and `tests/mocks/`, and connector inputs in `connectors/fixtures/`. See the [tests overview](../../tests/README.md).
- Python tests: `ml/tests/`, in files named `test_*.py`, which is how pytest finds them.

### Kotlin tests

- A test class is named `<Subject>Test`, for example `VersionCodeTest`.
- A test function is a backticked sentence in the present tense that says the behavior, for example `` `rejects a part that is out of range` ``.
- A test that uses a controlled substitute says so in its name or a comment.

### Python tests

- A test function is named `test_<behavior>`, and one test checks one behavior.

## Coverage

Coverage is measured and reported. It has no failing threshold anywhere: no standard covers a threshold, it is the project's choice, and ADR-0009 explains why one waits.

- **Kotlin on Android:** JaCoCo through the Android Gradle Plugin, switched on for the debug build type. The project does not set a JaCoCo version; the first report shows which version the plugin uses. The tasks, run in each of `:app` and `:modules:android:discovery`, are `createDebugUnitTestCoverageReport` and `createDebugAndroidTestCoverageReport`. The reports are written to `build/reports/coverage/test/debug/index.html` and `build/reports/coverage/androidTest/debug/connected/index.html` in each project. Gradle does not write a report if the tests it covers fail, so the tests run first.
- **Kotlin JVM projects:** none yet. This is decided when such a project has sources.
- **Python:** `pytest --cov --cov-branch --cov-report=term-missing`, run from `ml/`. The data file `.coverage` and the folder `htmlcov/` are ignored by version control.

None of these has run yet.

## Running the tests

Every command below is **not yet run**. Gradle tasks run with the Gradle wrapper at the repository root; the first-run steps are in [gradle/README.md](../../gradle/README.md).

| layer | where it lives | command | note |
|---|---|---|---|
| unit, Kotlin on Android | `src/test/kotlin/` | `testDebugUnitTest`, for example `:app:testDebugUnitTest` | part of `check` |
| unit, Kotlin JVM | `src/test/kotlin/` in `:contracts` and `build-logic/src/test/kotlin/` | `test` | `:contracts` has no sources yet. `build-logic` is an included build with its own `test` task; how the root build calls it is open and is confirmed at the first build |
| unit, Python | `ml/tests/` | `pytest`, run from `ml/` | |
| contract, producer and consumer | `tests/contracts/` | none yet | planned |
| integration | `tests/integration/` | none yet | planned |
| device | `src/androidTest/kotlin/` | `assembleDebugAndroidTest` compiles them (CI); `connectedDebugAndroidTest` runs them on a physical device | the run is manual: not in CI and not part of `check` |
| workflow | `tests/workflow/` | none yet | planned |

The coverage tasks and `assembleDebugAndroidTest` are called by name; they are not part of `check`.

The weekly test that every lead takes part in is described in the [ticket-board guide](../operations/TICKET_BOARD_SETUP.md#course-mapping).

The first ticket that adds a screen carries an accessibility check, using the Compose test tools and the lint run, and the device check covers the screen reader.

**Enforcement:** `testDebugUnitTest`, `test` and `pytest` fail on a failing test, and `assembleDebugAndroidTest` fails when the device tests do not compile; none has run yet. The naming rules, the substitute labels and the coverage reports have no check yet: review is the check until one is added under the "Adding a tool, package or dependency" rule of the coding standards.
