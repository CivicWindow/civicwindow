# ADR-0002: Build toolchain

- Status: Accepted
- Date: 2026-11-10
- Decision-makers: AI/ML lead, Android/Product lead, Data Integration/Platform lead
- Owner: Data Integration/Platform lead (see [ownership and coverage](../operations/OWNERSHIP_AND_COVERAGE.md))
- Supersedes: none
- Superseded by: none

## Context and problem statement

The source tree needs one build toolchain that every member can reproduce. The [Software Design Document](../design/SOFTWARE_DESIGN_DOCUMENT.md), section 5, proposes this pairing:

```text
The Android Gradle Plugin (AGP) 9.4 pairs with Gradle 9.6, so those two are proposed together.
```

The versions were checked against the official documentation when the build files were first drafted, and one differs from that proposal. The [Kotlin Gradle plugin compatibility table](https://kotlinlang.org/docs/gradle-configure-project.html) lists no Kotlin release with AGP 9.4, so this record uses AGP 9.3. The [AGP 9.3 release notes](https://developer.android.com/build/releases/agp-9-3-0-release-notes) give Gradle 9.5.0 as the minimum for AGP 9.3; Gradle 9.6.0 is used, and Kotlin's table covers both. The patch is 9.3.3, the newest AGP 9.3 patch; the same release notes list, under 9.3.2, a fix for a lint crash on JDK 17, which the project uses.

Its Developer Tools rows give the versions:

```text
Gradle 9.6.0 with AGP 9.4.0
JDK 17 (the minimum AGP 9 requires)
```

Section 5 also describes the toolchain as a whole:

```text
the toolchain would be Android Studio (a current stable release that supports AGP 9.4), the JDK, the Android SDK and Gradle above, on Windows 10 or 11 development machines
```

Section 7 (Folder Hierarchy) describes the build setup:

```text
the build setup would be Gradle with the Kotlin DSL: a root settings file and build file, a properties file, the Gradle wrapper scripts and, in `gradle/`, one version catalog that holds every dependency version.
```

The [gradle folder note](../../gradle/README.md) records the current state: "The catalog and the wrapper settings are written, but no build has run, so none of them is claimed to work yet." The wrapper scripts are not made yet, so the tree cannot be built until the wrapper command has run; the first CI run after publication checks it.

## Considered options

1. **Gradle 9.6.0 with Android Gradle Plugin 9.3.3.** The pair that the official documentation supports (see the context above).
2. **The newest Gradle release alone.** Not proposed, because the pairing with the plugin is the documented one.
3. **Groovy build scripts against Kotlin build scripts.** The Kotlin DSL is proposed, because the project's language is Kotlin and the Design Doc proposes it.

No standard covers the choice of a build toolchain; it is the project's choice, made on the product documents.

## Decision outcome

Option 1, with the Gradle Kotlin DSL:

- Gradle 9.6.0 and Android Gradle Plugin 9.3.3.
- JDK 17.
- The Gradle wrapper checked in, added with the build files. The wrapper pins Gradle for everyone, so nobody installs Gradle by hand.
- Android Studio: a current stable release that supports Android Gradle Plugin 9.3.
- Windows 10 or 11 development machines, as the Design Doc proposes. Other systems are added only if a member needs them.

These versions were proposed as the recent stable release of November 2026 and were checked again before the build files were written, which gave the set in the version catalog; the first CI run after publication confirms them. The exact Android Studio version is open.

## Consequences

- Good: one pinned toolchain; a build behaves the same on every machine and in CI.
- Bad: Gradle, the plugin and Kotlin move together, so an upgrade is one deliberate change.
- Major updates of the Android Gradle Plugin and of Kotlin are made by hand, in one change, checked against the pairing this record states. The dependency-update automation is set to ignore only those major updates.
- All other updates, including the Gradle wrapper, arrive as dependency-update pull requests and are checked against the same pairing before they merge.
- Re-checked: the versions at the first CI run after publication, and at each major update of the plugin or Kotlin.

## Affected modules and contracts

`build-logic/`, `gradle/`, the root build files, every module with a build file, and CI.

## Enforcement

The wrapper properties and the toolchain declaration in the build files fix the versions; no build has run yet. The wrapper script is made by the first run of the wrapper command. CI uses the same JDK (JDK 17: `setup-java` with the `temurin` distribution), and each job first runs on the first pull request after the repository is published; until the first CI run, review is the check.

## More information

- Gradle, Android Gradle Plugin and Kotlin Gradle plugin release notes.
- Related: [ADR-0001](0001-application-stack.md), [ADR-0007](0007-module-and-build-structure.md).
