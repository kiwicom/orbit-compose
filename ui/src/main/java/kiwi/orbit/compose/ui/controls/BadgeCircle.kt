package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.LocalColors
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle
import kiwi.orbit.compose.ui.foundation.asCriticalTheme
import kiwi.orbit.compose.ui.foundation.asInfoTheme
import kiwi.orbit.compose.ui.foundation.asSuccessTheme
import kiwi.orbit.compose.ui.foundation.asWarningTheme

/**
 * Cloud colored circled Badge.
 */
@Composable
public fun BadgeCircleNeutral(
    value: Int,
    modifier: Modifier = Modifier,
) {
    val colors = with(OrbitTheme.colors) {
        copy(
            surface = surface.copy(main = surface.background),
        )
    }
    CompositionLocalProvider(
        LocalColors provides colors,
    ) {
        BadgeCircle(subtle = true, value, modifier)
    }
}

/**
 * White colored circled Badge.
 */
@Composable
public fun BadgeCircleNeutralSubtle(
    value: Int,
    modifier: Modifier = Modifier,
) {
    val colors = with(OrbitTheme.colors) {
        copy(
            surface = surface.copy(background = surface.main),
        )
    }
    CompositionLocalProvider(
        LocalColors provides colors,
    ) {
        BadgeCircle(subtle = true, value, modifier)
    }
}

/**
 * Black colored circled Badge.
 */
@Composable
public fun BadgeCircleNeutralStrong(
    value: Int,
    modifier: Modifier = Modifier,
) {
    val colors = with(OrbitTheme.colors) {
        copy(
            primary = primary.copy(
                normal = this.content.normal,
                onNormal = surface.main,
            )
        )
    }
    CompositionLocalProvider(
        LocalColors provides colors,
    ) {
        BadgeCircle(subtle = false, value, modifier)
    }
}

/**
 * Blue normal colored circled Badge.
 */
@Composable
public fun BadgeCircleInfo(
    value: Int,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.asInfoTheme(),
    ) {
        BadgeCircle(subtle = false, value, modifier)
    }
}

/**
 * Blue light colored circled Badge.
 */
@Composable
public fun BadgeCircleInfoSubtle(
    value: Int,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.asInfoTheme(),
    ) {
        BadgeCircle(subtle = true, value, modifier)
    }
}

/**
 * Green normal colored circled Badge.
 */
@Composable
public fun BadgeCircleSuccess(
    value: Int,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.asSuccessTheme(),
    ) {
        BadgeCircle(subtle = false, value, modifier)
    }
}

/**
 * Green light colored circled Badge.
 */
@Composable
public fun BadgeCircleSuccessSubtle(
    value: Int,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.asSuccessTheme(),
    ) {
        BadgeCircle(subtle = true, value, modifier)
    }
}

/**
 * Orange normal colored circled Badge.
 */
@Composable
public fun BadgeCircleWarning(
    value: Int,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.asWarningTheme(),
    ) {
        BadgeCircle(subtle = false, value, modifier)
    }
}

/**
 * Orange light colored circled Badge.
 */
@Composable
public fun BadgeCircleWarningSubtle(
    value: Int,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.asWarningTheme(),
    ) {
        BadgeCircle(subtle = true, value, modifier)
    }
}

/**
 * Red normal colored circled Badge.
 */
@Composable
public fun BadgeCircleCritical(
    value: Int,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.asCriticalTheme(),
    ) {
        BadgeCircle(subtle = false, value, modifier)
    }
}

/**
 * Red light colored circled Badge.
 */
@Composable
public fun BadgeCircleCriticalSubtle(
    value: Int,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(
        LocalColors provides OrbitTheme.colors.asCriticalTheme(),
    ) {
        BadgeCircle(subtle = true, value, modifier)
    }
}

@Composable
private fun BadgeCircle(
    subtle: Boolean,
    value: Int,
    modifier: Modifier,
) {
    ThemedSurface(
        modifier = modifier,
        subtle = subtle,
        shape = CircleShape,
        strokeWidth = BadgeStrokeWidth,
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = BadgeContentPadding,
    ) {
        ProvideMergedTextStyle(OrbitTheme.typography.bodySmallMedium) {
            Text(text = value.toString())
        }
    }
}

@Preview
@Composable
internal fun BadgeCirclePreview() {
    Preview {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            BadgeCircleInfo(1)
            BadgeCircleInfoSubtle(1)
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            BadgeCircleSuccess(1)
            BadgeCircleSuccessSubtle(1)
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            BadgeCircleWarning(1)
            BadgeCircleWarningSubtle(1)
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            BadgeCircleCritical(1)
            BadgeCircleCriticalSubtle(1)
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            BadgeCircleNeutral(1)
            BadgeCircleNeutralSubtle(1)
            BadgeCircleNeutralStrong(1)
        }
    }
}
