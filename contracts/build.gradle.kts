// Enforcement: the build fails if this project or the convention plugin below is missing from a
// role build that requires it (ADR-0007, rule 4); ktlint checks this module in `check` and in the
// `android-static-analysis` job of CI, which first runs on the first pull request after the
// repository is published; detekt is added later; review is the check until then.
//
// The contracts project has no sources by design. Its README says that no contract is
// implemented until its producer and consumers have reviewed one, so no interface is
// declared here. The module graph follows ADR-0007, rule 5.

plugins {
    id("civicwindow.kotlin-library")
}

group = "org.civicwindow"
