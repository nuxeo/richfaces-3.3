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

import javax.faces.component.UICommand;
import javax.faces.component.UIForm;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlForm;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.component.html.HtmlSpacer;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class SpacerComponentTest extends AbstractAjax4JsfTestCase {
	private UICommand command = null;

	private UIForm form = null;

	// private UIComponent tree = null;
	private UISpacer spacer = null;

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public SpacerComponentTest(String testName) {
		super(testName);
	}

	public void setUp() throws Exception {
		super.setUp();

		form = new HtmlForm();

		facesContext.getViewRoot().getChildren().add(form);

		command = new HtmlCommandLink();
		command.setId("command");

		form.getChildren().add(command);
		spacer = (UISpacer) application
				.createComponent(HtmlSpacer.COMPONENT_TYPE);
		form.getChildren().add(spacer);

		spacer.setId("spacer");
		spacer.getAttributes().put("width", "30");
		spacer.getAttributes().put("height", "10");
		spacer.getAttributes().put("title", "10");
		spacer.getAttributes().put("style", "background-color: lime");

		// spacer.getAttributes().put("height", new Integer(10));

	}

	public void tearDown() throws Exception {
		super.tearDown();

		this.form = null;
		this.command = null;
		this.spacer = null;
	}

	/**
	 * Rigourous Test :-)
	 *
	 * @throws Exception
	 */
	public void testComponent() throws Exception {
		HtmlPage renderedView = renderView();
		assertNotNull(renderedView);
		//System.out.println(renderedView.asXml());

		HtmlAnchor htmlLink = (HtmlAnchor) renderedView
				.getHtmlElementById(command.getClientId(facesContext));
		htmlLink.click();

		HtmlImage htmlSpacer = (HtmlImage) renderedView
				.getHtmlElementById(spacer.getClientId(facesContext));
		assertNotNull(htmlSpacer);

		String str = htmlSpacer.getAttributeValue("width");
		assertEquals("30", str);
		str = htmlSpacer.getAttributeValue("height");
		assertEquals("10", str);
		str = htmlSpacer.getAttributeValue("style");
		assertEquals("background-color: lime", str);

		str = htmlSpacer.getClassAttribute();
		str = htmlSpacer.getAttributeValue("class");
		assertTrue(str.contains("rich-spacer"));

		str = htmlSpacer.getSrcAttribute();
		assertTrue(str.contains("images/spacer.gif"));

		//System.out.println(renderedView.getWebResponse().getContentAsString());

		/*
		 * List lastParameters = this.webConnection.getLastParameters(); for
		 * (Iterator iterator = lastParameters.iterator(); iterator.hasNext();) {
		 * KeyValuePair keyValue = (KeyValuePair) iterator.next();
		 *
		 * externalContext.addRequestParameterMap((String) keyValue.getKey(),
		 * (String) keyValue.getValue()); }

		 // System.out.println(this.webConnection.getLastParameters());


		 UIViewRoot root = facesContext.getViewRoot();
		 root.processDecodes(facesContext);
		 root.processValidators(facesContext);
		 root.processUpdates(facesContext);
		 root.processApplication(facesContext);
		 */
		// renderedView = renderView();
		// System.out.println(renderedView.getWebResponse().getContentAsString());
	}

	public void testDecode() throws Exception {

		try {
			spacer.processDecodes(facesContext);
		} catch (NullPointerException e) {
			fail();
		}
		;
	}

	public void testValidate() {

		spacer.processValidators(facesContext);

	}

	public void testUpdate() {
		spacer.processUpdates(facesContext);
	}
	/*
	 * testDecode: tests if component accepts request parameters and stores them
	 * in submittedValue(). If component is immediate, validation (possibly with
	 * conversion) should occur on that phase. testValidate: tests if component
	 * is able to handle submittedValue() correctly (convert & validate)
	 * testUpdate: tests if component handles value bindings correctly
	 */

}
