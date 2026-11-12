# Ownership and coverage

**Status:** Roles are designed and the three leads are assigned. The hosted permissions are set when the repository is published (see [GitHub setup](GITHUB_SETUP.md)).

This table is the single source that maps the leads to their roles. Other files in this repository name roles only.

| Lead | Role | Owned paths | Branch | Backup reviewer |
|---|---|---|---|---|
| Dwight Thompson | AI/ML | `modules/ai/`, `ml/` | `role/ai-ml` | Steven Asphodel |
| Steven Asphodel | Android/Product | `app/`, `modules/android/` | `role/android` | Dwight Thompson |
| Dwight Thompson | Data Integration/Platform | `modules/data/`, `connectors/` | `role/data-platform` | Steven Asphodel |

All three share the shared foundation: `governance/`, `schemas/`, `contracts/`, `docs/`, and the build and test support. Each area's backup reviewer's team is its second code owner in `.github/CODEOWNERS`; the code owners there are the three role teams (see [GitHub setup](GITHUB_SETUP.md)).

Primary responsibility of each lead, from the blueprint (v0.7 §1.2):

| Lead | Primary responsibility |
|---|---|
| AI/ML lead | Taxonomy, datasets, training, evaluation, inference integration and model packaging |
| Android/Product lead | Application composition, interface, user flows, accessibility and Android behavior |
| Data Integration/Platform lead | Connectors, normalization, persistence, retrieval reliability and platform tooling |

For a three-person team, the pitch (v1.3) combines data integration and platform into one role, and says: "All three share testing, code review, and release work." Every member contributes code each week.

**Integration coordination** (the integrator): the Data Integration/Platform lead, for now, with the Android/Product lead as backup (see the table above). The integrator merges each role branch into `dev` and `dev` into `main`, and every one of those pull requests carries the other lead's approval ([GitHub setup](GITHUB_SETUP.md)). The team can change it.

**Release coordination:** the Android/Product lead (see the table above). The release coordinator reviews and approves each pull request from `dev` into `main`; the integrator merges it. Integration coordination, affected-contract review, candidate publishing and release coordination are assigned among these same three leads with backup coverage. This is not a fourth engineer role.

Record a named owner and qualified reviewer for each candidate once staffed. A missing reviewer or release owner is an explicit readiness gap. CODEOWNERS routing alone does not prove every affected role approved a change; the rulesets in [GitHub setup](GITHUB_SETUP.md) (step 12) require one approval and a code-owner review on `dev` and `main`, and the reviewer checks that every affected role was covered.
