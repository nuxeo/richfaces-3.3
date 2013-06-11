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

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.component.UIColorPicker;

/**
 * @author Nick Belaevski
 *
 */
public class LongColorConverterTest extends AbstractAjax4JsfTestCase {

	public LongColorConverterTest(String name) {
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
		assertNotNull(facesContext.getApplication().createConverter(LongColorConverter.CONVERTER_ID));
	}
	
	public void testConversion() throws Exception {
		LongColorConverter converter = new LongColorConverter();
		
		assertNull(converter.getAsObject(facesContext, colorPicker, null));
		assertNull(converter.getAsObject(facesContext, colorPicker, ""));
		assertEquals("", converter.getAsString(facesContext, colorPicker, null));

		assertEquals(0x6633ccL, converter.getAsObject(facesContext, colorPicker, "#6633cc"));
		assertEquals("#6633cc", converter.getAsString(facesContext, colorPicker, 0x6633ccL));
	}
}
