# Module dependencies

**Status:** Boundary proposal. The actual Gradle and Python dependency graph is not finalized.

`contracts/` and `schemas/` define approved interfaces and data shapes for all roles. Data/Platform implementations produce normalized records and source evidence. AI/ML implementations consume record contracts and produce classification/matching results. Android/Product composes these modules and stores/presents results. No role should depend on another role's unfinished source in its normal independent build.

Cross-role development dependencies use pinned, accepted artifacts and explicit contract versions. Isolated tests may use contract-conforming substitutes; release composition uses real selected implementations. Root settings, generators, lockfiles, fixtures and test resources belong in each role's declared build profile where required.

Do not create one build project per folder merely to make the visual tree look modular. The first three build projects, `:app`, `:modules:android:discovery` and `:contracts`, are listed in `settings.gradle.kts` (the rules for the build structure are in [ADR-0007](../adr/0007-module-and-build-structure.md)); no build has run yet, and the first CI run after publication checks them. Later build projects follow the dependency graph, one at a time. Record producer/consumer tests and migration impact for shared interface changes.
