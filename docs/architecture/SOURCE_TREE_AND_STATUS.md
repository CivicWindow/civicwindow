# Source tree and staging status

**Current root:** This repository's root is the one source tree being constructed. No Git repository, branches, linked worktrees, remote or role clones exist until the team publishes it (see [GitHub setup](../operations/GITHUB_SETUP.md)).

```text
CivicWindow/                         Current single source root
├── .github/                         Issue, review and CI controls (the CI workflows exist; none has run yet)
├── governance/                      Shared policies for every role
├── schemas/                         Shared versioned data definitions (pending)
├── contracts/                       Shared code interfaces (pending)
├── docs/                            Shared product, design, technical, operations
├── role-configs/                    Role build files, one per role (no build has run yet; the first CI run checks them)
├── app/                             Android host, minimal first screen (no build has run yet; the first CI run checks it)
├── modules/
│   ├── ai/                          Classifier, matcher, inference runtime
│   ├── android/                     Seven product feature areas
│   └── data/                        Four source/platform feature areas
├── ml/                              Development-time AI/ML assets
├── connectors/                      Source registry, fixtures and checks
├── tests/                           Cross-role and workflow checks
├── scripts/                         Planned PowerShell controls
├── build-logic/                     Build conventions (no build has run yet; the first CI run checks them)
├── gradle/                          Pinned build inputs (wrapper files not yet made; the first CI run checks them)
└── dist/                            Local generated output; excluded from Git
```

The 14 feature areas are already represented as directories. Every feature has `src/main` and `src/test`; Android areas also have `src/androidTest`. The Android app has the same three source/test tiers. `contracts/src/main/kotlin/org/civicwindow/contracts` and `contracts/src/test` reserve the common interface locations. These locations contain directory markers only until approved code and tests exist; the exception is the first minimal source in `app/` and `modules/android/discovery/`, where no build has run yet and the first CI run after publication checks it.

| Layer | Present now | Still required to make it operational |
|---|---|---|
| Shared rules and project overview | Initial meaningful Markdown documents | Ratification and owner review |
| Schemas and Kotlin contracts | Directory indexes | Agreed fields, interface behavior, versions and validation |
| Role environments | Ownership and process documentation; the `.properties` role files in `role-configs/` (project lists, no build has run yet) | The rest of each role profile (owned source, tools, check commands), accepted inputs, clean-checkout proof; the first CI run after publication checks the toolchain |
| Feature areas | 14 definitions and source directories; a minimal first module (`discovery`) with a build file | Code, module manifests, tests and accepted dependencies; the first CI run after publication checks the first module |
| Git collaboration | Planned branch and ticket rules; the CI workflows in `.github/workflows/` (none has run yet) | One initialized repository, role views, tracker, review controls and the first CI run, on the first pull request after publication |
| Release and distribution | Process documentation, local `dist/` | Real candidate manifest, builds, checks, hashes and retained artifacts |
| Faculty cloud folder | Mapping described in planning | Chosen service, approved snapshots, sharing and submitted link |

**Directory marker policy:** `.gitkeep` may appear only in an otherwise empty planned leaf. It preserves a directory when the repository is created and is never implementation, a test, an approval or evidence of a build. `dist/` intentionally has no tracked marker because it is generated output. Empty JSON schemas and scripts are deliberately absent until their actual inputs and behavior are decided. The CI workflows in `.github/workflows/` are not empty: each runs real commands, and none has run yet; the first run is the first pull request after the repository is published.

The external `CivicWindow_Blueprint_v0.7.md` proposal is kept in the COS359 planning workspace, outside this source root; this file records the current physical mapping without copying that candidate into project authority. The older v0.6 drawing retains its original form in that workspace. Reconcile and approve future design changes there before treating them as implemented here.
