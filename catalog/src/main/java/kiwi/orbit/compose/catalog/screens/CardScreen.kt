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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.catalog.AppTheme
import kiwi.orbit.compose.catalog.components.CustomPlaceholder
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.ButtonTextLinkPrimary
import kiwi.orbit.compose.ui.controls.Card
import kiwi.orbit.compose.ui.controls.Icon
import kiwi.orbit.compose.ui.controls.ListChoice
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBar

@Composable
internal fun CardScreen(
    onNavigateUp: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Card") },
                onNavigateUp = onNavigateUp,
            )
        },
        backgroundColor = OrbitTheme.colors.surface.subtle,
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding),
        ) {
            CardScreenInner()
        }
    }
}

@Composable
private fun CardScreenInner() {
    Column(
        modifier = Modifier.padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Card {
            CustomPlaceholder()
        }
        Card(
            title = { Text("Card title") },
            action = { ButtonTextLinkPrimary("Action", onClick = {}) },
        ) {
            CustomPlaceholder()
        }
        Card(
            title = { Text("Card title", style = OrbitTheme.typography.title1) },
            action = { ButtonTextLinkPrimary("Action", onClick = {}) },
        ) {
            CustomPlaceholder()
        }
        Card(
            title = { Text("Card title") },
            contentPadding = PaddingValues(vertical = 12.dp),
        ) {
            Column {
                ListChoice(
                    onClick = {},
                    icon = { Icon(Icons.AirplaneTakeoff, contentDescription = null) },
                    trailingIcon = { Icon(Icons.ChevronForward, contentDescription = null) },
                ) {
                    Text("Takeoff")
                }
                ListChoice(
                    onClick = {},
                    icon = { Icon(Icons.AirplaneLanding, contentDescription = null) },
                    trailingIcon = { Icon(Icons.ChevronForward, contentDescription = null) },
                ) {
                    Text("Landing")
                }
            }
        }
    }
}

@Preview
@Composable
private fun CardScreenPreview() {
    AppTheme {
        CardScreen(onNavigateUp = {})
    }
}
