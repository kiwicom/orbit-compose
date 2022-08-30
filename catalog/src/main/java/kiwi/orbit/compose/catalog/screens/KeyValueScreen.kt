package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.controls.KeyValue
import kiwi.orbit.compose.ui.controls.KeyValueLarge
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBar

@Composable
internal fun KeyValueScreen(onNavigateUp: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("KeyValue") },
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
            KeyValueScreenInner()
        }
    }
}

@Preview
@Composable
private fun KeyValueScreenInner() {
    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        KeyValue(
            key = "Key",
            value = "Value",
        )
        Spacer(modifier = Modifier.height(16.dp))
        KeyValueLarge(
            key = "Large key",
            value = "Large value",
        )
    }
}
