// Enforcement: the build fails on a missing or non-numeric version property, on an out-of-range
// version part (computeVersionCode), and on a missing catalog value.
// Documentation: Android Gradle Plugin 9.3 release notes and the Android DSL (versionName,
// versionCode, targetSdk); Gradle, "Build environment" (project properties). The version-code
// formula is described in docs/operations/RELEASE_PROCESS.md; no standard covers this formula, it
// is the project's choice.
//
// An Android application: the base rules plus the Android application plugin. The version is
// written once, in gradle.properties (civicwindow.version.major, .minor, .patch and .build), and
// read here.
// open: the DSL interface (ApplicationExtension) follows the documentation as understood at
// writing; checked again at the first build.

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.artifacts.VersionCatalogsExtension

plugins {
    id("civicwindow.base")
    id("com.android.application")
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun catalogInt(name: String): Int = libs.findVersion(name).get().requiredVersion.toInt()

fun versionPart(key: String): Int {
    val raw = providers.gradleProperty(key).orNull
        ?: throw GradleException(
            "The property $key is missing; the app version is written in gradle.properties."
        )
    return raw.toIntOrNull()
        ?: throw GradleException("The property $key must be a whole number, but it is $raw.")
}

extensions.configure<ApplicationExtension> {
    compileSdk = catalogInt("compileSdk")
    defaultConfig {
        minSdk = catalogInt("minSdk")
        targetSdk = catalogInt("targetSdk")

        val major = versionPart("civicwindow.version.major")
        val minor = versionPart("civicwindow.version.minor")
        val patch = versionPart("civicwindow.version.patch")
        val build = versionPart("civicwindow.version.build")

        versionName = "$major.$minor.$patch"
        versionCode = computeVersionCode(major, minor, patch, build)
    }
    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(catalogInt("jdk"))
        targetCompatibility = JavaVersion.toVersion(catalogInt("jdk"))
    }
}
