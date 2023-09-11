package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.catalog.semantics.SubScreenSemantics
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Tag
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.ToastHostState
import kiwi.orbit.compose.ui.controls.TopAppBar
import kotlinx.coroutines.launch

@Composable
internal fun TagScreen(onNavigateUp: () -> Unit) {
    val toastHostState = remember { ToastHostState() }
    Scaffold(
        modifier = Modifier.testTag(SubScreenSemantics.Tag),
        topBar = {
            TopAppBar(
                title = { Text("Tag") },
                onNavigateUp = onNavigateUp,
            )
        },
        toastHostState = toastHostState,
    ) { contentPadding: PaddingValues ->
        Box(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding),
        ) {
            TagScreenInner(toastHostState)
        }
    }
}

@Composable
private fun TagScreenInner(toastHostState: ToastHostState) {
    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        val scope = rememberCoroutineScope()
        val onRemove: () -> Unit = {
            scope.launch {
                toastHostState.showToast("Tag removed.") { Icons.InformationCircle }
            }
        }

        Text("Non-interactive", style = OrbitTheme.typography.title4)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Tag {
                Text("Simple")
            }
            Tag(active = true) {
                Text("Active")
            }
            Tag(selected = true) {
                Text("Selected")
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Tag(onRemove = onRemove) {
                Text("Simple")
            }
            Tag(active = true, onRemove = onRemove) {
                Text("Active")
            }
            Tag(selected = true, onRemove = onRemove) {
                Text("Selected")
            }
        }

        Text("Interactive", style = OrbitTheme.typography.title4)

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            var selected1 by rememberSaveable { mutableStateOf(false) }
            var selected2 by rememberSaveable { mutableStateOf(false) }
            var selected3 by rememberSaveable { mutableStateOf(true) }
            Tag(selected = selected1, onSelect = { selected1 = !selected1 }) {
                Text("Simple")
            }
            Tag(active = true, selected = selected2, onSelect = { selected2 = !selected2 }) {
                Text("Active")
            }
            Tag(selected = selected3, onSelect = { selected3 = !selected3 }) {
                Text("Selected")
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            var selected1 by rememberSaveable { mutableStateOf(false) }
            var selected2 by rememberSaveable { mutableStateOf(false) }
            var selected3 by rememberSaveable { mutableStateOf(true) }
            Tag(selected = selected1, onSelect = { selected1 = !selected1 }, onRemove = onRemove) {
                Text("Simple")
            }
            Tag(
                active = true,
                selected = selected2,
                onSelect = { selected2 = !selected2 },
                onRemove = onRemove,
            ) {
                Text("Active")
            }
            Tag(selected = selected3, onSelect = { selected3 = !selected3 }, onRemove = onRemove) {
                Text("Selected")
            }
        }
    }
}

@Preview
@Composable
private fun TagScreenPreview() {
    TagScreen({})
}
