package kiwi.orbit.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import kiwi.orbit.app.screens.AlertScreen
import kiwi.orbit.app.screens.BadgeScreen
import kiwi.orbit.app.screens.ButtonScreen
import kiwi.orbit.app.screens.ColorsScreen
import kiwi.orbit.app.screens.MainScreen
import kiwi.orbit.app.screens.StepperScreen
import kiwi.orbit.app.screens.SwitchScreen
import kiwi.orbit.app.screens.TextFieldScreen
import kiwi.orbit.app.screens.TypographyScreen
import kiwi.orbit.app.screens.XProfileScreen

/**
 * Destinations used in the ([OwlApp]).
 */
private object MainDestinations {
    const val MAIN = "main"

    const val COLORS = "colors"
    const val TYPOGRAPHY = "typography"

    const val ALERT = "alert"
    const val BADGE = "badge"
    const val BUTTON = "button"
    const val STEPPER = "stepper"
    const val SWITCH = "switch"
    const val TEXT_FIELD = "text_field"

    const val X_PROFILE = "x_profile"
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
        composable(MainDestinations.STEPPER) {
            StepperScreen(actions::navigateUp)
        }
        composable(MainDestinations.SWITCH) {
            SwitchScreen(actions::navigateUp)
        }
        composable(MainDestinations.TEXT_FIELD) {
            TextFieldScreen(actions::navigateUp)
        }

        composable(MainDestinations.X_PROFILE) {
            XProfileScreen(actions::navigateUp)
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

    fun showStepper() {
        navController.navigate(MainDestinations.STEPPER)
    }

    fun showSwitch() {
        navController.navigate(MainDestinations.SWITCH)
    }

    fun showTextField() {
        navController.navigate(MainDestinations.TEXT_FIELD)
    }

    fun showXProfile() {
        navController.navigate(MainDestinations.X_PROFILE)
    }
}
