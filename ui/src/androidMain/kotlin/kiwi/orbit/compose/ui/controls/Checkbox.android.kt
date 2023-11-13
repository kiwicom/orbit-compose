package kiwi.orbit.compose.ui.controls

import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import kiwi.orbit.compose.ui.R

@OptIn(ExperimentalAnimationGraphicsApi::class)
@Composable
internal actual fun getCheckPainter(checked: Boolean): Painter {
    val animatedImage = AnimatedImageVector.animatedVectorResource(R.drawable.avd_check)
    return rememberAnimatedVectorPainter(animatedImage, atEnd = checked)
}
