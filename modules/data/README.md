# Data Integration/Platform subsystem

**Status:** Ownership map; implementation and build modules are not present yet.

**Owned areas:** `modules/data/ and connectors/`. Public-source connectors, normalization, source evidence, retrieval reliability and local persistence.

Every lead receives the complete shared `governance/`, `schemas/`, `contracts/` and `docs/` foundation. This role uses its own source, tests, declared tooling and pinned accepted cross-role dependencies. A proposed feature folder does not establish a separately buildable module.

See [architecture](ARCHITECTURE.md) and [testing](TESTING.md) for the intended boundary and evidence. Actual module manifests are added after interfaces and build boundaries are defined.
