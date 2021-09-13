package kiwi.orbit.compose.ui.foundation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import kiwi.orbit.compose.ui.OrbitTheme

public enum class ContentEmphasis {
    Normal,
    Minor,
    Subtle,
    Disabled,
}

public val LocalContentEmphasis: ProvidableCompositionLocal<ContentEmphasis> =
    compositionLocalOf { ContentEmphasis.Normal }

@Composable
public fun ProvideContentEmphasis(emphasis: ContentEmphasis, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalContentEmphasis provides emphasis, content = content)
}

@Composable
public fun Color.applyEmphasis(emphasis: ContentEmphasis): Color =
    when (emphasis) {
        ContentEmphasis.Normal -> {
            this
        }
        ContentEmphasis.Minor -> {
            val colors = OrbitTheme.colors
            if (colors.content.normal == this) {
                colors.content.minor
            } else {
                copy(alpha = 0.80f)
            }
        }
        ContentEmphasis.Subtle -> {
            val colors = OrbitTheme.colors
            if (colors.content.normal == this) {
                colors.content.subtle
            } else {
                copy(alpha = 0.66f)
            }
        }
        ContentEmphasis.Disabled -> {
            val colors = OrbitTheme.colors
            if (colors.content.normal == this) {
                colors.content.disabled
            } else {
                copy(alpha = 0.48f)
            }
        }
    }
