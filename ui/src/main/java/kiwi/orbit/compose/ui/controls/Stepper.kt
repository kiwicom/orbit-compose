package kiwi.orbit.compose.ui.controls

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.icons.Icons
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.R
import kiwi.orbit.compose.ui.foundation.ContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalContentEmphasis
import kiwi.orbit.compose.ui.foundation.LocalTextStyle

@Composable
public fun Stepper(
    value: Int,
    modifier: Modifier = Modifier,
    onValueChange: (Int) -> Unit,
    minValue: Int = 0,
    maxValue: Int = Int.MAX_VALUE,
) {
    check(value in minValue..maxValue) { "Provided value is out of required bounds." }

    Stepper(
        value = value,
        modifier = modifier,
        onValueChange = onValueChange,
        valueValidator = { newValue ->
            newValue in minValue..maxValue
        }
    )
}

@Composable
public fun Stepper(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    addContentDescription: String = stringResource(id = R.string.orbit_cd_stepper_add),
    removeContentDescription: String = stringResource(id = R.string.orbit_cd_stepper_remove),
    valueValidator: ((Int) -> Boolean)? = null,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        StepperButton(
            onClick = { onValueChange.invoke(value - 1) },
            enabled = valueValidator?.invoke(value - 1) ?: true,
        ) {
            Icon(Icons.Minus, contentDescription = removeContentDescription)
        }

        Text(
            text = value.toString(),
            style = OrbitTheme.typography.bodyNormalBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.defaultMinSize(44.dp),
        )

        StepperButton(
            onClick = { onValueChange.invoke(value + 1) },
            enabled = valueValidator?.invoke(value + 1) ?: true,
        ) {
            Icon(Icons.Plus, contentDescription = addContentDescription)
        }
    }
}

@Composable
private fun StepperButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Surface(
        onClick = onClick,
        shape = OrbitTheme.shapes.small,
        color = OrbitTheme.colors.surface.strong,
        contentColor = OrbitTheme.colors.content.normal,
        interactionSource = interactionSource,
        indication = null,
        enabled = enabled,
    ) {
        CompositionLocalProvider(
            LocalTextStyle provides OrbitTheme.typography.bodyNormal,
            LocalContentEmphasis provides if (enabled) ContentEmphasis.Normal else ContentEmphasis.Disabled,
        ) {
            Row(
                Modifier
                    .indication(interactionSource, LocalIndication.current)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                content = content,
            )
        }
    }
}
