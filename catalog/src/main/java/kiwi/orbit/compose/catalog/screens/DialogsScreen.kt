package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.catalog.Screen
import kiwi.orbit.compose.ui.controls.ButtonPrimary
import kiwi.orbit.compose.ui.controls.Text

@Composable
fun DialogsScreen(
    onNavigateUp: () -> Unit,
    onShowMaterialDialog: () -> Unit,
) {
    Screen(
        title = "Dialogs",
        onNavigateUp = onNavigateUp,
    ) { contentPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding)
        ) {
            DialogsScreenInner(onShowMaterialDialog)
        }
    }
}

@Composable
private fun DialogsScreenInner(
    onShowMaterialDialog: () -> Unit,
) {
    Column(Modifier.padding(16.dp)) {
        ButtonPrimary(
            onClick = onShowMaterialDialog,
        ) { Text("Show Material Dialog") }
    }
}
