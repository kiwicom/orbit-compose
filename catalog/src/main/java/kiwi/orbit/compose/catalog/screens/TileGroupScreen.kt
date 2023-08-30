package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.catalog.AppTheme
import kiwi.orbit.compose.catalog.components.CustomPlaceholder
import kiwi.orbit.compose.catalog.semantics.SubScreenSemantics
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.Icon
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.Tile
import kiwi.orbit.compose.ui.controls.TileGroup
import kiwi.orbit.compose.ui.controls.TopAppBar

@Composable
internal fun TileGroupScreen(
    onNavigateUp: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.testTag(SubScreenSemantics.Tag),
        topBar = {
            TopAppBar(
                title = { Text("TileGroup") },
                onNavigateUp = onNavigateUp,
            )
        },
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding),
        ) {
            TileGroupScreenInner()
        }
    }
}

@Composable
private fun TileGroupScreenInner() {
    TileGroup(
        modifier = Modifier.padding(16.dp),
    ) {
        Tile(
            title = { Text("Title") },
            onClick = {},
        )
        Tile(
            title = { Text("Title") },
            onClick = {},
            icon = { Icon(Icons.Airplane, contentDescription = null) },
        )
        Tile(
            title = { Text("Title") },
            onClick = {},
            description = { Text("Description") },
        )
        Tile(
            title = { Text("Title") },
            onClick = {},
            icon = { Icon(Icons.Airplane, contentDescription = null) },
            description = { Text("Description") },
        )
        Tile(
            title = { Text("Title") },
            onClick = {},
            description = { Text("Description") },
            trailingContent = {
                Text(
                    text = "Action",
                    modifier = Modifier.align(Alignment.Top),
                    color = OrbitTheme.colors.primary.normal,
                    style = OrbitTheme.typography.bodyNormalMedium,
                )
            },
        )
        Tile(
            onClick = {},
        ) {
            CustomPlaceholder()
        }
    }
}

@Preview
@Composable
private fun TileGroupScreenPreview() {
    AppTheme {
        TileGroupScreen(onNavigateUp = {})
    }
}
