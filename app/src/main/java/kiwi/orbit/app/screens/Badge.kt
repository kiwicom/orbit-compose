package kiwi.orbit.app.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.app.SubScreen
import kiwi.orbit.controls.BadgeCritical
import kiwi.orbit.controls.BadgeCriticalSubtle
import kiwi.orbit.controls.BadgeInfo
import kiwi.orbit.controls.BadgeInfoSubtle
import kiwi.orbit.controls.BadgeNeutral
import kiwi.orbit.controls.BadgeNeutralSubtle
import kiwi.orbit.controls.BadgeSecondary
import kiwi.orbit.controls.BadgeSuccess
import kiwi.orbit.controls.BadgeSuccessSubtle
import kiwi.orbit.controls.BadgeWarning
import kiwi.orbit.controls.BadgeWarningSubtle
import kiwi.orbit.icons.Icons

@Composable
fun BadgeScreen(onUpClick: () -> Unit) {
    SubScreen(
        title = "Badge",
        onUpClick = onUpClick,
    ) {
        BadgeScreen()
    }
}

@Preview
@Composable
fun BadgeScreen() {
    Surface {
        Column(
            Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row {
                BadgeNeutral {
                    Text(text = "label")
                }
                Spacer(modifier = Modifier.size(8.dp))
                BadgeNeutral(
                    icon = { Icon(painter = Icons.Airplane, contentDescription = null) }
                ) {
                    Text(text = "label")
                }
                Spacer(modifier = Modifier.size(8.dp))
                BadgeNeutral(
                    icon = { Icon(painter = Icons.Airplane, contentDescription = null) }
                ) {
                }
            }
            Spacer(modifier = Modifier.size(8.dp))
            Row {
                BadgeNeutralSubtle {
                    Text(text = "label")
                }
                Spacer(modifier = Modifier.size(8.dp))
                BadgeNeutralSubtle(
                    icon = { Icon(painter = Icons.Airplane, contentDescription = null) }
                ) {
                    Text(text = "label")
                }
                Spacer(modifier = Modifier.size(8.dp))
                BadgeNeutralSubtle(
                    icon = { Icon(painter = Icons.Airplane, contentDescription = null) }
                ) {
                }
            }

            Spacer(modifier = Modifier.size(32.dp))

            Row {
                BadgeSecondary {
                    Text(text = "label")
                }
                Spacer(modifier = Modifier.size(8.dp))
                BadgeSecondary(
                    icon = { Icon(painter = Icons.Airplane, contentDescription = null) }
                ) {
                    Text(text = "label")
                }
                Spacer(modifier = Modifier.size(8.dp))
                BadgeSecondary(
                    icon = { Icon(painter = Icons.Airplane, contentDescription = null) }
                ) {
                }
            }

            Spacer(modifier = Modifier.size(32.dp))

            Row {
                BadgeInfo {
                    Text(text = "label")
                }
                Spacer(modifier = Modifier.size(8.dp))
                BadgeInfo(
                    icon = { Icon(painter = Icons.Airplane, contentDescription = null) }
                ) {
                    Text(text = "label")
                }
                Spacer(modifier = Modifier.size(8.dp))
                BadgeInfo(
                    icon = { Icon(painter = Icons.Airplane, contentDescription = null) }
                ) {
                }
            }
            Spacer(modifier = Modifier.size(8.dp))
            Row {
                BadgeInfoSubtle {
                    Text(text = "label")
                }
                Spacer(modifier = Modifier.size(8.dp))
                BadgeInfoSubtle(
                    icon = { Icon(painter = Icons.Airplane, contentDescription = null) }
                ) {
                    Text(text = "label")
                }
                Spacer(modifier = Modifier.size(8.dp))
                BadgeInfoSubtle(
                    icon = { Icon(painter = Icons.Airplane, contentDescription = null) }
                ) {
                }
            }

            Spacer(modifier = Modifier.size(32.dp))

            Row {
                BadgeSuccess {
                    Text(text = "label")
                }
                Spacer(modifier = Modifier.size(8.dp))
                BadgeSuccess(
                    icon = { Icon(painter = Icons.Airplane, contentDescription = null) }
                ) {
                    Text(text = "label")
                }
                Spacer(modifier = Modifier.size(8.dp))
                BadgeSuccess(
                    icon = { Icon(painter = Icons.Airplane, contentDescription = null) }
                ) {
                }
            }
            Spacer(modifier = Modifier.size(8.dp))
            Row {
                BadgeSuccessSubtle {
                    Text(text = "label")
                }
                Spacer(modifier = Modifier.size(8.dp))
                BadgeSuccessSubtle(
                    icon = { Icon(painter = Icons.Airplane, contentDescription = null) }
                ) {
                    Text(text = "label")
                }
                Spacer(modifier = Modifier.size(8.dp))
                BadgeSuccessSubtle(
                    icon = { Icon(painter = Icons.Airplane, contentDescription = null) }
                ) {
                }
            }

            Spacer(modifier = Modifier.size(32.dp))

            Row {
                BadgeWarning {
                    Text(text = "label")
                }
                Spacer(modifier = Modifier.size(8.dp))
                BadgeWarning(
                    icon = { Icon(painter = Icons.Airplane, contentDescription = null) }
                ) {
                    Text(text = "label")
                }
                Spacer(modifier = Modifier.size(8.dp))
                BadgeWarning(
                    icon = { Icon(painter = Icons.Airplane, contentDescription = null) }
                ) {
                }
            }
            Spacer(modifier = Modifier.size(8.dp))
            Row {
                BadgeWarningSubtle {
                    Text(text = "label")
                }
                Spacer(modifier = Modifier.size(8.dp))
                BadgeWarningSubtle(
                    icon = { Icon(painter = Icons.Airplane, contentDescription = null) }
                ) {
                    Text(text = "label")
                }
                Spacer(modifier = Modifier.size(8.dp))
                BadgeWarningSubtle(
                    icon = { Icon(painter = Icons.Airplane, contentDescription = null) }
                ) {
                }
            }

            Spacer(modifier = Modifier.size(32.dp))

            Row {
                BadgeCritical {
                    Text(text = "label")
                }
                Spacer(modifier = Modifier.size(8.dp))
                BadgeCritical(
                    icon = { Icon(painter = Icons.Airplane, contentDescription = null) }
                ) {
                    Text(text = "label")
                }
                Spacer(modifier = Modifier.size(8.dp))
                BadgeCritical(
                    icon = { Icon(painter = Icons.Airplane, contentDescription = null) }
                ) {
                }
            }
            Spacer(modifier = Modifier.size(8.dp))
            Row {
                BadgeCriticalSubtle {
                    Text(text = "label")
                }
                Spacer(modifier = Modifier.size(8.dp))
                BadgeCriticalSubtle(
                    icon = { Icon(painter = Icons.Airplane, contentDescription = null) }
                ) {
                    Text(text = "label")
                }
                Spacer(modifier = Modifier.size(8.dp))
                BadgeCriticalSubtle(
                    icon = { Icon(painter = Icons.Airplane, contentDescription = null) }
                ) {
                }
            }
        }
    }
}
