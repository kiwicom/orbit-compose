package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kiwi.orbit.compose.catalog.semantics.TopAppBarScreenSemantics
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.Icon
import kiwi.orbit.compose.ui.controls.IconButton
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Tab
import kiwi.orbit.compose.ui.controls.TabRow
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBarLarge
import kiwi.orbit.compose.ui.controls.TopAppBarScrollBehavior
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kotlinx.coroutines.delay

@Composable
internal fun TopAppBarLargeScreen(
    onNavigateUp: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.testTag(TopAppBarScreenSemantics.LargeSimpleScreenTag),
        topBar = {
            TopAppBarLarge(
                title = { Text("Simple") },
                onNavigateUp = onNavigateUp,
                actions = {
                    IconButton(onClick = {}) {
                        Icon(painter = Icons.Notification, contentDescription = null)
                    }
                },
            )
        },
        content = { CustomContentPlaceholder(it) },
    )
}

@Composable
internal fun TopAppBarLargeScrollableScreen(
    onNavigateUp: () -> Unit,
) {
    val scrollBehavior = TopAppBarScrollBehavior.exitUntilCollapsed()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarLarge(
                title = { Text("Scrollable") },
                onNavigateUp = onNavigateUp,
                scrollBehavior = scrollBehavior,
                largeElevated = false,
            )
        },
        backgroundColor = OrbitTheme.colors.surface.subtle,
        content = { CustomContentPlaceholder(it) },
    )
}

@Composable
internal fun TopAppBarLargeScrollableElevatedScreen(
    onNavigateUp: () -> Unit,
) {
    val scrollBehavior = TopAppBarScrollBehavior.exitUntilCollapsed()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarLarge(
                title = { Text("Scrollable Elevated") },
                onNavigateUp = onNavigateUp,
                scrollBehavior = scrollBehavior,
                largeElevated = true,
            )
        },
        content = { CustomContentPlaceholder(it) },
    )
}

@Composable
internal fun TopAppBarLargeWithTabsScreen(
    onNavigateUp: () -> Unit,
) {
    val scrollBehavior = TopAppBarScrollBehavior.exitUntilCollapsed()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarLarge(
                title = { Text("With Tabs") },
                onNavigateUp = onNavigateUp,
                extraContent = {
                    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
                    TabRow(selectedTabIndex = selectedTabIndex) {
                        Tab(
                            selected = selectedTabIndex == 0,
                            onClick = { selectedTabIndex = 0 },
                            text = { Text("Normal") },
                        )
                        Tab(
                            selected = selectedTabIndex == 1,
                            onClick = { selectedTabIndex = 1 },
                            text = { Text("Inline") },
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
        content = { CustomContentPlaceholder(it) },
    )
}

@Composable
internal fun TopAppBarLargeCustomContentScreen(
    onNavigateUp: () -> Unit,
) {
    val scrollBehavior = TopAppBarScrollBehavior.exitUntilCollapsed()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarLarge(
                title = {
                    Row(
                        modifier = Modifier.padding(end = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(Modifier.weight(1f)) {
                            Text("Jonathan Archer")
                            Text(
                                text = "j.archer@nx01.starfleet",
                                style = OrbitTheme.typography.bodySmall,
                                emphasis = ContentEmphasis.Minor,
                            )
                        }
                        AsyncImage(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape),
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(ArcherPhoto)
                                .crossfade(true)
                                .build(),
                            contentScale = ContentScale.Crop,
                            contentDescription = null,
                        )
                    }
                },
                largeTitle = {
                    Column {
                        Row {
                            Text("Jonathan Archer", Modifier.weight(1f))
                            AsyncImage(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape),
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(ArcherPhoto)
                                    .crossfade(true)
                                    .build(),
                                contentScale = ContentScale.Crop,
                                contentDescription = null,
                            )
                        }
                        Text(
                            text = "j.archer@nx01.starfleet",
                            style = OrbitTheme.typography.bodyNormal,
                            emphasis = ContentEmphasis.Minor,
                        )
                    }
                },
                onNavigateUp = onNavigateUp,
                largeElevated = false,
                scrollBehavior = scrollBehavior,
            )
        },
        content = { CustomContentPlaceholder(it) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TopAppBarLargePullRefreshScreen(
    onNavigateUp: () -> Unit,
) {
    var i by rememberSaveable { mutableIntStateOf(0) }

    val pullRefreshState = rememberPullToRefreshState()
    if (pullRefreshState.isRefreshing) {
        LaunchedEffect(Unit) {
            delay(1500)
            i += 1
            pullRefreshState.endRefresh()
        }
    }
    val scrollBehavior = TopAppBarScrollBehavior.exitUntilCollapsed()

    Scaffold(
        modifier = Modifier
            .nestedScroll(pullRefreshState.nestedScrollConnection)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarLarge(
                title = { Text("Pull Refresh") },
                onNavigateUp = onNavigateUp,
                scrollBehavior = scrollBehavior,
                largeElevated = true,
            )
        },
        content = {
            Box {
                CustomContentPlaceholder(it, "Custom content $i")
                PullToRefreshContainer(
                    state = pullRefreshState,
                    containerColor = OrbitTheme.colors.surface.main,
                    contentColor = OrbitTheme.colors.primary.normal,
                    modifier = Modifier.align(Alignment.TopCenter),
                )
            }
        },
    )
}

private const val ArcherPhoto = "https://upload.wikimedia.org/wikipedia/commons/2/23/J_Archer.jpg"
