package kiwi.orbit.compose.ui.foundation

public fun Colors.asNeutralTheme(): Colors =
    themeWith(
        FeatureColors(
            normal = content.normal,
            normalAlt = content.normal,
            subtle = surface.background,
            subtleAlt = surface.strong,
            strong = surface.background,
            onNormal = surface.main,
            onSubtle = content.normal,
            onSubtleAlt = content.normal,
        ),
    )

public fun Colors.asNeutralSubtleStrongTheme(): Colors =
    themeWith(
        FeatureColors(
            normal = content.normal,
            normalAlt = content.normal,
            subtle = surface.main,
            subtleAlt = surface.strong,
            strong = surface.background,
            onNormal = surface.main,
            onSubtle = content.normal,
            onSubtleAlt = content.normal,
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
