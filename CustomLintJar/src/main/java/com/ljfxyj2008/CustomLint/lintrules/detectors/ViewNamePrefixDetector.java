/*
 * This source code is licensed under the MIT-style license found in the
 * LICENSE file in the root directory of this source tree.
 */
package com.ljfxyj2008.CustomLint.lintrules.detectors;

import com.android.SdkConstants;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.LayoutDetector;
import com.android.tools.lint.detector.api.LintUtils;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.android.tools.lint.detector.api.XmlContext;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import java.util.Arrays;
import java.util.Collection;


/**
 * <pre>
 * Created by ljfxyj2008 on 16/1/18.
 * Email: ljfxyj2008@gmail.com
 * GitHub: <a href="https://github.com/ljfxyj2008">https://github.com/ljfxyj2008</a>
 * HomePage: <a href="http://www.carrotsight.com">http://www.carrotsight.com</a>
 * </pre>
 *
 * In layout xml file, you should name an view item with specified prefix as following:
 * <pre>
 * ================================
 * View type        |Abbreviation prefix
 * -------------    |-------------
 * View             |v
 * Button           |btn
 * ImageView        |img
 * ImageButton      |imgBtn
 * TextView         |tv
 * ListView         |lv
 * RecycleView      |rv
 * LinearLayout     |ll
 * RelativeLayout   |rl
 * FrameLayout      |fl
 * ================================
 * </pre>
 *
 * For example, you should set id of Button in xml like this:<br>
 *
 * <pre>
 * < Button android:id="@+id/btn_login"
 *      android:layout_width="wrap_content"
 *      android:layout_height="wrap_content" />"
 * </pre>
 *
 */
public class ViewNamePrefixDetector extends LayoutDetector {
    public static final Issue ISSUE = Issue.create("ViewNamePrefixGone",
            "In layout xml file, you should name an view item with specified prefix.",
            "In layout xml file, you should name an view item with specified prefix." +
                    "The specification of view name prefix is below:" +
                    "\n* ================================\n" +
                    " * View type        |Abbreviation prefix\n" +
                    " * -------------    | -------------\n" +
                    " * View             -->v\n" +
                    " * Button           -->btn\n" +
                    " * ImageView        -->img\n" +
                    " * ImageButton      -->imgBtn\n" +
                    " * TextView         -->tv\n" +
                    " * ListView         -->lv\n" +
                    " * RecycleView      -->rv\n" +
                    " * LinearLayout     -->ll\n" +
                    " * RelativeLayout   -->rl\n" +
                    " * FrameLayout      -->fl\n" +
                    " * ================================" +
                    "\nFor example, `v_myCustomView` is appropriate.",
            Category.MESSAGES,
            9,
            Severity.ERROR,
            new Implementation(ViewNamePrefixDetector.class,
                    Scope.RESOURCE_FILE_SCOPE));

    @Override
    public Collection<String> getApplicableElements() {
        return Arrays.asList(
                SdkConstants.VIEW,
                SdkConstants.BUTTON,
                SdkConstants.IMAGE_VIEW,
                SdkConstants.IMAGE_BUTTON,
                SdkConstants.TEXT_VIEW,
                SdkConstants.LIST_VIEW,
                "android.support.v7.widget.RecyclerView",
                SdkConstants.LINEAR_LAYOUT,
                SdkConstants.RELATIVE_LAYOUT,
                SdkConstants.ABSOLUTE_LAYOUT,
                SdkConstants.FRAME_LAYOUT,
                SdkConstants.GRID_LAYOUT);
    }


    @Override
    public void visitElement(XmlContext context, Element element) {
        String nodeName = element.getNodeName();

        Attr attrNode = element.getAttributeNode("android:id");
        if (attrNode != null) {
            String attrValue = attrNode.getValue();
            String viewIdValue = LintUtils.stripIdPrefix(attrValue);

            switch (nodeName) {
                case SdkConstants.VIEW:
                    checkViewNameWithSpecificPrefix(viewIdValue, "v_", context, attrNode);
                    break;

                case SdkConstants.BUTTON:
                    checkViewNameWithSpecificPrefix(viewIdValue, "btn_", context, attrNode);
                    break;

                case SdkConstants.IMAGE_VIEW:
                    checkViewNameWithSpecificPrefix(viewIdValue, "img_", context, attrNode);
                    break;

                case SdkConstants.IMAGE_BUTTON:
                    checkViewNameWithSpecificPrefix(viewIdValue, "imgBtn_", context, attrNode);
                    break;

                case SdkConstants.TEXT_VIEW:
                    checkViewNameWithSpecificPrefix(viewIdValue, "tv_", context, attrNode);
                    break;

                case SdkConstants.LIST_VIEW:
                    checkViewNameWithSpecificPrefix(viewIdValue, "lv_", context, attrNode);
                    break;

                case "android.support.v7.widget.RecyclerView":
                    checkViewNameWithSpecificPrefix(viewIdValue, "rv_", context, attrNode);
                    break;

                case SdkConstants.LINEAR_LAYOUT:
                    checkViewNameWithSpecificPrefix(viewIdValue, "ll_", context, attrNode);
                    break;

                case SdkConstants.RELATIVE_LAYOUT:
                    checkViewNameWithSpecificPrefix(viewIdValue, "rl_", context, attrNode);
                    break;

                case SdkConstants.ABSOLUTE_LAYOUT:
                    checkViewNameWithSpecificPrefix(viewIdValue, "al_", context, attrNode);
                    break;

                case SdkConstants.FRAME_LAYOUT:
                    checkViewNameWithSpecificPrefix(viewIdValue, "fl_", context, attrNode);
                    break;

                case SdkConstants.GRID_LAYOUT:
                    checkViewNameWithSpecificPrefix(viewIdValue, "gl_", context, attrNode);
                    break;
            }

        }

    }

    private boolean checkViewNameWithSpecificPrefix(String viewName, String prefix, XmlContext context, Attr attrNode) {
        if (viewName.startsWith(prefix)) {

            return true;
        } else {
            reportIssus(context, attrNode);
            return false;
        }
    }

    private void reportIssus(XmlContext context, Attr attrNode){
        context.report(ISSUE,
                attrNode,
                context.getLocation(attrNode),
                "In layout xml file, you should name an view item with specified prefix.");
    }


}