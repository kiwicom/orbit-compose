package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Tab
import kiwi.orbit.compose.ui.controls.TabRow
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
internal fun TabsScreen(onNavigateUp: () -> Unit) {
    val pagerState = rememberPagerState(0)
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tabs") },
                onNavigateUp = onNavigateUp,
                extraContent = {
                    TabRow(selectedTabIndex = pagerState.currentPage) {
                        Tab(
                            selected = pagerState.currentPage == 0,
                            onClick = { scope.launch { pagerState.animateScrollToPage(0) } },
                            text = { Text("Variant A") },
                        )
                        Tab(
                            selected = pagerState.currentPage == 1,
                            onClick = { scope.launch { pagerState.animateScrollToPage(1) } },
                            text = { Text("Variant B") },
                        )
                        Tab(
                            selected = pagerState.currentPage == 2,
                            onClick = { scope.launch { pagerState.animateScrollToPage(2) } },
                            text = { Text("Variant C") },
                        )
                    }
                },
            )
        },
    ) { contentPadding: PaddingValues ->
        HorizontalPager(
            count = 3,
            state = pagerState,
        ) {
            when (it) {
                0 -> CustomContentPlaceholder(contentPadding, text = "Variant A")
                1 -> CustomContentPlaceholder(contentPadding, text = "Variant B")
                2 -> CustomContentPlaceholder(contentPadding, text = "Variant C")
            }
        }
    }
}

@Preview
@Composable
private fun TagScreenPreview() {
    TabsScreen({})
}
