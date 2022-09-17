package kiwi.orbit.compose.ui.foundation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.foundation.tokens.ColorTokens

/**
 * Content emphasis changes content color in a semantic way.
 *
 * The main use case is to change [ContentColors.normal] (`InkNormal`) color
 * other content/ink shades, though the logic is able to handle also other
 * colors - in such case the color resolution applies opacity.
 */
public enum class ContentEmphasis {
    /**
     * Normal text color (no color change).
     */
    Normal,

    /**
     * Less important text color.
     *
     * Represents a text that is rather a comment or an explanation.
     *
     * If applied on:
     * - `content.normal` -> [ContentColors.minor] ([ColorTokens.InkNormal])
     * - else -> opacity 20%
     */
    Minor,

    /**
     * Least important text color.
     *
     * Represents a suggestion or a placeholder.
     *
     * If applied on:
     * - `content.normal` -> [ContentColors.subtle] ([ColorTokens.InkLight])
     * - else -> opacity 34%
     */
    Subtle,

    /**
     * Disabled text color.
     *
     * If applied on:
     * - `content.normal` -> [ContentColors.disabled] ([ColorTokens.CloudDarkHover])
     * - else -> opacity 52%
     */
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
