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

package org.richfaces.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;

/**
 * @author Nick Belaevski
 * @since 3.3.0
 */

public class StackingTreeModelKeyConversionTest extends AbstractAjax4JsfTestCase {

	public StackingTreeModelKeyConversionTest(String name) {
		super(name);
	}

	private StackingTreeModel model;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();

		final Map<Object, String> map = new HashMap<Object, String>();
		map.put(Long.valueOf(17), "17");
		map.put(new Object() {
			@Override
			public String toString() {
				return "abc";
			}
		}, "abc");
		map.put("9", "9");
		
		model = new StackingTreeModel(null, "varx", null);

		StackingTreeModel mapModel = new StackingTreeModel("mapsModel", "mapvar", new StackingTreeModelDataProvider() {
			public Object getData() {
				return new Map[] { 
						new HashMap(),
						map,
						new HashMap()
				};
			}
		});

		mapModel.addStackingModel(new StackingTreeModel("mapIteratorModel", "var", new StackingTreeModelDataProvider() {
			public Object getData() {
				return facesContext.getExternalContext().getRequestMap().get("mapvar");
			}
		}));
		
		model.addStackingModel(mapModel);
		model.addStackingModel(new StackingTreeModel(null, null, new StackingTreeModelDataProvider() {
			public Object getData() {
				return null;
			}
		}));
	}
	
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		model = null;
	}
	
	public void testConvertKey() throws Exception {
		ListRowKey<StackingTreeModelKey> convertedKey = (ListRowKey<StackingTreeModelKey>) model.convertToKey(facesContext, "mapsModel:1:mapIteratorModel:17", null, null);
		assertEquals(2, convertedKey.depth());

		StackingTreeModelKey key;
		
		Iterator<StackingTreeModelKey> iterator = convertedKey.iterator();
		key = iterator.next();

		assertEquals("mapsModel", key.getModelId());
		assertEquals(Integer.valueOf(1), key.getModelKey());
		
		key = iterator.next();

		assertEquals("mapIteratorModel", key.getModelId());
		assertEquals(Long.valueOf(17), key.getModelKey());

		assertFalse(iterator.hasNext());
	}

	public void testConvertKey1() throws Exception {
		ListRowKey<StackingTreeModelKey> convertedKey = (ListRowKey<StackingTreeModelKey>) model.convertToKey(facesContext, "mapsModel:1:mapIteratorModel:9", null, null);
		assertEquals(2, convertedKey.depth());

		StackingTreeModelKey key;

		Iterator<StackingTreeModelKey> iterator = convertedKey.iterator();
		key = iterator.next();
		
		assertEquals("mapsModel", key.getModelId());
		assertEquals(Integer.valueOf(1), key.getModelKey());

		key = iterator.next();

		assertEquals("mapIteratorModel", key.getModelId());
		assertEquals("9", key.getModelKey());

		assertFalse(iterator.hasNext());
	}

	public void testConvertKey2() throws Exception {
		ListRowKey<StackingTreeModelKey> convertedKey = (ListRowKey<StackingTreeModelKey>) model.convertToKey(facesContext, "mapsModel:1:mapIteratorModel:abc", null, null);
		assertEquals(2, convertedKey.depth());

		StackingTreeModelKey key;

		Iterator<StackingTreeModelKey> iterator = convertedKey.iterator();
		key = iterator.next();
		
		assertEquals("mapsModel", key.getModelId());
		assertEquals(Integer.valueOf(1), key.getModelKey());

		key = iterator.next();

		assertEquals("mapIteratorModel", key.getModelId());
		assertEquals("abc", key.getModelKey().toString());

		assertFalse(iterator.hasNext());
	}

	public void testConvertKey3() throws Exception {
		ListRowKey<StackingTreeModelKey> convertedKey = (ListRowKey<StackingTreeModelKey>) model.convertToKey(facesContext, "mapsModel:0", null, null);
		assertEquals(1, convertedKey.depth());

		StackingTreeModelKey key;

		Iterator<StackingTreeModelKey> iterator = convertedKey.iterator();
		key = iterator.next();

		assertEquals("mapsModel", key.getModelId());
		assertEquals(Integer.valueOf(0), key.getModelKey());

		assertFalse(iterator.hasNext());
	}

}
