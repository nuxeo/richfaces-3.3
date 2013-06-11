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
package org.richfaces.component;

import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.component.UIMessages;
import javax.faces.component.html.HtmlForm;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class GraphValidatorComponentTest extends AbstractAjax4JsfTestCase {

	UIForm form = null;
	UIGraphValidator validator = null;
	UIInput input = null;
	UIMessages messages = null;

	public GraphValidatorComponentTest(String name) {
		super(name);
	}

	public void setUp() throws Exception {
		super.setUp();
		form = new HtmlForm();
		form.setId("form");
		facesContext.getViewRoot().getChildren().add(form);

		validator = (UIGraphValidator) application
				.createComponent(UIGraphValidator.COMPONENT_TYPE);
		validator.setId("validator");
		input = (UIInput) application.createComponent(UIInput.COMPONENT_TYPE);
		input.setId("input");
		validator.getChildren().add(input);
		form.getChildren().add(validator);
		messages = (UIMessages) application
				.createComponent(UIMessages.COMPONENT_TYPE);
		messages.setId("messages");
		form.getChildren().add(messages);
	}

	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void testValidator() throws Exception {

		HtmlPage page = renderView();
		assertNotNull(page);
	}

}
