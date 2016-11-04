package com.ljfxyj2008.CustomLint.lintrules.detectors;

import com.android.annotations.NonNull;
import com.android.tools.lint.client.api.JavaParser;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

import java.util.Arrays;
import java.util.List;

import lombok.ast.AstVisitor;
import lombok.ast.ExpressionStatement;
import lombok.ast.ForwardingAstVisitor;
import lombok.ast.Modifiers;
import lombok.ast.Node;
import lombok.ast.StrictListAccessor;
import lombok.ast.VariableDeclaration;
import lombok.ast.VariableDefinition;
import lombok.ast.VariableDefinitionEntry;

/**
 * <pre>
 * Created by ljfxyj2008 on 16/1/18.
 * Email: ljfxyj2008@gmail.com
 * GitHub: <a href="https://github.com/ljfxyj2008">https://github.com/ljfxyj2008</a>
 * HomePage: <a href="http://www.carrotsight.com">http://www.carrotsight.com</a>
 * </pre>
 *
 * A constant should be named with only UPPER CASE letter and underline.
 */
public class ConstantNameDetector extends Detector
        implements Detector.JavaScanner {
    public static final Issue ISSUE = Issue.create("ConstantNameNotInUppercase",
            "A constant should be named with only UPPER CASE letter and underline.",
            "A constant should be named with only UPPER CASE letter and underline." +
                    "For example, `\"static final int FTP_SERVER_PORT = 80\"` is good, " +
                    "while `\"static final int ftpServerPort = 80\"` is inappropriate",
            Category.MESSAGES,
            9,
            Severity.ERROR,
            new Implementation(ConstantNameDetector.class,
                    Scope.JAVA_FILE_SCOPE));


    @Override
    public List<Class<? extends Node>> getApplicableNodeTypes() {
        return Arrays.asList(VariableDeclaration.class);

    }

    @Override
    public AstVisitor createJavaVisitor(@NonNull JavaContext context) {
        return new VariableDeclarationVisitor(context);

    }


    private class VariableDeclarationVisitor extends ForwardingAstVisitor {
        private final JavaContext mContext;

        public VariableDeclarationVisitor(JavaContext context) {
            mContext = context;
        }


        @Override
        public boolean visitExpressionStatement(ExpressionStatement node) {
            // System.out.println("===visitExpressionStatement node = " + node);
            return super.visitExpressionStatement(node);
        }


        @Override
        public boolean visitVariableDeclaration(VariableDeclaration node) {
            JavaParser.ResolvedNode resolved = mContext.resolve(JavaContext.findSurroundingClass(node));
            JavaParser.ResolvedClass sorroundingClass = (JavaParser.ResolvedClass) resolved;

            if (sorroundingClass.getName().contains(".R.")
                    || sorroundingClass.getName().endsWith(".BuildConfig")) {
                return true;
            }

            VariableDefinition definition = node.astDefinition();
            Modifiers modifiers = definition.astModifiers();

            if (modifiers.isStatic() && modifiers.isFinal()){
                StrictListAccessor<VariableDefinitionEntry, VariableDefinition> variableDefinition = definition.astVariables();
                String variableName = variableDefinition.first().astName().toString();
                if (!variableName.equals(variableName.toUpperCase())){
                    mContext.report(ISSUE,
                            node,
                            mContext.getLocation(node),
                            "A constant should be named with only UPPER CASE letter and underline.");
                }


            }

            return super.visitVariableDeclaration(node);
        }


    }

}