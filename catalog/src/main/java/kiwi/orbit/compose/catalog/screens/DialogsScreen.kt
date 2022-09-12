package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kiwi.navigationcompose.typed.Destination
import kiwi.orbit.compose.catalog.Destinations
import kiwi.orbit.compose.ui.controls.ButtonSecondary
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBar
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@Composable
internal fun DialogsScreen(
    onNavigateUp: () -> Unit,
    onNavigate: (Destination) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dialogs") },
                onNavigateUp = onNavigateUp,
            )
        },
    ) { contentPadding: PaddingValues ->
        DialogsScreenInner(
            onShowMaterialDialog = { onNavigate(Destinations.DialogMaterial) },
            onShowOrbitDialog = { onNavigate(Destinations.DialogOrbit) },
            contentPadding = contentPadding,
        )
    }
}

@Composable
private fun DialogsScreenInner(
    onShowMaterialDialog: () -> Unit,
    onShowOrbitDialog: () -> Unit,
    contentPadding: PaddingValues,
) {
    Column(
        Modifier
            .fillMaxSize()
            .wrapContentHeight()
            .verticalScroll(rememberScrollState())
            .padding(contentPadding)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ButtonSecondary(onClick = onShowMaterialDialog) { Text("Show Material Dialog") }
        ButtonSecondary(onClick = onShowOrbitDialog) { Text("Show Orbit Dialog") }
    }
}
