# Android/Product architecture boundary

**Status:** Proposed responsibility and interfaces; no implementation is claimed.

**Consumes:** Approved record, model-result and connector/storage contracts, plus pinned accepted implementations.

**Produces:** Accessible user workflows, source-linked views, refresh state, user-directed exports and applicable Android packages.

**Owned source:** `app/ and modules/android/`. Shared contracts define cross-role behavior and changes require affected-role review. Development may use contract-conforming substitutes for isolated checks; full integration and release use real selected implementations. The exact build graph, schema fields and runtime choices remain open.
