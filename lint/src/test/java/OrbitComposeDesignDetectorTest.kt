import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import kiwi.orbit.compose.detectors.OrbitComposeDesignDetector
import org.junit.Test

class OrbitComposeDesignDetectorTest : LintDetectorTest() {
    override fun getDetector(): Detector {
        return OrbitComposeDesignDetector()
    }

    override fun getIssues(): MutableList<Issue> {
        return mutableListOf(OrbitComposeDesignDetector.ISSUE)
    }

    @Test
    fun shouldDetectUsageOfAndroidLog() {
        val stubFile = kotlin(
            """
                package kiwi.orbit.compose.catalog
                
                import android.os.Bundle
                import androidx.activity.ComponentActivity
                import androidx.activity.compose.setContent
                import androidx.compose.material.MaterialTheme
                import androidx.core.view.WindowCompat
                
                class MainActivity : ComponentActivity() {
                    override fun onCreate(savedInstanceState: Bundle?) {
                        super.onCreate(savedInstanceState)
                        WindowCompat.setDecorFitsSystemWindows(window, false)
                        setContent {
                            MaterialTheme {   }
                        }
                    }
                }

        """,
        ).indented()

        val lintResult = lint()
            .files(stubFile)
            .run()

        lintResult.cleanup()
//        lintResult
//            .expectErrorCount(1)
//            .expect(
//                """
//             src/com/fabiocarballo/lint/Dog.kt:8: Error: android.util.Log usage is forbidden. [AndroidLogDetector]
//                     Log.d(TAG, "woof! woof!")
//                     ~~~~~~~~~~~~~~~~~~~~~~~~~
//             1 errors, 0 warnings
//         """.trimIndent(),
//            )
    }
}