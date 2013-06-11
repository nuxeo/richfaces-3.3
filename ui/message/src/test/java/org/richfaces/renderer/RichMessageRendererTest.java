/**
 * License Agreement.
 *
 * Rich Faces - Natural Ajax for Java Server Faces (JSF)
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */
package org.richfaces.renderer;

import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIMessage;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputText;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.component.html.HtmlRichMessage;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlLink;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author Anton Belevich
 *
 */
public class RichMessageRendererTest extends AbstractAjax4JsfTestCase {
	
	HtmlRichMessage message1 = null;
	HtmlRichMessage message2 = null;
	HtmlRichMessage message3 = null;
	HtmlRichMessage message4 = null;
	
	int ERROR = 0;
	int WARN = 1;
	int FATAL = 2;
	int INFO = 3;
	

	
	HtmlOutputText text1 = null;
	HtmlInputText input1 = null;
	
	HtmlOutputText text2 = null;
	HtmlInputText input2 = null;

	HtmlOutputText text3 = null;
	HtmlInputText input3 = null;

	HtmlOutputText text4 = null;
	HtmlInputText input4 = null;

	
		
	public RichMessageRendererTest(String name) {
		super(name);
	}
	
	public void setUp() throws Exception {
		
		super.setUp();
		
		input1 = (HtmlInputText)application.createComponent("javax.faces.HtmlInputText");
		input1.setId("input1");
		
		text1 = (HtmlOutputText)application.createComponent("javax.faces.HtmlOutputText");
		text1.setValue("Error");
			
		input2 = (HtmlInputText)application.createComponent("javax.faces.HtmlInputText");
		input2.setId("input2");

		text2 = (HtmlOutputText)application.createComponent("javax.faces.HtmlOutputText");
		text2.setValue("Warning");
		
		input3 = (HtmlInputText)application.createComponent("javax.faces.HtmlInputText");
		input3.setId("input3");
		
		text3 = (HtmlOutputText)application.createComponent("javax.faces.HtmlOutputText");
		text3.setValue("Fatal");
		
		input4 = (HtmlInputText)application.createComponent("javax.faces.HtmlInputText");
		input4.setId("input4");
		
		text4 = (HtmlOutputText)application.createComponent("javax.faces.HtmlOutputText");
		text4.setValue("Info");
		
		message1 = (HtmlRichMessage)application.createComponent("org.richfaces.component.RichMessage");
		message1.setId("message1");
		message1.setFor("input1");
		message1.setLevel("ERROR");
		message1.setShowSummary(true);
		message1.setShowDetail(true);
		message1.setTooltip(true);
		
		message1.setErrorClass("errorClass");
		message1.setErrorLabelClass("errorLabelClass");
		message1.setErrorMarkerClass("errorMarkerClass");
		
		
		message2 = (HtmlRichMessage)application.createComponent("org.richfaces.component.RichMessage");
		message2.setId("message2");
		message2.setFor("input2");
		message2.setLevel("WARN");
		message2.setShowSummary(true);
		message2.setShowDetail(true);
		message2.setTooltip(true);

		
		message2.setWarnClass("warnClass");
		message2.setWarnLabelClass("warnLabelClass");
		message2.setWarnMarkerClass("warnMarkerClass");
		
		message3 = (HtmlRichMessage)application.createComponent("org.richfaces.component.RichMessage");
		message3.setId("message3");
		message3.setFor("input3");
		message3.setLevel("FATAL");
		message3.setShowSummary(true);
		message3.setShowDetail(true);
		message3.setTooltip(true);
				
		message3.setFatalClass("fatalClass");
		message3.setFatalLabelClass("fatalLabelClass");
		message3.setFatalMarkerClass("fatalMarkerClass");
		
		message4 = (HtmlRichMessage)application.createComponent("org.richfaces.component.RichMessage");
		message4.setId("message4");
		message4.setFor("input4");
		message4.setLevel("INFO");
		message4.setShowSummary(true);
		message4.setShowDetail(true);
		message4.setTooltip(true);
		
		message4.setInfoClass("infoClass");
		message4.setInfoLabelClass("infoLabelClass");
		message4.setInfoMarkerClass("infoMarkerClass");
		
		message1.getFacets().put("errorMarker", text1);
		message2.getFacets().put("warnMarker", text2);
		message3.getFacets().put("fatalMarker", text3);
		message4.getFacets().put("infoMarker", text4);

		FacesMessage facesMessage1 = new FacesMessage();
		facesMessage1.setDetail("TEST detail");
		facesMessage1.setSummary("TEST summary");
		facesMessage1.setSeverity(FacesMessage.SEVERITY_ERROR);
		facesContext.addMessage("input1",facesMessage1);
		
		FacesMessage facesMessage2 = new FacesMessage();
		facesMessage2.setDetail("TEST detail");
		facesMessage2.setSummary("TEST summary");
		facesMessage2.setSeverity(FacesMessage.SEVERITY_WARN);
		facesContext.addMessage("input2",facesMessage2);
		
		FacesMessage facesMessage3 = new FacesMessage();
		facesMessage3.setDetail("TEST detail");
		facesMessage3.setSummary("TEST summary");
		facesMessage3.setSeverity(FacesMessage.SEVERITY_FATAL);
		facesContext.addMessage("input3",facesMessage3);
		
		FacesMessage facesMessage4 = new FacesMessage();
		facesMessage4.setDetail("TEST detail");
		facesMessage4.setSummary("TEST summary");
		facesMessage4.setSeverity(FacesMessage.SEVERITY_INFO);
		facesContext.addMessage("input4",facesMessage4);
		
		facesContext.getViewRoot().getChildren().add(input1);
		facesContext.getViewRoot().getChildren().add(message1);
		facesContext.getViewRoot().getChildren().add(input2);
		facesContext.getViewRoot().getChildren().add(message2);
		facesContext.getViewRoot().getChildren().add(input3);
		facesContext.getViewRoot().getChildren().add(message3);
		facesContext.getViewRoot().getChildren().add(input4);
		facesContext.getViewRoot().getChildren().add(message4);
	
	}
	
	public void testRenderStyle() throws Exception {
        HtmlPage page = renderView();
        assertNotNull(page);
        List <HtmlLink> links = page.getDocumentHtmlElement().getHtmlElementsByTagName("link");
        assertNotNull(links);
        HtmlElement link = (HtmlElement)links.get(0);
        assertTrue(link.getAttributeValue("href").contains("org/richfaces/renderkit/html/css/msg.css"));
    }
	
	public void testRendererMessage() throws Exception{
		
		HtmlPage page = renderView();
		assertNotNull(page);
		
		checkMockUp(page, message1, ERROR);
	    checkMockUp(page, message2, WARN);
	    checkMockUp(page, message3, FATAL);
	    checkMockUp(page, message4, INFO);
	}
	
	public void checkMockUp(HtmlPage page, UIMessage message, int type ) {
		
		HtmlElement span = page.getHtmlElementById(message.getClientId(facesContext));
		assertNotNull(span);
		assertEquals("span", span.getNodeName().toLowerCase());
		String classAttr = span.getAttributeValue("class");
	    assertTrue(classAttr.contains("rich-message"));
	    
	    switch (type) {
			case 0:
				assertTrue(checkErrorClass(classAttr));
				break;
			case 1:
				assertTrue(checkWarnClass(classAttr));
			break;
			case 2:
				assertTrue(checkFatalClass(classAttr));
				break;
			case 3:
				assertTrue(checkInfoClass(classAttr));
				break;	
		}
	    	
	    	    
	    for (Iterator <HtmlElement> childIter= span.getChildElementsIterator();childIter.hasNext();) {
	    	HtmlElement name =  childIter.next();
	    	assertNotNull(name);
	    	assertEquals(name.getNodeName().toLowerCase(), "span");
	    	classAttr = name.getAttributeValue("class");
	    	assertTrue(classAttr.contains("rich-message-label")|| classAttr.contains("rich-message-marker") );
	    	switch (type) {
		    	case 0:
					assertTrue(checkErrorClass(classAttr));
					break;
				case 1:
					assertTrue(checkWarnClass(classAttr));
					break;
				case 2:
					assertTrue(checkFatalClass(classAttr));
					break;
				case 3:
					assertTrue(checkInfoClass(classAttr));
					break;	
	     	}
	    }	
	}
	
	public boolean checkErrorClass(String classAttribute) {
		return classAttribute.contains("errorClass") ||classAttribute.contains("errorLabelClass") || classAttribute.contains("errorMarkerClass");
	}
	
	public boolean checkInfoClass(String classAttribute) {
		return  classAttribute.contains("infoClass") ||classAttribute.contains("infoLabelClass") || classAttribute.contains("infoMarkerClass");
	}
	
	public boolean checkFatalClass(String classAttribute) {
		return  classAttribute.contains("fatalClass") ||classAttribute.contains("fatalLabelClass") || classAttribute.contains("fatalMarkerClass");
	}
	
	public boolean checkWarnClass(String classAttribute) {
		return  classAttribute.contains("warnClass") ||classAttribute.contains("warnLabelClass") || classAttribute.contains("warnMarkerClass");
	}
	
	public void tearDown() throws Exception {
		super.tearDown();
	} 

}
