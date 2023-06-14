package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toolingGraphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.IntrinsicMeasurable
import androidx.compose.ui.layout.IntrinsicMeasureScope
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.constrain
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalContentColor
import kiwi.orbit.compose.ui.foundation.LocalContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalTextStyle
import kiwi.orbit.compose.ui.foundation.ProvideMergedTextStyle
import kiwi.orbit.compose.ui.foundation.applyEmphasis
import kiwi.orbit.compose.ui.layout.size

/**
 * Orbit Icon.
 *
 * - Size is resolved to current line-height. To modify the size, use [Modifier.size]. Defaults to 20.sp.
 * - Color is resolved to current text color, possible to change [emphasis] or [tint] icon directly.
 */
@Composable
public fun Icon(
    imageVector: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    emphasis: ContentEmphasis = LocalContentEmphasis.current,
    tint: Color = LocalContentColor.current.applyEmphasis(emphasis),
) {
    Icon(
        painter = rememberVectorPainter(imageVector),
        contentDescription = contentDescription,
        modifier = modifier,
        tint = tint,
    )
}

/**
 * Orbit Icon.
 *
 * - Size is resolved to current line-height. To modify the size, use [Modifier.size]. Defaults to 20.sp.
 * - Color is resolved to current text color, possible to change [emphasis] or [tint] icon directly.
 */
@Composable
public fun Icon(
    bitmap: ImageBitmap,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    emphasis: ContentEmphasis = LocalContentEmphasis.current,
    tint: Color = LocalContentColor.current.applyEmphasis(emphasis),
) {
    val painter = remember(bitmap) { BitmapPainter(bitmap) }
    Icon(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = tint,
    )
}

/**
 * Orbit Icon.
 *
 * - Size is resolved to current line-height. To modify the size, use [Modifier.size]. Defaults to 20.sp.
 * - Color is resolved to current text color, possible to change [emphasis] or [tint] icon directly.
 */
@Composable
public fun Icon(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    emphasis: ContentEmphasis = LocalContentEmphasis.current,
    tint: Color = LocalContentColor.current.applyEmphasis(emphasis),
) {
    val colorFilter = if (tint == Color.Unspecified) null else ColorFilter.tint(tint)
    val semantics = if (contentDescription != null) {
        Modifier.semantics {
            this.contentDescription = contentDescription
            this.role = Role.Image
        }
    } else {
        Modifier
    }
    val lineHeight = LocalTextStyle.current.lineHeight
    val sizeModifier = when (lineHeight.type) {
        TextUnitType.Sp -> DefaultSizeModifier(lineHeight)
        else -> Modifier
    }
    Box(
        modifier
            .toolingGraphicsLayer()
            .then(sizeModifier)
            .paint(
                painter = painter,
                colorFilter = colorFilter,
                contentScale = ContentScale.Fit,
            )
            .then(semantics),
    )
}

private data class DefaultSizeModifier(
    private val defaultSize: TextUnit,
) : LayoutModifier {
    override fun MeasureScope.measure(measurable: Measurable, constraints: Constraints): MeasureResult {
        val size = defaultSize.roundToPx()
        val scaledConstraints = constraints.constrain(Constraints.fixed(size, size))
        val placeable = measurable.measure(scaledConstraints)
        return layout(placeable.width, placeable.height) {
            placeable.placeRelative(0, 0)
        }
    }

    override fun IntrinsicMeasureScope.maxIntrinsicHeight(measurable: IntrinsicMeasurable, width: Int): Int =
        defaultSize.roundToPx()

    override fun IntrinsicMeasureScope.maxIntrinsicWidth(measurable: IntrinsicMeasurable, height: Int): Int =
        defaultSize.roundToPx()

    override fun IntrinsicMeasureScope.minIntrinsicHeight(measurable: IntrinsicMeasurable, width: Int): Int =
        defaultSize.roundToPx()

    override fun IntrinsicMeasureScope.minIntrinsicWidth(measurable: IntrinsicMeasurable, height: Int): Int =
        defaultSize.roundToPx()
}

@OrbitPreviews
@Composable
internal fun IconPreview() {
    Preview {
        Row {
            Icon(Icons.Cake, contentDescription = null)
            Icon(Icons.Cake, contentDescription = null, Modifier.size(20.sp))
            Icon(Icons.Cake, contentDescription = null, Modifier.size(24.sp))
            Icon(Icons.Cake, contentDescription = null, Modifier.size(20.dp))
            Icon(Icons.Cake, contentDescription = null, Modifier.size(24.dp))
            Box(Modifier.size(16.dp)) {
                Icon(Icons.Cake, contentDescription = null)
            }
        }
        Row {
            ProvideMergedTextStyle(OrbitTheme.typography.bodyLarge) {
                Icon(Icons.Cake, contentDescription = null)
                Icon(Icons.Cake, contentDescription = null, Modifier.size(22.sp))
                Icon(Icons.Cake, contentDescription = null, Modifier.size(26.sp))
                Icon(Icons.Cake, contentDescription = null, Modifier.size(22.dp))
                Icon(Icons.Cake, contentDescription = null, Modifier.size(26.dp))
            }
        }
    }
}
