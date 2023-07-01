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
internal class TileTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testBasics() {
        var clicked = false
        composeTestRule.setContent {
            Tile(
                title = { Text("Title") },
                onClick = { clicked = true },
                modifier = Modifier.testTag("tile"),
                description = { Text("Description") },
            )
        }
        composeTestRule.onNodeWithTag("tile").assertTextContains("Title")
        composeTestRule.onNodeWithTag("tile").assertTextContains("Description")
        composeTestRule.onNodeWithTag("tile").performClick()
        Assert.assertEquals(true, clicked)
    }
}
