# ADR-0006: Kotlin and Android style and static analysis

- Status: Accepted
- Date: 2026-11-10
- Decision-makers: AI/ML lead, Android/Product lead, Data Integration/Platform lead
- Owner: Android/Product lead (see [ownership and coverage](../operations/OWNERSHIP_AND_COVERAGE.md))
- Supersedes: none
- Superseded by: none

## Context and problem statement

Kotlin and Android code needs one style and one set of checks, so a change reads the same whoever wrote it. The [Software Design Document](../design/SOFTWARE_DESIGN_DOCUMENT.md), section 6, proposes:

```text
Kotlin and Android code would follow the official Kotlin coding conventions and Android's Kotlin style guide, formatted by ktlint and checked by Android Lint and detekt.
```

It adds a review gate and a documentation rule:

```text
A change would pass the formatter and linters before review.
```

and "a KDoc comment on every public Kotlin class and function".

The [coding standards note](../../governance/CODING_STANDARDS.md) says: "Select concrete Kotlin, Android, Python, Gradle, lint and formatter rules with the supported toolchain. A written standard alone is not an executable check." The [style guide](../../governance/STYLE_GUIDE.md) says: "This document does not make an untested Kotlin, Python or Android style choice on behalf of the team."

## Considered options

1. **The official Kotlin coding conventions with the Android Kotlin style guide.** The proposal.
2. **A style invented by the project.** Not pursued: a named standard exists.
3. **Formatting left to each editor.** Rejected by the Design Doc's rule that a change passes the formatter and linters before review.

## Decision outcome

Option 1:

- The [Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html) and the [Android Kotlin style guide](https://developer.android.com/kotlin/style-guide) are the style for every Kotlin file.
- [ktlint](https://ktlint.github.io/ktlint/latest/install/integrations/) formats and checks.
- [detekt](https://detekt.dev/) does static analysis.
- [Android Lint](https://developer.android.com/studio/write/lint) checks Android code.
- A KDoc comment is written on every public class and function.
- [EditorConfig](https://editorconfig.org/) (`.editorconfig`) is the shared editor base that ktlint reads. No project document states this; it is proposed here because ktlint reads that file and every editor can follow it.
- A change passes all of the tools above before review.

Open, to be decided when the configuration is added: the tool versions, the rule sets and any Compose-specific rules.

## Consequences

- Good: one style, checked by tools and not by taste.
- Bad: each tool needs its configuration and upgrades kept current.
- Re-checked: the tool versions and rule sets when the configuration is first added.

## Affected modules and contracts

All Kotlin: `app/`, `modules/`, `contracts/`, `build-logic/`.

## Enforcement

ktlint and Android Lint run in the build and in the `android-static-analysis` job of CI. The job first runs on the first pull request after the repository is published. Their configuration files (`.editorconfig` and `config/lint/lint.xml`) and the `civicwindow.ktlint` and `civicwindow.lint` plugins are in the repository. detekt is added later. No build has run yet, and the first CI run after publication checks the build files. Until then review is the check.

## More information

- [Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html), [Android Kotlin style guide](https://developer.android.com/kotlin/style-guide), [ktlint](https://ktlint.github.io/ktlint/latest/install/integrations/), [detekt](https://detekt.dev/), [Android Lint](https://developer.android.com/studio/write/lint), [EditorConfig](https://editorconfig.org/).
- Related: [ADR-0003](0003-android-sdk-levels.md), [ADR-0005](0005-python-environment.md), [ADR-0007](0007-module-and-build-structure.md).
