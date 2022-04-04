package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import coil.size.Scale
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.R
import kiwi.orbit.compose.ui.foundation.LocalTextStyle

/**
 * Implementation of [Orbit CountryFlag](https://orbit.kiwi/components/countryflag/)
 *
 * - Size is resolved to current line-height. To modify the size, use [Modifier.size]. Defaults to 20.sp.
 */
@Composable
public fun CountryFlag(
    countryCode: String,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    val url = "file:///android_asset/country_flags/$countryCode.png"

    // Default size -> 20.sp (~20.dp), bodyNormal's line-height.
    val lineHeight = with(LocalDensity.current) {
        LocalTextStyle.current.lineHeight.toDp()
    }
    Image(
        painter = rememberImagePainter(url) {
            crossfade(true)
            size(OriginalSize)
            scale(Scale.FIT)
            error(R.drawable.orbit_country_flag_undefined)
        },
        contentDescription = contentDescription,
        modifier = modifier
            .size(lineHeight)
            .aspectRatio(12 / 7f)
            .clip(RoundedCornerShape(2.dp))
            .border(
                width = 0.5.dp,
                color = OrbitTheme.colors.content.normal.copy(alpha = .15f),
                shape = RoundedCornerShape(2.dp),
            )
    )
}
