package kiwi.orbit.compose.icons

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

/**
 * TopAppBarIcons are to be used as a navigation icon in TopAppBar and its variants.
 */
@OptIn(ExperimentalResourceApi::class)
@Suppress("unused")
public object TopAppBarIcons {
    public val Back: Painter
        @Composable
        get() = painterResource("ic_top_app_bar_back.xml")

    public val Close: Painter
        @Composable
        get() = painterResource("ic_top_app_bar_close.xml")
}
