package kiwi.orbit.controls

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.material.LocalContentColor
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import kiwi.orbit.OrbitTheme

@Composable
public fun CheckboxLayout(
    checked: Boolean,
    onCheckedChange: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    description: (@Composable () -> Unit)? = null,
    label: @Composable ColumnScope.() -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val selectable = if (onCheckedChange != null) {
        Modifier
            .triStateToggleable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = onCheckedChange,
                state = if (checked) ToggleableState.On else ToggleableState.Off,
            )
    } else {
        Modifier
    }
    Row(
        modifier = modifier
            .then(selectable),
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.padding(end = 10.dp),
            enabled = enabled,
            interactionSource = interactionSource,
        )
        Column(Modifier.padding(top = 2.dp)) {
            CompositionLocalProvider(
                LocalTextStyle provides OrbitTheme.typography.title4,
                LocalContentColor provides if (enabled) {
                    OrbitTheme.colors.surfaceContent
                } else {
                    /** TODO: [kiwi.orbit.foundation.ColorTokens.InkLighterHover] */
                    OrbitTheme.colors.surfaceContentTertiary
                },
            ) {
                label()
            }
            if (description != null) {
                CompositionLocalProvider(
                    LocalTextStyle provides OrbitTheme.typography.bodySmall,
                    LocalContentColor provides if (enabled) {
                        OrbitTheme.colors.surfaceContentAlt
                    } else {
                        /** TODO: [kiwi.orbit.foundation.ColorTokens.InkLighterHover] */
                        OrbitTheme.colors.surfaceContentTertiary
                    },
                ) {
                    description()
                }
            }
        }
    }
}
