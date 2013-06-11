/**
 * 
 */
package org.richfaces.testng;

import junit.framework.Assert;

import org.ajax4jsf.template.Template;
import org.richfaces.SeleniumTestBase;
import org.testng.annotations.Test;

/**
 * @author Alexandr Levkovsky
 *
 */
public class ComponentControlTest extends SeleniumTestBase {
	
	private final static String TEST_COMPONENT_CONTROL_WITH_ATTACH_TO = "pages/componentControl/componentControlWithAttachTo.xhtml";
	private final static String TEST_COMPONENT_CONTROL_WITH_DISABLE_DEFAULT = "pages/componentControl/componentControlWithDisabledDefault.xhtml";
	private final static String TEST_COMPONENT_CONTROL_WITH_PARAMETERS = "pages/componentControl/componentControlWithParameters.xhtml";
	
	private String formId;
	private String panelId;
	private String panelShowLinkId;
	private String panelHideLinkId;
	private String tableId;
	private String outputTextId;
	private String panelSecondShowLinkId;
	private String fparamsTestLinkId;
	private String paramsTestLinkId;
	private String clearLinkId;
	private String testComponentId;
	
	
	private void initIds(String parentId){
		formId = parentId + "_form:";
		panelId = formId + "panel";
		panelHideLinkId = formId + "hidelink";
		panelShowLinkId = formId + "showlink";
		tableId = formId + "table";
		outputTextId = "outputText";
		panelSecondShowLinkId = formId + "showlink2";
		fparamsTestLinkId = formId + "link1";
		paramsTestLinkId = formId + "link2";
		clearLinkId = formId + "link3";
		testComponentId = formId + "comp";
	}

	/* (non-Javadoc)
	 * @see org.richfaces.SeleniumTestBase#getTestUrl()
	 */
	@Override
	public String getTestUrl() {
		return "pages/componentControl/componentControl.xhtml";
	}
	
	@Test
    public void testAttachingToEventHandlerOfParentComponent(Template template) {
        renderPage(template);
        initIds(getParentId());
        
        //check attaching
        AssertPresent(panelShowLinkId);
        String onclick = selenium.getAttribute("//a[@id='" + panelShowLinkId + "']/@onclick");
        Assert.assertNotNull("Component conytrol not attached to parent component event handler", onclick);
    	Assert.assertTrue(onclick.contains("Richfaces.componentControl.performOperation"));
        
        AssertPresent(tableId);
        for(int i=1; i < 8; i++){
        	onclick = selenium.getAttribute("//table[@id='" + tableId + "']/tbody/tr[" + i + "]/@onclick");
        	Assert.assertNotNull("Component conytrol not attached to parent component event handler", onclick);
        	Assert.assertTrue(onclick.contains("Richfaces.componentControl.performOperation"));
        }
        
        
        //check functional working
        asserPanel(false);
        clickById(panelShowLinkId);
        asserPanel(true);
        clickById(panelHideLinkId);
        
        for(int i=0; i < 8; i++){
        	asserPanel(false);
            clickById(tableId + ":" + i + ":" + outputTextId);
            asserPanel(true);
            clickById(panelHideLinkId);
        }
	}
	
	@Test
    public void testAttachingToEventHandlerWithAttachToAttribute(Template template) {
        renderPage(TEST_COMPONENT_CONTROL_WITH_ATTACH_TO, template, null);
        initIds(getParentId());        
        
        //check functional with attach to working
        
        //attach to in same naming container
        asserPanel(false);
        clickById(panelShowLinkId);
        asserPanel(true);
        clickById(panelHideLinkId);
      
        //attach to in another naming container
        asserPanel(false);
        clickById(panelSecondShowLinkId);
        asserPanel(true);
        clickById(panelHideLinkId);
	}
	
	@Test
    public void testForAndOperationAttribute(Template template) {
        //same as testAttachingToEventHandlerOfParentComponent
		
		renderPage(template);
        initIds(getParentId());
        
        
        //check operations show and hide for modal panel working
        asserPanel(false);
        clickById(panelShowLinkId);
        asserPanel(true);
        clickById(panelHideLinkId);
        
        for(int i=0; i < 8; i++){
        	asserPanel(false);
            clickById(tableId + ":" + i + ":" + outputTextId);
            asserPanel(true);
            clickById(panelHideLinkId);
        }
	}
	
	@Test(groups=FAILURES_GROUP)
    public void testDisableDefaultAttribute(Template template) {
        //Not working: https://jira.jboss.org/jira/browse/RF-5607 - waiting for fix
		
		renderPage(TEST_COMPONENT_CONTROL_WITH_DISABLE_DEFAULT, template, null);
        initIds(getParentId());        
        
        // check attaching and firing event with disabledDefault="true"
        
        //attach to in same naming container
        asserPanel(false);
        clickCommandAndWait(panelShowLinkId);
        asserPanel(true);
        clickById(panelHideLinkId);
      
        //attach to in another naming container
        asserPanel(false);
        clickCommandAndWait(panelSecondShowLinkId);
        asserPanel(true);
        clickById(panelHideLinkId);
	}
	
	@Test
    public void testParamsAttribute(Template template) {
      
		renderPage(TEST_COMPONENT_CONTROL_WITH_PARAMETERS, template, null);
        initIds(getParentId());        
        
        AssertPresent(testComponentId);        
        AssertTextEquals(testComponentId, "");              
        clickById(paramsTestLinkId);
        AssertTextEquals(testComponentId, "2");       
        clickById(clearLinkId);
	}
	
	@Test
    public void testpassingFParams(Template template) {
      
		renderPage(TEST_COMPONENT_CONTROL_WITH_PARAMETERS, template, null);
        initIds(getParentId());        
        
        AssertPresent(testComponentId);        
        AssertTextEquals(testComponentId, "");              
        clickById(fparamsTestLinkId);
        AssertTextEquals(testComponentId, "1");       
        clickById(clearLinkId);
	}
	
	
	private void asserPanel(boolean visible){
		AssertPresent(panelId);
		AssertPresent(panelId + "CDiv");
		if(visible){
			AssertVisible(panelId + "CDiv");
		}else{
			AssertNotVisible(panelId + "CDiv");
		}
		
	}

}
