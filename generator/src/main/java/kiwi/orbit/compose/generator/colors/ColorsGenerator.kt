package kiwi.orbit.compose.generator.colors

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import java.net.URL
import java.nio.file.Path
import kotlin.math.roundToInt
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import net.thisptr.jackson.jq.BuiltinFunctionLoader
import net.thisptr.jackson.jq.JsonQuery
import net.thisptr.jackson.jq.Scope
import net.thisptr.jackson.jq.Versions
import net.thisptr.jackson.jq.module.loaders.BuiltinModuleLoader

@OptIn(ExperimentalStdlibApi::class)
class ColorsGenerator {
    @Serializable
    private data class ColorDefinition(
        val name: String,
        val color: Color?,
        val gradient: List<ColorPair>?,
    ) {
        @Serializable
        data class Color(
            val r: Double,
            val g: Double,
            val b: Double,
            val a: Double,
        )

        @Serializable
        data class ColorPair(
            val color: Color,
            val position: Float,
        )
    }

    private val hexFormat = HexFormat {
        upperCase = true
        number.prefix = "0x"
    }

    fun build(figmaToken: String, kotlinOutDir: Path) {
        val colors = downloadColors(figmaToken)
        generateClass(colors, kotlinOutDir)
    }

    private fun downloadColors(
        figmaToken: String,
    ): List<Pair<String, ColorDefinition.Color>> {
        val url = "https://api.figma.com/v1/files/2rTHlBKKR6IWGeP6Dw6qbP/nodes?" +
            "ids=2002%3A506,2002%3A448,2002%3A350,2002%3A767,2002%3A634,2002%3A709,2002%3A566,2002%3A836,2002%3A351"
        val fullJson = URL(url)
            .openConnection()
            .apply {
                setRequestProperty("X-Figma-Token", figmaToken)
            }
            .getInputStream()
            .buffered()
            .reader()
            .readText()

        val rootScope = Scope.newEmptyScope()
        BuiltinFunctionLoader.getInstance().loadFunctions(Versions.JQ_1_6, rootScope)
        rootScope.moduleLoader = BuiltinModuleLoader.getInstance()
        val childScope = Scope.newChildScope(rootScope)
        val query = JsonQuery.compile(
            "[.. | select(.children?[0].name?==\"Color\") " +
                "| {name: .name, color: .children[0].fills[0].color, gradient: .children[0].fills[0].gradientStops}]",
            Versions.JQ_1_6,
        )
        val out = arrayListOf<JsonNode>()
        val inJson = ObjectMapper().readTree(fullJson)
        query.apply(childScope, inJson, out::add)

        val filteredJson = out.first().toString()
        val parser = Json { isLenient = true }
        val parsed = parser.decodeFromString<List<ColorDefinition>>(filteredJson)

        return buildList {
            parsed.forEach { colorDefinition ->
                val name = colorDefinition.name
                    .replace(":", "")
                    .replace("gradient", "")
                    .split(' ')
                    .joinToString(separator = "") {
                        it.trim().replaceFirstChar { it.uppercase() }
                    }
                colorDefinition.color?.let { color ->
                    add(name to color)
                }
                colorDefinition.gradient?.let { gradient ->
                    val start = gradient.first { it.position <= 0.01 }
                    val end = gradient.first { it.position >= 0.99 }
                    add("${name}Start" to start.color)
                    add("${name}End" to end.color)
                }
            }
        }.sortedBy { it.first }
    }

    private fun generateClass(colors: List<Pair<String, ColorDefinition.Color>>, dir: Path) {
        val colorClass = ClassName("kiwi.orbit.compose.ui.foundation.tokens", "ColorTokens")
        val colorType = ClassName("androidx.compose.ui.graphics", "Color")
        val objectBuilder = TypeSpec.objectBuilder(colorClass)
            .addModifiers(KModifier.INTERNAL)

        colors.forEach { (name, colorString) ->
            val red = (colorString.r * 255).roundToInt()
            val green = (colorString.g * 255).roundToInt()
            val blue = (colorString.b * 255).roundToInt()
            val colorValue = (0xFF shl 24) or
                ((red and 0xFF) shl 16) or
                ((green and 0xFF) shl 8) or
                (blue and 0xFF)

            val property = PropertySpec.builder(name, colorType)
                .initializer(
                    "%T(%L)",
                    colorType,
                    colorValue.toHexString(hexFormat),
                )
                .build()
            objectBuilder.addProperty(property)
        }

        val file = FileSpec.builder("kiwi.orbit.compose.ui.foundation.tokens", "ColorTokens")
            .addType(objectBuilder.build())
            .addAliasedImport(colorType, "UIColor")
            .indent("    ")
            .build()

        file.writeTo(dir)
    }
}
