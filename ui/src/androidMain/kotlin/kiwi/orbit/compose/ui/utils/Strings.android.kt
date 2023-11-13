package kiwi.orbit.compose.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import kiwi.orbit.compose.ui.R

@Composable
@ReadOnlyComposable
internal actual fun getString(string: Strings): String {
    LocalConfiguration.current
    val resources = LocalContext.current.resources
    return resources.getString(string.value)
}

@JvmInline
@Immutable
internal actual value class Strings constructor(val value: Int) {
    actual companion object {
        actual inline val FieldDefaultError get() = Strings(R.string.orbit_field_default_error)
        actual inline val PasswordTextFieldHidePassword get() = Strings(R.string.orbit_cd_text_field_hide_password)
        actual inline val PasswordTextFieldShowPassword get() = Strings(R.string.orbit_cd_text_field_show_password)
        actual inline val SeatPriceUnavailable get() = Strings(R.string.orbit_seat_price_unavailable)
        actual inline val StepperAdd get() = Strings(R.string.orbit_cd_stepper_add)
        actual inline val StepperRemove get() = Strings(R.string.orbit_cd_stepper_remove)
        actual inline val TagRemove get() = Strings(R.string.orbit_cd_tag_remove)
        actual inline val TopAppBarNavigationUp get() = Strings(R.string.orbit_cd_navigate_up)
    }
}
