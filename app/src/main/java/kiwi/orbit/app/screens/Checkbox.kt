package kiwi.orbit.app.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.app.SubScreen
import kiwi.orbit.controls.Checkbox
import kiwi.orbit.controls.CheckboxLayout

@Composable
fun CheckboxScreen(onUpClick: () -> Unit) {
    SubScreen(
        title = "Checkbox Button",
        onUpClick = onUpClick,
    ) {
        Surface {
            Column(
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                CheckboxScreenInner()
            }
        }
    }
}

@Preview
@Composable
fun CheckboxScreenInner() {
    Column {
        Row {
            var checkbox1 by remember { mutableStateOf(true) }
            Checkbox(checked = checkbox1, onCheckedChange = { checkbox1 = !checkbox1 })

            Spacer(Modifier.size(32.dp))

            var checkbox2 by remember { mutableStateOf(false) }
            Checkbox(checked = checkbox2, onCheckedChange = { checkbox2 = !checkbox2 })

            Spacer(Modifier.size(32.dp))

            Checkbox(checked = true, enabled = false, onCheckedChange = {})

            Spacer(Modifier.size(32.dp))

            Checkbox(checked = false, enabled = false, onCheckedChange = {})
        }

        Spacer(Modifier.size(32.dp))

        var checkbox1 by remember { mutableStateOf(true) }
        CheckboxLayout(
            checked = checkbox1,
            onCheckedChange = { checkbox1 = !checkbox1 },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Star Trek")
        }
        Spacer(Modifier.size(8.dp))
        var checkbox2 by remember { mutableStateOf(false) }
        CheckboxLayout(
            checked = checkbox2,
            onCheckedChange = { checkbox2 = !checkbox2 },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Star Wars")
        }
        Spacer(Modifier.size(8.dp))
        var checkbox3 by remember { mutableStateOf(false) }
        CheckboxLayout(
            checked = checkbox3,
            onCheckedChange = { checkbox3 = !checkbox3 },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Star Gate")
        }

        Spacer(Modifier.size(32.dp))

        var checkbox4 by remember { mutableStateOf(true) }
        CheckboxLayout(
            checked = checkbox4,
            onCheckedChange = { checkbox4 = !checkbox4 },
            modifier = Modifier.fillMaxWidth(),
            description = { Text("Live Long and Prosper.") }
        ) {
            Text("Star Trek")
        }
        Spacer(Modifier.size(8.dp))
        var checkbox5 by remember { mutableStateOf(false) }
        CheckboxLayout(
            checked = checkbox5,
            onCheckedChange = { checkbox5 = !checkbox5 },
            modifier = Modifier.fillMaxWidth(),
            description = { Text("May the Force be with you.") },
        ) {
            Text("Star Wars")
        }
        Spacer(Modifier.size(8.dp))
        var checkbox6 by remember { mutableStateOf(false) }
        CheckboxLayout(
            checked = checkbox6,
            onCheckedChange = { checkbox6 = !checkbox6 },
            modifier = Modifier.fillMaxWidth(),
            description = { Text("Indeed.") },
        ) {
            Text("Star Gate")
        }

        Spacer(Modifier.size(32.dp))

        CheckboxLayout(
            checked = true,
            onCheckedChange = { },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            description = { Text("Live Long and Prosper.") }
        ) {
            Text("Star Trek")
        }
        Spacer(Modifier.size(8.dp))
        CheckboxLayout(
            checked = false,
            onCheckedChange = { },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            description = { Text("May the Force be with you.") },
        ) {
            Text("Star Wars")
        }
        Spacer(Modifier.size(8.dp))
        CheckboxLayout(
            checked = false,
            onCheckedChange = { },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            description = { Text("Indeed.") },
        ) {
            Text("Star Gate")
        }
    }
}
