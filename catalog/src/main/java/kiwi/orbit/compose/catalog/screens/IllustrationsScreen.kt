package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.catalog.semantics.SubScreenSemantics
import kiwi.orbit.compose.illustrations.IllustrationName
import kiwi.orbit.compose.illustrations.painter
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.SurfaceCard
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBar
import kiwi.orbit.compose.ui.utils.plus

@Composable
internal fun IllustrationsScreen(onNavigateUp: () -> Unit) {
    Scaffold(
        modifier = Modifier.testTag(SubScreenSemantics.Tag),
        topBar = {
            TopAppBar(
                title = { Text("Illustrations") },
                onNavigateUp = onNavigateUp,
            )
        },
    ) { contentPadding: PaddingValues ->
        IllustrationsScreenInner(contentPadding)
    }
}

@Composable
private fun IllustrationsScreenInner(contentPadding: PaddingValues) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(256.dp),
        contentPadding = contentPadding + PaddingValues(8.dp),
    ) {
        items(IllustrationName.values()) { illustration ->
            SurfaceCard(Modifier.padding(8.dp)) {
                Column {
                    Text(
                        illustration.name,
                        Modifier.padding(top = 4.dp, start = 6.dp),
                        style = OrbitTheme.typography.bodyNormal,
                        textAlign = TextAlign.Center,
                    )
                    Image(
                        painter = illustration.painter(),
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        contentDescription = illustration.name,
                    )
                }
            }
        }
    }
}
