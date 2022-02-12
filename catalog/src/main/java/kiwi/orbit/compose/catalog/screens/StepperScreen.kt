package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import kiwi.orbit.compose.ui.controls.Stepper

@Composable
fun StepperScreen(onNavigateUp: () -> Unit) {
    Screen(
        title = "Stepper",
        onNavigateUp = onNavigateUp,
    ) { contentPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding)
        ) {
            StepperScreenInner()
        }
    }
}

@Preview
@Composable
private fun StepperScreenInner() {
    Column(Modifier.padding(16.dp)) {
        var value by remember { mutableStateOf(1) }
        Stepper(value = value, onValueChange = { value = it }, maxValue = 8)
    }
}
