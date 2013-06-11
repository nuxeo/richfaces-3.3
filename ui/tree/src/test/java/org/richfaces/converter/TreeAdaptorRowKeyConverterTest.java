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
import org.richfaces.model.StackingTreeModelKey;

/**
 * @author Nick Belaevski
 * @since 3.3.1
 */

public class TreeAdaptorRowKeyConverterTest extends AbstractAjax4JsfTestCase {

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
	
	
	public TreeAdaptorRowKeyConverterTest(String name) {
		super(name);
	}
	
	public void testCreateConverter() throws Exception {
		Converter converter = facesContext.getApplication().createConverter(TreeAdaptorRowKeyConverter.CONVERTER_ID);
		assertNotNull(converter);
	}
	
	public void testGetAsString() throws Exception {
		Converter converter = new TreeAdaptorRowKeyConverter();
		
		Object key = new ListRowKey<StackingTreeModelKey<Object>>(Arrays.asList(
			new StackingTreeModelKey<Object>("list", Integer.valueOf(12)),
			new StackingTreeModelKey<Object>("map", "key"),
			new StackingTreeModelKey<Object>("custom", Long.valueOf(456))
		));
		
		String string = converter.getAsString(facesContext, tree, key);
		assertEquals("list:12:map:key:custom:456:", string);
		
		string = converter.getAsString(facesContext, tree, null);
		assertNull(string);
	}
	
	public void testGetAsObject() throws Exception {
		Converter converter = new TreeAdaptorRowKeyConverter();
		ListRowKey<StackingTreeModelKey<Object>> key = (ListRowKey<StackingTreeModelKey<Object>>) 
			converter.getAsObject(facesContext, tree, "map:key1:anotherMap:itsKey:m:key_x32");
		StackingTreeModelKey stackingModelKey;
		
		assertEquals(3, key.depth());

		stackingModelKey = key.get(0);
		assertEquals("map", stackingModelKey.getModelId());
		assertEquals("key1", stackingModelKey.getModelKey());
		
		stackingModelKey = key.get(1);
		assertEquals("anotherMap", stackingModelKey.getModelId());
		assertEquals("itsKey", stackingModelKey.getModelKey());

		stackingModelKey = key.get(2);
		assertEquals("m", stackingModelKey.getModelId());
		assertEquals("key2", stackingModelKey.getModelKey());
		
		Object object = converter.getAsObject(facesContext, tree, null);
		assertNull(object);
		
		object = converter.getAsObject(facesContext, tree, "");
		assertNull(object);
	}

	public void testThrows() throws Exception {
		Converter converter = new TreeAdaptorRowKeyConverter();
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
			converter.getAsString(null, tree, new ListRowKey<StackingTreeModelKey<String>>(null, new StackingTreeModelKey<String>("a", "b")));
			fail();
		} catch (NullPointerException e) {
		}
		try {
			converter.getAsString(facesContext, null, new ListRowKey<StackingTreeModelKey<String>>(null, new StackingTreeModelKey<String>("a", "b")));
			fail();
		} catch (NullPointerException e) {
		}
		
		try {
			converter.getAsObject(facesContext, tree, "a:b:c_d");
			fail();
		} catch (ConverterException e) {
			FacesMessage message = e.getFacesMessage();
			System.out.println(message.getSummary());
		}

		converter = new TreeAdaptorRowKeyConverter() {
			@Override
			protected String convertModelKeyToString(FacesContext context,
					UIComponent component, String modelId, Object value) {
				return Double.toString((Double) value);
			}
		};
		
		try {
			converter.getAsString(facesContext, tree, new ListRowKey<StackingTreeModelKey<Object>>(null, new StackingTreeModelKey<Object>("a", "b")));
			fail();
		} catch (ConverterException e) {
			FacesMessage message = e.getFacesMessage();
			System.out.println(message.getSummary());
		}
	}
}
