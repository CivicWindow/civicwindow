# Baseline synchronization

**Status:** Planned procedure; no automation is active.

An accepted shared-foundation change enters a reviewed integration baseline. Identify affected roles, compare their current baselines, check dirty state and compatibility, then propose explicit update work. If conflicts or failing checks appear, stop automatic advancement and resolve them with review. Do not force-reset a role branch to match `dev`.

Carry production hotfixes back to `dev` and affected role baselines. Record changed contract versions, migrations and supported transition period. The automation script may prepare/check an update but may not silently grant approval or discard work.

An update is an ordinary ticket: `feature/<n>-sync-<what>` with the lead's own local number (provisional). Its pull request brings the new `dev` into the affected role branch and notes for each change whether it applies and what was checked. A hotfix is carried the same way, linked to its ticket. A conflict stops the update until a reviewer resolves it. Provisional.
