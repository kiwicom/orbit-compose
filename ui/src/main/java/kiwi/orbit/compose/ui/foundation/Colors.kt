package kiwi.orbit.compose.ui.foundation

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.graphics.Color

@Immutable
public data class SurfaceColors(
    public val background: Color,
    public val main: Color,
    public val strong: Color,
    public val disabled: Color,
)

@Immutable
public data class ContentColors(
    public val normal: Color,
    public val minor: Color,
    public val subtle: Color,
    public val disabled: Color,
)

@Immutable
public data class FeatureColors(
    public val main: Color,
    public val mainAlt: Color,
    public val subtle: Color,
    public val subtleAlt: Color,
    public val strong: Color,
    public val onMain: Color,
)

@Stable
public class Colors(
    surface: SurfaceColors,
    content: ContentColors,
    primary: FeatureColors,
    interactive: FeatureColors,
    success: FeatureColors,
    warning: FeatureColors,
    critical: FeatureColors,
    isLight: Boolean,
) {
    public var surface: SurfaceColors by mutableStateOf(surface, structuralEqualityPolicy()); internal set
    public var content: ContentColors by mutableStateOf(content, structuralEqualityPolicy()); internal set
    public var primary: FeatureColors by mutableStateOf(primary, structuralEqualityPolicy()); internal set
    public var interactive: FeatureColors by mutableStateOf(interactive, structuralEqualityPolicy()); internal set
    public var success: FeatureColors by mutableStateOf(success, structuralEqualityPolicy()); internal set
    public var warning: FeatureColors by mutableStateOf(warning, structuralEqualityPolicy()); internal set
    public var critical: FeatureColors by mutableStateOf(critical, structuralEqualityPolicy()); internal set
    public var isLight: Boolean by mutableStateOf(isLight, structuralEqualityPolicy()); internal set

    public fun copy(
        surface: SurfaceColors = this.surface,
        content: ContentColors = this.content,
        primary: FeatureColors = this.primary,
        interactive: FeatureColors = this.interactive,
        success: FeatureColors = this.success,
        warning: FeatureColors = this.warning,
        critical: FeatureColors = this.critical,
        isLight: Boolean = this.isLight,
    ): Colors = Colors(
        surface = surface,
        content = content,
        primary = primary,
        interactive = interactive,
        success = success,
        warning = warning,
        critical = critical,
        isLight = isLight,
    )
}

/**
 * Local Orbit Colors.
 *
 * Access the colors through [kiwi.orbit.OrbitTheme.colors].
 */
internal val LocalColors: ProvidableCompositionLocal<Colors> = compositionLocalOf { lightColors() }
