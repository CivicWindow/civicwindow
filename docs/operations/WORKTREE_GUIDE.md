# Clones, worktrees and source views

**Current mapping:** This repository's root is the canonical source root. Each lead works in their own clone of the published repository.

One future Git repository contains `main`, `dev` and the three role branches. Each developer uses a separate local clone or a linked worktree outside this canonical source tree. Sparse checkout selects a focused working view; it does not make a separate repository or enforce authorization. Every role view retains the complete shared foundation and all declared build prerequisites.

Never manually copy and maintain five divergent source trees to imitate branches. Before registering a linked worktree, inspect existing paths and branch occupancy. Before baseline updates, preserve dirty work and stop on conflicts. The role build files (project lists) are in `role-configs/`; the sparse checkout paths of each role are not yet recorded, and no build has run yet; the first CI run after publication checks the role build files. The Git commands are these:

- Clone only your role branch, then add `dev` so you can record baselines: `git clone --branch role/ai-ml --single-branch <repository> <folder>`, then `git remote set-branches --add origin dev`, then `git fetch origin`.
- Cut a ticket branch from the current `dev` and note its short commit id (the local `L` number and the commit-id baseline are provisional): `git switch -c feature/L101-<short-name> --no-track origin/dev`, then `git rev-parse --short origin/dev`. The `--no-track` keeps `origin/dev` from becoming the branch's upstream. With no upstream, `git branch -d` checks the branch you have checked out, so run it from the role branch you merged the ticket into.
- Publish a ticket: push the ticket branch (`git push -u origin feature/L101-<short-name>`) and open a pull request into your role branch. Only your role's team can update that branch, and the integrator merges the role branch into `dev` ([branching policy](../../governance/BRANCHING_POLICY.md)). A single-branch clone is a local view of a public repository: anyone can read every branch, and what you can push is set by your team's rules ([GitHub setup](GITHUB_SETUP.md)).
- One branch can be checked out in only one worktree at a time (`git worktree add <path> <branch>` stops with "already used by worktree"). Use one worktree per branch.
- Set your commit name and email in each clone before the first commit.
