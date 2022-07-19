package kiwi.orbit.compose.detectors

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UQualifiedReferenceExpression

@Suppress("UnstableApiUsage")
class OrbitComposeDesignDetector : Detector(), Detector.UastScanner {

    override fun getApplicableUastTypes(): List<Class<out UElement>> {
        return listOf(
            UCallExpression::class.java,
            UQualifiedReferenceExpression::class.java
        )
    }

    override fun createUastHandler(context: JavaContext): UElementHandler {
        return object : UElementHandler() {
            override fun visitCallExpression(node: UCallExpression) {
                val name = node.methodName ?: return
                val preferredName = METHOD_NAMES[name] ?: return
                reportIssue(context, node, name, preferredName)
            }

            override fun visitQualifiedReferenceExpression(node: UQualifiedReferenceExpression) {
                return
            }
        }
    }

    companion object {
        @JvmField
        val ISSUE: Issue = Issue.create(
            id = "OrbitComposeDesignSystem",
            briefDescription = "Usage of Material UI instead of Orbit Compose UI",
            explanation = "This check highlights calls in code that use Compose Material " +
                    "composables instead of equivalents from the Orbit design system " +
                    "module.",
            category = Category.CUSTOM_LINT_CHECKS,
            priority = 4,
            severity = Severity.WARNING,
            implementation = Implementation(
                OrbitComposeDesignDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )

        val METHOD_NAMES = mapOf(
            "androidx.compose.material.MaterialTheme" to "OrbitThemee",
            "androidx.compose.material.Card" to "Card",
            "androidx.compose.material.Scaffold" to "NiaOutlinedButton",
            "TextButton" to "NiaTextButton",
//            "FilterChip" to "NiaFilterChip",
//            "ElevatedFilterChip" to "NiaFilterChip",
//            "DropdownMenu" to "NiaDropdownMenu",
//            "NavigationBar" to "NiaNavigationBar",
//            "NavigationBarItem" to "NiaNavigationBarItem",
//            "NavigationRail" to "NiaNavigationRail",
//            "NavigationRailItem" to "NiaNavigationRailItem",
//            "TabRow" to "NiaTabRow",
//            "Tab" to "NiaTab",
//            "IconToggleButton" to "NiaToggleButton",
//            "FilledIconToggleButton" to "NiaToggleButton",
//            "FilledTonalIconToggleButton" to "NiaToggleButton",
//            "OutlinedIconToggleButton" to "NiaToggleButton",
//            "CenterAlignedTopAppBar" to "NiaTopAppBar",
//            "SmallTopAppBar" to "NiaTopAppBar",
//            "MediumTopAppBar" to "NiaTopAppBar",
//            "LargeTopAppBar" to "NiaTopAppBar"
        )
        val RECEIVER_NAMES = mapOf(
            "Icons" to "NiaIcons"
        )

        fun reportIssue(
            context: JavaContext,
            node: UElement,
            name: String,
            preferredName: String
        ) {
            context.report(
                ISSUE, node, context.getLocation(node),
                "Using $name instead of $preferredName"
            )
        }
    }
}
