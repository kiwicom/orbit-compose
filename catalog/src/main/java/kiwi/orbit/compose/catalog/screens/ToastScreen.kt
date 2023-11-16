package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.catalog.semantics.SubScreenSemantics
import kiwi.orbit.compose.catalog.semantics.ToastScreenSemantics
import kiwi.orbit.compose.icons.IconName
import kiwi.orbit.compose.ui.controls.ButtonSecondary
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBar
import kiwi.orbit.compose.ui.controls.rememberToastHostState
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
internal fun ToastScreen(
    onNavigateUp: () -> Unit,
) {
    var dismissedText by remember { mutableStateOf("") }
    val toastHostState = rememberToastHostState(
        onDismiss = {
            val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            dismissedText = "Last toast dismissed at ${now.hour}:${now.minute}:${now.second}"
        },
    )
    Scaffold(
        modifier = Modifier.testTag(SubScreenSemantics.Tag),
        topBar = {
            TopAppBar(
                title = { Text("Toast") },
                onNavigateUp = onNavigateUp,
            )
        },
        toastHostState = toastHostState,
    ) { contentPadding: PaddingValues ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(contentPadding),
        ) {
            ToastScreenInner(dismissedText) { message, iconName ->
                toastHostState.showToast(message, iconName)
            }
        }
    }
}

@Composable
private fun ToastScreenInner(
    dismissedText: String,
    onToast: (String, IconName?) -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize()
            .wrapContentHeight()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ButtonSecondary(
            onClick = { onToast("You’re signed in as jon.snow@wall.7k.", IconName.CheckCircle) },
            modifier = Modifier.testTag(ToastScreenSemantics.ToastSignedInButtonTag),
            content = { Text("Toast – signed in") },
        )
        ButtonSecondary(
            onClick = { onToast("Price alert was removed.", IconName.NotificationOff) },
            content = { Text("Toast – price alert") },
        )
        ButtonSecondary(
            onClick = { onToast("We’ll notify you when the price changes.", IconName.Notification) },
            content = { Text("Toast – price alert created") },
        )
        ButtonSecondary(
            onClick = {
                onToast("On mobile there’s always a fixed width to make the Toast stand out a bit more.", null)
            },
            content = { Text("Toast – long message") },
        )
        ButtonSecondary(
            onClick = {
                onToast(
                    "On mobile there’s always a fixed width to make the Toast stand out a bit more.",
                    IconName.AirplaneLanding,
                )
            },
            content = { Text("Toast – long message 2") },
        )
        Text(
            text = dismissedText,
            modifier = Modifier.padding(top = 24.dp),
        )
    }
}
