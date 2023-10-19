package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.windowInsetsStartWidth
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.CustomPlaceholder
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.contentColorFor
import kotlin.math.min
import kotlin.math.roundToInt

/**
 * Scaffold helps layouting basic screen widgets, such as [TopAppBar], [action] and the [content].
 *
 * The [action] slot takes a primary action for the screen, usually a button. This slot is wrapped with
 * [actionLayout] to provide additional insets and another visual padding and background fade.
 *
 * Utilize contentPadding passed to [content] lambda to handle IME, navigation or status bar paddings.
 * If you define your own [actionLayout], do not forget to handle IME and navigation bar insets in it.
 *
 * The [contentWindowInsets] allows disabling handling IME insets and keeping the Scaffold unaffected.
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
    contentWindowInsets: WindowInsets = WindowInsets.ime,
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

@Composable
private fun ScaffoldLayout(
    topBar: @Composable () -> Unit,
    toast: @Composable () -> Unit,
    action: @Composable () -> Unit,
    content: @Composable (contentPadding: PaddingValues) -> Unit,
    contentWindowInsets: WindowInsets,
) {
    var topContentPadding by remember { mutableStateOf(0.dp) }
    var startContentPadding by remember { mutableStateOf(0.dp) }
    var endContentPadding by remember { mutableStateOf(0.dp) }
    var bottomContentPadding by remember { mutableStateOf(0.dp) }
    val contentPadding = remember {
        object : PaddingValues {
            override fun calculateLeftPadding(layoutDirection: LayoutDirection): Dp =
                when (layoutDirection) {
                    LayoutDirection.Ltr -> startContentPadding
                    LayoutDirection.Rtl -> endContentPadding
                }

            override fun calculateTopPadding(): Dp = topContentPadding

            override fun calculateRightPadding(layoutDirection: LayoutDirection): Dp =
                when (layoutDirection) {
                    LayoutDirection.Ltr -> endContentPadding
                    LayoutDirection.Rtl -> startContentPadding
                }

            override fun calculateBottomPadding(): Dp = bottomContentPadding
        }
    }

    Layout(
        modifier = Modifier.windowInsetsPadding(contentWindowInsets),
        contents = listOf(
            { Spacer(Modifier.windowInsetsTopHeight(WindowInsets.systemBars)) },
            { Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.systemBars)) },
            { Spacer(Modifier.windowInsetsStartWidth(WindowInsets.systemBars)) },
            { Spacer(Modifier.windowInsetsEndWidth(WindowInsets.systemBars)) },
            topBar,
            toast,
            action,
            { content(contentPadding) },
        ),
    ) { measurables, constraints ->
        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight
        val looseConstraint = constraints.copy(minWidth = 0, minHeight = 0)

        // Measure insets separately and reuse for computation when needed
        val insetTop = measurables[0][0].measure(looseConstraint).height
        val insetBottom = measurables[1][0].measure(looseConstraint).height
        val insetStart = measurables[2][0].measure(looseConstraint).width
        val insetEnd = measurables[3][0].measure(looseConstraint).width

        // Measure top bar
        val topBarPlaceables = measurables[4].map { it.measure(looseConstraint) }

        // Calculate the space for action, it is limited to 80% of the available height.
        val topBarHeight = topBarPlaceables.maxOfOrNull { it.height } ?: 0
        val actionTop = if (topBarHeight != 0) topBarHeight else insetTop
        val maxActionHeight = ((layoutHeight - actionTop) * 0.8).roundToInt()
        val actionConstraints = looseConstraint.copy(maxHeight = maxActionHeight)

        // Measure action
        val actionPlaceables = measurables[6].map { it.measure(actionConstraints) }

        // Calculate the space for content and toast.
        val actionFadeHeight = actionPlaceables.firstOrNull()?.get(ActionFadeLine)
            ?.takeIf { it != AlignmentLine.Unspecified } ?: 0
        val actionHeight = actionPlaceables.maxOfOrNull { it.height } ?: 0

        // If there is fade, let's do not subtract it, we want the content to be shown below it.
        val maxContentHeight = layoutHeight - topBarHeight - actionHeight + actionFadeHeight
        val contentConstraints = Constraints(maxHeight = maxContentHeight, maxWidth = layoutWidth)

        // Update insets paddings for content before measuring it.
        topContentPadding = if (topBarHeight == 0) insetTop.toDp() else 0.dp
        bottomContentPadding = if (actionHeight == 0) insetBottom.toDp() else actionFadeHeight.toDp()
        startContentPadding = insetStart.toDp()
        endContentPadding = insetEnd.toDp()

        // Measure toast and content.
        val toastPlaceables = measurables[5].map { it.measure(contentConstraints) }
        val contentPlaceables = measurables[7].map { it.measure(contentConstraints) }

        layout(layoutWidth, layoutHeight) {
            contentPlaceables.forEach { it.placeRelative(x = 0, y = topBarHeight) }
            topBarPlaceables.forEach { it.placeRelative(x = 0, y = 0) }
            actionPlaceables.forEach { it.placeRelative(x = 0, y = layoutHeight - actionHeight) }
            toastPlaceables.forEach { it.placeRelative(x = 0, y = topBarHeight) }
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
    contentWindowInsets: WindowInsets = WindowInsets.systemBars,
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
    Layout(
        modifier = modifier.background(brush),
        content = {
            Box(Modifier.fillMaxWidth(), propagateMinConstraints = true) {
                content()
            }
            Spacer(Modifier.windowInsetsBottomHeight(contentWindowInsets))
        },
    ) { measurables, constraints ->
        val padding = 16.dp.roundToPx()
        val top = fadeHeight.roundToPx()
        val action = measurables.first().measure(
            constraints
                .offset(
                    horizontal = padding * -2,
                    vertical = -(padding + top),
                ),
        )

        if (action.height == 0) {
            return@Layout layout(0, 0) {}
        }

        val inset = measurables.last().measure(constraints.copy(minWidth = 0, minHeight = 0))
        val width = constraints.maxWidth
        val height = top + action.height + padding + inset.height

        layout(width, height, alignmentLines = mapOf(ActionFadeLine to top)) {
            action.place(x = (width - action.width) / 2, y = top)
        }
    }
}

/**
 * Models the height of the fading gradient - how much should be the content shifted under the action slot.
 */
public val ActionFadeLine: HorizontalAlignmentLine = HorizontalAlignmentLine(::min)

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

@OrbitPreviews
@Composable
internal fun ScaffoldWithFullScreenActionPreview() {
    Preview {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = "Title") })
            },
            action = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(OrbitTheme.colors.surface.normal),
                ) {
                    Text(
                        text = "Custom full screen action",
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(),
                    )
                    ButtonPrimary(
                        onClick = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter),
                    ) {
                        Text("Custom full screen action")
                    }
                }
            },
        ) {
            CustomPlaceholder(Modifier.fillMaxSize())
        }
    }
}
