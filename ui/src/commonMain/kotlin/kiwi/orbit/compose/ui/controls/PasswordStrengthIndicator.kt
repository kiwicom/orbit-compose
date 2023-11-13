package kiwi.orbit.compose.ui.controls

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.foundation.LocalContentColor
import kiwi.orbit.compose.ui.foundation.LocalTextStyle

@Composable
public fun PasswordStrengthIndicator(
    value: Float,
    color: Color,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    val progressAnimation by animateFloatAsState(value)

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        LinearProgressIndicator(
            modifier = Modifier.weight(1f),
            progress = progressAnimation,
            color = color,
        )

        CompositionLocalProvider(
            LocalContentColor provides color,
            LocalTextStyle provides OrbitTheme.typography.bodySmallMedium,
        ) {
            BoxWithConstraints(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterEnd,
            ) {
                label()
            }
        }
    }
}

public object PasswordStrengthColor {
    public val Weak: Color
        @Composable
        get() = OrbitTheme.colors.critical.normal

    public val Medium: Color
        @Composable
        get() = OrbitTheme.colors.warning.normal

    public val Strong: Color
        @Composable
        get() = OrbitTheme.colors.success.normal
}

@OrbitPreviews
@Composable
private fun PasswordStrengthIndicatorPreview() {
    Surface {
        Column {
            PasswordStrengthIndicator(
                value = 0.2f,
                color = PasswordStrengthColor.Weak,
                label = { Text("Weak") },
            )
            PasswordStrengthIndicator(
                value = 0.4f,
                color = PasswordStrengthColor.Weak,
                label = { Text("Weak") },
            )
            PasswordStrengthIndicator(
                value = 0.6f,
                color = PasswordStrengthColor.Medium,
                label = { Text("Medium") },
            )
            PasswordStrengthIndicator(
                value = 0.8f,
                color = PasswordStrengthColor.Medium,
                label = { Text("Medium") },
            )
            PasswordStrengthIndicator(
                value = 1f,
                color = PasswordStrengthColor.Strong,
                label = { Text("Strong") },
            )
        }
    }
}
