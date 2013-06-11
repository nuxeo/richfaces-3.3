/**
 * License Agreement.
 *
 *  JBoss RichFaces 3.0 - Ajax4jsf Component Library
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

package org.richfaces.model.impl.expressive;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.richfaces.model.SortField;
import org.richfaces.model.impl.expressive.JavaBeanWrapper;
import org.richfaces.model.impl.expressive.WrappedBeanComparator;

/**
 * @author Maksim Kaszynski
 *
 */
public class WrappedBeanComparatorTest extends TestCase {

	private SortField[] sortFields;
	private WrappedBeanComparator comparator;
	
	private static final int [][] testData = {{0,0}, {0,1}, {1, 0}, {1, 1} };
	
	private JavaBeanWrapper [] testWrapers;
	
	public WrappedBeanComparatorTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		sortFields = new SortField[2];
		sortFields[0] = new SortField("a", Boolean.FALSE);
		sortFields[1] = new SortField("b", Boolean.TRUE);

		
		comparator = new WrappedBeanComparator(sortFields);
		
		testWrapers = new JavaBeanWrapper[testData.length];
		for(int i = 0; i < testData.length; i++) {
			testWrapers[i] = w(testData[i]);
		}
		
	}

	private JavaBeanWrapper w(int [] ints) {
		Map m = new HashMap();
		m.put("a", new Integer(ints[0]));
		m.put("b", new Integer(ints[1]));
		
		return new JavaBeanWrapper(m, m);
	}
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		sortFields = null;
		comparator = null;
		testWrapers = null;
	}

	/**
	 * Test method for {@link org.richfaces.model.impl.expressive.WrappedBeanComparator#compare(java.lang.Object, java.lang.Object)}.
	 */
	public final void testCompare() {
		
		assertTrue(comparator.compare(w(new int[] {1, 0}), w(new int [] {1, 1})) < 0);
		assertTrue(comparator.compare(w(new int[] {1, 1}), w(new int [] {0, 0})) < 0);
		assertTrue(comparator.compare(w(new int[] {0, 0}), w(new int [] {0, 1})) < 0);
		assertTrue(comparator.compare(w(new int[] {1, 0}), w(new int [] {1, 0})) == 0);
		//assertTrue(comparator.compare(w(new int[] {1, 0}), w(new int [] {1, 1})) < 0);
		//assertTrue(comparator.compare(w(new int[] {1, 0}), w(new int [] {1, 1})) < 0);
		
	}

}
