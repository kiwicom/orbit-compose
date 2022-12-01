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
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.BadgeItemCritical
import kiwi.orbit.compose.ui.controls.BadgeItemInfo
import kiwi.orbit.compose.ui.controls.BadgeItemNeutral
import kiwi.orbit.compose.ui.controls.BadgeItemSuccess
import kiwi.orbit.compose.ui.controls.BadgeItemWarning
import kiwi.orbit.compose.ui.controls.BadgeList
import kiwi.orbit.compose.ui.controls.BadgeListSmall
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Surface
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBar
import kiwi.orbit.compose.ui.foundation.ContentEmphasis

@Composable
internal fun BadgeListScreen(onNavigateUp: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("BadgeList") },
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
            BadgeListScreenInner()
        }
    }
}

@Composable
private fun BadgeListScreenInner() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = "Badge List Small",
            style = OrbitTheme.typography.title4,
        )

        BadgeListSmall {
            BadgeItemNeutral(icon = Icons.AirConditioning) {
                Text(text = "This is simple BadgeList item")
            }
            BadgeItemInfo(icon = Icons.Airplane) {
                Text(text = "This is simple BadgeList item")
            }
            BadgeItemSuccess(icon = Icons.Check) {
                Text(text = "This is simple BadgeList item")
            }
            BadgeItemWarning(icon = Icons.Terminal) {
                Text(text = "This is simple BadgeList item")
            }
            BadgeItemCritical(icon = Icons.Alert) {
                Text(text = "This is simple BadgeList item")
            }
        }

        Text(
            text = "Badge List Normal",
            style = OrbitTheme.typography.title4,
        )

        BadgeList {
            BadgeItemNeutral(icon = Icons.AirConditioning) {
                Text(text = "This is simple BadgeList item")
            }
            BadgeItemInfo(icon = Icons.Airplane) {
                Text(text = "This is simple BadgeList item")
            }
            BadgeItemSuccess(icon = Icons.Check) {
                Text(text = "This is simple BadgeList item")
            }
            BadgeItemWarning(icon = Icons.Terminal) {
                Text(text = "This is simple BadgeList item")
            }
            BadgeItemCritical(icon = Icons.Alert) {
                Text(text = "This is simple BadgeList item")
            }
        }

        Text(
            text = "Badge List Normal Minor",
            style = OrbitTheme.typography.title4,
        )

        BadgeList(contentEmphasis = ContentEmphasis.Minor) {
            BadgeItemNeutral(icon = Icons.AirConditioning) {
                Text(text = "This is simple BadgeList item")
            }
            BadgeItemInfo(icon = Icons.Airplane) {
                Text(text = "This is simple BadgeList item")
            }
            BadgeItemSuccess(icon = Icons.Check) {
                Text(text = "This is simple BadgeList item")
            }
            BadgeItemWarning(icon = Icons.Terminal) {
                Text(text = "This is simple BadgeList item")
            }
            BadgeItemCritical(icon = Icons.Alert) {
                Text(text = "This is simple BadgeList item")
            }
        }

        Text(
            text = "Badge List Small Minor",
            style = OrbitTheme.typography.title4,
        )

        BadgeListSmall(contentEmphasis = ContentEmphasis.Minor) {
            BadgeItemNeutral(icon = Icons.AirConditioning) {
                Text(text = "This is simple BadgeList item")
            }
            BadgeItemInfo(icon = Icons.Airplane) {
                Text(text = "This is simple BadgeList item")
            }
            BadgeItemSuccess(icon = Icons.Check) {
                Text(text = "This is simple BadgeList item")
            }
            BadgeItemWarning(icon = Icons.Terminal) {
                Text(text = "This is simple BadgeList item")
            }
            BadgeItemCritical(icon = Icons.Alert) {
                Text(text = "This is simple BadgeList item")
            }
        }

        Text(
            text = "Badge List Multi-lined",
            style = OrbitTheme.typography.title4,
        )

        BadgeListSmall {
            BadgeItemNeutral(icon = Icons.AirConditioning) {
                Text(text = "This is simple BadgeList item, but text is really long. \nMore than one line is needed.")
            }
            BadgeItemInfo(icon = Icons.Airplane) {
                Text(text = "This is simple BadgeList item, but text is really long. \nMore than one line is needed.")
            }
            BadgeItemSuccess(icon = Icons.Check) {
                Text(text = "This is simple BadgeList item, but text is really long. \nMore than one line is needed.")
            }
            BadgeItemWarning(icon = Icons.Terminal) {
                Text(text = "This is simple BadgeList item, but text is really long. \nMore than one line is needed.")
            }
            BadgeItemCritical(icon = Icons.Alert) {
                Text(text = "This is simple BadgeList item, but text is really long. \nMore than one line is needed.")
            }
        }
    }
}

@Preview
@Composable
private fun BadgeScreenPreview() {
    Surface {
        Column(Modifier.verticalScroll(rememberScrollState())) {
            BadgeListScreenInner()
        }
    }
}
