package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.layout.Column
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
internal class ButtonLinkTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testBasics() {
        var clicked1 = false
        var clicked2 = false
        composeTestRule.setContent {
            Column {
                ButtonLinkPrimary(onClick = { clicked1 = true }, Modifier.testTag("Button1")) {
                    Text("Update1")
                }
                ButtonTextLinkPrimary("Update2", onClick = { clicked2 = true }, Modifier.testTag("Button2"))
            }
        }

        composeTestRule.onNodeWithTag("Button1").assertTextContains("Update1")
        composeTestRule.onNodeWithTag("Button1").performClick()
        Assert.assertEquals(true, clicked1)

        composeTestRule.onNodeWithTag("Button2").assertTextContains("Update2")
        composeTestRule.onNodeWithTag("Button2").performClick()
        Assert.assertEquals(true, clicked2)
    }
}
