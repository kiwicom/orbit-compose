package kiwi.orbit.compose.ui.controls

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.foundation.contentColorFor

@Composable
public fun Scaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    backgroundColor: Color = OrbitTheme.colors.surface.main,
    contentColor: Color = contentColorFor(backgroundColor),
    toastHostState: ToastHostState = remember { ToastHostState() },
    toastHost: @Composable (ToastHostState) -> Unit = { ToastHost(it) },
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        color = backgroundColor,
        contentColor = contentColor,
    ) {
        SubcomposeLayout { constraints ->
            val layoutWidth = constraints.maxWidth
            val layoutHeight = constraints.maxHeight

            val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

            layout(layoutWidth, layoutHeight) {
                val topBarPlaceables = subcompose("top_app_bar", topBar).map {
                    it.measure(looseConstraints)
                }

                val topBarHeight = topBarPlaceables.maxByOrNull { it.height }?.height ?: 0
                val bodyContentHeight = layoutHeight - topBarHeight

                val toastPlacables = subcompose("toast") { toastHost(toastHostState) }.map {
                    it.measure(looseConstraints.copy(maxHeight = bodyContentHeight))
                }
                val contentPlaceables = subcompose("content", content).map {
                    it.measure(looseConstraints.copy(maxHeight = bodyContentHeight))
                }

                contentPlaceables.forEach {
                    it.place(0, topBarHeight)
                }
                topBarPlaceables.forEach {
                    it.place(0, 0)
                }
                toastPlacables.forEach {
                    // place it centered for tablet layouts
                    it.place((layoutWidth - it.measuredWidth) / 2, topBarHeight)
                }
            }
        }
    }
}
