package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.catalog.semantics.SubScreenSemantics
import kiwi.orbit.compose.illustrations.Illustrations
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.ButtonPrimary
import kiwi.orbit.compose.ui.controls.ButtonPrimarySubtle
import kiwi.orbit.compose.ui.controls.EmptyState
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Separator
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBar

@Composable
internal fun EmptyStateScreen(onNavigateUp: () -> Unit) {
    Scaffold(
        modifier = Modifier.testTag(SubScreenSemantics.Tag),
        topBar = {
            TopAppBar(
                title = { Text("EmptyState") },
                onNavigateUp = onNavigateUp,
            )
        },
    ) { contentPadding: PaddingValues ->
        EmptyStateScreenInner(contentPadding)
    }
}

@Composable
private fun EmptyStateScreenInner(contentPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        EmptyState(
            illustration = { Image(Illustrations.NoResults, contentDescription = null) },
            title = { Text(text = "Sorry, we couldn't find that connection. And let's make the title a bit longer, so we can see the text wrapping.") },
        )
        Separator(Modifier.padding(vertical = 8.dp))
        EmptyState(
            illustration = { Image(Illustrations.NoResults, contentDescription = null) },
            title = { Text(text = "Sorry, we couldn't find that connection.") },
            description = { Text(text = "Try changing up your search a bit. We'll try harder next time.") },
        )
        Separator(Modifier.padding(vertical = 8.dp))
        EmptyState(
            illustration = { Image(Illustrations.NoResults, contentDescription = null) },
            title = { Text(text = "Sorry, we couldn't find that connection.") },
            action = {
                ButtonPrimary(onClick = { }) {
                    Text(text = "Adjust search")
                }
            },
        )
        Separator(Modifier.padding(vertical = 8.dp))
        EmptyState(
            illustration = Illustrations.NoResults,
            title = "Sorry, we couldn't find that connection.",
            description = "Try changing up your search a bit. We'll try harder next time.",
            action = {
                ButtonPrimarySubtle(onClick = { }) {
                    Text(text = "Adjust search")
                }
            },
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffffff)
@Composable
private fun EmptyStatePreview() {
    OrbitTheme {
        EmptyStateScreenInner(PaddingValues())
    }
}
