package kiwi.orbit.compose.ui.controls

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertRangeInfoEquals
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class TextFieldTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testTextField() {
        composeTestRule.setContent {
            var value by remember { mutableStateOf("") }
            TextField(
                modifier = Modifier.testTag("textfield"),
                value = value,
                onValueChange = { value = it },
                info = { Text("Info") },
                error = if (value.length > 3) {
                    @Composable { Text("Error") }
                } else {
                    null
                },
                label = { Text("Label") },
                placeholder = { Text("Placeholder") },
            )
        }
        composeTestRule.waitForIdle()

        val textField = composeTestRule.onNodeWithTag("textfield")
        textField.assert(hasEditTextExactly(""))
        textField.assertTextEquals("Label", "Placeholder", includeEditableText = false)

        textField.performClick() // focus to show info
        textField.assertTextEquals("Label", "Placeholder", "Info", includeEditableText = false)

        textField.performTextReplacement("text")
        textField.assert(hasEditTextExactly("text"))
        textField.assertTextEquals("Label", "Error", includeEditableText = false)

        textField.performTextReplacement("te")
        textField.assert(hasEditTextExactly("te"))
        textField.assertTextEquals("Label", "Info", includeEditableText = false)
    }

    @Test
    fun testPasswordField() {
        composeTestRule.setContent {
            var value by remember { mutableStateOf("") }
            PasswordTextField(
                modifier = Modifier.testTag("passwordField"),
                value = value,
                onValueChange = { value = it },
                info = { Text("Info") },
                error = if (value.length < 3) {
                    @Composable { Text("Error") }
                } else {
                    null
                },
                label = { Text("Label") },
                placeholder = { Text("Placeholder") },
                strengthContent = {
                    val s = value.length.coerceIn(0..3)
                    PasswordStrengthIndicator(
                        modifier = Modifier.testTag("passwordStrength"),
                        value = (1 / 3f) * s.toFloat(),
                        color = when (s) {
                            0 -> PasswordStrengthColor.Weak
                            1 -> PasswordStrengthColor.Weak
                            2 -> PasswordStrengthColor.Medium
                            else -> PasswordStrengthColor.Strong
                        },
                        label = { Text("Strength: $s") },
                    )
                },
            )
        }
        composeTestRule.waitForIdle()
        composeTestRule.waitForIdle()

        val textField = composeTestRule.onNodeWithTag("passwordField")
        val passwordStrength = composeTestRule.onNodeWithTag("passwordStrength", useUnmergedTree = true)

        textField.assert(hasEditTextExactly(""))
        textField.assertTextEquals(
            "Label",
            "Placeholder",
            "Strength: 0",
            "Error",
            includeEditableText = false,
        )

        textField.performTextReplacement("text")
        textField.assert(hasEditTextExactly("••••"))
        textField.assertTextEquals("Label", "Strength: 3", "Info", includeEditableText = false)
        passwordStrength.onChildAt(0).assertRangeInfoEquals(ProgressBarRangeInfo(1f, 0f..1f, 0))

        textField.performTextReplacement("te")
        textField.assert(hasEditTextExactly("••"))
        textField.assertTextEquals("Label", "Strength: 2", "Error", includeEditableText = false)
        passwordStrength.onChildAt(0).assertRangeInfoEquals(ProgressBarRangeInfo(2 / 3f, 0f..1f, 0))
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
