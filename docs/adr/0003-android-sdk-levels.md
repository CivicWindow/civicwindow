# ADR-0003: Android SDK levels

- Status: Accepted
- Date: 2026-11-10
- Decision-makers: AI/ML lead, Android/Product lead, Data Integration/Platform lead
- Owner: Android/Product lead (see [ownership and coverage](../operations/OWNERSHIP_AND_COVERAGE.md))
- Supersedes: none
- Superseded by: none

## Context and problem statement

The build and the test plan need three Android levels: the lowest supported, the one compiled against and the one targeted. The [Software Design Document](../design/SOFTWARE_DESIGN_DOCUMENT.md), section 2.1.3, proposes them:

```text
the minimum would be Android 8.0 (API level 26) and the target Android 16 (API level 36), the level Google Play requires of new applications from 31 August 2026
```

It also sets the test coverage:

```text
Testing would cover the minimum, a middle release and the target, and at least one physical device.
```

Section 5 repeats the levels:

```text
Minimum API level 26 (Android 8.0); compile and target API level 36 (Android 16), the level Google Play requires for new apps from 31 August 2026
```

## Considered options

1. **minSdk 26.** The proposal.
2. **A lower floor.** A lower floor widens the test matrix.
3. **A higher floor.** It narrows the set of supported phones.

The Design Doc proposes the floor and gives no reason for it. No project document states the reason; it is proposed here because a floor of 26 limits the test matrix and keeps notification channels and WorkManager available.

## Decision outcome

Option 1: minSdk 26, compileSdk 37 and targetSdk 36. Testing covers the minimum, a middle release and the target, and at least one physical device. The target follows the Google Play requirement for new applications and is raised in step with it. The levels are proposed as the Design Doc's baseline, and are checked again at the first CI run after publication.

The compile level differs from the Design Doc's proposal of 36. The [Compose setup page](https://developer.android.com/develop/ui/compose/setup-compose-dependencies-and-compiler) states that, starting with Compose 1.12.0, projects are required to use compileSdk 37 and Android Gradle Plugin 9, and the [AGP 9.3 release notes](https://developer.android.com/build/releases/agp-9-3-0-release-notes) list support up to API level 37. The compile level is separate from the target level, as the [Compose compiler page](https://developer.android.com/develop/ui/compose/compiler) describes, so the target stays 36 and the minimum stays 26.

## Consequences

- Good: a small test matrix; notification channels and background work are available on every supported phone.
- Bad: phones below Android 8.0 are not supported.
- The target level is revisited at each yearly Google Play requirement.
- Raising minSdk needs a new record that supersedes this one.

## Affected modules and contracts

`app/`, every Android module, and the test plan.

## Enforcement

The levels are declared once in the build files (the version catalog); no build has run yet, and the first CI run after publication checks them. The Android Lint configuration is in `config/lint/lint.xml` and the `civicwindow.lint` plugin, and Android Lint runs in `check` and in the `android-static-analysis` job of CI, which first runs on the first pull request after the repository is published. Whether its target-SDK checks fire is open until the first build. Until then review is the check.

## More information

- Google Play target API level requirement; Android API level documentation.
- Related: [ADR-0001](0001-application-stack.md), [ADR-0006](0006-kotlin-android-style.md).
