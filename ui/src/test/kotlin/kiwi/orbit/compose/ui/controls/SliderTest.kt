package kiwi.orbit.compose.ui.controls

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performSemanticsAction
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class SliderTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testBasics() {
        composeTestRule.setContent {
            var value by rememberSaveable { mutableStateOf(0.0f) }
            var currentValue by rememberSaveable { mutableStateOf(0.0f) }
            Slider(
                value = currentValue,
                onValueChange = { currentValue = it },
                onValueChangeFinished = { value = currentValue },
                modifier = Modifier.testTag("slider"),
                valueLabel = {},
                startLabel = {},
                endLabel = {},
            )
        }
        val slider = composeTestRule.onNodeWithTag("slider")
        slider.performSemanticsAction(SemanticsActions.SetProgress) { it.invoke(0.5f) }
        val progressBarInfo = slider.fetchSemanticsNode().config[SemanticsProperties.ProgressBarRangeInfo]
        Assert.assertEquals(0.5f, progressBarInfo.current)
    }
}
