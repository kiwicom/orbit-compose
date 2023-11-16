package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.catalog.AppTheme
import kiwi.orbit.compose.catalog.semantics.SubScreenSemantics
import kiwi.orbit.compose.icons.IconName
import kiwi.orbit.compose.ui.controls.Coupon
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBar
import kiwi.orbit.compose.ui.controls.rememberToastHostState

@Composable
internal fun CouponScreen(
    onNavigateUp: () -> Unit,
) {
    val toastHostState = rememberToastHostState()
    Scaffold(
        modifier = Modifier.testTag(SubScreenSemantics.Tag),
        topBar = {
            TopAppBar(
                title = { Text("Coupon") },
                onNavigateUp = onNavigateUp,
            )
        },
        toastHostState = toastHostState,
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding),
        ) {
            CouponScreenInner(
                onCouponCopied = {
                    toastHostState.showToast("Copied to clipboard!", IconName.Copy)
                },
            )
        }
    }
}

@Composable
private fun CouponScreenInner(
    onCouponCopied: () -> Unit,
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = "Your coupon:",
                modifier = Modifier.alignByBaseline(),
            )
            Coupon(
                code = "hxt3b81f",
                onCopied = null,
                modifier = Modifier.alignByBaseline(),
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = "Copy your coupon:",
                modifier = Modifier.alignByBaseline(),
            )
            Coupon(
                code = "hxt3b81f",
                onCopied = onCouponCopied,
                modifier = Modifier.alignByBaseline(),
            )
        }
    }
}

@Preview
@Composable
private fun CouponScreenPreview() {
    AppTheme {
        CouponScreen(onNavigateUp = {})
    }
}
