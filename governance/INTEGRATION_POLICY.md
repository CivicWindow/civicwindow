# Integration policy

**State:** Design rule; no candidates, CI or PR protections exist yet.

Select completed tickets with their complete prerequisite set. A candidate identifies the exact source revision/selection, included tickets, contract versions, direct and transitive dependencies, external model/assets, artifact digest, checks and reviewer disposition. A package built from a branch containing tickets A and B cannot be called A-only unless its actual source and contents exclude B.

Propose selected work against the current `dev` revision. The integrator (see [ownership](../docs/operations/OWNERSHIP_AND_COVERAGE.md)) merges it into `dev` with the other lead's approval, and merges `dev` into `main` the same way ([GitHub setup](../docs/operations/GITHUB_SETUP.md)). Resolve conflicts explicitly and refresh affected checks after source, dependency or target changes. Validate the actual integrated application with real implementations; role tests using substitutes establish only isolated behavior.

A release candidate is one fixed source commit with a dependency manifest. Continued integration on `dev` must not mutate that candidate. A changed candidate requires applicable checks and review to be refreshed before production promotion. The production source must correspond to the validated output.
