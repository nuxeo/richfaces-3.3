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
package org.richfaces.model.selection;

import junit.framework.TestCase;

public class SelectionRangeTest extends TestCase {

	SelectionRange range;
	
	public SelectionRangeTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		range = new SelectionRange(1,3);
	}

	protected void tearDown() throws Exception {
		range = null;
		super.tearDown();
	}

	public void testEqualsHashCode() {
		SelectionRange selectionRange = new SelectionRange(1,3);
		assertTrue(range.equals(selectionRange));
		assertEquals(selectionRange.hashCode(), range.hashCode());
		assertFalse(range.equals(new SelectionRange(1,2)));
	}

	public void testStartIndex() {
		int i = 1;
		range.setStartIndex(i);
		assertEquals(i, range.getStartIndex());
	}

	public void testEndIndex() {
		int i = 1;
		range.setEndIndex(i);
		assertEquals(i, range.getEndIndex());
	}

	public void testWithin() {
		assertTrue(range.within(2));
	}
}
