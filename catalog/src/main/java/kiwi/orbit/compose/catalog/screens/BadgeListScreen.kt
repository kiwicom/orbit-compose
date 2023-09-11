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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.catalog.semantics.SubScreenSemantics
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.BadgeList
import kiwi.orbit.compose.ui.controls.BadgeListItemCritical
import kiwi.orbit.compose.ui.controls.BadgeListItemInfo
import kiwi.orbit.compose.ui.controls.BadgeListItemNeutral
import kiwi.orbit.compose.ui.controls.BadgeListItemSuccess
import kiwi.orbit.compose.ui.controls.BadgeListItemWarning
import kiwi.orbit.compose.ui.controls.BadgeListSmall
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Surface
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBar
import kiwi.orbit.compose.ui.foundation.ContentEmphasis

@Composable
internal fun BadgeListScreen(onNavigateUp: () -> Unit) {
    Scaffold(
        modifier = Modifier.testTag(SubScreenSemantics.Tag),
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
            BadgeListItemNeutral(icon = Icons.AirConditioning) {
                Text(text = "This is simple BadgeList item")
            }
            BadgeListItemInfo(icon = Icons.Airplane) {
                Text(text = "This is simple BadgeList item")
            }
            BadgeListItemSuccess(icon = Icons.Check) {
                Text(text = "This is simple BadgeList item")
            }
            BadgeListItemWarning(icon = Icons.Terminal) {
                Text(text = "This is simple BadgeList item")
            }
            BadgeListItemCritical(icon = Icons.Alert) {
                Text(text = "This is simple BadgeList item")
            }
        }

        Text(
            text = "Badge List Normal",
            style = OrbitTheme.typography.title4,
        )

        BadgeList {
            BadgeListItemNeutral(icon = Icons.AirConditioning) {
                Text(text = "This is simple BadgeList item")
            }
            BadgeListItemInfo(icon = Icons.Airplane) {
                Text(text = "This is simple BadgeList item")
            }
            BadgeListItemSuccess(icon = Icons.Check) {
                Text(text = "This is simple BadgeList item")
            }
            BadgeListItemWarning(icon = Icons.Terminal) {
                Text(text = "This is simple BadgeList item")
            }
            BadgeListItemCritical(icon = Icons.Alert) {
                Text(text = "This is simple BadgeList item")
            }
        }

        Text(
            text = "Badge List Normal Minor",
            style = OrbitTheme.typography.title4,
        )

        BadgeList(contentEmphasis = ContentEmphasis.Minor) {
            BadgeListItemNeutral(icon = Icons.AirConditioning) {
                Text(text = "This is simple BadgeList item")
            }
            BadgeListItemInfo(icon = Icons.Airplane) {
                Text(text = "This is simple BadgeList item")
            }
            BadgeListItemSuccess(icon = Icons.Check) {
                Text(text = "This is simple BadgeList item")
            }
            BadgeListItemWarning(icon = Icons.Terminal) {
                Text(text = "This is simple BadgeList item")
            }
            BadgeListItemCritical(icon = Icons.Alert) {
                Text(text = "This is simple BadgeList item")
            }
        }

        Text(
            text = "Badge List Small Minor",
            style = OrbitTheme.typography.title4,
        )

        BadgeListSmall(contentEmphasis = ContentEmphasis.Minor) {
            BadgeListItemNeutral(icon = Icons.AirConditioning) {
                Text(text = "This is simple BadgeList item")
            }
            BadgeListItemInfo(icon = Icons.Airplane) {
                Text(text = "This is simple BadgeList item")
            }
            BadgeListItemSuccess(icon = Icons.Check) {
                Text(text = "This is simple BadgeList item")
            }
            BadgeListItemWarning(icon = Icons.Terminal) {
                Text(text = "This is simple BadgeList item")
            }
            BadgeListItemCritical(icon = Icons.Alert) {
                Text(text = "This is simple BadgeList item")
            }
        }

        Text(
            text = "Badge List Multi-lined",
            style = OrbitTheme.typography.title4,
        )

        BadgeListSmall {
            BadgeListItemNeutral(icon = Icons.AirConditioning) {
                Text(text = "This is simple BadgeList item, but text is really long. \nMore than one line is needed.")
            }
            BadgeListItemInfo(icon = Icons.Airplane) {
                Text(text = "This is simple BadgeList item, but text is really long. \nMore than one line is needed.")
            }
            BadgeListItemSuccess(icon = Icons.Check) {
                Text(text = "This is simple BadgeList item, but text is really long. \nMore than one line is needed.")
            }
            BadgeListItemWarning(icon = Icons.Terminal) {
                Text(text = "This is simple BadgeList item, but text is really long. \nMore than one line is needed.")
            }
            BadgeListItemCritical(icon = Icons.Alert) {
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
