package org.richfaces.testng.rf5948;

import org.richfaces.SeleniumTestBase;

public class Test extends SeleniumTestBase {

    private static final String TEST_STR = "test_01";
    
    private static final String FORM = "form";
    private static final String FORM_OUT = FORM + ":out";
    private static final String FORM_PANEL = FORM + ":panel_header";
    private static final String FORM_IN = FORM + ":in";

    @org.testng.annotations.Test
    public void testProcessAttr() throws Exception {
        renderPage();
        
        AssertPresent(FORM_IN);
        selenium.type(FORM_IN, TEST_STR);
        
        AssertPresent(FORM_PANEL);
        AssertPresent(FORM_OUT);
        clickAjaxCommandAndWait(FORM_PANEL);
        
        AssertValueEquals(FORM_OUT, TEST_STR);
    }
    
    @Override
    public String getTestUrl() {
        return "pages/rf5948.xhtml";
    }
}
