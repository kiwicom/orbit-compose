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
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.ButtonSecondary
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBar

@Composable
fun TopAppBarScreen(
    demoId: Int,
    onNavigateUp: () -> Unit,
    onShowTopAppBarDemo: (Int) -> Unit,
) {
    when (demoId) {
        1 -> TopAppBarNormalScreen(onNavigateUp)
        2 -> TopAppBarNormalScrollableScreen(onNavigateUp)
        3 -> TopAppBarNormalWithTabsScreen(onNavigateUp)
        4 -> TopAppBarNormalWithFiltersScreen(onNavigateUp)
        10 -> TopAppBarLargeScreen(onNavigateUp)
        11 -> TopAppBarLargeScrollableScreen(onNavigateUp)
        12 -> TopAppBarLargeScrollableElevatedScreen(onNavigateUp)
        13 -> TopAppBarLargeCustomContentScreen(onNavigateUp)
        else -> TopAppBarScreenInner(onNavigateUp, onShowTopAppBarDemo)
    }
}

@Composable
private fun TopAppBarScreenInner(
    onNavigateUp: () -> Unit,
    onSelect: (Int) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("TopAppBar") }, onNavigateUp = onNavigateUp)
        }
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
            ButtonSecondary(onClick = { onSelect(1) }) { Text("Simple") }
            ButtonSecondary(onClick = { onSelect(2) }) { Text("Scrollable") }
            ButtonSecondary(onClick = { onSelect(3) }) { Text("With Tabs") }
            ButtonSecondary(onClick = { onSelect(4) }) { Text("With Filters") }

            Spacer(Modifier.size(16.dp))
            Text("Large", style = OrbitTheme.typography.title3)
            ButtonSecondary(onClick = { onSelect(10) }) { Text("Simple") }
            ButtonSecondary(onClick = { onSelect(11) }) { Text("Scrollable") }
            ButtonSecondary(onClick = { onSelect(12) }) { Text("Scrollable Elevated") }
            ButtonSecondary(onClick = { onSelect(13) }) { Text("With Custom Content") }
        }
    }
}
