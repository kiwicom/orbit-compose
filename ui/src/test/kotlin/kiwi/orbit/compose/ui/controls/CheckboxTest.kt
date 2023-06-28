package kiwi.orbit.compose.ui.controls

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class CheckboxTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testBasics() {
        composeTestRule.setContent {
            var checked by remember { mutableStateOf(false) }
            CheckboxField(
                modifier = Modifier.testTag("checkbox"),
                checked = checked,
                onCheckedChange = { checked = !checked },
                description = { Text("Description") },
            ) {
                Text("Label")
            }
        }
        val checkbox = composeTestRule.onNodeWithTag("checkbox")
        checkbox.assertIsOff()
        checkbox.performClick()
        checkbox.assertIsOn()
    }
}
