package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.icons.TopAppBarIcons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalContentColor
import kiwi.orbit.compose.ui.foundation.LocalContentEmphasis
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle
import kiwi.orbit.compose.ui.utils.Strings
import kiwi.orbit.compose.ui.utils.getString
import kotlin.math.max
import kotlin.math.roundToInt

@Composable
public fun TopAppBar(
    title: @Composable () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: Painter = TopAppBarIcons.Back,
    actions: @Composable RowScope.() -> Unit = {},
    extraContent: @Composable () -> Unit = {},
    elevation: Dp = TopAppBarElevation,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    TopAppBar(
        title = title,
        modifier = modifier,
        navigationIcon = {
            IconButton(
                onClick = onNavigateUp,
            ) {
                Icon(
                    painter = navigationIcon,
                    contentDescription = getString(Strings.TopAppBarNavigationUp),
                )
            }
        },
        actions = actions,
        extraContent = extraContent,
        elevation = elevation,
        scrollBehavior = scrollBehavior,
    )
}

@Composable
public fun TopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    extraContent: @Composable () -> Unit = {},
    elevation: Dp = TopAppBarElevation,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    SingleRowTopAppBar(
        title = {
            Box(
                Modifier.semantics(mergeDescendants = true) {
                    testTag = TopAppBarSemantics.TitleTag
                },
            ) {
                title()
            }
        },
        navigationIcon = navigationIcon,
        actions = {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                content = actions,
            )
        },
        elevation = elevation,
        extraContent = extraContent,
        scrollBehavior = scrollBehavior,
        modifier = modifier.testTag(TopAppBarSemantics.Tag),
    )
}

@Composable
private fun SingleRowTopAppBar(
    title: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit,
    actions: @Composable () -> Unit,
    elevation: Dp,
    extraContent: @Composable () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior?,
    modifier: Modifier = Modifier,
) {
    // Set up support for resizing the top app bar when vertically dragging the bar itself.
    val appBarDragModifier = if (scrollBehavior?.isPinned == false) {
        Modifier.draggable(
            orientation = Orientation.Vertical,
            state = rememberDraggableState { delta ->
                scrollBehavior.state.heightOffset = scrollBehavior.state.heightOffset + delta
            },
            onDragStopped = { velocity ->
                settleAppBar(
                    scrollBehavior.state,
                    velocity,
                    scrollBehavior.flingAnimationSpec,
                    scrollBehavior.snapAnimationSpec,
                )
            },
        )
    } else {
        Modifier
    }

    Surface(
        modifier = modifier.then(appBarDragModifier),
        color = OrbitTheme.colors.surface.main,
        elevation = elevation,
    ) {
        Column(
            Modifier
                .statusBarsPadding()
                .windowInsetsPadding(WindowInsets.navigationBars.only(WindowInsetsSides.Horizontal)),
        ) {
            TopAppBarLayout(
                title = title,
                titleAlpha = 1f,
                hideTitleSemantics = false,
                navigationIcon = navigationIcon,
                actions = actions,
                modifier = when (scrollBehavior) {
                    null -> Modifier
                    else -> Modifier.scrollBehaviorLayout(scrollBehavior)
                },
            )
            extraContent()
        }
    }
}

internal fun Modifier.scrollBehaviorLayout(
    scrollBehavior: TopAppBarScrollBehavior,
): Modifier = clipToBounds().layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    val heightOffsetLimit = -placeable.height.toFloat()
    if (scrollBehavior.state.heightOffsetLimit != heightOffsetLimit) {
        scrollBehavior.state.heightOffsetLimit = heightOffsetLimit
    }
    val height = placeable.height + scrollBehavior.state.heightOffset.roundToInt()
    layout(placeable.width, height) {
        placeable.place(0, height - placeable.height)
    }
}

@Composable
internal fun TopAppBarLayout(
    title: @Composable () -> Unit,
    titleAlpha: Float,
    hideTitleSemantics: Boolean,
    navigationIcon: @Composable () -> Unit,
    actions: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Layout(
        modifier = modifier,
        content = {
            CompositionLocalProvider(
                LocalContentColor provides OrbitTheme.colors.content.normal,
                LocalContentEmphasis provides ContentEmphasis.Normal,
            ) {
                Box(
                    Modifier
                        .layoutId("navigationIcon")
                        .padding(start = TopAppBarHorizontalPadding),
                ) {
                    navigationIcon()
                }
                Box(
                    Modifier
                        .layoutId("title")
                        .padding(horizontal = TopAppBarHorizontalPadding)
                        .alpha(titleAlpha)
                        .then(if (hideTitleSemantics) Modifier.clearAndSetSemantics { } else Modifier),
                ) {
                    ProvideMergedTextStyle(TopAppBarTitleNormalTextStyle) {
                        title()
                    }
                }
                Box(
                    Modifier
                        .layoutId("actionIcons")
                        .padding(end = TopAppBarHorizontalPadding),
                ) {
                    ProvideMergedTextStyle(OrbitTheme.typography.bodyExtraLarge) {
                        actions()
                    }
                }
            }
        },
    ) { measurables, constraints ->
        val iconPlaceable = measurables.first { it.layoutId == "navigationIcon" }.measure(constraints)
        val actionsPlaceable = measurables.first { it.layoutId == "actionIcons" }.measure(constraints)

        val maxTitleWidth = constraints.maxWidth - iconPlaceable.width - actionsPlaceable.width
        val titleConstraints = constraints.copy(maxWidth = maxTitleWidth)
        val titlePlaceable = measurables.first { it.layoutId == "title" }.measure(titleConstraints)
        val layoutHeight = TopAppBarSingleRowContainerHeight.toPx().roundToInt()

        layout(constraints.maxWidth, layoutHeight) {
            iconPlaceable.placeRelative(
                x = 0,
                y = (layoutHeight - iconPlaceable.height) / 2,
            )
            titlePlaceable.placeRelative(
                x = max(TopAppBarTitleInset.roundToPx(), iconPlaceable.width),
                y = (layoutHeight - titlePlaceable.height) / 2,
            )
            actionsPlaceable.placeRelative(
                x = constraints.maxWidth - actionsPlaceable.width,
                y = (layoutHeight - actionsPlaceable.height) / 2,
            )
        }
    }
}

internal val TopAppBarElevation = OrbitTheme.elevations.Level1

private val TopAppBarHorizontalPadding = 4.dp
private val TopAppBarTitleInset = 16.dp - TopAppBarHorizontalPadding
private val TopAppBarSingleRowContainerHeight = 56.dp

private val TopAppBarTitleNormalTextStyle = TextStyle(
    fontSize = 19.sp,
    letterSpacing = 0.15.sp,
    fontWeight = FontWeight.Medium,
)

public object TopAppBarSemantics {
    public const val Tag: String = "top_app_bar"
    public const val TitleTag: String = "title"
}

@OrbitPreviews
@Composable
internal fun TopAppBarPreview() {
    Preview {
        TopAppBar(
            title = { Text("Title") },
            onNavigateUp = {},
        )
        TopAppBar(
            title = { Text("Title") },
            navigationIcon = {},
            actions = {
                IconButton(onClick = {}) {
                    Icon(Icons.Security, contentDescription = null)
                }
            },
        )
        TopAppBar(
            title = { Text("Title") },
            onNavigateUp = {},
            extraContent = {
                var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
                TabRow(selectedTabIndex = selectedTabIndex) {
                    Tab(
                        selected = selectedTabIndex == 0,
                        onClick = { selectedTabIndex = 0 },
                        text = { Text("Tab A") },
                    )
                    Tab(
                        selected = selectedTabIndex == 1,
                        onClick = { selectedTabIndex = 1 },
                        text = { Text("Tab B") },
                    )
                }
            },
        )
    }
}
