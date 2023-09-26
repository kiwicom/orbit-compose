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

class MaterialDesignInsteadOrbitDesignDetectorTest {
    @Test
    fun testDetector() {
        val stubFile = kotlin(
            """
                package test
                import androidx.compose.material.Card
                import androidx.compose.material.contentColorFor
                import androidx.compose.material.Text
                import androidx.compose.material.icons.Icons
                import androidx.compose.material3.AlertDialog
                import androidx.compose.material3.Card as Card3
                import androidx.compose.material3.contentColorFor as contentColorFor3
                import androidx.compose.material3.Text as Text3
                import androidx.compose.material3.TextButton
                import androidx.compose.material3.Divider
                import androidx.compose.material3.LocalTextStyle
                fun Test() {
                    Card {
                        Text("test")
                        Icons.Test
                        contentColorFor()
                    }
                    Card3 {
                        Text3("test")
                        contentColorFor3()
                        Divider()
                        LocalTextStyle.current
                        LocalTextStyle
                        LocalTextStyle.provides()
                        AlertDialog(confirmButton = { TextButton("test") })
                    } 
                    AlertDialog(confirmButton = { TextButton("test") })
                }
            """.trimIndent(),
        )
        val materialFile = kotlin(
            """
                package androidx.compose.material
                fun Card(content: () -> Unit) {}
                fun Text(content: () -> Unit) {}
                fun TextButton(content: String) {}
                fun contentColorFor(backgroundColor: Color): Color = TODO()
            """.trimIndent(),
        )
        val material3File = kotlin(
            """
                package androidx.compose.material3
                fun Card(content: () -> Unit) {}
                fun Text(content: () -> Unit) {}
                fun TextButton(content: String) {}
                fun Divider() {}
                fun AlertDialog(
                    confirmButton: () -> Unit,
                    dismissButton: (() -> Unit)? = null,
                ) {}
                fun contentColorFor(backgroundColor: Color): Color = TODO()
                @Stable
                sealed class CompositionLocal<T> constructor(defaultFactory: () -> T) {
                    @Suppress("UNCHECKED_CAST")
                    internal val defaultValueHolder = LazyValueHolder(defaultFactory)

                    @Composable
                    internal abstract fun provided(value: T): State<T>

                    /**
                     * Return the value provided by the nearest [CompositionLocalProvider] component that invokes, directly or
                     * indirectly, the composable function that uses this property.
                     *
                     * @sample androidx.compose.runtime.samples.consumeCompositionLocal
                     */
                    @OptIn(InternalComposeApi::class)
                    inline val current: T
                        @ReadOnlyComposable
                        @Composable
                        get() = currentComposer.consume(this)
                }
                val LocalTextStyle: CompositionLocal<Int> = TODO()
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
                "Using androidx.compose.material3.Divider instead of kiwi.orbit.compose.ui.controls.Separator",
                "Using androidx.compose.material3.LocalTextStyle instead of kiwi.orbit.compose.ui.foundation.LocalTextStyle",
                "Using androidx.compose.material3.LocalTextStyle instead of kiwi.orbit.compose.ui.foundation.LocalTextStyle",
                "Using androidx.compose.material3.LocalTextStyle instead of kiwi.orbit.compose.ui.foundation.LocalTextStyle",
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
