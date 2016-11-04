/*
 * This source code is licensed under the MIT-style license found in the
 * LICENSE file in the root directory of this source tree.
 */
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

import java.util.Collections;
import java.util.List;

import lombok.ast.AstVisitor;
import lombok.ast.ConstructorInvocation;
import lombok.ast.ForwardingAstVisitor;
import lombok.ast.Node;

/**
 * <pre>
 * Created by ljfxyj2008 on 16/1/18.
 * Email: ljfxyj2008@gmail.com
 * GitHub: <a href="https://github.com/ljfxyj2008">https://github.com/ljfxyj2008</a>
 * HomePage: <a href="http://www.carrotsight.com">http://www.carrotsight.com</a>
 * </pre>
 *
 * You should not call `new Message()` directly. Instead, you should use `handler.obtainMessage` or `Message.Obtain()`.
 */
public class MessageObtainDetector extends Detector
        implements Detector.JavaScanner {
    public static final Issue ISSUE = Issue.create("MessageObtainNotUsed",
            "You should not call `new Message()` directly.",
            "You should not call `new Message()` directly. Instead, you should use `handler.obtainMessage` or `Message.Obtain()`.",
            Category.CORRECTNESS,
            9,
            Severity.ERROR,
            new Implementation(MessageObtainDetector.class,
                    Scope.JAVA_FILE_SCOPE));

    @Override
    public List<Class<? extends Node>> getApplicableNodeTypes() {
        return Collections.<Class<? extends Node>>singletonList(ConstructorInvocation.class);
    }

    @Override
    public AstVisitor createJavaVisitor(@NonNull JavaContext context) {
        return new MessageObtainVisitor(context);
    }


    private class MessageObtainVisitor extends ForwardingAstVisitor {
        private final JavaContext mContext;

        public MessageObtainVisitor(JavaContext context) {
            mContext = context;
        }

        @Override
        public boolean visitConstructorInvocation(ConstructorInvocation node) {
            JavaParser.ResolvedNode resolvedType = mContext.resolve(node.astTypeReference());
            JavaParser.ResolvedClass resolvedClass = (JavaParser.ResolvedClass) resolvedType;

            if (resolvedClass != null
                    && resolvedClass.isSubclassOf("android.os.Message", false)){
                mContext.report(ISSUE,
                        node,
                        mContext.getLocation(node),
                        "You should not call `new Message()` directly.");

                return true;

            }

            return super.visitConstructorInvocation(node);
        }


    }

}