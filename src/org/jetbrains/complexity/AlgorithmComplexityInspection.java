package org.jetbrains.complexity;

import com.intellij.psi.*;
import com.siyeh.ig.BaseInspection;
import com.siyeh.ig.BaseInspectionVisitor;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public class AlgorithmComplexityInspection extends BaseInspection {

    @Nls
    @NotNull
    @Override
    public String getDisplayName() {
        return "Algorithm complexity analysis";
    }

    @NotNull
    @Override
    protected String buildErrorString(Object... infos) {
        return "Algorithm complexity problem";
    }

    @Override
    public BaseInspectionVisitor buildVisitor() {
        return new BaseInspectionVisitor() {
            @Override
            public void visitMethod(PsiMethod method) {
                for (PsiParameter parameter : method.getParameterList().getParameters()) {
                    PsiModifierList modifiers = parameter.getModifierList();
                    if (modifiers != null) {
                        PsiAnnotation annotation = modifiers.findAnnotation("org.jetbrains.complexity.Constant");
                        if (annotation == null)
                            annotation = modifiers.findAnnotation("org.jetbrains.complexity.Linear");
                        if (annotation == null)
                            annotation = modifiers.findAnnotation("org.jetbrains.complexity.LogLinear");
                        if (annotation == null)
                            annotation = modifiers.findAnnotation("org.jetbrains.complexity.Quadratic");
                        if (annotation != null) {
                            Complexity methodComplexity = Complexity.getInstance(annotation.getQualifiedName());
                            PsiCodeBlock body = method.getBody();
                            if (body != null) {
                                int bodyComplexity = getNestedLoopsNumber(body.getStatements(), parameter, 0);
                                if (!methodComplexity.isInbound(bodyComplexity)) {
                                    registerError(annotation);
                                }
                            }
                        }
                    }
                }
            }
        };
    }

    private int getNestedLoopsNumber(PsiStatement[] statements, PsiParameter parameter, int counter) {
        int nestedLoopsNumber = counter;
        for (PsiStatement statement : statements) {
            if (statement instanceof PsiForStatement) {
                if (dependsOn(((PsiForStatement) statement).getCondition(), parameter)) {
                    PsiStatement body = ((PsiForStatement) statement).getBody();
                    PsiStatement[] loopStatements = body instanceof PsiBlockStatement ? ((PsiBlockStatement) body).getCodeBlock().getStatements() : new PsiStatement[]{body};
                    int statementNestedLoops = getNestedLoopsNumber(loopStatements, parameter, nestedLoopsNumber + 1);
                    if (statementNestedLoops > nestedLoopsNumber) {
                        nestedLoopsNumber = statementNestedLoops;
                    }
                }
            }
        }
        return nestedLoopsNumber;
    }

    private boolean dependsOn(PsiExpression expression, PsiParameter parameter) {
        if (expression instanceof PsiPolyadicExpression) {
            for (PsiExpression operand : ((PsiPolyadicExpression) expression).getOperands()) {
                if (dependsOn(operand, parameter)) {
                    return true;
                }
            }
            return false;
        } else if (expression instanceof PsiParenthesizedExpression) {
            return dependsOn(((PsiParenthesizedExpression) expression).getExpression(), parameter);
        } else if (expression instanceof PsiReferenceExpression) {
            PsiReferenceExpression reference = (PsiReferenceExpression) expression;
            PsiElement qualifier = reference.getQualifier();
            if (qualifier != null)
                if (qualifier.getReference() != null) {
                    return qualifier.getReference().resolve() == parameter;
                }
            return false;
        } else
            return false;
    }
}
