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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.foundation.tokens.ColorTokens

/**
 * Surface colors suitable for various backgrounds and surfaces.
 */
@Stable
public class SurfaceColors(
    main: Color,
    background: Color,
    subtle: Color,
    strong: Color,
    disabled: Color,
) {
    /**
     * Main white background color.
     *
     * This color is used for main surface backgrounds.
     *
     * - Orbit light-theme color: [ColorTokens.White].
     */
    public var main: Color by mutableStateOf(main, structuralEqualityPolicy()); internal set

    /**
     * Secondary background color.
     *
     * To let content stand out, use this light gray shade color and put over a card or other UI
     * with the [main] background color.
     *
     * - Orbit light-theme color: [ColorTokens.CloudLight].
     */
    public var background: Color by mutableStateOf(background, structuralEqualityPolicy()); internal set

    /**
     * Subtle gray shade of [strong], stronger than [background].
     *
     * - Orbit light-theme color: [ColorTokens.CloudNormal].
     */
    public var subtle: Color by mutableStateOf(subtle, structuralEqualityPolicy()); internal set

    /**
     * Strong gray background color..
     *
     * - Orbit light-theme color: [ColorTokens.CloudDark].
     */
    public var strong: Color by mutableStateOf(strong, structuralEqualityPolicy()); internal set

    /**
     * Disabled surface color.
     *
     * Use the color for disabled element's background.
     *
     * - Orbit light-theme color: [ColorTokens.CloudDarker].
     */
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

/**
 * Content colors suitable for various text coloring.
 */
@Stable
public class ContentColors(
    normal: Color,
    minor: Color,
    subtle: Color,
    highlight: Color,
    disabled: Color,
) {
    /**
     * Normal text color.
     *
     * - Orbit light-theme color: [ColorTokens.InkNormal].
     */
    public var normal: Color by mutableStateOf(normal, structuralEqualityPolicy()); internal set

    /**
     * Less important text color.
     *
     * Represents a text that is rather a comment or an explanation.
     *
     * - Orbit light-theme color: [ColorTokens.InkLight].
     */
    public var minor: Color by mutableStateOf(minor, structuralEqualityPolicy()); internal set

    /**
     *
     * Least important text color.
     *
     * Represents a suggestion or a placeholder.
     *
     * - Orbit light-theme color: [ColorTokens.InkLighter].
     */
    public var subtle: Color by mutableStateOf(subtle, structuralEqualityPolicy()); internal set

    /**
     * Highlighting text color.
     *
     * Use it to highlight a link in a paragraph, etc.
     *
     * - Orbit light-theme color: [ColorTokens.ProductDark].
     */
    public var highlight: Color by mutableStateOf(highlight, structuralEqualityPolicy()); internal set

    /**
     * Disabled text color.
     *
     * - Orbit light-theme color: [ColorTokens.CloudDarkerHover].
     */
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

/**
 * Feature color set.
 *
 * Represents multiple similar shades for particular color.
 * The color set is exposed through [OrbitTheme.colors] semantic properties (accessors) on [Colors] class.
 */
@Stable
public class FeatureColors(
    normal: Color,
    normalAlt: Color,
    subtle: Color,
    subtleAlt: Color,
    strong: Color,
    onNormal: Color,
    onSubtle: Color,
    onSubtleAlt: Color,
) {
    /**
     * Main full color of particular feature color set.
     *
     * - Related content color: [onNormal].
     * - Orbit light-theme color example: [ColorTokens.RedNormal].
     */
    public var normal: Color by mutableStateOf(normal, structuralEqualityPolicy()); internal set

    /**
     * Alternative (darker) full color of particular feature color set.
     *
     * - Related content color: [onNormal].
     * - Orbit light-theme color example: [ColorTokens.RedNormalActive].
     */
    public var normalAlt: Color by mutableStateOf(normalAlt, structuralEqualityPolicy()); internal set

    /**
     * Light color of particular feature color set.
     *
     * - Related content color: [normal].
     * - Orbit light-theme color example: [ColorTokens.RedLight].
     */
    public var subtle: Color by mutableStateOf(subtle, structuralEqualityPolicy()); internal set

    /**
     * Alternative (darker) light color of particular feature color set.
     *
     * - Related content color: [normal].
     * - Orbit light-theme color example: [ColorTokens.RedLightActive].
     */
    public var subtleAlt: Color by mutableStateOf(subtleAlt, structuralEqualityPolicy()); internal set

    /**
     * Dark color of particular feature color set.
     *
     * - Related content color: [onNormal].
     * - Orbit light-theme color example: [ColorTokens.RedDark].
     */
    public var strong: Color by mutableStateOf(strong, structuralEqualityPolicy()); internal set

    /**
     * Color for content on [normal], [strong] shades.
     *
     * - Orbit light-theme color example: [ColorTokens.White].
     */
    public var onNormal: Color by mutableStateOf(onNormal, structuralEqualityPolicy()); internal set

    /**
     * Color for content on [subtle] shades.
     *
     * - Orbit light-theme color example: [strong].
     */
    public var onSubtle: Color by mutableStateOf(onSubtle, structuralEqualityPolicy()); internal set

    /**
     * Color for content on [subtleAlt] shades.
     *
     * - Orbit light-theme color example: [normalAlt].
     */
    public var onSubtleAlt: Color by mutableStateOf(onSubtleAlt, structuralEqualityPolicy()); internal set

    public fun copy(
        normal: Color = this.normal,
        normalAlt: Color = this.normalAlt,
        subtle: Color = this.subtle,
        subtleAlt: Color = this.subtleAlt,
        strong: Color = this.strong,
        onNormal: Color = this.onNormal,
        onSubtle: Color = this.onSubtle,
        onSubtleAlt: Color = this.onSubtleAlt,
    ): FeatureColors = FeatureColors(
        normal = normal,
        normalAlt = normalAlt,
        subtle = subtle,
        subtleAlt = subtleAlt,
        strong = strong,
        onNormal = onNormal,
        onSubtle = onSubtle,
        onSubtleAlt = onSubtleAlt,
    )
}

/**
 * Product color set for various product levels.
 */
@Stable
public class BundleColors(
    basic: Color,
    basicGradient: Brush,
    medium: Color,
    mediumGradient: Brush,
    top: Color,
    topGradient: Brush,
    onBasic: Color,
    onMedium: Color,
    onTop: Color,
) {
    public var basic: Color by mutableStateOf(basic, structuralEqualityPolicy()); internal set
    public var basicGradient: Brush by mutableStateOf(basicGradient, structuralEqualityPolicy()); internal set
    public var medium: Color by mutableStateOf(medium, structuralEqualityPolicy()); internal set
    public var mediumGradient: Brush
        by mutableStateOf(mediumGradient, structuralEqualityPolicy())
        internal set
    public var top: Color by mutableStateOf(top, structuralEqualityPolicy()); internal set
    public var topGradient: Brush by mutableStateOf(topGradient, structuralEqualityPolicy()); internal set
    public var onBasic: Color by mutableStateOf(onBasic, structuralEqualityPolicy()); internal set
    public var onMedium: Color by mutableStateOf(onMedium, structuralEqualityPolicy()); internal set
    public var onTop: Color by mutableStateOf(onTop, structuralEqualityPolicy()); internal set

    public fun copy(
        basic: Color = this.basic,
        basicGradient: Brush = this.basicGradient,
        medium: Color = this.medium,
        mediumGradient: Brush = this.mediumGradient,
        top: Color = this.top,
        topGradient: Brush = this.topGradient,
        onBasic: Color = this.onBasic,
        onMedium: Color = this.onMedium,
        onTop: Color = this.onTop,
    ): BundleColors = BundleColors(
        basic,
        basicGradient,
        medium,
        mediumGradient,
        top,
        topGradient,
        onBasic,
        onMedium,
        onTop,
    )
}

/**
 * Orbit Color Theme.
 *
 * Colors are exposed through semantic color sets:
 * - [surface] light colors representing background shades;
 * - [content] dark colors representing text color shades;
 * - [primary] product colors representing main product colors and primary actions;
 * - [info] blue colors representing info state and interactive components;
 * - [success] green colors representing success states;
 * - [warning] orange colors representing warning states;
 * - [critical] red colors representing dangerous and critical states;
 * - [bundle] bundle colors representing particular product variants;
 */
@Stable
public class Colors(
    public val surface: SurfaceColors,
    public val content: ContentColors,
    public val primary: FeatureColors,
    public val info: FeatureColors,
    public val success: FeatureColors,
    public val warning: FeatureColors,
    public val critical: FeatureColors,
    public val bundle: BundleColors,
    isLight: Boolean,
) {
    public var isLight: Boolean by mutableStateOf(isLight, structuralEqualityPolicy()); internal set

    public fun copy(
        surface: SurfaceColors = this.surface,
        content: ContentColors = this.content,
        primary: FeatureColors = this.primary,
        info: FeatureColors = this.info,
        success: FeatureColors = this.success,
        warning: FeatureColors = this.warning,
        critical: FeatureColors = this.critical,
        bundle: BundleColors = this.bundle,
        isLight: Boolean = this.isLight,
    ): Colors = Colors(
        surface = surface,
        content = content,
        primary = primary,
        info = info,
        success = success,
        warning = warning,
        critical = critical,
        bundle = bundle,
        isLight = isLight,
    )

    @Suppress("Dependency")
    internal fun toMaterialColors(): androidx.compose.material.Colors =
        androidx.compose.material.Colors(
            primary = primary.normal,
            primaryVariant = primary.strong,
            secondary = info.normal,
            secondaryVariant = info.strong,
            background = surface.background,
            surface = surface.main,
            error = critical.normal,
            onPrimary = primary.onNormal,
            onSecondary = info.onNormal,
            onBackground = content.normal,
            onSurface = content.normal,
            onError = critical.onNormal,
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
    info.updateColorsFrom(other.info)
    success.updateColorsFrom(other.success)
    warning.updateColorsFrom(other.warning)
    critical.updateColorsFrom(other.critical)
    bundle.updateColorsFrom(other.bundle)
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
    normal = other.normal
    normalAlt = other.normalAlt
    subtle = other.subtle
    subtleAlt = other.subtleAlt
    strong = other.strong
    onNormal = other.onNormal
    onSubtle = other.onSubtle
    onSubtleAlt = other.onSubtleAlt
}

internal fun BundleColors.updateColorsFrom(other: BundleColors) {
    basic = other.basic
    basicGradient = other.basicGradient
    medium = other.medium
    mediumGradient = other.mediumGradient
    top = other.top
    topGradient = other.topGradient
    onBasic = other.onBasic
    onMedium = other.onMedium
    onTop = other.onTop
}

/**
 * Local Orbit Colors.
 *
 * Access the colors through [kiwi.orbit.compose.ui.OrbitTheme.colors].
 */
internal val LocalColors: ProvidableCompositionLocal<Colors> = compositionLocalOf { lightColors() }
