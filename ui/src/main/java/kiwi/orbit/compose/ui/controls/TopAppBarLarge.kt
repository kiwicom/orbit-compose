package kiwi.orbit.compose.ui.controls

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.icons.Icons
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
    navigationIcon: Painter = painterResource(R.drawable.ic_appbar_arrow_back),
    actions: @Composable RowScope.() -> Unit = {},
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
    largeElevated: Boolean = true,
    elevation: Dp = TopAppBarElevation,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    TwoRowsTopAppBar(
        modifier = modifier.testTag(TopAppBarSemantics.Tag),
        title = {
            Box(
                Modifier.semantics(mergeDescendants = true) {
                    testTag = TopAppBarSemantics.TitleTag
                }
            ) {
                title()
            }
        },
        largeTitle = {
            Box(
                Modifier.semantics(mergeDescendants = true) {
                    testTag = TopAppBarSemantics.TitleTag
                }
            ) {
                largeTitle()
            }
        },
        navigationIcon = navigationIcon,
        actions = {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                content = actions
            )
        },
        largeElevated = largeElevated,
        elevation = elevation,
        scrollBehavior = scrollBehavior,
    )
}

@Composable
private fun TwoRowsTopAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    largeTitle: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit,
    actions: @Composable () -> Unit,
    largeElevated: Boolean,
    elevation: Dp,
    scrollBehavior: TopAppBarScrollBehavior?
) {
    check(largeElevated || (!largeElevated && scrollBehavior != null)) {
        "TopAppBarLarge non-elevated is supported only with scrolling behavior."
    }

    val scrollFraction = scrollBehavior?.scrollFraction ?: 0f
    val hideTopRowSemantics = scrollFraction < 0.5f
    val hideBottomRowSemantics = !hideTopRowSemantics

    if (largeElevated) {
        Surface(
            modifier = modifier,
            color = OrbitTheme.colors.surface.main,
            elevation = elevation,
        ) {
            Column(
                Modifier.windowInsetsPadding(
                    WindowInsets.systemBars.only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top)
                )
            ) {
                TopAppBarLayout(
                    modifier = Modifier,
                    title = title,
                    titleAlpha = scrollFraction,
                    hideTitleSemantics = hideTopRowSemantics,
                    navigationIcon = navigationIcon,
                    actions = actions,
                )
                TopAppBarLargeLayout(
                    modifier = Modifier,
                    scrollBehavior = scrollBehavior,
                    largeTitle = largeTitle,
                    hideTitleSemantics = hideBottomRowSemantics,
                )
            }
        }
    } else {
        Column {
            Surface(
                modifier = modifier,
                color = when (scrollFraction > 0.01f) {
                    true -> OrbitTheme.colors.surface.main
                    false -> Color.Transparent
                },
                elevation = elevation * scrollFraction,
            ) {
                TopAppBarLayout(
                    modifier = Modifier.windowInsetsPadding(
                        WindowInsets.systemBars.only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top)
                    ),
                    title = title,
                    titleAlpha = scrollFraction,
                    hideTitleSemantics = hideTopRowSemantics,
                    navigationIcon = navigationIcon,
                    actions = actions,
                )
            }
            TopAppBarLargeLayout(
                modifier = Modifier.windowInsetsPadding(
                    WindowInsets.systemBars.only(WindowInsetsSides.Horizontal)
                ),
                scrollBehavior = scrollBehavior,
                largeTitle = largeTitle,
                hideTitleSemantics = hideBottomRowSemantics,
            )
        }
    }
}

@Composable
private fun TopAppBarLargeLayout(
    modifier: Modifier,
    scrollBehavior: TopAppBarScrollBehavior?,
    largeTitle: @Composable () -> Unit,
    hideTitleSemantics: Boolean,
) {
    val scrollFraction = scrollBehavior?.scrollFraction ?: 0f
    Box(
        modifier
            .then(if (hideTitleSemantics) Modifier.clearAndSetSemantics { } else Modifier)
            .alpha(1f - (scrollFraction * 1.8f).coerceAtMost(1f))
            .then(
                when (scrollBehavior) {
                    null -> Modifier
                    else -> Modifier.scrollBehaviorLayout(scrollBehavior)
                }
            )
            .padding(horizontal = 16.dp, vertical = 8.dp)
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
            }
        )
    }
}
