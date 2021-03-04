package kiwi.orbit.app.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.OrbitTheme
import kiwi.orbit.app.SubScreen
import kiwi.orbit.foundation.ColorTokens
import kiwi.orbit.icons.Icons
import kiwi.orbit.illustrations.Illustrations

@Composable
fun XProfileScreen(onUpClick: () -> Unit) {
    SubScreen(
        title = "Profile",
        onUpClick = onUpClick,
        withBackground = false,
    ) {
        XProfileScreen()
    }
}

@Preview
@Composable
fun XProfileScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Image(painter = Illustrations.WomanWithPhone, contentDescription = null, Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "My Kiwi.com", style = OrbitTheme.typography.bodySmall)

        Spacer(modifier = Modifier.height(8.dp))

        Card {
            Column {
                MenuItem(
                    text = "Messages",
                    icon = Icons.Messages
                )
                MenuItemSeparator()
                MenuItem(
                    text = "Price Alerts",
                    icon = Icons.Notification
                )
                MenuItemSeparator()
                MenuItem(
                    text = "Refer a Friend",
                    icon = Icons.Passengers
                )
            }
        }
    }
}

@Composable
fun MenuItem(
    text: String,
    icon: Painter,
) {
    Row(
        modifier = Modifier
            .clickable(onClick = {})
            .padding(top = 12.dp, bottom = 12.dp, start = 16.dp, end = 16.dp)
    ) {
        Icon(icon, tint = ColorTokens.InkLight, contentDescription = text)
        Text(
            text = text,
            modifier = Modifier
                .padding(start = 16.dp)
                .align(Alignment.CenterVertically)
                .fillMaxWidth(),
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun MenuItemSeparator() {
    Divider(startIndent = 32.dp + 24.dp, thickness = 0.5.dp)
}
