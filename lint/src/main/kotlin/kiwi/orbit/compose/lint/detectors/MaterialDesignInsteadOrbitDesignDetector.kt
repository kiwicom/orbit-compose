package kiwi.orbit.compose.lint.detectors

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.intellij.psi.impl.source.PsiClassReferenceType
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UQualifiedReferenceExpression

@Suppress("UnstableApiUsage")
class MaterialDesignInsteadOrbitDesignDetector : Detector(), Detector.UastScanner {
    override fun getApplicableUastTypes(): List<Class<out UElement>> {
        return listOf(
            UCallExpression::class.java,
            UQualifiedReferenceExpression::class.java,
        )
    }

    override fun createUastHandler(context: JavaContext): UElementHandler {
        return object : UElementHandler() {
            override fun visitCallExpression(node: UCallExpression) {
                val name = node.methodName ?: return
                val (originalName, preferredName) = METHOD_NAMES[name] ?: return
                val fqn = node.resolve()?.containingClass?.qualifiedName ?: return
                val packageName = fqn.substring(0, fqn.lastIndexOf("."))
                if ("$packageName.$name" != originalName) return
                reportIssue(context, node, originalName, preferredName)
            }

            override fun visitQualifiedReferenceExpression(node: UQualifiedReferenceExpression) {
                val fqn = (node.receiver.getExpressionType() as? PsiClassReferenceType)
                    ?.reference?.qualifiedName
                    ?: return
                val preferredName = RECEIVER_NAMES[fqn] ?: return
                reportIssue(context, node, fqn, preferredName)
            }
        }
    }

    companion object {
        internal val ISSUE: Issue = Issue.create(
            id = "MaterialDesignInsteadOrbitDesign",
            briefDescription = "Usage of Material UI instead of Orbit Compose UI",
            explanation = "This check highlights calls in code that use Material Design composables instead of equivalents from the Orbit design system.",
            category = Category.CUSTOM_LINT_CHECKS,
            priority = 7,
            severity = Severity.ERROR,
            implementation = Implementation(
                MaterialDesignInsteadOrbitDesignDetector::class.java,
                Scope.JAVA_FILE_SCOPE,
            ),
        )

        private val METHOD_NAMES = mapOf(
            "Card" to ("androidx.compose.material.Card" to "kiwi.orbit.compose.ui.controls.Card"),
            "IconButton" to ("androidx.compose.material.IconButton" to "kiwi.orbit.compose.ui.controls.IconButton"),
            "Icon" to ("androidx.compose.material.Icon" to "kiwi.orbit.compose.ui.controls.Icon"),
            "LinearProgressIndicator" to ("androidx.compose.material.LinearProgressIndicator" to "kiwi.orbit.compose.ui.controls.LinearProgressIndicator"),
            "Scaffold" to ("androidx.compose.material.Scaffold" to "kiwi.orbit.compose.ui.controls.Scaffold"),
            "Surface" to ("androidx.compose.material.Surface" to "kiwi.orbit.compose.ui.controls.Surface"),
            "Text" to ("androidx.compose.material.Text" to "kiwi.orbit.compose.ui.controls.Text"),
        )
        private val RECEIVER_NAMES = mapOf(
            "androidx.compose.material.icons.Icons" to "kiwi.orbit.compose.icons.Icons",
        )

        internal fun reportIssue(
            context: JavaContext,
            node: UElement,
            name: String,
            preferredName: String,
        ) {
            context.report(
                ISSUE, node, context.getLocation(node),
                "Using $name instead of $preferredName",
            )
        }
    }
}
