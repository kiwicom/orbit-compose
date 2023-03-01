package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kiwi.orbit.compose.icons.ColoredIcons
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.Icon
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBar
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.utils.plus
import kotlin.reflect.full.memberProperties

@Composable
internal fun IconsScreen(onNavigateUp: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Icons") },
                onNavigateUp = onNavigateUp,
            )
        },
    ) { contentPadding: PaddingValues ->
        IconsScreenInner(contentPadding)
    }
}

@Composable
private fun IconsScreenInner(contentPadding: PaddingValues) {
    val icons: List<Pair<String, Painter>> = Icons::class.memberProperties.map {
        it.name to (it.getter.call(Icons, currentComposer, 0) as Painter)
    }
    val coloredIcons: List<Pair<String, Painter>> = ColoredIcons::class.memberProperties.map {
        it.name to (it.getter.call(ColoredIcons, currentComposer, 0) as Painter)
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(120.dp),
        contentPadding = contentPadding + PaddingValues(8.dp),
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Column(Modifier.padding(horizontal = 4.dp, vertical = 8.dp)) {
                Text("Monochromatic", style = OrbitTheme.typography.title4)
                Text(
                    text = "Available through Icon.* object",
                    emphasis = ContentEmphasis.Minor,
                    style = OrbitTheme.typography.bodySmall,
                )
            }
        }
        items(icons) { (name, icon) ->
            Icon({ Icon(painter = icon, contentDescription = name) }, name)
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            Column(Modifier.padding(horizontal = 4.dp, vertical = 8.dp)) {
                Text("Colored", style = OrbitTheme.typography.title4)
                Text(
                    text = "Available through ColoredIcon.* object",
                    emphasis = ContentEmphasis.Minor,
                    style = OrbitTheme.typography.bodySmall,
                )
            }
        }
        items(coloredIcons) { (name, icon) ->
            Icon({ Image(painter = icon, contentDescription = name) }, name)
        }
    }
}

@Composable
private fun Icon(icon: @Composable () -> Unit, name: String) {
    Column(
        modifier = Modifier
            .padding(4.dp)
            .border(1.dp, OrbitTheme.colors.surface.normal, OrbitTheme.shapes.normal)
            .padding(vertical = 8.dp, horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        icon()
        Text(
            name,
            Modifier.padding(top = 4.dp),
            fontSize = 10.sp,
            emphasis = ContentEmphasis.Minor,
            textAlign = TextAlign.Center,
        )
    }
}
