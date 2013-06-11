package org.richfaces.testng;

import org.ajax4jsf.template.Template;
import org.richfaces.AutoTester;
import org.richfaces.AutoTester.TestSetupEntry;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PanelMenuGroupTest extends PanelMenuItemTest {

    private final static String CORE_TEST_URL = "panelMenuGroupCoreTest.xhtml";

    private static final String RESET_METHOD = "#{panelBean.reset}";

    @Test
    public void testIconsAttributesApply(Template  template) {
        renderPage(LOOK_AND_FEEL_TEST_URL, template, null);

        writeStatus("Check icons attributes apply: are output to client and images are accessible");
        String itemId = getParentId() + FORM_ID + "componentId";
        String iconImgXpath = "//*[@id='" + itemId + "']/table/tbody/tr/td[1]/img";

        writeStatus("Check iconDisabled attribute");
        disable(true);

        Assert.assertTrue(isPresent(iconImgXpath), "Icon is not rendered");
        testIcon(iconImgXpath, "Disc");

        disable(false);

        writeStatus("Check iconCollapsed attribute");
        Assert.assertTrue(isPresent(iconImgXpath), "Icon is not rendered");
        testIcon(iconImgXpath, "TriangleUp");

        writeStatus("Check iconExpanded attribute");
        clickAjaxCommandAndWait("icon" + itemId);
        Assert.assertTrue(isPresent(iconImgXpath), "Icon is not rendered");
        testIcon(iconImgXpath, "TriangleDown");
    }

    @Test
    public void testOncollapseOnexpandEventsFired(Template template) {
        renderPage(LOOK_AND_FEEL_TEST_URL, template, RESET_METHOD);

        writeStatus("Check oncollapse/onexpand events are fired ");

        String componentId = "icon" + getParentId() + FORM_ID + "componentId";

        clickAjaxCommandAndWait(componentId);
        assertEvent("onexpand");
        clickAjaxCommandAndWait(componentId);
        assertEvent("oncollapse");
    }

    @Test
    public void testComponentIsFunctioningCorrectlyForAllSubmissionModes(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(CORE_TEST_URL, template, null);
        writeStatus("Check component is functioning correctly for all submission modes; server listeners fire");

        String serverGrpId = "icon" + tester.getClientId("serverGrp");
        String ajaxGrpId = "icon" + tester.getClientId("ajaxGrp");
        String noneGrpId = "icon" + tester.getClientId("noneGrp");

        writeStatus("Check server mode: listeners are invoked, model is updated");
        clickCommandAndWait(serverGrpId);

        tester.checkUpdateModel(true);
        tester.checkActionListener(true);

        writeStatus("Check ajax mode: listeners are invoked, model is updated");

        tester.startTracing();
        clickAjaxCommandAndWait(ajaxGrpId);

        tester.checkUpdateModel(true);
        tester.checkActionListener(true);

        writeStatus("Check none mode: listeners are not invoked, model is not updated");

        tester.startTracing();
        clickById(noneGrpId);

        tester.checkUpdateModel(false);
        tester.checkActionListener(false);
    }

    //https://jira.jboss.org/jira/browse/RF-6119
    @Test(groups=FAILURES_GROUP)
    public void testNestedComponentsAreNotProcessedForServerAwareClosedGroups(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(CORE_TEST_URL, template, RESET_METHOD);
        writeStatus("Check nested components aren't processed for server-aware closed groups");

        String inputsId = tester.getClientId("_inputs");

        writeStatus("Primarily all groups are closed. Check that content of server-aware groups are not processed");

        tester.clickSubmit();

        String inputs = selenium.getText(inputsId);
        Assert.assertTrue(inputs.contains("grp3"), "Content of closed non-server-aware group (grp3) must be processed");
        Assert.assertFalse(inputs.contains("grp2"), "Content of closed server-aware group (grp2) must not be processed");
        Assert.assertFalse(inputs.contains("grp1"), "Content of closed server-aware group (grp1) must not be processed");

        String sRequiredId = tester.getClientId("sRequired");
        String aRequiredId = tester.getClientId("aRequired");
        String nRequiredId = tester.getClientId("nRequired");

        String serverGrpId = "icon" + tester.getClientId("serverGrp");
        String ajaxGrpId = "icon" + tester.getClientId("ajaxGrp");
        String noneGrpId = "icon" + tester.getClientId("noneGrp");

        tester.setupControl(TestSetupEntry.required, true);
        tester.clickLoad();

        writeStatus("Now all groups have nested invalid controls");
        writeStatus("Because client groups are processed anyway clicking any server-aware group has no affect: " +
        		"listeners are not invoked, model is not update, a group is not expanded");
        clickAjaxCommandAndWait(ajaxGrpId);
        tester.checkUpdateModel(false);
        tester.checkActionListener(false);
        AssertNotPresentOrNotVisible(aRequiredId, "Ajax group must not be expanded");

        clickCommandAndWait(serverGrpId);
        tester.checkUpdateModel(false);
        tester.checkActionListener(false);
        AssertNotPresentOrNotVisible(aRequiredId, "Server group must not be expanded");

        writeStatus("Fix client group invalid content and so on");
        clickById(noneGrpId);
        setValueById(nRequiredId, "valid");

        clickAjaxCommandAndWait(ajaxGrpId);
        tester.checkUpdateModel(true);
        tester.checkActionListener(true);
        AssertPresentAndVisible(aRequiredId, "Ajax group would have been expanded");

        tester.startTracing();
        clickCommandAndWait(serverGrpId);
        tester.checkUpdateModel(false);
        tester.checkActionListener(false);
        AssertNotPresentOrNotVisible(sRequiredId, "Server group wouldn't have been expanded");

        writeStatus("Fix ajax group invalid content");
        clickAjaxCommandAndWait(ajaxGrpId);
        setValueById(aRequiredId, "valid");

        clickCommandAndWait(serverGrpId);
        tester.checkUpdateModel(true);
        tester.checkActionListener(true);
        AssertPresentAndVisible(sRequiredId, "Server group would have been expanded");
    }

    @Test
    public void testComponentIsOutputToClientAndTopLevelAndNotHaveDifferentStyling(Template template) {
        AutoTester tester = getAutoTester(this);
        tester.renderPage(template, null);
        writeStatus("Check component is output to client; top-level and not groups have different styling");

        String componentId = tester.getClientId("componentId");
        AssertPresentAndVisible(componentId, "Component is not output to a client");
//        String subComponentId = tester.getClientId("subComponentId");
//
//        String styleGroup = selenium.getAttribute("//*[@id='icon" + componentId + "']/@style");
//        String styleSubGroup = selenium.getAttribute("//*[@id='icon" + subComponentId + "']/@style");
//
//        Assert.assertFalse(styleGroup == null ? styleSubGroup == null : styleGroup.equals(styleSubGroup), "Expected [" + styleGroup + "] <> [" + styleSubGroup + "]");
    }

    @Override
    public void sendAjax() {
        clickAjaxCommandAndWait("icon" + getAutoTester(this).getClientId(AutoTester.COMPONENT_ID));
    }

    @Override
    public String getTestBase() {
        return "pages/panelMenuGroup/";
    }

    @Override
    public String getAutoTestUrl() {
        return "panelMenuGroupAutoTest.xhtml";
    }

}
