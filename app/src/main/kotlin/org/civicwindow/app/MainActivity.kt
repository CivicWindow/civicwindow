// Enforcement: ktlint and Android Lint check this file in `check` and in the
// `android-static-analysis` job of CI (ADR-0006), which first runs on the first pull request after
// the repository is published; detekt is added later; review is the check until then.
package org.civicwindow.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.res.stringResource
import org.civicwindow.discovery.DiscoveryHome

/**
 * The launcher activity of the app.
 *
 * It shows the discovery home screen and passes it the app name from this module's resources.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiscoveryHome(appName = stringResource(R.string.app_name))
        }
    }
}
