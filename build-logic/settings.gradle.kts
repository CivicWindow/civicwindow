// Enforcement: Gradle fails the build if the catalog file is missing or an alias is unknown;
// review is the check that these repositories equal the root settings file's repositories.
// Documentation: Gradle, "Sharing build logic between subprojects" (convention plugins in an
// included build) and "Version catalogs".

// open: the repositories below are written to match the root settings file; compare them
// with the root file when both are in place, and checked again at the first build.
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "build-logic"
