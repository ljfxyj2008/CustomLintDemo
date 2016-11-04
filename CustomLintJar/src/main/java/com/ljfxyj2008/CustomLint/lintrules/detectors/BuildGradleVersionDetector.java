/*
 * This source code is licensed under the MIT-style license found in the
 * LICENSE file in the root directory of this source tree.
 */
package com.ljfxyj2008.CustomLint.lintrules.detectors;

import com.android.annotations.NonNull;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Context;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.Location;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

import java.io.File;
import java.util.Map;
import java.util.Scanner;

/**
 * <pre>
 * Created by ljfxyj2008 on 16/1/18.
 * Email: ljfxyj2008@gmail.com
 * GitHub: <a href="https://github.com/ljfxyj2008">https://github.com/ljfxyj2008</a>
 * HomePage: <a href="http://www.carrotsight.com">http://www.carrotsight.com</a>
 * </pre>
 *
 * In build.gradle file, version info should refer to gradle-wrapper.properties.
 */
public class BuildGradleVersionDetector extends Detector
        implements Detector.GradleScanner {
    public static final Issue ISSUE = Issue.create("BuildGradleVersionReferenceNotUsed",
            "In build.gradle file, version info should refer to gradle-wrapper.properties. ",
            "In build.gradle file, version info should refer to gradle-wrapper.properties. " +
                    "You should NOT define version info in build.gradle directly, " +
                    "for it is not conducive to a unified upgrades in the future.",
            Category.USABILITY,
            9,
            Severity.ERROR,
            new Implementation(BuildGradleVersionDetector.class,
                    Scope.GRADLE_SCOPE));

    @Override
    public boolean appliesTo(@NonNull Context context, @NonNull File file) {
        return true;
    }

    @Override
    public void visitBuildScript(@NonNull Context context, Map<String, Object> sharedData) {
        int lineNum = 0;
        Scanner scanner = new Scanner(context.getContents());
        while (scanner.hasNextLine()) {
            lineNum++;
            String line = scanner.nextLine();
            String lineTrim = line.trim();
            int firstWhileSpaceIndex = lineTrim.indexOf(" ");

            if (firstWhileSpaceIndex != -1) {
                String optionKey = lineTrim.substring(0, firstWhileSpaceIndex);
                if (optionKey.endsWith("Version")) {
                    String optionValue = lineTrim.substring(firstWhileSpaceIndex).trim();
                    if (optionValue.startsWith("\"") && optionValue.endsWith("\"")) {
                        optionValue = optionValue.substring(1, optionValue.length() - 1);
                    }
                    char valueFirstChar = optionValue.charAt(0);
                    if (valueFirstChar >= '0' && valueFirstChar <= '9') {

                        // Value of lineNum shows correct in Log info while debuging, but incorrect in lint report ( error of 1).
                        // So we decrease it by 1.
                        Location issueLocation = Location.create(context.file, context.getContents(), lineNum-1);

                        context.report(ISSUE,
                                issueLocation,
                                "In build.gradle file, version info should refer to gradle-wrapper.properties. ");

                    }


                }
            }

        }
        scanner.close();

    }


}