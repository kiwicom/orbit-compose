package kiwi.orbit.compose.catalog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kiwi.orbit.compose.catalog.screens.AlertScreen
import kiwi.orbit.compose.catalog.screens.BadgeScreen
import kiwi.orbit.compose.catalog.screens.ButtonScreen
import kiwi.orbit.compose.catalog.screens.CardsScreen
import kiwi.orbit.compose.catalog.screens.CheckboxScreen
import kiwi.orbit.compose.catalog.screens.ChoiceTileScreen
import kiwi.orbit.compose.catalog.screens.ColorsScreen
import kiwi.orbit.compose.catalog.screens.DialogsMaterialDialog
import kiwi.orbit.compose.catalog.screens.DialogsScreen
import kiwi.orbit.compose.catalog.screens.IconsScreen
import kiwi.orbit.compose.catalog.screens.IllustrationsScreen
import kiwi.orbit.compose.catalog.screens.MainScreen
import kiwi.orbit.compose.catalog.screens.RadioScreen
import kiwi.orbit.compose.catalog.screens.SeatScreen
import kiwi.orbit.compose.catalog.screens.SelectFieldScreen
import kiwi.orbit.compose.catalog.screens.StepperScreen
import kiwi.orbit.compose.catalog.screens.SwitchScreen
import kiwi.orbit.compose.catalog.screens.TagScreen
import kiwi.orbit.compose.catalog.screens.TextFieldScreen
import kiwi.orbit.compose.catalog.screens.ToastScreen
import kiwi.orbit.compose.catalog.screens.TopAppBarScreen
import kiwi.orbit.compose.catalog.screens.TypographyScreen

private object MainDestinations {
    const val MAIN = "main"

    const val COLORS = "colors"
    const val ICONS = "icons"
    const val ILLUSTRATIONS = "illustrations"
    const val TYPOGRAPHY = "typography"

    const val ALERT = "alert"
    const val BADGE = "badge"
    const val BUTTON = "button"
    const val CARDS = "cards"
    const val CHECKBOX = "checkbox"
    const val CHOICE_TILE = "choice_tile"
    const val DIALOGS = "dialogs"
    const val DIALOGS_MATERIAL_DIALOG = "dialogs_material_dialog"
    const val RADIO = "radio"
    const val SEAT = "seat"
    const val SELECT_FIELD = "select_field"
    const val STEPPER = "stepper"
    const val SWITCH = "switch"
    const val TAG = "tag"
    const val TEXT_FIELD = "text_field"
    const val TOAST = "toast"
    const val TOP_APP_BAR = "top_app_bar"
}

@Composable
fun NavGraph(
    startDestination: String = MainDestinations.MAIN,
    onToggleTheme: () -> Unit,
) {
    val navController = rememberNavController()

    val actions = remember(navController) { MainActions(navController) }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MainDestinations.MAIN) {
            MainScreen(actions, onToggleTheme)
        }

        composable(MainDestinations.COLORS) {
            ColorsScreen(actions::navigateUp)
        }
        composable(MainDestinations.ICONS) {
            IconsScreen(actions::navigateUp)
        }
        composable(MainDestinations.ILLUSTRATIONS) {
            IllustrationsScreen(actions::navigateUp)
        }
        composable(MainDestinations.TYPOGRAPHY) {
            TypographyScreen(actions::navigateUp)
        }

        composable(MainDestinations.ALERT) {
            AlertScreen(actions::navigateUp)
        }
        composable(MainDestinations.BADGE) {
            BadgeScreen(actions::navigateUp)
        }
        composable(MainDestinations.BUTTON) {
            ButtonScreen(actions::navigateUp)
        }
        composable(MainDestinations.CARDS) {
            CardsScreen(actions::navigateUp)
        }
        composable(MainDestinations.CHECKBOX) {
            CheckboxScreen(actions::navigateUp)
        }
        composable(MainDestinations.CHOICE_TILE) {
            ChoiceTileScreen(actions::navigateUp)
        }
        composable(MainDestinations.DIALOGS) {
            DialogsScreen(
                actions::navigateUp,
                actions::showDialogsMaterialDialog,
            )
        }
        dialog(MainDestinations.DIALOGS_MATERIAL_DIALOG) {
            DialogsMaterialDialog(navController)
        }
        composable(MainDestinations.RADIO) {
            RadioScreen(actions::navigateUp)
        }
        composable(MainDestinations.SEAT) {
            SeatScreen(actions::navigateUp)
        }
        composable(MainDestinations.SELECT_FIELD) {
            SelectFieldScreen(actions::navigateUp)
        }
        composable(MainDestinations.STEPPER) {
            StepperScreen(actions::navigateUp)
        }
        composable(MainDestinations.SWITCH) {
            SwitchScreen(actions::navigateUp)
        }
        composable(MainDestinations.TAG) {
            TagScreen(actions::navigateUp)
        }
        composable(MainDestinations.TEXT_FIELD) {
            TextFieldScreen(actions::navigateUp)
        }
        composable(MainDestinations.TOAST) {
            ToastScreen(actions::navigateUp)
        }
        composable(
            route = MainDestinations.TOP_APP_BAR + "/{demoId}",
            arguments = listOf(
                navArgument("demoId") { type = NavType.IntType }
            )
        ) { entry ->
            val demoId = requireNotNull(entry.arguments).getInt("demoId")
            TopAppBarScreen(demoId, actions::navigateUp, actions::showTopAppBar)
        }
    }
}

class MainActions(
    private val navController: NavHostController,
) {
    fun navigateUp() {
        navController.navigateUp()
    }

    fun showColors() {
        navController.navigate(MainDestinations.COLORS)
    }

    fun showIcons() {
        navController.navigate(MainDestinations.ICONS)
    }

    fun showIllustrations() {
        navController.navigate(MainDestinations.ILLUSTRATIONS)
    }

    fun showTypography() {
        navController.navigate(MainDestinations.TYPOGRAPHY)
    }

    fun showAlert() {
        navController.navigate(MainDestinations.ALERT)
    }

    fun showBadge() {
        navController.navigate(MainDestinations.BADGE)
    }

    fun showButton() {
        navController.navigate(MainDestinations.BUTTON)
    }

    fun showCards() {
        navController.navigate(MainDestinations.CARDS)
    }

    fun showCheckbox() {
        navController.navigate(MainDestinations.CHECKBOX)
    }

    fun showChoiceTile() {
        navController.navigate(MainDestinations.CHOICE_TILE)
    }

    fun showDialogs() {
        navController.navigate(MainDestinations.DIALOGS)
    }

    fun showDialogsMaterialDialog() {
        navController.navigate(MainDestinations.DIALOGS_MATERIAL_DIALOG)
    }

    fun showRadio() {
        navController.navigate(MainDestinations.RADIO)
    }

    fun showSeat() {
        navController.navigate(MainDestinations.SEAT)
    }

    fun showSelectField() {
        navController.navigate(MainDestinations.SELECT_FIELD)
    }

    fun showStepper() {
        navController.navigate(MainDestinations.STEPPER)
    }

    fun showSwitch() {
        navController.navigate(MainDestinations.SWITCH)
    }

    fun showTag() {
        navController.navigate(MainDestinations.TAG)
    }

    fun showTextField() {
        navController.navigate(MainDestinations.TEXT_FIELD)
    }

    fun showToast() {
        navController.navigate(MainDestinations.TOAST)
    }

    fun showTopAppBar(id: Int = 0) {
        navController.navigate(MainDestinations.TOP_APP_BAR + "/$id")
    }
}
