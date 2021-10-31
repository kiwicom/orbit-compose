package kiwi.orbit.compose.catalog.screens

import androidx.compose.material.AlertDialog
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import kiwi.orbit.compose.ui.foundation.MaterialIsland
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle

@Destination(style = DestinationStyle.Dialog::class)
@Composable
fun DialogsMaterialDialog(navigator: DestinationsNavigator) {
    DialogsMaterialDialog(
        onClose = navigator::popBackStack,
    )
}

@Suppress("Dependency")
@Composable
private fun DialogsMaterialDialog(
    onClose: () -> Unit,
) {
    MaterialIsland {
        AlertDialog(
            onDismissRequest = onClose,
            title = { androidx.compose.material.Text("Title") },
            text = { androidx.compose.material.Text("Some message.") },
            confirmButton = {
                androidx.compose.material.TextButton(onClick = onClose) {
                    androidx.compose.material.Text("Ok")
                }
            }
        )
    }
}
