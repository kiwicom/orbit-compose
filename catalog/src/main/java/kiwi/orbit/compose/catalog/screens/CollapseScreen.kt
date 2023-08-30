package kiwi.orbit.compose.catalog.screens

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.catalog.semantics.SubScreenSemantics
import kiwi.orbit.compose.ui.controls.Collapse
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBar

@Composable
internal fun CollapseScreen(onNavigateUp: () -> Unit) {
    Scaffold(
        modifier = Modifier.testTag(SubScreenSemantics.Tag),
        topBar = {
            TopAppBar(
                title = { Text("Collapse") },
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
            CollapseScreenInner()
        }
    }
}

@Preview
@Composable
private fun CollapseScreenInner() {
    var isFirstCollapseExpanded by remember { mutableStateOf(true) }
    var isSecondCollapseExpanded by remember { mutableStateOf(false) }
    Column(modifier = Modifier.padding(16.dp)) {
        Collapse(
            expanded = isFirstCollapseExpanded,
            onExpandChange = { isFirstCollapseExpanded = it },
            title = {
                Text(text = "Title 1")
            },
            content = {
                Text(text = "Something with many many words, descriptive and longer for more lines.")
            },
        )

        Collapse(
            expanded = isSecondCollapseExpanded,
            onExpandChange = { isSecondCollapseExpanded = it },
            withSeparator = false,
            title = {
                Text(text = "Title 2")
            },
            content = {
                Text(text = "Something with many many words, descriptive and longer for more lines.")
            },
        )
    }
}
