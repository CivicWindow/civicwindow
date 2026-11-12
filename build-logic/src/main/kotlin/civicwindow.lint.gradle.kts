// Enforcement: the build fails (task lintDebug, run by check) on any Android Lint warning or error
// in an Android application or library project; the two target-SDK checks are fatal
// in config/lint/lint.xml.
// Documentation: Android Gradle Plugin, "Configure lint options with Gradle" and the DSL reference
// for com.android.build.api.dsl.Lint (abortOnError, warningsAsErrors, lintConfig, baseline,
// checkDependencies).
//
// Android Lint options for the Android application and library projects. A Kotlin JVM project has
// no Android Lint, so this plugin changes nothing there. The base plugin applies this plugin, so
// this plugin does not apply the base plugin.
//
// checkDependencies is left at its default (false): each Android project runs its own lint.
// The options below are the project's choice; no standard covers them.
//
// Baseline: the baseline file is config/lint/lint-baseline.xml. It is used only when it exists, and
// it starts absent. No baseline entry may be added to hide a new finding.
// open: the option names follow the DSL reference for the 9.0 API as understood at writing,
// the lintDebug task name and its link to check are Android Gradle Plugin behavior not checked
// against its 9.3 documentation, and the Lint, ApplicationExtension and LibraryExtension types are
// checked again at the first build.

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.dsl.Lint

val lintConfigFile = rootProject.file("config/lint/lint.xml")
val lintBaselineFile = rootProject.file("config/lint/lint-baseline.xml")

fun applyLintOptions(lint: Lint) {
    lint.abortOnError = true
    lint.warningsAsErrors = true
    lint.lintConfig = lintConfigFile
    if (lintBaselineFile.exists()) {
        lint.baseline = lintBaselineFile
    }
}

pluginManager.withPlugin("com.android.application") {
    extensions.configure<ApplicationExtension> {
        lint { applyLintOptions(this) }
    }
    tasks.named("check") { dependsOn("lintDebug") }
}

pluginManager.withPlugin("com.android.library") {
    extensions.configure<LibraryExtension> {
        lint { applyLintOptions(this) }
    }
    tasks.named("check") { dependsOn("lintDebug") }
}
