package kiwi.orbit.compose.generator.colors

import java.nio.file.Paths

fun main() {
    val processor = ColorsGenerator()

    val figmaToken = System.getenv("FIGMA_TOKEN")
    val root = Paths.get("").toAbsolutePath().toFile().absolutePath.removeSuffix("generator")
    val kotlinOutDir = Paths.get(root, "ui/src/main/java/")

    processor.build(figmaToken, kotlinOutDir)
}
