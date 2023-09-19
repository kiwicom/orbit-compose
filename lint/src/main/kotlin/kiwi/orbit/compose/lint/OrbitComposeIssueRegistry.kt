package kiwi.orbit.compose.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API
import kiwi.orbit.compose.lint.detectors.MaterialDesignInsteadOrbitDesignDetector
import kiwi.orbit.compose.lint.detectors.MaterialDialogWithOrbitButtonsDetector
import kiwi.orbit.compose.lint.detectors.ScaffoldContentPaddingDetector

@Suppress("UnstableApiUsage")
class OrbitComposeIssueRegistry : IssueRegistry() {
    override val issues = listOf(
        MaterialDesignInsteadOrbitDesignDetector.ISSUE,
        MaterialDialogWithOrbitButtonsDetector.ISSUE,
        ScaffoldContentPaddingDetector.ISSUE,
    )

    override val api: Int
        get() = CURRENT_API

    override val minApi: Int
        get() = 8

    override val vendor: Vendor = Vendor(
        identifier = "kiwi-com-orbit-compose",
        vendorName = "Kiwi.com",
        feedbackUrl = "https://github.com/kiwicom/orbit-compose/issues",
        contact = "https://github.com/kiwicom/orbit-compose/issues",
    )
}
