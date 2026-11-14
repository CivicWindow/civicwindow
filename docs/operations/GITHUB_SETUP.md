# GitHub setup

**Status:** To do when the repository is published, after the pre-publication audit. The decisions below are made; nothing is configured yet.

## Decisions

- **Owner and visibility:** a free GitHub organization, `<org>`, owns one public repository, `<repo>`, so instructors can read it. Everything here uses features that GitHub offers a public repository on the Free plan.
- **Default branch:** `dev` while the project is developed. `main` receives releases and hotfixes.
- **Integrator:** the Data Integration/Platform lead (see [ownership](OWNERSHIP_AND_COVERAGE.md)) creates the organization, holds its owner role, and merges each role branch into `dev` and `dev` into `main`. Every one of those pull requests carries the other lead's approval.
- **Push rights:** one organization team per role branch. A person who leaves or joins a role is a change of team membership, not a change of rule.

## Plan check

Confirm on the day that each setting above is offered on the plan in use; where one is not, review is the check.

## The runbook

The steps are in order. Steps 1 to 3 need no repository, steps 4 to 6 are local, steps 7 to 9 create the repository and push, and steps 10 to 18 follow the push. The rules come after the push because the rules in step 12 would refuse it.

### Organization, teams and code owners

1. **Create the organization** `<org>` on the Free plan. The integrator holds the owner role.
2. **Create three teams**, one per role branch: `<team-ai-ml>`, `<team-android>` and `<team-data-platform>`. Make each team visible: a code owner team must be visible, and a secret team cannot go on a bypass list. Teams hold organization members only. The team lists are filled in step 14.
3. **Set the code owners.** In `.github/CODEOWNERS` replace `<org>` and each `<team-...>` with the real slugs, so that the first commit already holds them (see [Placeholders](#placeholders-to-replace-before-publishing)).

### Placing the repository and the first branch

This is a repository step, not a GitHub setting, and it is the owner's.

4. Choose the parent folder. Before `git init`, verify the actual Git boundary of that parent: whether it or any folder above it already holds a `.git` entry that belongs to another project. Do not change any unrelated project. The earlier check was made before this skeleton existed, so repeat it.
5. Move the entire CivicWindow folder to the chosen parent. Compare relative paths and file digests before and after the move; they must match. If Git or linked worktrees already exist at that point, verify their references after the move. Portable role configuration must not depend on any machine's absolute path.
6. Create the repository and the first commit in the CivicWindow folder, written `<folder>` below. Run the commands one at a time, in the order given. Each is one line with its own `git -C "<folder>"`, so a paste cannot run in another folder or out of order. Git cannot create a branch before the first commit exists, so the branches come last.

   Create the repository (a machine's default branch can be `master`, so `-b main` is required):

   ```text
   git -C "<folder>" init -b main
   ```

   Set your commit name and email: the history is public, so use a name and an address you are content to publish (GitHub offers a no-reply address for this):

   ```text
   git -C "<folder>" config user.name "<name>"
   ```

   ```text
   git -C "<folder>" config user.email "<email>"
   ```

   Commit the placed files as the first commit. Its message follows the repository's own commit format, `type: summary` (see [CONTRIBUTING](../../CONTRIBUTING.md#commit-messages)), not a free-form line. This commit is the documented baseline:

   ```text
   git -C "<folder>" add .
   ```

   ```text
   git -C "<folder>" commit -m "chore: add the initial repository skeleton"
   ```

   Create `dev`, `role/ai-ml`, `role/android` and `role/data-platform` from that commit:

   ```text
   git -C "<folder>" branch dev
   ```

   ```text
   git -C "<folder>" branch role/ai-ml
   ```

   ```text
   git -C "<folder>" branch role/android
   ```

   ```text
   git -C "<folder>" branch role/data-platform
   ```

### Publishing

7. **Check what is pushed.** The pre-publication audit (see [Before publishing](#before-publishing)) must have passed on exactly the commits that step 9 pushes. All five branches point at the baseline commit, so one check covers them.
8. **Create the repository** `<org>/<repo>`: public and empty (no README, licence or `.gitignore` from GitHub), so that nothing differs from the local baseline.
9. **Push the five permanent branches:** `main`, `dev`, `role/ai-ml`, `role/android`, `role/data-platform`. Run the two commands one at a time, in this order, with `<folder>` as in step 6:

   ```text
   git -C "<folder>" remote add origin https://github.com/<org>/<repo>.git
   ```

   ```text
   git -C "<folder>" push -u origin main dev role/ai-ml role/android role/data-platform
   ```

10. **Make `dev` the default branch** (repository Settings, General, Default branch, Switch to another branch; this needs admin access). Issue forms and "Closes #n" work only on the default branch, and the team's pull requests land in `dev`. Under the [branching policy](../../governance/BRANCHING_POLICY.md) a ticket's pull request lands in its role branch and carries "Refs #n"; the integration pull request into `dev` carries "Closes #n", because a closing keyword acts only on a merge into the default branch.
11. **Give the teams access.** In the repository's Settings, Collaborators and teams, add each of the three teams with the Write role, and no more. A team named in `.github/CODEOWNERS` needs write access to the repository.
12. **Create the rulesets** (Settings, Rules, Rulesets, New branch ruleset). Set each to Active. Use rulesets, not classic branch protection: classic "Restrict who can push to matching branches" does not hold repository administrators, who can always push, while a ruleset's push limit holds every actor that is not on its bypass list. A bypass list (Add bypass) exempts the actors on it from every rule in that ruleset, so the rules that allow no bypass and the push limits that need one are separate rulesets. A team on a bypass list must be visible, and "For pull requests only" makes the team change the branch by pull request alone:

    | Ruleset | Targets | Rules | Bypass list |
    |---|---|---|---|
    | permanent branches | `main`, `dev`, `role/*` | Restrict deletions; Block force pushes | none |
    | pull requests | `main`, `dev` | Require a pull request before merging, with 1 required approval and Require review from Code Owners | none |
    | integrator | `main`, `dev` | Restrict updates | `<team-data-platform>`, For pull requests only |
    | role AI/ML | `role/ai-ml` | Restrict updates | `<team-ai-ml>`, For pull requests only |
    | role Android | `role/android` | Restrict updates | `<team-android>`, For pull requests only |
    | role Data/Platform | `role/data-platform` | Restrict updates | `<team-data-platform>`, For pull requests only |

    The result: nobody pushes directly to `main`, `dev` or a role branch, and nobody force-pushes or deletes one. A pull request into `dev` or `main` needs one approval, and a code owner's approval; only the integrator's team can merge it. A pull request into a role branch is merged by that role's team. The integrator's account (it holds the organization's owner role and belongs to `<team-data-platform>`) is bound for direct pushes: its team's bypass is the For pull requests only kind, and the permanent branches and pull requests rulesets have no bypass at all. Step 18 checks this with that account. The Android/Product lead is the integrator's backup (see ownership), and there is no standing bypass for that team: when the backup must act, the integrator adds `<team-android>` to the integrator ruleset's bypass list for the time it is needed, then removes it. The organization owner can still edit a ruleset, so these rules guard against mistakes and do not replace review.
13. **Confirm secret scanning and push protection are on** (repository Settings, Advanced Security, Secret Protection). Both are free for a public repository and on by default; if either is off, turn it on there.
14. **Invite the leads.** A team holds organization members only, so invite each lead as a member of the organization (not as an outside collaborator) with `<username>`, then add them to the team of every role they lead. One person can lead two roles and so belong to two teams. Open `.github/CODEOWNERS` in the repository: it shows no errors once the teams exist and hold Write access.
15. **Create the labels and milestones** listed under [Labels to create](#labels-to-create-parked) and [Milestones](#milestones-placeholders-parked).
16. **Turn on GitHub Actions and set what it may run.** A new organization can start with Actions off, so the first push may have come before it was on and no workflow has run yet. In the organization's Settings, Actions, General (the repository's Settings, Actions, General shows the same options), set:

    - Actions permissions: allow actions created by GitHub, and allow only `astral-sh/setup-uv@*` and `gradle/actions/setup-gradle@*` besides. Nothing else.
    - Require actions to be pinned to a full-length commit SHA: on. Every `uses:` line in `.github/workflows/` is pinned that way (see the [CI workflows](../../.github/workflows/README.md)).
    - Workflow permissions: Read repository contents and packages permissions, so the token is read-only.
    - Allow GitHub Actions to create and approve pull requests: off.

17. **Run each workflow once by hand on `dev`, then select the required checks.** Because the first push may have come before Actions was on, a job may not have run yet, and GitHub offers a check name only after its job has run. In the repository's Actions tab choose each of the four workflows in turn, choose Run workflow, pick the branch `dev`, and wait for every job to finish. Then edit the pull requests ruleset, add Require status checks to pass before merging, and choose the check names in the [CI workflows](../../.github/workflows/README.md) table. A check that fails until the generated files are committed stays unselected until then.
18. **Verify the rules once.** As the integrator's account, try a direct push to `dev`, a force push to `dev`, and the deletion of `role/ai-ml`: all three are refused. (Keep the local clone; if a test is not refused, push the branch back.) As the Android/Product lead, try a push to `role/ai-ml`: it is refused. As the integrator's account, open a pull request from a role branch into `dev` and confirm that it cannot merge without the other lead's approval, and that the Android/Product lead's account cannot merge it even when approved.

## Ticket branches

Named, based and sized by the [branching policy](../../governance/BRANCHING_POLICY.md). GitHub merges them by pull request.

## Placeholders to replace before publishing

- **`.github/CODEOWNERS`:** replace `<org>` and each `<team-...>` with the organization's and the teams' slugs (step 3). Each area lists its own role's team first and the backup lead's team second, so a lead's own pull request still gets a code-owner review from the other team. Until the teams exist and hold Write access (steps 11 and 14), GitHub shows the owners as errors in the file's view.

## Labels to create (parked)

- `role: ai-ml`, `role: android`, `role: data-platform`, `role: shared`
- `type: feature`, `type: bug`, `type: research`, `type: documentation`, `type: integration`
- `blocked`

The issue forms add the `type:` labels, which must exist for that to work.

## Milestones (placeholders) (parked)

`Month 1` (ends Nov 19, 2026), `Month 2`, `Month 3`, `Month 4`, `SGX` (final delivery). Dates are set as the course confirms them.

## Before publishing

- License: MIT, in `LICENSE` (chosen).
- Confirm nothing secret is in the history.
- Record the terms of any civic data committed as samples or fixtures.
- Check that the README is the repository's front page.
- Record the private route for security and conduct reports in `SECURITY.md` and `CODE_OF_CONDUCT.md`; the project owner chooses it when the repository is published.

## Moved here

From `.github/README.md`:

> # GitHub collaboration configuration
>
> **Status:** Planned; the CI workflows exist, and none has run yet.
>
> Issue templates, ownership and pull request guidance are planned for the future canonical repository. The CI workflows now exist in `.github/workflows/`; none has run yet. The Git host's settings (branch protection, required checks and reviewers) are chosen when the repository is published, before these files become operational.

From `.github/ISSUE_TEMPLATE/README.md`:

> # Issue templates
>
> **Status:** Planned; no implementation or executable check exists here yet.
>
> Future templates should require owner, module, acceptance examples, prerequisites, contract changes, test evidence and scope. No ticket host is active in this source tree.

They were moved so the root `README.md` is the repository's front page, and the issue-template folder holds only templates.
