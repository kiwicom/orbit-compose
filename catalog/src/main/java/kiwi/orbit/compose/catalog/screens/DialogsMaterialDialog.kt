package kiwi.orbit.compose.catalog.screens

import android.annotation.SuppressLint
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun DialogsMaterialDialog(navController: NavController) {
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
            androidx.compose.material.TextButton(onClick = onClose) {
                Text("Ok")
            }
        }
    )
}
