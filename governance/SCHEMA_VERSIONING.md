# Schema and contract versioning

**State:** Compatibility policy; application schema contents are not yet defined.

Every shared schema and executable contract needs a declared version and a known consumer/producer set. Proposed changes run applicable producer and consumer checks. A breaking change requires affected-role review, an ADR, migration expectations for stored data or exports when relevant, and an explicit adoption plan.

If old and new forms coexist, record supported versions, transition owner, acceptance condition and retirement condition. Publishing a role candidate alone does not make a new shared contract authoritative. Affected roles update their recorded baselines through reviewed work that preserves unfinished tickets.
