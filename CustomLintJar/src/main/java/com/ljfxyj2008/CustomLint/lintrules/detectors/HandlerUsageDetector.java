package com.ljfxyj2008.CustomLint.lintrules.detectors;

import com.android.annotations.NonNull;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.ClassContext;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.Arrays;
import java.util.List;


/**
 * <pre>
 * Created by ljfxyj2008 on 16/1/18.
 * Email: ljfxyj2008@gmail.com
 * GitHub: <a href="https://github.com/ljfxyj2008">https://github.com/ljfxyj2008</a>
 * HomePage: <a href="http://www.carrotsight.com">http://www.carrotsight.com</a>
 * </pre>
 *
 * You should NOT instantiate android.os.Handler by implement `handleMessage(Message msg)`. Instead you should use HandlerUtils we offered.
 */
public class HandlerUsageDetector extends Detector
        implements Detector.ClassScanner {

    public static final Issue ISSUE = Issue.create("HandlerUtilsNotUsed",
            "You must use our `HandlerUtils`",
            "You should NOT instantiate android.os.Handler by implement `handleMessage(Message msg)`. Instead you should use HandlerUtils we offered.",
            Category.USABILITY,
            9,
            Severity.ERROR,
            new Implementation(HandlerUsageDetector.class,
                    Scope.CLASS_FILE_SCOPE));

    @Override
    public List<String> getApplicableCallNames() {
        return Arrays.asList("handleMessage");
    }


    @Override
    public void checkCall(@NonNull ClassContext context,
                          @NonNull ClassNode classNode,
                          @NonNull MethodNode method,
                          @NonNull MethodInsnNode call) {
        String owner = call.owner;
        if (owner.startsWith("android/os/Handler")) {
            context.report(ISSUE,
                    method,
                    call,
                    context.getLocation(call),
                    "You must use our `HandlerUtils`");
        }
    }
}