package com.ljfxyj2008.CustomLint.lintrules.detectors;

import com.android.annotations.NonNull;
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
import lombok.ast.For;
import lombok.ast.ForwardingAstVisitor;
import lombok.ast.If;
import lombok.ast.Node;
import lombok.ast.Try;


/**
 * <pre>
 * Created by ljfxyj2008 on 16/1/18.
 * Email: ljfxyj2008@gmail.com
 * GitHub: <a href="https://github.com/ljfxyj2008">https://github.com/ljfxyj2008</a>
 * HomePage: <a href="http://www.carrotsight.com">http://www.carrotsight.com</a>
 * </pre>
 *
 * Nested For/If/Try statements can not be more than 3 layers.
 */
public class ForIfTryDepthDetector extends Detector
        implements Detector.JavaScanner {
    public static final Issue ForDepthISSUE = Issue.create("ForDepthTooLarge",
            "Nested `for` statements can not be more than 3 layers.",
            "Nested `for` statements can not be more than 3 layers. You should optimize your code structure.",
            Category.PERFORMANCE,
            9,
            Severity.ERROR,
            new Implementation(ForIfTryDepthDetector.class,
                    Scope.JAVA_FILE_SCOPE));

    public static final Issue IfDepthISSUE = Issue.create("IfDepthTooLarge",
            "Nested `if` statements can not be more than 3 layers.",
            "Nested `if` statements can not be more than 3 layers. You should optimize your code structure.",
            Category.PERFORMANCE,
            9,
            Severity.ERROR,
            new Implementation(ForIfTryDepthDetector.class,
                    Scope.JAVA_FILE_SCOPE));

    public static final Issue TryDepthISSUE = Issue.create("TryDepthTooLarge",
            "Nested `try` statements can not be more than 3 layers.",
            "Nested `try` statements can not be more than 3 layers. You should optimize your code structure.",
            Category.PERFORMANCE,
            9,
            Severity.ERROR,
            new Implementation(ForIfTryDepthDetector.class,
                    Scope.JAVA_FILE_SCOPE));


    @Override
    public List<Class<? extends Node>> getApplicableNodeTypes() {
        return Arrays.asList(Try.class, If.class, For.class);

    }

    @Override
    public AstVisitor createJavaVisitor(@NonNull JavaContext context) {

        return new ForIfTryBlockVisitor(context);

    }


    /**
     * Get depth of current Try node.
     * By default, the init depth of Try node is 1.
     * @param node current Try node.
     */
    private int getCurrentTryDepth(Try node) {
        Node currentCheckNode = node;
        int depth = 0;
        while (currentCheckNode != null){
            if (currentCheckNode instanceof Try){
                depth++;
            }

            currentCheckNode = currentCheckNode.getParent();

        }
        return depth;
    }


    /**
     * Get depth of current If node.
     * By default, the init depth of If node is 1.
     * @param node current If node.
     */
    private int getCurrentIfDepth(If node) {
        Node currentCheckNode = node;
        int depth = 0;
        while (currentCheckNode != null){
            if (currentCheckNode instanceof If){
                depth++;
            }

            currentCheckNode = currentCheckNode.getParent();

        }
        return depth;
    }

    private int getCurrentForDepth(For node) {
        Node currentCheckNode = node;
        int depth = 0;
        while (currentCheckNode != null){
            if (currentCheckNode instanceof For){
                depth++;
            }

            currentCheckNode = currentCheckNode.getParent();

        }
        return depth;
    }

    private class ForIfTryBlockVisitor extends ForwardingAstVisitor {
        private final JavaContext mContext;

        public ForIfTryBlockVisitor(JavaContext context) {
            mContext = context;
        }

        @Override
        public boolean visitTry(Try node) {
            int tryDepth = getCurrentTryDepth(node);
            if (tryDepth > 3) {
                mContext.report(TryDepthISSUE,
                        node,
                        mContext.getLocation(node),
                        "Nested `try` statements can not be more than 3 layers." +
                                "Current layer count is " + tryDepth + "!");
            }

            return super.visitTry(node);
        }

        @Override
        public boolean visitFor(For node) {
            int forDepth = getCurrentForDepth(node);
            if (forDepth > 3) {
                mContext.report(ForDepthISSUE,
                        node,
                        mContext.getLocation(node),
                        "Nested `for` statements can not be more than 3 layers." +
                                "Current layer count is " + forDepth + "!");
            }

            return super.visitFor(node);
        }

        @Override
        public boolean visitIf(If node) {
            int ifDepth = getCurrentIfDepth(node);
            if (ifDepth > 3) {
                mContext.report(IfDepthISSUE,
                        node,
                        mContext.getLocation(node),
                        "Nested `if` statements can not be more than 3 layers." +
                                "Current layer count is " + ifDepth + "!");
            }

            return super.visitIf(node);
        }


    }

}