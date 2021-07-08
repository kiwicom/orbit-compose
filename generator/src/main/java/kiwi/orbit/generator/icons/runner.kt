package kiwi.orbit.generator.icons

import java.nio.file.Paths

fun main() {
    val processor = IconsGenerator()

    val root = Paths.get("").toAbsolutePath().toFile().absolutePath
    val kotlinOutDir = Paths.get("$root/icons/src/main/kotlin/")
    val resourceOutDir = Paths.get("$root/icons/src/main/res/drawable/")
    val urlIcons = "https://images.kiwi.com/orbit-icons/orbit-svgs.zip"

    processor.build(urlIcons, kotlinOutDir, resourceOutDir)
}
