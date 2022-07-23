package kiwi.orbit.compose.lint.detectors

import com.android.tools.lint.checks.infrastructure.TestFiles.kotlin
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import com.android.tools.lint.checks.infrastructure.TestMode
import org.junit.Test

@Suppress("UnstableApiUsage")
class MaterialDesignInsteadOrbitDesignDetectorTest {
    @Test
    fun testDetector() {
        val stubFile = kotlin(
            """
                import androidx.compose.material.Card
                import androidx.compose.material.Text
                import androidx.compose.material.icons.Icons
                fun Test() {
                    Card {
                        Text("test")
                        Icons.Test
                    }
                }
            """.trimIndent(),
        )
        val materialFile = kotlin(
            """
                package androidx.compose.material
                fun Card(content: () -> Unit) {}
                fun Text(content: () -> Unit) {}
            """.trimIndent(),
        )
        val materialIconsFile = kotlin(
            """
                package androidx.compose.material.icons
                object Icons { val Test: Int = 0 }
            """.trimIndent(),
        )

        lint()
            .detector(MaterialDesignInsteadOrbitDesignDetector())
            .issues(MaterialDesignInsteadOrbitDesignDetector.ISSUE)
            .testModes(TestMode.FULLY_QUALIFIED)
            .files(stubFile, materialFile, materialIconsFile)
            .run()
            .expectErrorCount(3)
    }
}
