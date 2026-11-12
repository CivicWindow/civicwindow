// Enforcement: `check` runs the unit tests of every project that applies this plugin. In CI, the
// `android-unit-tests` job runs testDebugUnitTest, the build-logic tests (:build-logic:test, open)
// and the unit-test coverage report, and the `android-build` job compiles the device tests
// (assembleDebugAndroidTest); the device tests run by hand on a physical device. Each job first
// runs on the first pull request after the repository is published; review is the check until then.
// Documentation: JUnit 4; Kotlin, "Test code using JUnit in Kotlin/JVM" and the kotlin-test
// library; MockK; Turbine; kotlinx-coroutines-test; AndroidX Test, "AndroidJUnitRunner"; Jetpack
// Compose, "Test your Compose layout"; Android Gradle Plugin, "Code coverage"
// (enableUnitTestCoverage, enableAndroidTestCoverage).
//
// The test libraries, the instrumentation runner and the coverage switches for the Android
// application, Android library and Kotlin JVM projects. It names no version: every version comes
// from the catalog, and the Compose test libraries take theirs from the Compose Bill of Materials.
// The base plugin applies this plugin, so this plugin does not apply the base plugin.
//
// JUnit 4 is Gradle's default for Kotlin/JVM, so there is no useJUnitPlatform() and no Vintage
// engine.
//
// Coverage is measured and reported with the Android Gradle Plugin's JaCoCo support, on the debug
// build type. There is no failing threshold: no standard covers a threshold, it is the project's
// choice. The JaCoCo version is not set here; the build uses the Android Gradle Plugin's default.
// Coverage of a Kotlin JVM project is not configured: the first such project with sources decides
// it.
//
// open: the call forms and names below follow the documentation as understood at writing and are
// checked again at the first build: the testImplementation, androidTestImplementation and
// debugImplementation configurations; the testInstrumentationRunner and the two coverage switches
// in the Kotlin DSL; the kotlin-test dependency resolving to the JUnit 4 variant; the Compose Bill
// of Materials reaching the androidTest classpath and covering ui-test-junit4; the package of
// AndroidJUnitRunner; and that the runner runs JUnit 4 tests with this Android Gradle Plugin.

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.artifacts.VersionCatalogsExtension

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun lib(alias: String) = libs.findLibrary(alias).get()

val instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

// Unit tests, in every project that applies this plugin.
val unitTestLibraries = listOf(
    "junit",
    "kotlin-test",
    "mockk",
    "turbine",
    "kotlinx-coroutines-test"
)

listOf(
    "org.jetbrains.kotlin.jvm",
    "com.android.library",
    "com.android.application"
).forEach { pluginId ->
    pluginManager.withPlugin(pluginId) {
        unitTestLibraries.forEach { alias ->
            dependencies.add("testImplementation", lib(alias))
        }
    }
}

// Device tests, in the Android projects. A test that uses a substitute (MockK) says so in its name
// or a comment.
val androidTestLibraries = listOf(
    "androidx-test-runner",
    "androidx-test-rules",
    "androidx-test-core",
    "androidx-test-ext-junit",
    "mockk-android"
)

fun addAndroidTestLibraries() {
    androidTestLibraries.forEach { alias ->
        dependencies.add("androidTestImplementation", lib(alias))
    }
}

pluginManager.withPlugin("com.android.application") {
    addAndroidTestLibraries()
    extensions.configure<ApplicationExtension> {
        defaultConfig {
            testInstrumentationRunner = instrumentationRunner
        }
        buildTypes {
            getByName("debug") {
                enableUnitTestCoverage = true
                enableAndroidTestCoverage = true
            }
        }
    }
}

pluginManager.withPlugin("com.android.library") {
    addAndroidTestLibraries()
    extensions.configure<LibraryExtension> {
        defaultConfig {
            testInstrumentationRunner = instrumentationRunner
        }
        buildTypes {
            getByName("debug") {
                enableUnitTestCoverage = true
                enableAndroidTestCoverage = true
            }
        }
    }
}

// Compose UI tests, in the Android projects that apply the Compose plugin. The libraries have no
// version of their own: they take it from the Compose Bill of Materials.
pluginManager.withPlugin("org.jetbrains.kotlin.plugin.compose") {
    dependencies.add(
        "androidTestImplementation",
        dependencies.platform(lib("androidx-compose-bom"))
    )
    dependencies.add("androidTestImplementation", lib("androidx-compose-ui-test-junit4"))
    dependencies.add("debugImplementation", lib("androidx-compose-ui-test-manifest"))
}
