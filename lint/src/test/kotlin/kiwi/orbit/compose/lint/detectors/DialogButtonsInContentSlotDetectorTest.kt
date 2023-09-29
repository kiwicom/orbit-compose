package kiwi.orbit.compose.lint.detectors

import com.android.tools.lint.checks.infrastructure.TestFiles
import com.android.tools.lint.checks.infrastructure.TestLintTask
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.SourceCodeScanner
import org.junit.Test


class DialogButtonsInContentSlotDetectorTest: Detector(), SourceCodeScanner {

    companion object {
        private val ALERT_INFO_STUB = TestFiles.kotlin(
            """
            package kiwi.orbit.compose.ui.controls

            fun AlertInfo(
                actions: () -> Unit = {},
                content: () -> Unit,
            ) 
            """.trimIndent(),
        )

        private val ORBIT_BUTTON_PRIMARY_STUB = TestFiles.kotlin(
            """
            package kiwi.orbit.compose.ui.controls
            
            fun ButtonPrimary(
                onClick: () -> Unit,
            ) 
            
            fun Column(
                content: () -> Unit,
            ) 
        """.trimIndent(),
        )

        private val ORBIT_BUTTON_CRITICAL_STUB = TestFiles.kotlin(
            """
            package kiwi.orbit.compose.ui.controls
            
            fun ButtonCritical(
                onClick: () -> Unit,
            ) 
        """.trimIndent(),
        )
    }


    @Test
    fun testDetector() {
        TestLintTask.lint()
            .files(
                TestFiles.kotlin(
                    """
                    package foo

                    import kiwi.orbit.compose.ui.controls.AlertInfo
                    import kiwi.orbit.compose.ui.controls.ButtonPrimary
                    import kiwi.orbit.compose.ui.controls.ButtonCritical
                    import kiwi.orbit.compose.ui.controls.Column

                    fun test() {
                        AlertInfo { ButtonPrimary( { } ) }
                        AlertInfo { ButtonCritical( { } ) }
                        AlertInfo { Column { ButtonPrimary( { } ) } }
                        AlertInfo { 
                            Column { 
                            }
                            ButtonPrimary( { } ) 
                        }
                    }
                    """,
                ),
                ALERT_INFO_STUB,
                ORBIT_BUTTON_PRIMARY_STUB,
                ORBIT_BUTTON_CRITICAL_STUB,
            )
            .issues(DialogButtonsInContentSlotDetector.ISSUE)
            .allowMissingSdk()
            .run()
            .expectErrorCount(4)
            .expect(
                """
                    src/foo/test.kt:10: Error: ButtonPrimary in Dialog's content slot. Use actions slot for CTAs instead. [DialogButtonsInContentSlotDetector]
                                            AlertInfo { ButtonPrimary( { } ) }
                                                        ~~~~~~~~~~~~~~~~~~~~
                    src/foo/test.kt:11: Error: ButtonCritical in Dialog's content slot. Use actions slot for CTAs instead. [DialogButtonsInContentSlotDetector]
                                            AlertInfo { ButtonCritical( { } ) }
                                                        ~~~~~~~~~~~~~~~~~~~~~
                    src/foo/test.kt:12: Error: ButtonPrimary in Dialog's content slot. Use actions slot for CTAs instead. [DialogButtonsInContentSlotDetector]
                                            AlertInfo { Column { ButtonPrimary( { } ) } }
                                                                 ~~~~~~~~~~~~~~~~~~~~
                    src/foo/test.kt:16: Error: ButtonPrimary in Dialog's content slot. Use actions slot for CTAs instead. [DialogButtonsInContentSlotDetector]
                                                ButtonPrimary( { } ) 
                                                ~~~~~~~~~~~~~~~~~~~~
                    4 errors, 0 warnings
                """.trimIndent(),
            )
    }
}
