// Enforcement: the build fails if the Android library plugin cannot be applied or a catalog value
// is missing; the SDK levels and Java level are read from the catalog, so a module cannot set
// its own.
// Documentation: Android Gradle Plugin, "Migrate to built-in Kotlin" and the Android Gradle Plugin
// 9.3 release notes (the Android DSL interfaces in com.android.build.api.dsl).
//
// An Android library: the base rules plus the Android library plugin. AGP's built-in Kotlin
// supplies Kotlin support, so the separate Kotlin Android plugin is not applied.
// open: the DSL interface (LibraryExtension) and the built-in Kotlin form follow the documentation
// as understood at writing; checked again at the first build.

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.artifacts.VersionCatalogsExtension

plugins {
    id("civicwindow.base")
    id("com.android.library")
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun catalogInt(name: String): Int = libs.findVersion(name).get().requiredVersion.toInt()

extensions.configure<LibraryExtension> {
    compileSdk = catalogInt("compileSdk")
    defaultConfig {
        minSdk = catalogInt("minSdk")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(catalogInt("jdk"))
        targetCompatibility = JavaVersion.toVersion(catalogInt("jdk"))
    }
}
