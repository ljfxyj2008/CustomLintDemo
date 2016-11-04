package com.ljfxyj2008.CustomLint.lintrules;

import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.Issue;
import com.ljfxyj2008.CustomLint.lintrules.detectors.ActivityFragmentLayoutNameDetector;
import com.ljfxyj2008.CustomLint.lintrules.detectors.BuildGradleVersionDetector;
import com.ljfxyj2008.CustomLint.lintrules.detectors.ConstantNameDetector;
import com.ljfxyj2008.CustomLint.lintrules.detectors.ForIfTryDepthDetector;
import com.ljfxyj2008.CustomLint.lintrules.detectors.HandlerUsageDetector;
import com.ljfxyj2008.CustomLint.lintrules.detectors.LogUsageDetector;
import com.ljfxyj2008.CustomLint.lintrules.detectors.MessageObtainDetector;
import com.ljfxyj2008.CustomLint.lintrules.detectors.ThrowExceptionDetector;
import com.ljfxyj2008.CustomLint.lintrules.detectors.ToastUsageDetector;
import com.ljfxyj2008.CustomLint.lintrules.detectors.ViewHolderItemNameDetector;
import com.ljfxyj2008.CustomLint.lintrules.detectors.ViewNamePrefixDetector;

import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 * Created by ljfxyj2008 on 16/01/07.
 * Email: ljfxyj2008@gmail.com
 * GitHub: <a href="https://github.com/ljfxyj2008">https://github.com/ljfxyj2008</a>
 * HomePage: <a href="http://www.carrotsight.com">http://www.carrotsight.com</a>
 * </pre>
 *
 * Main class to register custom lint rules.
 */
public class MyIssueRegistry extends IssueRegistry {
    @Override
    public List<Issue> getIssues() {
        System.out.println("********Custom Lint rules works!!!********");
        return Arrays.asList(
                LogUsageDetector.ISSUE,
                ToastUsageDetector.ISSUE,
                HandlerUsageDetector.ISSUE,
                ActivityFragmentLayoutNameDetector.ACTIVITY_LAYOUT_NAME_ISSUE,
                ActivityFragmentLayoutNameDetector.FRAGMENT_LAYOUT_NAME_ISSUE,
                ThrowExceptionDetector.ISSUE,
                MessageObtainDetector.ISSUE,
                ViewNamePrefixDetector.ISSUE,
                ForIfTryDepthDetector.TryDepthISSUE,
                ForIfTryDepthDetector.IfDepthISSUE,
                ForIfTryDepthDetector.ForDepthISSUE,
                ConstantNameDetector.ISSUE,
                BuildGradleVersionDetector.ISSUE,
                ViewHolderItemNameDetector.ISSUE);
    }
}
