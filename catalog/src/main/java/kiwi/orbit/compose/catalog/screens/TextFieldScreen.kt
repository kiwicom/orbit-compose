package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.catalog.semantics.SubScreenSemantics
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.controls.ButtonPrimary
import kiwi.orbit.compose.ui.controls.Icon
import kiwi.orbit.compose.ui.controls.IconButton
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TextField
import kiwi.orbit.compose.ui.controls.TopAppBar

@Composable
internal fun TextFieldScreen(onNavigateUp: () -> Unit) {
    var showAction by rememberSaveable { mutableStateOf(true) }
    Scaffold(
        modifier = Modifier.testTag(SubScreenSemantics.Tag),
        topBar = {
            TopAppBar(
                title = { Text("Text Field") },
                onNavigateUp = onNavigateUp,
                actions = {
                    IconButton(onClick = { showAction = !showAction }) {
                        Icon(
                            painter = when (showAction) {
                                true -> Icons.Visibility
                                false -> Icons.VisibilityOff
                            },
                            contentDescription = null,
                        )
                    }
                },
            )
        },
        action = {
            if (showAction) {
                ButtonPrimary(onClick = {}) {
                    Text("Primary Action")
                }
            }
        },
    ) { contentPadding: PaddingValues ->
        Box(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding),
        ) {
            TextFieldScreenInner()
        }
    }
}

@Preview
@Composable
private fun TextFieldScreenInner() {
    Column(Modifier.padding(16.dp)) {
        var isNationalityError by rememberSaveable { mutableStateOf(false) }
        var nationality by rememberSaveable { mutableStateOf("") }
        TextField(
            value = nationality,
            onValueChange = {
                nationality = it
                isNationalityError = it.length < 3
            },
            label = { Text("Nationality") },
            leadingIcon = { Icon(Icons.Trip, contentDescription = null) },
            error = { if (isNationalityError) Text("Nationality must have at least 3 letters.") },
            info = { Text("A nationality code or full name.") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            modifier = Modifier
                .fillMaxWidth(),
        )
    }
}
