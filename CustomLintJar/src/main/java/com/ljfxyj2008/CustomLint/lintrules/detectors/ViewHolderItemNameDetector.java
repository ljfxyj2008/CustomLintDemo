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

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import lombok.ast.AstVisitor;
import lombok.ast.Expression;
import lombok.ast.MethodInvocation;


/**
 * <pre>
 * Created by ljfxyj2008 on 16/1/18.
 * Email: ljfxyj2008@gmail.com
 * GitHub: <a href="https://github.com/ljfxyj2008">https://github.com/ljfxyj2008</a>
 * HomePage: <a href="http://www.carrotsight.com">http://www.carrotsight.com</a>
 * </pre>
 *
 * Layout resource file in ViewHolder must be named with prefix "item_".
 * <p>
 * For example, "R.layout.item_bookinfo" is a appropriate name.
 */
public class ViewHolderItemNameDetector extends Detector
        implements Detector.JavaScanner {
    public static final Issue ISSUE = Issue.create("ViewHolderItemNamePrefixGone",
            "Layout resource file in ViewHolder must be named with prefix `item_`.",
            "Layout resource file in ViewHolder must be named with prefix `item_`." +
                    "For example, `R.layout.item_bookinfo` is a appropriate name.",
            Category.MESSAGES,
            9,
            Severity.ERROR,
            new Implementation(ViewHolderItemNameDetector.class,
                    Scope.JAVA_FILE_SCOPE));


    @Override
    public List<String> getApplicableMethodNames() {
        return Arrays.asList("inflate");
    }

    @Override
    public void visitMethod(@NonNull JavaContext context, AstVisitor visitor, @NonNull MethodInvocation node) {
        JavaParser.ResolvedClass sorroundingClass = (JavaParser.ResolvedClass)context.resolve(JavaContext.findSurroundingClass(node));
        JavaParser.ResolvedMethod sorroundingMethod = (JavaParser.ResolvedMethod)context.resolve(JavaContext.findSurroundingMethod(node));

        if (sorroundingMethod.getName().equals("onCreateViewHolder")
                && sorroundingClass.isSubclassOf("android.support.v7.widget.RecyclerView.Adapter", false)){


            String layoutString = getParamWithLayoutAnnotation(context, node);
            if (layoutString == null){
                return;
            }

            if (!isFileStringStartWithPrefix(layoutString, "item_")) {
                context.report(ISSUE,
                        node,
                        context.getLocation(node),
                        "Layout resource file in ViewHolder must be named with prefix `item_`.");
            }

        }


    }

    /**
     * There are more than one methods overloading in the name of "inflate()" in android.view.LayoutInflater.<br>
     * We only care about those having an param with `@LayoutRes` annotation,
     * for example {public View inflate(@LayoutRes int resource, @Nullable ViewGroup root, boolean attachToRoot)}.<br>
     * This method will find out the resource param with an `@LayoutRes` annotation in String format, for example `R.layout.fragment_blank` .<br>
     * If no such param exists, <B>null</B> will be returned.
     */
    private String getParamWithLayoutAnnotation(@NonNull JavaContext context, @NonNull MethodInvocation node) {
        Iterator<Expression> arguments = node.astArguments().iterator();
        Expression argument = arguments.next();

        JavaParser.ResolvedNode resolved = context.resolve(node);
        JavaParser.ResolvedMethod method = (JavaParser.ResolvedMethod) resolved;

        JavaParser.ResolvedAnnotation layoutParamAnnotation = method.getParameterAnnotation("android.support.annotation.LayoutRes", 0);
        if (layoutParamAnnotation != null) {
            return argument.toString();
        } else {
            return null;
        }

    }

    /**
     * We get the layout file resource name, for example "R.layout.fragment_blank".
     * This method will check if it starts with the given prefix.
     * @param layoutFileResourceString layout resource file name, like "R.layout.fragment_blank"
     * @param prefix the given prefix, must be "activity_" or "fragment)"
     * @return "true" if layoutFileResourceString starts with prefix, "false" otherwise.
     */
    private boolean isFileStringStartWithPrefix(String layoutFileResourceString, String prefix){
        int lastDotIndex = layoutFileResourceString.lastIndexOf(".");
        String fileName = layoutFileResourceString.substring(lastDotIndex + 1);
        if (fileName.startsWith(prefix)){
            return true;
        }else{
            return false;
        }
    }
}