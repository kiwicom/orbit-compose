package kiwi.orbit.compose.lint.detectors

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.intellij.psi.PsiMember
import com.intellij.psi.impl.source.PsiClassReferenceType
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.psiUtil.parents
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UQualifiedReferenceExpression
import org.jetbrains.uast.USimpleNameReferenceExpression
import org.jetbrains.uast.toUElement
import org.jetbrains.uast.tryResolveNamed

class MaterialDesignInsteadOrbitDesignDetector : Detector(), Detector.UastScanner {
    override fun getApplicableUastTypes(): List<Class<out UElement>> {
        return listOf(
            UCallExpression::class.java,
            UQualifiedReferenceExpression::class.java,
            USimpleNameReferenceExpression::class.java,
        )
    }

    override fun createUastHandler(context: JavaContext): UElementHandler {
        return object : UElementHandler() {
            override fun visitCallExpression(node: UCallExpression) {
                val name = node.methodName ?: return
                val packageName = node.resolve()?.getPackageName()?: return
                val fqn = "$packageName.$name"
                val (preferredName) = METHOD_NAMES.entries.firstOrNull { it.value.contains(fqn) } ?: return

                // check the potential violation against our allowlist
                val allowedEntry = METHOD_ALLOWLIST_IN_PARENT.entries.find { it.key.contains(fqn) }
                if (allowedEntry != null) {
                    val parentExpression = node.sourcePsi?.parents?.find { it is KtCallExpression }
                    val resolved = parentExpression.toUElement()?.tryResolveNamed()
                    val parentName = resolved?.name
                    val parentPackage = resolved?.getPackageName() ?: ""
                    val parentFqn = "$parentPackage.$parentName"
                    if (allowedEntry.value.find { parentFqn.contains(it) } != null) {
                        return
                    }
                }

                reportIssue(context, node, "$packageName.$name", preferredName)
            }

            override fun visitQualifiedReferenceExpression(node: UQualifiedReferenceExpression) {
                val fqn = (node.receiver.getExpressionType() as? PsiClassReferenceType)
                    ?.reference?.qualifiedName
                    ?: return
                val preferredName = RECEIVER_NAMES[fqn] ?: return
                reportIssue(context, node, fqn, preferredName)
            }

            override fun visitSimpleNameReferenceExpression(node: USimpleNameReferenceExpression) {
                val className = (node.resolve() as? PsiMember)?.containingClass?.qualifiedName ?: return
                val fqn = className.dropLastWhile { it != '.' }.dropLast(1) + "." + node.identifier
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

        private val METHOD_ALLOWLIST_IN_PARENT = mapOf(
            "androidx.compose.material.TextButton" to setOf(
                "androidx.compose.material.AlertDialog",
                "androidx.compose.material3.AlertDialog",
            ),
            "androidx.compose.material3.TextButton" to setOf(
                "androidx.compose.material.AlertDialog",
                "androidx.compose.material3.AlertDialog",
            ),
        )

        private val METHOD_NAMES = mapOf(
            "kiwi.orbit.compose.ui.controls.ButtonPrimary" to setOf(
                "androidx.compose.material.Button",
                "androidx.compose.material3.ElevatedButton",
                "androidx.compose.material3.FilledTonalButton",
            ),
            "kiwi.orbit.compose.ui.controls.ButtonSecondary" to setOf(
                "androidx.compose.material.OutlinedButton",
                "androidx.compose.material3.OutlinedButton",
            ),
            "kiwi.orbit.compose.ui.controls.ButtonTextLinkPrimary" to setOf(
                "androidx.compose.material.TextButton",
                "androidx.compose.material3.TextButton",
            ),
            "kiwi.orbit.compose.ui.controls.SurfaceCard" to setOf(
                "androidx.compose.material.Card",
                "androidx.compose.material3.Card",
                "androidx.compose.material3.OutlinedCard",
            ),
            "kiwi.orbit.compose.ui.controls.Checkbox" to setOf(
                "androidx.compose.material.Checkbox",
                "androidx.compose.material3.Checkbox",
            ),
            "kiwi.orbit.compose.ui.controls.Separator" to setOf(
                "androidx.compose.material.Divider",
                "androidx.compose.material3.Divider",
            ),
            "kiwi.orbit.compose.ui.controls.IconButton" to setOf(
                "androidx.compose.material.IconButton",
                "androidx.compose.material3.IconButton",
            ),
            "kiwi.orbit.compose.ui.controls.Icon" to setOf(
                "androidx.compose.material.Icon",
                "androidx.compose.material3.Icon",
            ),
            "kiwi.orbit.compose.ui.controls.LinearProgressIndicator" to setOf(
                "androidx.compose.material.LinearProgressIndicator",
                "androidx.compose.material3.LinearProgressIndicator",
            ),
            "kiwi.orbit.compose.ui.controls.CircularProgresIndicator" to setOf(
                "androidx.compose.material.CircularProgresIndicator",
                "androidx.compose.material3.CircularProgresIndicator",
            ),
            "kiwi.orbit.compose.ui.controls.ListChoice" to setOf(
                "androidx.compose.material.ListItem",
                "androidx.compose.material3.ListItem",
            ),
            "kiwi.orbit.compose.ui.controls.Radio" to setOf(
                "androidx.compose.material.RadioButton",
                "androidx.compose.material3.RadioButton",
            ),
            "kiwi.orbit.compose.ui.controls.RangeSlider" to setOf(
                "androidx.compose.material.RangeSlider",
                "androidx.compose.material3.RangeSlider",
            ),
            "kiwi.orbit.compose.ui.controls.Scaffold" to setOf(
                "androidx.compose.material.Scaffold",
                "androidx.compose.material3.Scaffold",
            ),
            "kiwi.orbit.compose.ui.controls.Slider" to setOf(
                "androidx.compose.material.Slider",
                "androidx.compose.material3.Slider",
            ),
            "kiwi.orbit.compose.ui.controls.Surface" to setOf(
                "androidx.compose.material.Surface",
                "androidx.compose.material3.Surface",
            ),
            "kiwi.orbit.compose.ui.controls.Switch" to setOf(
                "androidx.compose.material.Switch",
                "androidx.compose.material3.Switch",
            ),
            "kiwi.orbit.compose.ui.controls.Tab" to setOf(
                "androidx.compose.material.Tab",
                "androidx.compose.material3.Tab",
            ),
            "kiwi.orbit.compose.ui.controls.TabRow" to setOf(
                "androidx.compose.material.TabRow",
                "androidx.compose.material3.TabRow",
            ),
            "kiwi.orbit.compose.ui.controls.ScrollableTabRow" to setOf(
                "androidx.compose.material.ScrollableTabRow",
                "androidx.compose.material3.ScrollableTabRow",
            ),
            "kiwi.orbit.compose.ui.controls.Text" to setOf(
                "androidx.compose.material.Text",
                "androidx.compose.material3.Text",
            ),
            "kiwi.orbit.compose.ui.controls.TextField" to setOf(
                "androidx.compose.material.TextField",
                "androidx.compose.material.OutlinedTextField",
                "androidx.compose.material3.TextField",
                "androidx.compose.material3.OutlinedTextField",
            ),
            "kiwi.orbit.compose.ui.controls.ToastHost" to setOf(
                "androidx.compose.material.SnackbarHost",
                "androidx.compose.material3.SnackbarHost",
            ),
            "kiwi.orbit.compose.ui.controls.TopAppBar" to setOf(
                "androidx.compose.material.TopAppBar",
                "androidx.compose.material3.TopAppBar",
                "androidx.compose.material3.LargeTopAppBar",
            ),
            "kiwi.orbit.compose.ui.foundation.contentColorFor" to setOf(
                "androidx.compose.material.contentColorFor",
                "androidx.compose.material3.contentColorFor",
            ),
        )

        private val RECEIVER_NAMES = mapOf(
            "androidx.compose.material.icons.Icons" to "kiwi.orbit.compose.icons.Icons",
            "androidx.compose.material3.LocalTextStyle" to "kiwi.orbit.compose.ui.foundation.LocalTextStyle",
            "androidx.compose.material3.LocalContentColor" to "kiwi.orbit.compose.ui.foundation.LocalContentColor",
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
