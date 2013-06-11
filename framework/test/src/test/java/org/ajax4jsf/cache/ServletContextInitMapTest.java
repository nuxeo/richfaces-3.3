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

package org.ajax4jsf.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.shale.test.mock.MockServletContext;

import junit.framework.TestCase;

/**
 * Created 25.09.2008
 * @author Nick Belaevski
 * @since 3.3.0
 */

public class ServletContextInitMapTest extends TestCase {

	private ServletContextInitMap filledMap;
	
	private ServletContextInitMap emptyMap;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();

		MockServletContext filledContext = new MockServletContext();

		filledContext.addInitParameter("org.richfaces.SKIN", "blueSky");
		filledContext.addInitParameter("org.ajax4jsf.COMPRESS", "true");
		filledContext.addInitParameter("userParam", "xyz");
		
		filledMap = new ServletContextInitMap(filledContext);
		
		emptyMap = new ServletContextInitMap(new MockServletContext());
	}
	
	private boolean isValidEntryValue(Entry<String, String> entry) {
		String key = entry.getKey();
		String value = entry.getValue();
		
		if ("org.richfaces.SKIN".equals(key)) {
			return "blueSky".equals(value);
		} else if ("org.ajax4jsf.COMPRESS".equals(key)) {
			return "true".equals(value);
		} else if ("userParam".equals(key)) {
			return "xyz".equals(value);
		} else {
			return false;
		}
 	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();

		filledMap = null;
		
		emptyMap = null;
	}
	
	public void testGet() throws Exception {
		assertEquals("blueSky", filledMap.get("org.richfaces.SKIN"));
		assertEquals("true", filledMap.get("org.ajax4jsf.COMPRESS"));
		assertEquals("xyz", filledMap.get("userParam"));
	}

	public void testEntrySet() throws Exception {
		Set<Entry<String, String>> emptySet = emptyMap.entrySet();
		assertNotNull(emptySet);
		assertFalse(emptySet.iterator().hasNext());
		
		try {
			emptySet.iterator().next();
			
			fail();
		} catch (Exception e) {
			//ok
		}
		
		assertEquals(0, emptyMap.entrySet().size());
	
		Set<Entry<String, String>> filledSet = filledMap.entrySet();

		Entry<String, String> entry;
		Set<String> processedKeys = new HashSet<String>();
		
		Iterator<Entry<String, String>> iterator = filledSet.iterator();
		assertTrue(iterator.hasNext());
		entry = iterator.next();
		assertTrue(entry.toString(), isValidEntryValue(entry));
		processedKeys.add(entry.getKey());
		
		assertTrue(iterator.hasNext());
		entry = iterator.next();
		assertTrue(entry.toString(), isValidEntryValue(entry));
		processedKeys.add(entry.getKey());
		
		assertTrue(iterator.hasNext());
		entry = iterator.next();
		assertTrue(entry.toString(), isValidEntryValue(entry));
		processedKeys.add(entry.getKey());

		assertFalse(iterator.hasNext());
		try {
			iterator.next();
			
			fail();
		} catch (Exception e) {
			//ok
		}
		
		assertEquals(3, processedKeys.size());
		assertEquals(3, filledMap.entrySet().size());
	}
	
	public void testMapSize() throws Exception {
		assertEquals(0, emptyMap.size());
		assertEquals(3, filledMap.size());
	}
	
	public void testIsEmpty() throws Exception {
		assertTrue(emptyMap.isEmpty());
		assertFalse(filledMap.isEmpty());
	}
	
	public void testEntrySetIsEmpty() throws Exception {
		assertTrue(emptyMap.entrySet().isEmpty());
		assertFalse(filledMap.entrySet().isEmpty());
	}
	
	public void testRemoveEntry() throws Exception {
		//emptyMap - not applicable
		
		Iterator<Entry<String, String>> iterator = filledMap.entrySet().iterator();
		
		assertTrue(iterator.hasNext());
		
		while (iterator.hasNext()) {
			iterator.next();

			try {
				iterator.remove();
				
				fail();
			} catch (UnsupportedOperationException e) {
				//ok
			}
		}
	}
	
	public void testRemove() throws Exception {
		try {
			emptyMap.remove("key");
			
			fail();
		} catch (UnsupportedOperationException e) {
			//ok
		}

		try {
			filledMap.remove("blueSky");
			
			fail();
		} catch (UnsupportedOperationException e) {
			//ok
		}
	}
	
	public void testPut() throws Exception {
		try {
			emptyMap.put("key", "value");
			
			fail();
		} catch (UnsupportedOperationException e) {
			//ok
		}

		try {
			filledMap.put("key", "value");
			
			fail();
		} catch (UnsupportedOperationException e) {
			//ok
		}
	}
	
	public void testPutAll() throws Exception {
		try {
			Map<String,String> map = new HashMap<String, String>();
			map.put("key", "value");
			
			emptyMap.putAll(map);
			
			fail();
		} catch (UnsupportedOperationException e) {
			//ok
		}

		try {
			Map<String,String> map = new HashMap<String, String>();
			map.put("key", "value");

			filledMap.putAll(map);
			
			fail();
		} catch (UnsupportedOperationException e) {
			//ok
		}
	}
	
	public void testEntryWrite() throws Exception {
		//emptyMap - not applicable
		
		Iterator<Entry<String, String>> iterator = filledMap.entrySet().iterator();
		
		assertTrue(iterator.hasNext());

		while (iterator.hasNext()) {
			Entry<String, String> entry = iterator.next();
			try {
				entry.setValue("a");
				
				fail();
			} catch (UnsupportedOperationException e) {
				//ok
			}
		}
	}
}
