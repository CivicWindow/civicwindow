# Contributing to CivicWindow

**Status:** Working agreement. Git and automated enforcement are not yet active.

1. Define a ticket with owner, baseline, acceptance criteria, affected module and prerequisites.
2. Keep unfinished work on independent ticket branches once Git exists. Record dependencies on other unfinished tickets explicitly.
3. Preserve the complete shared foundation in every role environment. Review changes to shared contracts with affected roles.
4. Document real interfaces, dependencies, failures, fixtures, checks and limitations. A directory does not establish module readiness.
5. Distinguish isolated substitute-based tests from checks of real implementations and report unrun checks.
6. Build candidates from the exact selected source and prerequisites. Propose only selected work into `dev`; validate the resulting composition.
7. Tie a release to a fixed source commit, dependency manifest, real implementation checks and traceable outputs.

## Conventions

Every contributor follows the same conventions, whatever the module, branch or role.

- Decisions: [architecture decision records](docs/adr/README.md), in the MADR format; the records ADR-0001 to ADR-0009 are listed there.
- Branches and pull requests: [branching policy](governance/BRANCHING_POLICY.md).
- Commit messages: the next section, following Conventional Commits 1.0.0.
- Versions and tags: [release process](docs/operations/RELEASE_PROCESS.md), following Semantic Versioning 2.0.0.
- Changelog: [CHANGELOG.md](CHANGELOG.md), following Keep a Changelog 1.1.0.
- Code of conduct: [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md), the Contributor Covenant 2.1.
- Secrets and signing keys: [security decisions](docs/security/README.md).
- Style: the Kotlin and Python decisions in the architecture decision records, and [.editorconfig](.editorconfig).
- Tests: the [test strategy](docs/testing/TEST_STRATEGY.md).
- Setting up a machine: the [development environment guide](docs/operations/DEV_ENVIRONMENT.md).
- The ticket board and the course mapping: the [ticket-board guide](docs/operations/TICKET_BOARD_SETUP.md).

Enforcement: checked in pull-request review; the `commit-messages` and `commit-format` jobs check commit messages in continuous integration, and they first run on the first pull request after the repository is published.

**Status:** Proposed. The conventions are written down now. The tools that check them are in the lint and CI configuration, not in the build files: the CI workflows exist and have not run yet, and until their first run, review is the check.

## Commit messages

Commit messages follow [Conventional Commits 1.0.0](https://www.conventionalcommits.org/en/v1.0.0/):

    <type>(<scope>): <summary>

    <body>

    <footer>

- **type** is one of `feat`, `fix`, `docs`, `refactor`, `test`, `build`, `ci`, `chore`. Conventional Commits defines `feat` and `fix`; the others are the types its documentation suggests. No standard fixes this list; it is the project's choice.
- **scope** is optional: the module folder or area changed, such as `discovery`, `legistar`, `ml` or `governance`.
- **summary** is imperative, lower case, without a full stop; the whole first line is at most 72 characters. No standard covers this summary rule; it is the project's choice.
- **body** says what changed and why, wrapped at 72 characters (a common Git practice; no standard covers this), after one blank line.
- **A breaking change** puts `!` before the colon and adds a `BREAKING CHANGE:` line in the footer. A breaking change to a shared contract or schema also needs an architecture decision record ([schema and contract versioning](governance/SCHEMA_VERSIONING.md)).
- **Issue link:** a commit may end with `Refs #<issue number>`, the number of the ticket's GitHub issue (no standard covers this); nothing else links a commit to an issue. The closing keyword (`Closes #<issue number>`) is written only in the integration pull request into `dev`, as the [branching policy](governance/BRANCHING_POLICY.md) says. This adds no rule to the branching policy.

Example:

    feat(discovery): show the last refresh date on the results list

    Show the date of the last successful refresh above the list so that
    stale data is visible.

    Refs #123

A commit template is in [.gitmessage](.gitmessage). To use it in a clone, run `git config commit.template .gitmessage`.

Enforcement: the `commit-messages` job (the closing-keyword rule, required) and the `commit-format` job (the format rules, advisory) check commit messages in continuous integration; an optional local hook is not provided yet. The jobs first run on the first pull request after the repository is published; until then, review is the check.

The shared policies live in [governance](governance/README.md). No sentence here authorizes an integration or production promotion.
