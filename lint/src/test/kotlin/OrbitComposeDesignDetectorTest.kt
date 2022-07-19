import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import kiwi.orbit.compose.detectors.OrbitComposeDesignDetector
import com.android.tools.lint.detector.api.Detector
import org.junit.Test

class OrbitComposeDesignDetectorTest : LintDetectorTest() {
    override fun getDetector(): Detector {
        return OrbitComposeDesignDetector()
    }

    override fun getIssues(): MutableList<com.android.tools.lint.detector.api.Issue> {
        return listOf(OrbitComposeDesignDetector.ISSUE)
    }

    @Test
    fun testBlah() {
        println(123)
    }
}