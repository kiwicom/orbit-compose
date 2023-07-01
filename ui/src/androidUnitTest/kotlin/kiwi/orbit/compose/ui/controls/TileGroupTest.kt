package kiwi.orbit.compose.ui.controls

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class TileGroupTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testBasics() {
        var clicked = false
        composeTestRule.setContent {
            TileGroup(
                modifier = Modifier.testTag("tileGroup"),
                separatorStartIndent = 12.dp,
            ) {
                Tile(
                    modifier = Modifier.testTag("tile"),
                    title = { Text("Title") },
                    onClick = { clicked = true },
                    description = { Text("Description") },
                )
            }
        }
        composeTestRule.onNodeWithTag("tileGroup").onChildAt(0).assertTextContains("Title")
        composeTestRule.onNodeWithTag("tileGroup").onChildAt(0).assertTextContains("Description")
        composeTestRule.onNodeWithTag("tileGroup").onChildAt(0).performClick()
        Assert.assertEquals(true, clicked)

        composeTestRule.onNodeWithTag("tile").assertTextContains("Title")
    }
}
