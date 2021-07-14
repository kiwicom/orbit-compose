package kiwi.orbit.compose.generator.colors

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
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
        return parsed
            .filter { it.key.startsWith(COLOR_PREFIX) }
            .toList()
            .sortedBy { it.first }
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
        val colorClass = ClassName("kiwi.orbit.compose.ui.foundation", "ColorTokens")
        val colorType = ClassName("androidx.compose.ui.graphics", "Color")
        val objectBuilder = TypeSpec.objectBuilder(colorClass)

        colors.forEach { (colorName, colorString) ->
            val name = colorName
                .removePrefix(COLOR_PREFIX)
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

            val property = PropertySpec.builder(name, colorType)
                .initializer(
                    "%T(0x%L)",
                    colorType,
                    "%08x".format(colorValue).uppercase()
                )
                .build()
            objectBuilder.addProperty(property)
        }

        val file = FileSpec.builder("kiwi.orbit.compose.ui.foundation", "ColorTokens")
            .addType(objectBuilder.build())
            .addAliasedImport(colorType, "UIColor")
            .indent("    ")
            .build()

        file.writeTo(dir)
    }
}
