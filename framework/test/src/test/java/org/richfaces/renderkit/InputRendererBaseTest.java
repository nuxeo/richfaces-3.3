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

package org.richfaces.renderkit;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;

/**
 * @author Nick - mailto:nbelaevski@exadel.com
 * created 12.04.2007
 * 
 */
public class InputRendererBaseTest extends AbstractAjax4JsfTestCase {

	private static final String TYPE = "test.Type";
	
	private UIInput input;
	
	public InputRendererBaseTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	public void setUp() throws Exception {
		super.setUp();
	
		List children = facesContext.getViewRoot().getChildren();
		UIComponent form = application.createComponent(UIForm.COMPONENT_TYPE);
		children.add(form);
		input = (UIInput) createComponent(TYPE, UIInput.class.getName(), "test.Renderer", 
				InputRendererBase.class, null);
		form.getChildren().add(input);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	public void tearDown() throws Exception {
		super.tearDown();
	
		this.input = null;
	}

	public void testDoDecode() throws Exception {
		externalContext.addRequestParameterMap(
				input.getClientId(facesContext), 
				"12;true");
	
		assertNull(input.getSubmittedValue());
		input.decode(facesContext);
		assertEquals("12;true", input.getSubmittedValue());
		assertTrue(input.isValid());
	}

	public void testDoSkipDecode() throws Exception {
		externalContext.addRequestParameterMap(
				input.getClientId(facesContext) + ":aaa", 
				"12;true");
	
		assertNull(input.getSubmittedValue());
		input.decode(facesContext);
		assertNull(input.getSubmittedValue());
		assertTrue(input.isValid());
	}
	
	public void testGetConvertedValue() throws Exception {
		input.setConverter(new InputRendererBaseMockConverter());
		input.setSubmittedValue("12;true");

		assertTrue(input.isValid());
		input.validate(facesContext);
		
		InputRendererBaseMockConverterBean value = 
			(InputRendererBaseMockConverterBean) input.getValue();
		
		assertEquals(12, value.getFirst()); 
		assertEquals(true, value.getSecond()); 
	}
	
	public void testGetInputValue() throws Exception {
		InputRendererBaseMockConverterBean value = 
			new InputRendererBaseMockConverterBean();

		value.setFirst(44);
		value.setSecond(true);
		
		input.setValue(value);
		input.setConverter(new InputRendererBaseMockConverter());

		String strValue = new InputRendererBase().getInputValue(facesContext, input);
		assertEquals("44;true", strValue);

		input.setValue(null);
		strValue = new InputRendererBase().getInputValue(facesContext, input);
		assertEquals("", strValue);
		
		input.setValue(value);
		input.setSubmittedValue("444");
		strValue = new InputRendererBase().getInputValue(facesContext, input);
		assertEquals("444", strValue);
		
		input.setSubmittedValue(null);
		input.setValue(value);
		input.setConverter(null);
		strValue = new InputRendererBase().getInputValue(facesContext, input);
		assertEquals("true:44", strValue);

		input.setValue(null);
		input.setConverter(null);
		strValue = new InputRendererBase().getInputValue(facesContext, input);
		assertEquals("", strValue);
	}
}

class InputRendererBaseMockConverterBean {

	int first;
	boolean second;
	public int getFirst() {
		return first;
	}
	public void setFirst(int first) {
		this.first = first;
	}
	public boolean getSecond() {
		return second;
	}
	public void setSecond(boolean second) {
		this.second = second;
	}

	public String toString() {
		return second + ":" + first;
	}
}

class InputRendererBaseMockConverter implements Converter {

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	public Object getAsObject(FacesContext context, UIComponent comp, String str) {
		InputRendererBaseMockConverterBean bean = new InputRendererBaseMockConverterBean();
		String[] values = str.split(";");
		bean.setFirst(Integer.parseInt(values[0]));
		bean.setSecond(Boolean.valueOf(values[1]).booleanValue());
		return bean;
	}

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public String getAsString(FacesContext context, UIComponent comp, Object value) {
		if (value == null) {
			return null;
		}
		
		InputRendererBaseMockConverterBean bean = (InputRendererBaseMockConverterBean) value;
		return String.valueOf(bean.getFirst())+";"+String.valueOf(bean.getSecond());
	}
	
}
