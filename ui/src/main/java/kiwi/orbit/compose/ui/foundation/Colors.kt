package kiwi.orbit.compose.ui.foundation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.graphics.Color

@Stable
public class SurfaceColors(
    main: Color,
    background: Color,
    subtle: Color,
    strong: Color,
    disabled: Color,
) {
    public var main: Color by mutableStateOf(main, structuralEqualityPolicy()); internal set
    public var background: Color by mutableStateOf(background, structuralEqualityPolicy()); internal set
    public var subtle: Color by mutableStateOf(subtle, structuralEqualityPolicy()); internal set
    public var strong: Color by mutableStateOf(strong, structuralEqualityPolicy()); internal set
    public var disabled: Color by mutableStateOf(disabled, structuralEqualityPolicy()); internal set

    public fun copy(
        main: Color = this.main,
        background: Color = this.background,
        subtle: Color = this.subtle,
        strong: Color = this.strong,
        disabled: Color = this.disabled,
    ): SurfaceColors = SurfaceColors(
        main,
        background,
        subtle,
        strong,
        disabled,
    )
}

@Stable
public class ContentColors(
    normal: Color,
    minor: Color,
    subtle: Color,
    highlight: Color,
    disabled: Color,
) {
    public var normal: Color by mutableStateOf(normal, structuralEqualityPolicy()); internal set
    public var minor: Color by mutableStateOf(minor, structuralEqualityPolicy()); internal set
    public var subtle: Color by mutableStateOf(subtle, structuralEqualityPolicy()); internal set

    /**
     * Color suitable for highlighting links, product dark by default.
     */
    public var highlight: Color by mutableStateOf(highlight, structuralEqualityPolicy()); internal set
    public var disabled: Color by mutableStateOf(disabled, structuralEqualityPolicy()); internal set

    public fun copy(
        normal: Color = this.normal,
        minor: Color = this.minor,
        subtle: Color = this.subtle,
        highlight: Color = this.highlight,
        disabled: Color = this.disabled,
    ): ContentColors = ContentColors(
        normal,
        minor,
        subtle,
        highlight,
        disabled,
    )
}

@Stable
public class FeatureColors(
    main: Color,
    mainAlt: Color,
    subtle: Color,
    subtleAlt: Color,
    strong: Color,
    onMain: Color,
) {
    public var main: Color by mutableStateOf(main, structuralEqualityPolicy()); internal set
    public var mainAlt: Color by mutableStateOf(mainAlt, structuralEqualityPolicy()); internal set
    public var subtle: Color by mutableStateOf(subtle, structuralEqualityPolicy()); internal set
    public var subtleAlt: Color by mutableStateOf(subtleAlt, structuralEqualityPolicy()); internal set
    public var strong: Color by mutableStateOf(strong, structuralEqualityPolicy()); internal set
    public var onMain: Color by mutableStateOf(onMain, structuralEqualityPolicy()); internal set

    public fun copy(
        main: Color = this.main,
        mainAlt: Color = this.mainAlt,
        subtle: Color = this.subtle,
        subtleAlt: Color = this.subtleAlt,
        strong: Color = this.strong,
        onMain: Color = this.onMain,
    ): FeatureColors = FeatureColors(
        main,
        mainAlt,
        subtle,
        subtleAlt,
        strong,
        onMain,
    )
}

@Stable
public class Colors(
    public val surface: SurfaceColors,
    public val content: ContentColors,
    public val primary: FeatureColors,
    public val interactive: FeatureColors,
    public val success: FeatureColors,
    public val warning: FeatureColors,
    public val critical: FeatureColors,
    isLight: Boolean,
) {
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

    @Suppress("Dependency")
    internal fun toMaterialColors(): androidx.compose.material.Colors =
        androidx.compose.material.Colors(
            primary = primary.main,
            primaryVariant = primary.strong,
            secondary = interactive.main,
            secondaryVariant = interactive.strong,
            background = surface.background,
            surface = surface.main,
            error = critical.main,
            onPrimary = primary.onMain,
            onSecondary = interactive.onMain,
            onBackground = content.normal,
            onSurface = content.normal,
            onError = critical.onMain,
            isLight = isLight,
        )
}

@Composable
public fun ProvideColors(colors: Colors, content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalColors provides colors,
        content = content
    )
}

internal fun Colors.updateColorsFrom(other: Colors) {
    surface.updateColorsFrom(other.surface)
    content.updateColorsFrom(other.content)
    primary.updateColorsFrom(other.primary)
    interactive.updateColorsFrom(other.interactive)
    success.updateColorsFrom(other.success)
    warning.updateColorsFrom(other.warning)
    critical.updateColorsFrom(other.critical)
    isLight = other.isLight
}

internal fun SurfaceColors.updateColorsFrom(other: SurfaceColors) {
    main = other.main
    background = other.background
    subtle = other.subtle
    strong = other.strong
    disabled = other.disabled
}

internal fun ContentColors.updateColorsFrom(other: ContentColors) {
    normal = other.normal
    minor = other.minor
    subtle = other.subtle
    disabled = other.disabled
}

internal fun FeatureColors.updateColorsFrom(other: FeatureColors) {
    main = other.main
    mainAlt = other.mainAlt
    subtle = other.subtle
    subtleAlt = other.subtleAlt
    strong = other.strong
    onMain = other.onMain
}

/**
 * Local Orbit Colors.
 *
 * Access the colors through [kiwi.orbit.OrbitTheme.colors].
 */
internal val LocalColors: ProvidableCompositionLocal<Colors> = compositionLocalOf { lightColors() }
