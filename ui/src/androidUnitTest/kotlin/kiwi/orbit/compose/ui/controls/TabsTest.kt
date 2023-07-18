package kiwi.orbit.compose.ui.controls

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class TabsTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testTabRow() {
        composeTestRule.setContent {
            var selectedPage by remember { mutableStateOf(0) }
            TabRow(selectedTabIndex = selectedPage) {
                Tab(
                    modifier = Modifier.testTag("variantA"),
                    selected = selectedPage == 0,
                    onClick = { selectedPage = 0 },
                    text = { Text("Variant A") },
                )
                Tab(
                    modifier = Modifier.testTag("variantB"),
                    selected = selectedPage == 1,
                    onClick = { selectedPage = 1 },
                    text = { Text("Variant B") },
                )
            }
        }
        composeTestRule.onNodeWithTag("variantB").performClick().assertIsSelected()
    }

    @Test
    fun testScrollableTabRow() {
        composeTestRule.setContent {
            var selectedPage by remember { mutableStateOf(0) }
            ScrollableTabRow(selectedTabIndex = selectedPage) {
                Tab(
                    modifier = Modifier.testTag("variantA"),
                    selected = selectedPage == 0,
                    onClick = { selectedPage = 0 },
                    text = { Text("Variant A") },
                )
                Tab(
                    modifier = Modifier.testTag("variantB"),
                    selected = selectedPage == 1,
                    onClick = { selectedPage = 1 },
                    text = { Text("Variant B") },
                )
                Tab(
                    modifier = Modifier.testTag("variantC"),
                    selected = selectedPage == 2,
                    onClick = { selectedPage = 2 },
                    text = { Text("Variant C") },
                )
                Tab(
                    modifier = Modifier.testTag("variantD"),
                    selected = selectedPage == 3,
                    onClick = { selectedPage = 3 },
                    text = { Text("Variant D") },
                )
                Tab(
                    modifier = Modifier.testTag("variantE"),
                    selected = selectedPage == 4,
                    onClick = { selectedPage = 4 },
                    text = { Text("Variant E") },
                )
            }
        }
        composeTestRule.onNodeWithTag("variantE").performScrollTo().performClick().assertIsSelected()
    }
}
