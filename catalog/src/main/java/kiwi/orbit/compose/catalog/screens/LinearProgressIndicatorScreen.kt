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
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.catalog.Screen
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.ButtonCritical
import kiwi.orbit.compose.ui.controls.ButtonPrimary
import kiwi.orbit.compose.ui.controls.LinearProgressIndicator
import kiwi.orbit.compose.ui.controls.Text
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun LinearProgressIndicatorScreen(onNavigateUp: () -> Unit) {
    Screen(
        title = "Linear Progress Indicator",
        onNavigateUp = onNavigateUp,
    ) { contentPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding)
        ) {
            LinearProgressIndicatorScreenInner()
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun LinearProgressIndicatorScreenInner() {
    OrbitTheme {
        val progressFlow = remember { MutableStateFlow(0.5f) }
        val progress by progressFlow.collectAsState()
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            SideEffect {
                println("progress: $progress")
            }

            LinearProgressIndicator(progress)

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                ButtonPrimary(
                    onClick = {
                        if (progressFlow.value < 1f) {
                            progressFlow.value += 0.1f
                        }
                    },
                    modifier = Modifier
                        .weight(0.5f)
                        .alpha(if (progress < 1f) 1f else 0.3f),
                ) {
                    Text("Increase")
                }

                ButtonCritical(
                    onClick = {
                        if (progressFlow.value > 0.1f) {
                            progressFlow.value -= 0.1f
                        }
                    },
                    modifier = Modifier
                        .weight(0.5f)
                        .alpha(if (progress > 0.1f) 1f else 0.3f),
                ) {
                    Text("Decrease")
                }
            }
        }
    }
}
