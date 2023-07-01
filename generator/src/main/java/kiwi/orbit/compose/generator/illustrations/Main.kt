package kiwi.orbit.compose.generator.illustrations

import java.nio.file.Paths
import kotlin.io.path.div

@Suppress("UNUSED_VARIABLE")
fun main() {
    val processor = IllustrationsGenerator()

    // Install into PATH:
    // - pngquant - PngQuant https://pngquant.org/ (on Windows Visual Studio 2015 Redistributable needed)

    val root = Paths.get(Paths.get("").toAbsolutePath().toFile().absolutePath.removeSuffix("generator"))
    val kotlinOutDir = root / "illustrations/src/androidMain/kotlin/"
    val resourceOutDir = root / "illustrations/src/androidMain/res/"
    val urlIllustrationList =
        "https://raw.githubusercontent.com/kiwicom/orbit/master/packages/orbit-components/src/Illustration/consts.mts"
    val urlIllustrationPrefix = "https://images.kiwi.com/illustrations/originals"

    processor.build(urlIllustrationList, urlIllustrationPrefix, kotlinOutDir, resourceOutDir)
    // processor.buildImplOnly(kotlinOutDir, resourceOutDir)
}
