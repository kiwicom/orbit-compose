package kiwi.orbit.compose.ui.controls

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.semantics.collapse
import androidx.compose.ui.semantics.expand
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle

/**
 * Hides long or complex information under a block that can be hidden.
 *
 * Example :
 * ```
 * var expanded by rememberSaveable { mutableStateOf(false) }
 * Collapse(
 *    expanded = expanded,
 *    onExpandClick = { expanded = it },
 *    title = { Text("Title") },
 *    content = { Text(text = "This is the collapsible content") },
 * )
 * ```
 */
@Composable
public fun Collapse(
    expanded: Boolean,
    onExpandChange: (Boolean) -> Unit,
    title: @Composable () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    withSeparator: Boolean = true,
) {
    Column(
        modifier = modifier.semantics {
            expand {
                if (!expanded) {
                    onExpandChange(true)
                    true
                } else {
                    false
                }
            }
            collapse {
                if (expanded) {
                    onExpandChange(false)
                    true
                } else {
                    false
                }
            }
        },
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier
                .clickable(
                    enabled = true,
                    onClick = { onExpandChange(!expanded) },
                    indication = null,
                    interactionSource = interactionSource,
                )
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            ProvideMergedTextStyle(OrbitTheme.typography.bodyNormalMedium) {
                Box(Modifier.weight(1f)) {
                    title()
                }
            }
            CollapseArrow(
                expanded = expanded,
                interactionSource = interactionSource,
                onClick = { onExpandChange(!expanded) },
            )
        }
        AnimatedVisibility(
            visible = expanded,
            label = "CollapseContent",
        ) {
            ProvideMergedTextStyle(OrbitTheme.typography.bodyNormal) {
                content()
            }
        }
        if (withSeparator) {
            Divider()
        }
    }
}

@Composable
private fun CollapseArrow(
    expanded: Boolean,
    interactionSource: MutableInteractionSource,
    onClick: () -> Unit,
) {
    val arrowRotationDegree by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "CollapseExpandChevronRotation",
    )
    IconButton(
        onClick = onClick,
        modifier = Modifier.rotate(arrowRotationDegree),
        interactionSource = interactionSource,
        minimumInteractiveComponentEnforcement = false,
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
    var expanded by rememberSaveable { mutableStateOf(true) }
    Preview {
        Collapse(
            expanded = expanded,
            onExpandChange = { expanded = it },
            title = {
                Text(text = "Collapse title")
            },
            content = {
                Text(text = "This is the collapsible content")
            },
        )
    }
}
