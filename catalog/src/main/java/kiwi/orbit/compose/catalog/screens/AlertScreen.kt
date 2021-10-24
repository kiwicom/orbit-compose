package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kiwi.orbit.compose.catalog.Screen
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.AlertCritical
import kiwi.orbit.compose.ui.controls.AlertInfo
import kiwi.orbit.compose.ui.controls.AlertSuccess
import kiwi.orbit.compose.ui.controls.AlertWarning
import kiwi.orbit.compose.ui.controls.ButtonPrimary
import kiwi.orbit.compose.ui.controls.ButtonSecondary
import kiwi.orbit.compose.ui.controls.Text
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AlertScreen(onUpClick: () -> Unit) {
    Screen(
        title = "Alert",
        onUpClick = onUpClick,
        topAppBarElevation = 0.dp,
    ) {
        Column {
            val state = rememberPagerState(0)
            val scope = rememberCoroutineScope()
            TabRow(
                modifier = Modifier
                    .shadow(2.dp)
                    .zIndex(1f),
                selectedTabIndex = state.currentPage,
                backgroundColor = OrbitTheme.colors.surface.main,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[state.currentPage]),
                        color = OrbitTheme.colors.primary.main,
                    )
                },
                divider = {},
            ) {
                Tab(
                    selected = state.currentPage == 0,
                    onClick = { scope.launch { state.animateScrollToPage(0) } },
                    text = { Text("Normal") },
                )
                Tab(
                    selected = state.currentPage == 1,
                    onClick = { scope.launch { state.animateScrollToPage(1) } },
                    text = { Text("Suppressed") },
                )
                Tab(
                    selected = state.currentPage == 2,
                    onClick = { scope.launch { state.animateScrollToPage(2) } },
                    text = { Text("Inline") },
                )
            }

            HorizontalPager(
                count = 3,
                state = state,
                modifier = Modifier.fillMaxSize()
            ) { tabIndex ->
                Box(
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(it)
                ) {
                    when (tabIndex) {
                        0 -> AlertScreenNormalInner()
                        1 -> AlertScreenSuppressedInner()
                        2 -> AlertScreenInlineInner()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun AlertScreenNormalInner() {
    AlertsInner(suppressed = false)
}

@Preview
@Composable
private fun AlertScreenSuppressedInner() {
    AlertsInner(suppressed = true)
}

@Preview
@Composable
private fun AlertScreenInlineInner() {
}

@Composable
private fun AlertsInner(suppressed: Boolean) {
    Column(Modifier.padding(16.dp)) {
        AlertInfo(
            title = { Text("Re-check your credentials") },
            content = { Text("To avoid boarding complications, your entire name must be entered exactly as it appears in your passport/ID.") },
            actions = {
                ButtonPrimary(onClick = {}) {
                    Text("More info")
                }
                ButtonSecondary(onClick = {}) {
                    Text("Mark as checked")
                }
            },
            suppressed = suppressed,
        )

        Spacer(Modifier.height(16.dp))

        AlertSuccess(
            title = { Text("Your payment was successful.") },
            content = { Text("View the receipt in the profile:") },
            actions = {
                ButtonPrimary(onClick = {}) {
                    Text("Show receipt")
                }
                ButtonSecondary(onClick = {}) {
                    Text("Share receipt")
                }
            },
            suppressed = suppressed,
        )

        Spacer(Modifier.height(16.dp))

        AlertWarning(
            title = { Text("Visa requirements check") },
            content = { Text("The requirements found here are for reference purposes only. Contact the embassy or your foreign ministry for more information.") },
            actions = {
                ButtonPrimary(onClick = {}) {
                    Text("Check requirements")
                }
                ButtonSecondary(onClick = {}) {
                    Text("Mark as checked")
                }
            },
            suppressed = suppressed,
        )

        Spacer(Modifier.height(16.dp))

        AlertCritical(
            title = { Text("No results loaded") },
            content = { Text("There was an error while processing your request. Refresh the page to load the results.") },
            actions = {
                ButtonPrimary(onClick = {}) {
                    Text("Refresh page")
                }
                ButtonSecondary(onClick = {}) {
                    Text("Contact support")
                }
            },
            suppressed = suppressed,
        )

        Spacer(Modifier.height(16.dp))
        Text("Alternative versions")
        Spacer(Modifier.height(8.dp))

        AlertInfo(
            title = { Text("Re-check your credentials") },
            content = { Text("To avoid boarding complications, your entire name must be entered exactly as it appears in your passport/ID.") },
            actions = {
                ButtonPrimary(onClick = {}) {
                    Text("More info")
                }
            },
            suppressed = suppressed,
        )

        Spacer(Modifier.height(16.dp))

        AlertInfo(
            title = { Text("Re-check your credentials") },
            content = { Text("To avoid boarding complications, your entire name must be entered exactly as it appears in your passport/ID.") },
            actions = {
                ButtonSecondary(onClick = {}) {
                    Text("Mark as checked")
                }
            },
            suppressed = suppressed,
        )

        Spacer(Modifier.height(16.dp))

        AlertInfo(
            title = { Text("Re-check your credentials") },
            content = { Text("To avoid boarding complications, your entire name must be entered exactly as it appears in your passport/ID.") },
            suppressed = suppressed,
        )

        Spacer(Modifier.height(16.dp))

        AlertInfo(
            icon = null,
            title = { Text("Re-check your credentials") },
            content = { Text("To avoid boarding complications, your entire name must be entered exactly as it appears in your passport/ID.") },
            actions = {
                ButtonPrimary(onClick = {}) {
                    Text("More info")
                }
                ButtonSecondary(onClick = {}) {
                    Text("Mark as checked")
                }
            },
            suppressed = suppressed,
        )

        Spacer(Modifier.height(16.dp))

        AlertInfo(
            icon = null,
            title = { Text("Re-check your credentials") },
            content = {
                Text("To avoid boarding complications, your entire name must be entered exactly as it appears in your passport/ID.")
                Text("Alternatively, provide a second paragraph.")
            },
            actions = {
                ButtonSecondary(onClick = {}) {
                    Text("Mark as checked")
                }
            },
            suppressed = suppressed,
        )

        Spacer(Modifier.height(16.dp))

        AlertInfo(
            icon = null,
            title = { Text("Re-check your credentials") },
            content = { Text("To avoid boarding complications, your entire name must be entered exactly as it appears in your passport/ID.") },
            suppressed = suppressed,
        )

        Spacer(Modifier.height(16.dp))

        AlertInfo(
            icon = null,
            title = { Text("Re-check your credentials") },
            content = { },
            suppressed = suppressed,
        )

        Spacer(Modifier.height(16.dp))

        AlertInfo(
            icon = null,
            title = { },
            content = { Text("To avoid boarding complications, your entire name must be entered exactly as it appears in your passport/ID.") },
            suppressed = suppressed,
        )
    }
}
