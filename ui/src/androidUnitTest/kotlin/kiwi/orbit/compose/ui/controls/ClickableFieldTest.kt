package kiwi.orbit.compose.ui.controls

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
internal class ClickableFieldTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testBasics() {
        var clicked by mutableIntStateOf(0)
        composeTestRule.setContent {
            ClickableField(
                modifier = Modifier.testTag("clickable"),
                value = if (clicked > 0) "Clicked" else "Empty",
                label = { Text("Pick letter") },
                error = if (clicked > 1) {
                    @Composable { Text("Err") }
                } else {
                    null
                },
                onClick = { clicked += 1 },
            )
        }
        val clickable = composeTestRule.onNodeWithTag("clickable")
        clickable.assert(hasEditTextExactly("Empty"))
        clickable.assert(hasErrorText(null))
        clickable.performClick()
        clickable.assert(hasEditTextExactly("Clicked"))
        clickable.assert(hasErrorText(null))
        clickable.performClick()
        clickable.assert(hasErrorText("Invalid input"))
    }

    private fun hasErrorText(expected: String?): SemanticsMatcher {
        val propertyName = SemanticsProperties.Error.name
        return SemanticsMatcher(
            "$propertyName = [$expected]",
        ) { node ->
            val actual = node.config.getOrNull(SemanticsProperties.Error)
            actual == expected
        }
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
