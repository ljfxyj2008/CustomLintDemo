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
import lombok.ast.BinaryExpression;
import lombok.ast.Expression;
import lombok.ast.MethodInvocation;
import lombok.ast.Node;
import lombok.ast.StrictListAccessor;

/**
 * <pre>
 * Created by ljfxyj2008 on 16/11/15.
 * Email: ljfxyj2008@gmail.com
 * GitHub: <a href="https://github.com/ljfxyj2008">https://github.com/ljfxyj2008</a>
 * HomePage: <a href="http://www.carrotsight.com">http://www.carrotsight.com</a>
 * </pre>
 *
 * You should pass in a value or variable as a parameter, rather than passing in the operand and operator as parameters.
 *
 * <p/>
 * For example, when you call method :
 * <pre>
 * public void printName(String name){...}
 * </pre>
 * You should call it like this:
 * <pre>
 *     {
 *         ...
 *         printName("John");
 *
 *         String mName = "Lucy";
 *         printName(mName);
 *         ...
 *     }
 * </pre>
 *
 * You should *NOT* call it like this:
 * <pre>
 *     {
 *         ...
 *         printName("John" + "Lucy");//Don't do this
 *
 *         String mName1 = "Lucy";
 *         String mName2 = "Jack";
 *         printName(mName1 + mName2);//Don't do this
 *         ...
 *     }
 * </pre>
 */
public class NotOperatorArgumentDetector extends Detector
        implements Detector.JavaScanner {
    public static final Issue ISSUE = Issue.create("NotOperatorInArguments",
            "You should pass in a value or variable as a parameter, rather than passing in operands and operator as parameters.",
            "You should pass in a value or variable as a parameter, rather than passing in operands and operator as parameters.",
            Category.MESSAGES,
            9,
            Severity.ERROR,
            new Implementation(NotOperatorArgumentDetector.class,
                    Scope.JAVA_FILE_SCOPE));



    @Override
    public List<String> getApplicableMethodNames() {
        return Arrays.asList("printName", "otherMethodName");
    }

    @Override
    public void visitMethod(@NonNull JavaContext context, AstVisitor visitor, @NonNull MethodInvocation node) {
        //String methodName = node.astName().astValue();

        StrictListAccessor<Expression, MethodInvocation> arguments = node.astArguments();
        Iterator<Expression> argIterator = arguments.iterator();
        while (argIterator.hasNext()){
            Expression mArg = argIterator.next();
            if (mArg instanceof BinaryExpression){
                context.report(ISSUE,
                        node,
                        context.getLocation(node),
                        "You should pass in a value or variable as a parameter, rather than passing in operands and operator as parameters.");
            }
        }

    }


}