package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.unit.dp
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class ScaffoldTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testScrollingLazyColumn() {
        val clicked = mutableSetOf<Int>()
        composeTestRule.setContent {
            Scaffold(
                modifier = Modifier.height(300.dp),
                topBar = { TopAppBarLarge({ Text("Title") }) },
                action = { ButtonPrimary(onClick = {}) { Text("Action") } },
            ) { contentPadding ->
                LazyColumn(Modifier.testTag("list"), contentPadding = contentPadding) {
                    items(50) {
                        ListChoice(onClick = { clicked.add(it) }) {
                            Text("Item $it")
                        }
                    }
                }
            }
        }
        composeTestRule.onNodeWithTag("list").performScrollToIndex(49)
        composeTestRule.onNodeWithText("Item 49").performClick()
        assert(clicked.contains(49))
        composeTestRule.onNodeWithTag("list").performScrollToIndex(0)
        composeTestRule.onNodeWithText("Item 0").performClick()
        assert(clicked.contains(0))
    }

    @Test
    fun testScrollingColumn() {
        val clicked = mutableSetOf<Int>()
        composeTestRule.setContent {
            Scaffold(
                modifier = Modifier.height(300.dp),
                topBar = { TopAppBarLarge({ Text("Title") }) },
                action = { ButtonPrimary(onClick = {}) { Text("Action") } },
            ) { contentPadding ->
                Column(
                    Modifier
                        .testTag("list")
                        .verticalScroll(rememberScrollState())
                        .padding(contentPadding),
                ) {
                    repeat(50) {
                        ListChoice(onClick = { clicked.add(it) }) {
                            Text("Item $it")
                        }
                    }
                }
            }
        }
        composeTestRule.onNodeWithText("Item 49").performScrollTo().performClick()
        assert(clicked.contains(49))
        composeTestRule.onNodeWithText("Item 0").performScrollTo().performClick()
        assert(clicked.contains(0))
    }
}
