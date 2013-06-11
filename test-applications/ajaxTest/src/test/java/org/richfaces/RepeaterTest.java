/**
 * 
 */
package org.richfaces;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;
import org.richfaces.test.AbstractFacesTest;
import org.richfaces.test.LocalWebClient;
import org.richfaces.test.TestException;
import org.w3c.dom.Element;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author asmirnov
 * 
 */
public class RepeaterTest extends AbstractFacesTest {

	@Override
	protected void setupWebContent() {
		super.setupWebContent();
		facesServer.addResource("/WEB-INF/faces-config.xml", "test-faces-config.xml");
	}

	// @Override
	// protected void setupSunFaces() {
	// }
	/**
	 * @throws java.lang.Exception
	 */

	@Test
	public void testHelloFacelets() throws Exception {
		WebClient webClient = new LocalWebClient(facesServer);
		HtmlPage page = webClient.getPage("http://localhost/repeater.jsf");
		page.getEnclosingWindow().getThreadManager().joinAll(10000);
		HtmlInput htmlInput = (HtmlInput) page.getElementById("ajaxForm:text");
		assertNotNull(htmlInput);
		htmlInput.type("foo");
		// System.out.println(page.asXml());
		Element element = page.getElementById("ajaxForm:out");
		assertEquals("foo", element.getTextContent().trim());
	}

}
