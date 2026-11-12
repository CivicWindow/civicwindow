# Ticket board setup

**Status:** Planned; no board exists yet. This file holds the course mapping only. The settings of the board itself are written when the board is set up on the Git host.

## Course mapping

The course names three things: a Gitflow branch plan, a Jira board and a weekly test that every member takes part in. CivicWindow has its own workflow, set by the [branching policy](../../governance/BRANCHING_POLICY.md), the [ticket workflow](TICKET_WORKFLOW.md) and the [release process](RELEASE_PROCESS.md). This section shows what each course item corresponds to in that workflow. Each part rests on the files it links.

Three GitHub words are used below. An **issue** is GitHub's ticket. A **pull request** asks for one branch to be merged into another; others review it before it merges. A **label** is a short tag on an issue, such as `blocked`. The **default branch** is the branch GitHub opens first, and it is the only one where a closing keyword such as "Closes #12" acts.

### Gitflow

Gitflow is a branch plan with a production branch, a development branch, feature branches, release branches and hotfix branches. CivicWindow's branches match it as follows. The rules that the branching policy marks as provisional stay provisional here.

| Gitflow element | In CivicWindow | Rests on |
|---|---|---|
| Production branch | `main`, the approved production source | [branching policy](../../governance/BRANCHING_POLICY.md) |
| Development branch | `dev`, the integration branch. It is also the repository's default branch | [branching policy](../../governance/BRANCHING_POLICY.md); [GitHub setup](GITHUB_SETUP.md) |
| Feature branch | A ticket branch, `feature/<n>-<short-name>`: one ticket, one branch, one merge, within the week of the ticket. The branch is deleted after its merge | [branching policy](../../governance/BRANCHING_POLICY.md); [ticket workflow](TICKET_WORKFLOW.md) |
| No Gitflow element | The role branches `role/ai-ml`, `role/android` and `role/data-platform`. Each is a lead's reviewed baseline. A ticket enters one only when it is finished and reviewed | [branching policy](../../governance/BRANCHING_POLICY.md) |
| Merge of features into develop | The integration pull request into `dev`, which carries "Closes #n". A ticket's own pull request into a role branch carries "Refs #n" | [branching policy](../../governance/BRANCHING_POLICY.md); [GitHub setup](GITHUB_SETUP.md) |
| Release branch | A fixed release candidate: one exact `dev` commit and a manifest, checked as they stand. `main` is then confirmed to hold it | [release process](RELEASE_PROCESS.md) |
| Hotfix branch | A `fix/` branch cut from `main`. The fix goes to `main`, then returns to `dev` and to the affected role branches | [branching policy](../../governance/BRANCHING_POLICY.md); [release process](RELEASE_PROCESS.md) |

Completed work reaches `dev` through its lead's integration pull request. Until then it waits on the role branch, so that each lead's baseline stays reviewed and no lead's unreviewed work is shared.

### Jira

The course uses a Jira board. CivicWindow keeps its tickets as GitHub issues, and GitHub stays the record. If the instructors ask for a Jira board, it is a copy made by hand:

1. Use the fields of the project's board (status, role, module, baseline and blocked reason) as the columns of the Jira board.
2. Export the board's issues from GitHub.
3. Create or update the matching Jira items by hand from the export. The Jira copy lives outside this repository.
4. Update the Jira copy when the instructors next ask. Never edit it as the source.

This is an equivalent mapping, not set up. GitHub Projects, GitHub's board for issues, is where the board itself will be built; its fields and automation are written when that is done.

### The weekly all-members test

The course asks every member to test the week's completed work. In CivicWindow the test runs like this:

1. The week's candidate is a fixed `dev` commit. No later change alters it.
2. Each lead tests that commit from their own clone, on their own time within the week. The commands are in the [development environment guide](DEV_ENVIRONMENT.md#first-build-test-and-check-commands) and the [test strategy](../testing/TEST_STRATEGY.md#running-the-tests). Device behavior is tested on a physical Android device, because an emulator-only result does not establish device readiness ([Software Design Document](../design/SOFTWARE_DESIGN_DOCUMENT.md), section 2.1).
3. Each lead records the result as a comment on the issue that tracks the candidate.
4. A failure becomes a ticket with the `blocked` label, the reason, and a link to the failing ticket. A blocked ticket holds up no unrelated finished ticket ([ticket workflow](TICKET_WORKFLOW.md)).
5. No part of the test needs another lead to be present.

Without a physical device, the weekly test is the unit and build checks only, and the record says so.

Enforcement: checked by the first test pull request and the first weekly test; until then review is the check.
