package kiwi.orbit.compose.lint.detectors

import com.android.tools.lint.LintCliClient
import com.android.tools.lint.LintStats
import com.android.tools.lint.Reporter
import com.android.tools.lint.checks.infrastructure.TestFiles.kotlin
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import com.android.tools.lint.checks.infrastructure.TestMode
import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.Incident
import com.google.common.io.Files
import java.io.BufferedWriter
import java.io.File
import org.junit.Assert
import org.junit.Test

@Suppress("UnstableApiUsage")
class MaterialDesignInsteadOrbitDesignDetectorTest {
    @Test
    fun testDetector() {
        val stubFile = kotlin(
            """
                import androidx.compose.material.Card
                import androidx.compose.material.contentColorFor
                import androidx.compose.material.Text
                import androidx.compose.material.icons.Icons
                import androidx.compose.material3.Card as Card3
                import androidx.compose.material3.contentColorFor as contentColorFor3
                import androidx.compose.material3.Text as Text3
                fun Test() {
                    Card {
                        Text("test")
                        Icons.Test
                        contentColorFor()
                    }
                    Card3 {
                        Text3("test")
                        contentColorFor3()
                    }
                }
            """.trimIndent(),
        )
        val materialFile = kotlin(
            """
                package androidx.compose.material
                fun Card(content: () -> Unit) {}
                fun Text(content: () -> Unit) {}
                fun contentColorFor(backgroundColor: Color): Color = TODO()
            """.trimIndent(),
        )
        val material3File = kotlin(
            """
                package androidx.compose.material3
                fun Card(content: () -> Unit) {}
                fun Text(content: () -> Unit) {}
                fun contentColorFor(backgroundColor: Color): Color = TODO()
            """.trimIndent(),
        )
        val materialIconsFile = kotlin(
            """
                package androidx.compose.material.icons
                object Icons { val Test: Int = 0 }
            """.trimIndent(),
        )

        val result = lint()
            .detector(MaterialDesignInsteadOrbitDesignDetector())
            .issues(MaterialDesignInsteadOrbitDesignDetector.ISSUE)
            .testModes(TestMode.FULLY_QUALIFIED)
            .allowMissingSdk()
            .files(stubFile, materialFile, material3File, materialIconsFile)
            .run()

        val messages = mutableListOf<String>()
        result.checkReport(
            { lintClient, file -> MessageReporter(lintClient, file, messages) },
            ".custom",
        )

        Assert.assertEquals(
            listOf(
                "Using androidx.compose.material.Card instead of kiwi.orbit.compose.ui.controls.SurfaceCard",
                "Using androidx.compose.material.Text instead of kiwi.orbit.compose.ui.controls.Text",
                "Using androidx.compose.material.icons.Icons instead of kiwi.orbit.compose.icons.Icons",
                "Using androidx.compose.material.contentColorFor instead of kiwi.orbit.compose.ui.foundation.contentColorFor",
                "Using androidx.compose.material3.Card instead of kiwi.orbit.compose.ui.controls.SurfaceCard",
                "Using androidx.compose.material3.Text instead of kiwi.orbit.compose.ui.controls.Text",
                "Using androidx.compose.material3.contentColorFor instead of kiwi.orbit.compose.ui.foundation.contentColorFor",
            ),
            messages,
        )
    }

    class MessageReporter(
        client: LintCliClient,
        output: File,
        private val messages: MutableList<String>,
    ) : Reporter(client, output) {
        init {
            BufferedWriter(Files.newWriter(output, Charsets.UTF_8)).use {
                it.newLine()
            }
        }

        override fun write(stats: LintStats, incidents: List<Incident>, registry: IssueRegistry) {
            messages.addAll(incidents.map { it.message })
        }
    }
}
