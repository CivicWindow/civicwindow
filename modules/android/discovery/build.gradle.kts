// Enforcement: the build fails if this project, its dependency on the contracts project or a
// convention plugin below is missing from a role build that requires it (ADR-0007, rule 4);
// ktlint and Android Lint check this module in `check` and in the `android-static-analysis` job of
// CI, which first runs on the first pull request after the repository is published; detekt is
// added later; review is the check until then.
//
// The discovery feature module: an Android library with Jetpack Compose. It depends on the
// contracts project and on no other module. Every version comes from the version catalog and
// the Compose Bill of Materials (ADR-0007, rule 1), so none is written here.

plugins {
    id("civicwindow.android-library")
    id("civicwindow.compose")
}

android {
    namespace = "org.civicwindow.discovery"
}

dependencies {
    implementation(project(":contracts"))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
}
