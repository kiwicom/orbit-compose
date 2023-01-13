package kiwi.orbit.compose.ui.controls

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import kiwi.orbit.compose.icons.Icons
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
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
