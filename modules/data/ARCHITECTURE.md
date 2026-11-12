# Data Integration/Platform architecture boundary

**Status:** Proposed responsibility and interfaces; no implementation is claimed.

**Consumes:** Validated source terms, source formats and approved common record/interface contracts.

**Produces:** Normalized civic records, retrieval status, source links and local storage interfaces.

**Owned source:** `modules/data/ and connectors/`. Shared contracts define cross-role behavior and changes require affected-role review. Development may use contract-conforming substitutes for isolated checks; full integration and release use real selected implementations. The exact build graph, schema fields and runtime choices remain open.
