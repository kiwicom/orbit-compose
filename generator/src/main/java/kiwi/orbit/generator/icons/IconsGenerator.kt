package kiwi.orbit.generator.icons

import com.android.ide.common.vectordrawable.Svg2Vector
import com.squareup.kotlinpoet.*
import java.io.File
import java.net.URL
import java.nio.charset.StandardCharsets
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
            ?.filter { it.name == "OrbitIcons.kt" }
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
        val iconClass = ClassName("kiwi.orbit.icons", "Icon")
        val composable = ClassName("androidx.compose.runtime", "Composable")
        val vectorAssetType = ClassName("androidx.compose.ui.graphics.vector", "VectorAsset")
        val vectorAssetTypeNullable = vectorAssetType.copy(nullable = true)

        icons.forEach { (iconName, iconResource) ->
            val objectBuilder = TypeSpec.objectBuilder(iconName)

            val backingProperty = PropertySpec.builder("icon", vectorAssetTypeNullable)
                .mutable()
                .addModifiers(KModifier.PRIVATE)
                .initializer("null")
                .build()
            objectBuilder.addProperty(backingProperty)

            val property = PropertySpec.builder(iconName, vectorAssetType)
                .receiver(iconClass)
                .addAnnotation(composable)
                .getter(
                    FunSpec.getterBuilder()
                        .addStatement(
                            "if (%N != null) return %N!!",
                            "icon",
                            "icon"
                        )
                        .addStatement(
                            "%N = %M(id = %L)",
                            backingProperty.name,
                            MemberName("androidx.compose.ui.res", "vectorResource"),
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
