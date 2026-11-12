// Enforcement: the build fails if the Compose compiler plugin or the Compose Bill of Materials
// cannot be resolved from the catalog; review is the check that a module applies this plugin only
// with an Android plugin.
// Documentation: Android developers, "Set up Compose dependencies and compiler" and the Compose
// compiler Gradle plugin documentation (the plugin's version matches the Kotlin version).
//
// Compose for a module: the Compose compiler plugin and the Compose Bill of Materials. It names no
// version; the Bill of Materials version is the catalog's. Apply it after an Android plugin.

import org.gradle.api.artifacts.VersionCatalogsExtension

plugins {
    id("org.jetbrains.kotlin.plugin.compose")
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    add("implementation", platform(libs.findLibrary("androidx-compose-bom").get()))
}
