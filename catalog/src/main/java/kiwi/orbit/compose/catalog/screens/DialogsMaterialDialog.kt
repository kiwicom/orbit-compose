package kiwi.orbit.compose.catalog.screens

import android.annotation.SuppressLint
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import kiwi.orbit.compose.ui.controls.Text

@Composable
internal fun DialogsMaterialDialog(navController: NavController) {
    DialogsMaterialDialog(
        onClose = { navController.popBackStack() },
    )
}

@SuppressLint("MaterialDesignInsteadOrbitDesign")
@Suppress("Dependency")
@Composable
private fun DialogsMaterialDialog(
    onClose: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onClose,
        title = { Text("Title") },
        text = { Text("Some message.") },
        confirmButton = {
            androidx.compose.material3.TextButton(onClick = onClose) {
                Text("Confirm")
            }
        },
        dismissButton = {
            androidx.compose.material3.TextButton(onClick = onClose) {
                Text("Cancel")
            }
        },
    )
}
