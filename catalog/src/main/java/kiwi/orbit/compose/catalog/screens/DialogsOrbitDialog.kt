package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import kiwi.orbit.compose.illustrations.Illustrations
import kiwi.orbit.compose.ui.controls.ButtonLinkCritical
import kiwi.orbit.compose.ui.controls.ButtonLinkSecondary
import kiwi.orbit.compose.ui.controls.ButtonPrimary
import kiwi.orbit.compose.ui.controls.Dialog
import kiwi.orbit.compose.ui.controls.Text

@Composable
fun DialogsOrbitDialog(navController: NavController) {
    DialogsOrbitDialog(
        onClose = { navController.popBackStack() },
    )
}

@Composable
private fun DialogsOrbitDialog(
    onClose: () -> Unit,
) {
    Dialog(
        onDismissRequest = onClose,
        title = { Text("Kiwi.com would like to send you notifications.") },
        text = { Text("Notifications may include alerts, sounds, and icon badges. These can be configured in Settings.") },
        buttons = {
            ButtonPrimary(
                onClick = onClose,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Confirm")
            }
            ButtonLinkCritical(
                onClick = onClose,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Delete")
            }
            ButtonLinkSecondary(
                onClick = onClose,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Dismiss")
            }
        },
        illustration = {
            Image(
                painter = Illustrations.NoNotification,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(0.64f),
            )
        },
    )
}
