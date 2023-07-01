// ktlint-disable filename

package kiwi.orbit.compose.ui.layout

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import kiwi.orbit.compose.ui.foundation.LocalTextStyle

/**
 * Aligns the composable vertically to the current LineHeight size.
 */
public fun Modifier.alignByLineHeight(
    textStyle: TextStyle? = null,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
): Modifier = composed {
    val style: TextStyle = textStyle ?: LocalTextStyle.current
    val density = LocalDensity.current
    val height = with(density) { style.lineHeight.toDp() }
    Modifier
        .height(height)
        .wrapContentHeight(align = verticalAlignment)
}
