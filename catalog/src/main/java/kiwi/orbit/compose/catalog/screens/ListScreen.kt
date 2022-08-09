package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.layout.Arrangement
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
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.Icon
import kiwi.orbit.compose.ui.controls.List
import kiwi.orbit.compose.ui.controls.ListItem
import kiwi.orbit.compose.ui.controls.ListLarge
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBar

@Composable
fun ListScreen(
    onNavigateUp: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("List") },
                onNavigateUp = onNavigateUp,
            )
        },
    ) { contentPadding: PaddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding),
        ) {
            ListScreenInner()
        }
    }
}

@Composable
private fun ListScreenInner() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "List",
                style = OrbitTheme.typography.title2,
            )

            List {
                ListItem { Text("This is simple list item") }
                ListItem { Text("This is another simple list item") }
                ListItem { Text("This list item\nspans multiple lines") }
            }

            Text(
                text = "List with custom icons",
                style = OrbitTheme.typography.title2,
            )

            List {
                ListItem(
                    icon = { Icon(painter = Icons.BaggagePersonal, contentDescription = null) },
                    content = { Text("Personal Item") },
                )
                ListItem(
                    icon = { Icon(painter = Icons.BaggageCabin, contentDescription = null) },
                    content = { Text("Cabin Baggage") },
                )
                ListItem(
                    icon = { Icon(painter = Icons.BaggageChecked20, contentDescription = null) },
                    content = { Text("Checked Baggage") },
                )
            }

            Text(
                text = "List with custom color and icons",
                style = OrbitTheme.typography.title2,
            )

            List(
                contentColor = OrbitTheme.colors.critical.normal,
                defaultIcon = Icons.ThumbDown,
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                ListItem { Text("Oh no, two women love me.") }
                ListItem { Text("They're both gorgeous and sexy.") }
                ListItem { Text("My wallet's too small for my fifties.") }
                ListItem { Text("And my diamond shoes are too tight!") }
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "ListLarge",
                style = OrbitTheme.typography.title2,
            )

            ListLarge {
                ListItem { Text("This is simple list item") }
                ListItem { Text("This is another simple list item") }
                ListItem { Text("This list item\nspans multiple lines") }
            }

            Text(
                text = "ListLarge with custom color and icons",
                style = OrbitTheme.typography.title2,
            )

            List(
                contentColor = OrbitTheme.colors.info.strong,
                defaultIcon = Icons.Moon,
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                ListItem { Text("Good night.") }
                ListItem { Text("Sleep tight.") }
                ListItem { Text("Don't let the bedbugs bite.") }
            }
        }
    }
}

@Suppress("UnusedPrivateMember")
@Preview
@Composable
private fun ListScreenPreview() {
    AppTheme {
        ListScreen {}
    }
}
