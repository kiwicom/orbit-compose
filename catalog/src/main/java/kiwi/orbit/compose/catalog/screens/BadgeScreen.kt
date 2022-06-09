package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
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
import kiwi.orbit.compose.catalog.Screen
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.BadgeCircleCritical
import kiwi.orbit.compose.ui.controls.BadgeCircleCriticalSubtle
import kiwi.orbit.compose.ui.controls.BadgeCircleInfo
import kiwi.orbit.compose.ui.controls.BadgeCircleInfoSubtle
import kiwi.orbit.compose.ui.controls.BadgeCircleNeutral
import kiwi.orbit.compose.ui.controls.BadgeCircleNeutralStrong
import kiwi.orbit.compose.ui.controls.BadgeCircleNeutralSubtle
import kiwi.orbit.compose.ui.controls.BadgeCircleSuccess
import kiwi.orbit.compose.ui.controls.BadgeCircleSuccessSubtle
import kiwi.orbit.compose.ui.controls.BadgeCircleWarning
import kiwi.orbit.compose.ui.controls.BadgeCircleWarningSubtle
import kiwi.orbit.compose.ui.controls.BadgeCritical
import kiwi.orbit.compose.ui.controls.BadgeCriticalSubtle
import kiwi.orbit.compose.ui.controls.BadgeInfo
import kiwi.orbit.compose.ui.controls.BadgeInfoSubtle
import kiwi.orbit.compose.ui.controls.BadgeNeutral
import kiwi.orbit.compose.ui.controls.BadgeNeutralStrong
import kiwi.orbit.compose.ui.controls.BadgeNeutralSubtle
import kiwi.orbit.compose.ui.controls.BadgeSuccess
import kiwi.orbit.compose.ui.controls.BadgeSuccessSubtle
import kiwi.orbit.compose.ui.controls.BadgeWarning
import kiwi.orbit.compose.ui.controls.BadgeWarningSubtle
import kiwi.orbit.compose.ui.controls.Icon
import kiwi.orbit.compose.ui.controls.Text

@Composable
fun BadgeScreen(onNavigateUp: () -> Unit) {
    Screen(
        title = "Badge",
        onNavigateUp = onNavigateUp,
    ) { contentPadding ->
        Box(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding)
        ) {
            BadgeScreenInner()
        }
    }
}

@Preview
@Composable
private fun BadgeScreenInner() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "Badge",
            style = OrbitTheme.typography.title3,
        )

        Spacer(modifier = Modifier.size(16.dp))

        BadgeRow("BadgeNeutral") {
            BadgeNeutral {
                Text(text = "label")
            }
            BadgeNeutral(
                icon = { Icon(painter = Icons.Airplane, contentDescription = null) }
            ) {
                Text(text = "label")
            }
            BadgeNeutral(
                icon = { Icon(painter = Icons.Airplane, contentDescription = null) }
            ) {
            }
        }
        BadgeRow("BadgeNeutralSubtle") {
            BadgeNeutralSubtle {
                Text(text = "label")
            }
            BadgeNeutralSubtle(
                icon = { Icon(painter = Icons.Airplane, contentDescription = null) }
            ) {
                Text(text = "label")
            }
            BadgeNeutralSubtle(
                icon = { Icon(painter = Icons.Airplane, contentDescription = null) }
            ) {
            }
        }
        BadgeRow("BadgeNeutralStrong") {
            BadgeNeutralStrong {
                Text(text = "label")
            }
            BadgeNeutralStrong(
                icon = { Icon(painter = Icons.Airplane, contentDescription = null) }
            ) {
                Text(text = "label")
            }
            BadgeNeutralStrong(
                icon = { Icon(painter = Icons.Airplane, contentDescription = null) }
            ) {
            }
        }

        Spacer(modifier = Modifier.size(16.dp))

        BadgeRow("BadgeInfoSubtle") {
            BadgeInfoSubtle {
                Text(text = "label")
            }
            BadgeInfoSubtle(
                icon = { Icon(painter = Icons.InformationCircle, contentDescription = null) }
            ) {
                Text(text = "label")
            }
            BadgeInfoSubtle(
                icon = { Icon(painter = Icons.InformationCircle, contentDescription = null) }
            ) {
            }
        }
        BadgeRow("BadgeInfo") {
            BadgeInfo {
                Text(text = "label")
            }
            BadgeInfo(
                icon = { Icon(painter = Icons.InformationCircle, contentDescription = null) }
            ) {
                Text(text = "label")
            }
            BadgeInfo(
                icon = { Icon(painter = Icons.InformationCircle, contentDescription = null) }
            ) {
            }
        }

        Spacer(modifier = Modifier.size(16.dp))

        BadgeRow("BadgeSuccessSubtle") {
            BadgeSuccessSubtle {
                Text(text = "label")
            }
            BadgeSuccessSubtle(
                icon = { Icon(painter = Icons.Check, contentDescription = null) }
            ) {
                Text(text = "label")
            }
            BadgeSuccessSubtle(
                icon = { Icon(painter = Icons.Check, contentDescription = null) }
            ) {
            }
        }
        BadgeRow("BadgeSuccess") {
            BadgeSuccess {
                Text(text = "label")
            }
            BadgeSuccess(
                icon = { Icon(painter = Icons.Check, contentDescription = null) }
            ) {
                Text(text = "label")
            }
            BadgeSuccess(
                icon = { Icon(painter = Icons.Check, contentDescription = null) }
            ) {
            }
        }

        Spacer(modifier = Modifier.size(16.dp))

        BadgeRow("BadgeWarningSubtle") {
            BadgeWarningSubtle {
                Text(text = "label")
            }
            BadgeWarningSubtle(
                icon = { Icon(painter = Icons.Alert, contentDescription = null) }
            ) {
                Text(text = "label")
            }
            BadgeWarningSubtle(
                icon = { Icon(painter = Icons.Alert, contentDescription = null) }
            ) {
            }
        }
        BadgeRow("BadgeWarning") {
            BadgeWarning {
                Text(text = "label")
            }
            BadgeWarning(
                icon = { Icon(painter = Icons.Alert, contentDescription = null) }
            ) {
                Text(text = "label")
            }
            BadgeWarning(
                icon = { Icon(painter = Icons.Alert, contentDescription = null) }
            ) {
            }
        }

        Spacer(modifier = Modifier.size(16.dp))

        BadgeRow("BadgeCriticalSubtle") {
            BadgeCriticalSubtle {
                Text(text = "label")
            }
            BadgeCriticalSubtle(
                icon = { Icon(painter = Icons.Alert, contentDescription = null) }
            ) {
                Text(text = "label")
            }
            BadgeCriticalSubtle(
                icon = { Icon(painter = Icons.Alert, contentDescription = null) }
            ) {
            }
        }
        BadgeRow("BadgeCritical") {
            BadgeCritical {
                Text(text = "label")
            }
            BadgeCritical(
                icon = { Icon(painter = Icons.Alert, contentDescription = null) }
            ) {
                Text(text = "label")
            }
            BadgeCritical(
                icon = { Icon(painter = Icons.Alert, contentDescription = null) }
            ) {
            }
        }

        Spacer(modifier = Modifier.size(16.dp))

        Text(
            text = "BadgeCircle",
            style = OrbitTheme.typography.title3,
        )

        Spacer(modifier = Modifier.size(16.dp))

        BadgeRow(name = "BadgeCircleNeutral") {
            BadgeCircleNeutral(1)
            BadgeCircleNeutral(2)
            BadgeCircleNeutral(3)
        }

        BadgeRow(name = "BadgeCircleNeutralSubtle") {
            BadgeCircleNeutralSubtle(1)
            BadgeCircleNeutralSubtle(2)
            BadgeCircleNeutralSubtle(3)
        }

        BadgeRow(name = "BadgeCircleNeutralStrong") {
            BadgeCircleNeutralStrong(1)
            BadgeCircleNeutralStrong(2)
            BadgeCircleNeutralStrong(3)
        }

        Spacer(modifier = Modifier.size(16.dp))

        BadgeRow(name = "BadgeCircleInfo") {
            BadgeCircleInfo(1)
            BadgeCircleInfo(2)
            BadgeCircleInfo(3)
        }

        BadgeRow(name = "BadgeCircleInfoSubtle") {
            BadgeCircleInfoSubtle(1)
            BadgeCircleInfoSubtle(2)
            BadgeCircleInfoSubtle(3)
        }

        Spacer(modifier = Modifier.size(16.dp))

        BadgeRow(name = "BadgeCircleSuccess") {
            BadgeCircleSuccess(1)
            BadgeCircleSuccess(2)
            BadgeCircleSuccess(3)
        }

        BadgeRow(name = "BadgeCircleSuccessSubtle") {
            BadgeCircleSuccessSubtle(1)
            BadgeCircleSuccessSubtle(2)
            BadgeCircleSuccessSubtle(3)
        }

        Spacer(modifier = Modifier.size(16.dp))

        BadgeRow(name = "BadgeCircleWarning") {
            BadgeCircleWarning(1)
            BadgeCircleWarning(2)
            BadgeCircleWarning(3)
        }

        BadgeRow(name = "BadgeCircleWarningSubtle") {
            BadgeCircleWarningSubtle(1)
            BadgeCircleWarningSubtle(2)
            BadgeCircleWarningSubtle(3)
        }

        Spacer(modifier = Modifier.size(16.dp))

        BadgeRow(name = "BadgeCircleCritical") {
            BadgeCircleCritical(1)
            BadgeCircleCritical(2)
            BadgeCircleCritical(3)
        }

        BadgeRow(name = "BadgeCircleCriticalSubtle") {
            BadgeCircleCriticalSubtle(1)
            BadgeCircleCriticalSubtle(2)
            BadgeCircleCriticalSubtle(3)
        }
    }
}

@Composable
private fun BadgeRow(
    name: String,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        content()
        Text(
            text = name,
            modifier = Modifier
                .padding(start = 8.dp)
                .alignByBaseline(),
            style = OrbitTheme.typography.bodyNormalMedium,
        )
    }
}
