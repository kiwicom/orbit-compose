package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.catalog.SubScreen
import kiwi.orbit.compose.ui.controls.Separator
import kiwi.orbit.compose.ui.controls.Surface
import kiwi.orbit.compose.ui.controls.Switch

@Composable
fun SwitchScreen(onUpClick: () -> Unit) {
    SubScreen(
        title = "Switch",
        onUpClick = onUpClick,
    ) {
        SwitchScreenInner()
    }
}

@Preview
@Composable
fun SwitchScreenInner() {
    Surface {
        Column(
            Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
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
}
