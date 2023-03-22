package kiwi.orbit.compose.ui.controls

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.testTag
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview

/**
 * Hides long or complex information under a block that can be hidden.
 *
 * Example :
 *
 * var isExpended by remember { mutableStateOf(false) }
 *
 * Collapse(
 *    isExpended = isExpended,
 *    onExpandChange = { isExpended = it },
 *    header = {
 *      Text(text = "This the header")
 *    },
 *    body = {
 *      Text(text = "This is the collapsible body")
 *    },
 * )
 */
@Composable
public fun Collapse(
    isExpended: Boolean,
    onExpandChange: (Boolean) -> Unit,
    header: @Composable () -> Unit,
    body: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    showSeparator: Boolean = true,
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            header()
            CollapseArrow(isExpended = isExpended, onClick = { onExpandChange(!isExpended) })
        }
        AnimatedVisibility(visible = isExpended, modifier = Modifier.fillMaxWidth()) {
            body()
        }

        if (showSeparator) {
            Divider()
        }
    }
}

@Composable
private fun CollapseArrow(
    isExpended: Boolean,
    onClick: () -> Unit,
) {
    val arrowRotationDegree by animateFloatAsState(
        targetValue = if (isExpended) 180f else 0f,
    )

    IconButton(
        onClick = onClick,
        modifier = Modifier
            .rotate(arrowRotationDegree)
            .testTag("collapse_arrow"),
    ) {
        Icon(
            painter = Icons.ChevronDown,
            contentDescription = null,
        )
    }
}

@OrbitPreviews
@Composable
internal fun CollapsePreview() {
    var isExpended by remember { mutableStateOf(false) }
    Preview {
        Collapse(
            isExpended = isExpended,
            onExpandChange = { isExpended = it },
            header = {
                Text(text = "This the header")
            },
            body = {
                Text(text = "This is the collapsible body")
            },
        )
    }
}
