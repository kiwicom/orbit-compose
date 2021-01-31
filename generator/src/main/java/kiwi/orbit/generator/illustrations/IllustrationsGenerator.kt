package kiwi.orbit.generator.illustrations

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import java.awt.image.BufferedImage
import java.io.File
import java.net.URL
import java.nio.file.Path
import java.util.*
import javax.imageio.ImageIO
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

        val illustrations = mutableListOf<Pair<String, String>>()
        for (name in names) {
            val nameLowered = name.toSnakeCase()
            val url = "$prefixUrl/$name.png"
            val inFile = File(resourceOutDir.toFile(), "drawable/il_$nameLowered.png")
            val inPath = inFile.absolutePath
            inFile.outputStream().use {
                URL(url).openConnection().getInputStream().copyTo(it)
            }

            val inImage = ImageIO.read(inFile)
            if (inImage.width != 2200) {
                println("Skipping $name")
                inFile.delete()
                continue
            }

            sizes.map { (sizeName, expectedWidth) ->
                val outPath = File(resourceOutDir.toFile(), "drawable-$sizeName/il_$nameLowered.png").absolutePath
                Runtime.getRuntime().exec("magick convert $inPath -geometry ${expectedWidth}x $outPath").waitFor()
                Runtime.getRuntime().exec("pngquant --skip-if-larger -f --strip -o $outPath $outPath")
            }.forEach { process ->
                process.waitFor()
            }

            inFile.delete()
            illustrations.add(Pair(name, "il_$nameLowered"))
            println("Rendered: $name")
        }

        return illustrations
    }

    private fun resize(
        inputImage: BufferedImage,
        scaledWidth: Int,
        scaledHeight: Int
    ): BufferedImage {
        val outputImage = BufferedImage(scaledWidth, scaledHeight, inputImage.type)

        val g2d = outputImage.createGraphics()
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null)
        g2d.dispose()

        return outputImage
    }

    private fun generateClass(illustrations: List<Pair<String, String>>, dir: Path) {
        val iconClass = ClassName("kiwi.orbit.illustrations", "Illustrations")
        val vectorAssetType = ClassName("androidx.compose.ui.graphics", "ImageBitmap")
        val vectorAssetTypeNullable = vectorAssetType.copy(nullable = true)

        val composable = ClassName("androidx.compose.runtime", "Composable")
        val composableAnnotation = AnnotationSpec.builder(composable)
            .useSiteTarget(AnnotationSpec.UseSiteTarget.GET)
            .build()

        illustrations.forEach { (illustrationName, illustrationResource) ->
            val objectBuilder = TypeSpec.objectBuilder(illustrationName)

            val backingProperty = PropertySpec.builder("illustration", vectorAssetTypeNullable)
                .mutable()
                .addModifiers(KModifier.PRIVATE)
                .initializer("null")
                .build()
            objectBuilder.addProperty(backingProperty)

            val property = PropertySpec.builder(illustrationName, vectorAssetType)
                .receiver(iconClass)
                .addAnnotation(composableAnnotation)
                .getter(
                    FunSpec.getterBuilder()
                        .addStatement(
                            "if (%N != null) return %N!!",
                            "illustration",
                            "illustration"
                        )
                        .addStatement(
                            "%N = %M(id = %L)",
                            backingProperty.name,
                            MemberName("androidx.compose.ui.res", "imageResource"),
                            "R.drawable.$illustrationResource"
                        )
                        .addStatement(
                            "return %N!!",
                            backingProperty.name
                        )
                        .build()
                )
                .build()

            val file = FileSpec.builder("kiwi.orbit.illustrations", illustrationName)
                .addProperty(property)
                .addProperty(backingProperty)
                .indent("\t")
                .addImport("kiwi.orbit", "R")
                .build()

            file.writeTo(dir)
        }
    }

    private fun String.toSnakeCase() = replace(humps, "_").toLowerCase(Locale.ROOT)
}
