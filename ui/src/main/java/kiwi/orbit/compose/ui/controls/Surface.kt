@file:Suppress("Dependency")

package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalContentColor
import kiwi.orbit.compose.ui.foundation.LocalContentEmphasis
import kiwi.orbit.compose.ui.foundation.contentColorFor
import kotlin.math.ln

@Composable
public fun Surface(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    color: Color = OrbitTheme.colors.surface.main,
    contentColor: Color = contentColorFor(color),
    border: BorderStroke? = null,
    elevation: Dp = OrbitTheme.elevations.None,
    content: @Composable () -> Unit,
) {
    val absoluteElevation = LocalAbsoluteTonalElevation.current + elevation
    CompositionLocalProvider(
        LocalContentColor provides contentColor,
        LocalContentEmphasis provides ContentEmphasis.Normal,
        LocalAbsoluteTonalElevation provides absoluteElevation,
    ) {
        Box(
            modifier = modifier
                .surface(
                    shape = shape,
                    backgroundColor = surfaceColorAtElevation(
                        color = color,
                        elevation = absoluteElevation,
                    ),
                    border = border,
                    elevation = elevation,
                )
                .semantics(mergeDescendants = false) {
                    isContainer = true
                }
                .pointerInput(Unit) {},
            propagateMinConstraints = true,
        ) {
            content()
        }
    }
}

@Composable
public fun Surface(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RectangleShape,
    color: Color = OrbitTheme.colors.surface.main,
    contentColor: Color = contentColorFor(color),
    border: BorderStroke? = null,
    elevation: Dp = OrbitTheme.elevations.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit,
) {
    val absoluteElevation = LocalAbsoluteTonalElevation.current + elevation
    CompositionLocalProvider(
        LocalContentColor provides contentColor,
        LocalContentEmphasis provides ContentEmphasis.Normal,
        LocalAbsoluteTonalElevation provides absoluteElevation,
    ) {
        Box(
            modifier
                .surface(
                    shape = shape,
                    backgroundColor = surfaceColorAtElevation(
                        color = color,
                        elevation = absoluteElevation,
                    ),
                    border = border,
                    elevation = elevation,
                )
                .clickable(
                    interactionSource = interactionSource,
                    indication = rememberRipple(),
                    enabled = enabled,
                    role = Role.Button,
                    onClick = onClick,
                ),
            propagateMinConstraints = true,
        ) {
            content()
        }
    }
}

@Composable
public fun Surface(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RectangleShape,
    color: Color = OrbitTheme.colors.surface.main,
    contentColor: Color = contentColorFor(color),
    border: BorderStroke? = null,
    elevation: Dp = OrbitTheme.elevations.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit,
) {
    val absoluteElevation = LocalAbsoluteTonalElevation.current + elevation
    CompositionLocalProvider(
        LocalContentColor provides contentColor,
        LocalContentEmphasis provides ContentEmphasis.Normal,
        LocalAbsoluteTonalElevation provides absoluteElevation,
    ) {
        Box(
            modifier
                .surface(
                    shape = shape,
                    backgroundColor = surfaceColorAtElevation(
                        color = color,
                        elevation = absoluteElevation,
                    ),
                    border = border,
                    elevation = elevation,
                )
                .selectable(
                    selected = selected,
                    interactionSource = interactionSource,
                    indication = rememberRipple(),
                    enabled = enabled,
                    role = Role.Tab,
                    onClick = onClick,
                ),
            propagateMinConstraints = true,
        ) {
            content()
        }
    }
}

@Composable
public fun Surface(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RectangleShape,
    color: Color = OrbitTheme.colors.surface.main,
    contentColor: Color = contentColorFor(color),
    border: BorderStroke? = null,
    elevation: Dp = OrbitTheme.elevations.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit,
) {
    val absoluteElevation = LocalAbsoluteTonalElevation.current + elevation
    CompositionLocalProvider(
        LocalContentColor provides contentColor,
        LocalContentEmphasis provides ContentEmphasis.Normal,
        LocalAbsoluteTonalElevation provides absoluteElevation,
    ) {
        Box(
            modifier
                .surface(
                    shape = shape,
                    backgroundColor = surfaceColorAtElevation(
                        color = color,
                        elevation = absoluteElevation,
                    ),
                    border = border,
                    elevation = elevation,
                )
                .toggleable(
                    value = checked,
                    interactionSource = interactionSource,
                    indication = rememberRipple(),
                    enabled = enabled,
                    role = Role.Switch,
                    onValueChange = onCheckedChange,
                ),
            propagateMinConstraints = true,
        ) {
            content()
        }
    }
}

internal fun Modifier.surface(
    shape: Shape,
    backgroundColor: Color,
    border: BorderStroke?,
    elevation: Dp,
): Modifier = this
    .shadow(elevation, shape, clip = false)
    .then(if (border != null) Modifier.border(border, shape) else Modifier)
    .background(color = backgroundColor, shape = shape)
    .clip(shape)

@Composable
internal fun surfaceColorAtElevation(
    color: Color,
    elevation: Dp,
): Color {
    return if (color == OrbitTheme.colors.surface.main && !OrbitTheme.colors.isLight) {
        if (elevation == 0.dp) return color
        val alpha = ((4.5f * ln(elevation.value + 1)) + 2f) / 100f
        return contentColorFor(color).copy(alpha = alpha).compositeOver(color)
    } else {
        color
    }
}
