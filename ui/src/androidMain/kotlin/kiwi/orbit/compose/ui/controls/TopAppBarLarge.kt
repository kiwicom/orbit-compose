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
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.icons.TopAppBarIcons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.R
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle

@Composable
public fun TopAppBarLarge(
    title: @Composable () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    largeTitle: @Composable () -> Unit = title,
    navigationIcon: Painter = TopAppBarIcons.Back,
    actions: @Composable RowScope.() -> Unit = {},
    extraContent: @Composable () -> Unit = {},
    largeElevated: Boolean = true,
    elevation: Dp = TopAppBarElevation,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    TopAppBarLarge(
        title = title,
        modifier = modifier,
        navigationIcon = {
            IconButton(
                onClick = onNavigateUp,
            ) {
                Icon(
                    painter = navigationIcon,
                    contentDescription = stringResource(R.string.orbit_cd_navigate_up),
                )
            }
        },
        largeTitle = largeTitle,
        actions = actions,
        extraContent = extraContent,
        largeElevated = largeElevated,
        elevation = elevation,
        scrollBehavior = scrollBehavior,
    )
}

@Composable
public fun TopAppBarLarge(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    largeTitle: @Composable () -> Unit = title,
    actions: @Composable RowScope.() -> Unit = {},
    extraContent: @Composable () -> Unit = {},
    largeElevated: Boolean = true,
    elevation: Dp = TopAppBarElevation,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    TwoRowsTopAppBar(
        title = {
            Box(
                Modifier.semantics(mergeDescendants = true) {
                    testTag = TopAppBarSemantics.TitleTag
                },
            ) {
                title()
            }
        },
        largeTitle = {
            Box(
                Modifier.semantics(mergeDescendants = true) {
                    testTag = TopAppBarSemantics.TitleTag
                },
            ) {
                largeTitle()
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
        extraContent = extraContent,
        largeElevated = largeElevated,
        elevation = elevation,
        scrollBehavior = scrollBehavior,
        modifier = modifier.testTag(TopAppBarSemantics.Tag),
    )
}

@Composable
private fun TwoRowsTopAppBar(
    title: @Composable () -> Unit,
    largeTitle: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit,
    actions: @Composable () -> Unit,
    extraContent: @Composable () -> Unit,
    largeElevated: Boolean,
    elevation: Dp,
    scrollBehavior: TopAppBarScrollBehavior?,
    modifier: Modifier = Modifier,
) {
    check(largeElevated || (!largeElevated && scrollBehavior != null)) {
        "TopAppBarLarge non-elevated is supported only with scrolling behavior."
    }

    val alphaFraction = scrollBehavior?.state?.collapsedFraction ?: 0f
    val hideTopRowSemantics = alphaFraction < 0.5f
    val hideBottomRowSemantics = !hideTopRowSemantics

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

    if (largeElevated) {
        Surface(
            modifier = modifier.then(appBarDragModifier),
            color = OrbitTheme.colors.surface.main,
            elevation = elevation,
        ) {
            Column(
                Modifier.windowInsetsPadding(
                    WindowInsets.systemBars.only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top),
                ),
            ) {
                TopAppBarLayout(
                    title = title,
                    titleAlpha = alphaFraction,
                    hideTitleSemantics = hideTopRowSemantics,
                    navigationIcon = navigationIcon,
                    actions = actions,
                    modifier = Modifier,
                )
                TopAppBarLargeLayout(
                    scrollBehavior = scrollBehavior,
                    largeTitle = largeTitle,
                    hideTitleSemantics = hideBottomRowSemantics,
                    modifier = Modifier,
                )
                extraContent()
            }
        }
    } else {
        Column(
            Modifier.then(appBarDragModifier),
        ) {
            Surface(
                modifier = modifier,
                color = when (alphaFraction > 0.01f) {
                    true -> OrbitTheme.colors.surface.main
                    false -> Color.Transparent
                },
                elevation = elevation * alphaFraction,
            ) {
                TopAppBarLayout(
                    title = title,
                    titleAlpha = alphaFraction,
                    hideTitleSemantics = hideTopRowSemantics,
                    navigationIcon = navigationIcon,
                    actions = actions,
                    modifier = Modifier.windowInsetsPadding(
                        WindowInsets.systemBars.only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top),
                    ),
                )
            }
            TopAppBarLargeLayout(
                scrollBehavior = scrollBehavior,
                largeTitle = largeTitle,
                hideTitleSemantics = hideBottomRowSemantics,
                modifier = Modifier.windowInsetsPadding(
                    WindowInsets.systemBars.only(WindowInsetsSides.Horizontal),
                ),
            )
            extraContent()
        }
    }
}

@Composable
private fun TopAppBarLargeLayout(
    scrollBehavior: TopAppBarScrollBehavior?,
    largeTitle: @Composable () -> Unit,
    hideTitleSemantics: Boolean,
    modifier: Modifier = Modifier,
) {
    val scrollFraction = scrollBehavior?.state?.collapsedFraction ?: 0f
    Box(
        modifier
            .then(if (hideTitleSemantics) Modifier.clearAndSetSemantics { } else Modifier)
            .alpha(1f - (scrollFraction * 1.8f).coerceAtMost(1f))
            .then(
                when (scrollBehavior) {
                    null -> Modifier
                    else -> Modifier.scrollBehaviorLayout(scrollBehavior)
                },
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        ProvideMergedTextStyle(value = OrbitTheme.typography.title1) {
            largeTitle()
        }
    }
}

@OrbitPreviews
@Composable
internal fun TopAppBarLargePreview() {
    Preview {
        TopAppBarLarge(
            title = { Text("Title") },
            onNavigateUp = {},
        )
        TopAppBarLarge(
            title = { Text("Title") },
            navigationIcon = {},
            actions = {
                IconButton(onClick = {}) {
                    Icon(Icons.Security, contentDescription = null)
                }
            },
        )
        TopAppBarLarge(
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
