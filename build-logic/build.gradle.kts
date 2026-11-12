// Enforcement: Gradle fails the build if a plugin script does not compile against the plugins
// below; every version comes from the catalog, and the build fails on an alias that is not in it.
// Documentation: Gradle, "Precompiled script plugins" and the Kotlin DSL plugin; the Android Gradle
// Plugin and Kotlin Gradle plugin are compile-only here because the root build puts them on the
// build's classpath.

plugins {
    `kotlin-dsl`
}

// open: the Kotlin toolchain call in this plugin build follows the documentation as understood at
// writing; checked again at the first build.
kotlin {
    jvmToolchain(libs.versions.jdk.get().toInt())
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.compose.compiler.gradle.plugin)
    compileOnly(libs.ktlint.gradle.plugin)
    testImplementation(libs.junit)
    testImplementation(libs.kotlin.test)
}
