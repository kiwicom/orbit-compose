package kiwi.orbit.compose.icons

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

public enum class ColoredIconName {
    Facebook,
    Google,
    Imessage,
    Messenger,
    Signal,
    Telegram,
    Twitter,
    Viber,
    Whatsapp,
}

@Composable
public fun ColoredIconName.painter(): Painter = when (this) {
    ColoredIconName.Facebook -> ColoredIcons.Facebook
    ColoredIconName.Google -> ColoredIcons.Google
    ColoredIconName.Imessage -> ColoredIcons.Imessage
    ColoredIconName.Messenger -> ColoredIcons.Messenger
    ColoredIconName.Signal -> ColoredIcons.Signal
    ColoredIconName.Telegram -> ColoredIcons.Telegram
    ColoredIconName.Twitter -> ColoredIcons.Twitter
    ColoredIconName.Viber -> ColoredIcons.Viber
    ColoredIconName.Whatsapp -> ColoredIcons.Whatsapp
}
