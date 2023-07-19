package kiwi.orbit.compose.ui.controls

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class RadioTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testBasics() {
        composeTestRule.setContent {
            var selected by remember { mutableStateOf(false) }
            RadioField(
                modifier = Modifier.testTag("radio"),
                selected = selected,
                onClick = { selected = !selected },
                description = { Text("Description") },
            ) {
                Text("Label")
            }
        }
        val radio = composeTestRule.onNodeWithTag("radio")
        radio.assertIsNotSelected()
        radio.performClick()
        radio.assertIsSelected()
    }
}
