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
    companion object {
        private val RtlAwareIcons = listOf(
            "chevron_double_backward",
            "chevron_double_forward",
            "chevron_backward",
            "chevron_forward",
            "flight_direct",
            "flight_multicity",
            "flight_nomad",
            "flight_return",
            "route_no_stops",
            "route_one_stop",
            "route_two_stops",
        )
        private val SkipFiles = setOf(
            "svg/chevron-double-left.svg",
            "svg/chevron-double-right.svg",
            "svg/chevron-left.svg",
            "svg/chevron-right.svg",
            "orbit-icons.svg",
        )
    }

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
            nameBase = "Icon",
            icons = groupedIcons[false]!!,
            dir = kotlinOutDir,
        )
        generateClass(
            nameBase = "ColoredIcon",
            icons = groupedIcons[true]!!.map { it.copy(name = it.name.removePrefix("Colored")) },
            dir = kotlinOutDir,
            kdoc = """
                ColoredIcons are multi-colored icons and should be rendered using Image composable.
            """.trimIndent(),
        )
    }

    private fun removeOldIcons(kotlinDir: Path, resourceDir: Path) {
        kotlinDir.toFile().listFiles()
            ?.filter { it.name != "Icons.kt" }
            ?.forEach { it.delete() }

        resourceDir.toFile().listFiles()
            ?.filter { it.name.startsWith("ic_orbit") }
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
                if (entry.name in SkipFiles) {
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
                val isRtlAware = filenameXml.removeSuffix(".xml").removePrefix("ic_orbit_") in RtlAwareIcons
                val outFileSvg = File(resourceOutDir.toFile(), filenameSvg)
                val outFileXml = File(resourceOutDir.toFile(), filenameXml)
                outFileSvg.outputStream().use { outputStream ->
                    zis.copyTo(outputStream)
                }
                zis.closeEntry()

                outFileXml.outputStream().buffered().use { outputStream ->
                    Svg2Vector.parseSvgToXml(outFileSvg.toPath(), outputStream)
                }

                outFileSvg.delete()

                val resourceName = filenameXml.removeSuffix(".xml")

                @Suppress("SpellCheckingInspection")
                val iconName = resourceName
                    .removePrefix("ic_orbit_")
                    .split("_")
                    .joinToString("") { it.replaceFirstChar { c -> c.uppercase() } }
                    .replace("Checkin", "CheckIn")

                val content = StringBuilder(outFileXml.readLines().joinToString("\n"))
                if (isRtlAware) {
                    val attributeIndex = content.indexOf("""android:viewportHeight="24"""")
                    content.insert(
                        attributeIndex + """android:viewportHeight="24"""".length,
                        "\n    android:autoMirrored=\"true\"",
                    )
                }
                content.append("\n")

                Files.write(outFileXml.toPath(), content.toString().toByteArray())
                icons.add(Icon(iconName, resourceName))
            }
        }
        return icons
    }

    private fun generateClass(nameBase: String, icons: List<Icon>, dir: Path, kdoc: String? = null) {
        val iconsClassType = ClassName("kiwi.orbit.compose.icons", "${nameBase}s")
        val iconNameClassType = ClassName("kiwi.orbit.compose.icons", "${nameBase}Name")
        val painterType = ClassName("androidx.compose.ui.graphics.painter", "Painter")
        val composable = ClassName("androidx.compose.runtime", "Composable")
        val composableAnnotation = AnnotationSpec.builder(composable).build()

        val iconsClass = TypeSpec.objectBuilder(iconsClassType)
        if (kdoc != null) {
            iconsClass.addKdoc(kdoc)
        }
        iconsClass.addAnnotation(
            AnnotationSpec.builder(Suppress::class)
                .addMember("%S", "unused")
                .build(),
        )

        val iconNameClass = TypeSpec.enumBuilder(iconNameClassType)
        val painterFun = FunSpec.builder("painter")
            .addAnnotation(composableAnnotation)
            .receiver(iconNameClassType)
            .returns(painterType)
            .beginControlFlow("return when (this)")

        icons.sortedBy { it.name }.forEach { icon ->
            iconsClass.addProperty(
                PropertySpec.builder(icon.name, painterType)
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
                    .build(),
            )
            iconNameClass.addEnumConstant(icon.name)
            painterFun.addStatement(
                "%T.%L -> %T.%L",
                iconNameClassType,
                icon.name,
                iconsClassType,
                icon.name,
            )
        }

        painterFun.endControlFlow()

        FileSpec.builder("kiwi.orbit.compose.icons", iconsClassType.simpleName)
            .addType(iconsClass.build())
            .indent("    ")
            .build()
            .writeTo(dir)

        FileSpec.builder("kiwi.orbit.compose.icons", iconNameClassType.simpleName)
            .addType(iconNameClass.build())
            .addFunction(painterFun.build())
            .indent("    ")
            .build()
            .writeTo(dir)
    }
}
