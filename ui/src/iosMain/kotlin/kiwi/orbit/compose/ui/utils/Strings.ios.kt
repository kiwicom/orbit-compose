package kiwi.orbit.compose.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable

@Composable
@ReadOnlyComposable
internal actual fun getString(string: Strings): String =
    when (string) {
        Strings.FieldDefaultError -> "Invalid input"
        Strings.PasswordTextFieldHidePassword -> "Hide password"
        Strings.PasswordTextFieldShowPassword -> "Show password"
        Strings.SeatPriceUnavailable -> "-"
        Strings.StepperAdd -> "Add"
        Strings.StepperRemove -> "Remove"
        Strings.TagRemove -> "Remove"
        Strings.TopAppBarNavigationUp -> "Navigate up"
        else -> ""
    }

@Immutable
internal actual value class Strings constructor(val value: Int) {
    actual companion object {
        actual inline val FieldDefaultError get() = Strings(1)
        actual inline val PasswordTextFieldHidePassword get() = Strings(2)
        actual inline val PasswordTextFieldShowPassword get() = Strings(3)
        actual inline val SeatPriceUnavailable get() = Strings(4)
        actual inline val StepperAdd get() = Strings(5)
        actual inline val StepperRemove get() = Strings(6)
        actual inline val TagRemove get() = Strings(7)
        actual inline val TopAppBarNavigationUp get() = Strings(8)
    }
}
