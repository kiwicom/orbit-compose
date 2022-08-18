package kiwi.orbit.compose.ui.controls.internal

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.Surface
import kiwi.orbit.compose.ui.foundation.darkColors
import kiwi.orbit.compose.ui.foundation.lightColors

@Composable
internal fun Preview(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = when (!isSystemInDarkTheme()) {
        true -> lightColors()
        false -> darkColors()
    }
    OrbitTheme(colors = colors) {
        Surface {
            Column(
                modifier = modifier.padding(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                content()
            }
        }
    }
}

@Preview(
    name = "A/Standard",
    group = "standard",
)
@Preview(
    name = "B/Large font",
    group = "large-font",
    fontScale = 1.6f,
)
@Preview(
    name = "C/Dark mode",
    group = "dark-mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
internal annotation class OrbitPreviews
