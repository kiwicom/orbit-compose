package kiwi.orbit.compose.catalog.screens

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
import kiwi.orbit.compose.catalog.SubScreen
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.controls.BadgeCritical
import kiwi.orbit.compose.ui.controls.BadgeCriticalSubtle
import kiwi.orbit.compose.ui.controls.BadgeInfo
import kiwi.orbit.compose.ui.controls.BadgeInfoSubtle
import kiwi.orbit.compose.ui.controls.BadgeNeutral
import kiwi.orbit.compose.ui.controls.BadgeNeutralSubtle
import kiwi.orbit.compose.ui.controls.BadgeSecondary
import kiwi.orbit.compose.ui.controls.BadgeSuccess
import kiwi.orbit.compose.ui.controls.BadgeSuccessSubtle
import kiwi.orbit.compose.ui.controls.BadgeWarning
import kiwi.orbit.compose.ui.controls.BadgeWarningSubtle

@Composable
fun BadgeScreen(onUpClick: () -> Unit) {
    SubScreen(
        title = "Badge",
        onUpClick = onUpClick,
    ) {
        BadgeScreenInner()
    }
}

@Preview
@Composable
fun BadgeScreenInner() {
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
