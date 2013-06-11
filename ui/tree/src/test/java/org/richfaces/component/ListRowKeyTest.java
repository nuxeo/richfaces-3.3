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

package org.richfaces.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.collections.iterators.ArrayIterator;
import org.richfaces.model.ListRowKey;
import org.richfaces.model.TreeRowKey;

/**
 * @author Nick Belaevski - nbelaevski@exadel.com
 * created 09.04.2007
 * 
 */
public class ListRowKeyTest extends TestCase {

	public void testConstructors() {
		ListRowKey<Long> key = new ListRowKey<Long>();
		
		assertEquals(0, key.depth());
		assertEquals(0, key.getPath().length());

		List<Long> list = new LinkedList<Long>();
		list.add(new Long(2));
		list.add(new Long(4));

		key = new ListRowKey<Long>(list);
	
		assertEquals(2, key.depth());
		Iterator<Long> iterator = key.iterator();
		assertTrue(iterator.hasNext());
		assertEquals(new Long(2), iterator.next());

		assertTrue(iterator.hasNext());
		assertEquals(new Long(4), iterator.next());
		
		assertFalse(iterator.hasNext());
	
		key = new ListRowKey<Long>(null, new Long(5));
		assertEquals(1, key.depth());
		iterator = key.iterator();
		assertTrue(iterator.hasNext());
		assertEquals(new Long(5), iterator.next());

		assertFalse(iterator.hasNext());
		
		key = new ListRowKey<Long>(new ListRowKey<Long>(null, new Long(6)), new Long(7));
		assertEquals(2, key.depth());
		iterator = key.iterator();
		assertTrue(iterator.hasNext());
		assertEquals(new Long(6), iterator.next());

		assertTrue(iterator.hasNext());
		assertEquals(new Long(7), iterator.next());
		
		assertFalse(iterator.hasNext());

		key = new ListRowKey<Long>(key);
		assertEquals(2, key.depth());
		iterator = key.iterator();
		assertTrue(iterator.hasNext());
		assertEquals(new Long(6), iterator.next());

		assertTrue(iterator.hasNext());
		assertEquals(new Long(7), iterator.next());
		
		assertFalse(iterator.hasNext());
	}
	
	public void testObjectMethods() {
		String[] data = new String[] {
			"test1", "test:2", "test3"	
		};
		
		ListRowKey<String> key1 = new ListRowKey<String>(Arrays.asList(data));
		
		ListRowKey<String> key2 = new ListRowKey<String>();
		for (int i = 0; i < data.length; i++) {
			String string = data[i];
		
			key2 = new ListRowKey<String>(key2, string);
		}
		
		assertTrue(key1.hashCode() == key2.hashCode());
		assertTrue(key1.equals(key2));
		assertTrue(key1.toString().equals(key2.toString()));
	}

	public void testEqualsAndHash() {
		List<Object> keyBase = new ArrayList<Object>();
		keyBase.add(new Long(12));
		keyBase.add("string");
		
		List<Object> keyBase1 = new ArrayList<Object>();
		keyBase1.add(keyBase);
		keyBase1.add(new Double(23.56));
		keyBase1.add("moreStrings");
		
		List<Object> keyBase2 = new ArrayList<Object>();
		keyBase2.add(keyBase);
		keyBase2.add(new Double(23.56));
		keyBase2.add("moreStrings");
		
		ListRowKey<Object> rowKey = new ListRowKey<Object>(keyBase);
		ListRowKey<Object> rowKey1 = new ListRowKey<Object>(keyBase1);
		ListRowKey<Object> rowKey2 = new ListRowKey<Object>(keyBase2);
		
		assertTrue(rowKey.equals(rowKey));
		assertTrue(rowKey1.equals(rowKey1));
		assertTrue(rowKey2.equals(rowKey2));

		assertTrue(rowKey1.equals(rowKey2));
		assertTrue(rowKey2.equals(rowKey1));

		assertFalse(rowKey.equals(rowKey1));
		assertFalse(rowKey.equals(rowKey2));
		assertFalse(rowKey1.equals(rowKey));
		assertFalse(rowKey2.equals(rowKey));

		assertFalse(rowKey.equals(null));
		assertFalse(rowKey1.equals(null));
		assertFalse(rowKey2.equals(null));

		TreeRowKey<Object> treeKey = new TreeRowKey<Object>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 3323949145821721122L;

			public int depth() {
				throw new UnsupportedOperationException();
			}

			public int getCommonPathLength(TreeRowKey<Object> otherRowKey) {
				throw new UnsupportedOperationException();
			}

			public String getPath() {
				throw new UnsupportedOperationException();
			}

			public Iterator<Object> getSubPathIterator(int fromIndex) {
				throw new UnsupportedOperationException();
			}

			public Iterator<Object> iterator() {
				throw new UnsupportedOperationException();
			}

			@Override
			public TreeRowKey<Object> getParentKey() {
				throw new UnsupportedOperationException();
			}
			
		};		

		assertFalse(rowKey.equals(treeKey));
		assertFalse(rowKey1.equals(treeKey));
		assertFalse(rowKey2.equals(rowKey));
		
		assertTrue(rowKey1.hashCode() == rowKey2.hashCode());

		assertFalse(rowKey.hashCode() == rowKey1.hashCode());
		assertFalse(rowKey.hashCode() == rowKey2.hashCode());
	}

	public void testSubPathIterator() throws Exception {
		List<Object> keyBase = new ArrayList<Object>();
		keyBase.add(new Long(13));
		keyBase.add("string");
		
		ListRowKey<Object> listRowKey = new ListRowKey<Object>(keyBase);
		Iterator<Object> iterator = listRowKey.getSubPathIterator(0);
		assertTrue(iterator.hasNext());
		assertEquals(new Long(13), iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals("string", iterator.next());
		assertFalse(iterator.hasNext());
		
		iterator = listRowKey.getSubPathIterator(1);
		assertTrue(iterator.hasNext());
		assertEquals("string", iterator.next());
		assertFalse(iterator.hasNext());

		iterator = listRowKey.getSubPathIterator(2);
		assertFalse(iterator.hasNext());
	}

	public void testIsSubkey() throws Exception {
		List<Object> keyBase = new ArrayList<Object>();
		keyBase.add(new Long(12));

		ListRowKey<Object> listRowKey = new ListRowKey<Object>(keyBase);
		
		keyBase.add("string");
		
		ListRowKey<Object> listRowKey1 = new ListRowKey<Object>(keyBase);
	
		assertTrue(new ListRowKey<Object>().isSubKey(listRowKey1));
		assertTrue(new ListRowKey<Object>().isSubKey(listRowKey));
		assertFalse(listRowKey.isSubKey(new ListRowKey<Object>()));
		assertFalse(listRowKey1.isSubKey(new ListRowKey<Object>()));

		assertTrue(listRowKey.isSubKey(listRowKey1));
		assertFalse(listRowKey1.isSubKey(listRowKey));

		assertTrue(listRowKey.isSubKey(listRowKey));
		assertTrue(listRowKey1.isSubKey(listRowKey1));

		assertFalse(listRowKey.isSubKey(null));
		assertFalse(listRowKey1.isSubKey(null));
	
		TreeRowKey<Object> treeRowKey = new TreeRowKey<Object>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -6975483562771738488L;

			private Object[] data = new Object[] {
				new Long(12),
				"string",
				new Double(3.14159265)
			};
			
			public int depth() {
				return 3;
			}

			public int getCommonPathLength(TreeRowKey<Object> otherRowKey) {
				throw new UnsupportedOperationException();
			}

			public String getPath() {
				return "12:string:3.14159265";
			}

			public Iterator<Object> getSubPathIterator(int fromIndex) {
				throw new UnsupportedOperationException();
			}

			public Iterator<Object> iterator() {
				return new ArrayIterator(data);
			}

			@Override
			public TreeRowKey<Object> getParentKey() {
				throw new UnsupportedOperationException();
			}
			
		};

		assertTrue(listRowKey.isSubKey(treeRowKey));
		assertTrue(listRowKey1.isSubKey(treeRowKey));

		assertFalse(treeRowKey.isSubKey(listRowKey));
		assertFalse(treeRowKey.isSubKey(listRowKey));
	}
}
