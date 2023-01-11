package kiwi.orbit.compose.ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import kiwi.orbit.compose.ui.controls.ButtonPrimary
import kiwi.orbit.compose.ui.controls.Text
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

internal class ButtonTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testBasics() {
        var clicked = false
        composeTestRule.setContent {
            ButtonPrimary(onClick = { clicked = true }, Modifier.testTag("Button")) { Text("Update") }
        }
        composeTestRule.onNodeWithTag("Button").assertTextContains("Update")
        composeTestRule.onNodeWithTag("Button").performClick()
        Assert.assertEquals(true, clicked)
    }
}
