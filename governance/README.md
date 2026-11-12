# Shared engineering foundation

Every AI/ML, Android/Product and Data/Platform working copy receives this entire directory. Documents here describe rules across all three roles. This is written guidance; automation and repository protections are not active yet.

| Document | Responsibility | Status |
|---|---|---|
| `BRANCHING_POLICY.md` | Branch baselines and ticket isolation | See the document's own state line |
| `INTEGRATION_POLICY.md` | Exact candidate selection and review | See the document's own state line |
| `MODULE_STANDARD.md` | Module interfaces, dependencies and evidence | See the document's own state line |
| `SCHEMA_VERSIONING.md` | Compatibility, migration and affected-role review | See the document's own state line |
| `DEFINITION_OF_DONE.md` | Role acceptance, integration and release states | Concrete: the table names how each state is checked, and no check has run yet |
| `ARTIFACT_POLICY.md` | Immutable identity and retention | See the document's own state line |
| `SECURITY_POLICY.md` | Credentials, data and authority | See the document's own state line |
| [`CODING_STANDARDS.md`](CODING_STANDARDS.md) | Shared coding rules, each paired with its check; adding a tool, package or dependency | Concrete: the decision record, tool and command for Kotlin, Android, Python and Gradle. Checks Proposed, not yet run |
| [`STYLE_GUIDE.md`](STYLE_GUIDE.md) | Shared conventions and naming | Concrete: it links the formatter and naming decisions. Checks Proposed, not yet run |
| `DOCUMENTATION_STANDARD.md` | Shared conventions for maintained documents | Concrete: the code-comment rule and the model and dataset record lists are added; the Python docstring check is Proposed and has not run, and the Kotlin comment rule has no check except review |

Shared changes later enter a reviewed integration baseline and reach roles through explicit updates that preserve unfinished work. Keep one authoritative copy of each rule.

**Conventions.** Decisions are recorded as [architecture decision records](../docs/adr/README.md). The commit, version, changelog, conduct and secrets conventions are listed in [CONTRIBUTING.md](../CONTRIBUTING.md). They follow named industry standards and apply the same way to every module and branch.

Enforcement: checked in pull-request review; the `commit-messages` and `commit-format` jobs check commit messages in continuous integration, and they first run on the first pull request after the repository is published. The checks that each concrete document names run in the CI jobs listed in the [workflows README](../.github/workflows/README.md); none has run yet.
