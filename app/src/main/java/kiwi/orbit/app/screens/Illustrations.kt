package kiwi.orbit.app.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.toPaddingValues
import kiwi.orbit.OrbitTheme
import kiwi.orbit.app.SubScreen
import kiwi.orbit.illustrations.Illustrations
import kotlin.reflect.full.memberProperties

@Composable
fun IllustrationsScreen(onUpClick: () -> Unit) {
    SubScreen(
        title = "Illustrations",
        onUpClick = onUpClick,
        withBackground = false,
        scrollable = false,
    ) {
        IllustrationsScreen()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun IllustrationsScreen() {
    val illustrations: List<Pair<String, Painter>> = Illustrations::class.memberProperties.map {
        it.name to (it.getter.call(Illustrations, currentComposer, 0) as Painter)
    }

    LazyVerticalGrid(
        cells = GridCells.Adaptive(256.dp),
        contentPadding = LocalWindowInsets.current.navigationBars.toPaddingValues(
            additionalHorizontal = 8.dp,
            additionalVertical = 8.dp
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
