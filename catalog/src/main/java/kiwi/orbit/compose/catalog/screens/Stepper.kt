package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import kiwi.orbit.compose.ui.controls.Stepper
import kiwi.orbit.compose.ui.controls.Surface

@Composable
fun StepperScreen(onUpClick: () -> Unit) {
    SubScreen(
        title = "Stepper",
        onUpClick = onUpClick,
    ) {
        StepperScreenInner()
    }
}

@Preview
@Composable
fun StepperScreenInner() {
    Surface {
        Column(
            Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            var value by remember { mutableStateOf(1) }
            Stepper(value = value, onValueChange = { value = it }, maxValue = 8)
        }
    }
}
