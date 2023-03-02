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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kiwi.navigationcompose.typed.DialogResultEffect
import com.kiwi.navigationcompose.typed.createRoutePattern
import com.kiwi.navigationcompose.typed.navigate
import kiwi.orbit.compose.catalog.Destinations
import kiwi.orbit.compose.ui.controls.ButtonSecondary
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Separator
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBar
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@Composable
internal fun DialogsScreen(
    navController: NavController,
) {
    var timeResult by remember { mutableStateOf<LocalTime?>(null) }
    var dateResult by remember { mutableStateOf<LocalDate?>(null) }
    DialogResultEffect(
        currentRoutePattern = createRoutePattern<Destinations.Dialog>(),
        navController = navController,
    ) { result: Destinations.DialogMaterialTimePicker.Result ->
        timeResult = result.time
    }
    DialogResultEffect(
        currentRoutePattern = createRoutePattern<Destinations.Dialog>(),
        navController = navController,
    ) { result: Destinations.DialogMaterialDatePicker.Result ->
        dateResult = result.date
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dialogs") },
                onNavigateUp = navController::navigateUp,
            )
        },
    ) { contentPadding: PaddingValues ->
        DialogsScreenInner(
            time = timeResult,
            date = dateResult,
            onShowOrbitDialog = { navController.navigate(Destinations.DialogOrbit) },
            onShowMaterialDialog = { navController.navigate(Destinations.DialogMaterial) },
            onShowMaterialTimePicker = { navController.navigate(Destinations.DialogMaterialTimePicker) },
            onShowMaterialDatePicker = { navController.navigate(Destinations.DialogMaterialDatePicker) },
            contentPadding = contentPadding,
        )
    }
}

@Composable
private fun DialogsScreenInner(
    time: LocalTime?,
    date: LocalDate?,
    onShowOrbitDialog: () -> Unit,
    onShowMaterialDialog: () -> Unit,
    onShowMaterialTimePicker: () -> Unit,
    onShowMaterialDatePicker: () -> Unit,
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
        ButtonSecondary(onClick = onShowOrbitDialog) { Text("Show Orbit Dialog") }
        Separator(Modifier.padding(vertical = 16.dp))
        ButtonSecondary(onClick = onShowMaterialDialog) { Text("Show M3 Dialog") }
        ButtonSecondary(onClick = onShowMaterialTimePicker) { Text("Show M3 TimePicker") }
        ButtonSecondary(onClick = onShowMaterialDatePicker) { Text("Show M3 DatePicker") }
        Text("Picked Time: $time")
        Text("Picked Date: $date")
    }
}
