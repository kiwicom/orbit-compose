package kiwi.orbit.compose.catalog

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kiwi.navigationcompose.typed.composable
import com.kiwi.navigationcompose.typed.createRoutePattern
import com.kiwi.navigationcompose.typed.dialog
import com.kiwi.navigationcompose.typed.navigate
import kiwi.orbit.compose.catalog.screens.AlertScreen
import kiwi.orbit.compose.catalog.screens.BadgeListScreen
import kiwi.orbit.compose.catalog.screens.BadgeScreen
import kiwi.orbit.compose.catalog.screens.ButtonScreen
import kiwi.orbit.compose.catalog.screens.CheckboxScreen
import kiwi.orbit.compose.catalog.screens.ChoiceTileScreen
import kiwi.orbit.compose.catalog.screens.ColorsScreen
import kiwi.orbit.compose.catalog.screens.DialogsMaterialDialog
import kiwi.orbit.compose.catalog.screens.DialogsOrbitDialog
import kiwi.orbit.compose.catalog.screens.DialogsScreen
import kiwi.orbit.compose.catalog.screens.EmptyStateScreen
import kiwi.orbit.compose.catalog.screens.IconsScreen
import kiwi.orbit.compose.catalog.screens.IllustrationsScreen
import kiwi.orbit.compose.catalog.screens.KeyValueScreen
import kiwi.orbit.compose.catalog.screens.LinearProgressIndicatorScreen
import kiwi.orbit.compose.catalog.screens.ListChoiceScreen
import kiwi.orbit.compose.catalog.screens.ListScreen
import kiwi.orbit.compose.catalog.screens.LoadingScreen
import kiwi.orbit.compose.catalog.screens.MainScreen
import kiwi.orbit.compose.catalog.screens.PillButtonScreen
import kiwi.orbit.compose.catalog.screens.RadioScreen
import kiwi.orbit.compose.catalog.screens.SeatScreen
import kiwi.orbit.compose.catalog.screens.SegmentedSwitchScreen
import kiwi.orbit.compose.catalog.screens.SelectFieldScreen
import kiwi.orbit.compose.catalog.screens.StepperScreen
import kiwi.orbit.compose.catalog.screens.SurfaceCardScreen
import kiwi.orbit.compose.catalog.screens.SwitchScreen
import kiwi.orbit.compose.catalog.screens.TabsScreen
import kiwi.orbit.compose.catalog.screens.TagScreen
import kiwi.orbit.compose.catalog.screens.TextFieldScreen
import kiwi.orbit.compose.catalog.screens.TimelineScreen
import kiwi.orbit.compose.catalog.screens.ToastScreen
import kiwi.orbit.compose.catalog.screens.TypographyScreen
import kiwi.orbit.compose.catalog.screens.topAppBarNavigation
import kotlinx.serialization.ExperimentalSerializationApi

@Composable
fun CatalogApplication() {
    val systemUiController = rememberSystemUiController()

    var isLightTheme by rememberSaveable { mutableStateOf<Boolean?>(null) }
    val isLightThemeFinal = isLightTheme ?: !isSystemInDarkTheme()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = isLightThemeFinal,
        )
    }

    AppTheme(isLightTheme = isLightThemeFinal) {
        NavGraph(
            onToggleTheme = {
                isLightTheme = !isLightThemeFinal
            },
        )
    }
}

@OptIn(ExperimentalSerializationApi::class)
@Composable
private fun NavGraph(
    onToggleTheme: () -> Unit,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = createRoutePattern<Destinations.Main>(),
    ) {
        composable<Destinations.Main> { MainScreen(navController::navigate, onToggleTheme) }

        composable<Destinations.Colors> { ColorsScreen(navController::navigateUp) }
        composable<Destinations.Icons> { IconsScreen(navController::navigateUp) }
        composable<Destinations.Illustrations> { IllustrationsScreen(navController::navigateUp) }
        composable<Destinations.Typography> { TypographyScreen(navController::navigateUp) }

        composable<Destinations.Alert> { AlertScreen(navController::navigateUp) }
        composable<Destinations.Badge> { BadgeScreen(navController::navigateUp) }
        composable<Destinations.BadgeList> { BadgeListScreen(navController::navigateUp) }
        composable<Destinations.Button> { ButtonScreen(navController::navigateUp) }
        composable<Destinations.Checkbox> { CheckboxScreen(navController::navigateUp) }
        composable<Destinations.ChoiceTile> { ChoiceTileScreen(navController::navigateUp) }
        composable<Destinations.Dialog> { DialogsScreen(navController::navigateUp, navController::navigate) }
        dialog<Destinations.DialogMaterial> { DialogsMaterialDialog(navController) }
        dialog<Destinations.DialogOrbit> { DialogsOrbitDialog(navController) }
        composable<Destinations.EmptyState> { EmptyStateScreen(navController::navigateUp) }
        composable<Destinations.KeyValue> { KeyValueScreen(navController::navigateUp) }
        composable<Destinations.LinearProgressIndicator> { LinearProgressIndicatorScreen(navController::navigateUp) }
        composable<Destinations.List> { ListScreen(navController::navigateUp) }
        composable<Destinations.ListChoice> { ListChoiceScreen(navController::navigateUp) }
        composable<Destinations.Loading> { LoadingScreen(navController::navigateUp) }
        composable<Destinations.Radio> { RadioScreen(navController::navigateUp) }
        composable<Destinations.PillButton> { PillButtonScreen(navController::navigateUp) }
        composable<Destinations.Seat> { SeatScreen(navController::navigateUp) }
        composable<Destinations.SegmentedSwitch> { SegmentedSwitchScreen(navController::navigateUp) }
        composable<Destinations.SelectField> { SelectFieldScreen(navController::navigateUp) }
        composable<Destinations.Stepper> { StepperScreen(navController::navigateUp) }
        composable<Destinations.SurfaceCard> { SurfaceCardScreen(navController::navigateUp) }
        composable<Destinations.Switch> { SwitchScreen(navController::navigateUp) }
        composable<Destinations.Tabs> { TabsScreen(navController::navigateUp) }
        composable<Destinations.Tag> { TagScreen(navController::navigateUp) }
        composable<Destinations.TextField> { TextFieldScreen(navController::navigateUp) }
        composable<Destinations.Timeline> { TimelineScreen(navController::navigateUp) }
        composable<Destinations.Toast> { ToastScreen(navController::navigateUp) }
        topAppBarNavigation<Destinations.TopAppBar>(navController)
    }
}
