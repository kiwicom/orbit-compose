package kiwi.orbit.compose.generator.colors

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import java.net.HttpURLConnection
import java.net.URL
import java.nio.file.Path
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class ColorsGenerator {
    companion object {
        private const val COLOR_PREFIX = "palette"
        private const val BUNDLE_PREFIX = "backgroundButtonBundle"
        private val DROP_SUFFIXES = arrayOf("Active", "Hover")
    }

    fun build(svgUrl: String, kotlinOutDir: Path) {
        val colors = downloadColors(svgUrl)
        generateClass(colors, kotlinOutDir)
    }

    @Suppress("UNCHECKED_CAST")
    private fun downloadColors(
        url: String,
    ): List<Pair<String, String>> {
        val finalUrl = getFinalRedirectedUrl(url)

        val inputStream = URL(finalUrl).openConnection().getInputStream().buffered()
        val inputText = inputStream.reader().readText()

        val parser = Json { isLenient = true }
        val parsed = parser.decodeFromString<Map<String, String>>(inputText)
        val colors = parsed
            .filter {
                it.key.startsWith(COLOR_PREFIX) || (
                    it.key.startsWith(BUNDLE_PREFIX) &&
                        DROP_SUFFIXES.none { suffix -> it.key.endsWith(suffix) }
                    )
            }
            .toList()
            .sortedBy { it.first }

        val pattern =
            """linear-gradient\(to top right, (#[a-fA-F0-9]{6}) 0%, (#[a-fA-F0-9]{6}) 100%\)""".toRegex()
        val expanded = colors.flatMap { (key, color) ->
            if (!color.contains("gradient")) {
                return@flatMap listOf(
                    key.removePrefix(COLOR_PREFIX) to color,
                )
            }

            val matchResult = pattern.matchEntire(color) ?: return@flatMap emptyList()
            val matched = matchResult.groupValues.drop(1)
            val newKey = key.removePrefix("backgroundButton")
            return@flatMap listOf(
                "${newKey}Start" to matched.first(),
                "${newKey}End" to matched.last(),
            )
        }
        return expanded.sortedBy { it.first }
    }

    private fun getFinalRedirectedUrl(url: String): String {
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.instanceFollowRedirects = false
        connection.useCaches = false
        connection.requestMethod = "GET"
        connection.connect()

        val responseCode: Int = connection.responseCode
        connection.disconnect()

        if (responseCode in 300..399) {
            val location = connection.getHeaderField("Location") ?: url
            val orig = URL(url)
            val redirect = URL(orig.protocol, orig.host, orig.port, location, null)
            return redirect.toString().replace("@", "%40")
        } else {
            return url
        }
    }

    private fun generateClass(colors: List<Pair<String, String>>, dir: Path) {
        val colorClass = ClassName("kiwi.orbit.compose.ui.foundation.tokens", "ColorTokens")
        val colorType = ClassName("androidx.compose.ui.graphics", "Color")
        val objectBuilder = TypeSpec.objectBuilder(colorClass)
            .addModifiers(KModifier.INTERNAL)

        colors.forEach { (name, colorString) ->
            val colorValue = when (colorString.contains("rgb")) {
                true -> {
                    val segments = colorString
                        .removePrefix("rgb(")
                        .removeSuffix(")")
                        .split(",\\s*".toRegex())
                        .mapNotNull { it.toIntOrNull() }
                    assert(segments.size == 3)
                    val (red, green, blue) = segments
                    val colorValue = (0xFF shl 24) or
                        ((red and 0xFF) shl 16) or
                        ((green and 0xFF) shl 8) or
                        (blue and 0xFF)
                    colorValue
                }
                false -> {
                    "FF${colorString.drop(1)}".toLong(radix = 16)
                }
            }

            val property = PropertySpec.builder(name, colorType)
                .initializer(
                    "%T(0x%L)",
                    colorType,
                    "%08x".format(colorValue).uppercase()
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
