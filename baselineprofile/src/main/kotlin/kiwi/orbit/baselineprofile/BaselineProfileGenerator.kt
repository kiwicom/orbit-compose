package kiwi.orbit.baselineprofile

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.filters.LargeTest
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiScrollable
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import kiwi.orbit.compose.catalog.semantics.AlertScreenSemantics
import kiwi.orbit.compose.catalog.semantics.ButtonScreenSemantics
import kiwi.orbit.compose.catalog.semantics.DialogScreenSemantics
import kiwi.orbit.compose.catalog.semantics.MainScreenSemantics
import kiwi.orbit.compose.catalog.semantics.PillButtonScreenSemantics
import kiwi.orbit.compose.catalog.semantics.SelectFieldScreenSemantics
import kiwi.orbit.compose.catalog.semantics.SubScreenSemantics
import kiwi.orbit.compose.catalog.semantics.ToastScreenSemantics
import kiwi.orbit.compose.catalog.semantics.TopAppBarScreenSemantics
import org.junit.Rule
import org.junit.Test

/**
 * This test class generates a basic startup baseline profile for the target package.
 *
 * We recommend you start with this but add important user flows to the profile to improve their performance.
 * Refer to the [baseline profile documentation](https://d.android.com/topic/performance/baselineprofiles)
 * for more information.
 *
 * You can run the generator with the Generate Baseline Profile run configuration,
 * or directly with `generateBaselineProfile` Gradle task:
 * ```
 * ./gradlew :catalog:generateReleaseBaselineProfile -Pandroid.testInstrumentationRunnerArguments.androidx.benchmark.enabledRules=BaselineProfile
 * ```
 * The run configuration runs the Gradle task and applies filtering to run only the generators.
 *
 * Check [documentation](https://d.android.com/topic/performance/benchmarking/macrobenchmark-instrumentation-args)
 * for more information about available instrumentation arguments.
 *
 * After you run the generator, you can verify the improvements running the [StartupBenchmarks] benchmark.
 **/
@LargeTest
internal class BaselineProfileGenerator {
    private companion object {
        const val TIMEOUT = 5000L
    }

    @get:Rule
    val rule = BaselineProfileRule()

    @Test
    fun generate() {
        // The rule tries to iterate several times to get stable list of classes. This is generally not needed
        // as the flow remains the same regardless how many times it is ran. More iterations are also more
        // likely to fail.
        rule.collect(
            packageName = "kiwi.orbit.compose.catalog",
            maxIterations = 1,
            stableIterations = 1,
        ) {
            pressHome()
            startActivityAndWait()

            profileSubScreen(MainScreenSemantics.ColorsItemTag)
            profileSubScreen(MainScreenSemantics.IconsItemTag)
            profileSubScreen(MainScreenSemantics.IllustrationsItemTag)
            profileSubScreen(MainScreenSemantics.TypographyItemTag)

            profileSubScreen(MainScreenSemantics.AlertItemTag) {
                device.findObject(By.res(AlertScreenSemantics.NormalTabTag)).click()
                device.findObject(By.res(AlertScreenSemantics.SuppressedTabTag)).click()
                device.findObject(By.res(AlertScreenSemantics.InlineTabTag)).click()
            }
            profileSubScreen(MainScreenSemantics.BadgeItemTag)
            profileSubScreen(MainScreenSemantics.BadgeListItemTag)
            profileSubScreen(MainScreenSemantics.ButtonItemTag) {
                device.findObject(By.res(ButtonScreenSemantics.ButtonTabTag)).click()
                device.findObject(By.res(ButtonScreenSemantics.ButtonLinkTabTag)).click()
            }
            profileSubScreen(MainScreenSemantics.CardItemTag)
            profileSubScreen(MainScreenSemantics.CheckboxItemTag)
            profileSubScreen(MainScreenSemantics.ChoiceTileItemTag)
            profileSubScreen(MainScreenSemantics.CollapseItemTag)
            profileSubScreen(MainScreenSemantics.CouponItemTag)
            profileSubScreen(MainScreenSemantics.DialogItemTag) {
                device
                    .findObject(By.res(DialogScreenSemantics.OrbitDialogButtonTag))
                    .clickAndWait(Until.newWindow(), TIMEOUT)
                device.pressBack()
                device
                    .findObject(By.res(DialogScreenSemantics.M3DialogButtonTag))
                    .clickAndWait(Until.newWindow(), TIMEOUT)
                device.pressBack()
                device
                    .findObject(By.res(DialogScreenSemantics.M3TimePickerButtonTag))
                    .clickAndWait(Until.newWindow(), TIMEOUT)
                device.pressBack()
                device
                    .findObject(By.res(DialogScreenSemantics.M3DatePickerButtonTag))
                    .clickAndWait(Until.newWindow(), TIMEOUT)
                device.pressBack()
            }
            profileSubScreen(MainScreenSemantics.EmptyStateItemTag)
            profileSubScreen(MainScreenSemantics.KeyValueItemTag)
            profileSubScreen(MainScreenSemantics.ListItemTag)
            profileSubScreen(MainScreenSemantics.ListChoiceItemTag)
            profileSubScreen(MainScreenSemantics.LoadingItemTag)
            profileSubScreen(MainScreenSemantics.PillButtonItemTag) {
                device.findObject(By.res(PillButtonScreenSemantics.ShowWithIconButtonTag)).click()
            }
            profileSubScreen(MainScreenSemantics.ProgressIndicatorItemTag)
            profileSubScreen(MainScreenSemantics.RadioItemTag)
            profileSubScreen(MainScreenSemantics.SeatItemTag)
            profileSubScreen(MainScreenSemantics.SegmentedSwitchItemTag)
            profileSubScreen(MainScreenSemantics.SelectFieldItemTag) {
                device.findObject(By.res(SelectFieldScreenSemantics.CountrySelectFieldTag)).click() // open
                device.findObject(By.res(SelectFieldScreenSemantics.CountrySelectFieldTag)).click() // close
                Thread.sleep(1000L) // back navigation is blocked until the popup is fully closed
            }
            profileSubScreen(MainScreenSemantics.SliderItemTag)
            profileSubScreen(MainScreenSemantics.StepperItemTag)
            profileSubScreen(MainScreenSemantics.SurfaceCardItemTag)
            profileSubScreen(MainScreenSemantics.SwitchItemTag)
            profileSubScreen(MainScreenSemantics.TabsItemTag)
            profileSubScreen(MainScreenSemantics.TagItemTag)
            profileSubScreen(MainScreenSemantics.TextFieldItemTag)
            profileSubScreen(MainScreenSemantics.TileItemTag)
            profileSubScreen(MainScreenSemantics.TileGroupItemTag)
            profileSubScreen(MainScreenSemantics.TimelineItemTag)
            profileSubScreen(MainScreenSemantics.ToastItemTag) {
                device.findObject(By.res(ToastScreenSemantics.ToastAddToTripWithImageButtonTag)).click()
            }
            profileSubScreen(MainScreenSemantics.TopAppBarItemTag) {
                device.findObject(By.res(TopAppBarScreenSemantics.NormalSimpleButtonTag)).click()
                device.wait(Until.hasObject(By.res(TopAppBarScreenSemantics.NormalSimpleScreenTag)), TIMEOUT)
                device.pressBack()
                device.wait(Until.hasObject(By.res(SubScreenSemantics.Tag)), TIMEOUT)
                device.findObject(By.res(TopAppBarScreenSemantics.LargeSimpleButtonTag)).click()
                device.wait(Until.hasObject(By.res(TopAppBarScreenSemantics.LargeSimpleScreenTag)), TIMEOUT)
                device.pressBack()
            }
        }
    }

    private fun MacrobenchmarkScope.profileSubScreen(
        mainScreenItemTag: String,
        profileContent: MacrobenchmarkScope.() -> Unit = {},
    ) {
        val mainScreenScrollable = UiScrollable(UiSelector().scrollable(true))
        mainScreenScrollable.scrollIntoView(UiSelector().resourceId(mainScreenItemTag))
        device.wait(Until.hasObject(By.res(mainScreenItemTag)), TIMEOUT)

        device.findObject(By.res(mainScreenItemTag)).click()
        device.wait(Until.hasObject(By.res(SubScreenSemantics.Tag)), TIMEOUT)

        profileContent()

        device.pressBack()
        device.wait(Until.hasObject(By.res(MainScreenSemantics.Tag)), TIMEOUT)
    }
}
