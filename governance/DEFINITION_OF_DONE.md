# Definition of done

| State | Required evidence | How it is checked |
|---|---|---|
| Role accepted | Exact ticket change, declared behavior, relevant module/contract checks, documentation and review | Code-owner review of the pull request (`.github/CODEOWNERS`). The role build's `check` task, `./gradlew -Pcivicwindow.role=<ai-ml\|android\|data-platform> check`; the tasks a role runs beyond `check` are open. The CI jobs `android-build`, `android-unit-tests`, `android-static-analysis`, `python-checks`, `commit-messages` and `secret-scan`. Proposed; not yet run |
| Accepted development dependency | Immutable candidate, source revision, resolved dependencies, artifact digest, checks, reviewer and retained location | The Gradle lock files and dependency verification (`--dependency-verification strict`) fail the build when a dependency changes; `uv sync --locked` does the same for Python. The digest, the reviewer and the location are recorded by hand, so review is the check for them. Proposed; not yet run |
| Integrated | Selected source and real dependencies pass applicable full integration checks; the weekly all-members test of the [course mapping](../docs/operations/TICKET_BOARD_SETUP.md#the-weekly-all-members-test) (a fixed `dev` commit, each lead testing on their own time, a failure recorded as a blocked ticket) | The full build without `-Pcivicwindow.role`: `./gradlew assembleDebug testDebugUnitTest`, and the CI jobs above on the pull request into `dev`. The weekly test is run by hand and recorded in the candidate's issue. Proposed; not yet run |
| Release qualified | Fixed source/manifest pass application, device, security and packaging checks; outputs are traceable | The [release process](../docs/operations/RELEASE_PROCESS.md) against one fixed `dev` commit. Device checks run by hand on a physical Android device (`connectedDebugAndroidTest`, which is not part of CI). The security check is the `secret-scan` job. No workflow checks packaging yet, so review is the check. Not yet run |

Report the states separately. Empty directories, draft schemas, substitute-only tests and a successful build relying on undeclared warm-cache inputs do not satisfy them. Concrete thresholds and required checks must follow the approved feature and build profile. No state is claimed reached by this staging scaffold.

The CI jobs are described in the [workflows README](../.github/workflows/README.md), and the test layers and their commands in the [test strategy](../docs/testing/TEST_STRATEGY.md#running-the-tests).

Enforcement: the checks named in the table, once they have run; review is the check until then and for every item the table marks as recorded by hand.
