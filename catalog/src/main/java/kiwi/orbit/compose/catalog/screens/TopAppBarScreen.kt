package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.kiwi.navigationcompose.typed.Destination
import com.kiwi.navigationcompose.typed.composable
import com.kiwi.navigationcompose.typed.createRoutePattern
import com.kiwi.navigationcompose.typed.navigate
import com.kiwi.navigationcompose.typed.navigation
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.ButtonSecondary
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBar
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

sealed interface TopAppBarDestination : Destination {
    @Serializable
    object Home : TopAppBarDestination

    @Serializable
    object Normal : TopAppBarDestination

    @Serializable
    object NormalScrollable : TopAppBarDestination

    @Serializable
    object NormalWithTabs : TopAppBarDestination

    @Serializable
    object NormalWithFilters : TopAppBarDestination

    @Serializable
    object Large : TopAppBarDestination

    @Serializable
    object LargeScrollable : TopAppBarDestination

    @Serializable
    object LargeScrollableElevated : TopAppBarDestination

    @Serializable
    object LargeCustomContent : TopAppBarDestination

    @Serializable
    object LargePullRefresh : TopAppBarDestination
}

@ExperimentalSerializationApi
internal inline fun <reified T : Destination> NavGraphBuilder.topAppBarNavigation(
    navController: NavController,
) {
    navigation<T>(
        startDestination = createRoutePattern<TopAppBarDestination.Home>(),
    ) {
        composable<TopAppBarDestination.Home> {
            TopAppBarScreenInner(navController::navigateUp, navController::navigate)
        }
        composable<TopAppBarDestination.Normal> {
            TopAppBarNormalScreen(navController::navigateUp)
        }
        composable<TopAppBarDestination.NormalScrollable> {
            TopAppBarNormalScrollableScreen(navController::navigateUp)
        }
        composable<TopAppBarDestination.NormalWithTabs> {
            TopAppBarNormalWithTabsScreen(navController::navigateUp)
        }
        composable<TopAppBarDestination.NormalWithFilters> {
            TopAppBarNormalWithFiltersScreen(navController::navigateUp)
        }
        composable<TopAppBarDestination.Large> {
            TopAppBarLargeScreen(navController::navigateUp)
        }
        composable<TopAppBarDestination.LargeScrollable> {
            TopAppBarLargeScrollableScreen(navController::navigateUp)
        }
        composable<TopAppBarDestination.LargeScrollableElevated> {
            TopAppBarLargeScrollableElevatedScreen(navController::navigateUp)
        }
        composable<TopAppBarDestination.LargeCustomContent> {
            TopAppBarLargeCustomContentScreen(navController::navigateUp)
        }
        composable<TopAppBarDestination.LargePullRefresh> {
            TopAppBarLargePullRefreshScreen(navController::navigateUp)
        }
    }
}

@ExperimentalSerializationApi
@Composable
internal fun TopAppBarScreenInner(
    onNavigateUp: () -> Unit,
    onSelect: (Destination) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("TopAppBar") }, onNavigateUp = onNavigateUp)
        },
    ) { contentPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .wrapContentHeight()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("Normal", style = OrbitTheme.typography.title3)
            ButtonSecondary(
                onClick = { onSelect(TopAppBarDestination.Normal) },
            ) {
                Text("Simple")
            }
            ButtonSecondary(
                onClick = { onSelect(TopAppBarDestination.NormalScrollable) },
            ) {
                Text("Scrollable")
            }
            ButtonSecondary(
                onClick = { onSelect(TopAppBarDestination.NormalWithTabs) },
            ) {
                Text("With Tabs")
            }
            ButtonSecondary(
                onClick = { onSelect(TopAppBarDestination.NormalWithFilters) },
            ) {
                Text("With Filters")
            }

            Spacer(Modifier.size(16.dp))
            Text("Large", style = OrbitTheme.typography.title3)
            ButtonSecondary(
                onClick = { onSelect(TopAppBarDestination.Large) },
            ) {
                Text("Simple")
            }
            ButtonSecondary(
                onClick = { onSelect(TopAppBarDestination.LargeScrollable) },
            ) {
                Text("Scrollable")
            }
            ButtonSecondary(
                onClick = { onSelect(TopAppBarDestination.LargeScrollableElevated) },
            ) {
                Text("Scrollable Elevated")
            }
            ButtonSecondary(
                onClick = { onSelect(TopAppBarDestination.LargeCustomContent) },
            ) {
                Text("With Custom Content")
            }
            ButtonSecondary(
                onClick = { onSelect(TopAppBarDestination.LargePullRefresh) },
            ) {
                Text("With Pull Refresh")
            }
        }
    }
}
