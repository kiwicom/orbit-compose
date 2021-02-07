package kiwi.orbit.icons

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import kiwi.orbit.R

@get:Composable
public val Icons.InsuranceOff: Painter
	get() {
		if (icon != null) return icon!!
		icon = painterResource(R.drawable.ic_insurance_off)
		return icon!!
	}

private var icon: Painter? = null
