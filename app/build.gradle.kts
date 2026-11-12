// Enforcement: the build fails if this project, the discovery module it depends on or a convention
// plugin below is missing from a role build that requires it (ADR-0007, rule 4); ktlint and
// Android Lint check this module in `check` and in the `android-static-analysis` job of CI, which
// first runs on the first pull request after the repository is published; detekt is added later;
// review is the check until then.
//
// The application. It composes the discovery module and holds the launcher activity, the
// manifest and the app resources. Every version comes from the version catalog and the
// Compose Bill of Materials (ADR-0007, rule 1), so none is written here. The application id
// is the project's choice; no standard covers it.

plugins {
    id("civicwindow.android-application")
    id("civicwindow.compose")
}

android {
    namespace = "org.civicwindow.app"

    defaultConfig {
        applicationId = "org.civicwindow.app"
    }
}

dependencies {
    implementation(project(":modules:android:discovery"))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui)
}
