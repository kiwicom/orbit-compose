package kiwi.orbit.compose.ui.foundation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.foundation.tokens.ColorTokens

/**
 * Surface colors suitable for various backgrounds and surfaces.
 */
@Immutable
public class SurfaceColors(
    /**
     * Primary main white color for backgrounds.
     *
     * - Orbit light-theme color: [ColorTokens.White].
     */
    public val main: Color,
    /**
     * Subtle gray color, secondary color for backgrounds.
     *
     * To let surfaces stand out, use this light color and put over a card or other UI with
     * the [main] background color.
     *
     * - Orbit light-theme color: [ColorTokens.CloudLight].
     */
    public val subtle: Color,
    /**
     * Alt darker subtle gray color.
     *
     * - Orbit light-theme color: [ColorTokens.CloudLightHover].
     */
    public val subtleAlt: Color,
    /**
     * Normal gray color for UI control backgrounds.
     *
     * - Orbit light-theme color: [ColorTokens.CloudNormal].
     */
    public val normal: Color,
    /**
     * Alt darker normal gray color.
     *
     * - Orbit light-theme color: [ColorTokens.CloudNormalHover].
     */
    public val normalAlt: Color,
    /**
     * Strong gray color.
     *
     * - Orbit light-theme color: [ColorTokens.CloudDark].
     */
    public val strong: Color,
    /**
     * Alt strong gray color.
     *
     * - Orbit light-theme color: [ColorTokens.CloudDarkHover].
     */
    public val strongAlt: Color,
    /**
     * Disabled surface color.
     *
     * Use the color for disabled element's background.
     *
     * - Orbit light-theme color: [ColorTokens.CloudNormal].
     */
    public val disabled: Color,
) {
    public fun copy(
        main: Color = this.main,
        subtle: Color = this.subtle,
        subtleAlt: Color = this.subtleAlt,
        normal: Color = this.normal,
        normalAlt: Color = this.normalAlt,
        strong: Color = this.strong,
        strongAlt: Color = this.strongAlt,
        disabled: Color = this.disabled,
    ): SurfaceColors = SurfaceColors(
        main = main,
        subtle = subtle,
        subtleAlt = subtleAlt,
        normal = normal,
        normalAlt = normalAlt,
        strong = strong,
        strongAlt = strongAlt,
        disabled = disabled,
    )
}

/**
 * Content colors suitable for various text coloring.
 */
@Immutable
public class ContentColors(
    /**
     * Normal text color.
     *
     * - Orbit light-theme color: [ColorTokens.InkDark].
     */
    public val normal: Color,
    /**
     * Less important text color.
     *
     * Represents a text that is rather a comment or an explanation.
     *
     * - Orbit light-theme color: [ColorTokens.InkNormal].
     */
    public val minor: Color,
    /**
     * Least important text color.
     *
     * Represents a suggestion or a placeholder.
     *
     * - Orbit light-theme color: [ColorTokens.InkLight].
     */
    public val subtle: Color,
    /**
     * Highlighting text color.
     *
     * Use it to highlight a link in a paragraph, etc.
     *
     * - Orbit light-theme color: [ColorTokens.ProductDark].
     */
    public val highlight: Color,
    /**
     * Disabled text color.
     *
     * - Orbit light-theme color: [ColorTokens.CloudDarkHover].
     */
    public val disabled: Color,
) {
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
@Immutable
public class FeatureColors(
    /**
     * Main full color of particular feature color set.
     *
     * - Related content color: [onNormal].
     * - Orbit light-theme color example: [ColorTokens.RedNormal].
     */
    public val normal: Color,
    /**
     * Alternative (darker) full color of particular feature color set.
     *
     * - Related content color: [onNormal].
     * - Orbit light-theme color example: [ColorTokens.RedNormalActive].
     */
    public val normalAlt: Color,
    /**
     * Light color of particular feature color set.
     *
     * - Related content color: [strong].
     * - Orbit light-theme color example: [ColorTokens.RedLight].
     */
    public val subtle: Color,
    /**
     * Alternative (darker) light color of particular feature color set.
     *
     * - Related content color: [strongAlt].
     * - Orbit light-theme color example: [ColorTokens.RedLightHover].
     */
    public val subtleAlt: Color,
    /**
     * Dark color of particular feature color set.
     *
     * - Related content color: [onNormal].
     * - Orbit light-theme color example: [ColorTokens.RedDark].
     */
    public val strong: Color,
    /**
     * Darker color of particular feature color set.
     *
     * - Related content color: [onNormal].
     * - Orbit light-theme color example: [ColorTokens.RedDarkHover].
     */
    public val strongAlt: Color,
    /**
     * Color for content on [normal], [strong] shades.
     *
     * - Orbit light-theme color example: [ColorTokens.White].
     */
    public val onNormal: Color,
) {
    public fun copy(
        normal: Color = this.normal,
        normalAlt: Color = this.normalAlt,
        subtle: Color = this.subtle,
        subtleAlt: Color = this.subtleAlt,
        strong: Color = this.strong,
        strongAlt: Color = this.strongAlt,
        onNormal: Color = this.onNormal,
    ): FeatureColors = FeatureColors(
        normal = normal,
        normalAlt = normalAlt,
        subtle = subtle,
        subtleAlt = subtleAlt,
        strong = strong,
        strongAlt = strongAlt,
        onNormal = onNormal,
    )
}

/**
 * Product color set for various product levels.
 */
@Immutable
public class BundleColors(
    public val basic: Color,
    public val basicGradient: Brush,
    public val medium: Color,
    public val mediumGradient: Brush,
    public val top: Color,
    public val topGradient: Brush,
    public val onBasic: Color,
    public val onMedium: Color,
    public val onTop: Color,
) {
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
@Immutable
public class Colors(
    public val surface: SurfaceColors,
    public val content: ContentColors,
    public val primary: FeatureColors,
    public val info: FeatureColors,
    public val success: FeatureColors,
    public val warning: FeatureColors,
    public val critical: FeatureColors,
    public val bundle: BundleColors,
    public val isLight: Boolean,
) {
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

    internal fun toMaterial3Colors(): androidx.compose.material3.ColorScheme =
        androidx.compose.material3.ColorScheme(
            primary = primary.normal,
            onPrimary = contentColorFor(primary.normal),
            primaryContainer = primary.subtle,
            onPrimaryContainer = contentColorFor(primary.subtle),
            inversePrimary = primary.strong,
            secondary = info.normal,
            onSecondary = contentColorFor(info.normal),
            secondaryContainer = info.subtle,
            onSecondaryContainer = contentColorFor(info.subtle),
            tertiary = info.normal,
            onTertiary = contentColorFor(info.normal),
            tertiaryContainer = info.subtle,
            onTertiaryContainer = contentColorFor(info.subtle),
            background = surface.main,
            onBackground = contentColorFor(surface.main),
            surface = surface.main,
            onSurface = contentColorFor(surface.main),
            surfaceVariant = surface.subtle,
            onSurfaceVariant = contentColorFor(surface.subtle),
            surfaceTint = contentColorFor(surface.main),
            inverseSurface = content.normal,
            inverseOnSurface = contentColorFor(content.normal),
            error = critical.normal,
            onError = contentColorFor(critical.normal),
            errorContainer = critical.subtle,
            onErrorContainer = contentColorFor(critical.subtle),
            outline = surface.strong,
            outlineVariant = surface.normal,
            scrim = surface.strong,
            surfaceBright = Color.Unspecified,
            surfaceDim = Color.Unspecified,
            surfaceContainerHighest = surface.main,
            surfaceContainerHigh = surface.main,
            surfaceContainer = surface.main,
            surfaceContainerLow = surface.main,
            surfaceContainerLowest = surface.main,
        )
}

@Composable
public fun ProvideColors(colors: Colors, content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalColors provides colors,
        content = content,
    )
}

/**
 * Local Orbit Colors.
 *
 * Access the colors through [kiwi.orbit.compose.ui.OrbitTheme.colors].
 */
internal val LocalColors: ProvidableCompositionLocal<Colors> = compositionLocalOf { lightColors() }
