// Enforcement: the build fails if the Kotlin JVM plugin cannot be applied; the base plugin's rules
// (warnings as errors, locking, toolchain) apply as written there.
// Documentation: Kotlin Gradle plugin, "Configure a Gradle project" (the Kotlin JVM plugin).
//
// A Kotlin JVM library: the base rules plus the Kotlin JVM plugin. It names no version; the
// plugin's version comes from the root build through the catalog.

plugins {
    id("civicwindow.base")
    id("org.jetbrains.kotlin.jvm")
}
