package kiwi.orbit.illustrations

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import kiwi.orbit.R

@get:Composable
public val Illustrations.OnlineCheckIn: Painter
	get() {
		if (illustration != null) return illustration!!
		illustration = painterResource(R.drawable.il_online_check_in)
		return illustration!!
	}

private var illustration: Painter? = null
