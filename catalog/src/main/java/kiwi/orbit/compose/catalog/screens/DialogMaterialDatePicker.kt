@file:Suppress("Dependency")

package kiwi.orbit.compose.catalog.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.kiwi.navigationcompose.typed.setResult
import kiwi.orbit.compose.catalog.Destinations
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSerializationApi::class)
@Composable
internal fun DialogMaterialDatePicker(navController: NavController) {
    val state = rememberDatePickerState()

    DatePickerDialog(
        onCancel = { navController.popBackStack() },
        onConfirm = {
            val millis = state.selectedDateMillis
            if (millis != null) {
                val localDate = Instant.fromEpochMilliseconds(millis)
                    .toLocalDateTime(TimeZone.currentSystemDefault())
                    .date
                navController.setResult(Destinations.DialogMaterialDatePicker.Result(localDate))
            }
            navController.popBackStack()
        },
        content = {
            DatePicker(state)
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MaterialDesignInsteadOrbitDesign")
@Composable
private fun DatePickerDialog(
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    DatePickerDialog(
        onDismissRequest = onCancel,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Ok")
            }
        },
        dismissButton = {
            TextButton(onClick = onConfirm) {
                Text("Cancel")
            }
        },
        content = content,
    )
}
