# Changelog

All notable changes to CivicWindow are recorded here. The format follows [Keep a Changelog 1.1.0](https://keepachangelog.com/en/1.1.0/), and versions follow [Semantic Versioning 2.0.0](https://semver.org/) as set in the [release process](docs/operations/RELEASE_PROCESS.md).

- Newest first. Unreleased changes go under `## [Unreleased]`.
- A release has the heading `## [x.y.z] - YYYY-MM-DD`.
- Entries are grouped under `### Added`, `### Changed`, `### Deprecated`, `### Removed`, `### Fixed` and `### Security`; only groups with entries appear.
- Write for the people who use or build on the app, not as a list of commits.
- Add dated entries for approved material changes and actual releases. Do not present planning milestones as shipped features.

Enforcement: review is the check until the first release; a check that each release has its heading and a non-empty entry is added with the release workflow.

## [Unreleased]

### Added

- The CivicWindow source directory scaffold and initial engineering documentation.
- Architecture decision records, proposed, and the repository conventions: commit messages, versions and tags, the changelog format, a code of conduct, dependency update automation, ignore rules, and the rules for signing keys and secrets.
- The test setup, proposed: the test libraries and coverage reporting (ADR-0009), the naming rules and layers in the validation strategy, and two example tests. No test has run yet.
- The continuous integration workflows, proposed: Android build, unit-test and static-analysis checks, Python checks, a commit-message check and a secret scan. No workflow has run yet.
- The setup guides and governance standards, proposed: the [development environment guide](docs/operations/DEV_ENVIRONMENT.md) with role onboarding, the [ticket-board guide](docs/operations/TICKET_BOARD_SETUP.md) with the course mapping, and concrete coding standards, style guide, definition of done and documentation standard in [governance](governance/README.md). No command or check in them has run yet.

A minimal application screen and the first build files are drafted; no build has run yet, and the first CI run after publication checks them. Git branches, schemas, and releases remain pending.
