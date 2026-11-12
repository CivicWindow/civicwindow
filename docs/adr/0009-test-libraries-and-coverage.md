# ADR-0009: Test libraries and coverage

- Status: Proposed
- Date: 2026-11-11
- Decision-makers: AI/ML lead, Android/Product lead, Data Integration/Platform lead
- Owner: Android/Product lead (see [ownership and coverage](../operations/OWNERSHIP_AND_COVERAGE.md))
- Supersedes: none
- Superseded by: none

## Context and problem statement

The [validation strategy](../testing/TEST_STRATEGY.md) says what the checks must show, and ends with this rule:

```text
A green test is evidence only for the property it would actually fail under a prohibited behavior. Record exact source, inputs, environment and result. Missing or unrun checks stay visible; mocks cannot qualify a release.
```

It does not name a test framework, a library for controlled substitutes or a coverage tool, and no test runner is set up yet. The [Software Design Document](../design/SOFTWARE_DESIGN_DOCUMENT.md), section 2.1, sets the bar for device behavior:

```text
At least one designated tester must have access to a physical Android device. Weekly build demonstrations will use tested device behavior, and emulator-only results will not establish device readiness.
```

This record picks the libraries for Kotlin unit tests and Android device tests, the library for controlled substitutes, and the coverage tools for Kotlin and Python. Python already has pytest ([ADR-0008](0008-python-lock-tool.md)). The Kotlin and Android versions follow [ADR-0001](0001-application-stack.md) to [ADR-0003](0003-android-sdk-levels.md), and the minimum Android level is 26.

## Considered options

Each option is judged only from the vendor's own documentation. Where the documentation read for this record does not confirm a point, the table says "not confirmed by the saved documentation" or "not stated", which is not the same as "no".

| Option | What the documentation says | Verdict |
|---|---|---|
| **JUnit 4 (4.13.2) with Kotlin test assertions** | On Kotlin/JVM, Gradle uses JUnit 4 by default, so `kotlin("test")` resolves to `kotlin-test-junit` ([Kotlin Gradle configuration](https://kotlinlang.org/docs/gradle-configure-project.html)). The Android runner "lets you run instrumented JUnit 4 tests on Android devices, including those using the Espresso, UI Automator, and Compose testing frameworks" ([AndroidJUnitRunner](https://developer.android.com/training/testing/instrumented-tests/androidx-test-libraries/runner)). The project says "JUnit 4 is in maintenance mode." and "At this point, only critical bugs and security issues will be fixed." ([JUnit 4](https://junit.org/junit4/)) | Chosen: one framework serves unit tests and device tests. Maintenance mode is a cost, recorded under Consequences |
| JUnit Platform with the Vintage engine | "JUnit Vintage provides a TestEngine for running JUnit 3 and JUnit 4 based tests on the platform." ([JUnit user guide](https://docs.junit.org/current/user-guide/)). The runner page above does not mention JUnit 5 at all | Not now: it adds a second engine to configure, and the device runner page mentions JUnit 4 and does not mention JUnit 5 |
| MockK 1.14.12 for controlled substitutes | Supports Kotlin 1.4 and higher ([MockK](https://github.com/mockk/mockk)); `io.mockk:mockk-android` serves device tests, with inlining on Android P and later ([MockK on Android](https://github.com/mockk/mockk/blob/master/ANDROID.md)). A minimum JDK is not stated | Chosen, for substitutes only. A substitute is labelled as one and cannot qualify a release |
| Turbine 1.2.1 and kotlinx-coroutines-test 1.11.0 for Flow and coroutine code | Turbine: `app.cash.turbine:turbine:1.2.1` ([Turbine](https://github.com/cashapp/turbine)); the coroutines version it needs is not stated ([changelog](https://github.com/cashapp/turbine/blob/trunk/CHANGELOG.md)). Coroutines: "runTest is the way to test code that involves coroutines. suspend functions can be called inside it." ([kotlinx-coroutines-test](https://github.com/Kotlin/kotlinx.coroutines/blob/master/kotlinx-coroutines-test/README.md)) | Chosen |
| AndroidX Test (runner 1.7.0, rules 1.7.0, core 1.7.0, ext:junit 1.3.0) and Compose UI test | The AndroidX Test releases need Android level 21, below the project's 26 ([release notes](https://developer.android.com/jetpack/androidx/releases/test)). Compose UI test uses `ui-test-junit4` for device tests and `ui-test-manifest` for the debug build ([Compose testing](https://developer.android.com/develop/ui/compose/testing)); under the Bill of Materials a library takes no version of its own ([BOM](https://developer.android.com/develop/ui/compose/bom)). The Bill of Materials' version of `ui-test-junit4` is not confirmed by the saved documentation | Chosen. The version of `ui-test-junit4` is settled at the first build |
| Robolectric | Runs tests "on your workstation, or on your continuous integration environment, in a regular JVM," ([Robolectric](https://robolectric.org/)). The Room testing page says "We don't recommend Android local unit tests with Robolectric. Use local JVM tests using Room KMP instead." ([Room testing](https://developer.android.com/training/data-storage/room/testing-db)) | Rejected: device behavior is checked on a physical device, and a JVM that imitates Android does not establish it |

Coverage options, also from the vendors' documentation:

| Option | What the documentation says | Verdict |
|---|---|---|
| JaCoCo through the Android Gradle Plugin | "AGP automatically applies Jacoco when you enable coverage on your modules." Coverage is enabled with `enableUnitTestCoverage` and `enableAndroidTestCoverage` per build type, and the tasks are named `createVariantNameUnitTestCoverageReport` and `createVariantNameAndroidTestCoverageReport` ([coverage reports](https://developer.android.com/studio/test/coverage-report)). The default JaCoCo version of AGP 9.3 is not stated. The latest JaCoCo release is 0.8.16 ([JaCoCo changes](https://www.jacoco.org/jacoco/trunk/doc/changes.html)) | Chosen for Kotlin on Android. The build does not set `jacocoVersion`: it uses AGP's default, and the first report shows which version that is |
| `coverage` 7.17.2 with `pytest-cov` 7.1.0 for Python | coverage.py 7.17.2 is supported on Python 3.11 through 3.16.0a1, so 3.12 ([coverage.py](https://coverage.readthedocs.io/en/latest/)); branch coverage is turned on with the `--branch` flag ([branch coverage](https://coverage.readthedocs.io/en/latest/branch.html)). `pytest-cov` adds automatic erasing and combination of `.coverage` files ([pytest-cov](https://github.com/pytest-dev/pytest-cov)). The Python and pytest versions `pytest-cov` requires are not confirmed by the saved documentation | Chosen for Python |

No standard covers a coverage threshold; it is the project's choice.

## Decision outcome

**JUnit 4 with Kotlin test assertions for unit tests,** because AndroidJUnitRunner runs JUnit 4 tests, so one framework serves unit tests and device tests.

- **MockK** for controlled substitutes, labelled as substitutes in the tests. A test that uses a substitute does not qualify a release.
- **Turbine and kotlinx-coroutines-test** for Flow and coroutine tests.
- **Compose UI test and AndroidX Test** for device tests, run on a physical device.
- **No Robolectric.**
- **Coverage is measured and reported, with no failing threshold.** A threshold waits until a module has enough tests for a number to mean something. Kotlin coverage uses JaCoCo through the Android Gradle Plugin, switched on for the debug build type; Python coverage uses `coverage` with `pytest-cov`, with branch coverage on.
- The versions are proposed baselines, written once in `gradle/libs.versions.toml` ([ADR-0007](0007-module-and-build-structure.md), rule 1) or in `ml/pyproject.toml`, and checked again at the first CI run after publication: JUnit 4.13.2, MockK 1.14.12, Turbine 1.2.1, kotlinx-coroutines-test 1.11.0, AndroidX Test as above, `coverage` 7.17.2 and `pytest-cov` 7.1.0. Kotlin test has no version of its own and follows the Kotlin Gradle plugin; that, and the Compose Bill of Materials' version of `ui-test-junit4`, are open until the first build.
- The libraries are applied by one convention plugin, so a module declares none of them itself.

## Consequences

- Good: one test framework, one runner and one assertion style for unit tests and device tests.
- Good: no test depends on Robolectric or on a runtime library that [ADR-0004](0004-inference-runtime.md) withholds.
- Bad: JUnit 4 is in maintenance mode: only critical bugs and security issues are fixed. This record is revisited when the Android runner documents a newer JUnit; the runner page read for this record does not mention JUnit 5.
- Bad: coverage without a threshold does not stop coverage from falling. It is reported at each run, and a threshold is proposed in a new record when a module has enough tests.
- Bad: several points are open until the first CI run: that Kotlin test resolves to the JUnit 4 variant as the documentation describes, the Bill of Materials' version of `ui-test-junit4`, the JaCoCo version AGP uses, and that AndroidJUnitRunner runs JUnit 4 tests on Android Gradle Plugin 9.3.
- Not decided here: Room and WorkManager testing. Neither library is in the version catalog yet. When each is added, its testing artifact joins with it: Room 2.8.5 has `room-testing`, Room 3.0.3 has `room3-testing` for migration tests, and WorkManager 2.12.0 has `work-testing` ([Room](https://developer.android.com/jetpack/androidx/releases/room), [Room 3](https://developer.android.com/jetpack/androidx/releases/room3), [WorkManager](https://developer.android.com/jetpack/androidx/releases/work)).
- Re-checked: the versions and the open points at the first CI run after publication, and the framework choice when the Android runner documents a newer JUnit.

## Affected modules and contracts

`build-logic/` (the testing plugin and the unit test of the version-code function), `app/`, `modules/android/discovery/`, `contracts/` (no sources yet), `gradle/libs.versions.toml`, `ml/`, `docs/testing/`, `tests/`, and `.github/` (the test jobs `android-unit-tests`, `android-build` and `python-checks`, which first run on the first pull request after the repository is published).

## Enforcement

`testDebugUnitTest` fails the build when a unit test fails, and `assembleDebugAndroidTest` fails when the device tests do not compile; the coverage tasks `createDebugUnitTestCoverageReport` and `createDebugAndroidTestCoverageReport` write the reports, and Gradle does not generate a coverage report if the tests it covers fail. `pytest` fails on a failing Python test. The Gradle build fails on a library alias that is not declared in the version catalog. In CI, the `android-unit-tests` job runs `testDebugUnitTest` and `createDebugUnitTestCoverageReport`, the `android-build` job runs `assembleDebugAndroidTest`, and the `python-checks` job runs `pytest`. None of these jobs has run: each first runs on the first pull request after the repository is published, and no build or test has run. Until then review is the check, and the first CI run after publication checks the versions. Coverage has no failing threshold.

## More information

- [JUnit 4](https://junit.org/junit4/), [JUnit user guide](https://docs.junit.org/current/user-guide/), [Kotlin Gradle configuration](https://kotlinlang.org/docs/gradle-configure-project.html).
- [MockK](https://github.com/mockk/mockk), [Turbine](https://github.com/cashapp/turbine), [kotlinx-coroutines-test](https://github.com/Kotlin/kotlinx.coroutines/blob/master/kotlinx-coroutines-test/README.md).
- [AndroidX Test releases](https://developer.android.com/jetpack/androidx/releases/test), [AndroidJUnitRunner](https://developer.android.com/training/testing/instrumented-tests/androidx-test-libraries/runner), [Compose testing](https://developer.android.com/develop/ui/compose/testing), [Compose Bill of Materials](https://developer.android.com/develop/ui/compose/bom).
- [Android coverage reports](https://developer.android.com/studio/test/coverage-report), [JaCoCo](https://www.jacoco.org/jacoco/trunk/doc/changes.html), [coverage.py](https://coverage.readthedocs.io/en/latest/), [pytest-cov](https://github.com/pytest-dev/pytest-cov).
- Related: [ADR-0001](0001-application-stack.md), [ADR-0003](0003-android-sdk-levels.md), [ADR-0004](0004-inference-runtime.md), [ADR-0006](0006-kotlin-android-style.md), [ADR-0007](0007-module-and-build-structure.md), [ADR-0008](0008-python-lock-tool.md).
