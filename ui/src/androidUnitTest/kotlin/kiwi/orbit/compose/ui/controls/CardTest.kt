package kiwi.orbit.compose.ui.controls

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class CardTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testBasics() {
        composeTestRule.setContent {
            Card(
                title = { Text("Card title", Modifier.testTag("title")) },
                action = { ButtonTextLinkPrimary("Action", onClick = {}, Modifier.testTag("action")) },
            ) {
                Text("Content", Modifier.testTag("content"))
            }
        }
        composeTestRule.onNodeWithTag("title").assertTextEquals("Card title")
        composeTestRule.onNodeWithTag("action").assertTextEquals("Action")
        composeTestRule.onNodeWithTag("content").assertTextEquals("Content")
    }
}
