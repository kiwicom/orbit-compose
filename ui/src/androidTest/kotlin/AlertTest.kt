package kiwi.orbit.compose.ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import kiwi.orbit.compose.ui.controls.AlertSuccess
import kiwi.orbit.compose.ui.controls.ButtonPrimary
import kiwi.orbit.compose.ui.controls.Text
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

internal class AlertTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testBasics() {
        var clicked = false
        composeTestRule.setContent {
            AlertSuccess(
                title = {},
                content = { Text("Thank you for subscribing") },
                actions = {
                    ButtonPrimary(
                        onClick = { clicked = true },
                        modifier = Modifier.testTag("action"),
                    ) {
                        Text("Show content")
                    }
                },
            )
        }
        composeTestRule.onNodeWithTag("action").performClick()
        Assert.assertEquals(true, clicked)
    }
}
