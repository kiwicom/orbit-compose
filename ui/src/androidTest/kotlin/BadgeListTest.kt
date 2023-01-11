package kiwi.orbit.compose.ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.controls.BadgeList
import kiwi.orbit.compose.ui.controls.BadgeListItemNeutral
import kiwi.orbit.compose.ui.controls.Text
import org.junit.Rule
import org.junit.Test

internal class BadgeListTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testBasics() {
        composeTestRule.setContent {
            BadgeList {
                BadgeListItemNeutral(Icons.Email) {
                    Text("Email up to date!", Modifier.testTag("Email"))
                }
            }
        }
        composeTestRule.onNodeWithTag("Email").assertTextContains("Email up to date!")
    }
}
