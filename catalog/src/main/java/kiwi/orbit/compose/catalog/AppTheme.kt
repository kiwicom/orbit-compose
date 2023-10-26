package kiwi.orbit.compose.catalog

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import kiwi.orbit.compose.catalog.utils.CirclePath
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.foundation.Typography
import kiwi.orbit.compose.ui.foundation.createTypography
import kiwi.orbit.compose.ui.foundation.darkColors
import kiwi.orbit.compose.ui.foundation.lightColors

@Composable
fun AppTheme(
    isLightTheme: Boolean = true,
    content: @Composable () -> Unit,
) {
    OrbitTheme(
        typography = rememberTypography(LocalContext.current),
        colors = when (isLightTheme) {
            true -> lightColors()
            false -> darkColors()
        },
        content = content,
    )
}

// Inspired by https://github.com/sanathsajeevakumara/ThemePickerAnimation
@Composable
internal fun AnimatedAppTheme(
    isLightTheme: Boolean,
    onThemeToggle: (Boolean) -> Unit,
    content: @Composable (onToggleTheme: (offset: Offset) -> Unit) -> Unit,
) {
    var animationOffset by remember { mutableStateOf(Offset(0f, 0f)) }
    AnimatedContent(
        targetState = isLightTheme,
        modifier = Modifier.fillMaxSize(),
        transitionSpec = {
            fadeIn(
                initialAlpha = 0f,
                animationSpec = tween(100),
            ) togetherWith fadeOut(
                targetAlpha = .9f,
                animationSpec = tween(800),
            )
        },
        label = "AppThemeChange",
    ) { currentTheme ->
        val revealSize = remember { Animatable(1f) }
        LaunchedEffect(Unit) {
            if (animationOffset.x > 0f) {
                revealSize.snapTo(0f)
                revealSize.animateTo(1f, animationSpec = tween(800))
            } else {
                revealSize.snapTo(1f)
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CirclePath(revealSize.value, animationOffset)),
        ) {
            AppTheme(isLightTheme = currentTheme) {
                content { offset ->
                    animationOffset = offset
                    onThemeToggle(!currentTheme)
                }
            }
        }
    }
}

@SuppressLint("DiscouragedApi")
@Composable
private fun rememberTypography(context: Context): Typography = remember {
    val resources = context.resources
    val packageName = context.packageName

    val book = resources.getIdentifier("circular20_book", "font", packageName)
    val medium = resources.getIdentifier("circular20_medium", "font", packageName)
    val bold = resources.getIdentifier("circular20_bold", "font", packageName)

    if (book == 0 || medium == 0 || bold == 0) {
        return@remember createTypography()
    }

    return@remember createTypography(
        defaultFontFamily = FontFamily(
            Font(book, FontWeight.Normal),
            Font(medium, FontWeight.Medium),
            Font(bold, FontWeight.Bold),
        ),
    )
}
