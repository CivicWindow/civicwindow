# Product requirements and evidence status

**Status:** Requirements derived from the current pitch. Acceptance thresholds and source jurisdictions require feasibility decisions. This file does not claim implementation.

1. A user can select supported jurisdictions and maintain private local interests without an account.
2. Validated connectors retrieve civic records and preserve source identity, retrieval status and official links.
3. Downloaded records remain searchable and inspectable offline; offline use does not imply fresh monitoring.
4. A civic classifier and semantic matcher run on the device for core matching. Model outputs show uncertainty and remain distinct from source statements.
5. The app exposes evidence-linked results and user-controlled JSON or Markdown Evidence Pack export.
6. Optional notifications follow successful retrieval and matching when permitted by Android and the user. Manual refresh and last successful refresh remain visible.
7. Supported data sources require access-term, format, rate-limit and repeatable-retrieval validation. A source requiring unplanned OCR is not counted as a working core integration.
8. Physical-device tests must cover model loading, inference, offline search, retrieval recovery and notification permissions before device readiness is claimed.
9. Development evidence distinguishes raw records, weak labels, reviewed labels, held-out evaluation, device measurements and future work.

Category A completion is due by the Software Architecture Month 2 milestone. The exact calendar and supported jurisdiction set must be fixed during planning. Engineering acceptance for independent modules and release candidates is specified in shared governance.
