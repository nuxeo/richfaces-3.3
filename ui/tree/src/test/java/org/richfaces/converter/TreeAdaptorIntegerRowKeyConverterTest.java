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

import javax.faces.component.UIComponent;
import javax.faces.convert.Converter;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.component.UITree;
import org.richfaces.model.ListRowKey;
import org.richfaces.model.StackingTreeModelKey;

/**
 * @author Nick Belaevski
 * @since 3.3.1
 */

public class TreeAdaptorIntegerRowKeyConverterTest extends AbstractAjax4JsfTestCase {

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
	
	
	public TreeAdaptorIntegerRowKeyConverterTest(String name) {
		super(name);
	}
	
	public void testCreateConverter() throws Exception {
		Converter converter = facesContext.getApplication().createConverter(TreeAdaptorIntegerRowKeyConverter.CONVERTER_ID);
		assertNotNull(converter);
	}
	
	public void testGetAsString() throws Exception {
		Converter converter = new TreeAdaptorIntegerRowKeyConverter();
		
		Object key = new ListRowKey<StackingTreeModelKey<Integer>>(Arrays.asList(
			new StackingTreeModelKey<Integer>("list", Integer.valueOf(12)),
			new StackingTreeModelKey<Integer>("map", Integer.valueOf(456)),
			new StackingTreeModelKey<Integer>("custom", Integer.valueOf(789))
		));
		
		String string = converter.getAsString(facesContext, tree, key);
		assertEquals("list:12:map:456:custom:789:", string);
		
		string = converter.getAsString(facesContext, tree, null);
		assertNull(string);
	}
	
	public void testGetAsObject() throws Exception {
		Converter converter = new TreeAdaptorIntegerRowKeyConverter();
		ListRowKey<StackingTreeModelKey<Integer>> key = (ListRowKey<StackingTreeModelKey<Integer>>) 
			converter.getAsObject(facesContext, tree, "map:123:anotherMap:456:m:789");
		StackingTreeModelKey stackingModelKey;
		
		assertEquals(3, key.depth());

		stackingModelKey = key.get(0);
		assertEquals("map", stackingModelKey.getModelId());
		assertEquals(Integer.valueOf(123), stackingModelKey.getModelKey());
		
		stackingModelKey = key.get(1);
		assertEquals("anotherMap", stackingModelKey.getModelId());
		assertEquals(Integer.valueOf(456), stackingModelKey.getModelKey());

		stackingModelKey = key.get(2);
		assertEquals("m", stackingModelKey.getModelId());
		assertEquals(Integer.valueOf(789), stackingModelKey.getModelKey());
	}

}
