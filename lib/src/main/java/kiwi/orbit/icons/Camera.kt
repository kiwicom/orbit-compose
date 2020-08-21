package kiwi.orbit.icons

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.VectorAsset
import androidx.compose.ui.res.vectorResource
import kiwi.orbit.R

@Composable
val Icon.Camera: VectorAsset
	get() {
		if (icon != null) return icon!!
		icon = vectorResource(id = R.drawable.ic_camera)
		return icon!!
	}

private var icon: VectorAsset? = null
