# AI/ML architecture boundary

**Status:** Proposed responsibility and interfaces; no implementation is claimed.

**Consumes:** Approved civic-record, source-evidence and interest-profile contracts; validated examples and explicitly versioned model/runtime assets.

**Produces:** Classification and relevance results with uncertainty, model provenance and deployable model manifests.

**Owned source:** `modules/ai/ and ml/`. Shared contracts define cross-role behavior and changes require affected-role review. Development may use contract-conforming substitutes for isolated checks; full integration and release use real selected implementations. The exact build graph, schema fields and runtime choices remain open.
