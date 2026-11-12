// Enforcement: ktlintCheck runs in `check` and in the `android-static-analysis` job of CI, which
// first runs on the first pull request after the repository is published; review is the check
// until then.
// Documentation: ktlint, "Integrations" (the Gradle plugins),
// https://ktlint.github.io/ktlint/latest/install/integrations/; the ktlint Gradle plugin
// (org.jlleitschuh.gradle.ktlint), its README; the Android Kotlin style guide.
//
// Runs the ktlint formatter as a build check. The code style and the column limit are not set here:
// they come from the Kotlin section of .editorconfig. The tasks are ktlintCheck (fails on a
// finding) and ktlintFormat (rewrites files; it is not part of `check`). The check covers each
// project's Kotlin sources and its *.gradle.kts files; build output is excluded.
//
// open: the plugin's property names, its task names and its default coverage of *.gradle.kts files,
// and ktlint 1.8.0 and plugin 14.2.0 with Kotlin 2.4.21 and AGP 9.3.3, are checked again at the
// first build.
//
// Coverage: this plugin is applied to :app, :modules:android:discovery and :contracts through
// civicwindow.base. The root build files and build-logic itself are not covered by it; review is
// the check for those until the root build and build-logic apply this plugin.

import org.gradle.api.artifacts.VersionCatalogsExtension
import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
    id("org.jlleitschuh.gradle.ktlint")
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

configure<KtlintExtension> {
    // The engine version comes from the catalog, never from a literal.
    version.set(libs.findVersion("ktlint").get().requiredVersion)
    // A finding fails the build.
    ignoreFailures.set(false)
    // Build output is not checked.
    filter {
        exclude("**/build/**")
    }
}

// ktlintCheck is part of `check`. The match works whichever plugin creates `check` first.
tasks.matching { it.name == "check" }.configureEach {
    dependsOn("ktlintCheck")
}
