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
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputText;

import org.ajax4jsf.renderkit.RendererUtils.HTML;
import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.component.html.HtmlRichMessages;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author Anton Belevich
 *
 */
public class RichMessagesRendererTest extends AbstractAjax4JsfTestCase {

	HtmlRichMessages messages1 = null;
	HtmlRichMessages messages2 = null;
	
	HtmlInputText input1 = null;
	HtmlInputText input2 = null;
	HtmlInputText input3 = null;
	HtmlInputText input4 = null;
	
	HtmlOutputText output1 = null;
	HtmlOutputText output2 = null;
	HtmlOutputText output3 = null;
	HtmlOutputText output4 = null;
	
	
	public RichMessagesRendererTest(String name) {
		super(name);
	}
	
	public void setUp() throws Exception {
		
		super.setUp();
		
		input1 = (HtmlInputText)application.createComponent("javax.faces.HtmlInputText");
		input1.setId("input1");
		
		output1 = (HtmlOutputText)application.createComponent("javax.faces.HtmlOutputText");
		output1.setId("output1");
		output1.setValue("output1");
		
		input2 = (HtmlInputText)application.createComponent("javax.faces.HtmlInputText");
		input2.setId("input2");
		
		output2 = (HtmlOutputText)application.createComponent("javax.faces.HtmlOutputText");
		output2.setId("output2");
		output2.setValue("output2");
		
		input3 = (HtmlInputText)application.createComponent("javax.faces.HtmlInputText");
		input3.setId("input3");
		
		output3 = (HtmlOutputText)application.createComponent("javax.faces.HtmlOutputText");
		output3.setId("output3");
		output3.setValue("output3");
		
		input4 = (HtmlInputText)application.createComponent("javax.faces.HtmlInputText");
		input4.setId("input4");
		
		output4 = (HtmlOutputText)application.createComponent("javax.faces.HtmlOutputText");
		output4.setId("output4");
		output4.setValue("output4");
		
		messages1 = (HtmlRichMessages)application.createComponent("org.richfaces.component.RichMessages");
		messages1.setId("message1");
		messages1.setShowSummary(true);
		messages1.setShowDetail(true);

		messages1.setLayout("table");	
		messages1.setErrorClass("errorClass");
		messages1.setWarnClass("warnClass");
		messages1.setFatalClass("fatalClass");
		messages1.setInfoClass("infoClass");
		
		messages1.setErrorLabelClass("errorLabelClass");
		messages1.setWarnLabelClass("errorLabelClass");
		messages1.setFatalLabelClass("errorLabelClass");
		messages1.setInfoLabelClass("errorLabelClass");
		
		messages1.setErrorMarkerClass("errorMarkerClass");
		messages1.setFatalMarkerClass("fatalMarkerClass");
		messages1.setInfoMarkerClass("infoMarkerClass");
		messages1.setWarnMarkerClass("warnMarkerClass");
		
		messages1.getFacets().put("errorMarker", output1);
		messages1.getFacets().put("warnMarker", output2);
		messages1.getFacets().put("fatalMarker", output3);
		messages1.getFacets().put("infoMarker", output4);
		messages1.setTooltip(true);
		
		messages1.setTitle("TITLE");
		
		messages2 = (HtmlRichMessages)application.createComponent("org.richfaces.component.RichMessages");
		messages2.setId("message2");
		messages2.setLayout("list");
		messages2.setShowSummary(true);
		messages2.setShowDetail(true);
		messages2.setTooltip(true);
		
		messages2.setErrorClass("errorClass");
		messages2.setWarnClass("warnClass");
		messages2.setFatalClass("fatalClass");
		messages2.setInfoClass("infoClass");
		
		messages2.setErrorLabelClass("errorLabelClass");
		messages2.setWarnLabelClass("errorLabelClass");
		messages2.setFatalLabelClass("errorLabelClass");
		messages2.setInfoLabelClass("errorLabelClass");
		
		messages2.setErrorMarkerClass("errorMarkerClass");
		messages2.setFatalMarkerClass("fatalMarkerClass");
		messages2.setInfoMarkerClass("infoMarkerClass");
		messages2.setWarnMarkerClass("warnMarkerClass");
		
		messages2.getFacets().put("errorMarker", output1);
		messages2.getFacets().put("warnMarker", output2);
		messages2.getFacets().put("fatalMarker", output3);
		messages2.getFacets().put("infoMarker", output4);
		
		messages1.setTitle("TITLE2");
		
		FacesMessage facesMessage = new FacesMessage();
	
		facesMessage.setDetail("TEST detail");
		facesMessage.setSummary("TEST summary");
		facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
		facesContext.addMessage("input1",facesMessage);
		
		facesMessage.setDetail("TEST detail");
		facesMessage.setSummary("TEST summary");
		facesMessage.setSeverity(FacesMessage.SEVERITY_FATAL);
		facesContext.addMessage("input2",facesMessage);
		
		facesMessage.setDetail("TEST detail");
		facesMessage.setSummary("TEST summary");
		facesMessage.setSeverity(FacesMessage.SEVERITY_INFO);
		facesContext.addMessage("input3",facesMessage);
		
		facesMessage.setDetail("TEST detail");
		facesMessage.setSummary("TEST summary");
		facesMessage.setSeverity(FacesMessage.SEVERITY_WARN);
		facesContext.addMessage("input4",facesMessage);
				
		facesContext.getViewRoot().getChildren().add(input1);
		facesContext.getViewRoot().getChildren().add(input2);
		facesContext.getViewRoot().getChildren().add(input3);
		facesContext.getViewRoot().getChildren().add(input4);
		facesContext.getViewRoot().getChildren().add(messages1);
		facesContext.getViewRoot().getChildren().add(messages2);
	
	}
	
	public void tearDown() throws Exception {
		super.tearDown();
		messages1 = null;
		messages2 = null;
	}
	
	public void testRenderStyle() throws Exception {
        HtmlPage page = renderView();
        assertNotNull(page);
        List <HtmlElement> links = page.getDocumentHtmlElement().getHtmlElementsByTagName(HTML.LINK_ELEMENT);
        assertNotNull(links);
        boolean found = false;
        for (HtmlElement link : links) {
        	if (link.getAttributeValue(HTML.HREF_ATTR).contains("org/richfaces/renderkit/html/css/msgs.css")) {
        		found = true;
        		break;
        	}
		}
        assertTrue(found);
    }
	
	public void testRendererMessage() throws Exception{
		HtmlPage page = renderView();
		assertNotNull(page);
		
		HtmlElement table = page.getHtmlElementById(messages1.getClientId(facesContext));
		HtmlElement dl = page.getHtmlElementById(messages2.getClientId(facesContext));
		
		assertNotNull(table);
		assertNotNull(dl);
		
		assertEquals(HTML.TABLE_ELEMENT, table.getNodeName());
		assertEquals(HTML.DL_ELEMENT, dl.getNodeName());
		
		String classAttr = table.getAttributeValue(HTML.class_ATTRIBUTE);
	    assertTrue(classAttr.contains("rich-message"));
	    
		classAttr = dl.getAttributeValue(HTML.class_ATTRIBUTE);
		assertTrue(classAttr.contains("rich-message"));
		
		// test rendering component mockup for list layout
		
		for (Iterator<HtmlElement> dtIter= dl.getChildElementsIterator(); dtIter.hasNext();) {
			
			HtmlElement dt = dtIter.next();
	    	assertNotNull(dt);
	    	assertEquals(dt.getNodeName().toLowerCase(), HTML.DT_ELEMENT);
	    	classAttr = dt.getAttributeValue(HTML.class_ATTRIBUTE);
			assertTrue(classAttr.contains("errorClass")||classAttr.contains("fatalClass") 
					   ||classAttr.contains("warnClass") ||classAttr.contains("infoClass"));
			
			Iterator <HtmlElement> spanIter= dt.getChildElementsIterator();
			for (; spanIter.hasNext();) {
				
				HtmlElement span = (HtmlElement) spanIter.next();
		    	assertNotNull(span);
		    	assertEquals(HTML.SPAN_ELEM, span.getNodeName());
		    	
		    	classAttr = span.getAttributeValue(HTML.class_ATTRIBUTE);
		    	
		    	assertTrue(classAttr.contains("rich-messages-label") || classAttr.contains("rich-messages-marker"));
		    	
		    	assertTrue(classAttr.contains("errorLabelClass") || classAttr.contains("infoLabelClass") 
		    			   || classAttr.contains("fatalLabelClass") || classAttr.contains("warnLabelClass")
		    			   || classAttr.contains("errorMarkerClass") || classAttr.contains("fatalMarkerClass") 
		    			   || classAttr.contains("infoMarkerClass") || classAttr.contains("warnMarkerClass"));
			}
		}
		
		// test rendering component mockup for table layout
		HtmlElement tbody = (HtmlElement)table.getFirstDomChild();
		
		assertNotNull(tbody);
		assertEquals(tbody.getNodeName().toLowerCase(), "tbody");
		
		
	
		for (Iterator<HtmlElement> trIter = tbody.getChildIterator();trIter.hasNext();) {
		
			HtmlElement tr = trIter.next();
			
			assertNotNull(tr);
	    	assertEquals(tr.getNodeName().toLowerCase(), HTML.TR_ELEMENT);
	    	
			
			for (Iterator<HtmlElement> tdIter = tr.getChildIterator();tdIter.hasNext();) {
				
				HtmlElement td = tdIter.next();
				assertNotNull(td);
		    	assertEquals(td.getNodeName().toLowerCase(), HTML.td_ELEM);
		    	
		    	if(td.getAttributeValue(HTML.class_ATTRIBUTE)!= null){
		    		classAttr = td.getAttributeValue(HTML.class_ATTRIBUTE);
		    		
		    		assertTrue(classAttr.contains("errorClass")||classAttr.contains("fatalClass") 
							   ||classAttr.contains("warnClass") ||classAttr.contains("infoClass"));
		    	}
		    	
		    	HtmlElement elem = (HtmlElement)td.getFirstDomChild();
		    	assertNotNull(elem);
		    	
		    	if(elem.getNodeName().equals(HTML.SPAN_ELEM)){
		    		classAttr = tr.getAttributeValue(HTML.class_ATTRIBUTE);
		    		
			    	String title = elem.getAttributeValue(HTML.title_ATTRIBUTE);
			    	assertEquals("TITLE2", title);	
		    	}
			}
		}
	}
}
