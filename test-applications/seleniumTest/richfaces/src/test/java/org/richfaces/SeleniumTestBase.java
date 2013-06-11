/**
 * License Agreement.
 *
 * JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */ 
package org.richfaces;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.ajax4jsf.bean.Configurator;
import org.ajax4jsf.javascript.ScriptUtils;
import org.ajax4jsf.template.Template;
import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.thoughtworks.selenium.DefaultSelenium;

/**
 * The base java class for selenium tests implementation.
 * 
 * @author Andrey Markavtsov
 * 
 */
public abstract class SeleniumTestBase implements RichSeleniumTest {

    /** Specifies the time to wait for page rendering */
    private static final Integer pageRenderTime = 10000;

    /** Specifies the time to wait for ajax processing */
    protected static final int ajaxCompletionTime = 10000;

    protected static final String serverPort = "8085";

    protected static final String WINDOW_JS_RESOLVER = "selenium.browserbot.getCurrentWindow().";
    
    private static final String DEFAULT_PAGE_URL = "/template/default.xhtml";

    protected static final String FAILURES_GROUP = "failures";
    
    /** Parent component id */
    private String parentId;

    /** The default selenium instance */
    public DefaultSelenium selenium;

    /** Host */
    public String host;

    /** Port */
    public String port;

    /** Protocol */
    public String protocol;

    private String filterPrefix;

    private SeleniumServer seleniumServer;

    private Object[][] data;
    
    private Template template;
    
    private AutoTester autoTester;
    
    public AutoTester getAutoTester(SeleniumTestBase base) {
    	if (autoTester == null) {
    		autoTester = new AutoTester(base);
    	}
    	return autoTester;
    }
    
    /**
     * Returns current template
     * */
    public Template getTemplate() {
		return template;
	}

    /** Defines the name of current j2ee application name */
    public static final String APPLICATION_NAME = "richfaces";

    public SeleniumTestBase() {
        this("http", "localhost", serverPort);
    }

    public SeleniumTestBase(String protocol, String host, String port) {
        this.host = host;
        this.port = port;
        this.protocol = protocol;
    }

    @BeforeSuite
    public void startSeleniumServer() throws Exception {
        RemoteControlConfiguration config = new RemoteControlConfiguration();
        if (getFirefoxTemplate() != null) {
        	config.setFirefoxProfileTemplate(new File(getFirefoxTemplate()));
        }
        config.setUserExtensions(new File(getClass().getClassLoader().getResource("script/selenium/user-extensions.js").toURI()));
        config.setMultiWindow(false);
        //TODO only for test
        config.setPort(16334);
        seleniumServer = new SeleniumServer(false, config);
    	seleniumServer.start();
    }

    @DataProvider(name = "templates")
    protected Object[][] templates() {
        //return new Object[][] { { Template.SIMPLE }, { Template.DATA_TABLE }, { Template.DATA_TABLE2 }, { Template.MODAL_PANEL } };
   	
    	return !useDefaultTemplates() ? getTestDependedTemplates() : Configurator.getTemplates(); 
    }

    /**
     * This method are invoked before selenium tests started
     */
    @BeforeClass
    @Parameters({"browser", "filterPrefix"})
    public void startSelenium(String browser, String filterPrefix) {
		if ("*firefox".equals(browser)) {
			String firefox = findFirefox();
			if (firefox != null) {
				browser = firefox;
			}
		}
		
	    synchronized (MUTEX) {
            this.filterPrefix = filterPrefix;
            selenium = createSeleniumClient(protocol + "://" + host + ":" + port + "/", browser);
            setFileExtensionContent();
            selenium.start();
            selenium.allowNativeXpath("false");
        }
    }

	private String findFirefox() {
		String browser = null;
		File[] libs = {new File("/usr/lib"), new File("/usr/lib64")};
		for (int i = 0; browser == null && i < libs.length; i++) {
			File lib = libs[i];
			if (lib.isDirectory()) {
				System.out.println("---->lib#" + i + ": " + lib.getPath());
				File[] firefoxDirs = lib.listFiles(new FileFilter() {
					public boolean accept(File pathname) {
						return pathname != null&& pathname.getName().startsWith("firefox")
						&& pathname.isDirectory();
					}
				});
	    		for (int j = firefoxDirs.length - 1; browser == null && j >= 0; j--) {
					System.out.println("------>firefoxDir#" + j + ": " + firefoxDirs[j].getPath());
	    			File[] firefoxes = firefoxDirs[j].listFiles(new FileFilter() {
						public boolean accept(File pathname) {
							boolean result = false;
							if (pathname != null && pathname.isFile()) {
								String name = pathname.getName();
								result = "firefox".equals(name) || "firefox-bin".equals(name);
							}
							return result;
						}
					});
	    			if (firefoxes.length > 1 && "firefox-bin".equals(firefoxes[1].getName())) {
	    				browser = "*firefox " + firefoxes[1].getPath();	    				
	    			} else if (firefoxes.length > 0){
	    				browser = "*firefox " + firefoxes[0].getPath();
	    			} 
				}
			}
		}
		return browser;
	}
    
    private void setFileExtensionContent() {
		try {
			File file = new File(getClass().getClassLoader().getResource(
					"script/selenium/user-extensions.js").toURI());
			FileInputStream stream = new FileInputStream(file);
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			byte[] buffer = new byte[4096];
			int read;
			while ((read = stream.read(buffer)) > 0) {
				b.write(buffer, 0, read);
			}
			
			String js = b.toString();
			if (js != null && js.length() > 0) {
				selenium.setExtensionJs(js);
			}
		} catch (Exception e) {

		}
	}

    @BeforeTest
    @Parameters({"loadStyleStrategy", "loadScriptStrategy", "templates"})
    protected void loadConfiguration(String loadStyleStrategy, String loadScriptStrategy, @Optional String templates) throws Exception {
    	Configurator.setLoadScriptStrategy(loadScriptStrategy);
        Configurator.setLoadStyleStrategy(loadStyleStrategy);
        Configurator.setTemplates(templates);
    }
    
    
    /**
     *  override this methods if you want use set custom templates per test class 
     * 
     */
    protected Object [][] getTestDependedTemplates() {
    	return null;
    }
    
    public boolean useDefaultTemplates() {
    	return true;
    }
    
    /**
     * This method are invoked after selenium tests completed
     */
    @AfterClass(alwaysRun=true)
    public void stopSelenium() {
        synchronized (MUTEX) {
            selenium.stop();
            selenium = null;
        }
    }
    
    protected String getFirefoxTemplate() {
    	return null;
    }

    protected void loadTemplates(String templateExpr) {
        String[] array = new String[]{};
        if(null != templateExpr) {
            array = templateExpr.split(",");
        }

        List<Object[]> list = new ArrayList<Object[]>();
        for (String string : array) {
            Object[] elem = new Object[] {Template.valueOf(string)};
            list.add(elem);
        }

        this.data = (Object[][]) list.toArray(new Object[0][0]);
    }

    @AfterSuite
    public void stopSeleniumServer() throws Exception {
        seleniumServer.stop();
    }

    /**
     * @param url
     * @param browser
     * @return
     */
    private DefaultSelenium createSeleniumClient(String url, String browser) {
        return new DefaultSelenium(host, 16334, browser, url);
    }

    private static final Object MUTEX = new Object();

    /**
     * Renders page
     */
    protected void renderPage(Template template) {
        renderPage(template, null);
    }

    /**
     * Renders page
     */
    protected void renderPage(Template template, String resetMethodName) {
        renderPage(null, template, resetMethodName);
    }

    protected void renderPage(String url, Template template, String resetMethodName) {
        String postfix = url;
        if (null == url) {
            postfix = getTestUrl();
        }
        
        selenium.open(protocol + "://" + host + ":" + port + "/" + APPLICATION_NAME + filterPrefix + DEFAULT_PAGE_URL);
        waitForPageToLoad();

        setParentId(template.getPrefix());
        runScript("loadTemplate('" + template + "', '" + resetMethodName + "');", false);
        waitForPageToLoad();

        String base = getTestBase();
        selenium.open(protocol + "://" + host + ":" + port + "/" + APPLICATION_NAME + filterPrefix + (null != base ? base : "") + postfix);
        selenium.waitForPageToLoad(String.valueOf(pageRenderTime));

//        setParentId(template.getPrefix());
//        runScript("loadTemplate('" + template + "', '" + resetMethodName + "');", false);
//        waitForPageToLoad();

        checkPageRendering(); // At the first we check if page has been
                                // rendered
        checkJSError(); // At the second we check if JS errors occurred

        reRenderForm(); // ReRender component

        checkPageRendering(); // Check all again
        checkJSError();
        
        this.template = template;
    }
    

    /**
     * Renders page
     */
    protected void renderAutoTestPage(Template template, String resetMethodName) {
       renderAutoTestPage(null, template, resetMethodName);
    }

    protected void renderAutoTestPage(String url, Template template, String resetMethodName) {
        String base = getTestBase();
        selenium.open(protocol + "://" + host + ":" + port + "/" + APPLICATION_NAME + filterPrefix + (null != base ? base : "") + (url != null ? url : getAutoTestUrl()));
        selenium.waitForPageToLoad(String.valueOf(pageRenderTime));

        setParentId(template.getPrefix());
        //runScript("loadTemplate('" + template + "');", false);
        runScript("loadTemplate('" + template + "', '" + resetMethodName + "');", false);
        waitForPageToLoad();

        checkPageRendering(); // At the first we check if page has been
                                // rendered
        checkJSError(); // At the second we check if JS errors occurred

        //reRenderForm(resetMethodName); // ReRender component
        reRenderForm(); // ReRender component

        checkPageRendering(); // Check all again
        checkJSError();
        
        this.template = template;

    }
    
    /**
     * Writes status message on client side
     *
     * @param message
     */
    public void writeStatus(String message, boolean isError) {
        message = message.replace("'", "\\'");
        StringBuffer buffer = new StringBuffer("writeStatus('");
        buffer.append(message);
        if (!isError) {
        	buffer.append("')");
        }else {
        	buffer.append("',true)");
        }
        runScript(buffer.toString());
    }
    
    /**
     * Writes status message on client side
     *
     * @param message
     */
    public void writeStatus(String message) {
    	writeStatus(message, false);
    }

    /**
     * ReRenders the component
     */
    public void reRenderForm() {
        reRenderForm(null);
    }

    /**
     * ReRenders the component
     */
    private void reRenderForm(String resetMethodName) {
        if (null != resetMethodName) {
            selenium.getEval("selenium.browserbot.getCurrentWindow().reRenderAll('" + resetMethodName + "');");
        } else {
            selenium.getEval("selenium.browserbot.getCurrentWindow().reRenderAll();");
        }
        waitForAjaxCompletion(ajaxCompletionTime);
    }

    /**
     * Performs script defined in parameter
     * 
     * @param script
     * @return
     */
    public String runScript(String script) {
        String result = selenium.getEval(WINDOW_JS_RESOLVER + script);
        checkJSError();
        return result;
    }

    /**
     * Performs script defined in parameter
     * 
     * @param script
     * @return
     */
    public String runScript(String script, boolean checkEerror) {
        String result = selenium.getEval(WINDOW_JS_RESOLVER + script);
        if (checkEerror) {
            checkJSError();
        }
        return result;
    }

    /**
     * Checks if JS error occurred. Fails test if yes. This method should be
     * invoked after each event or any thing which can be a cause of JS error.
     */
    public void checkJSError() {
        String error = selenium.getEval(WINDOW_JS_RESOLVER + "checkError();");
        if (error != null && !("null".equals(error)) && !("".equals(error))) {
            Assert.fail("Failure by the following Javascript error: " + error);
        }
    }

    /**
     * Checks if page containing component test has been rendered completely
     */
    public void checkPageRendering() {
        try {
            String t1 = getTextById("_Selenium_Test_ControlPoint1");
            String t2 = getTextById("_Selenium_Test_ControlPoint2");
            if (t1 == null || t2 == null || !"Control1".equals(t1) || !"Control2".equals(t2)) {
                Assert.fail("The page has been not rendered properlly");
            }
        } catch (Exception e) {
            Assert.fail("The page has not been rendered properly due the following error: " + e);
        }
    }

    /**
     * Waits while AJAX request will be completed
     * 
     * @param miliseconds -
     *                time to wait
     */
    public void waitForAjaxCompletion(int miliseconds) {
        selenium.waitForCondition(WINDOW_JS_RESOLVER + "done==true", String.valueOf(miliseconds));
        runScript("window.done=false");
    }

    /**
     * Waits while AJAX request will be completed
     * 
     * @param miliseconds -
     *                time to wait
     */
    public void waitForAjaxCompletion() {
        waitForAjaxCompletion(ajaxCompletionTime);
    }

    /**
     * Waits while simple request will be completed
     * 
     * @param miliseconds -
     *                time to wait
     */
    public void waitForPageToLoad(int miliseconds) {
        selenium.waitForPageToLoad(String.valueOf(miliseconds));
    }

    /**
     * Waits for condition
     * 
     * @param condition
     * @param miliseconds
     */
    public void waiteForCondition(String condition, int miliseconds) {
        selenium.waitForCondition(WINDOW_JS_RESOLVER + condition, String.valueOf(miliseconds));
        checkJSError();
    }

    /**
     * Waits while simple request will be completed
     * 
     */
    public void waitForPageToLoad() {
        selenium.waitForPageToLoad(String.valueOf(pageRenderTime));
        selenium.waitForCondition(WINDOW_JS_RESOLVER + "loaded = true;", String.valueOf(pageRenderTime));
    }

    /**
     * Asserts DOM node value equals to value defined
     * 
     * @param id -
     *                DOM element id
     * @param value -
     *                value defined
     */
    public void AssertValueEquals(String id, String value) {
        String _v = getValueById(id);
        Assert.assertEquals(_v, value);
    }
    
    /**
     * Asserts DOM node value equals to value defined
     * 
     * @param id -
     *                DOM element id
     * @param value -
     *                value defined
     * @param message = message to be displayed in failure case
     */
    public void AssertValueEquals(String id, String value, String message) {
        String _v = getValueById(id);
        Assert.assertEquals(_v, value, message);
    }

    /**
     * Asserts DOM node value does not equal to value defined
     * 
     * @param id -
     *                DOM element id
     * @param value -
     *                value defined
     */
    public void AssertValueNotEquals(String id, String value) {
        String _v = getValueById(id);
        Assert.assertNotSame(_v, value);
    }
    
    
    /**
     * Asserts DOM node value does not equal to value defined
     * 
     * @param id -
     *                DOM element id
     * @param value -
     *                value defined
     * @param message = message to be displayed in failure case
     */
    public void AssertValueNotEquals(String id, String value, String message) {
        String _v = getValueById(id);
        Assert.assertNotSame(_v, value, message);
    }


    /**
     * Asserts DOM node text equals to text defined
     *
     * @param locator -
     *                locator an element locator
     * @param value -
     *                text defined
     */
    public void AssertTextEquals(String locator, String value) {
        String _v = selenium.getText(locator);
        Assert.assertEquals(_v, value);
    }

    /**
     * Asserts DOM node text equals to text defined
     * 
     * @param locator -
     *                locator an element locator
     * @param value -
     *                text defined
     * @param message = message to be displayed in failure case
     */
    public void AssertTextEquals(String locator, String value, String message) {
        String _v = selenium.getText(locator);
        Assert.assertEquals(_v, value, message);
    }

    /**
     * Asserts DOM node text does not equal to text defined
     * 
     * @param id -
     *                DOM element id
     * @param value -
     *                text defined
     */
    public void AssertTextNotEquals(String id, String value) {
        String _v = getTextById(id);
        Assert.assertFalse(value.equals(_v));
    }
    
    /**
     * Asserts DOM node text does not equal to text defined
     * 
     * @param id -
     *                DOM element id
     * @param value -
     *                text defined
     * @param message = message to be displayed in failure case
     */
    public void AssertTextNotEquals(String id, String value, String message) {
        String _v = getTextById(id);
        Assert.assertFalse(value.equals(_v), message);
    }

    /**
     * Asserts DOM node is visible
     * 
     * @param id -
     *                DOM element id
     */
    public void AssertVisible(String id) {
        Assert.assertTrue(isVisibleById(id));
    }
    
    /**
     * Asserts DOM node is visible
     * 
     * @param id -
     *                DOM element id
     */
    public void AssertVisible(String id, String message) {
        Assert.assertTrue(isVisibleById(id), message);
    }

    /**
     * Asserts DOM node is present and visible
     * 
     * @param id -
     *                DOM element id
     */
    public void AssertPresentAndVisible(String id, String message) {
        AssertPresent(id, message);
        AssertVisible(id, message);
    }

    /**
     * Asserts DOM node is not present or not visible
     * 
     * @param id -
     *                DOM element id
     */
    public void AssertNotPresentOrNotVisible(String id, String message) {
        try {
            AssertNotPresent(id, message);
        } catch (AssertionError ae) {
            AssertNotVisible(id, message);
        }
    }

    /**
     * Asserts DOM node is not present
     * 
     * @param id -
     *                DOM element id
     */
    public void AssertNotPresent(String id) {
        Assert.assertFalse(isPresentById(id));
    }
    
    /**
     * Asserts DOM node is not present
     * 
     * @param id -
     *                DOM element id
     */
    public void AssertNotPresent(String id, String message) {
        Assert.assertFalse(isPresentById(id), message);
    }


    /**
     * Asserts DOM node is not visible
     * 
     * @param id -
     *                DOM element id
     */
    public void AssertNotVisible(String id) {
        Assert.assertFalse(isVisibleById(id));
    }
    
    /**
     * Asserts DOM node is not visible
     * 
     * @param id -
     *                DOM element id
     */
    public void AssertNotVisible(String id, String message) {
        Assert.assertFalse(isVisibleById(id), message);
    }
    
    
    /**
     * Asserts DOM node is present
     * 
     * @param id -
     *                DOM element id
     */
    public void AssertPresent(String id) {
        Assert.assertTrue(isPresentById(id));
    }
    
    /**
     * Asserts DOM node is present
     * 
     * @param id -
     *                DOM element id
     */
    public void AssertPresent(String id, String message) {
        Assert.assertTrue(isPresentById(id), message);
    }
    
    /**
     * Asserts DOM node is present
     * @param id - DOM element id
     */
    public void AssertRendered(String id) {
	if (!isPresentById(id)) {
	    Assert.fail("Component " + id + " should be rendered on page");
	}
    }
    
    /**
     * Asserts DOM node is not present
     * @param id - DOM element id
     */
    public void AssertNotRendered(String id) {
	if (isPresentById(id)) {
	    Assert.fail("Component " + id + " should not be rendered on page");
	}
    }

    /**
     * Returns element's text
     * 
     * @param id -
     *                DOM element id
     * @return
     */
    public String getTextById(String id) {
        return selenium.getText("id=" + id);
    }

    /**
     * Returns element's value
     * 
     * @param id -
     *                DOM element id
     * @return
     */
    public String getValueById(String id) {
        return selenium.getValue("id=" + id);
    }

    /**
     * Sets a new value for DOM node with specified id.
     * 
     * @param id -
     *                DOM element id
     * @param value -
     *                a new DOM element's value
     */
    public void setValueById(String id, String value) {
        runScript(String.format("document.getElementById('%1$s').value='%2$s';", id, value));
    }

    /**
     * Sets a new value for DOM node with specified name.
     *
     * @param name -
     *                DOM element name (has to be unique)
     * @param value -
     *                a new DOM element's value
     */
    public void setValueByName(String name, String value) {
        runScript(String.format("document.getElementsByName('%1$s')[0].value='%2$s';", name, value));
    }

    /**
     * Returns element's width
     * 
     * @param id -
     *                DOM element id
     * @return
     */
    public Number getWidthById(String id) {
        return selenium.getElementWidth("id=" + id);
    }

    /**
     * Returns element's height
     * 
     * @param id -
     *                DOM element id
     * @return
     */
    public Number getHeightById(String id) {
        return selenium.getElementHeight("id=" + id);
    }
    
    /**
    * Returns element's left
    * 
    * @param id -
    *                DOM element id
    * @return
    */
   public Number getLeftById(String id) {
       return selenium.getElementPositionLeft("id=" + id);
   }
   
   /**
    * Returns element's top
    * 
    * @param id -
    *                DOM element id
    * @return
    */
   public Number getTopById(String id) {
       return selenium.getElementPositionTop("id=" + id);
   }

    /**
     * Clicks on element
     * 
     * @param id -
     *                DOM element id
     * @return
     */
    public void clickById(String id) {
        selenium.click("id=" + id);
        checkJSError();
    }

    /**
     * Clicks an ajax command and wait for ajax request completion.
     * @param locator an element locator
     */
    public void clickAjaxCommandAndWait(String locator) {
        selenium.click(locator);
        waitForAjaxCompletion();
    }

    /**
     * This method should be used for click on command controls instead of
     * 'clickById' method.
     * 
     * @param commandId
     */
    public void clickCommandAndWait(String commandId) {
        selenium.click("id=" + commandId);
        waitForPageToLoad();
    }

    /**
     * Return true if element is visible
     * @deprecated replaced by <code>isVisible(String locator)</code>.
     * @param id -
     *                DOM element id
     * @return
     */
    @Deprecated
    public boolean isVisibleById(String id) {
        return selenium.isVisible("id=" + id);
    }

    /**
     * Returns true if element with given id is present.
     * @deprecated replaced by <code>isPresent(String locator)</code>.
     * @param id -
     *                DOM element id
     * @return true if element with given id is present, otherwise - false
     */
    @Deprecated
    public boolean isPresentById(String id) {
        return selenium.isElementPresent("id=" + id);
    }

    /**
     * Returns true if element with given locator is present.
     *
     * @param locator
     *                an element locator
     * @return true if element with given locator is present, otherwise - false
     */
    public boolean isPresent(String locator) {
        return selenium.isElementPresent(locator);
    }

    /**
     * Returns true if element with given locator is visible.
     *
     * @param locator
     *                an element locator
     * @return true if element with given locator is visible, otherwise - false
     */
    public boolean isVisible(String locator) {
        return selenium.isVisible(locator);
    }

    /**
     * Invokes JS method on client.
     * 
     * @param id -
     *                DOM id of component
     * @param method -
     *                string method name
     * @param parameters -
     *                parameters
     * @return
     */
    public String invokeFromComponent(String id, String method, Object parameters) {
        String _return = null;
        StringBuffer buffer = new StringBuffer();
        buffer.append("$('");
        buffer.append(id);
        buffer.append("').component.");
        buffer.append(method);
        buffer.append("(");
        buffer.append(ScriptUtils.toScript(parameters));
        buffer.append(");");
        _return = runScript(buffer.toString());
        return _return;
    }

    /**
     * Creates delay
     * 
     * @param miliSeconds
     * @throws InterruptedException
     */
    public void delay(int miliSeconds) {
        try {
            Thread.sleep(miliSeconds);
        } catch (Exception e) {

        }
    }

    /**
     * Creates pause for defined time in miliSeconds
     * 
     * @param miliSeconds
     */
    public void pause(int miliSeconds, String id) {
        StringBuffer script = new StringBuffer("pause(");
        script.append(miliSeconds);
        script.append(",'");
        script.append(id);
        script.append("');");
        runScript(script.toString());
        selenium.waitForCondition(WINDOW_JS_RESOLVER + "pauseHolder['" + id + "'] == true;", String
                .valueOf(miliSeconds + 10000));
    }

    /**
     * @return the parentId
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * @param parentId
     *                the parentId to set
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * Simulates keystroke events on the specified element, as though you typed the value key-by-key.
     * Workaround for selenium.type/selenium.typeKeys 
     * @param locator
     * @param string
     */
    @Deprecated
    public void type(String locator, String string) {
        selenium.type(locator, "");
        StringBuffer value = new StringBuffer(selenium.getValue(locator));
        char[] chars = string.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            String key = Character.toString(chars[i]);
            value.append(key);

            selenium.keyDown(locator, key);

            if(!isFF()) {
                selenium.type(locator, value.toString());
            }

            selenium.keyPress(locator, key);
            selenium.keyUp(locator, key);

        }
    }

    /**
     * This method differs from <code>type</code> method in only that <code>type</code> erases previous typing as though you are typing over it,
     * while <code>typeOn</code> doesn't (go on typing)
     * @param locator an element locator
     * @param string the value to type
     */
    @Deprecated
    public void typeOn(String locator, String string) {
        StringBuffer value = new StringBuffer(selenium.getValue(locator));
        char[] chars = string.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            String key = Character.toString(chars[i]);
            value.append(key);

            selenium.keyDown(locator, key);

            if(!isFF()) {
                selenium.type(locator, value.toString());
            }

            selenium.keyPress(locator, key);
            selenium.keyUp(locator, key);
        }
    }

    public void testOnclickEvent(String testElementId, String testElementResultId, String beforeResult, String afterResult) {
	writeStatus("Testing onclick event");
	AssertTextEquals(testElementResultId, beforeResult);
	selenium.click(testElementId);
	AssertTextEquals(testElementResultId, afterResult);
    }

    public void testOnDblclickEvent(String testElementId, String testElementResultId, String beforeResult, String afterResult) {
	writeStatus("Testing ondblclick event");
	AssertTextEquals(testElementResultId, beforeResult);
	selenium.doubleClick(testElementId);
	AssertTextEquals(testElementResultId, afterResult);
    }

    public void testOnfocusEvent(String testElementId, String testElementResultId, String beforeResult, String afterResult) {
	writeStatus("Testing onfocus event");
	AssertTextEquals(testElementResultId, beforeResult);
	selenium.focus(testElementId);
	AssertTextEquals(testElementResultId, afterResult);
    }

    public void testOnkeydownEvent(String testElementId, String testElementResultId, String beforeResult, String afterResult) {
	writeStatus("Testing onkeydown event");
	AssertTextEquals(testElementResultId, beforeResult);
	selenium.keyDown(testElementId, "1");
	AssertTextEquals(testElementResultId, afterResult);
    }

    public void testOnkeypressEvent(String testElementId, String testElementResultId, String beforeResult, String afterResult) {
	writeStatus("Testing onkeypress event");
	AssertTextEquals(testElementResultId, beforeResult);
	selenium.keyPress(testElementId, "1");
	AssertTextEquals(testElementResultId, afterResult);
    }

    public void testOnkeyupEvent(String testElementId, String testElementResultId, String beforeResult, String afterResult) {
	writeStatus("Testing onkeyup event");
	AssertTextEquals(testElementResultId, beforeResult);
	selenium.keyUp(testElementId, "1");
	AssertTextEquals(testElementResultId, afterResult);
    }

    public void testOnmousedownEvent(String testElementId, String testElementResultId, String beforeResult, String afterResult) {
	writeStatus("Testing onmousedown event");
	AssertTextEquals(testElementResultId, beforeResult);
	selenium.mouseDown(testElementId);
	AssertTextEquals(testElementResultId, afterResult);
    }

    public void testOnmousemoveEvent(String testElementId, String testElementResultId, String beforeResult, String afterResult) {
	writeStatus("Testing onmousemove event");
	AssertTextEquals(testElementResultId, beforeResult);
	selenium.mouseMove(testElementId);
	AssertTextEquals(testElementResultId, afterResult);
    }

    public void testOnmouseoutEvent(String testElementId, String testElementResultId, String beforeResult, String afterResult) {
	writeStatus("Testing onmouseout event");
	AssertTextEquals(testElementResultId, beforeResult);
	selenium.mouseOut(testElementId);
	AssertTextEquals(testElementResultId, afterResult);
    }

    public void testOnmouseoverEvent(String testElementId, String testElementResultId, String beforeResult, String afterResult) {
	writeStatus("Testing onmouseover event");
	AssertTextEquals(testElementResultId, beforeResult);
	selenium.mouseOver(testElementId);
	AssertTextEquals(testElementResultId, afterResult);
    }

    public void testOnmouseupEvent(String testElementId, String testElementResultId, String beforeResult, String afterResult) {
	writeStatus("Testing onmouseup event");
	AssertTextEquals(testElementResultId, beforeResult);
	selenium.mouseUp(testElementId);
	AssertTextEquals(testElementResultId, afterResult);
    }
    
    /**
     * Method to assert element style attribute with expected
     * @param elementId - tested element id
     * @param expectedExpression - expected style attribute
     */
    public void assertStyleAttribute(String elementId, String expectedExpression) {
        assertStyleAttribute(elementId, expectedExpression, null);
    }

    /**
     * Method to assert element style attribute with expected
     * @param elementId - tested element id
     * @param expectedExpression - expected style attribute
     * @param message    - Message will be inserted in the log after test failure
     */

    public void assertStyleAttribute(String elementId, String expectedExpression, String message) {
        String styleAttribute = null;
        try {
            styleAttribute = selenium.getAttribute("//*[@id='" + elementId + "']/@style");
        } catch (Throwable e) {
            // consume exception
        }

        if (null == styleAttribute || !styleAttribute.toLowerCase().contains(expectedExpression.toLowerCase())) {
            String failureMsg = "Element '" + elementId + "' with style '" + styleAttribute + "' doesn't contain '"
                + expectedExpression + "'";
            if (null != message && !"".equals(message.trim())) {
                failureMsg = message + ": " + failureMsg;
            }
            Assert.fail(failureMsg);
        }
    }

    public void assertStyleAttributeContains(String locator, String expectedExpression, String message) {
        assertAttributeContains(locator, "style", expectedExpression, message);
    }

    public void assertClassAttributeContains(String locator, String expectedExpression, String message) {
        assertAttributeContains(locator, "class", expectedExpression, message);
    }

    public void assertClassAttributeDoesNotContain(String locator, String expectedExpression, String message) {
        boolean contain = true;
        try {
            assertAttributeContains(locator, "class", expectedExpression, message);
        } catch (AssertionError ae) {
            contain = false;
        }
        if (contain) {
            throw new AssertionError(message);
        }
    }

    /**
     * Asserts that element attribute with given name <code>attrName</code> contains <code>expectedExpression</code>
     * @param locator id or xpath expression locating element
     * @param attrName attribute name
     * @param expectedExpression expected expression
     * @param message failure message
     */
    public void assertAttributeContains(String locator, String attrName, String expectedExpression, String message) {
        if (null == locator) {
            throw new NullPointerException("locator cannot be null");
        }

        // //div[contains(@style, "font-size: 14px") and contains(@class, "dr-pnlbar-c rich-panelbar-content")]
        String xpath = "";
        if (locator.startsWith("//")) {
            xpath = locator + "/@" + attrName;
        } else {
            xpath = String.format("//*[@id='%1$s']/@%2$s", locator, attrName);
        }

        String attrValue = null;
        try {
            attrValue = selenium.getAttribute(xpath);
        } catch (Throwable e) {
            // consume exception
        }

        if (null == attrValue || !attrValue.toLowerCase().contains(expectedExpression.toLowerCase())) {
            String failureMsg = String.format(
                    "Attribute [%1$s] = [%4$s] of element [%2$s] doesn't contain expected expression [%3$s]", attrName, locator,
                    expectedExpression, attrValue);
            if (null != message && !"".equals(message.trim())) {
                failureMsg = message + ": " + failureMsg;
            }
            Assert.fail(failureMsg);
        }
    }

    /**
     * Asserts that expected columns count equals actual one.
     * @param i the expected columns count
     * @param tableId id of table to be checked
     */
    public void assertColumnsCount(int i, String tableId) {
    	assertColumnsCount(i, tableId, "");
    }
    
    /**
     * Asserts that expected columns count equals actual one.
     * @param i the expected columns count
     * @param tableId id of table to be checked
     * @param message - error message to insert in log 
     */
    public void assertColumnsCount(int i, String tableId, String mesage) {
        StringBuffer script = new StringBuffer("document.getElementById('");
        script.append(tableId);
        script.append("').rows[0].cells.length");

        String count = runScript(script.toString());
        Assert.assertEquals(count, String.valueOf(i), mesage);
    }

    /**
     * Asserts that expected rows count equals actual one.
     * @param i the expected rows count
     * @param tableId id of table to be checked
     */
    public void assertRowsCount(int i, String tableId) {
        int count = selenium.getXpathCount("//table[@id='" + tableId + "']/tbody/tr").intValue();
        Assert.assertEquals(count, i);
    }

    /**
     * Checks whether client is FireFox
     * 
     * @return true if client is FireFox
     */
    public boolean isFF() {
        return new Boolean(selenium.getEval("navigator.userAgent.indexOf('Firefox') > -1"));
    }
    
    /**
     * Checks whether client is FireFox
     * 
     * @return true if client is FireFox
     */
    public boolean isIE() {
        return new Boolean(selenium.getEval("!!(window.attachEvent && !window.opera)"));
    }

    /**
     * Returns the url to test page to be opened by selenium
     *
     * @return
     */
    public abstract String getTestUrl();
    
    /**
     * Returns the url to auto test page to be opened by selenium
     *
     * @return
     */
    public String getAutoTestUrl() {
    	return null;
    }

    public String getTestBase() {
        return null;
    }

    /**
     * Control action that should force ajax request from the component.
     * This method should wait for ajax completion
     * This method should be overridden for auto test   
     */
    public void sendAjax() {
    }

    /**
     * Control action that should force ActionSource component to produce an action event (as a rule - a full form submit)
     * This method should wait for full submit completion
     * Used for auto testing only
     */
    public void sendAction() {
        throw new UnsupportedOperationException("This method has always to be overridden before usage");
    }

     /**
     * Control action that should force ajax request that navigate to other page.
     * This method should NOT wait for ajax completion
     * This method should be overridden for auto test   
     */
    public void navigate() {
    }
    /**
     * This call back has to move auto test component into invalid state.
     * Used for auto testing only.
     */
    public void setInternalValidationFailed() {
    }

    /**
     * This call back has to change auto test component value.
     * Used for auto testing only.
     */
    public void changeValue() {
    }

    /**
     * This call back has to bring auto test component value to the state as if a user has not provided a submitted value for the component
     * Used for auto testing only.
     */
    public void setValueEmpty() {
        throw new UnsupportedOperationException("This method has always to be overridden before usage");
    }

    /**
     * Returns the array of components' ids that are rerendering after ajax request from the component.
     * This method should be overridden for auto test   
     */
    public String [] getReRendersId() {
    	return null;
    }
    
    /**
     * Simulates mouse event for element 
     * @param id        - Element ID
     * @param eventName - Mouse event Name
     * @param x         - X mouse position
     * @param y         - Y mouse position
     * @return          - Event
     */
    public String fireMouseEvent(String id, String eventName, int x, int y, boolean ctrl) {
    	return runScript("fireMouseEvent('" + id + "','" + eventName + "', "
				+ x + "," + y + ","+ctrl+");");
    }
    
    /**
     * Check is DOM element contain defined class names  
     * @param id         - Element DOM id
     * @param classNames - Class names to check
     * @param message    - Message will be inserted in the log after test failure
     * @param isId       - Pass true if ID is string, false - if ID is expression to load appropriate DOM element  
     */
    public void assertClassNames(String id, String[] classNames, String message, boolean isId) {
    	//String clazz = runScript(((isId) ? "document.getElementById('"+id+"')" : id) + ".className");
    	String clazz = null;
    	if (isId) {
    		clazz = selenium.getAttribute("//*[@id='" + id + "']/@class");
    	}else {
    		clazz = selenium.getAttribute(id + "/@class");
    	}
    	List<String> classes  = (clazz != null) ? Arrays.asList(clazz.split(" ")) : new ArrayList<String>();
        for (String s : classNames) {
            boolean result = classes.contains(s);
            if (!result) {
                Assert.fail(message + " Element [id=" + id + "] should contain class name '" + s + "'");
            }
        }
    }

    public void assertEvents(List<String> eventsExpected) {
        StringBuilder sb = new StringBuilder();
        String delim = "";
        for (String event : eventsExpected) {
            sb.append(delim).append("'").append(event).append("'");
            delim = ",";
        }
        String json = "[" + sb.toString() + "]";
        String message = runScript("EventQueue.assert(eval(\"" + json + "\"))");
        if (null != message && message.length() > 0) {
            Assert.fail(message);
        }
        eventsExpected.clear();
    }
    
    public void assertEvent(String eventExpected) {
        String message = runScript("EventQueue.assertEvent('"+eventExpected+"')");
        if (null != message && message.length() > 0) {
            Assert.fail(message);
        }
    }
    
    public void assertEvent(String eventExpected, String m) {
        String message = runScript("EventQueue.assertEvent('"+eventExpected+"')");
        if (null != message && message.length() > 0) {
            Assert.fail(m + message);
        }
    }
    
    public Object assertEvents(String locator, List<SeleniumEvent> eventsExpected) {
    	
    	for (SeleniumEvent ev : eventsExpected) {
    		if (ev == SeleniumEvent.ONCLICK) {
    			selenium.click(locator);
    		}else if (ev == SeleniumEvent.ONDBLCLICK) {
    			selenium.doubleClick(locator);
    		} else if (ev == SeleniumEvent.ONMOUSEDOWN) {
    			selenium.mouseDown(locator);
    		}else if (ev == SeleniumEvent.ONMOUSEMOVE) {
    			selenium.mouseMove(locator);
    		}else if (ev == SeleniumEvent.ONMOUSEUP) {
    			selenium.mouseUp(locator);
    		}else if (ev == SeleniumEvent.ONMOUSEOVER) {
    			selenium.mouseOver(locator);
    		}else if (ev == SeleniumEvent.ONMOUSEOUT) {
    			selenium.mouseOut(locator);
    		}else if (ev == SeleniumEvent.ONKEYDOWN) {
    			selenium.keyDown(locator, "a");
    		}else if (ev == SeleniumEvent.ONKEYUP) {
    			selenium.keyUp(locator, "a");
    		}else if (ev == SeleniumEvent.ONKEYPRESS) {
    			selenium.keyPress(locator, "a");
    		}else if (ev == SeleniumEvent.ONFOCUS) {
    			selenium.focus(locator);
    		}
    			
    	}
    	
    	for (SeleniumEvent ev : eventsExpected) {
    		assertEvent(ev.getName());
    	}
    	
    	// Remove checking for event ordering. It may be an accidental one
    	
    	/*  
        StringBuilder sb = new StringBuilder();
        String delim = "";
        for (SeleniumEvent event : eventsExpected) {
            sb.append(delim).append("'").append(event).append("'");
            delim = ",";
        }
        String json = "[" + sb.toString() + "]";
        String message = runScript("EventQueue.assert(eval(\"" + json + "\"))");
        if (null != message && message.length() > 0) {
            Assert.fail(message);
        }
        eventsExpected.clear();
        */
        return null;
    }

    public void autoTest(Template template, String component, List<SeleniumEvent> events, Map<String , String> attributes) {
    	selenium.open(protocol + "://" + host + ":" + port + "/" + APPLICATION_NAME + filterPrefix + "/pages/_autotest/autotestsetup.xhtml");
    	waitForPageToLoad();
    	
    	runScript("setComponent('"+component+"');", false);
    	
    	List<String> assertevent = new ArrayList<String>();
    	
    	for (SeleniumEvent ev : events) {
    		if (ev != SeleniumEvent.ONDRAGANDDROP) {
    			runScript("addEvent('"+ev.getName()+"');", false);
    			assertevent.add(ev.getName());
    		}else {
    			runScript("addEvent('ondrag');addEvent('ondrop');", false);
    			assertevent.add("ondrag");
    			assertevent.add("ondrop");
    		}
    	}
    	
    	for (String attr : attributes.keySet()) {
    		runScript("addAttribute('"+attr+"', '"+attributes.get(attr)+"');", false);
    	}
    	
    	runScript("submitForm();", false);
    	waitForPageToLoad();
    	
    	runScript("loadTemplate('" + template + "');", false);
        waitForPageToLoad();
    	
    	String id = template.getPrefix() + "test";
    	for (SeleniumEvent ev : events) {
    		if (ev == SeleniumEvent.ONCLICK) {
    			clickById(id);
    		}else if (ev == SeleniumEvent.ONDBLCLICK) {
    			selenium.doubleClick(id);
    		} else if (ev == SeleniumEvent.ONMOUSEDOWN) {
    			selenium.mouseDown(id);
    		}else if (ev == SeleniumEvent.ONMOUSEMOVE) {
    			selenium.mouseMove(id);
    		}else if (ev == SeleniumEvent.ONMOUSEUP) {
    			selenium.mouseUp(id);
    		}else if (ev == SeleniumEvent.ONMOUSEOVER) {
    			selenium.mouseOver(id);
    		}else if (ev == SeleniumEvent.ONMOUSEOUT) {
    			selenium.mouseOut(id);
    		}else if (ev == SeleniumEvent.ONKEYDOWN) {
    			selenium.keyDown(id, "a");
    		}else if (ev == SeleniumEvent.ONKEYUP) {
    			selenium.keyUp(id, "a");
    		}else if (ev == SeleniumEvent.ONKEYPRESS) {
    			selenium.keyPress(id, "a");
    		}
    			
    	}
    	    
    	assertEvents(assertevent);
    }
    
    public String getElementById(String id) {
    	StringBuffer b = new StringBuffer("$('");
    	b.append(id);
    	b.append("')");
    	return b.toString();
    }
    
    public String getElementOnclickAttr(String id) {
    	StringBuffer b = new StringBuffer("document.getElementById('");
    	b.append(id);
    	b.append("').onclick");
    	String onclick = runScript(b.toString());
    	if (onclick != null && ("null".equals(onclick) || "undefined".equals(onclick))) {
    		onclick = null;
    	}
    	return onclick;
    }
    
    public String getHTMLById(String id) {
    	return runScript("document.getElementById('"+id+"')" + ".innerHTML");    	
    }
    
    public String getStyleAttributeString (String id, String attr) {
    	return runScript("getStyle('"+id+"', '"+attr+"')");
    	
    }
    
    public void assertStyleAttributes(String id, Map<String, String> styleAttrs) {
    	for (String a : styleAttrs.keySet()) {
    		String actualStyle = getStyleAttributeString(id, a);
    		Assert.assertEquals(actualStyle, styleAttrs.get(a), "Style attribute invalid. Expected ["+ a + "="+ styleAttrs.get(a)+"]. But was ["+a + "=" + actualStyle+"]");
    	}
    	
    }
    
    
}
