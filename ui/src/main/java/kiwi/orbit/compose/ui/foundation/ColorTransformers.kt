package kiwi.orbit.compose.ui.foundation

public fun Colors.asInfoTheme(suppressed: Boolean = false): Colors =
    themeWith(info, suppressed)

public fun Colors.asSuccessTheme(suppressed: Boolean = false): Colors =
    themeWith(success, suppressed)

public fun Colors.asWarningTheme(suppressed: Boolean = false): Colors =
    themeWith(warning, suppressed)

public fun Colors.asCriticalTheme(suppressed: Boolean = false): Colors =
    themeWith(critical, suppressed)

private fun Colors.themeWith(
    featureColors: FeatureColors,
    suppressed: Boolean = false,
): Colors =
    copy(
        surface = if (!suppressed) {
            SurfaceColors(
                main = featureColors.subtleAlt,
                background = featureColors.subtle,
                subtle = featureColors.subtle,
                strong = featureColors.subtleAlt,
                disabled = surface.disabled,
            )
        } else {
            surface
        },
        content = content.copy(
            normal = if (!suppressed) featureColors.strong else content.normal,
            highlight = content.normal,
        ),
        primary = featureColors.copy(
            subtle = featureColors.subtleAlt,
        ),
    )
