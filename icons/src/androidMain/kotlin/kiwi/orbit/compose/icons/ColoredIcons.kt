package kiwi.orbit.compose.icons

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import kotlin.Suppress

/**
 * ColoredIcons are multi-colored icons and should be rendered using Image composable.
 */
@Suppress("unused")
public object ColoredIcons {
    public val Facebook: Painter
        @Composable
        get() = painterResource(R.drawable.ic_orbit_colored_facebook)

    public val Google: Painter
        @Composable
        get() = painterResource(R.drawable.ic_orbit_google)

    public val Imessage: Painter
        @Composable
        get() = painterResource(R.drawable.ic_orbit_colored_imessage)

    public val Messenger: Painter
        @Composable
        get() = painterResource(R.drawable.ic_orbit_colored_messenger)

    public val Signal: Painter
        @Composable
        get() = painterResource(R.drawable.ic_orbit_colored_signal)

    public val Telegram: Painter
        @Composable
        get() = painterResource(R.drawable.ic_orbit_colored_telegram)

    public val Twitter: Painter
        @Composable
        get() = painterResource(R.drawable.ic_orbit_colored_twitter)

    public val Viber: Painter
        @Composable
        get() = painterResource(R.drawable.ic_orbit_colored_viber)

    public val Whatsapp: Painter
        @Composable
        get() = painterResource(R.drawable.ic_orbit_colored_whatsapp)
}
