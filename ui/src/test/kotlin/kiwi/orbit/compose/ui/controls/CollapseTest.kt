package kiwi.orbit.compose.ui.controls

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class CollapseTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testBasics() {
        composeTestRule.setContent {
            var isExpended by remember { mutableStateOf(false) }
            Collapse(
                isExpended = isExpended,
                onExpandChange = { isExpended = it },
                header = {
                    Text(text = "This the header", modifier = Modifier.testTag("header"))
                },
                body = {
                    Text(text = "This is the collapsible body", modifier = Modifier.testTag("body"))
                },
            )
        }

        composeTestRule.onNodeWithTag("header").assertIsDisplayed()
        composeTestRule.onNodeWithTag("body").assertIsNotDisplayed()
        composeTestRule.onNodeWithTag("collapse_arrow").assertIsDisplayed()

        composeTestRule.onNodeWithTag("collapse_arrow").performClick()

        composeTestRule.onNodeWithTag("body").assertIsDisplayed()
    }
}
