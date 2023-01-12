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
                val (originalNames, preferredName) = METHOD_NAMES[name] ?: return
                val fqn = node.resolve()?.containingClass?.qualifiedName ?: return
                val packageName = fqn.substring(0, fqn.lastIndexOf("."))
                if ("$packageName.$name" !in originalNames) return
                reportIssue(context, node, "$packageName.$name", preferredName)
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
            "Card" to (setOf(
                "androidx.compose.material.Card",
                "androidx.compose.material3.Card",
            ) to "kiwi.orbit.compose.ui.controls.SurfaceCard"),
            "IconButton" to (setOf(
                "androidx.compose.material.IconButton",
                "androidx.compose.material3.IconButton",
            ) to "kiwi.orbit.compose.ui.controls.IconButton"),
            "Icon" to (setOf(
                "androidx.compose.material.Icon",
                "androidx.compose.material3.Icon",
            ) to "kiwi.orbit.compose.ui.controls.Icon"),
            "LinearProgressIndicator" to (setOf(
                "androidx.compose.material.LinearProgressIndicator",
                "androidx.compose.material3.LinearProgressIndicator",
            ) to "kiwi.orbit.compose.ui.controls.LinearProgressIndicator"),
            "Scaffold" to (setOf(
                "androidx.compose.material.Scaffold",
                "androidx.compose.material3.Scaffold",
            ) to "kiwi.orbit.compose.ui.controls.Scaffold"),
            "Surface" to (setOf(
                "androidx.compose.material.Surface",
                "androidx.compose.material3.Surface",
            ) to "kiwi.orbit.compose.ui.controls.Surface"),
            "Text" to (setOf(
                "androidx.compose.material.Text",
                "androidx.compose.material3.Text",
            ) to "kiwi.orbit.compose.ui.controls.Text"),
            "contentColorFor" to (setOf(
                "androidx.compose.material.contentColorFor",
                "androidx.compose.material3.contentColorFor",
            ) to "kiwi.orbit.compose.ui.foundation.contentColorFor"),
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
