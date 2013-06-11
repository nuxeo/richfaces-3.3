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

package org.ajax4jsf.framework.util.javascript;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.ajax4jsf.javascript.ScriptUtils;

import junit.framework.TestCase;

/**
 * @author shura
 *
 */
public class ScriptUtilsTest extends TestCase {

	/**
	 * @author shura
	 *
	 */
	public static class Bean {
		
		int _integer;
		boolean _bool;
		Object _foo;
		
		public Bean() {
		}
		/**
		 * @param ineger
		 * @param bool
		 * @param foo
		 */
		public Bean(int ineger, boolean bool, Object foo) {
			this._integer = ineger;
			this._bool = bool;
			this._foo = foo;
		}
		/**
		 * @return the bool
		 */
		public boolean isBool() {
			return this._bool;
		}
		/**
		 * @param bool the bool to set
		 */
		public void setBool(boolean bool) {
			this._bool = bool;
		}
		/**
		 * @return the ineger
		 */
		public int getInteger() {
			return this._integer;
		}
		/**
		 * @param ineger the ineger to set
		 */
		public void setInteger(int ineger) {
			this._integer = ineger;
		}
		/**
		 * @return the foo
		 */
		public Object getFoo() {
			return this._foo;
		}
		/**
		 * @param foo the foo to set
		 */
		public void setFoo(Object foo) {
			this._foo = foo;
		}
	}

	/**
	 * @param name
	 */
	public ScriptUtilsTest(String name) {
		super(name);
	}

	/**
	 * Test method for {@link org.ajax4jsf.javascript.ScriptUtils#toScript(java.lang.Object)}.
	 */
	public void testStringToScript() {
		Object obj = "foo";
		assertEquals("'foo'", ScriptUtils.toScript(obj));
	}

	/**
	 * Test method for {@link org.ajax4jsf.javascript.ScriptUtils#toScript(java.lang.Object)}.
	 */
	public void testArrayToScript() {
		int[] obj = {1,2,3,4,5};
		assertEquals("[1,2,3,4,5] ", ScriptUtils.toScript(obj));
	}

	public void testSqlDate() {
	    java.sql.Time obj = new java.sql.Time(1);
	    assertNotNull(ScriptUtils.toScript(obj));

	    java.sql.Date obj1 = new java.sql.Date(1);
	    assertNotNull(ScriptUtils.toScript(obj1));
	}
	
	/**
	 * Test method for {@link org.ajax4jsf.javascript.ScriptUtils#toScript(java.lang.Object)}.
	 */
	public void testTwoDimentionalArrayToScript() {
		int[][] obj = {{1,2},{3,4}};
		assertEquals("[[1,2] ,[3,4] ] ", ScriptUtils.toScript(obj));
	}

	/**
	 * Test method for {@link org.ajax4jsf.javascript.ScriptUtils#toScript(java.lang.Object)}.
	 */
	public void testTwoDimentionalStringArrayToScript() {
		String[][] obj = {{"one","two"},{"three","four"}};
		assertEquals("[['one','two'] ,['three','four'] ] ", ScriptUtils.toScript(obj));
		Map<String, Object> map = new TreeMap<String, Object>();
		map.put("a", obj);
		map.put("b", "c");
		assertEquals("{'a':[['one','two'] ,['three','four'] ] ,'b':'c'} ", ScriptUtils.toScript(map));
	}
	/**
	 * Test method for {@link org.ajax4jsf.javascript.ScriptUtils#toScript(java.lang.Object)}.
	 */
	public void testListToScript() {
		List<Integer> obj = new ArrayList<Integer>();
		obj.add(new Integer(1));
		obj.add(new Integer(2));
		obj.add(new Integer(3));
		obj.add(new Integer(4));
		obj.add(new Integer(5));
		assertEquals("[1,2,3,4,5] ", ScriptUtils.toScript(obj));
	}

	/**
	 * Test method for {@link org.ajax4jsf.javascript.ScriptUtils#toScript(java.lang.Object)}.
	 */
	public void testSetToScript() {
		Set<Integer> obj = new TreeSet<Integer>();
		obj.add(new Integer(1));
		obj.add(new Integer(2));
		obj.add(new Integer(3));
		obj.add(new Integer(4));
		obj.add(new Integer(5));
		assertEquals("[1,2,3,4,5] ", ScriptUtils.toScript(obj));
	}
	/**
	 * Test method for {@link org.ajax4jsf.javascript.ScriptUtils#toScript(java.lang.Object)}.
	 */
	public void testObjectArrayToScript() {
		Bean[] obj = {new Bean(1,true,"foo"),new Bean(2,false,"bar")};
		assertEquals("[{'bool':true,'foo':'foo',\'integer\':1} ,{'bool':false,'foo':'bar','integer':2} ] ", ScriptUtils.toScript(obj));
	}

	/**
	 * Test method for {@link org.ajax4jsf.javascript.ScriptUtils#toScript(java.lang.Object)}.
	 */
	public void testObjectListToScript() {
		Bean[] array = {new Bean(1,true,"foo"),new Bean(2,false,"bar")};
		List<Bean> obj = Arrays.asList(array);
		assertEquals("[{'bool':true,'foo':'foo',\'integer\':1} ,{'bool':false,'foo':'bar','integer':2} ] ", ScriptUtils.toScript(obj));
	}
	
	/**
	 * Test method for {@link org.ajax4jsf.javascript.ScriptUtils#toScript(java.lang.Object)}.
	 */
	public void testMapToScript() {
		TreeMap<String, String> obj = new TreeMap<String, String>();
		obj.put("a", "foo");
		obj.put("b", "bar");
		obj.put("c", "baz");
		assertEquals("{'a':'foo','b':'bar','c':'baz'} ", ScriptUtils.toScript(obj));
	}
	/**
	 * Test method for {@link org.ajax4jsf.javascript.ScriptUtils#addEncodedString(java.lang.StringBuffer, java.lang.Object)}.
	 */
	public void testAddEncodedString() {
		StringBuilder buff = new StringBuilder();
		ScriptUtils.addEncodedString(buff, "foo");
		assertEquals("'foo'", buff.toString());
	}

	/**
	 * Test method for {@link org.ajax4jsf.javascript.ScriptUtils#addEncoded(java.lang.StringBuffer, java.lang.Object)}.
	 */
	public void testAddEncoded() {
		StringBuilder buff = new StringBuilder();
		ScriptUtils.addEncoded(buff, "foo\"\'");
		assertEquals("foo\\\"\\\'", buff.toString());
	}

	
}
