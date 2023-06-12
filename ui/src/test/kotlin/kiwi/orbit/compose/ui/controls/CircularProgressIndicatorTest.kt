package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class CircularProgressIndicatorTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testBasics() {
        composeTestRule.setContent {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(18.dp)
                    .testTag("cpi"),
                color = OrbitTheme.colors.info.normal,
                strokeWidth = 2.dp,
            )
        }
        composeTestRule.onNodeWithTag("cpi").assertWidthIsEqualTo(18.dp)
    }
}
