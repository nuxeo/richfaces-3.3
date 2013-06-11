package org.richfaces.testng.rf5851;

import org.richfaces.SeleniumTestBase;


public class Test extends SeleniumTestBase {

    private static final String TEST_STR = "test_02";
    
    private static final String FORM = "form";
    private static final String FORM_OUT = FORM + ":out";
    private static final String FORM_MENU_ITEM = FORM + ":menu";
    private static final String FORM_IN = FORM + ":in";

    @org.testng.annotations.Test
    public void testBeyondMinimum() throws Exception {
        renderPage();
        
        AssertPresent(FORM_IN);
        selenium.type(FORM_IN, TEST_STR);
        
        AssertPresent(FORM_MENU_ITEM);
        AssertPresent(FORM_OUT);
        clickAjaxCommandAndWait(FORM_MENU_ITEM);
        
        AssertValueEquals(FORM_OUT, TEST_STR);
    }
    
    @Override
    public String getTestUrl() {
        return "pages/rf5851.xhtml";
    }
}

