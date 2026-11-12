# ADR-0001: Application stack

- Status: Accepted
- Date: 2026-11-10
- Decision-makers: AI/ML lead, Android/Product lead, Data Integration/Platform lead
- Owner: Android/Product lead (see [ownership and coverage](../operations/OWNERSHIP_AND_COVERAGE.md))
- Supersedes: none
- Superseded by: none

## Context and problem statement

The application needs one set of technologies for its screens, its on-device data and its background updates. The [Software Design Document](../design/SOFTWARE_DESIGN_DOCUMENT.md), section 2.1.4, proposes this stack:

```text
| Application and interface | Kotlin and Jetpack Compose |
| Local persistence | Room / SQLite and local full-text indexing |
| Background retrieval | Android WorkManager with network constraints and retry handling |
```

The same section states the limit of that table:

```text
These are proposals. No runtime, model, version or external source is approved merely by appearing in this table.
```

Section 2.1.5 sets the data boundary:

```text
The application needs no CivicWindow server and no user account, and a full local-data deletion control is required.
```

## Considered options

1. **Native Android with Kotlin, Jetpack Compose, Room and WorkManager.** The proposal of the Design Doc.
2. **A cross-platform interface framework.** Not pursued. Section 5 of the Design Doc states: "The platform is native Android only. No iOS version is proposed."
3. **A server-backed design.** Not pursued. Section 2.1.5 (above) states that the application needs no CivicWindow server and no user account.

No standard covers the choice of an application stack; it is the project's choice, made on the product documents.

## Decision outcome

Option 1. The application is a native Android app written in Kotlin. Screens use Jetpack Compose with Material 3. Local data uses Room on SQLite, with local full-text indexing. Background retrieval uses WorkManager. Data stays on the device, and a full local-data deletion control is required.

Library baselines, each proposed as the recent stable release of November 2026 and checked again at the first CI run after publication (the Design Doc, section 5, lists them):

| Library | Baseline |
|---|---|
| Kotlin | 2.4.21 |
| Jetpack Compose | 1.12 line (UI, foundation, runtime 1.12.1) |
| Material 3 | 1.4.0, taken through the Compose Bill of Materials 2026.09.00, which maps to the 1.12 line (the resolved Material 3 version is recorded at the first build) |
| WorkManager | 2.12.0 |
| Room | 2.8.5 |

These versions were checked against the official release pages when the build files were first drafted. Kotlin 2.4.21 is the 2.4 release that [Kotlin's Gradle compatibility table](https://kotlinlang.org/docs/gradle-configure-project.html) lists with the Android Gradle Plugin used ([ADR-0002](0002-build-toolchain.md)), and [Kotlin's release page](https://kotlinlang.org/docs/releases.html) lists it as a bug-fix release. The Bill of Materials version is the one that the [Compose setup page](https://developer.android.com/develop/ui/compose/setup-compose-dependencies-and-compiler) names.

Room 3.0 is a separate package and is not proposed. No project document states this; it is proposed here, because a new major package is a larger risk for a first release.

## Consequences

- Good: the app needs no backend, no account system and no server operation.
- Good: every dependency version is written once, in the version catalog ([ADR-0007](0007-module-and-build-structure.md)), so a change to any of them is a visible change to one file.
- Bad: the stack is Android only. An iOS version would need a new record.
- Re-checked: the baselines at the first CI run after publication, and at each upgrade.
- A new library that changes this stack needs its own record.

## Affected modules and contracts

`app/`, `modules/android/`, `modules/data/local-storage/`, and `modules/data/` generally.

## Enforcement

Every version is written once, in the version catalog ([ADR-0007](0007-module-and-build-structure.md)). The catalog is in the build files; no build has run yet, and the first CI run after publication checks it. No automated check enforces the rule that no version is written outside the catalog yet, so review is the check.

## More information

- Kotlin, Jetpack Compose, Room and WorkManager official documentation.
- Related: [ADR-0002](0002-build-toolchain.md), [ADR-0003](0003-android-sdk-levels.md), [ADR-0007](0007-module-and-build-structure.md).
