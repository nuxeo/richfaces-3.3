package org.richfaces.testng;

import org.richfaces.SeleniumTestBase;
import org.testng.annotations.Test;

public class SimpleTest extends SeleniumTestBase {

	@Test
	public void testXHTML() {
		renderPage();

	}

	@Test
	public void testJSP() {
		selenium.open("http://localhost:" + serverPort + "/" + APPLICATION_NAME + "/faces/NEKO/pages/examples/home.jsp");
		waitForPageToLoad();

	}

	@Override
	public String getTestUrl() {
		return "pages/examples/home.xhtml";
	}

}
