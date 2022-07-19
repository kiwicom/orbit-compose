package kiwi.orbit.compose

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API
import kiwi.orbit.compose.detectors.OrbitComposeDesignDetector

@Suppress("UnstableApiUsage")
class OrbitCompose : IssueRegistry() {
    override val issues = listOf(OrbitComposeDesignDetector.ISSUE)

    override val api: Int
        get() = CURRENT_API

    override val minApi: Int
        get() = 8

    override val vendor: Vendor = Vendor(
        vendorName = "kiwi.com",
        feedbackUrl = "https://github.com/kiwicom/orbit-compose/issues",
        contact = "https://github.com/kiwicom/orbit-compose/issues",
    )
}
