package kiwi.orbit.compose.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable

@Composable
@ReadOnlyComposable
internal expect fun getString(string: Strings): String

@Immutable
internal expect value class Strings constructor(val value: Int) {
    companion object {
        val FieldDefaultError: Strings
        val PasswordTextFieldHidePassword: Strings
        val PasswordTextFieldShowPassword: Strings
        val SeatPriceUnavailable: Strings
        val StepperAdd: Strings
        val StepperRemove: Strings
        val TagRemove: Strings
        val TopAppBarNavigationUp: Strings
    }
}
