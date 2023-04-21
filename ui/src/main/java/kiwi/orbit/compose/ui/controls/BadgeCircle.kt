package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.Colors
import kiwi.orbit.compose.ui.foundation.LocalColors
import kiwi.orbit.compose.ui.foundation.asCriticalTheme
import kiwi.orbit.compose.ui.foundation.asInfoTheme
import kiwi.orbit.compose.ui.foundation.asNeutralSubtleStrongTheme
import kiwi.orbit.compose.ui.foundation.asNeutralTheme
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
    BadgeCircle(OrbitTheme.colors.asNeutralTheme(), subtle = true, value, modifier)
}

/**
 * White colored circled Badge.
 */
@Composable
public fun BadgeCircleNeutralSubtle(
    value: Int,
    modifier: Modifier = Modifier,
) {
    BadgeCircle(OrbitTheme.colors.asNeutralSubtleStrongTheme(), subtle = true, value, modifier)
}

/**
 * Black colored circled Badge.
 */
@Composable
public fun BadgeCircleNeutralStrong(
    value: Int,
    modifier: Modifier = Modifier,
) {
    BadgeCircle(OrbitTheme.colors.asNeutralSubtleStrongTheme(), subtle = false, value, modifier)
}

/**
 * Blue normal colored circled Badge.
 */
@Composable
public fun BadgeCircleInfo(
    value: Int,
    modifier: Modifier = Modifier,
) {
    BadgeCircle(OrbitTheme.colors.asInfoTheme(), subtle = false, value, modifier)
}

/**
 * Blue light colored circled Badge.
 */
@Composable
public fun BadgeCircleInfoSubtle(
    value: Int,
    modifier: Modifier = Modifier,
) {
    BadgeCircle(OrbitTheme.colors.asInfoTheme(), subtle = true, value, modifier)
}

/**
 * Green normal colored circled Badge.
 */
@Composable
public fun BadgeCircleSuccess(
    value: Int,
    modifier: Modifier = Modifier,
) {
    BadgeCircle(OrbitTheme.colors.asSuccessTheme(), subtle = false, value, modifier)
}

/**
 * Green light colored circled Badge.
 */
@Composable
public fun BadgeCircleSuccessSubtle(
    value: Int,
    modifier: Modifier = Modifier,
) {
    BadgeCircle(OrbitTheme.colors.asSuccessTheme(), subtle = true, value, modifier)
}

/**
 * Orange normal colored circled Badge.
 */
@Composable
public fun BadgeCircleWarning(
    value: Int,
    modifier: Modifier = Modifier,
) {
    BadgeCircle(OrbitTheme.colors.asWarningTheme(), subtle = false, value, modifier)
}

/**
 * Orange light colored circled Badge.
 */
@Composable
public fun BadgeCircleWarningSubtle(
    value: Int,
    modifier: Modifier = Modifier,
) {
    BadgeCircle(OrbitTheme.colors.asWarningTheme(), subtle = true, value, modifier)
}

/**
 * Red normal colored circled Badge.
 */
@Composable
public fun BadgeCircleCritical(
    value: Int,
    modifier: Modifier = Modifier,
) {
    BadgeCircle(OrbitTheme.colors.asCriticalTheme(), subtle = false, value, modifier)
}

/**
 * Red light colored circled Badge.
 */
@Composable
public fun BadgeCircleCriticalSubtle(
    value: Int,
    modifier: Modifier = Modifier,
) {
    BadgeCircle(OrbitTheme.colors.asCriticalTheme(), subtle = true, value, modifier)
}

@Composable
private fun BadgeCircle(
    colors: Colors,
    subtle: Boolean,
    value: Int,
    modifier: Modifier = Modifier,
) {
    val height = with(LocalDensity.current) {
        OrbitTheme.typography.bodySmallMedium.lineHeight.toDp() +
            BadgeDefaults.ContentPadding.calculateTopPadding() +
            BadgeDefaults.ContentPadding.calculateBottomPadding()
    }
    val backgroundColor = when (subtle) {
        true -> colors.primary.subtle
        false -> colors.primary.normal
    }
    CompositionLocalProvider(
        LocalColors provides colors,
    ) {
        BadgePrimitive(
            modifier = modifier
                .requiredHeight(height)
                .requiredWidth(height),
            backgroundColor = backgroundColor,
            backgroundBrush = null,
            borderStroke = null,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = ContentPadding,
            icon = {},
            content = {
                Text(text = value.toString(), maxLines = 1, overflow = TextOverflow.Visible)
            },
        )
    }
}

@OrbitPreviews
@Composable
internal fun BadgeCirclePreview() {
    Preview {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            BadgeCircleInfo(1)
            BadgeCircleInfoSubtle(5)
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            BadgeCircleSuccess(2)
            BadgeCircleSuccessSubtle(6)
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            BadgeCircleWarning(3)
            BadgeCircleWarningSubtle(7)
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            BadgeCircleCritical(4)
            BadgeCircleCriticalSubtle(8)
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            BadgeCircleNeutral(10)
            BadgeCircleNeutralSubtle(100)
            BadgeCircleNeutralStrong(1234)
        }
    }
}

private val ContentPadding = PaddingValues(
    top = BadgeDefaults.ContentPadding.calculateTopPadding(),
    bottom = BadgeDefaults.ContentPadding.calculateBottomPadding(),
)
