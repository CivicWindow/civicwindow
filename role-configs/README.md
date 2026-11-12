# Role environment manifests

**Status:** Proposed. The three role files below are written, but no build has run, so none of them is claimed to work yet. This is a shared directory, not three repositories.

A role file is a Java `.properties` file that Gradle reads in `settings.gradle.kts` with no extra tool. It holds one key, `projects`: a comma-separated list of Gradle project paths. A role build is started with `-Pcivicwindow.role=<role>` and includes only those projects. Without the property, the full integration build includes every project.

| role file | `projects` |
|---|---|
| [`android.properties`](android.properties) | `:app`, `:modules:android:discovery`, `:contracts` |
| [`ai-ml.properties`](ai-ml.properties) | `:contracts` |
| [`data-platform.properties`](data-platform.properties) | `:contracts` |

The role file selects projects only. The rest of what a role needs (owned source, shared foundation, tools and exact check commands) is described in [build profiles](../docs/operations/BUILD_PROFILES.md) and [role onboarding](../docs/operations/ROLE_ONBOARDING.md). Verify each role from a fresh checkout with an empty cache; record sparse checkout paths separately.

## Failure behavior

The build stops with a message, and never goes on with fewer projects, when:

- a listed project has no folder (the message names the project, its folder and the role file, or "the project list in settings.gradle.kts" in the full build);
- the role named by the property has no role file (the message names the role and the expected file);
- the `projects` list is empty or missing (the message says that no project is selected and where the list was read).

Standards: the [Java properties file format](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Properties.html#load(java.io.Reader)) and Gradle documentation on the [settings file](https://docs.gradle.org/current/userguide/organizing_gradle_projects.html#sec:settings_file) and on [project properties](https://docs.gradle.org/current/userguide/build_environment.html#sec:project_properties). The key `projects`, the list of Gradle paths and the property name are not defined by any standard; no standard covers this; it is the project's choice.

Enforcement: `settings.gradle.kts` fails the build on the three cases above; review is the check that each role file lists only projects that exist, until the first CI run.
