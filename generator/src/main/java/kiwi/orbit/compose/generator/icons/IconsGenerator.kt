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
    data class Icon(
        val name: String,
        val resourceName: String,
    )

    fun build(svgUrl: String, kotlinOutDir: Path, resourceOutDir: Path) {
        removeOldIcons(kotlinOutDir, resourceOutDir)
        val icons = downloadAndUnpackIcons(svgUrl, resourceOutDir)

        val isColoredRegexp = """(?!#FF000000)#[0-9A-F]{6,8}""".toRegex(RegexOption.IGNORE_CASE)
        val groupedIcons = icons.groupBy { icon ->
            val file = File(resourceOutDir.toFile(), icon.resourceName + ".xml")
            val content = file.readText()
            isColoredRegexp.containsMatchIn(content)
        }

        generateClass(
            className = "Icons",
            icons = groupedIcons[false]!!,
            dir = kotlinOutDir,
        )
        generateClass(
            className = "ColoredIcons",
            icons = groupedIcons[true]!!.map { it.copy(name = it.name.removePrefix("Colored")) },
            dir = kotlinOutDir,
            kdoc = """
                ColoredIcons are multi-colored icons and should be rendered using Image composable.
            """.trimIndent()
        )
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
    ): List<Icon> {
        val icons = mutableListOf<Icon>()
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
                icons.add(Icon(iconName, resourceName))
            }
        }
        return icons
    }

    private fun generateClass(className: String, icons: List<Icon>, dir: Path, kdoc: String? = null) {
        val iconClassType = ClassName("kiwi.orbit.compose.icons", className)
        val painterType = ClassName("androidx.compose.ui.graphics.painter", "Painter")
        val composable = ClassName("androidx.compose.runtime", "Composable")
        val composableAnnotation = AnnotationSpec.builder(composable).build()

        val iconClass = TypeSpec.objectBuilder(iconClassType)
        if (kdoc != null) {
            iconClass.addKdoc(kdoc)
        }
        iconClass.addAnnotation(
            AnnotationSpec.builder(Suppress::class)
                .addMember("%S", "unused")
                .build(),
        )

        icons.sortedBy { it.name }.forEach { icon ->
            val property = PropertySpec.builder(icon.name, painterType)
                .getter(
                    FunSpec.getterBuilder()
                        .addAnnotation(composableAnnotation)
                        .addStatement(
                            "return %M(%L)",
                            MemberName("androidx.compose.ui.res", "painterResource"),
                            "R.drawable.${icon.resourceName}",
                        )
                        .build(),
                )
                .build()
            iconClass.addProperty(property)
        }

        val file = FileSpec.builder("kiwi.orbit.compose.icons", className)
            .addType(iconClass.build())
            .indent("    ")
            .build()

        file.writeTo(dir)
    }
}
