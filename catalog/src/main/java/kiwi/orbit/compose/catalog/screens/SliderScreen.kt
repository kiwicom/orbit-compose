package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Slider
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBar

@Composable
internal fun SliderScreen(onNavigateUp: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Slider") },
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
            SliderScreenInner()
        }
    }
}

@Composable
private fun SliderScreenInner() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text("Slider 1", style = OrbitTheme.typography.title4)
            var value1 by rememberSaveable { mutableStateOf(0.5f) }
            Slider(
                value = value1,
                onValueChange = { value1 = it },
                valueLabel = { Text(it.toString()) },
                startLabel = { Text("0") },
                endLabel = { Text("1") },
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text("Slider 2", style = OrbitTheme.typography.title4)
            var value2 by rememberSaveable { mutableStateOf(0.5f) }
            Slider(
                value = value2,
                onValueChange = { value2 = it },
                valueLabel = { Text(it.toString()) },
                startLabel = { Text("Start") },
                endLabel = { Text("End") },
                steps = 3,
            )
        }
    }
}
