package kiwi.orbit.illustrations

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import kiwi.orbit.R

public val Illustrations.RentalCar: Painter
	@Composable
	get() {
		if (illustration != null) return illustration!!
		illustration = painterResource(R.drawable.il_rental_car)
		return illustration!!
	}

private var illustration: Painter? = null
