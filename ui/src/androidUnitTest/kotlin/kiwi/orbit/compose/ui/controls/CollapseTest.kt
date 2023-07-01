package kiwi.orbit.compose.ui.controls

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performSemanticsAction
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
            var expanded by remember { mutableStateOf(false) }
            Collapse(
                expanded = expanded,
                onExpandChange = { expanded = it },
                title = {
                    Text(text = "Collapse title", modifier = Modifier.testTag("title"))
                },
                content = {
                    Text(text = "This is the collapsible content", modifier = Modifier.testTag("content"))
                },
                modifier = Modifier.testTag("collapse"),
            )
        }

        composeTestRule.onNodeWithTag("title", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithTag("content").assertDoesNotExist()

        composeTestRule.onNodeWithTag("collapse").performSemanticsAction(SemanticsActions.Expand)
        composeTestRule.onNodeWithTag("content").assertIsDisplayed()

        composeTestRule.onNodeWithTag("collapse").performSemanticsAction(SemanticsActions.Collapse)
        composeTestRule.onNodeWithTag("content").assertDoesNotExist()
    }
}
