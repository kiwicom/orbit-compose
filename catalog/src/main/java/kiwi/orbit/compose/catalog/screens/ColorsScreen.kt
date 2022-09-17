package kiwi.orbit.compose.catalog.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kiwi.orbit.compose.ui.OrbitTheme
import kiwi.orbit.compose.ui.controls.Scaffold
import kiwi.orbit.compose.ui.controls.Surface
import kiwi.orbit.compose.ui.controls.SurfaceCard
import kiwi.orbit.compose.ui.controls.Text
import kiwi.orbit.compose.ui.controls.TopAppBar
import kiwi.orbit.compose.ui.foundation.BundleColors
import kiwi.orbit.compose.ui.foundation.FeatureColors
import kiwi.orbit.compose.ui.foundation.contentColorFor

@Composable
internal fun ColorsScreen(onNavigateUp: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Colors") },
                onNavigateUp = onNavigateUp,
            )
        },
    ) { contentPadding: PaddingValues ->
        Box(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding),
        ) {
            ColorsScreenInner()
        }
    }
}

@Composable
private fun ColorsScreenInner() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp),
    ) {
        SurfaceColors()
        ContentColors()
        FeatureColors(OrbitTheme.colors.primary, name = "Primary", orbitName = "Product")
        FeatureColors(OrbitTheme.colors.info, name = "Info", orbitName = "Blue")
        FeatureColors(OrbitTheme.colors.success, name = "Success", orbitName = "Green")
        FeatureColors(OrbitTheme.colors.warning, name = "Warning", orbitName = "Warning")
        FeatureColors(OrbitTheme.colors.critical, name = "Critical", orbitName = "Red")
        BundleColors(OrbitTheme.colors.bundle)
    }
}

@Composable
private fun SurfaceColors() {
    Column {
        Title("Surface – background colors")

        val colors = OrbitTheme.colors.surface
        SurfaceCard {
            Column {
                ColorRow {
                    Color(colors.main, "surface.main", "White")
                }
                ColorRow {
                    Color(colors.subtle, "surface.subtle", "CloudLight")
                    Color(colors.subtleAlt, "surface.subtleAlt", "CloudLightHover")
                }
                ColorRow {
                    Color(colors.normal, "surface.normal", "CloudNormal")
                    Color(colors.normalAlt, "surface.normalAlt", "CloudNormalHover")
                }
                ColorRow {
                    Color(colors.strong, "surface.strong", "CloudDark")
                    Color(colors.strongAlt, "surface.strongAlt", "CloudDarkHover")
                }
                ColorRow {
                    Color(colors.disabled, "surface.disabled", "CloudNormal")
                }
            }
        }
    }
}

@Composable
private fun ContentColors() {
    Column {
        Title("Content – text colors")

        val colors = OrbitTheme.colors.content
        val contentColor = OrbitTheme.colors.surface.main
        SurfaceCard {
            Column {
                ColorRow {
                    Color(colors.normal, "content.normal", "InkDark", contentColor)
                }
                ColorRow {
                    Color(colors.minor, "content.minor", "InkNormal", contentColor)
                    Color(colors.subtle, "content.subtle", "InkLight", contentColor)
                }
                ColorRow {
                    Color(colors.disabled, "content.disabled", "CloudDarkHover", contentColor)
                }
            }
        }
    }
}

@Composable
private fun FeatureColors(colors: FeatureColors, name: String, orbitName: String) {
    Column {
        Title(name)
        val featureName = name.lowercase()

        SurfaceCard {
            Column {
                ColorRow {
                    Color(colors.subtle, "$featureName.subtle", "${orbitName}Light")
                    Color(colors.subtleAlt, "$featureName.subtleAlt", "${orbitName}LightHover")
                }
                ColorRow {
                    Color(colors.normal, "$featureName.normal", "${orbitName}Normal")
                    Color(colors.normalAlt, "$featureName.normalAlt", "${orbitName}NormalHover")
                }
                ColorRow {
                    Color(colors.strong, "$featureName.strong", "${orbitName}Dark")
                    Color(colors.strongAlt, "$featureName.strongAlt", "${orbitName}DarkHover")
                }
            }
        }
    }
}

@Composable
private fun BundleColors(colors: BundleColors) {
    Column {
        Title("Bundle")

        SurfaceCard {
            Column {
                ColorRow {
                    Color(colors.basicGradient, "bundle.basicGradient", "BundleBasic gradient")
                    Color(colors.basic, "bundle.basic", "BundleBasic")
                }
                ColorRow {
                    Color(colors.mediumGradient, "bundle.mediumGradient", "BundleMedium gradient")
                    Color(colors.medium, "bundle.medium", "BundleMedium")
                }
                ColorRow {
                    Color(colors.topGradient, "bundle.topGradient", "BundleTop gradient")
                    Color(colors.top, "bundle.top", "BundleTop")
                }
            }
        }
    }
}

@Composable
private fun Title(name: String) {
    Text(name, Modifier.padding(bottom = 6.dp), style = OrbitTheme.typography.title4)
}

@Composable
private fun ColorRow(content: @Composable RowScope.() -> Unit) {
    Row(
        modifier = Modifier.height(IntrinsicSize.Max),
        content = content,
    )
}

@Composable
private fun RowScope.Color(
    color: Color,
    name: String,
    orbitName: String,
    contentColor: Color = Color.Unspecified,
) {
    Column(
        Modifier
            .weight(1f)
            .fillMaxHeight()
            .background(color)
            .padding(horizontal = 6.dp, vertical = 4.dp),
    ) {
        val finalContentColor = contentColor.takeOrElse { contentColorFor(color) }
        Text(
            name,
            style = OrbitTheme.typography.bodyNormalMedium,
            color = finalContentColor,
        )
        Text(
            text = orbitName,
            style = OrbitTheme.typography.bodySmall,
            color = finalContentColor,
            modifier = Modifier.weight(1f),
        )
        Text(
            text = "#%06X".format(color.toArgb() and 0xFFFFFF),
            style = OrbitTheme.typography.bodySmall,
            color = finalContentColor,
        )
    }
}

@Composable
private fun RowScope.Color(
    color: Brush,
    name: String,
    orbitName: String,
    contentColor: Color = Color.Unspecified,
) {
    Column(
        Modifier
            .fillMaxHeight()
            .weight(1f)
            .background(color)
            .padding(horizontal = 6.dp, vertical = 4.dp),
    ) {
        Text(
            name,
            color = contentColor.takeOrElse { contentColorFor(color) },
        )
        Text(
            text = orbitName,
            style = OrbitTheme.typography.bodySmall,
            color = contentColor.takeOrElse { contentColorFor(color) },
        )
    }
}

@Preview
@Composable
private fun ColorScreensPreview() {
    OrbitTheme {
        Surface(Modifier.verticalScroll(rememberScrollState())) {
            ColorsScreenInner()
        }
    }
}
