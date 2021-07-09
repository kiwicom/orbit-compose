package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.catalog.SubScreen
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.ButtonCritical
import kiwi.orbit.compose.ui.controls.ButtonCriticalSubtle
import kiwi.orbit.compose.ui.controls.ButtonLink
import kiwi.orbit.compose.ui.controls.ButtonPrimary
import kiwi.orbit.compose.ui.controls.ButtonPrimarySubtle
import kiwi.orbit.compose.ui.controls.ButtonSecondary

@Composable
fun ButtonScreen(onUpClick: () -> Unit) {
    SubScreen(
        title = "Button",
        onUpClick = onUpClick,
    ) {
        ButtonScreenInner()
    }
}

@Preview
@Composable
fun ButtonScreenInner() {
    Surface {
        Column {
            val maxWidth = Modifier.fillMaxWidth()

            Block("Primary Button") {
                ButtonPrimary(onClick = {}, maxWidth) { Text("Primary Button") }
            }

            Block("Primary Subtle Button") {
                ButtonPrimarySubtle(onClick = {}, maxWidth) { Text("Primary Subtle Button") }
            }

            Block("Secondary Button") {
                ButtonSecondary(onClick = {}, maxWidth) { Text("Secondary Button") }
            }

            Block("Critical Button") {
                ButtonCritical(onClick = {}, maxWidth) { Text("Critical Button") }
            }

            Block("Critical Subtle Button") {
                ButtonCriticalSubtle(onClick = {}, maxWidth) { Text("Critical Subtle Button") }
            }

            Block("Link Button") {
                ButtonLink(onClick = {}, maxWidth) { Text("Link Button") }
            }
        }
    }
}

@Composable
fun Block(title: String, content: @Composable () -> Unit) {
    Column(Modifier.padding(16.dp)) {
        Text(text = title, style = OrbitTheme.typography.title4)
        Spacer(modifier = Modifier.height(12.dp))
        content()
    }
}
