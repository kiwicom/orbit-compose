package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.CustomPlaceholder
import kiwi.orbit.compose.ui.controls.internal.MutablePaddingValues
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.contentColorFor

public val LocalScaffoldPadding: ProvidableCompositionLocal<PaddingValues> =
    staticCompositionLocalOf { PaddingValues(0.dp) }

@Composable
public fun Scaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    action: @Composable () -> Unit = {},
    backgroundColor: Color = OrbitTheme.colors.surface.main,
    contentColor: Color = contentColorFor(backgroundColor),
    actionLayout: @Composable () -> Unit = { ScaffoldAction(backgroundColor, action) },
    toastHostState: ToastHostState = remember { ToastHostState() },
    toastHost: @Composable (ToastHostState) -> Unit = { ToastHost(it) },
    content: @Composable (contentPadding: PaddingValues) -> Unit,
) {
    Surface(
        modifier = modifier,
        color = backgroundColor,
        contentColor = contentColor,
    ) {
        ScaffoldLayout(
            topBar = topBar,
            toast = { toastHost(toastHostState) },
            action = actionLayout,
            content = content,
        )
    }
}

@Composable
private fun ScaffoldLayout(
    topBar: @Composable () -> Unit,
    toast: @Composable () -> Unit,
    action: @Composable () -> Unit,
    content: @Composable (contentPadding: PaddingValues) -> Unit,
) {
    val density = LocalDensity.current
    val contentPadding = remember { MutablePaddingValues() }
    val insets = WindowInsets.ime.union(WindowInsets.navigationBars)

    Layout(
        content = {
            Box { topBar() }
            Box { toast() }
            Box { action() }
            Box {
                CompositionLocalProvider(
                    LocalScaffoldPadding provides contentPadding,
                ) {
                    content(contentPadding)
                }
            }
        },
    ) { measurables, constraints ->
        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight
        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        val topBarPlaceable = measurables[0].measure(looseConstraints)

        val topBarHeight = topBarPlaceable.height
        val mainConstraints = looseConstraints.copy(maxHeight = layoutHeight - topBarHeight)
        val toastPlaceables = measurables[1].measure(mainConstraints)

        val actionPlaceable = measurables[2].measure(mainConstraints)
        val actionHeight = actionPlaceable.height

        val contentPlaceable = measurables[3].measure(looseConstraints)

        val topInset = topBarHeight.takeUnless { it == 0 } ?: insets.getTop(density)
        val startInset = insets.getLeft(density, LayoutDirection.Ltr)
        val endInset = insets.getRight(density, LayoutDirection.Ltr)
        val bottomInset = actionHeight.takeUnless { it == 0 } ?: insets.getBottom(density)

        contentPadding.updateFrom(
            top = topInset.toDp(),
            start = startInset.toDp(),
            end = endInset.toDp(),
            bottom = bottomInset.toDp(),
        )

        layout(layoutWidth, layoutHeight) {
            contentPlaceable.place(0, 0)
            topBarPlaceable.place(0, 0)
            actionPlaceable.place(0, layoutHeight - bottomInset)
            toastPlaceables.place((layoutWidth - toastPlaceables.measuredWidth) / 2, topBarHeight)
        }
    }
}

/**
 * Draws action if there is any action's content.
 * Adds additional padding and background to this action.
 */
@Composable
private fun ScaffoldAction(
    backgroundColor: Color,
    content: @Composable () -> Unit,
) {
    val density = LocalDensity.current
    val brush = remember(density, backgroundColor) {
        Brush.verticalGradient(
            colors = listOf(Color.Transparent, backgroundColor),
            endY = with(density) { 16.dp.toPx() },
        )
    }
    Layout(
        content = content,
        modifier = Modifier
            .background(brush)
            .windowInsetsPadding(WindowInsets.ime.union(WindowInsets.navigationBars)),
    ) { measurables, constraints ->
        val action = measurables.firstOrNull() ?: return@Layout layout(0, 0) {}

        val padding = 16.dp.roundToPx()
        val placeable = action.measure(constraints.offset(horizontal = -2 * padding))
        val width = constraints.maxWidth
        val height = placeable.height + 2 * padding

        layout(width, height) {
            placeable.place(x = (width - placeable.width) / 2, y = padding)
        }
    }
}

@Preview
@Composable
internal fun ScaffoldPreview() {
    Preview {
        Scaffold(
            action = {
                ButtonPrimary(onClick = {}, Modifier.fillMaxWidth()) { Text("Test") }
            },
        ) {
            CustomPlaceholder(Modifier.fillMaxSize())
        }
    }
}
