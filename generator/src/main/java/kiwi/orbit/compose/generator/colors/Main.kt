package kiwi.orbit.compose.generator.colors

import java.nio.file.Paths
import kotlin.io.path.div

fun main() {
    val processor = ColorsGenerator()

    val figmaToken = System.getenv("FIGMA_TOKEN")
    val root = Paths.get(Paths.get("").toAbsolutePath().toFile().absolutePath.removeSuffix("generator"))
    val kotlinOutDir = root / "ui/src/androidMain/kotlin/"

    processor.build(figmaToken, kotlinOutDir)
}
