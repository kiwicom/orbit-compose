package kiwi.orbit.compose.generator.icons

import java.nio.file.Paths

fun main() {
    val processor = IconsGenerator()

    val root = Paths.get("").toAbsolutePath().toFile().absolutePath.removeSuffix("generator")
    val kotlinOutDir = Paths.get(root, "icons/src/main/java")
    val resourceOutDir = Paths.get("$root/icons/src/main/res/drawable/")
    val urlIcons = "https://unpkg.com/@kiwicom/orbit-components%40latest/orbit-svgs.zip"

    processor.build(urlIcons, kotlinOutDir, resourceOutDir)
}
