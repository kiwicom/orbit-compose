package kiwi.orbit.illustrations

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import kiwi.orbit.R

public val Illustrations.ReferAFriend: Painter
	@Composable
	get() {
		if (illustration != null) return illustration!!
		illustration = painterResource(R.drawable.il_refer_a_friend)
		return illustration!!
	}

private var illustration: Painter? = null
