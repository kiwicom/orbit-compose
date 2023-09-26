package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.ContentEmphasis

/**
 * Simple component used for highlighting coupons / promo codes.
 *
 * The [code] you provide will be converted to uppercase and wrapped in a dashed border.
 *
 * If you provide [onCopied] callback, the component will copy the displayed code to clipboard on long click.
 * You should provide adequate feedback to the user in reaction to this action. To disable this functionality,
 * pass null to the parameter.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
public fun Coupon(
    code: String,
    onCopied: (() -> Unit)?,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val uppercaseCode = remember(code) { code.uppercase() }
    val clipboardManager = LocalClipboardManager.current

    Text(
        text = uppercaseCode,
        style = OrbitTheme.typography.title6.copy(fontFeatureSettings = "tnum"),
        modifier = modifier
            .dashedBorder(
                color = OrbitTheme.colors.surface.strong,
                strokeWidth = 1.dp,
                strokeLength = 2.dp,
                cornerRadius = 6.dp,
            )
            .clip(RoundedCornerShape(6.dp))
            .combinedClickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                enabled = onCopied != null,
                onLongClick = {
                    clipboardManager.setText(AnnotatedString(uppercaseCode))
                    onCopied?.invoke()
                },
                onClick = {}, // just long click copies
            )
            .padding(horizontal = 4.dp, vertical = 2.dp),
    )
}

private fun Modifier.dashedBorder(
    color: Color,
    strokeWidth: Dp = 1.dp,
    strokeLength: Dp = 8.dp,
    cornerRadius: Dp = 0.dp,
): Modifier = drawWithCache {
    val strokeWidthPx = strokeWidth.toPx()
    val strokeLengthPx = strokeLength.toPx()

    @Suppress("NAME_SHADOWING")
    val cornerRadius = CornerRadius(cornerRadius.toPx())

    val topLeft = Offset(strokeWidthPx / 2f, strokeWidthPx / 2f)
    val size = Size(size.width - strokeWidthPx, size.height - strokeWidthPx)
    val style = Stroke(
        width = strokeWidthPx,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(strokeLengthPx, strokeLengthPx)),
    )

    onDrawBehind { drawRoundRect(color, topLeft, size, cornerRadius, style) }
}

@Preview
@Composable
internal fun CouponPreview() {
    Preview {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = "Your coupon:",
                modifier = Modifier.alignByBaseline(),
            )
            Coupon(
                code = "hxt3b81f",
                onCopied = null,
                modifier = Modifier.alignByBaseline(),
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            var copied by remember { mutableStateOf(false) }
            Text(
                text = "Copy your coupon:",
                modifier = Modifier.alignByBaseline(),
            )
            Coupon(
                code = "hxt3b81f",
                onCopied = { copied = true },
                modifier = Modifier.alignByBaseline(),
            )
            if (copied) {
                Text(
                    text = "Copied to clipboard!",
                    modifier = Modifier.alignByBaseline(),
                    emphasis = ContentEmphasis.Minor,
                    style = OrbitTheme.typography.bodySmall,
                )
            }
        }
    }
}
