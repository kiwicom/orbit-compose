package kiwi.orbit.compose.catalog.screens

import androidx.compose.material.AlertDialog
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import kiwi.orbit.compose.ui.controls.Text

@Composable
fun DialogsMaterialDialog(navController: NavController) {
    DialogsMaterialDialog(
        onClose = { navController.popBackStack() },
    )
}

@Suppress("Dependency")
@Composable
private fun DialogsMaterialDialog(
    onClose: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onClose,
        title = { androidx.compose.material.Text("Title") },
        text = { androidx.compose.material.Text("Some message.") },
        confirmButton = {
            androidx.compose.material.TextButton(onClick = onClose) {
                Text("Ok")
            }
        }
    )
}
