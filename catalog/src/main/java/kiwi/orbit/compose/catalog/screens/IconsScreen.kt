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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kiwi.orbit.compose.catalog.semantics.SubScreenSemantics
import kiwi.orbit.compose.icons.ColoredIconName
import kiwi.orbit.compose.icons.IconName
import kiwi.orbit.compose.icons.painter
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.Icon
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBar
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.utils.plus

@Composable
internal fun IconsScreen(onNavigateUp: () -> Unit) {
    Scaffold(
        modifier = Modifier.testTag(SubScreenSemantics.Tag),
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
        items(IconName.values()) { icon ->
            Icon({ Icon(icon.painter(), contentDescription = icon.name) }, icon.name)
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
        items(ColoredIconName.values()) { icon ->
            Icon({ Image(icon.painter(), contentDescription = icon.name) }, icon.name)
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
