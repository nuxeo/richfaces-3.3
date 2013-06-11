/**
 * License Agreement.
 *
 *  JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007  Exadel, Inc.
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

package org.richfaces.component;


import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlForm;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;

import com.gargoylesoftware.htmlunit.KeyValuePair;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Unit test for simple Component.
 */
public class Paint2DTest extends AbstractAjax4JsfTestCase {

	private UIPaint2D p2d;

	private UIComponent form;
	private UIComponent command;


	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public Paint2DTest(String testName) {
		super(testName);
	}

	public void SetUp() throws Exception {

		super.setUp();

		form = new HtmlForm();
		assertNotNull(form);
		form.setId("form");
		facesContext.getViewRoot().getChildren().add(form);
		
		command = new HtmlCommandLink();
		command.setId("command");
		form.getChildren().add(command);
		
		p2d = (UIPaint2D) application.createComponent(UIPaint2D.COMPONENT_TYPE);
		assertNotNull(p2d);
		p2d.setId("p2d");
		//p2d.setBgcolor("pink");
		p2d.setHeight(300);
		p2d.setWidth(200);
		//p2d.getAttributes().put("align", "left");
		p2d.getAttributes().put("style","border:5;align:left");
		p2d.getAttributes().put("title", "title goes here");
//		p2d.setCacheable(true);
		form.getChildren().add(p2d);
//		Object obj = p2d.saveState(facesContext);
//		p2d.restoreState(facesContext, obj);
		}

	public void tearDown() throws Exception {
		form = null;
		p2d = null;
	}


	public void testComponent() throws Exception {
		SetUp();
		HtmlPage page = renderView();
		assertNotNull(page);
		//System.out.println(page.asXml());
		
		HtmlImage htmlP2D = (HtmlImage) page.getHtmlElementById(p2d.getClientId(facesContext));
		assertEquals("img", htmlP2D.getNodeName());
		
		String classAttr = htmlP2D.getAttributeValue("class");
	    assertTrue(classAttr.contains("rich-paint2D"));
	    
	    String srcAttr = htmlP2D.getAttributeValue("src");
	    assertTrue(srcAttr.contains("org.richfaces.renderkit.html.Paint2DResource"));
		
	    String str = htmlP2D.getAttributeValue("width");	    
	    assertEquals(str, "200");
	    
	    str  = htmlP2D.getAttributeValue("height");
	    assertEquals(str, "300");
	    
	    //str  = htmlP2D.getAttributeValue("bgcolor");
	    //assertEquals(str, "pink");
	    
	    //str  = htmlP2D.getAttributeValue("align");
	    //assertEquals(str, "left");	
	   
	    //str  = htmlP2D.getAttributeValue("border");
	    //assertEquals(str, "5");	
	    	   
	    str  = htmlP2D.getAttributeValue("title");
	    assertEquals(str, "title goes here");	
	}
	
	 public void testUpdate() throws Exception {
			//tests if component handles value bindings correctly
		 		SetUp();
		    	HtmlPage renderedView = renderView();
		    	
		    	int width = ((UIPaint2D)p2d).getWidth();
		    	
		    	HtmlImage htmlPaint2D = (HtmlImage) renderedView.getHtmlElementById(p2d.getClientId(facesContext));
		    	htmlPaint2D.setAttributeValue("width", "200");  	
			
		    	HtmlAnchor htmlLink = (HtmlAnchor) renderedView.getHtmlElementById(command.getClientId(facesContext));
		    	htmlLink.click();

		    	List lastParameters = this.webConnection.getLastParameters();
		    	for (Iterator iterator = lastParameters.iterator(); iterator.hasNext();) {
					KeyValuePair keyValue = (KeyValuePair) iterator.next();
					
					externalContext.addRequestParameterMap((String) keyValue.getKey(), (String) keyValue.getValue());
				}

		    	UIViewRoot root = facesContext.getViewRoot();
		    	root.processDecodes(facesContext);
		    	root.processValidators(facesContext);
		    	root.processUpdates(facesContext);
		    	root.processApplication(facesContext);
		    	
		    	renderedView = renderView();
		    	htmlPaint2D = (HtmlImage) renderedView.getHtmlElementById(p2d.getClientId(facesContext));
			assertTrue( width ==(((UIPaint2D)p2d).getWidth()) );
		    }
	
	public void testDecode() throws Exception{
		//Tests if component accepts request parameters and stores them in submittedValue(). 
		//If component is immediate, validation (possibly with conversion) should occur on that phase.
		SetUp();
		HtmlPage renderedView = renderView();
	    	HtmlAnchor htmlLink = (HtmlAnchor) renderedView.getHtmlElementById(command.getClientId(facesContext));
	    	htmlLink.click();
	    	externalContext.addRequestParameterMap(p2d.getClientId(facesContext),((UIPaint2D)p2d).getBgcolor());
	    	UIViewRoot root = facesContext.getViewRoot();
	    	root.processDecodes(facesContext);    	
		UIPaint2D uiP2d = (UIPaint2D) p2d;
		assertTrue(externalContext.getRequestParameterMap().get(p2d.getClientId(facesContext)).equals(uiP2d.getBgcolor()));
		
	    }
	/*
	
	public void testValidate()throws Exception {
		
		p2d.processValidators(facesContext);
		
	}

	public void testUpdate() throws Exception {
		p2d.processUpdates(facesContext);
	}
	*/
}
