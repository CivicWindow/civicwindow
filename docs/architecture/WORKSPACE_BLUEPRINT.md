# CivicWindow workspace and source layout

**Status:** Current physical mapping and future Git design. This file records the staging arrangement; it is not evidence of initialized Git or working role builds.

The CivicWindow source root is this repository's root. Its source directories live directly here.

The full shared foundation is `governance/`, `schemas/`, `contracts/`, and `docs/`. Role areas are `modules/ai/` and `ml/` for AI/ML; `app/` and `modules/android/` for Android/Product; `modules/data/` and `connectors/` for Data/Platform. `role-configs/`, `build-logic/`, and `gradle/` provide the shared build support (drafted; no build has run yet, and the first CI run after publication checks it); `tests/` and `scripts/` provide future shared validation support. `dist/` is local generated output.

One future Git repository holds the complete source. Its permanent branches are `main`, `dev`, `role/ai-ml`, `role/android`, and `role/data-platform`. Developers use separate local clones or linked worktrees outside this canonical source root, each with a focused sparse view plus the full shared foundation and all declared build prerequisites. These working copies are not separately published products.

The older v0.6 outer-container drawing differs from this present staging arrangement. The v0.7 proposal records the physical mapping explicitly while retaining the one-repository and three-role architecture. The role build files (project lists) are written; the exact sparse checkout paths and the remaining build rules remain to be written after their dependencies are selected.
