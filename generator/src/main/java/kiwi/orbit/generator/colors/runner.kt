package kiwi.orbit.generator.colors

import java.nio.file.Paths

fun main() {
    val processor = ColorsGenerator()

    val root = Paths.get("").toAbsolutePath().toFile().absolutePath
    val kotlinOutDir = Paths.get("$root/lib/src/main/java/")
    val urlColors = "https://unpkg.com/%40kiwicom/orbit-design-tokens%40latest/output/theo-spec.json"

    processor.build(urlColors, kotlinOutDir)
}
