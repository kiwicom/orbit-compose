package kiwi.orbit.generator.illustrations

import java.nio.file.Paths

@Suppress("UNUSED_VARIABLE")
fun main() {
    val processor = IllustrationsGenerator()

    // Install into PATH:
    // - magick - ImageMagick https://imagemagick.org/script/download.php
    // - pngquant - PngQuant https://pngquant.org/

    val root = Paths.get("").toAbsolutePath().toFile().absolutePath
    val kotlinOutDir = Paths.get("$root/lib/src/main/java/")
    val resourceOutDir = Paths.get("$root/lib/src/main/res/")
    val urlIllustrationList =
        "https://raw.githubusercontent.com/kiwicom/orbit/master/packages/orbit-components/src/Illustration/consts.js"
    val urlIllustrationPrefix = "https://images.kiwi.com/illustrations/originals"

    // processor.build(urlIllustrationList, urlIllustrationPrefix, kotlinOutDir, resourceOutDir)
    processor.buildImplOnly(kotlinOutDir, resourceOutDir)
}
