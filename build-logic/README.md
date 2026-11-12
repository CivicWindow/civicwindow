# Shared build logic

**Status:** Proposed. The convention plugins below are written, but no build has run, so none of them is claimed to work yet. The first run is the first CI run after the repository is published.

Gradle conventions and project selection belong here. Focused role builds must explicitly select required projects; absent source cannot silently disappear (project selection itself is in `settings.gradle.kts` at the repository root, see [role-configs](../role-configs/README.md)).

This folder is an included build: a module applies a convention plugin by id and writes no build rule of its own (see [ADR-0007](../docs/adr/0007-module-and-build-structure.md)). Every version comes from the [version catalog](../gradle/libs.versions.toml).

## Convention plugins

All are precompiled script plugins in `src/main/kotlin/`.

| id | what it does |
|---|---|
| `civicwindow.base` | The rules every project shares: the JDK 17 toolchain from the catalog, dependency locking on every configuration, and Kotlin compiler warnings as errors. It also holds marked places for what is still to come (detekt, and coverage for the Kotlin JVM projects). |
| `civicwindow.kotlin-library` | The base rules and the Kotlin JVM plugin. |
| `civicwindow.android-library` | The base rules and the Android library plugin. Kotlin support is the one built into the Android Gradle Plugin, so `org.jetbrains.kotlin.android` is not applied. SDK levels and the Java level come from the catalog. |
| `civicwindow.android-application` | The base rules and the Android application plugin, with the SDK levels from the catalog. It reads the four `civicwindow.version.*` keys of `gradle.properties` and sets `versionName` and `versionCode`. |
| `civicwindow.compose` | The Compose compiler plugin and the Compose Bill of Materials. It is applied after an Android plugin. |
| `civicwindow.ktlint` | The ktlint formatter as a build check: `ktlintCheck` fails on a finding and runs in `check`; `ktlintFormat` rewrites files and is not part of `check`. The code style and the column limit come from the Kotlin section of `.editorconfig`. The base plugin applies it to `:app`, `:modules:android:discovery` and `:contracts`; the root build files and `build-logic` itself are outside it. |
| `civicwindow.lint` | Android Lint options for the Android application and library projects: the build fails (task `lintDebug`, run by `check`) on any Android Lint warning or error, and the two target-SDK checks are fatal in `config/lint/lint.xml`. A Kotlin JVM project has no Android Lint, so it changes nothing there. The base plugin applies it. |
| `civicwindow.testing` | The test libraries, the instrumentation runner and the coverage switches for the Android application, Android library and Kotlin JVM projects. `check` runs the unit tests; coverage is measured on the debug build type with no failing threshold. It names no version; every version comes from the catalog. The base plugin applies it. |

`VersionCode.kt` holds `computeVersionCode`, a plain function with no Gradle types, so that it can be tested alone. The formula and its limits are described in the [release process](../docs/operations/RELEASE_PROCESS.md); no standard covers the formula, it is the project's choice.

The plugins for ktlint, Android Lint and the tests are in this folder, each in its own file. The plugin for detekt is added later.

## Failure behavior

- A Kotlin compiler warning fails the build.
- A resolved dependency that differs from its lock file fails the build.
- A missing or non-numeric `civicwindow.version.*` key, or a part out of range, fails the build with a message naming the key or the part.
- A catalog value or alias that is missing fails the build.

Standards: Gradle documentation on [convention plugins in an included build](https://docs.gradle.org/current/userguide/sharing_build_logic_between_subprojects.html), [precompiled script plugins](https://docs.gradle.org/current/userguide/custom_plugins.html#sec:precompile_script_plugin) and [dependency locking](https://docs.gradle.org/current/userguide/dependency_locking.html); Android Gradle Plugin documentation on [built-in Kotlin](https://developer.android.com/build/migrate-to-built-in-kotlin); Kotlin Gradle plugin documentation on [compiler options](https://kotlinlang.org/docs/gradle-compiler-options.html).

Enforcement: the Gradle build applies these plugins and fails on the behavior above. The ktlint, Android Lint and test plugins run in `check`; the `android-static-analysis`, `android-unit-tests` and `android-build` jobs of CI run them, and each job first runs on the first pull request after the repository is published. The plugin for detekt is added later. Review is the check until then.
