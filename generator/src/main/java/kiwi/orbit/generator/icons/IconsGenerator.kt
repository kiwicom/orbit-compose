package kiwi.orbit.generator.icons

import com.android.ide.common.vectordrawable.Svg2Vector
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import java.io.File
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import java.util.zip.ZipInputStream

class IconsGenerator {
    fun build(svgUrl: String, kotlinOutDir: Path, resourceOutDir: Path) {
        removeOldIcons(kotlinOutDir, resourceOutDir)
        val icons = downloadAndUnpackIcons(svgUrl, resourceOutDir)
        generateClass(icons, kotlinOutDir)
    }

    private fun removeOldIcons(kotlinDir: Path, resourceDir: Path) {
        kotlinDir.toFile().listFiles()
            ?.filter { it.name != "Icons.kt" }
            ?.forEach { it.delete() }

        resourceDir.toFile().listFiles()
            ?.filter { it.name.startsWith("ic_") }
            ?.forEach { it.delete() }
    }

    private fun downloadAndUnpackIcons(
        url: String,
        resourceOutDir: Path
    ): List<Pair<String, String>> {
        val icons = mutableListOf<Pair<String, String>>()
        val inputStream = URL(url).openConnection().getInputStream().buffered()
        ZipInputStream(inputStream).use { zis ->
            while (true) {
                val entry = zis.nextEntry ?: break
                if (entry.isDirectory) {
                    zis.closeEntry()
                    continue
                }
                if (entry.name.contains("mobile") || entry.name == "orbit-icons.svg") {
                    zis.closeEntry()
                    continue
                }

                val filenameSvg = "ic_" + entry.name
                    .removePrefix("svg/")
                    .replace("-", "_")
                    .toLowerCase(Locale.ROOT)
                val filenameXml = filenameSvg.replace("svg", "xml")

                val outFileSvg = File(resourceOutDir.toFile(), filenameSvg)
                val outFileXml = File(resourceOutDir.toFile(), filenameXml)
                outFileSvg.outputStream().use { outputStream ->
                    zis.copyTo(outputStream)
                }
                zis.closeEntry()

                outFileXml.outputStream().buffered().use { outputStream ->
                    Svg2Vector.parseSvgToXml(outFileSvg, outputStream)
                }

                outFileSvg.delete()

                val lines = outFileXml.readLines()
                val contents = lines.joinToString("\n") + "\n"
                Files.write(outFileXml.toPath(), contents.toByteArray())

                val resourceName = filenameXml.removeSuffix(".xml")
                val iconName = resourceName
                    .removePrefix("ic_")
                    .split("_")
                    .joinToString("") { it.capitalize(Locale.getDefault()) }
                icons.add(Pair(iconName, resourceName))
            }
        }
        return icons
    }

    private fun generateClass(icons: List<Pair<String, String>>, dir: Path) {
        val iconClass = ClassName("kiwi.orbit.icons", "Icons")
        val painterType = ClassName("androidx.compose.ui.graphics.painter", "Painter")
        val painterTypeNullable = painterType.copy(nullable = true)

        val composable = ClassName("androidx.compose.runtime", "Composable")
        val composableAnnotation = AnnotationSpec.builder(composable)
            .useSiteTarget(AnnotationSpec.UseSiteTarget.GET)
            .build()

        icons.forEach { (iconName, iconResource) ->
            val objectBuilder = TypeSpec.objectBuilder(iconName)

            val backingProperty = PropertySpec.builder("icon", painterTypeNullable)
                .mutable()
                .addModifiers(KModifier.PRIVATE)
                .initializer("null")
                .build()
            objectBuilder.addProperty(backingProperty)

            val property = PropertySpec.builder(iconName, painterType)
                .receiver(iconClass)
                .addAnnotation(composableAnnotation)
                .getter(
                    FunSpec.getterBuilder()
                        .addStatement(
                            "if (%N != null) return %N!!",
                            "icon",
                            "icon"
                        )
                        .addStatement(
                            "%N = %M(%L)",
                            backingProperty.name,
                            MemberName("androidx.compose.ui.res", "painterResource"),
                            "R.drawable.$iconResource"
                        )
                        .addStatement(
                            "return %N!!",
                            backingProperty.name
                        )
                        .build()
                )
                .build()

            val file = FileSpec.builder("kiwi.orbit.icons", iconName)
                .addProperty(property)
                .addProperty(backingProperty)
                .indent("\t")
                .addImport("kiwi.orbit", "R")
                .build()

            file.writeTo(dir)
        }
    }
}
