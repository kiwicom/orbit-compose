package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.Divider
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.OrbitElevations
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle

@Composable
public fun TabRow(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    indicator: @Composable (tabPositions: List<TabPosition>) -> Unit = @Composable { tabPositions ->
        TabIndicator(
            Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
        )
    },
    divider: @Composable () -> Unit = @Composable {
        Divider()
    },
    tabs: @Composable () -> Unit,
) {
    OrbitElevations {
        androidx.compose.material3.TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = modifier,
            containerColor = OrbitTheme.colors.surface.main,
            contentColor = contentColorFor(OrbitTheme.colors.surface.main),
            indicator = indicator,
            divider = divider,
            tabs = tabs,
        )
    }
}

@Composable
public fun ScrollableTabRow(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    edgePadding: Dp = ScrollableTabRowPadding,
    indicator: @Composable (tabPositions: List<TabPosition>) -> Unit = @Composable { tabPositions ->
        TabIndicator(
            Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
        )
    },
    divider: @Composable () -> Unit = @Composable {
        Divider()
    },
    tabs: @Composable () -> Unit,
) {
    OrbitElevations {
        androidx.compose.material3.ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = modifier,
            containerColor = OrbitTheme.colors.surface.main,
            contentColor = contentColorFor(OrbitTheme.colors.surface.main),
            edgePadding = edgePadding,
            indicator = indicator,
            divider = divider,
            tabs = tabs,
        )
    }
}

@Composable
public fun Tab(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    text: @Composable () -> Unit,
) {
    androidx.compose.material3.Tab(
        selected = selected,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        selectedContentColor = OrbitTheme.colors.content.normal,
        unselectedContentColor = OrbitTheme.colors.content.normal,
        interactionSource = interactionSource,
        text = {
            ProvideMergedTextStyle(OrbitTheme.typography.bodyNormalMedium) {
                text()
            }
        },
    )
}

@Composable
public fun TabIndicator(
    modifier: Modifier = Modifier,
    color: Color = OrbitTheme.colors.primary.normal,
) {
    Box(
        modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(
                color = color,
                shape = OrbitTheme.shapes.small.copy(
                    bottomStart = ZeroCornerSize,
                    bottomEnd = ZeroCornerSize,
                ),
            ),
    )
}

/**
 * The default padding from the starting edge before a tab in a [ScrollableTabRow].
 */
private val ScrollableTabRowPadding = 52.dp

@OrbitPreviews
@Composable
internal fun TabsPreview() {
    Preview {
        var i by rememberSaveable { mutableIntStateOf(1) }
        TabRow(selectedTabIndex = i) {
            Tab(selected = i == 0, onClick = { i = 0 }) { Text("Tab A") }
            Tab(selected = i == 1, onClick = { i = 1 }) { Text("Tab B") }
            Tab(selected = i == 2, onClick = { i = 2 }) { Text("Tab C") }
        }
        ScrollableTabRow(selectedTabIndex = i) {
            Tab(selected = i == 0, onClick = { i = 0 }) { Text("Tab A") }
            Tab(selected = i == 1, onClick = { i = 1 }) { Text("Tab B") }
            Tab(selected = i == 2, onClick = { i = 2 }) { Text("Tab C") }
            Tab(selected = i == 3, onClick = { i = 3 }) { Text("Tab D") }
            Tab(selected = i == 4, onClick = { i = 4 }) { Text("Tab E") }
        }
    }
}
