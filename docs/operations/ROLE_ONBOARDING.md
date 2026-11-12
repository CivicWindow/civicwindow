# Role onboarding

**Status:** Procedure design. The Git repository is created where the owner places this folder, after the checks in [Placing the repository](GITHUB_SETUP.md#placing-the-repository-and-the-first-branch) (see also [GitHub setup](GITHUB_SETUP.md)); the role build files (project lists) are in [`role-configs/`](../../role-configs/README.md), no build has run yet and the first CI run after publication checks them, and no runnable setup script exists yet.

Select AI/ML, Android/Product or Data Integration/Platform. Obtain access to the single CivicWindow source repository by joining your role's organization team (the integrator invites you, [GitHub setup](GITHUB_SETUP.md), step 14) and to the declared artifact source. Check out the assigned `role/...` branch in a separate clone or linked worktree. The commands are in [Clones, worktrees and source views](WORKTREE_GUIDE.md). Cut each ticket from a recorded `dev` baseline, not from the role branch's tip ([branching policy](../../governance/BRANCHING_POLICY.md)). Apply the role manifest to include the complete shared foundation, owned source, root build files, required scripts/tests/fixtures and approved dependency inputs. Run the documented environment and role checks from a clean cache.

Do not call onboarding complete until the role build and contract checks succeed with only declared inputs. Record OS/tool versions and any unavailable artifact.

## The steps

The commands are in the [development environment guide](DEV_ENVIRONMENT.md); this page says in what order a new member uses them. Every command is **not yet run**: the first run is the first build after the repository is published. A value marked "open: confirmed at the first build" is settled by the first person who sets up a machine, and recorded in the guide. In every step you work in your own area and need nobody else's action to build, test or lint it.

The three roles are written `android`, `ai-ml` and `data-platform`. The role names are the values of `-Pcivicwindow.role` and the file names in [`role-configs/`](../../role-configs/README.md).

1. **Set up the machine.** Follow [What to install](DEV_ENVIRONMENT.md#what-to-install): install only what your role's table lists. If `gradlew` is not in the repository yet, the guide also tells you how to make it once.
2. **Clone or add a worktree.** Check out your role branch (`role/android`, `role/ai-ml` or `role/data-platform`) in a separate clone or linked worktree. The commands are in [Clones, worktrees and source views](WORKTREE_GUIDE.md); they are not copied here.
3. **Choose the role build.** Select the projects of your role with the role property. The build reads `role-configs/<role>.properties` and includes only the projects listed there; a listed project whose folder is absent stops the build with a message that names the project and the role file. With no property, the full integration build includes every project (not yet run):

   ```powershell
   ./gradlew -Pcivicwindow.role=<ai-ml|android|data-platform> <task>
   ```

4. **Run the role checks from a clean cache.** Run the role's `check` task with the role build (not yet run). Which tasks each role runs first, beyond `check`, is open: confirmed at the first build.

   ```powershell
   ./gradlew -Pcivicwindow.role=<ai-ml|android|data-platform> check
   ```

5. **Run what your role runs first.** The commands are in [First build, test and check commands](DEV_ENVIRONMENT.md#first-build-test-and-check-commands).

   | Role | Projects of the role build | What the role runs first |
   |---|---|---|
   | `android` | `:app`, `:modules:android:discovery`, `:contracts` | its own module and the app: build, unit tests, ktlint and Android Lint; then the device tests on a physical device ([Physical device setup](DEV_ENVIRONMENT.md#physical-device-setup)) |
   | `ai-ml` | `:contracts` | `:contracts`, then the Python job of `ml/`, which this role owns (`python-checks`: the lock check, Ruff, and pytest, including the tests in `ml/tests`) |
   | `data-platform` | `:contracts` | `:contracts`. Its own folders, `modules/data/` and `connectors/`, hold no build files yet, so their checks are added with the first module (open: confirmed at the first build) |

   `scripts/` is shared by every role (`.github/CODEOWNERS`); its checks run inside the same Python job.

6. **Record the versions.** Fill in the table in [Check your versions](DEV_ENVIRONMENT.md#check-your-versions), and write down the operating system version and any artifact or tool that is unavailable to you.
7. **Check the done test.** Onboarding is complete when the role build and contract checks succeed with only declared inputs, as stated above.

Enforcement: The commands run in the clean-clone check before a release; the first lead's setup is the test.
