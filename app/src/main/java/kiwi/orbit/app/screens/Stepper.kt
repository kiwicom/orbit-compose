package kiwi.orbit.app.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import kiwi.orbit.controls.Stepper

@Preview
@Composable
fun StepperScreen() {
    Surface {
        Column(
            Modifier.padding(16.dp).fillMaxWidth()
        ) {
            var value by remember { mutableStateOf(1) }
            Stepper(value = value, onValueChange = { value = it }, maxValue = 8)
        }
    }
}
