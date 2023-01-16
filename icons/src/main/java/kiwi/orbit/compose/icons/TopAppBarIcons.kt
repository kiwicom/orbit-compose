package kiwi.orbit.compose.icons

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

/**
 * TopAppBarIcons are to be used as a navigation icon in TopAppBar and its variants.
 */
@Suppress("unused")
public object TopAppBarIcons {
    public val Back: Painter
        @Composable
        get() = painterResource(R.drawable.ic_top_app_bar_back)

    public val Close: Painter
        @Composable
        get() = painterResource(R.drawable.ic_top_app_bar_close)
}
