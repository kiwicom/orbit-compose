package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalContentColor
import kiwi.orbit.compose.ui.foundation.LocalContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalTextStyle
import kiwi.orbit.compose.ui.foundation.applyEmphasis

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
    // Defaults icon size to 20.sp (~20.dp), bodyNormal's line-height.
    val lineHeight = with(LocalDensity.current) {
        LocalTextStyle.current.lineHeight.toDp()
    }
    Box(
        modifier
            .toolingGraphicsLayer()
            .size(lineHeight)
            .paint(
                painter,
                colorFilter = colorFilter,
                contentScale = ContentScale.Fit,
            )
            .then(semantics),
    )
}
