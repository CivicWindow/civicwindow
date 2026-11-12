# Ticket workflow

**Status:** Planned issue/PR workflow; tracker and Git are not configured.

Each ticket identifies owner, role, feature, acceptance criteria, integration baseline, affected contracts, prerequisites and verification. Work begins on an isolated ticket branch. The role accepts exact reviewed changes after applicable module/contract checks. A blocked ticket records its reason; it does not hold an unrelated completed ticket.

Per ticket: take a number and a short name ([branching policy](../../governance/BRANCHING_POLICY.md)); cut the branch from a recorded `dev` baseline and write that commit id in the ticket's Baseline field (provisional); open a pull request into the role branch with the pull request template; merge it only when it is finished and reviewed; delete the branch. Until a tracker exists, the ticket's number is the local `L` number (provisional) and the pull request description links the real issue number once one exists.

A candidate package records its actual included changes and dependencies. A selected integration PR carries only the chosen tickets and complete prerequisites against current `dev`, with fresh checks of the result. Preserve links from issue to change, review, candidate, integration revision and release. Do not maintain a competing handwritten backlog in source docs.
