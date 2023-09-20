package kiwi.orbit.compose.lint.detectors

import com.android.tools.lint.checks.infrastructure.TestFiles
import com.android.tools.lint.checks.infrastructure.TestLintTask
import org.junit.Test


class MaterialDialogWithOrbitButtonsDetectorTest {

    private val ALERT_INFO_STUB = TestFiles.kotlin(
        """
            package androidx.compose.material3

            fun AlertDialog(
                confirmButton: () -> Unit,
                dismissButton: (() -> Unit)? = null,
                icon: (() -> Unit)? = null,
                title: (() -> Unit)? = null,
                text: (() -> Unit)? = null,
            ) 
            """.trimIndent(),
    )

    private val ORBIT_BUTTON_PRIMARY_STUB = TestFiles.kotlin(
        """
            package kiwi.orbit.compose.ui.controls
            
            fun ButtonPrimary(
                onClick: () -> Unit,
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

    @Test
    fun testDetector() {
        TestLintTask.lint()
            .files(
                TestFiles.kotlin(
                    """
                    package foo

                    import androidx.compose.material3.AlertDialog
                    import kiwi.orbit.compose.ui.controls.ButtonPrimary
                    import kiwi.orbit.compose.ui.controls.ButtonCritical

                    fun test() {
                        AlertDialog(confirmButton = { ButtonPrimary( { } ) })
                        AlertDialog(confirmButton = { ButtonCritical( { } ) })
                        AlertDialog(
                            dismissButton = { ButtonPrimary( { } ) },
                        )
                    }
                    """,
                ),
                ALERT_INFO_STUB,
                ORBIT_BUTTON_PRIMARY_STUB,
                ORBIT_BUTTON_CRITICAL_STUB,
            )
            .issues(MaterialDialogWithOrbitButtonsDetector.ISSUE)
            .allowMissingSdk()
            .run()
            .expect(
                """
                    src/foo/test.kt:9: Error: ButtonPrimary from Orbit used in Material Dialog's confirmButton slot. [MaterialDialogWithOrbitButtonsDetector]
                                            AlertDialog(confirmButton = { ButtonPrimary( { } ) })
                                                                          ~~~~~~~~~~~~~~~~~~~~
                    src/foo/test.kt:10: Error: ButtonCritical from Orbit used in Material Dialog's confirmButton slot. [MaterialDialogWithOrbitButtonsDetector]
                                            AlertDialog(confirmButton = { ButtonCritical( { } ) })
                                                                          ~~~~~~~~~~~~~~~~~~~~~
                    src/foo/test.kt:12: Error: ButtonPrimary from Orbit used in Material Dialog's dismissButton slot. [MaterialDialogWithOrbitButtonsDetector]
                                                dismissButton = { ButtonPrimary( { } ) },
                                                                  ~~~~~~~~~~~~~~~~~~~~
                    3 errors, 0 warnings
                """.trimIndent(),
            )
    }
}
