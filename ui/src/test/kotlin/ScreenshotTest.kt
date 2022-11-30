package kiwi.orbit.compose.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import app.cash.paparazzi.DeviceConfig.Companion.PIXEL_5
import app.cash.paparazzi.Paparazzi
import com.android.ide.common.rendering.api.SessionParams
import com.android.resources.NightMode
import kiwi.orbit.compose.ui.controls.AlertCriticalPreview
import kiwi.orbit.compose.ui.controls.AlertInfoPreview
import kiwi.orbit.compose.ui.controls.AlertInlineCustomizedPreview
import kiwi.orbit.compose.ui.controls.AlertInlinePreview
import kiwi.orbit.compose.ui.controls.AlertSimplePreview
import kiwi.orbit.compose.ui.controls.AlertSuccessPreview
import kiwi.orbit.compose.ui.controls.AlertWarningPreview
import kiwi.orbit.compose.ui.controls.BadgeCirclePreview
import kiwi.orbit.compose.ui.controls.BadgeListPreview
import kiwi.orbit.compose.ui.controls.BadgePreview
import kiwi.orbit.compose.ui.controls.ButtonLinkPreview
import kiwi.orbit.compose.ui.controls.ButtonPreview
import kiwi.orbit.compose.ui.controls.ButtonTextLinkPreview
import kiwi.orbit.compose.ui.controls.CheckboxFieldPreview
import kiwi.orbit.compose.ui.controls.CheckboxPreview
import kiwi.orbit.compose.ui.controls.ChoiceTileCenteredPreview
import kiwi.orbit.compose.ui.controls.ChoiceTilePreview
import kiwi.orbit.compose.ui.controls.ClickableFieldPreview
import kiwi.orbit.compose.ui.controls.CountryFlagPreview
import kiwi.orbit.compose.ui.controls.DialogPreview
import kiwi.orbit.compose.ui.controls.EmptyStatePreview
import kiwi.orbit.compose.ui.controls.IconPreview
import kiwi.orbit.compose.ui.controls.InlineLoadingPreview
import kiwi.orbit.compose.ui.controls.KeyValuePreview
import kiwi.orbit.compose.ui.controls.LinearIndeterminateProgressIndicatorPreview
import kiwi.orbit.compose.ui.controls.LinearProgressIndicatorPreview
import kiwi.orbit.compose.ui.controls.ListChoicePreview
import kiwi.orbit.compose.ui.controls.ListLargePreview
import kiwi.orbit.compose.ui.controls.ListPreview
import kiwi.orbit.compose.ui.controls.PasswordTextFieldPreview
import kiwi.orbit.compose.ui.controls.PillButtonPreview
import kiwi.orbit.compose.ui.controls.RadioFieldPreview
import kiwi.orbit.compose.ui.controls.RadioPreview
import kiwi.orbit.compose.ui.controls.ScaffoldPreview
import kiwi.orbit.compose.ui.controls.SeatLegendPreview
import kiwi.orbit.compose.ui.controls.SeatPreview
import kiwi.orbit.compose.ui.controls.SegmentedSwitchPreview
import kiwi.orbit.compose.ui.controls.SelectFieldPreview
import kiwi.orbit.compose.ui.controls.StepperPreview
import kiwi.orbit.compose.ui.controls.SurfaceCardPreview
import kiwi.orbit.compose.ui.controls.SwitchPreview
import kiwi.orbit.compose.ui.controls.TagPreview
import kiwi.orbit.compose.ui.controls.TextFieldPreview
import kiwi.orbit.compose.ui.controls.ToastPreview
import kiwi.orbit.compose.ui.controls.TopAppBarLargePreview
import kiwi.orbit.compose.ui.controls.TopAppBarPreview
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

internal class ScreenshotTest {

    @get:Rule
    val paparazzi = Paparazzi(
        theme = "android:Theme.Material.NoActionBar.Fullscreen",
        renderingMode = SessionParams.RenderingMode.V_SCROLL,
    )

    private fun snapshot(content: @Composable () -> Unit) {
        paparazzi.unsafeUpdateConfig(
            deviceConfig = PIXEL_5.copy(screenHeight = 1, softButtons = false),
        )
        paparazzi.snapshot { content() }
        paparazzi.unsafeUpdateConfig(
            deviceConfig = PIXEL_5.copy(screenHeight = 1, softButtons = false, fontScale = 1.6f),
        )
        paparazzi.snapshot(name = "big") { content() }
        paparazzi.unsafeUpdateConfig(
            deviceConfig = PIXEL_5.copy(screenHeight = 1, softButtons = false, nightMode = NightMode.NIGHT),
        )
        paparazzi.snapshot(name = "dark") { content() }
    }

    @Test
    fun alertInfo() {
        snapshot { AlertInfoPreview() }
    }

    @Test
    fun alertSuccess() {
        snapshot { AlertSuccessPreview() }
    }

    @Test
    fun alertWarning() {
        snapshot { AlertWarningPreview() }
    }

    @Test
    fun alertCritical() {
        snapshot { AlertCriticalPreview() }
    }

    @Test
    fun alertSimple() {
        snapshot { AlertSimplePreview() }
    }

    @Test
    fun alertInline() {
        snapshot { AlertInlinePreview() }
    }

    @Test
    fun alertInlineCustomized() {
        snapshot { AlertInlineCustomizedPreview() }
    }

    @Test
    fun badge() {
        snapshot { BadgePreview() }
    }

    @Test
    fun badgeCircle() {
        snapshot { BadgeCirclePreview() }
    }

    @Test
    fun badgeList() {
        snapshot { BadgeListPreview() }
    }

    @Test
    fun buttonLink() {
        snapshot { ButtonLinkPreview() }
    }

    @Test
    fun buttonTextLink() {
        snapshot { ButtonTextLinkPreview() }
    }

    @Test
    fun button() {
        snapshot { ButtonPreview() }
    }

    @Ignore("https://github.com/cashapp/paparazzi/issues/456")
    @Test
    fun checkbox() {
        snapshot { CheckboxPreview() }
    }

    @Ignore("https://github.com/cashapp/paparazzi/issues/456")
    @Test
    fun checkboxField() {
        snapshot { CheckboxFieldPreview() }
    }

    @Test
    fun choiceTile() {
        snapshot { ChoiceTilePreview() }
    }

    @Test
    fun choiceTileCentered() {
        snapshot { ChoiceTileCenteredPreview() }
    }

    @Test
    fun clickableField() {
        snapshot { ClickableFieldPreview() }
    }

    @Test
    fun countryFlag() {
        snapshot {
            CompositionLocalProvider(
                LocalInspectionMode provides true,
            ) {
                CountryFlagPreview()
            }
        }
    }

    @Test
    fun dialog() {
        snapshot { DialogPreview() }
    }

    @Test
    fun emptyState() {
        snapshot { EmptyStatePreview() }
    }

    @Test
    fun icon() {
        snapshot { IconPreview() }
    }

    @Test
    fun inlineLoading() {
        snapshot { InlineLoadingPreview() }
    }

    @Test
    fun linearProgressIndicator() {
        snapshot { LinearProgressIndicatorPreview() }
    }

    @Test
    fun linearIndeterminateProgressIndicator() {
        snapshot { LinearIndeterminateProgressIndicatorPreview() }
    }

    @Test
    fun list() {
        snapshot { ListPreview() }
    }

    @Test
    fun listChoice() {
        snapshot { ListChoicePreview() }
    }

    @Test
    fun listLarge() {
        snapshot { ListLargePreview() }
    }

    @Test
    fun keyValue() {
        snapshot { KeyValuePreview() }
    }

    @Test
    fun passwordTextField() {
        snapshot { PasswordTextFieldPreview() }
    }

    @Test
    fun pillButton() {
        snapshot { PillButtonPreview() }
    }

    @Test
    fun radio() {
        snapshot { RadioPreview() }
    }

    @Test
    fun radioField() {
        snapshot { RadioFieldPreview() }
    }

    @Test
    fun scaffold() {
        snapshot {
            Box(Modifier.height(200.dp)) {
                ScaffoldPreview()
            }
        }
    }

    @Test
    fun seat() {
        snapshot { SeatPreview() }
    }

    @Test
    fun segmentedSwitch() {
        snapshot { SegmentedSwitchPreview() }
    }

    @Test
    fun seatLegend() {
        snapshot { SeatLegendPreview() }
    }

    @Test
    fun selectField() {
        snapshot { SelectFieldPreview() }
    }

    @Test
    fun stepper() {
        snapshot { StepperPreview() }
    }

    @Test
    fun surfaceCard() {
        snapshot { SurfaceCardPreview() }
    }

    @Test
    fun switch() {
        snapshot { SwitchPreview() }
    }

    @Test
    fun tag() {
        snapshot { TagPreview() }
    }

    @Test
    fun textField() {
        snapshot { TextFieldPreview() }
    }

    @Test
    fun toast() {
        snapshot { ToastPreview() }
    }

    @Test
    fun topAppBar() {
        snapshot { TopAppBarPreview() }
    }

    @Test
    fun topAppBarLarge() {
        snapshot { TopAppBarLargePreview() }
    }
}
