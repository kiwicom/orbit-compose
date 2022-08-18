package kiwi.orbit.compose.generator.icons

import com.android.ide.common.vectordrawable.Svg2Vector
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import java.io.File
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.util.zip.ZipInputStream
import kotlin.io.path.createDirectories

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
        resourceOutDir: Path,
    ): List<Pair<String, String>> {
        val icons = mutableListOf<Pair<String, String>>()
        val inputStream = URL(url).openConnection().getInputStream().buffered()
        resourceOutDir.createDirectories()
        ZipInputStream(inputStream).use { zis ->
            while (true) {
                val entry = zis.nextEntry ?: break
                if (entry.isDirectory) {
                    println("Skipping ${entry.name}")
                    zis.closeEntry()
                    continue
                }
                if (entry.name == "orbit-icons.svg") {
                    println("Skipping ${entry.name}")
                    zis.closeEntry()
                    continue
                }

                println("Rendering ${entry.name}")
                val filenameSvg = "ic_orbit_" + entry.name
                    .removePrefix("svg/")
                    .replace("-", "_")
                    .lowercase()
                val filenameXml = filenameSvg.removeSuffix(".svg").replace('.', '_') + ".xml"

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

                @Suppress("SpellCheckingInspection")
                val iconName = resourceName
                    .removePrefix("ic_orbit_")
                    .split("_")
                    .joinToString("") { it.replaceFirstChar { c -> c.uppercase() } }
                    .replace("Checkin", "CheckIn")
                icons.add(Pair(iconName, resourceName))
            }
        }
        return icons
    }

    private fun generateClass(icons: List<Pair<String, String>>, dir: Path) {
        val iconClassType = ClassName("kiwi.orbit.compose.icons", "Icons")
        val painterType = ClassName("androidx.compose.ui.graphics.painter", "Painter")
        val composable = ClassName("androidx.compose.runtime", "Composable")
        val composableAnnotation = AnnotationSpec.builder(composable).build()

        val iconClass = TypeSpec.objectBuilder(iconClassType)
        iconClass.addAnnotation(
            AnnotationSpec.builder(Suppress::class)
                .addMember("%S", "unused")
                .build(),
        )

        icons.sortedBy { it.first }.forEach { (iconName, iconResource) ->
            val property = PropertySpec.builder(iconName, painterType)
                .getter(
                    FunSpec.getterBuilder()
                        .addAnnotation(composableAnnotation)
                        .addStatement(
                            "return %M(%L)",
                            MemberName("androidx.compose.ui.res", "painterResource"),
                            "R.drawable.$iconResource",
                        )
                        .build(),
                )
                .build()
            iconClass.addProperty(property)
        }

        val file = FileSpec.builder("kiwi.orbit.compose.icons", "Icons")
            .addType(iconClass.build())
            .indent("    ")
            .build()

        file.writeTo(dir)
    }
}
