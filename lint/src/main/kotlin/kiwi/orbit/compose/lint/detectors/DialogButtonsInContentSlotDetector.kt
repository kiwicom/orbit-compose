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
import org.jetbrains.uast.UBlockExpression
import org.jetbrains.uast.UCallExpression
import org.jetbrains.uast.ULambdaExpression
import org.jetbrains.uast.skipParenthesizedExprDown
import org.jetbrains.uast.toUElement
import org.jetbrains.uast.tryResolveNamed

class DialogButtonsInContentSlotDetector : Detector(), SourceCodeScanner {

    companion object {
        internal val ISSUE: Issue = Issue.create(
            id = "DialogButtonsInContentSlotDetector",
            briefDescription = "It is a mistake to place CTAs into content slot of AlertDialogs.",
            explanation = "Buttons in dialogs should be placed into action slot, where proper sizes and styling is provided.",
            category = Category.CUSTOM_LINT_CHECKS,
            priority = 7,
            severity = Severity.ERROR,
            implementation = Implementation(
                DialogButtonsInContentSlotDetector::class.java,
                Scope.JAVA_FILE_SCOPE,
            ),
        )

        private const val ALERT_CONTENT_PARAM = "content"
        private const val PACKAGE_ORBIT_ALERT = "kiwi.orbit.compose.ui.controls"

        private const val PACKAGE_ORBIT_BUTTON = "kiwi.orbit.compose.ui.controls"
        private const val NAME_ORBIT_BUTTON = "Button"
    }

    override fun getApplicableMethodNames(): List<String> = listOf(
        "AlertInfo", "AlertSuccess", "AlertWarning", "AlertCritical",
    )

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        if (method.isInPackageName(PACKAGE_ORBIT_ALERT)) {
            val contentArgument = method.getArgument(node, ALERT_CONTENT_PARAM)
            val topBlockExpression = contentArgument?.body as? UBlockExpression ?: return
            topBlockExpression.sourcePsi?.findAllBlockExpressionsInHierarchy()?.forEach {
                it.checkBlockExpression(context)
            }
        }
    }

    private fun UBlockExpression.checkBlockExpression(context: JavaContext) {
        expressions.forEach {
            val resolvedBodyExpression = it.skipParenthesizedExprDown().tryResolveNamed() ?: return@forEach
            val packageName = resolvedBodyExpression.getPackageName() ?: return@forEach

            val expressionName = resolvedBodyExpression.name
            val isButton = expressionName?.contains(NAME_ORBIT_BUTTON) == true
            val isOrbit = packageName == PACKAGE_ORBIT_BUTTON

            if (isButton && isOrbit) {
                context.report(
                    issue = ISSUE,
                    scope = it,
                    location = context.getLocation(it),
                    message = "$expressionName in Dialog's content slot. Use actions slot for CTAs instead.",
                )
            }
        }

    }

    private fun PsiElement.findAllBlockExpressionsInHierarchy(): List<UBlockExpression> {
        val expressions = mutableListOf<UBlockExpression>()

        val uElement = this.toUElement()
        if (uElement is UBlockExpression) expressions.add(uElement)
        expressions.addAll(children.flatMap { it.findAllBlockExpressionsInHierarchy() })

        return expressions
    }

    private fun PsiMethod.getArgument(
        node: UCallExpression,
        argumentName: String,
    ): ULambdaExpression? = computeKotlinArgumentMapping(node, this)
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
}
