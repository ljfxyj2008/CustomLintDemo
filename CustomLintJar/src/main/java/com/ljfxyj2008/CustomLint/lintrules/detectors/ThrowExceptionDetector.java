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
import lombok.ast.ConstructorInvocation;
import lombok.ast.ForwardingAstVisitor;
import lombok.ast.Node;
import lombok.ast.Throw;

/**
 * <pre>
 * Created by ljfxyj2008 on 16/1/18.
 * Email: ljfxyj2008@gmail.com
 * GitHub: <a href="https://github.com/ljfxyj2008">https://github.com/ljfxyj2008</a>
 * HomePage: <a href="http://www.carrotsight.com">http://www.carrotsight.com</a>
 * </pre>
 *
 * You should throw your own exception with the one caught in try-catch block.<br>
 *     <p>
 * For example, the following code segment is irregular:
 * <pre>
 *     try{
 *         ...
 *     }catch(XXXException e){
 *         throw new ManagerException("Database error");
 *     }
 *
 * </pre>
 *
 * <p>
 * You should do the following:
 * <pre>
 *     try{
 *         ...
 *     }catch(XXXException e){
 *         throw new ManagerException("Database error", e);
 *     }
 *
 * </pre>
 */
public class ThrowExceptionDetector extends Detector
        implements Detector.JavaScanner {
    public static final Issue ISSUE = Issue.create("ThrowExceptionIrregular",
            "You should throw your own exception with the one caught in try-catch block.",
            "You should throw your own exception with the one caught in try-catch block." +
                    "\n For example, `throw new ManagerException(\"Database error\")` is irregular." +
                    "\n You should do `throw new ManagerException(\"Database error\", e)` instead, where `e` is the param of `catch(Throwable e){}` statement.",
            Category.CORRECTNESS,
            9,
            Severity.ERROR,
            new Implementation(ThrowExceptionDetector.class,
                    Scope.JAVA_FILE_SCOPE));

    @Override
    public List<Class<? extends Node>> getApplicableNodeTypes() {
        return Arrays.asList(Throw.class);
    }

    @Override
    public AstVisitor createJavaVisitor(@NonNull JavaContext context) {
        return new ThrowExceptionParamsAstVisitor(context);
    }




    private class ThrowExceptionParamsAstVisitor extends ForwardingAstVisitor {
        private final JavaContext mContext;

        public ThrowExceptionParamsAstVisitor(JavaContext context) {
            mContext = context;
        }


        @Override
        public boolean visitThrow(Throw node) {
            //There are two possible scene:
            //(1)   throw new RuntimeException("my custom exception", e);
            //
            //(2)   {
            //          RuntimeException myexception = new RuntimeException("my conn exception");
            //          throw myexception;
            //      }
            //
            //The two cases should be handled differently,
            //as in case1, node.astThrowable().getClass() is lombok.ast.ConstructorInvocation ,
            //and in case2, node.astThrowable().getClass() is lombok.ast.VariableReference .

            if ("lombok.ast.ConstructorInvocation".equals(node.astThrowable().getClass().getName())){
                //System.out.println("&&&&&&&& In case (1)!!!");
                JavaParser.ResolvedMethod resolvedMethod = (JavaParser.ResolvedMethod)mContext.resolve(node.astThrowable());

                int argumentCount = resolvedMethod.getArgumentCount();
                for (int i=0; i < argumentCount; i++){
                    if(resolvedMethod.getArgumentType(i).getTypeClass().isSubclassOf("java.lang.Throwable", false)){
                        System.out.println("isSubclassOf(\"java.lang.Throwable\"!");
                        return true;
                    }
                }

                mContext.report(ISSUE,
                        node,
                        mContext.getLocation(node),
                        "You should throw your own exception with the one caught in try-catch block.");

                //isThrowableConstructorInvocationCalledWithParamThrowable(mContext, (ConstructorInvocation)node.astThrowable());
            } else if ("lombok.ast.VariableReference".equals(node.astThrowable().getClass().getName())){
                //Have no good idea to handle this situation,
                // for the statement "RuntimeException myexception = new RuntimeException("my conn exception");" could be anywhere.
                //

                //System.out.println("&&&&&&&& In case (2)!!!");

                //JavaParser.ResolvedVariable resolvedVariable = (JavaParser.ResolvedVariable)mContext.resolve(node.astThrowable());
                //Throwable paramException = resolvedVariable.;
                /*int argumentCount = resolvedMethod.getArgumentCount();
                for (int i=0; i < argumentCount; i++){
                    if(resolvedMethod.getArgumentType(i).getTypeClass().isSubclassOf("java.lang.Throwable", false)){
                        System.out.println("isSubclassOf(\"java.lang.Throwable\"!");
                        return true;
                    }
                }*/
            }

/*
            System.out.println("$%$%$%$%node = " + node);
            System.out.println("\t\tnode.astThrowable() = " + node.astThrowable().getClass().getName());
            System.out.println("\t\tnode.rawThrowable() = " + node.rawThrowable().getClass().getName());*/
            return true;


        }

        @Override
        public boolean visitConstructorInvocation(ConstructorInvocation node) {

            JavaParser.ResolvedNode resolvedType = mContext.resolve(node.astTypeReference());
            JavaParser.ResolvedClass resolvedClass = (JavaParser.ResolvedClass) resolvedType;

            JavaParser.ResolvedMethod resolvedMethod = (JavaParser.ResolvedMethod)mContext.resolve(node);

            if (resolvedClass != null
                    && resolvedClass.isSubclassOf("java.lang.Exception", false)){

                return true;

            }

            return super.visitConstructorInvocation(node);
        }

    }

}