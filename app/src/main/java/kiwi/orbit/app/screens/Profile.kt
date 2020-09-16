package kiwi.orbit.app.screens

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.VectorAsset
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kiwi.orbit.OrbitTheme
import kiwi.orbit.foundation.ColorTokens
import kiwi.orbit.icons.Icon
import kiwi.orbit.icons.Messages
import kiwi.orbit.icons.Notification
import kiwi.orbit.icons.Passengers

@Composable
fun ProfileScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "My Kiwi.com", style = OrbitTheme.typography.bodySmall)

        Spacer(modifier = Modifier.height(8.dp))

        Card {
            Column {
                MenuItem(
                    text = "Messages",
                    icon = Icon.Messages
                )
                MenuItemSeparator()
                MenuItem(
                    text = "Price Alerts",
                    icon = Icon.Notification
                )
                MenuItemSeparator()
                MenuItem(
                    text = "Refer a Friend",
                    icon = Icon.Passengers
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column {
                Switch(checked = true, onCheckedChange = {})
                Checkbox(checked = true, onCheckedChange = {})
                RadioButton(selected = true, onClick = {})
            }
        }
    }
}


@Composable
fun MenuItem(
    text: String,
    icon: VectorAsset,
) {
    Row(
        modifier = Modifier
            .clickable(onClick = {})
            .padding(top = 12.dp, bottom = 12.dp, start = 16.dp, end = 16.dp)
    ) {
        Icon(asset = icon, tint = ColorTokens.InkLight)
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
