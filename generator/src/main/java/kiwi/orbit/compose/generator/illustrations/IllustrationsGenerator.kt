package kiwi.orbit.compose.generator.illustrations

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import java.awt.Image
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.io.File
import java.net.URL
import java.nio.file.Path
import javax.imageio.ImageIO
import kotlin.io.path.createDirectories
import kotlin.io.path.div
import kotlin.math.roundToInt

class IllustrationsGenerator {
    private val humps = "(?<=.)(?=\\p{Upper})".toRegex()

    fun build(
        listUrl: String,
        prefixUrl: String,
        kotlinOutDir: Path,
        resourceOutDir: Path,
    ) {
        removeOldIllustrations(kotlinOutDir, resourceOutDir)
        val icons = downloadAndResizeIllustrations(listUrl, prefixUrl, resourceOutDir)
        generateClass(icons, kotlinOutDir)
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun buildImplOnly(
        kotlinOutDir: Path,
        resourceOutDir: Path,
    ) {
        val icons = buildList {
            File(resourceOutDir.toFile(), "drawable-xxxhdpi")
                .listFiles()
                .orEmpty()
                .filter { it.name.startsWith("il_") }
                .forEach {
                    val resource = it.nameWithoutExtension
                    val name = resource.removePrefix("il_orbit_").toTitleCase()
                    add(Pair(name, resource))
                }
        }
        generateClass(icons, kotlinOutDir)
    }

    private fun removeOldIllustrations(kotlinDir: Path, resourceDir: Path) {
        kotlinDir.toFile().listFiles()
            ?.filter { it.name != "Illustrations.kt" }
            ?.forEach { it.delete() }

        listOf("", "-mdpi", "-hdpi", "-xhdpi", "-xxhdpi", "-xxxhdpi").forEach { sizeName ->
            File(resourceDir.toFile(), "drawable$sizeName").listFiles()
                ?.filter { it.name.startsWith("il_") }
                ?.forEach { it.delete() }
        }
    }

    private fun downloadAndResizeIllustrations(
        listUrl: String,
        prefixUrl: String,
        resourceOutDir: Path
    ): List<Pair<String, String>> {
        val regexp = ".+\"([a-zA-Z0-9]+)\".+".toRegex()
        val names = URL(listUrl)
            .openConnection()
            .getInputStream()
            .buffered()
            .reader()
            .readLines()
            .mapNotNull { line ->
                regexp.matchEntire(line)?.groupValues?.get(1)
            }

        val sizes: List<Pair<String, Int>> = listOf(
            "mdpi" to 340,
            "hdpi" to (340 * 1.5).roundToInt(),
            "xhdpi" to 340 * 2,
            "xxhdpi" to 340 * 3,
            "xxxhdpi" to 340 * 4,
        )

        (resourceOutDir / "drawable").createDirectories()
        sizes.forEach { (size) ->
            (resourceOutDir / "drawable-$size").createDirectories()
        }

        val illustrations = mutableListOf<Pair<String, String>>()
        for (name in names) {
            val nameLowered = name.toSnakeCase()
            val url = "$prefixUrl/$name.png"
            val inFile = File(resourceOutDir.toFile(), "drawable/il_orbit_$nameLowered.png")
            inFile.outputStream().use {
                URL(url).openConnection().getInputStream().copyTo(it)
            }

            val inImage = ImageIO.read(inFile)
            if (inImage.width < 1360) {
                println("Skipping $name (not big enough, width ${inImage.width}px)")
                inFile.delete()
                continue
            }

            sizes.map { (sizeName, expectedWidth) ->
                val outPath = File(resourceOutDir.toFile(), "drawable-$sizeName/il_orbit_$nameLowered.png")
                val image = ImageIO.read(inFile)
                val resized = resize(image, width = expectedWidth, height = 0)
                ImageIO.write(resized, "PNG", outPath)
                Runtime.getRuntime().exec("pngquant --skip-if-larger -f --strip -o $outPath $outPath")
            }.forEach { process ->
                process.waitFor()
            }

            inFile.delete()
            illustrations.add(Pair(name, "il_orbit_$nameLowered"))
            println("Rendered: $name")
        }

        return illustrations
    }

    private fun generateClass(illustrations: List<Pair<String, String>>, dir: Path) {
        val illustrationClassType = ClassName("kiwi.orbit.compose.illustrations", "Illustrations")
        val painterType = ClassName("androidx.compose.ui.graphics.painter", "Painter")
        val composable = ClassName("androidx.compose.runtime", "Composable")
        val composableAnnotation = AnnotationSpec.builder(composable).build()

        val illustrationClass = TypeSpec.objectBuilder(illustrationClassType)
        illustrationClass.addAnnotation(
            AnnotationSpec.builder(Suppress::class)
                .addMember("%S", "unused")
                .build()
        )

        illustrations.sortedBy { it.first }.forEach { (illustrationName, illustrationResource) ->
            val property = PropertySpec.builder(illustrationName, painterType)
                .getter(
                    FunSpec.getterBuilder()
                        .addAnnotation(composableAnnotation)
                        .addStatement(
                            "return %M(%L)",
                            MemberName("androidx.compose.ui.res", "painterResource"),
                            "R.drawable.$illustrationResource"
                        )
                        .build()
                )
                .build()
            illustrationClass.addProperty(property)
        }

        val file = FileSpec.builder("kiwi.orbit.compose.illustrations", "Illustrations")
            .addType(illustrationClass.build())
            .indent("    ")
            .build()

        file.writeTo(dir)
    }

    @Suppress("NAME_SHADOWING", "SameParameterValue")
    private fun resize(sourceImage: BufferedImage, width: Int, height: Int): BufferedImage {
        var width = width
        var height = height
        val ratio = sourceImage.width.toDouble() / sourceImage.height
        if (width < 1) {
            width = (height * ratio + 0.4).toInt()
        } else if (height < 1) {
            height = (width / ratio + 0.4).toInt()
        }
        val scaled = sourceImage.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING)
        val bufferedScaled = BufferedImage(scaled.getWidth(null), scaled.getHeight(null), BufferedImage.TYPE_INT_ARGB)
        val g2d = bufferedScaled.createGraphics()
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC)
        g2d.drawImage(scaled, 0, 0, width, height, null)
        return bufferedScaled
    }

    private fun String.toSnakeCase(): String = replace(humps, "_").lowercase()
    private fun String.toTitleCase(): String = split("_")
        .joinToString("") { it.replaceFirstChar { c -> c.uppercase() } }
}
