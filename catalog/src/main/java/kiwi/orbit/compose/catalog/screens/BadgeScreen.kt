package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kiwi.orbit.compose.catalog.Screen
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
import kiwi.orbit.compose.ui.controls.Icon
import kiwi.orbit.compose.ui.controls.Text

@Destination
@Composable
fun BadgeScreen(navigator: DestinationsNavigator) {
    Screen(
        title = "Badge",
        onUpClick = navigator::navigateUp,
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(it)
        ) {
            BadgeScreenInner()
        }
    }
}

@Preview
@Composable
private fun BadgeScreenInner() {
    Column(Modifier.padding(16.dp)) {
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
