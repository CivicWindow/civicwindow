# Data flow and evidence

**Status:** Proposed flow; no connector, model or storage implementation is present.

1. Validate source access and retrieve a record with source identity, timestamp and retrieval outcome.
2. Normalize content and metadata without losing the original official link or source identifiers.
3. Persist downloaded records locally; keep user interest profiles on the device.
4. Classify and match local record content with on-device models, retaining model/version and uncertainty.
5. Combine outputs and check that presented excerpts and links correspond to retrieved evidence.
6. Present results, optionally notify after successful refresh and matching, and export selected evidence on user request.

Failures remain visible: unavailable source, malformed input, stale data, unavailable model and an empty successful result are different states. Offline views must identify the last successful refresh and must not imply current monitoring. Actual schema fields and storage migrations are pending.
