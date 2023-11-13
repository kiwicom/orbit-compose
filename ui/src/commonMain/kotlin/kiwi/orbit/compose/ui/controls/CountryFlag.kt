package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.internal.KnownCountryFlags
import kiwi.orbit.compose.ui.controls.internal.OrbitPreviews
import kiwi.orbit.compose.ui.controls.internal.Preview
import kiwi.orbit.compose.ui.foundation.LocalTextStyle
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

/**
 * Implementation of [Orbit CountryFlag](https://orbit.kiwi/components/countryflag/)
 *
 * - Size is resolved to current line-height. To modify the size, use [Modifier.size]. Defaults to 20.sp.
 */
@OptIn(ExperimentalResourceApi::class)
@Composable
public fun CountryFlag(
    countryCode: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
) {
    // Default size -> 20.sp (~20.dp), bodyNormal's line-height.
    val lineHeight = with(LocalDensity.current) {
        LocalTextStyle.current.lineHeight.toDp()
    }

    Image(
        painter = painterResource(
            when (countryCode) {
                in KnownCountryFlags -> "country_flags/$countryCode.png"
                else -> "country_flags/unknown.png"
            },
        ),
        contentScale = ContentScale.Fit,
        contentDescription = contentDescription,
        modifier = modifier
            .size(lineHeight)
            .aspectRatio(12 / 7f)
            .clip(RoundedCornerShape(2.dp))
            .border(
                width = 0.5.dp,
                color = OrbitTheme.colors.content.normal.copy(alpha = .15f),
                shape = RoundedCornerShape(2.dp),
            ),
    )
}

@OrbitPreviews
@Composable
internal fun CountryFlagPreview() {
    Preview {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            CountryFlag(countryCode = "cz", contentDescription = null)
            CountryFlag(countryCode = "sk", contentDescription = null)
            CountryFlag(countryCode = "", contentDescription = null)
        }
    }
}
