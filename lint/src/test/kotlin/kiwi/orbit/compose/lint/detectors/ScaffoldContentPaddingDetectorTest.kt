package kiwi.orbit.compose.lint.detectors

import com.android.tools.lint.checks.infrastructure.TestFiles.kotlin
import com.android.tools.lint.checks.infrastructure.TestLintTask
import org.junit.Test

internal class ScaffoldContentPaddingDetectorTest {

    companion object {
        private val SCAFFOLD_STUB = kotlin(
            """
            package kiwi.orbit.compose.ui.controls

            import androidx.compose.foundation.layout.PaddingValues

            fun Scaffold(
                topBar: () -> Unit = {},
                action: () -> Unit = {},
                content: (contentPadding: PaddingValues) -> Unit,
            )
            """.trimIndent(),
        )
        private val PADDING_VALUES_STUB = kotlin(
            """
                package androidx.compose.foundation.layout
                
                interface PaddingValues
            """.trimIndent(),
        )
    }

    @Test
    fun testDetectorReportingIssues() {
        TestLintTask.lint()
            .files(
                kotlin(
                    """
                    package foo

                    import kiwi.orbit.compose.ui.controls.Scaffold

                    fun test() {
                        Scaffold(topBar = {}, bottomBar = {}) { /**/ }
                        Scaffold(topBar = {}, bottomBar = {}, content = { /**/ })
                        Scaffold(topBar = {}, bottomBar = {}) { _ -> /**/ }
                        Scaffold(topBar = {}, bottomBar = {}) { innerPadding -> /*innerPadding*/ }
                    }
                    """
                ),
                SCAFFOLD_STUB,
                PADDING_VALUES_STUB
            )
            .issues(ScaffoldContentPaddingDetector.ISSUE)
            .allowMissingSdk()
            .run()
            .expect(
                """
                    src/foo/test.kt:7: Error: Content padding parameter it is not used [UnusedOrbitScaffoldPaddingParameter]
                                            Scaffold(topBar = {}, bottomBar = {}) { /**/ }
                                                                                  ~~~~~~~~
                    src/foo/test.kt:8: Error: Content padding parameter it is not used [UnusedOrbitScaffoldPaddingParameter]
                                            Scaffold(topBar = {}, bottomBar = {}, content = { /**/ })
                                                                                            ~~~~~~~~
                    src/foo/test.kt:9: Error: Content padding parameter _ is not used [UnusedOrbitScaffoldPaddingParameter]
                                            Scaffold(topBar = {}, bottomBar = {}) { _ -> /**/ }
                                                                                    ~
                    src/foo/test.kt:10: Error: Content padding parameter innerPadding is not used [UnusedOrbitScaffoldPaddingParameter]
                                            Scaffold(topBar = {}, bottomBar = {}) { innerPadding -> /*innerPadding*/ }
                                                                                    ~~~~~~~~~~~~
                    4 errors, 0 warnings
                """.trimIndent()
            )
    }

    @Test
    fun testDetectorShadowedNames() {
        TestLintTask.lint()
            .files(
                kotlin(
                    """
                package foo

                import kiwi.orbit.compose.ui.controls.Scaffold

                val foo = false

                fun test() {
                    Scaffold {
                        foo.let {
                            // These `it`s refer to the `let`, not the `Scaffold`, so we
                            // should still report an error
                            it
                        }
                    }
                    Scaffold(topBar = {}, bottomBar = {}) { innerPadding ->
                        foo.let { innerPadding ->
                            // These `innerPadding`s refer to the `let`, not the `Scaffold`, so we
                            // should still report an error
                            innerPadding
                        }
                    }
                }
            """
                ),
                SCAFFOLD_STUB,
                PADDING_VALUES_STUB
            )
            .issues(ScaffoldContentPaddingDetector.ISSUE)
            .allowMissingSdk()
            .run()
            .expect(
                """
                    src/foo/test.kt:9: Error: Content padding parameter it is not used [UnusedOrbitScaffoldPaddingParameter]
                                        Scaffold {
                                                 ^
                    src/foo/test.kt:16: Error: Content padding parameter innerPadding is not used [UnusedOrbitScaffoldPaddingParameter]
                                        Scaffold(topBar = {}, bottomBar = {}) { innerPadding ->
                                                                                ~~~~~~~~~~~~
                    2 errors, 0 warnings
                """.trimIndent()
            )
    }

    @Test
    fun testDetectorNoErrors() {
        TestLintTask.lint()
            .files(
                kotlin(
                    """
                    package foo

                    import kiwi.orbit.compose.ui.controls.Scaffold

                    fun test() {
                        Scaffold {
                            it
                        }
                        Scaffold(Modifier, topBar = {}, bottomBar = {}) { contentPadding ->
                            contentPadding
                        }
                    }
                """
                ),
                SCAFFOLD_STUB,
                PADDING_VALUES_STUB
            )
            .issues(ScaffoldContentPaddingDetector.ISSUE)
            .allowMissingSdk()
            .run()
            .expectClean()
    }

}
