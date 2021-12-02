package kiwi.orbit.compose.generator.illustrations

import java.nio.file.Paths

@Suppress("UNUSED_VARIABLE")
fun main() {
    val processor = IllustrationsGenerator()

    // Install into PATH:
    // - pngquant - PngQuant https://pngquant.org/ (on Windows Visual Studio 2015 Redistributable needed)

    val root = Paths.get("").toAbsolutePath().toFile().absolutePath.removeSuffix("generator")
    val kotlinOutDir = Paths.get(root, "illustrations/src/main/java/")
    val resourceOutDir = Paths.get("$root/illustrations/src/main/res/")
    val urlIllustrationList =
        "https://raw.githubusercontent.com/kiwicom/orbit/master/packages/orbit-components/src/Illustration/consts.js"
    val urlIllustrationPrefix = "https://images.kiwi.com/illustrations/originals"

    // processor.build(urlIllustrationList, urlIllustrationPrefix, kotlinOutDir, resourceOutDir)
    processor.buildImplOnly(kotlinOutDir, resourceOutDir)
}
