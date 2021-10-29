package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import kiwi.orbit.compose.catalog.Screen
import kiwi.orbit.compose.ui.controls.Separator
import kiwi.orbit.compose.ui.controls.Switch

@Composable
fun SwitchScreen(onUpClick: () -> Unit) {
    Screen(
        title = "Switch",
        onUpClick = onUpClick,
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(it)
        ) {
            SwitchScreenInner()
        }
    }
}

@Preview
@Composable
private fun SwitchScreenInner() {
    Column(Modifier.padding(16.dp)) {
        var value by remember { mutableStateOf(true) }
        Switch(checked = value, onCheckedChange = { value = it })

        Spacer(Modifier.height(8.dp))

        Switch(checked = !value, onCheckedChange = { value = !it })

        Spacer(Modifier.height(16.dp))
        Separator()
        Spacer(Modifier.height(16.dp))

        Switch(checked = true, onCheckedChange = {}, enabled = false)

        Spacer(Modifier.height(8.dp))

        Switch(checked = false, onCheckedChange = {}, enabled = false)
    }
}
