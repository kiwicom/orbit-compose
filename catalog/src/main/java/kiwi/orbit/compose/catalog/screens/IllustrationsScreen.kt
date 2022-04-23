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
import androidx.compose.runtime.currentComposer
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.catalog.Screen
import kiwi.orbit.compose.illustrations.Illustrations
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.Card
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.utils.plus
import kotlin.reflect.full.memberProperties

@Composable
fun IllustrationsScreen(onNavigateUp: () -> Unit) {
    Screen(
        title = "Illustrations",
        onNavigateUp = onNavigateUp,
    ) { contentPadding ->
        IllustrationsScreenInner(contentPadding)
    }
}

@Composable
fun IllustrationsScreenInner(contentPadding: PaddingValues) {
    val illustrations: List<Pair<String, Painter>> = Illustrations::class.memberProperties.map {
        it.name to (it.getter.call(Illustrations, currentComposer, 0) as Painter)
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(256.dp),
        contentPadding = contentPadding + PaddingValues(8.dp),
    ) {
        items(illustrations) { (name, icon) ->
            Card(Modifier.padding(8.dp)) {
                Column {
                    Text(
                        name,
                        Modifier.padding(top = 4.dp, start = 6.dp),
                        style = OrbitTheme.typography.bodyNormal,
                        textAlign = TextAlign.Center,
                    )
                    Image(
                        painter = icon,
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        contentDescription = name
                    )
                }
            }
        }
    }
}
