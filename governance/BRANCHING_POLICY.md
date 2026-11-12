# Branching policy

**State:** Design rule; Git and remote branch protections are not active.

CivicWindow will have one source repository. Its permanent branches are `main` for approved production source, `dev` for integrated development, and `role/ai-ml`, `role/android`, `role/data-platform` for accepted work by the three leads. The original `dev/...` role names are superseded because they conflict with the `dev` branch name.

An independent ticket starts from a recorded integration baseline and stays on a short-lived ticket branch while unfinished. A ticket depending on another unpromoted ticket must declare that prerequisite. Accepted role work may reach the role branch, but a cumulative role branch is not automatically the selected integration candidate.

Shared changes reach affected roles through explicit reviewed baseline updates. Synchronization checks for local uncommitted work and conflicts, preserves unfinished work, and never force-resets a role branch automatically. Production hotfixes return to `dev` and affected role baselines so they remain in subsequent releases.

## Ticket branches

- **Names:** `feature/<n>-<short-name>` for new work and `fix/<n>-<short-name>` for a bug fix or a hotfix. The short name says what the ticket does, such as `feature/L101-meeting-summary-prompt`. Never name a ticket branch under a role branch, such as `role/ai-ml/...`: Git refuses it.
- **Ticket number `<n>`:** the issue number once a tracker exists. Until then it is a local number, `L` plus three digits: AI/ML `L100`-`L199`, Android/Product `L200`-`L299`, Data Integration/Platform `L300`-`L399`, hotfixes `L900`-`L999`. Each lead numbers their own tickets. Provisional.
- **Base:** a ticket starts from a recorded integration baseline: the short commit id of the `dev` commit it was cut from, written in the ticket and the pull request. A ticket built on an unpromoted prerequisite names that prerequisite there too. Do not cut a ticket from a role branch's tip: the tip may hold other accepted tickets. Provisional.
- **Short:** one ticket, one branch, one merge, within the week of the ticket. Delete the branch after its merge. Split a ticket that cannot merge within the week. Provisional.
- **Bug fixes** are branches too. A bug in lane work uses `fix/` from a recorded baseline. A production hotfix uses `fix/` from `main`.

## Merging and promotion

1. A ticket enters its role branch only when it is finished and reviewed. Only that role's team can update the role branch ([GitHub setup](../docs/operations/GITHUB_SETUP.md)).
2. After work reaches `dev`, each affected role branch gets the new `dev` through an update ticket and pull request, with applicability checked. Never force-reset a role branch. See [Baseline synchronization](../docs/operations/SYNC_PROCEDURE.md).
3. Path: ticket, role branch, `dev`, `main`. The integrator (see [ownership](../docs/operations/OWNERSHIP_AND_COVERAGE.md)) merges each role branch into `dev` and `dev` into `main`, every pull request with the other lead's approval; `main` receives releases and hotfixes. A hotfix goes to `main`, then into `dev` and the affected role branches by the same update route.
4. Pull request keywords: a ticket's pull request into a role branch says "Refs #n". The integration pull request into `dev` says "Closes #n", because a closing keyword acts only on a merge into the default branch. Provisional.
