package kiwi.orbit.compose.catalog.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.Surface
import kiwi.orbit.compose.ui.foundation.contentColorFor

@Composable
fun Scaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    backgroundColor: Color = OrbitTheme.colors.surface.background,
    contentColor: Color = contentColorFor(backgroundColor),
    content: @Composable () -> Unit
) {
    Surface(modifier = modifier, color = backgroundColor, contentColor = contentColor) {
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

                val contentPlaceables = subcompose("content", content).map {
                    it.measure(looseConstraints.copy(maxHeight = bodyContentHeight))
                }

                contentPlaceables.forEach {
                    it.place(0, topBarHeight)
                }
                topBarPlaceables.forEach {
                    it.place(0, 0)
                }
            }
        }
    }
}
