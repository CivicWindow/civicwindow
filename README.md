# CivicWindow

CivicWindow is a proposed native Android app for discovering municipal and state civic records using local AI. This directory is the repository root.

**Current state:** Repository skeleton: the folder layout, initial documentation, the GitHub setup files (issue forms, a pull request template, `CODEOWNERS`), the first build files, the [CI workflows](.github/workflows/README.md) and a minimal app screen. No build or workflow has run yet, and the first CI run after publication checks the build files. Schemas, further application features and releases have not been implemented. The project is selected and the three leads are assigned; the slugs of their role teams go into `.github/CODEOWNERS` when the repository is published. See [GitHub setup](docs/operations/GITHUB_SETUP.md).

**Team:**
- AI/ML lead
- Android/Product lead
- Data Integration/Platform lead

**License:** MIT, see [LICENSE](LICENSE). Third-party models, libraries and civic data keep their own terms.

The engineering design uses **one** future Git repository containing the complete product. Planned branches are `main` (production source), `dev` (integration), `role/ai-ml`, `role/android`, and `role/data-platform`. Each lead later works in an independent clone or linked worktree with a focused source view. Every role receives complete `governance/`, `schemas/`, `contracts/`, and `docs/` plus declared build inputs, tests and accepted dependencies.

| Area | Purpose |
|---|---|
| `governance/` | Shared engineering rules |
| `schemas/`, `contracts/` | Versioned common data and interfaces when defined |
| `docs/` | Product, design, architecture, testing and operations |
| `role-configs/` | Role build files (project lists per role); exact role workspaces and profiles later |
| `app/`, `modules/android/` | Android application and features |
| `modules/ai/`, `ml/` | On-device AI integration and development-time model work |
| `modules/data/`, `connectors/` | Source retrieval and local data handling |
| `tests/`, `scripts/`, `build-logic/`, `gradle/` | Validation support (`tests/`, `scripts/`) when implemented; build support (`build-logic/`, `gradle/`) drafted, with no build run yet; the first CI run after publication checks it |
| `dist/` | Local generated output, excluded from future source history |

## Technical overview

CivicWindow is planned as **one source repository**, with GitHub as the intended collaboration surface, and three lead lanes: AI/ML, Android/Product and Data Integration/Platform. `main` holds approved production source, `dev` holds validated integration, and `role/ai-ml`, `role/android` and `role/data-platform` hold reviewed work for each lead. An engineer works from a recorded baseline on an isolated ticket branch. Completed work is selected with its actual prerequisites for a PR to `dev`; a blocked unrelated ticket stays out of that candidate. A fixed, separately validated composition later supplies `main`. Each role has a focused working copy but receives the complete shared foundation and all declared inputs.

Read the [repository technical overview](docs/architecture/REPOSITORY_TECHNICAL_OVERVIEW.md) for the ownership map, proposed GitHub route, evidence boundaries and open decisions. The overview describes a planned workflow: no GitHub repository, role branch, CI run or release exists yet.

The [application build plan](docs/architecture/APPLICATION_BUILD_PLAN.md) maps pitch v1.3 core features to proposed source areas, product evidence and unresolved implementation choices.

Start with the [workspace map](docs/architecture/WORKSPACE_BLUEPRINT.md), [contribution guide](CONTRIBUTING.md), and [shared governance index](governance/README.md). Most feature folders contain planning definitions and reserved source directories and are not working components; `modules/android/discovery/` and `app/` hold a minimal first source, and no build has run yet; the first CI run after publication checks it. See the [source-tree and status map](docs/architecture/SOURCE_TREE_AND_STATUS.md).

To set up a machine, follow the [development environment guide](docs/operations/DEV_ENVIRONMENT.md) and [role onboarding](docs/operations/ROLE_ONBOARDING.md). The [ticket-board guide](docs/operations/TICKET_BOARD_SETUP.md) maps the course's branch plan, board and weekly test to this workflow.
