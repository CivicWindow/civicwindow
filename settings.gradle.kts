// settings.gradle.kts: which projects exist, where plugins and dependencies come from,
// and how a role build selects its projects.
//
// Standard: Gradle documentation on the settings file, on composite builds and on
// sharing build logic with convention plugins in an included build
// (https://docs.gradle.org/current/userguide/sharing_build_logic_between_subprojects.html).
// The project list, the role-selection property and the role file format are not defined by
// any standard; no standard covers this; it is the project's choice (ADR-0007, rule 4).
//
// Enforcement: the Gradle build fails when a project that is selected has no folder (this
// file), when a role names no role file (this file), and when a project declares its own
// repositories (FAIL_ON_PROJECT_REPOS below).

pluginManagement {
    // The convention plugins live in the included build "build-logic" (ADR-0007, rule 2).
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    // Repositories are declared here only; a project that declares its own fails the build.
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "civicwindow"

// Every project of the integration build, as a Gradle path. The folder of a path is the path
// without its leading colon, with each remaining colon read as a slash.
val allProjects = listOf(
    ":app",
    ":modules:android:discovery",
    ":contracts",
)

// Role selection. With -Pcivicwindow.role=<ai-ml|android|data-platform> only the projects
// listed under the key "projects" in role-configs/<role>.properties are included.
// Without the property every project above is included.
val role: String? = providers.gradleProperty("civicwindow.role").orNull

val selected: List<String> =
    if (role == null) {
        allProjects
    } else {
        val roleFile = rootDir.resolve("role-configs/$role.properties")
        if (!roleFile.isFile) {
            throw GradleException(
                "The role '$role' has no role file: expected role-configs/$role.properties."
            )
        }
        val properties = java.util.Properties()
        roleFile.inputStream().use { properties.load(it) }
        properties.getProperty("projects", "")
            .split(",")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
    }

val selectionSource: String =
    if (role == null) {
        "the project list in settings.gradle.kts"
    } else {
        "role-configs/$role.properties"
    }

if (selected.isEmpty()) {
    throw GradleException("No project is selected: $selectionSource lists none.")
}

selected.forEach { path ->
    val folder = path.removePrefix(":").replace(':', '/')
    if (!rootDir.resolve(folder).isDirectory) {
        throw GradleException(
            "The project '$path' is listed in $selectionSource, but its folder '$folder' is absent."
        )
    }
    include(path)
}
