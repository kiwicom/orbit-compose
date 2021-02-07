package kiwi.orbit.icons

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import kiwi.orbit.R

@get:Composable
public val Icons.CarDoor: Painter
	get() {
		if (icon != null) return icon!!
		icon = painterResource(R.drawable.ic_car_door)
		return icon!!
	}

private var icon: Painter? = null
