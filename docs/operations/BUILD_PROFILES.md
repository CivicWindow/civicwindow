# Build profiles

**Status:** Input contract. Supported tool versions are selected in the [build toolchain record](../adr/0002-build-toolchain.md), and the first role build files (project lists) are in `role-configs/`; no build has run yet, and the first CI run after publication checks them. The full executable profiles (owned source, tools and exact check commands) are not written.

A role profile must enumerate owned source, complete shared foundation, root settings/build logic, generators, tests/fixtures/scripts, required accepted packages, toolchain and exact check commands. The role build explicitly selects projects that exist in its sparse view. Missing required source or artifacts fail clearly, rather than silently omitting modules or choosing another version.

Isolated tests can select controlled substitutes and must label that mode. Full integration and release profiles use real selected implementations and an exact dependency manifest. Record resolved direct/transitive versions and verify artifact contents. Validate each profile from a fresh checkout and empty cache before calling it reproducible.
