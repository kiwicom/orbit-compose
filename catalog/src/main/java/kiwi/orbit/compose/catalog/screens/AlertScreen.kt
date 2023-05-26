package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.AlertCritical
import kiwi.orbit.compose.ui.controls.AlertInfo
import kiwi.orbit.compose.ui.controls.AlertInlineCritical
import kiwi.orbit.compose.ui.controls.AlertInlineInfo
import kiwi.orbit.compose.ui.controls.AlertInlineSuccess
import kiwi.orbit.compose.ui.controls.AlertInlineWarning
import kiwi.orbit.compose.ui.controls.AlertSuccess
import kiwi.orbit.compose.ui.controls.AlertWarning
import kiwi.orbit.compose.ui.controls.ButtonPrimary
import kiwi.orbit.compose.ui.controls.ButtonSecondary
import kiwi.orbit.compose.ui.controls.ButtonTextLinkPrimary
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Tab
import kiwi.orbit.compose.ui.controls.TabRow
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun AlertScreen(onNavigateUp: () -> Unit) {
    val state = rememberPagerState(0) { 3 }
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Alert") },
                onNavigateUp = onNavigateUp,
                extraContent = {
                    TabRow(selectedTabIndex = state.currentPage) {
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
                },
            )
        },
    ) { contentPadding: PaddingValues ->
        HorizontalPager(
            state = state,
            modifier = Modifier.padding(top = contentPadding.calculateTopPadding()),
        ) { tabIndex ->
            Box(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(
                        start = contentPadding.calculateStartPadding(LayoutDirection.Ltr),
                        end = contentPadding.calculateEndPadding(LayoutDirection.Ltr),
                        bottom = contentPadding.calculateBottomPadding(),
                    ),
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
    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        AlertsInlineInner(suppressed = false, withIcon = true)
        AlertsInlineInner(suppressed = false, withIcon = false)
        AlertsInlineInner(suppressed = true, withIcon = true)
        AlertsInlineInner(suppressed = true, withIcon = false)
        AlertInlineInfo(
            modifier = Modifier.padding(16.dp),
            title = { Text("Informational message which is longer than expected. We should try avoid such a long copy.") },
            action = { ButtonTextLinkPrimary("Primary", onClick = {}) },
        )
    }
}

@Composable
private fun AlertsInner(suppressed: Boolean) {
    Column(Modifier.padding(16.dp)) {
        AlertInfo(
            title = { Text("Re-check your credentials") },
            content = {
                Text(
                    buildAnnotatedString {
                        append("To avoid boarding complications, your entire name must be entered exactly as it appears in your passport/ID. ")
                        withStyle(
                            SpanStyle(
                                color = OrbitTheme.colors.content.highlight,
                                fontWeight = FontWeight.Medium,
                                textDecoration = TextDecoration.Underline,
                            ),
                        ) {
                            append("Some link")
                        }
                        append(".")
                    },
                )
            },
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

@Composable
private fun AlertsInlineInner(suppressed: Boolean, withIcon: Boolean) {
    Column(
        Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        AlertInlineInfo(
            title = { Text("Informational message") },
            action = { ButtonTextLinkPrimary("Primary", onClick = {}) },
            suppressed = suppressed,
            icon = if (withIcon) Icons.InformationCircle else null,
        )

        AlertInlineSuccess(
            title = { Text("Success message") },
            action = { ButtonTextLinkPrimary("Primary", onClick = {}) },
            suppressed = suppressed,
            icon = if (withIcon) Icons.CheckCircle else null,
        )

        AlertInlineWarning(
            title = { Text("Warning message") },
            action = { ButtonTextLinkPrimary("Primary", onClick = {}) },
            suppressed = suppressed,
            icon = if (withIcon) Icons.AlertCircle else null,
        )

        AlertInlineCritical(
            title = { Text("Critical message") },
            action = { ButtonTextLinkPrimary("Primary", onClick = {}) },
            suppressed = suppressed,
            icon = if (withIcon) Icons.AlertOctagon else null,
        )
    }
}
