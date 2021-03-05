package kiwi.orbit.app.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.app.SubScreen
import kiwi.orbit.controls.Tag

@Composable
fun TagScreen(onUpClick: () -> Unit) {
    SubScreen(
        title = "Tag",
        onUpClick = onUpClick,
    ) {
        Surface() {
            Column(
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                TagScreen()
            }
        }
    }
}

@Preview
@Composable
fun ColumnScope.TagScreen() {

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
