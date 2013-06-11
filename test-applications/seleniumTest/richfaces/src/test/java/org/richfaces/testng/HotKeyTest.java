package org.richfaces.testng;

import org.ajax4jsf.template.Template;
import org.richfaces.SeleniumTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HotKeyTest extends SeleniumTestBase {

	private String key;

	private String timing;

	private String hotKey;

	private String input;

	private void init(Template template) {
        renderPage(null, template, "#{hotKey.init}");
        String attrForm = getParentId() + "attrForm";
        key = attrForm + ":key";
        timing = attrForm + ":timing";
        hotKey = getParentId() + "hotKey";
        input = "input";
	}

	/**
	 *  Create hotKey component with timing="immediate"
	 *  (or "onload")attribute and check that it works -
	 *  alpha/numeric/arrows/special(ins/del/pgup/pgdn/home/end)/enter/esc keys
	 *  and Check 'key' attribute
	 */
	@Test
	public void testKeyAttribute(Template template) {
	    init(template);
	    
	    String keySequence = "alt+a";
		selenium.type(key, keySequence);
		waitForAjaxCompletion();
		selenium.altKeyDown();
		selenium.keyDown(input, "a");
		selenium.altKeyUp();
		Assert.assertEquals(selenium.getValue(input), keySequence);
		
	    keySequence = "ctrl+1";
		selenium.type(key, keySequence);
		waitForAjaxCompletion();
		selenium.controlKeyDown();
		selenium.keyDown(input, "1");
		selenium.controlKeyUp();
		Assert.assertEquals(selenium.getValue(input), keySequence);
		
	    keySequence = "shift+down";
		selenium.type(key, keySequence);
		waitForAjaxCompletion();
		selenium.shiftKeyDown();
		selenium.keyDown(input,  "\\40");
		selenium.shiftKeyUp();
		Assert.assertEquals(selenium.getValue(input), keySequence);
		
	    keySequence = "insert";
		selenium.type(key, keySequence);
		waitForAjaxCompletion();
		selenium.keyDown(input,  "\\45");
		Assert.assertEquals(selenium.getValue(input), keySequence);
		
	    keySequence = "del";
		selenium.type(key, keySequence);
		waitForAjaxCompletion();
		selenium.keyDown(input,  "\\46");
		Assert.assertEquals(selenium.getValue(input), keySequence);
		
	    keySequence = "pageup";
		selenium.type(key, keySequence);
		waitForAjaxCompletion();
		selenium.keyDown(input,  "\\33");
		Assert.assertEquals(selenium.getValue(input), keySequence);
		
	    keySequence = "end";
		selenium.type(key, keySequence);
		waitForAjaxCompletion();
		selenium.keyDown(input,  "\\35");
		Assert.assertEquals(selenium.getValue(input), keySequence);
		
	    keySequence = "return";
		selenium.type(key, keySequence);
		waitForAjaxCompletion();
		selenium.keyDown(input,  "\\13");
		Assert.assertEquals(selenium.getValue(input), keySequence);
		
	    keySequence = "esc";
		selenium.type(key, keySequence);
		waitForAjaxCompletion();
		selenium.keyDown(input,  "\\27");
		Assert.assertEquals(selenium.getValue(input), keySequence);
		
	}

	/**
	 *  check 'enable()' and 'disable()' JS API functions
	 */
	@Test
	public void testDisableFunction(Template template) {
	    init(template);
	    String keySequence = "return";
		selenium.type(key, keySequence);
		waitForAjaxCompletion();
	    selenium.runScript("var hotKey = document.getElementById('" + hotKey + "').component");
		Assert.assertEquals(selenium.getValue(input), "");
		selenium.keyDown(input,  "\\13");
		Assert.assertEquals(selenium.getValue(input), keySequence);
	    selenium.runScript("hotKey.disable()");
	    selenium.type(input, "");
		selenium.keyDown(input,  "\\13");
		Assert.assertEquals(selenium.getValue(input), "");
	    selenium.runScript("hotKey.enable()");
		selenium.keyDown(input,  "\\13");
		Assert.assertEquals(selenium.getValue(input), keySequence);
	}
	
	/**
	 *  Define hotKey component with timing="onregistercall" and check that it does not work
	 */
	@Test
	public void testOnRegisterCall(Template template) {
	    init(template);
	    String keySequence = "return";
		selenium.type(key, keySequence);
		waitForAjaxCompletion();
		selenium.type(timing, "onregistercall");
		waitForAjaxCompletion();
	    selenium.runScript("var hotKey = document.getElementById('" + hotKey + "').component");
		selenium.keyDown(input,  "\\13");
		Assert.assertEquals(selenium.getValue(input), "");
	    selenium.runScript("hotKey.enable()");
		selenium.keyDown(input,  "\\13");
		Assert.assertEquals(selenium.getValue(input), keySequence);
	}
	
	@Override
	public String getTestUrl() {
		return "pages/hotKey/hotKey.xhtml";
	}

}
