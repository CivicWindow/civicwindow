// Enforcement: the `android-build` job of CI compiles this test (assembleDebugAndroidTest), and
// ktlint and Android Lint check it in `check` and in the `android-static-analysis` job; the test
// itself is run by hand on a physical device, because an emulator-only run does not establish
// device readiness. Each job first runs on the first pull request after the repository is
// published; review is the check until then.
// Documentation: Jetpack Compose, "Test your Compose layout" (createComposeRule); AndroidX Test,
// "AndroidJUnitRunner"; Kotlin coding conventions (backticked sentence names for tests only).
package org.civicwindow.discovery

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Device test for [DiscoveryHome]. The screen must show the app name it is given and read no
 * resource of the app module, so the test passes a fixed string and looks for it on screen. The
 * test uses no substitute.
 */
@RunWith(AndroidJUnit4::class)
class DiscoveryHomeTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `shows the app name it is given`() {
        val appName = "Name given by the test"

        composeRule.setContent { DiscoveryHome(appName = appName) }

        composeRule.onNodeWithText(appName).assertIsDisplayed()
    }
}
