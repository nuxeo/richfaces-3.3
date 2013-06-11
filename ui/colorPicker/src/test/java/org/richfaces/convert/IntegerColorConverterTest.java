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

package org.richfaces.convert;

import javax.faces.convert.ConverterException;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.component.UIColorPicker;

/**
 * @author Nick Belaevski
 *
 */
public class IntegerColorConverterTest extends AbstractAjax4JsfTestCase {

	public IntegerColorConverterTest(String name) {
		super(name);
	}

	private UIColorPicker colorPicker;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();

		colorPicker = (UIColorPicker) application.createComponent(UIColorPicker.COMPONENT_TYPE);
	}
	
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		colorPicker = null;
	}
	
	public void testConverterRegistration() throws Exception {
		assertNotNull(facesContext.getApplication().createConverter(IntegerColorConverter.CONVERTER_ID));
	}
	
	public void testGetAsString() throws Exception {
		IntegerColorConverter converter = new IntegerColorConverter();
		
		assertEquals("", converter.getAsString(facesContext, colorPicker, null));
		
		assertEquals("#ef0023", converter.getAsString(facesContext, colorPicker, 0xef0023));
		assertEquals("#000000", converter.getAsString(facesContext, colorPicker, 0x0));
		assertEquals("#012345", converter.getAsString(facesContext, colorPicker, 0x012345));
		assertEquals("#6633cc", converter.getAsString(facesContext, colorPicker, 0x6633cc));
	}
	
	public void testGetAsStringRGB() throws Exception {
		colorPicker.setColorMode(UIColorPicker.COLOR_MODE_RGB);
		IntegerColorConverter converter = new IntegerColorConverter();
		
		assertEquals("", converter.getAsString(facesContext, colorPicker, null));
		
		assertEquals("rgb(239, 0, 35)", converter.getAsString(facesContext, colorPicker, 0xef0023));
		assertEquals("rgb(0, 0, 0)", converter.getAsString(facesContext, colorPicker, 0x0));
		assertEquals("rgb(255, 255, 255)", converter.getAsString(facesContext, colorPicker, 0xffffff));
		assertEquals("rgb(1, 35, 69)", converter.getAsString(facesContext, colorPicker, 0x012345));
		assertEquals("rgb(102, 51, 204)", converter.getAsString(facesContext, colorPicker, 0x6633cc));
	}
	
	public void testGetAsObject() throws Exception {
		IntegerColorConverter converter = new IntegerColorConverter();
		
		assertNull(converter.getAsObject(facesContext, colorPicker, null));
		assertNull(converter.getAsObject(facesContext, colorPicker, ""));

		assertEquals(0x0, converter.getAsObject(facesContext, colorPicker, "#000000"));
		assertEquals(0x6633cc, converter.getAsObject(facesContext, colorPicker, "#6633cc"));
		assertEquals(0x123456, converter.getAsObject(facesContext, colorPicker, "#123456"));
		assertEquals(0x0fec, converter.getAsObject(facesContext, colorPicker, "#000fec"));
	}
	
	public void testGetAsObjectRGB() throws Exception {
		colorPicker.setColorMode(UIColorPicker.COLOR_MODE_RGB);
		IntegerColorConverter converter = new IntegerColorConverter();
		
		assertNull(converter.getAsObject(facesContext, colorPicker, null));
		assertNull(converter.getAsObject(facesContext, colorPicker, ""));
		
		assertEquals(0xef0023, converter.getAsObject(facesContext, colorPicker, "rgb(239, 0, 35)"));
		assertEquals(0x0, converter.getAsObject(facesContext, colorPicker, "rgb(0, 0, 0)"));
		assertEquals(0xffffff, converter.getAsObject(facesContext, colorPicker, "rgb(255, 255, 255)"));
		assertEquals(0x012345, converter.getAsObject(facesContext, colorPicker, "rgb(1, 35, 69)"));
		assertEquals(0x6633cc, converter.getAsObject(facesContext, colorPicker, "rgb(102, 51, 204)"));
	}
	
	public void testConversionErrorsHex() throws Exception {
		IntegerColorConverter converter = new IntegerColorConverter();

		try {
			converter.getAsObject(facesContext, colorPicker, "#12sq34");
			
			fail();
		} catch (ConverterException e) {
		}
	}

	public void testConversionErrorsRGB() throws Exception {
		colorPicker.setColorMode(UIColorPicker.COLOR_MODE_RGB);
		IntegerColorConverter converter = new IntegerColorConverter();
	
		try {
			converter.getAsObject(facesContext, colorPicker, "rgb(1, 2)");
			
			fail();
		} catch (ConverterException e) {
		}
	}
}
