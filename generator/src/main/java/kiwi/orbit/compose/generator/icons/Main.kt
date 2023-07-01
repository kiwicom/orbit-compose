package kiwi.orbit.compose.generator.icons

import java.nio.file.Paths
import kotlin.io.path.div

fun main() {
    val processor = IconsGenerator()

    val root = Paths.get(Paths.get("").toAbsolutePath().toFile().absolutePath.removeSuffix("generator"))
    val kotlinOutDir = root / "icons/src/androidMain/kotlin/"
    val resourceOutDir = root / "icons/src/androidMain/res/drawable/"
    val urlIcons = "https://unpkg.com/@kiwicom/orbit-components%40latest/orbit-svgs.zip"

    processor.build(urlIcons, kotlinOutDir, resourceOutDir)
}
