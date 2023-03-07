package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.union
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.CustomPlaceholder
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.contentColorFor

/**
 * Scaffold helps layouting basic screen widgets, such as [TopAppBar], [action] and the [content].
 *
 * The [action] slot takes a primary action for the screen, usually a button. This slot is wrapped with
 * [actionLayout] to provide additional insets and another visual padding and background fade.
 *
 * Utilize contentPadding passed to [content] lambda to handle IME, navigation or status bar paddings.
 * If you define your own [actionLayout], do not forget to handle IME and navigation bar insets in it.
 */
@Composable
public fun Scaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    action: @Composable () -> Unit = {},
    backgroundColor: Color = OrbitTheme.colors.surface.main,
    contentColor: Color = contentColorFor(backgroundColor),
    actionLayout: @Composable () -> Unit = {
        ScaffoldAction(
            backgroundColor = backgroundColor,
            content = action,
        )
    },
    toastHostState: ToastHostState = remember { ToastHostState() },
    toastHost: @Composable (ToastHostState) -> Unit = { ToastHost(it) },
    contentWindowInsets: WindowInsets = WindowInsets.systemBars.union(WindowInsets.ime),
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
            contentWindowInsets = contentWindowInsets,
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ScaffoldLayout(
    topBar: @Composable () -> Unit,
    toast: @Composable () -> Unit,
    action: @Composable () -> Unit,
    content: @Composable (contentPadding: PaddingValues) -> Unit,
    contentWindowInsets: WindowInsets,
) {
    val imeOpened = WindowInsets.isImeVisible
    SubcomposeLayout { constraints ->
        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight
        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        val topBarPlaceables = subcompose(SlotTopAppBar, topBar).map { it.measure(looseConstraints) }
        val actionPlaceables = subcompose(SlotAction, action).map { it.measure(looseConstraints) }
        val toastPlaceables = subcompose(SlotToast, toast).map { it.measure(looseConstraints) }

        val actionHeight = actionPlaceables.maxOfOrNull { it.height } ?: 0
        val actionFadeHeight = actionPlaceables.firstOrNull()?.get(ActionFadeLine)?.let { value ->
            if (value == AlignmentLine.Unspecified) 0 else value
        } ?: 0

        val contentInsets = contentWindowInsets.asPaddingValues(this)
        val contentTop = topBarPlaceables.maxOfOrNull { it.height } ?: 0
        val contentBottom = if (actionHeight > 0) {
            (actionHeight - actionFadeHeight).coerceAtLeast(0)
        } else {
            if (imeOpened) {
                contentInsets.calculateBottomPadding().roundToPx()
            } else {
                0
            }
        }
        val contentConstraints = looseConstraints.copy(
            maxHeight = layoutHeight - contentTop - contentBottom,
        )

        val innerPadding = PaddingValues(
            top = if (contentTop > 0) {
                0.dp
            } else {
                contentInsets.calculateTopPadding()
            },
            bottom = if (actionHeight > 0) {
                actionFadeHeight.toDp()
            } else {
                if (imeOpened) {
                    0.dp
                } else {
                    contentInsets.calculateBottomPadding()
                }
            },
            start = contentInsets.calculateStartPadding(layoutDirection),
            end = contentInsets.calculateEndPadding(layoutDirection),
        )
        val contentPlaceables = subcompose(SlotContent) {
            content(innerPadding)
        }.map { it.measure(contentConstraints) }

        layout(layoutWidth, layoutHeight) {
            contentPlaceables.forEach { it.placeRelative(0, contentTop) }
            topBarPlaceables.forEach { it.placeRelative(0, 0) }
            actionPlaceables.forEach { it.placeRelative(0, layoutHeight - actionHeight) }
            toastPlaceables.forEach { it.placeRelative(0, contentTop) }
        }
    }
}

/**
 * Draws action if there is any action's content.
 * Adds additional padding and background to this action.
 */
@Composable
public fun ScaffoldAction(
    modifier: Modifier = Modifier,
    backgroundColor: Color = OrbitTheme.colors.surface.main,
    contentWindowInsets: WindowInsets = WindowInsets.systemBars.union(WindowInsets.ime),
    fadeHeight: Dp = DefaultActionFadeHeight,
    content: @Composable () -> Unit,
) {
    val density = LocalDensity.current
    val brush = remember(density, backgroundColor) {
        Brush.verticalGradient(
            colors = listOf(Color.Transparent, backgroundColor),
            endY = with(density) { fadeHeight.toPx() },
        )
    }
    val inset = contentWindowInsets.asPaddingValues()
    Layout(
        modifier = modifier.background(brush),
        content = {
            Box(Modifier.fillMaxWidth(), propagateMinConstraints = true) {
                content()
            }
        },
    ) { measurables, constraints ->
        val padding = 16.dp.roundToPx()
        val action = measurables.first().measure(
            constraints.offset(horizontal = padding * -2),
        )

        if (action.height == 0) {
            return@Layout layout(0, 0) {}
        }

        val top = fadeHeight.roundToPx()
        val width = constraints.maxWidth
        val height = top + action.height + padding + inset.calculateBottomPadding().roundToPx()

        layout(width, height, alignmentLines = mapOf(ActionFadeLine to top)) {
            action.place(x = (width - action.width) / 2, y = top)
        }
    }
}

private val ActionFadeLine = HorizontalAlignmentLine(::minOf)

private val SlotTopAppBar = 0
private val SlotAction = 1
private val SlotToast = 2
private val SlotContent = 3

/**
 * Action's top gradient currently decreased from 16.dp to minimize contentPadding
 * (auto)scrolling issues. We need a new api for scroll to be able to account for
 * this semi-transparent area.
 * https://issuetracker.google.com/issues/221252680
 */
public val DefaultActionFadeHeight: Dp = 8.dp

@OrbitPreviews
@Composable
internal fun ScaffoldPreview() {
    Preview {
        Scaffold(
            action = {
                ButtonPrimary(onClick = {}) { Text("Test") }
            },
        ) {
            CustomPlaceholder(Modifier.fillMaxSize())
        }
    }
}
