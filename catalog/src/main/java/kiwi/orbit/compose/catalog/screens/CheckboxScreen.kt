package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.controls.Checkbox
import kiwi.orbit.compose.ui.controls.CheckboxField
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBar

@Composable
fun CheckboxScreen(onNavigateUp: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Checkbox Button") },
                onNavigateUp = onNavigateUp,
            )
        },
    ) { contentPadding: PaddingValues ->
        Box(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding),
        ) {
            CheckboxScreenInner()
        }
    }
}

@Preview
@Composable
private fun CheckboxScreenInner() {
    Column {
        Row(Modifier.padding(16.dp)) {
            var checkbox1 by remember { mutableStateOf(true) }
            Checkbox(checked = checkbox1, onCheckedChange = { checkbox1 = !checkbox1 })

            Spacer(Modifier.size(32.dp))

            var checkbox2 by remember { mutableStateOf(false) }
            Checkbox(checked = checkbox2, onCheckedChange = { checkbox2 = !checkbox2 })

            Spacer(Modifier.size(32.dp))

            var checkbox3 by remember { mutableStateOf(false) }
            Checkbox(checked = checkbox3, isError = true, onCheckedChange = { checkbox3 = !checkbox3 })

            Spacer(Modifier.size(32.dp))

            Checkbox(checked = true, enabled = false, onCheckedChange = {})

            Spacer(Modifier.size(32.dp))

            Checkbox(checked = false, enabled = false, onCheckedChange = {})

            Spacer(Modifier.size(32.dp))

            Checkbox(checked = false, enabled = false, isError = true, onCheckedChange = {})
        }

        Spacer(Modifier.size(32.dp))

        var checkbox1 by remember { mutableStateOf(true) }
        CheckboxField(
            checked = checkbox1,
            onCheckedChange = { checkbox1 = !checkbox1 },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Star Trek")
        }
        var checkbox2 by remember { mutableStateOf(false) }
        CheckboxField(
            checked = checkbox2,
            onCheckedChange = { checkbox2 = !checkbox2 },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Star Wars")
        }
        var checkbox3 by remember { mutableStateOf(false) }
        CheckboxField(
            checked = checkbox3,
            onCheckedChange = { checkbox3 = !checkbox3 },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Stargate")
        }

        Spacer(Modifier.size(32.dp))

        var checkbox4 by remember { mutableStateOf(true) }
        CheckboxField(
            checked = checkbox4,
            onCheckedChange = { checkbox4 = !checkbox4 },
            modifier = Modifier.fillMaxWidth(),
            description = { Text("Live Long and Prosper.") },
        ) {
            Text("Star Trek")
        }
        var checkbox5 by remember { mutableStateOf(false) }
        CheckboxField(
            checked = checkbox5,
            onCheckedChange = { checkbox5 = !checkbox5 },
            modifier = Modifier.fillMaxWidth(),
            isError = true,
            description = { Text("May the Force be with you.") },
        ) {
            Text("Star Wars")
        }
        var checkbox6 by remember { mutableStateOf(false) }
        CheckboxField(
            checked = checkbox6,
            onCheckedChange = { checkbox6 = !checkbox6 },
            modifier = Modifier.fillMaxWidth(),
            description = { Text("Indeed.") },
        ) {
            Text("Stargate")
        }

        Spacer(Modifier.size(32.dp))

        CheckboxField(
            checked = true,
            onCheckedChange = { },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            description = { Text("Live Long and Prosper.") },
        ) {
            Text("Star Trek")
        }
        CheckboxField(
            checked = false,
            onCheckedChange = { },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            description = { Text("May the Force be with you.") },
        ) {
            Text("Star Wars")
        }
        CheckboxField(
            checked = false,
            onCheckedChange = { },
            modifier = Modifier.fillMaxWidth(),
            enabled = false,
            description = { Text("Indeed.") },
        ) {
            Text("Stargate")
        }
    }
}
