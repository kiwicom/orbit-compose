package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.catalog.components.CustomPlaceholder
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.Icon
import kiwi.orbit.compose.ui.controls.IconButton
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Tag
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBar
import kiwi.orbit.compose.ui.controls.TopAppBarScrollBehavior

@Composable
internal fun TopAppBarNormalScreen(
    onNavigateUp: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Simple") },
                onNavigateUp = onNavigateUp,
                actions = {
                    IconButton(onClick = {}) {
                        Icon(painter = Icons.Notification, contentDescription = null)
                    }
                }
            )
        },
        content = { CustomContentPlaceholder(it) },
    )
}

@Composable
internal fun TopAppBarNormalScrollableScreen(
    onNavigateUp: () -> Unit,
) {
    val scrollBehavior = TopAppBarScrollBehavior.rememberEnterAlways()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text("Scrollable") },
                onNavigateUp = onNavigateUp,
                scrollBehavior = scrollBehavior,
            )
        },
        content = { CustomContentPlaceholder(it) },
    )
}

@Composable
internal fun TopAppBarNormalWithTabsScreen(
    onNavigateUp: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("With Tabs") },
                onNavigateUp = onNavigateUp,
                extraContent = {
                    var state by remember { mutableStateOf(0) }
                    TabRow(
                        selectedTabIndex = state,
                        backgroundColor = OrbitTheme.colors.surface.main,
                        indicator = { tabPositions ->
                            TabRowDefaults.Indicator(
                                modifier = Modifier.tabIndicatorOffset(tabPositions[state]),
                                color = OrbitTheme.colors.primary.normal,
                            )
                        },
                        divider = {},
                    ) {
                        Tab(
                            selected = state == 0,
                            onClick = { state = 0 },
                            text = { Text("Normal") },
                        )
                        Tab(
                            selected = state == 1,
                            onClick = { state = 1 },
                            text = { Text("Inline") },
                        )
                    }
                },
            )
        },
        content = { CustomContentPlaceholder(it) },
    )
}

@Composable
internal fun TopAppBarNormalWithFiltersScreen(
    onNavigateUp: () -> Unit,
) {
    val scrollBehavior = TopAppBarScrollBehavior.rememberPinned()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { Text("With Filters") },
                onNavigateUp = onNavigateUp,
                extraContent = {
                    Row(
                        Modifier
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 8.dp)
                    ) {
                        var selected by remember { mutableStateOf(true) }
                        Tag(selected = selected, onSelect = { selected = !selected }) {
                            Text("Custom Filter")
                        }
                    }
                },
            )
        },
        content = { CustomContentPlaceholder(it) },
    )
}

@Composable
internal fun CustomContentPlaceholder(
    contentPadding: PaddingValues,
) {
    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .padding(contentPadding)
            .padding(16.dp)
    ) {
        CustomPlaceholder(
            Modifier
                .height(987.dp)
                .clip(OrbitTheme.shapes.large)
        )
    }
}
