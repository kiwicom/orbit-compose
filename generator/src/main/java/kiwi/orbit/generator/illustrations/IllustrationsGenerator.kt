package kiwi.orbit.generator.illustrations

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import java.io.File
import java.net.URL
import java.nio.file.Path
import java.util.Locale
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
                    val name = resource.removePrefix("il_").toTitleCase()
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

    private fun generateClass(illustrations: List<Pair<String, String>>, dir: Path) {
        val illustrationClassType = ClassName("kiwi.orbit.illustrations", "Illustrations")
        val painterType = ClassName("androidx.compose.ui.graphics.painter", "Painter")
        val composable = ClassName("androidx.compose.runtime", "Composable")
        val composableAnnotation = AnnotationSpec.builder(composable).build()

        val illustrationClass = TypeSpec.objectBuilder(illustrationClassType)

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

        val file = FileSpec.builder("kiwi.orbit.illustrations", "Illustrations")
            .addType(illustrationClass.build())
            .indent("    ")
            .addImport("kiwi.orbit", "R")
            .build()

        file.writeTo(dir)
    }

    private fun String.toSnakeCase(): String = replace(humps, "_").toLowerCase(Locale.ROOT)
    private fun String.toTitleCase(): String = split("_").joinToString("") { it.capitalize(Locale.getDefault()) }
}
