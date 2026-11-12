# Module standard

A feature area records its owner, purpose, inputs, outputs, public interface, dependencies, failure behavior, checks and acceptance criteria. Its README and architecture/testing documents explain the implemented behavior and evidence. A `module.yaml` is added after its authoritative fields and actual module boundary are defined. A directory's presence is not evidence of a buildable module.

Choose Gradle or other build boundaries from the real dependency graph; do not create a separate build module solely because a feature directory exists. Role builds declare owned projects, complete shared prerequisites and pinned accepted dependencies. Missing required inputs fail clearly. Test substitutes are explicit and satisfy the same contract, but cannot qualify the release.

A generated candidate record describes one immutable source selection and artifact with evidence. It is distinct from a maintained module definition. Changed source or dependencies require a new candidate.
