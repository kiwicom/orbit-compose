package kiwi.orbit.compose.ui.foundation

public fun Colors.asInteractiveTheme(): Colors = themeWith(interactive)

public fun Colors.asSuccessTheme(): Colors = themeWith(success)

public fun Colors.asWarningTheme(): Colors = themeWith(warning)

public fun Colors.asCriticalTheme(): Colors = themeWith(critical)

private fun Colors.themeWith(featureColors: FeatureColors): Colors =
    copy(
        surface = SurfaceColors(
            main = featureColors.subtleAlt,
            background = featureColors.subtle,
            subtle = featureColors.subtle,
            strong = featureColors.subtleAlt,
            disabled = surface.disabled,
        ),
        content = content.copy(
            normal = featureColors.strong,
        ),
        primary = featureColors.copy(
            subtle = featureColors.subtleAlt
        ),
    )
