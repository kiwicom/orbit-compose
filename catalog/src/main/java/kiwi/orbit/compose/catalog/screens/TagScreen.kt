package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import kiwi.orbit.compose.ui.controls.Tag
import kiwi.orbit.compose.ui.controls.Text

@Composable
fun TagScreen(onUpClick: () -> Unit) {
    Screen(
        title = "Tag",
        onUpClick = onUpClick,
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(it)
        ) {
            TagScreenInner()
        }
    }
}

@Preview
@Composable
private fun TagScreenInner() {
    Column(Modifier.padding(16.dp)) {
        Row {
            Tag {
                Text("Prague")
            }
            Spacer(Modifier.size(16.dp))
            var selected by remember { mutableStateOf(false) }
            Tag(selected, onSelect = { selected = !selected }) {
                Text("Prague")
            }
        }
        Spacer(Modifier.size(16.dp))
        Row {
            Tag(selected = true) {
                Text("Prague")
            }
            Spacer(Modifier.size(16.dp))
            var selected by remember { mutableStateOf(true) }
            Tag(selected, onSelect = { selected = !selected }) {
                Text("Prague")
            }
        }

        Spacer(Modifier.size(32.dp))

        Row {
            Tag(onRemove = {}) {
                Text("Prague")
            }
            Spacer(Modifier.size(16.dp))
            var selected by remember { mutableStateOf(false) }
            Tag(selected, onSelect = { selected = !selected }, onRemove = {}) {
                Text("Prague")
            }
        }
        Spacer(Modifier.size(16.dp))
        Row {
            Tag(selected = true, onRemove = {}) {
                Text("Prague")
            }
            Spacer(Modifier.size(16.dp))
            var selected by remember { mutableStateOf(true) }
            Tag(selected, onSelect = { selected = !selected }, onRemove = {}) {
                Text("Prague")
            }
        }
    }
}
