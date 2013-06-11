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
package org.richfaces.sandbox;

import javax.faces.component.UIForm;
import javax.faces.component.UIOutput;
import javax.faces.component.html.HtmlForm;

import org.ajax4jsf.event.AjaxEvent;
import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.component.UIProgressBar;

import com.gargoylesoftware.htmlunit.html.DomText;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * Unit test for Progress bar component.
 */
public class ProgressBarComponentTest extends AbstractAjax4JsfTestCase {

    /** Form component */
    private UIForm form = null;

    /** Progress bar component */
    private UIProgressBar progressBar = null;
    
    /** Child component for progres bar  */
    private UIOutput output = null;

    /**
     * TODO Description goes here.
     * 
     * @param name
     */
    public ProgressBarComponentTest(String name) {
	super(name);
	// TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#setUp()
     */
    @Override
    public void setUp() throws Exception {
	// TODO Auto-generated method stub
	super.setUp();
	form = new HtmlForm();
	form.setId("form");
	facesContext.getViewRoot().getChildren().add(form);

	progressBar = (UIProgressBar) application
		.createComponent("org.richfaces.ProgressBar");
	progressBar.setId("prgs");
	progressBar.setValue(50);
	progressBar.setInterval(1000);
	
//	MockValueExpression expression = new MockValueExpression("50%");
//	ValueExpression exp = application.getExpressionFactory()
//		.createValueExpression(facesContext.getELContext(), "#{persent}", Object.class);
	output = (UIOutput)application.createComponent("javax.faces.Output");
	output.setValue("{value}%");
//	output.setValueExpression("value", expression);
	
	progressBar.getChildren().add(output);
	
	form.getChildren().add(progressBar);

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
     */
    @Override
    public void tearDown() throws Exception {
	// TODO Auto-generated method stub
	super.tearDown();
    }

    /**
     * Method tests progress bar component
     * @throws Exception
     */
    public void testProgressBar() throws Exception {
	HtmlPage page = renderView();
	assertNotNull(page);
	
	String clientId = progressBar.getClientId(facesContext);

	HtmlElement progress = page.getHtmlElementById(clientId);
	assertNotNull(progress);
	assertEquals("div", progress.getNodeName());

	String classAttr = progress.getAttributeValue("class");
	assertTrue(classAttr.contains("rich-progress-bar"));
	
	HtmlElement node = (HtmlElement)progress.getHtmlElementById(clientId + ":remain");
	assertTrue(node.getAttributeValue("class").indexOf("rich-progress-bar-remained") != -1);
	
	node = (HtmlElement) progress.getLastChild();
	assertTrue("span".equalsIgnoreCase(node.getTagName()));
	
	node = (HtmlElement) node.getLastChild();
	assertTrue("script".equalsIgnoreCase(node.getTagName()));
	
	DomText text = (DomText) node.getFirstChild();
	assertTrue(text.getData().contains("$('" + clientId + "').component"));
	assertTrue(text.getData().contains("renderLabel"));

	assertEquals(1L, progressBar.getNumber(1));
	assertEquals(new Double(1), progressBar.getNumber("1"));
	assertEquals(0, progressBar.getNumber(null));
	
	facesContext.getExternalContext().getRequestParameterMap().put("percent", "100");
	facesContext.getExternalContext().getRequestParameterMap().put(progressBar.getClientId(facesContext), progressBar.getClientId(facesContext));
	
	progressBar.broadcast(new AjaxEvent(progressBar));
	
    }

}
