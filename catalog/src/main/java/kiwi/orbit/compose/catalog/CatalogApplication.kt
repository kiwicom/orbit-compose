package kiwi.orbit.compose.catalog

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
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
import kiwi.orbit.compose.catalog.screens.CardScreen
import kiwi.orbit.compose.catalog.screens.CheckboxScreen
import kiwi.orbit.compose.catalog.screens.ChoiceTileScreen
import kiwi.orbit.compose.catalog.screens.CollapseScreen
import kiwi.orbit.compose.catalog.screens.ColorsScreen
import kiwi.orbit.compose.catalog.screens.DialogMaterialDatePicker
import kiwi.orbit.compose.catalog.screens.DialogMaterialTimePicker
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
import kiwi.orbit.compose.catalog.screens.SliderScreen
import kiwi.orbit.compose.catalog.screens.StepperScreen
import kiwi.orbit.compose.catalog.screens.SurfaceCardScreen
import kiwi.orbit.compose.catalog.screens.SwitchScreen
import kiwi.orbit.compose.catalog.screens.TabsScreen
import kiwi.orbit.compose.catalog.screens.TagScreen
import kiwi.orbit.compose.catalog.screens.TextFieldScreen
import kiwi.orbit.compose.catalog.screens.TileGroupScreen
import kiwi.orbit.compose.catalog.screens.TileScreen
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

@OptIn(ExperimentalSerializationApi::class, ExperimentalComposeUiApi::class)
@Composable
private fun NavGraph(
    onToggleTheme: () -> Unit,
) {
    val density = LocalDensity.current
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = createRoutePattern<Destinations.Main>(),
        modifier = Modifier.semantics { testTagsAsResourceId = true },
        enterTransition = { SharedXAxisEnterTransition(density) },
        exitTransition = { SharedXAxisExitTransition(density) },
        popEnterTransition = { SharedXAxisPopEnterTransition(density) },
        popExitTransition = { SharedXAxisPopExitTransition(density) },
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
        composable<Destinations.Card> { CardScreen(navController::navigateUp) }
        composable<Destinations.Checkbox> { CheckboxScreen(navController::navigateUp) }
        composable<Destinations.ChoiceTile> { ChoiceTileScreen(navController::navigateUp) }
        composable<Destinations.Collapse> { CollapseScreen(navController::navigateUp) }
        composable<Destinations.Dialog> { DialogsScreen(navController) }
        dialog<Destinations.DialogMaterialTimePicker> { DialogMaterialTimePicker(navController) }
        dialog<Destinations.DialogMaterialDatePicker> { DialogMaterialDatePicker(navController) }
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
        composable<Destinations.Slider> { SliderScreen(navController::navigateUp) }
        composable<Destinations.Stepper> { StepperScreen(navController::navigateUp) }
        composable<Destinations.SurfaceCard> { SurfaceCardScreen(navController::navigateUp) }
        composable<Destinations.Switch> { SwitchScreen(navController::navigateUp) }
        composable<Destinations.Tabs> { TabsScreen(navController::navigateUp) }
        composable<Destinations.Tag> { TagScreen(navController::navigateUp) }
        composable<Destinations.TextField> { TextFieldScreen(navController::navigateUp) }
        composable<Destinations.Tile> { TileScreen(navController::navigateUp) }
        composable<Destinations.TileGroup> { TileGroupScreen(navController::navigateUp) }
        composable<Destinations.Timeline> { TimelineScreen(navController::navigateUp) }
        composable<Destinations.Toast> { ToastScreen(navController::navigateUp) }
        topAppBarNavigation<Destinations.TopAppBar>(navController)
    }
}

private val SharedXAxisEnterTransition: (Density) -> EnterTransition = { density ->
    fadeIn(animationSpec = tween(durationMillis = 210, delayMillis = 90, easing = LinearOutSlowInEasing)) +
        slideInHorizontally(animationSpec = tween(durationMillis = 300)) {
            with(density) { 30.dp.roundToPx() }
        }
}

private val SharedXAxisPopEnterTransition: (Density) -> EnterTransition = { density ->
    fadeIn(animationSpec = tween(durationMillis = 210, delayMillis = 90, easing = LinearOutSlowInEasing)) +
        slideInHorizontally(animationSpec = tween(durationMillis = 300)) {
            with(density) { (-30).dp.roundToPx() }
        }
}

private val SharedXAxisExitTransition: (Density) -> ExitTransition = { density ->
    fadeOut(animationSpec = tween(durationMillis = 90, easing = FastOutLinearInEasing)) +
        slideOutHorizontally(animationSpec = tween(durationMillis = 300)) {
            with(density) { (-30).dp.roundToPx() }
        }
}

private val SharedXAxisPopExitTransition: (Density) -> ExitTransition = { density ->
    fadeOut(animationSpec = tween(durationMillis = 90, easing = FastOutLinearInEasing)) +
        slideOutHorizontally(animationSpec = tween(durationMillis = 300)) {
            with(density) { 30.dp.roundToPx() }
        }
}
