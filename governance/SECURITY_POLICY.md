# Security policy

Separate authority to read and change source, review a candidate, publish artifacts, sign builds and release production. Assign qualified reviewers, integration/release owners and backup coverage among the three leads. A missing reviewer is recorded as a readiness gap.

Keep tokens, signing keys and private user profiles outside source history, fixtures and generated reports. Do not expose production secrets to unreviewed ticket code. Use permitted samples with source terms and provenance. A checksum gives integrity only when matched to trusted candidate metadata. Distinguish public-source access from authority to redistribute data or assets.

**State:** Policy only. Git hosting, protection settings, secret storage and signing service have not been configured.
