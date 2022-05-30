package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.R
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalContentColor
import kiwi.orbit.compose.ui.foundation.LocalContentEmphasis
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle
import kotlin.math.max
import kotlin.math.roundToInt

@Composable
public fun TopAppBar(
    title: @Composable () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: Painter = painterResource(R.drawable.ic_appbar_arrow_back),
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
                    contentDescription = stringResource(R.string.orbit_cd_navigate_up),
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
        navigationIcon = navigationIcon,
        contentPadding = rememberInsetsPaddingValues(
            insets = LocalWindowInsets.current.systemBars,
            applyBottom = false
        ),
        actions = {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                content = actions
            )
        },
        elevation = elevation,
        extraContent = extraContent,
        scrollBehavior = scrollBehavior,
    )
}

@Composable
private fun SingleRowTopAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit,
    actions: @Composable () -> Unit,
    contentPadding: PaddingValues,
    elevation: Dp,
    extraContent: @Composable () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior?,
) {
    Surface(
        modifier = modifier,
        color = OrbitTheme.colors.surface.main,
        elevation = elevation
    ) {
        Column(Modifier.padding(contentPadding)) {
            TopAppBarLayout(
                modifier = when (scrollBehavior) {
                    null -> Modifier
                    else -> Modifier.scrollBehaviorLayout(scrollBehavior)
                },
                title = title,
                titleAlpha = 1f,
                hideTitleSemantics = false,
                navigationIcon = navigationIcon,
                actions = actions,
            )
            extraContent()
        }
    }
}

internal fun Modifier.scrollBehaviorLayout(
    scrollBehavior: TopAppBarScrollBehavior,
): Modifier = clipToBounds().layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)

    if (scrollBehavior.offsetLimit != placeable.height.toFloat()) {
        scrollBehavior.offsetLimit = -placeable.height.toFloat()
    }

    val height = placeable.height + scrollBehavior.offset.roundToInt()
    layout(placeable.width, height) {
        placeable.place(0, height - placeable.height)
    }
}

@Composable
internal fun TopAppBarLayout(
    modifier: Modifier,
    title: @Composable () -> Unit,
    titleAlpha: Float,
    hideTitleSemantics: Boolean,
    navigationIcon: @Composable () -> Unit,
    actions: @Composable () -> Unit,
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
                        .then(if (hideTitleSemantics) Modifier.clearAndSetSemantics { } else Modifier)
                ) {
                    ProvideMergedTextStyle(TopAppBarTitleNormalTextStyle) {
                        title()
                    }
                }
                Box(
                    Modifier
                        .layoutId("actionIcons")
                        .padding(end = TopAppBarHorizontalPadding)
                ) {
                    actions()
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
                y = (layoutHeight - iconPlaceable.height) / 2
            )
            titlePlaceable.placeRelative(
                x = max(TopAppBarTitleInset.roundToPx(), iconPlaceable.width),
                y = (layoutHeight - titlePlaceable.height) / 2
            )
            actionsPlaceable.placeRelative(
                x = constraints.maxWidth - actionsPlaceable.width,
                y = (layoutHeight - actionsPlaceable.height) / 2
            )
        }
    }
}

internal val TopAppBarElevation = 2.dp

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

@Preview
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
            }
        )
    }
}
