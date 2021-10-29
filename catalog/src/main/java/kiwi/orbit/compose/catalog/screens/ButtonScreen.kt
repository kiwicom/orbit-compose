package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.catalog.Screen
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.ButtonCritical
import kiwi.orbit.compose.ui.controls.ButtonCriticalSubtle
import kiwi.orbit.compose.ui.controls.ButtonLink
import kiwi.orbit.compose.ui.controls.ButtonPrimary
import kiwi.orbit.compose.ui.controls.ButtonPrimarySubtle
import kiwi.orbit.compose.ui.controls.ButtonSecondary
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.foundation.ProvideColors
import kiwi.orbit.compose.ui.foundation.asInteractiveTheme

@Composable
fun ButtonScreen(onUpClick: () -> Unit) {
    Screen(
        title = "Button",
        onUpClick = onUpClick,
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(it)
        ) {
            ButtonScreenInner()
        }
    }
}

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
        ButtonLink(onClick = {}, maxWidth) { Text("Link Button") }

        Spacer(Modifier.height(16.dp))
        Text("Manually themed")
        Spacer(Modifier.height(8.dp))

        ProvideColors(OrbitTheme.colors.asInteractiveTheme()) {
            ButtonPrimary(onClick = {}, maxWidth) { Text("Interactive Button") }
        }
    }
}
