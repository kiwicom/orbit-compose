package kiwi.orbit.compose.ui.foundation

public fun Colors.asNeutralTheme(): Colors =
    themeWith(
        FeatureColors(
            normal = content.normal,
            normalAlt = content.normal,
            subtle = surface.subtle,
            subtleAlt = surface.subtleAlt,
            strong = surface.strong,
            strongAlt = surface.strongAlt,
            onNormal = surface.main,
        ),
    )

public fun Colors.asNeutralSubtleStrongTheme(): Colors =
    themeWith(
        FeatureColors(
            normal = content.normal,
            normalAlt = content.normal,
            subtle = surface.main,
            subtleAlt = surface.subtleAlt, // WhiteAlt is not in theme, using CloudLightAlt
            strong = surface.subtle,
            strongAlt = surface.strongAlt,
            onNormal = surface.main,
        ),
    )

public fun Colors.asInfoTheme(): Colors =
    themeWith(info)

public fun Colors.asSuccessTheme(): Colors =
    themeWith(success)

public fun Colors.asWarningTheme(): Colors =
    themeWith(warning)

public fun Colors.asCriticalTheme(): Colors =
    themeWith(critical)

private fun Colors.themeWith(colors: FeatureColors): Colors =
    copy(
        primary = colors,
        content = content.copy(
            highlight = content.normal, // neutralize as the background is already colored
        ),
    )
