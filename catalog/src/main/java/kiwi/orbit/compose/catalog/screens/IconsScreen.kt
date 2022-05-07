package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kiwi.orbit.compose.catalog.Screen
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.Icon
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.utils.plus
import kotlin.reflect.full.memberProperties

@Composable
fun IconsScreen(onNavigateUp: () -> Unit) {
    Screen(
        title = "Icons",
        onNavigateUp = onNavigateUp,
    ) { contentPadding ->
        IconsScreenInner(contentPadding)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IconsScreenInner(contentPadding: PaddingValues) {
    val icons: List<Pair<String, Painter>> = Icons::class.memberProperties.map {
        it.name to (it.getter.call(Icons, currentComposer, 0) as Painter)
    }

    LazyVerticalGrid(
        cells = GridCells.Adaptive(120.dp),
        contentPadding = contentPadding + PaddingValues(8.dp),
    ) {
        items(icons) { (name, icon) ->
            Column(
                modifier = Modifier
                    .padding(4.dp)
                    .border(1.dp, OrbitTheme.colors.surface.strong, OrbitTheme.shapes.normal)
                    .padding(vertical = 8.dp, horizontal = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(painter = icon, contentDescription = name)
                Text(
                    name,
                    Modifier.padding(top = 4.dp),
                    fontSize = 10.sp,
                    emphasis = ContentEmphasis.Minor,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
