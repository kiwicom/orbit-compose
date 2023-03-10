package kiwi.orbit.compose.catalog.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.ButtonBundleBasic
import kiwi.orbit.compose.ui.controls.ButtonBundleMedium
import kiwi.orbit.compose.ui.controls.ButtonBundleTop
import kiwi.orbit.compose.ui.controls.ButtonCritical
import kiwi.orbit.compose.ui.controls.ButtonCriticalSubtle
import kiwi.orbit.compose.ui.controls.ButtonLinkCritical
import kiwi.orbit.compose.ui.controls.ButtonLinkPrimary
import kiwi.orbit.compose.ui.controls.ButtonLinkSecondary
import kiwi.orbit.compose.ui.controls.ButtonPrimary
import kiwi.orbit.compose.ui.controls.ButtonPrimarySubtle
import kiwi.orbit.compose.ui.controls.ButtonPrimitive
import kiwi.orbit.compose.ui.controls.ButtonSecondary
import kiwi.orbit.compose.ui.controls.ButtonTextLinkCritical
import kiwi.orbit.compose.ui.controls.ButtonTextLinkPrimary
import kiwi.orbit.compose.ui.controls.ButtonToggleContainer
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.SurfaceCard
import kiwi.orbit.compose.ui.controls.Tab
import kiwi.orbit.compose.ui.controls.TabRow
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ButtonScreen(onNavigateUp: () -> Unit) {
    val state = rememberPagerState(0)
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Buttons") },
                onNavigateUp = onNavigateUp,
                extraContent = {
                    TabRow(selectedTabIndex = state.currentPage) {
                        Tab(
                            selected = state.currentPage == 0,
                            onClick = { scope.launch { state.animateScrollToPage(0) } },
                            text = { Text("Button") },
                        )
                        Tab(
                            selected = state.currentPage == 1,
                            onClick = { scope.launch { state.animateScrollToPage(1) } },
                            text = { Text("ButtonLink") },
                        )
                    }
                },
            )
        },
    ) { contentPadding ->
        HorizontalPager(
            pageCount = 2,
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
                    0 -> ButtonScreenInner()
                    1 -> ButtonLinkScreenInner()
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Preview
@Composable
private fun ButtonScreenInner() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(16.dp),
    ) {
        val maxWidth = Modifier.fillMaxWidth()

        ButtonPrimary(onClick = {}, maxWidth) { Text("Primary Button") }
        ButtonPrimarySubtle(onClick = {}, maxWidth) { Text("Primary Subtle Button") }
        ButtonSecondary(onClick = {}, maxWidth) { Text("Secondary Button") }
        ButtonCritical(onClick = {}, maxWidth) { Text("Critical Button") }
        ButtonCriticalSubtle(onClick = {}, maxWidth) { Text("Critical Subtle Button") }
        ButtonBundleBasic(onClick = {}, maxWidth) { Text("Bundle Basic Button") }
        ButtonBundleMedium(onClick = {}, maxWidth) { Text("Bundle Medium Button") }
        ButtonBundleTop(onClick = {}, maxWidth) { Text("Bundle Top Button") }

        Text("Manually themed", Modifier.padding(top = 16.dp))

        ButtonPrimitive(
            onClick = {},
            modifier = maxWidth,
            backgroundColor = OrbitTheme.colors.info.normal,
        ) {
            Text("Info Button")
        }

        Text("Button Toggling", Modifier.padding(top = 16.dp))
        var targetState by rememberSaveable { mutableStateOf(true) }
        ButtonToggleContainer(targetState) { state ->
            if (state) {
                ButtonPrimarySubtle(onClick = { targetState = !targetState }, maxWidth) {
                    Text("Add Service")
                }
            } else {
                ButtonSecondary(onClick = { targetState = !targetState }, maxWidth) {
                    Text("Remove Service")
                }
            }
        }
    }
}

@Composable
private fun ButtonLinkScreenInner() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(16.dp),
    ) {
        ButtonLinkPrimary(onClick = {}, Modifier.fillMaxWidth()) { Text("Primary ButtonLink") }
        ButtonLinkSecondary(onClick = {}, Modifier.fillMaxWidth()) { Text("Secondary ButtonLink") }
        ButtonLinkCritical(onClick = {}, Modifier.fillMaxWidth()) { Text("Critical ButtonLink") }

        SurfaceCard {
            Column(Modifier.padding(16.dp)) {
                Text(
                    text = "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Nullam sit amet magna in magna gravida vehicula. Nam quis nulla. Nam sed tellus id magna elementum tincidunt.",
                )
                Spacer(Modifier.size(8.dp))
                ButtonTextLinkPrimary("Translate", onClick = {})
            }
        }

        SurfaceCard {
            Row(Modifier.padding(16.dp)) {
                Text(
                    text = "Title",
                    style = OrbitTheme.typography.title3,
                    modifier = Modifier
                        .alignByBaseline()
                        .weight(1f),
                )
                Spacer(Modifier.size(8.dp))
                ButtonTextLinkCritical("Delete", onClick = {}, modifier = Modifier.alignByBaseline())
            }
        }
    }
}
