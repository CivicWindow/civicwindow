// Enforcement: ktlint and Android Lint check this file in `check` and in the
// `android-static-analysis` job of CI (ADR-0006), which first runs on the first pull request after
// the repository is published; detekt is added later; review is the check until then.
package org.civicwindow.discovery

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * The home screen of the discovery feature.
 *
 * It shows the app name it is given, centered on the screen. The module reads no resource of
 * the app module, so the caller passes the name in.
 *
 * @param appName the name of the app, shown as text.
 */
@Composable
fun DiscoveryHome(appName: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = appName)
    }
}
