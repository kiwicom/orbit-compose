package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.catalog.AppTheme
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.controls.BadgeCircleInfoSubtle
import kiwi.orbit.compose.ui.controls.ButtonPrimarySubtle
import kiwi.orbit.compose.ui.controls.Checkbox
import kiwi.orbit.compose.ui.controls.Icon
import kiwi.orbit.compose.ui.controls.ListChoice
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBar

@Composable
internal fun ListChoiceScreen(
    onNavigateUp: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ListChoice") },
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
            ListChoiceScreenInner()
        }
    }
}

@Composable
private fun ListChoiceScreenInner() {
    Column {
        ListChoice(onClick = {}) {
            Text("ListChoice title")
        }
        ListChoice(
            onClick = {},
            description = { Text("Further description") },
        ) {
            Text("ListChoice title")
        }
        ListChoice(
            onClick = {},
            icon = { Icon(Icons.Accommodation, contentDescription = null) },
            trailingIcon = { Icon(Icons.ChevronRight, contentDescription = null) },
        ) {
            Text("ListChoice title")
        }
        ListChoice(
            onClick = {},
            icon = { Icon(Icons.Accommodation, contentDescription = null) },
            trailingIcon = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    BadgeCircleInfoSubtle(value = 1)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.ChevronRight, contentDescription = null)
                }
            },
            description = { Text("Further description") },
        ) {
            Text("ListChoice title")
        }
        ListChoice(
            onClick = {},
            icon = { Icon(Icons.Bus, contentDescription = null) },
            trailingIcon = {
                ButtonPrimarySubtle(onClick = {}) {
                    Icon(Icons.Plus, contentDescription = null)
                }
            },
        ) {
            Text("ListChoice title")
        }
        ListChoice(
            onClick = {},
            trailingIcon = {
                ButtonPrimarySubtle(onClick = {}) {
                    Icon(Icons.Plus, contentDescription = null)
                }
            },
        ) {
            Text("ListChoice title")
        }
        var checked by remember { mutableStateOf(false) }
        ListChoice(
            onClick = { checked = !checked },
            trailingIcon = {
                Checkbox(
                    checked = checked,
                    onCheckedChange = null,
                )
            },
        ) {
            Text("ListChoice title")
        }
    }
}

@Suppress("UnusedPrivateMember")
@Preview
@Composable
private fun ListChoiceScreenPreview() {
    AppTheme {
        ListChoiceScreen({})
    }
}
