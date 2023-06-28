package kiwi.orbit.compose.ui.controls

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class SelectTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testBasics() {
        composeTestRule.setContent {
            var selected by remember { mutableStateOf("A") }
            SelectField(
                modifier = Modifier.testTag("select"),
                value = selected,
                options = listOf("A", "B", "C"),
                onOptionSelect = { selected = it },
                label = { Text("Pick letter") },
            ) { letter ->
                Text(letter, modifier = Modifier.testTag("option$letter"))
            }
        }
        val select = composeTestRule.onNodeWithTag("select")
        select.assert(hasEditTextExactly("A"))
        select.performClick()
        composeTestRule.onNodeWithTag("optionB", useUnmergedTree = true).performClick()
        select.assert(hasEditTextExactly("B"))
    }

    private fun hasEditTextExactly(expected: String): SemanticsMatcher {
        val propertyName = SemanticsProperties.EditableText.name
        return SemanticsMatcher(
            "$propertyName = [$expected]",
        ) { node ->
            val actual = node.config.getOrNull(SemanticsProperties.EditableText)
            actual?.text == expected
        }
    }
}
