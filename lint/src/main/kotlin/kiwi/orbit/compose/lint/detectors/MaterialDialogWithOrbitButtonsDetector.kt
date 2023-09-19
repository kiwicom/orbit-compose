package kiwi.orbit.compose.lint.detectors

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.SourceCodeScanner
import com.android.tools.lint.detector.api.computeKotlinArgumentMapping
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiJavaFile
import com.intellij.psi.PsiMember
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiNamedElement
import org.jetbrains.uast.UBlockExpression
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.ULambdaExpression
import org.jetbrains.uast.skipParenthesizedExprDown
import org.jetbrains.uast.tryResolveNamed

class MaterialDialogWithOrbitButtonsDetector : Detector(), SourceCodeScanner {

    companion object {
        internal val ISSUE: Issue = Issue.create(
            id = "MaterialDialogWithOrbitButtonsDetector",
            briefDescription = "Usage of Orbit buttons inside Material Dialogs.",
            explanation = "Use Material TextButton inside of the Material Dialogs to preserve the material look.",
            category = Category.CUSTOM_LINT_CHECKS,
            priority = 7,
            severity = Severity.ERROR,
            implementation = Implementation(
                MaterialDialogWithOrbitButtonsDetector::class.java,
                Scope.JAVA_FILE_SCOPE,
            ),
        )

        private const val PACKAGE_MATERIAL_ALERT = "androidx.compose.material3"
        private const val MATERIAL_ALERT_CONFIRM_BUTTON_PARAM = "confirmButton"
        private const val MATERIAL_ALERT_DISMISS_BUTTON_PARAM = "dismissButton"

        private const val PACKAGE_MATERIAL_TEXT_BUTTON = "androidx.compose.material3.TextButton"
        private const val NAME_MATERIAL_TEXT_BUTTON = "TextButton"

        private const val PACKAGE_ORBIT_BUTTON = "kiwi.orbit.compose.ui.controls"
        private const val NAME_ORBIT_BUTTON = "Button"
    }

    override fun getApplicableMethodNames(): List<String> = listOf("AlertDialog")

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        if (method.isInPackageName(PACKAGE_MATERIAL_ALERT)) {
            val confirmButtonArgument = getArgument(node, method, MATERIAL_ALERT_CONFIRM_BUTTON_PARAM)

            (confirmButtonArgument?.body as? UBlockExpression)?.let { body ->
                val resolvedBodyExpression = body.resolveFirstBodyExpression() ?: return@let
                val packageName = resolvedBodyExpression.getPackageName() ?: return@let

                val expressionName = resolvedBodyExpression.name
                val isButton = expressionName?.contains(NAME_ORBIT_BUTTON) == true
                val isOrbit = packageName == PACKAGE_ORBIT_BUTTON
                if (isButton && isOrbit) {
                    context.report(
                        issue = ISSUE,
                        scope = body,
                        location = context.getLocation(body),
                        message = "$expressionName from Orbit used in Material Dialog's confirmButton slot.",
                        quickfixData = fix()
                            .replace()
                            .text(expressionName)
                            .with(NAME_MATERIAL_TEXT_BUTTON)
                            .imports(PACKAGE_MATERIAL_TEXT_BUTTON)
                            .shortenNames()
                            .build(),
                    )
                }
            }

            val dismissButtonArgument = getArgument(node, method, MATERIAL_ALERT_DISMISS_BUTTON_PARAM)

            (dismissButtonArgument?.body as? UBlockExpression)?.let { body ->
                val resolvedBodyExpression = body.resolveFirstBodyExpression() ?: return@let
                val packageName = resolvedBodyExpression.getPackageName() ?: return@let

                val expressionName = resolvedBodyExpression.name
                val isButton = expressionName?.contains(NAME_ORBIT_BUTTON) == true
                val isOrbit = packageName == PACKAGE_ORBIT_BUTTON
                if (isButton && isOrbit) {
                    context.report(
                        issue = ISSUE,
                        scope = body,
                        location = context.getLocation(body),
                        message = "$expressionName from Orbit used in Material Dialog's dismissButton slot.",
                        quickfixData = fix()
                            .replace()
                            .text(expressionName)
                            .with(NAME_MATERIAL_TEXT_BUTTON)
                            .imports(PACKAGE_MATERIAL_TEXT_BUTTON)
                            .shortenNames()
                            .build(),
                    )
                }
            }
        }
    }

    private fun getArgument(
        node: UCallExpression,
        method: PsiMethod,
        argumentName: String,
    ): ULambdaExpression? = computeKotlinArgumentMapping(node, method)
        .orEmpty()
        .filter { (_, parameter) ->
            parameter.name == argumentName
        }
        .keys
        .filterIsInstance<ULambdaExpression>()
        .firstOrNull()

    private fun PsiMethod.isInPackageName(packageName: String): Boolean {
        val actual = (containingFile as? PsiJavaFile)?.packageName
        return packageName == actual
    }

    private fun PsiElement.getPackageName(): String? = when (this) {
        is PsiMember -> this.containingClass?.qualifiedName?.let { it.substring(0, it.lastIndexOf(".")) }
        else -> null
    }

    private fun UBlockExpression.resolveFirstBodyExpression(): PsiNamedElement? {
        return expressions.firstOrNull()?.skipParenthesizedExprDown()?.tryResolveNamed()
    }
}
