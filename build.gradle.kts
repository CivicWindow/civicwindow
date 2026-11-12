// build.gradle.kts (root): declares the plugin aliases the modules use, without applying them.
// Each module applies a plugin through the convention plugins in build-logic.
//
// Standard: Gradle documentation on version catalogs
// (https://docs.gradle.org/current/userguide/version_catalogs.html); the aliases and their
// versions are written once, in gradle/libs.versions.toml (ADR-0007, rule 1).
//
// There is no alias for org.jetbrains.kotlin.android: Android modules use the Kotlin support
// built into the Android Gradle Plugin
// (https://developer.android.com/build/migrate-to-built-in-kotlin).
//
// Enforcement: the Gradle build fails on a plugin alias that is not in the catalog.

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ktlint) apply false
}
