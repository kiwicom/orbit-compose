package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.catalog.SubScreen
import kiwi.orbit.compose.ui.controls.Radio
import kiwi.orbit.compose.ui.controls.RadioLayout
import kiwi.orbit.compose.ui.controls.Surface
import kiwi.orbit.compose.ui.controls.Text

@Composable
fun RadioScreen(onUpClick: () -> Unit) {
    SubScreen(
        title = "Radio",
        onUpClick = onUpClick,
    ) {
        Surface {
            Column(
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                RadioScreenInner()
            }
        }
    }
}

@Preview
@Composable
fun RadioScreenInner() {
    Column {
        Row {
            var selected by remember { mutableStateOf(true) }
            Radio(selected = selected, onClick = { selected = true })

            Spacer(Modifier.size(32.dp))

            Radio(selected = !selected, onClick = { selected = false })

            Spacer(Modifier.size(32.dp))

            Radio(selected = true, enabled = false, onClick = {})

            Spacer(Modifier.size(32.dp))

            Radio(selected = false, enabled = false, onClick = {})
        }

        Spacer(Modifier.size(32.dp))

        var selected by remember { mutableStateOf(0) }
        RadioLayout(
            selected = selected == 0,
            onClick = { selected = 0 },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Star Trek")
        }
        Spacer(Modifier.size(8.dp))
        RadioLayout(
            selected = selected == 1,
            onClick = { selected = 1 },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Star Wars")
        }
        Spacer(Modifier.size(8.dp))
        RadioLayout(
            selected = selected == 2,
            onClick = { selected = 2 },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Star Gate")
        }

        Spacer(Modifier.size(32.dp))

        var selected2 by remember { mutableStateOf(0) }
        RadioLayout(
            selected = selected2 == 0,
            onClick = { selected2 = 0 },
            modifier = Modifier.fillMaxWidth(),
            description = { Text("Live Long and Prosper.") }
        ) {
            Text("Star Trek")
        }
        Spacer(Modifier.size(8.dp))
        RadioLayout(
            selected = selected2 == 1,
            onClick = { selected2 = 1 },
            modifier = Modifier.fillMaxWidth(),
            description = { Text("May the Force be with you.") },
        ) {
            Text("Star Wars")
        }
        Spacer(Modifier.size(8.dp))
        RadioLayout(
            selected = selected2 == 2,
            onClick = { selected2 = 2 },
            modifier = Modifier.fillMaxWidth(),
            description = { Text("Indeed.") },
        ) {
            Text("Star Gate")
        }

        Spacer(Modifier.size(32.dp))

        var selected3 by remember { mutableStateOf(0) }
        RadioLayout(
            selected = selected3 == 0,
            onClick = { selected3 = 0 },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            description = { Text("Live Long and Prosper.") }
        ) {
            Text("Star Trek")
        }
        Spacer(Modifier.size(8.dp))
        RadioLayout(
            selected = selected3 == 1,
            onClick = { selected3 = 1 },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            description = { Text("May the Force be with you.") },
        ) {
            Text("Star Wars")
        }
        Spacer(Modifier.size(8.dp))
        RadioLayout(
            selected = selected3 == 2,
            onClick = { selected3 = 2 },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            description = { Text("Indeed.") },
        ) {
            Text("Star Gate")
        }
    }
}
