package dev.theolm.rinku.compose.ext

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.core.util.Consumer
import dev.theolm.rinku.models.DeepLinkFilter
import dev.theolm.rinku.models.DeepLinkMapper
import dev.theolm.rinku.treatAndFireDeepLink

@Composable
fun ComponentActivity.Rinku(
    deepLinkFilter: DeepLinkFilter? = null,
    deepLinkMapper: DeepLinkMapper? = null,
    content: @Composable () -> Unit
) {
    intent.treatAndFireDeepLink(deepLinkFilter, deepLinkMapper)

    DisposableEffect(Unit) {
        val listener = Consumer<Intent> {
            it?.treatAndFireDeepLink(deepLinkFilter, deepLinkMapper)
        }

        this@Rinku.addOnNewIntentListener(listener)
        onDispose { this@Rinku.removeOnNewIntentListener(listener) }
    }

    content()
}
