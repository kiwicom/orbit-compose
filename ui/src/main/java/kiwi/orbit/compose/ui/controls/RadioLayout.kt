package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalContentColor
import kiwi.orbit.compose.ui.foundation.LocalContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalTextStyle

@Composable
public fun RadioLayout(
    selected: Boolean,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    error: Boolean = false,
    description: (@Composable () -> Unit)? = null,
    label: @Composable ColumnScope.() -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val selectable = if (onClick != null) {
        Modifier
            .selectable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = onClick,
                selected = selected,
            )
    } else {
        Modifier
    }
    Row(
        modifier = modifier
            .then(selectable),
    ) {
        Radio(
            selected = selected,
            onClick = onClick,
            modifier = Modifier.padding(end = 10.dp),
            enabled = enabled,
            error = error,
            interactionSource = interactionSource,
        )
        Column(Modifier.padding(top = 2.dp)) {
            CompositionLocalProvider(
                LocalTextStyle provides OrbitTheme.typography.title4,
                LocalContentColor provides OrbitTheme.colors.content.normal,
                LocalContentEmphasis provides if (enabled) ContentEmphasis.Normal else ContentEmphasis.Disabled,
            ) {
                label()
            }
            if (description != null) {
                CompositionLocalProvider(
                    LocalTextStyle provides OrbitTheme.typography.bodySmall,
                    LocalContentColor provides OrbitTheme.colors.content.normal,
                    LocalContentEmphasis provides if (enabled) ContentEmphasis.Minor else ContentEmphasis.Disabled,
                ) {
                    description()
                }
            }
        }
    }
}
