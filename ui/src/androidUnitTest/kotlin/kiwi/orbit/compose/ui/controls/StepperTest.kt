package kiwi.orbit.compose.ui.controls

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performSemanticsAction
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class StepperTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testBasics() {
        composeTestRule.setContent {
            var value by remember { mutableIntStateOf(0) }
            Stepper(
                modifier = Modifier.testTag("stepper"),
                value = value,
                onValueChange = { value = it },
            )
        }
        val stepper = composeTestRule.onNodeWithTag("stepper")

        assertFalse(stepper.fetchSemanticsNode().config.contains(StepperSemanticsActions.DecreaseValue))
        assertTrue(stepper.fetchSemanticsNode().config.contains(StepperSemanticsActions.IncreaseValue))

        stepper
            .assertTextEquals("0")
            .performSemanticsAction(StepperSemanticsActions.IncreaseValue)
            .assertTextEquals("1")
            .performSemanticsAction(StepperSemanticsActions.IncreaseValue)
            .assertTextEquals("2")
            .performSemanticsAction(StepperSemanticsActions.DecreaseValue)
            .assertTextEquals("1")

        assertTrue(stepper.fetchSemanticsNode().config.contains(StepperSemanticsActions.DecreaseValue))
        assertTrue(stepper.fetchSemanticsNode().config.contains(StepperSemanticsActions.IncreaseValue))
    }
}
