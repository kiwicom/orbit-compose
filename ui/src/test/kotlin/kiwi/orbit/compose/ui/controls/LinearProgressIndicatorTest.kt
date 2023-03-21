package kiwi.orbit.compose.ui.controls

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class LinearProgressIndicatorTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testBasics() {
        composeTestRule.setContent {
            LinearProgressIndicator(
                progress = 0.4f,
                modifier = Modifier.testTag("loader"),
            )
        }
        val progressBarInfo = composeTestRule
            .onNodeWithTag("loader")
            .fetchSemanticsNode()
            .config[SemanticsProperties.ProgressBarRangeInfo]
        Assert.assertEquals(0.4f, progressBarInfo.current)
    }
}
