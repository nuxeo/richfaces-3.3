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

import java.util.Arrays;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.component.UITree;
import org.richfaces.model.ListRowKey;

/**
 * @author Nick Belaevski
 * @since 3.3.1
 */

public class TreeRowKeyConverterTest extends AbstractAjax4JsfTestCase {

	private UIComponent tree;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		this.tree = facesContext.getApplication().createComponent(UITree.COMPONENT_TYPE);
	}
	
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		this.tree = null;
	}
	
	public TreeRowKeyConverterTest(String name) {
		super(name);
	}

	public void testCreateConverter() throws Exception {
		Converter converter = facesContext.getApplication().createConverter(TreeRowKeyConverter.CONVERTER_ID);
		assertNotNull(converter);
	}
	
	public void testGetAsString() throws Exception {
		Converter converter = new TreeRowKeyConverter();
		
		ListRowKey key = new ListRowKey(Arrays.asList("test", Long.valueOf(456), Integer.valueOf(123)));
		
		String string = converter.getAsString(facesContext, tree, key);
		assertEquals("test:456:123:", string);
	
		string = converter.getAsString(facesContext, tree, null);
		assertNull(string);
	}
	
	public void testGetAsObject() throws Exception {
		Converter converter = new TreeRowKeyConverter();
		ListRowKey<String> listRowKey = (ListRowKey<String>) converter.getAsObject(facesContext, tree, "test:test__2:test_:3");
		
		assertEquals(3, listRowKey.depth());
		assertEquals("test", listRowKey.get(0));
		assertEquals("test_2", listRowKey.get(1));
		assertEquals("test:3", listRowKey.get(2));
		
		Object convertedKey = converter.getAsObject(facesContext, tree, null);
		assertNull(convertedKey);

		convertedKey = converter.getAsObject(facesContext, tree, "");
		assertNull(convertedKey);
	}
	
	private static final class TreeRowKeyIntegerHexConverter extends TreeRowKeyConverter {
		@Override
		protected String convertKeyToString(FacesContext context,
				UIComponent component, Object value) {

			return Integer.toHexString((Integer) value);
		}
		
		@Override
		protected Object convertStringToKey(FacesContext context,
				UIComponent component, String value) {

			return Integer.parseInt(value, 16);
		}
	};
	
	public void testGetAsStringHexConversion() throws Exception {
		Converter converter = new TreeRowKeyIntegerHexConverter();

		Object key = new ListRowKey<Integer>(Arrays.asList(Integer.valueOf(0x33), Integer.valueOf(0x4512), 
			Integer.valueOf(0xfed0)));

		String string = converter.getAsString(facesContext, tree, key);
		assertEquals("33:4512:fed0:", string);
	}
	
	public void testGetAsObjectHexConversion() throws Exception {
		Converter converter = new TreeRowKeyIntegerHexConverter();

		ListRowKey<Integer> key = (ListRowKey<Integer>) converter.getAsObject(facesContext, tree, "45:678:fabc");
	
		assertEquals(3, key.depth());
		assertEquals(Integer.valueOf(0x45), key.get(0));
		assertEquals(Integer.valueOf(0x678), key.get(1));
		assertEquals(Integer.valueOf(0xfabc), key.get(2));
	}
	
	public void testThrows() throws Exception {
		Converter converter = new TreeRowKeyConverter();
		try {
			converter.getAsObject(null, tree, "model:value");
			fail();
		} catch (NullPointerException e) {
		}
		try {
			converter.getAsObject(facesContext, null, "model:value");
			fail();
		} catch (NullPointerException e) {
		}
		try {
			converter.getAsString(null, tree, new ListRowKey<String>(null, "a"));
			fail();
		} catch (NullPointerException e) {
		}
		try {
			converter.getAsString(facesContext, null, new ListRowKey<String>(null, "a"));
			fail();
		} catch (NullPointerException e) {
		}
		
		try {
			converter.getAsObject(facesContext, tree, "_x");
			fail();
		} catch (ConverterException e) {
			FacesMessage message = e.getFacesMessage();
			System.out.println(message.getSummary());
		}

		converter = new TreeRowKeyConverter() {
			@Override
			protected String convertKeyToString(FacesContext context,
					UIComponent component, Object value) {
				
				return Double.toString((Double) value);
			}
		};

		try {
			converter.getAsString(facesContext, tree, new ListRowKey<Object>(null, "abc"));
			fail();
		} catch (ConverterException e) {
			FacesMessage message = e.getFacesMessage();
			System.out.println(message.getSummary());
		}
	}
}
