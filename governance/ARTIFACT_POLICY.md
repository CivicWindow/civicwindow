# Artifact policy

**State:** Requirements; provider, credentials, publication rules and retention duration are still open.

An accepted artifact has an immutable identifier and trusted content digest. Its record identifies source revision, included tickets/prerequisites, contracts, resolved direct/transitive dependencies, toolchain/model versions, checks and review. Replacing accepted content under the same version is prohibited; changed selection means a new candidate.

Accepted packages, models, evaluation records, candidate evidence and release outputs must be retrievable for supported baselines. Local `dist/` and a developer cache are not their only durable copies. Select storage, ownership, publishing access, verification metadata, retention and recovery procedures before consuming the first accepted package as a dependency.
