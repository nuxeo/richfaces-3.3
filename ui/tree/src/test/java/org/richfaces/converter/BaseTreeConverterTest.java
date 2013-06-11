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

package org.richfaces.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;

/**
 * @author Nick Belaevski
 * @since 3.3.1
 */

public class BaseTreeConverterTest extends AbstractAjax4JsfTestCase {

	private static class BaseTreeConverterImpl extends BaseTreeConverter {

		public Object getAsObject(FacesContext context, UIComponent component,
				String value) {
			return null;
		}

		public String getAsString(FacesContext context, UIComponent component,
				Object value) {
			return null;
		}
		
	};
	
	public BaseTreeConverterTest(String name) {
		super(name);
	}

	public void testAppendToKeyString() throws Exception {
		BaseTreeConverterImpl converter = new BaseTreeConverterImpl();
		StringBuilder sb = new StringBuilder();
	
		converter.appendToKeyString(sb, "_a_b_");
		
		assertEquals("__a__b__:", sb.toString());
		sb.setLength(0);
		
		converter.appendToKeyString(sb, ":a:b:");
		assertEquals("_:a_:b_::", sb.toString());
		sb.setLength(0);

		converter.appendToKeyString(sb, "test");
		assertEquals("test:", sb.toString());
		sb.setLength(0);

		converter.appendToKeyString(sb, "a\u0009b");
		assertEquals("a_x09b:", sb.toString());
		sb.setLength(0);

		converter.appendToKeyString(sb, "a\u00a0c");
		assertEquals("a_xa0c:", sb.toString());
		sb.setLength(0);

		converter.appendToKeyString(sb, "a\u037ec");
		assertEquals("a_u037ec:", sb.toString());
		sb.setLength(0);

		converter.appendToKeyString(sb, "a\ue1acd");
		assertEquals("a_ue1acd:", sb.toString());
		sb.setLength(0);
	}
	
	public void testSplitKeyString() throws Exception {
		BaseTreeConverterImpl converter = new BaseTreeConverterImpl();
		List<String> split;
		
		split = converter.splitKeyString("__a__b__:c");
		assertEquals(2, split.size());
		assertEquals("_a_b_", split.get(0));
		assertEquals("c", split.get(1));
	
		split = converter.splitKeyString("c:__a__");
		assertEquals(2, split.size());
		assertEquals("c", split.get(0));
		assertEquals("_a_", split.get(1));

		split = converter.splitKeyString("_:a_::b");
		assertEquals(2, split.size());
		assertEquals(":a:", split.get(0));
		assertEquals("b", split.get(1));

		split = converter.splitKeyString("b:_:a_:");
		assertEquals(2, split.size());
		assertEquals("b", split.get(0));
		assertEquals(":a:", split.get(1));

		split = converter.splitKeyString("_x09:c_x08ab");
		assertEquals(2, split.size());
		assertEquals("\u0009", split.get(0));
		assertEquals("c\u0008ab", split.get(1));

		split = converter.splitKeyString("c_x08ab:_x09");
		assertEquals(2, split.size());
		assertEquals("c\u0008ab", split.get(0));
		assertEquals("\u0009", split.get(1));

		split = converter.splitKeyString("_xa9:c_x98ab");
		assertEquals(2, split.size());
		assertEquals("\u00a9", split.get(0));
		assertEquals("c\u0098ab", split.get(1));

		split = converter.splitKeyString("_u0008:a_u0009bcd");
		assertEquals(2, split.size());
		assertEquals("\u0008", split.get(0));
		assertEquals("a\u0009bcd", split.get(1));

		split = converter.splitKeyString("_u0028:a_u00a9bcd");
		assertEquals(2, split.size());
		assertEquals("\u0028", split.get(0));
		assertEquals("a\u00a9bcd", split.get(1));

		split = converter.splitKeyString("_u0a28:a_u0ea9bcd");
		assertEquals(2, split.size());
		assertEquals("\u0a28", split.get(0));
		assertEquals("a\u0ea9bcd", split.get(1));

		split = converter.splitKeyString("_u9e28:a_uf3a9bcd");
		assertEquals(2, split.size());
		assertEquals("\u9e28", split.get(0));
		assertEquals("a\uf3a9bcd", split.get(1));

		split = converter.splitKeyString("");
		assertEquals(0, split.size());
		
		try {
			converter.splitKeyString("_3");
			
			fail();
		} catch (IllegalArgumentException e) {
		}

		try {
			converter.splitKeyString("_30");
			
			fail();
		} catch (IllegalArgumentException e) {
		}

		try {
			converter.splitKeyString("_x3");
			
			fail();
		} catch (IllegalArgumentException e) {
		}

		try {
			converter.splitKeyString("_x3q");
			
			fail();
		} catch (IllegalArgumentException e) {
		}

		try {
			converter.splitKeyString("_234");
			
			fail();
		} catch (IllegalArgumentException e) {
		}

		try {
			converter.splitKeyString("_f3eq");
			
			fail();
		} catch (IllegalArgumentException e) {
		}
	}
}


