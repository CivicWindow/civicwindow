# ADR-0007: Module and build structure

- Status: Accepted
- Date: 2026-11-10
- Decision-makers: AI/ML lead, Android/Product lead, Data Integration/Platform lead
- Owner: Data Integration/Platform lead (see [ownership and coverage](../operations/OWNERSHIP_AND_COVERAGE.md))
- Supersedes: none
- Superseded by: none

## Context and problem statement

The repository has fourteen top-level folders and three leads who work on their own areas at their own pace. The build needs a structure in which versions are written once, shared rules are not copied, and one unfinished part does not stop work elsewhere. The [Software Design Document](../design/SOFTWARE_DESIGN_DOCUMENT.md), section 7 (Folder Hierarchy), says:

```text
the build setup would be Gradle with the Kotlin DSL: a root settings file and build file, a properties file, the Gradle wrapper scripts and, in `gradle/`, one version catalog that holds every dependency version.
```

```text
The fourteen folders express ownership areas. They do not imply fourteen build modules.
```

The folder notes and the build profiles add:

- [`build-logic/README.md`](../../build-logic/README.md): "Focused role builds must explicitly select required projects; absent source cannot silently disappear".
- [`gradle/README.md`](../../gradle/README.md): "The catalog and the wrapper settings are written, but no build has run, so none of them is claimed to work yet." The wrapper scripts, the verification metadata and the lock files are not made yet.
- [`docs/operations/BUILD_PROFILES.md`](../operations/BUILD_PROFILES.md): "Missing required source or artifacts fail clearly, rather than silently omitting modules or choosing another version." and "Record resolved direct/transitive versions and verify artifact contents."

## Considered options

1. **Versions written in each module's build file.** Rejected: the Design Doc asks for one version catalog that holds every dependency version.
2. **One version catalog, with shared rules in convention plugins.** The proposal.
3. **Shared build rules copied into each module.** Rejected: a copy drifts. Shared convention plugins in an included build keep one source.

No standard covers the layout of a project's build; it is the project's choice, made on the product documents and the folder notes above.

## Decision outcome

Option 2, as six rules:

1. One version catalog, `gradle/libs.versions.toml`, is the only place a dependency or plugin version is written.
2. Shared build rules live as convention plugins in `build-logic`, and modules apply them.
3. Dependency locking and dependency verification metadata are used, so a resolved version and an artifact's content are checked.
4. A role build selects its projects explicitly, and fails when a required project or artifact is missing.
5. A Gradle module exists where a boundary can be tested on its own. The app composes accepted implementations through `contracts/`. The first module graph is set in `settings.gradle.kts`; later modules follow the real dependency graph, under these rules. No project document states this rule; it is proposed here.
6. Work is independent. Each lead works on tickets in their own area, cut from a recorded `dev` baseline ([ticket workflow](../operations/TICKET_WORKFLOW.md)). A blocked ticket or an unbuilt acceptance criterion records its reason and holds up no unrelated work. The ticket workflow says: "A blocked ticket records its reason; it does not hold an unrelated completed ticket." The [branching policy](../../governance/BRANCHING_POLICY.md) keeps `main` for approved production source and `dev` for integrated development, so `dev` is not the production artifact. The production artifact lands through the [release process](../operations/RELEASE_PROCESS.md), which confirms that `main` contains the validated production source.

This record writes no build file and no module list; it states rules only.

## Consequences

- Good: a version appears once, so upgrades and dependency-update pull requests touch one file.
- Good: the build is independent by design. A role build names only the projects it needs, so a project outside that selection that is not yet built does not stop it; a required project that is missing fails clearly.
- Good: every lead has a job in their own area, and the build is not held up by one unbuilt ticket or acceptance criterion (rule 6).
- Bad: the catalog, convention plugins and verification metadata need upkeep at each dependency change.
- Re-checked: the module graph at the first CI run after publication.

## Affected modules and contracts

`build-logic/`, `gradle/`, the root build files, every module, and `role-configs/`.

## Enforcement

The build fails on a missing required project (the settings script) and on a plugin or library alias that the catalog does not declare. Nothing in the build fails on a version written outside the catalog: review is the check on versions until a build check is added. No build has run yet, and the first CI run after publication checks the build files.

Rule 6: pull-request review now; the branch rules once they are turned on when the repository is published; a ticket's status records a block.

## More information

- Gradle documentation on version catalogs, convention plugins, dependency locking and dependency verification.
- [Release process](../operations/RELEASE_PROCESS.md), [ticket workflow](../operations/TICKET_WORKFLOW.md), [branching policy](../../governance/BRANCHING_POLICY.md).
- Related: [ADR-0001](0001-application-stack.md), [ADR-0002](0002-build-toolchain.md), [ADR-0006](0006-kotlin-android-style.md).
