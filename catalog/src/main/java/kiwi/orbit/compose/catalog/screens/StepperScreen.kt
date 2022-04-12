package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.catalog.Screen
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.Stepper
import kiwi.orbit.compose.ui.controls.Text

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
        var valueFirst by remember { mutableStateOf(0) }
        var valueSecond by remember { mutableStateOf(0) }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "How many pets to you have?",
                style = OrbitTheme.typography.title5,
            )

            Stepper(
                value = valueFirst,
                onValueChange = { valueFirst = it },
                maxValue = 10,
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text(
                modifier = Modifier.weight(1f),
                style = OrbitTheme.typography.title5,
                text = "How many pets would you like to have?"
            )

            Stepper(
                value = valueSecond,
                active = false,
                onValueChange = { valueSecond = it },
                maxValue = 10,
            )
        }
    }
}
