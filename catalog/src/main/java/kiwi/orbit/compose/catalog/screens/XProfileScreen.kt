package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kiwi.orbit.compose.catalog.Screen
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.illustrations.Illustrations
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.BadgeInfo
import kiwi.orbit.compose.ui.controls.Card
import kiwi.orbit.compose.ui.controls.Icon
import kiwi.orbit.compose.ui.controls.Separator
import kiwi.orbit.compose.ui.controls.Text

@Destination
@Composable
fun XProfileScreen(navigator: DestinationsNavigator) {
    Screen(
        title = "Profile",
        onUpClick = navigator::navigateUp,
        withBackground = false,
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(it)
        ) {
            XProfileScreenInner()
        }
    }
}

@Preview
@Composable
private fun XProfileScreenInner() {
    Column(Modifier.padding(16.dp)) {
        Image(painter = Illustrations.WomanWithPhone, contentDescription = null, Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "My Kiwi.com", style = OrbitTheme.typography.bodySmall)

        Spacer(modifier = Modifier.height(8.dp))

        Card {
            Column {
                MenuItem(
                    text = "Messages",
                    icon = Icons.Messages,
                    badge = "2"
                )
                MenuItemSeparator()
                MenuItem(
                    text = "Price Alerts",
                    icon = Icons.Notification
                )
                MenuItemSeparator()
                MenuItem(
                    text = "Refer a Friend",
                    icon = Icons.Passengers,
                    badge = "Receive 20 EUR"
                )
            }
        }
    }
}

@Composable
private fun MenuItem(
    text: String,
    icon: Painter,
    badge: String? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {})
            .padding(top = 12.dp, bottom = 12.dp, start = 16.dp, end = 16.dp)
    ) {
        Icon(icon, tint = OrbitTheme.colors.content.minor, contentDescription = text)
        Text(
            text = text,
            modifier = Modifier
                .padding(start = 16.dp)
                .align(Alignment.CenterVertically),
            overflow = TextOverflow.Ellipsis,
            style = OrbitTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
        )
        if (badge != null) {
            Spacer(Modifier.size(8.dp))
            BadgeInfo {
                Text(badge)
            }
        }
    }
}

@Composable
private fun MenuItemSeparator() {
    Separator(startIndent = 32.dp + 24.dp, thickness = 0.5.dp)
}
