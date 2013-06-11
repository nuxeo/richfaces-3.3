/**
 * 
 */
package org.richfaces.test;

import static org.junit.Assert.*;

import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.SubmittableElement;

/**
 * @author asmirnov
 * 
 */
public class FacesServerTest extends AbstractFacesTest {


	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUpTest() throws Exception {
	}

	@Override
	protected void setupWebContent() {
		facesServer.addResource("/WEB-INF/faces-config.xml",
		"org/richfaces/test/faces-config.xml");
		facesServer.addResource("/hello.xhtml", "org/richfaces/test/hello.xhtml");
		facesServer.addResource("/response.xhtml", "org/richfaces/test/response.xhtml");
		facesServer.addResource("/wave.med.gif", "org/richfaces/test/wave.med.gif");
	}
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDownTest() throws Exception {
	}

	/**
	 * Test method for
	 * {@link org.richfaces.test.StagingServer#getConnection(java.net.URL)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testHelloFacelets() throws Exception {
		WebClient webClient = new LocalWebClient(facesServer);
		HtmlPage page = webClient.getPage("http://localhost/hello.jsf");
		System.out.println(page.asXml());		
		Element submitElement = page.getElementById("helloForm:submit");
		HtmlForm htmlForm = page.getFormByName("helloForm");
		htmlForm.getInputByName("helloForm:username");
		assertNotNull(htmlForm);
		HtmlInput input = htmlForm.getInputByName("helloForm:username");
		assertNotNull(input);
		input.setValueAttribute("foo");
		HtmlPage responsePage = (HtmlPage) htmlForm.submit((SubmittableElement) submitElement);
		assertNotNull(responsePage);
		System.out.println(responsePage.asXml());		
		HttpSession session = facesServer.getSession(false);
		assertNotNull(session);
		HelloBean bean = (HelloBean) session.getAttribute("HelloBean");
		assertNotNull(bean);
		assertEquals("foo", bean.getName());
		Element span = responsePage.getElementById("responseform:userLabel");
		assertNotNull(span);
		assertEquals("foo", span.getTextContent().trim());		
	}

}
