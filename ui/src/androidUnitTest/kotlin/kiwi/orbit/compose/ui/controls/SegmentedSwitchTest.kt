package kiwi.orbit.compose.ui.controls

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class SegmentedSwitchTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testBasics() {
        var selectedIndex: Int? = null

        composeTestRule.setContent {
            SegmentedSwitch(
                onOptionClick = { index ->
                    selectedIndex = index.takeIf { index != selectedIndex }
                },
                options = listOf(
                    { Text(modifier = Modifier.testTag("option_1"), text = "Option 1") },
                    { Text(modifier = Modifier.testTag("option_2"), text = "Option 2") },
                    { Text(modifier = Modifier.testTag("option_3"), text = "Option 3") },
                ),
                selectedIndex = selectedIndex,
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "option_1",
            useUnmergedTree = true,
        ).assertTextContains("Option 1")

        composeTestRule.onNodeWithTag(
            testTag = "option_2",
            useUnmergedTree = true,
        ).assertTextContains("Option 2")

        composeTestRule.onNodeWithTag(
            testTag = "option_3",
            useUnmergedTree = true,
        ).assertTextContains("Option 3")

        Assert.assertNull(selectedIndex)

        composeTestRule.onNodeWithTag(
            testTag = "option_1",
            useUnmergedTree = true,
        ).performClick()
        Assert.assertEquals(selectedIndex, 0)

        composeTestRule.onNodeWithTag(
            testTag = "option_2",
            useUnmergedTree = true,
        ).performClick()
        Assert.assertEquals(selectedIndex, 1)

        composeTestRule.onNodeWithTag(
            testTag = "option_3",
            useUnmergedTree = true,
        ).performClick()
        Assert.assertEquals(selectedIndex, 2)

        composeTestRule.onNodeWithTag(
            testTag = "option_3",
            useUnmergedTree = true,
        ).performClick()
        Assert.assertNull(selectedIndex)
    }
}
