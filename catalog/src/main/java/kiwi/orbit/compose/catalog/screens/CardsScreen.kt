package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.catalog.SubScreen
import kiwi.orbit.compose.illustrations.Illustrations
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.Card
import kiwi.orbit.compose.ui.controls.Separator
import kiwi.orbit.compose.ui.controls.Text

@Composable
fun CardsScreen(onUpClick: () -> Unit) {
    SubScreen(
        title = "Cards / Tiles",
        onUpClick = onUpClick,
        withBackground = false,
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            CardScreenInner()
        }
    }
}

@Composable
private fun CardScreenInner() {
    var state by remember { mutableStateOf(true) }

    Card(Modifier.fillMaxWidth()) {
        Box(Modifier.padding(16.dp)) {
            Text("Basic card")
        }
    }

    Separator()
    Text("Selectable cards")

    Card(
        onClick = { state = true },
        border = if (state) {
            BorderStroke(2.dp, OrbitTheme.colors.interactive.main)
        } else null,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Image(Illustrations.AirportShuttle, contentDescription = null)
    }

    Card(
        onClick = { state = false },
        border = if (!state) {
            BorderStroke(2.dp, OrbitTheme.colors.interactive.main)
        } else null,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Image(Illustrations.AirHelp, contentDescription = null)
    }
}
