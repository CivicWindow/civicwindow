# Release process

**Status:** Requirements; no release workflow or signed build exists.

Choose an exact integrated source commit and dependency/model/asset manifest. Run the required application, physical-device, security and packaging checks against that fixed candidate. New `dev` changes cannot change it. Refresh affected evidence if the candidate changes. Confirm `main` contains the validated production source and record AAB/APK/archive checksums, signing identity reference and retained locations.

Where finished builds are kept is chosen by the project owner before the first release and recorded in the release record.

Do not store signing secrets in source. Preserve the previous approved release if a candidate fails. A binary's checksum can be recorded alongside the completed archive; avoid requiring an archive to contain its own final checksum. Distribution and store publication are separate authorized actions.

## Versions and tags

CivicWindow is one product in one repository, so it has one version. It follows [Semantic Versioning 2.0.0](https://semver.org/): `MAJOR.MINOR.PATCH`, with an optional pre-release part such as `-rc.1` for a release candidate.

- **Before the first store release** the version is `0.MINOR.PATCH`. `1.0.0` is the first version published to a store. No standard sets this; it is the project's choice.
- **MAJOR, MINOR, PATCH.** Semantic Versioning defines these for a public API. For this app the compatibility surface is stored user data and exported files: MAJOR changes when a change breaks either; MINOR adds behavior that keeps both working; PATCH only fixes defects. No standard covers this mapping; it is the project's choice.
- **Version name:** the version, for example `0.3.0`.
- **Version code:** the integer Android requires to rise with every build, derived by a fixed formula: `MAJOR x 1,000,000 + MINOR x 10,000 + PATCH x 100 + BUILD`, where MINOR, PATCH and BUILD each run from 0 to 99 and MAJOR stays below 2,100, so that no two versions share a code and the code stays within the limit Android allows. BUILD counts builds of the same version. When a part would pass 99, the next part up is raised instead (after 1.0.99 comes 1.1.0; after build 99 of a version, the next build is a new PATCH), and a lasting need for more room is met by a new decision record that replaces the formula. The limit of 99 on each part is a project rule that exists only to make the formula work: Semantic Versioning sets no upper bound on any part, and no standard covers this. No standard defines the formula either; Android requires only that the number rises. Example: 1.2.3 build 4 is 1020304.
- **One place:** the version is written once in the build, and everything else reads it.
- **Tags:** a release is tagged `v<version>` (for example `v0.3.0`) and a candidate `v<version>-rc.<n>`. A tag is an annotated tag on the exact commit checked as that release or candidate, and is never moved or reused ([artifact policy](../../governance/ARTIFACT_POLICY.md)). The release coordinator named in [ownership and coverage](OWNERSHIP_AND_COVERAGE.md) creates it.
- **Schemas and contracts** carry their own versions ([schema and contract versioning](../../governance/SCHEMA_VERSIONING.md)) and are not tied to the app version.
- **A hotfix** raises PATCH, is tagged from `main`, and returns to `dev` as the [branching policy](../../governance/BRANCHING_POLICY.md) says.

Enforcement: the build takes the version name and code from the one place; review is the check until the first release; a check that a tag equals the version in the build and that the changelog has the matching heading is added with the release workflow.
