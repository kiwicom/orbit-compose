package kiwi.orbit.compose.icons

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import kotlin.OptIn
import kotlin.Suppress
import org.jetbrains.compose.resources.painterResource

/**
 * ColoredIcons are multi-colored icons and should be rendered using Image composable.
 */
@Suppress("unused")
@OptIn(org.jetbrains.compose.resources.ExperimentalResourceApi::class)
public object ColoredIcons {
    public val Facebook: Painter
        @Composable
        get() = painterResource("ic_orbit_colored_facebook.xml")

    public val Google: Painter
        @Composable
        get() = painterResource("ic_orbit_google.xml")

    public val Imessage: Painter
        @Composable
        get() = painterResource("ic_orbit_colored_imessage.xml")

    public val Messenger: Painter
        @Composable
        get() = painterResource("ic_orbit_colored_messenger.xml")

    public val Signal: Painter
        @Composable
        get() = painterResource("ic_orbit_colored_signal.xml")

    public val Telegram: Painter
        @Composable
        get() = painterResource("ic_orbit_colored_telegram.xml")

    public val Twitter: Painter
        @Composable
        get() = painterResource("ic_orbit_colored_twitter.xml")

    public val Viber: Painter
        @Composable
        get() = painterResource("ic_orbit_colored_viber.xml")

    public val Whatsapp: Painter
        @Composable
        get() = painterResource("ic_orbit_colored_whatsapp.xml")
}
