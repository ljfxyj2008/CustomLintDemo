package com.ljfxyj2008.CustomLint.lintrules.detectors;

import com.android.tools.lint.checks.infrastructure.LintDetectorTest;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Issue;
import com.google.common.collect.ImmutableList;

import java.io.InputStream;
import java.util.List;

public class LogUsageDetectorTest extends LintDetectorTest {

    public void testGetApplicableCallNames() throws Exception {

    }

    public void testGetApplicableMethodNames() throws Exception {

    }

    public void testCheckCall() throws Exception {
        String adsf = lintProject(java("src/main/java/com/ljfxyj2008/CustomLint/MainActivity.java",
                ""));

        String asdf = lintProject(
                xml("AndroidManifest.xml", "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
                        + "<manifest xmlns:android=\"http://schemas.android.com/apk/res/android\"\n"
                        + "    package=\"de.ad.test\" >\n"
                        + "\n"
                        + "    <application\n"
                        + "        android:allowBackup=\"true\"\n"
                        + "        android:icon=\"@mipmap/ic_launcher\"\n"
                        + "        android:label=\"Hello world\"\n"
                        + "        android:theme=\"@style/AppTheme\" >\n"
                        + "        <activity\n"
                        + "            android:name=\".MainActivity\"\n"
                        + "            android:label=\"@string/app_name\" >\n"
                        + "            <intent-filter>\n"
                        + "                <action android:name=\"android.intent.action.MAIN\" />\n"
                        + "\n"
                        + "                <category android:name=\"android.intent.category.LAUNCHER\" />\n"
                        + "            </intent-filter>\n"
                        + "        </activity>\n"
                        + "    </application>\n"
                        + "\n"
                        + "</manifest>"));
    }

    @Override
    protected Detector getDetector() {
        return new LogUsageDetector();
    }



    @Override
    protected List<Issue> getIssues() {
        return ImmutableList.of(LogUsageDetector.ISSUE);
    }

    @Override protected InputStream getTestResource(String relativePath, boolean expectExists) {
        String path = relativePath; //$NON-NLS-1$
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream(path);
        if (!expectExists && stream == null) {
            return null;
        }
        return stream;
    }
}