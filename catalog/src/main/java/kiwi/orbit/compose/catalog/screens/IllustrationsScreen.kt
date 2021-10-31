package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kiwi.orbit.compose.catalog.Screen
import kiwi.orbit.compose.illustrations.Illustrations
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.Card
import kiwi.orbit.compose.ui.controls.Text
import kotlin.reflect.full.memberProperties

@Destination
@Composable
fun IllustrationsScreen(navigator: DestinationsNavigator) {
    Screen(
        title = "Illustrations",
        onUpClick = navigator::navigateUp,
        withBackground = false,
    ) {
        IllustrationsScreenInner()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun IllustrationsScreenInner() {
    val illustrations: List<Pair<String, Painter>> = Illustrations::class.memberProperties.map {
        it.name to (it.getter.call(Illustrations, currentComposer, 0) as Painter)
    }

    LazyVerticalGrid(
        cells = GridCells.Adaptive(256.dp),
        contentPadding = rememberInsetsPaddingValues(
            insets = LocalWindowInsets.current.navigationBars,
            additionalStart = 8.dp,
            additionalTop = 8.dp,
            additionalEnd = 8.dp,
            additionalBottom = 8.dp
        )
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
