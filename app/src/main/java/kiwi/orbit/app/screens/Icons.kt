package kiwi.orbit.app.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.toPaddingValues
import kiwi.orbit.OrbitTheme
import kiwi.orbit.app.SubScreen
import kiwi.orbit.icons.Icons
import kotlin.reflect.full.memberProperties

@Composable
fun IconsScreen(onUpClick: () -> Unit) {
    SubScreen(
        title = "Icons",
        onUpClick = onUpClick,
        withBackground = false,
        scrollable = false,
    ) {
        IconsScreenInner()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun IconsScreenInner() {
    val icons: List<Pair<String, Painter>> = Icons::class.memberProperties.map {
        it.name to (it.getter.call(Icons, currentComposer, 0) as Painter)
    }

    LazyVerticalGrid(
        cells = GridCells.Adaptive(96.dp),
        contentPadding = LocalWindowInsets.current.navigationBars.toPaddingValues(
            additionalHorizontal = 8.dp,
            additionalVertical = 8.dp
        )
    ) {
        items(icons) { (name, icon) ->
            Column(Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(painter = icon, contentDescription = name)
                Text(
                    name,
                    Modifier.padding(top = 4.dp),
                    style = OrbitTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
