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

package org.richfaces.renderkit.html;

import junit.framework.TestCase;

/**
 * @author Maksim Kaszynski
 *
 */
public class GridScrollSettingsTest extends TestCase {

	final int count = 100;
	final int index = 12;
	final int startRow = 30;
	
	private GridScrollSettings data;
	
	/**
	 * @param name
	 */
	public GridScrollSettingsTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		data = new GridScrollSettings(index, startRow, count);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		data = null;
	}

	/**
	 * Test method for {@link org.richfaces.renderkit.html.ScrollableDataTableScrollData#getCount()}.
	 */
	public final void testGetCount() {
		assertEquals(count, data.getCount());
	}

	/**
	 * Test method for {@link org.richfaces.renderkit.html.ScrollableDataTableScrollData#setCount(int)}.
	 */
	public final void testSetCount() {
		int i = 22;
		data.setCount(i);
		assertEquals(i, data.getCount());
	}

	/**
	 * Test method for {@link org.richfaces.renderkit.html.ScrollableDataTableScrollData#getIndex()}.
	 */
	public final void testGetIndex() {
		assertEquals(index, data.getIndex());
	}

	/**
	 * Test method for {@link org.richfaces.renderkit.html.ScrollableDataTableScrollData#setIndex(int)}.
	 */
	public final void testSetIndex() {
		int i = 11112;
		data.setIndex(i);
		assertEquals(i, data.getIndex());
	}

	/**
	 * Test method for {@link org.richfaces.renderkit.html.ScrollableDataTableScrollData#getStartRow()}.
	 */
	public final void testGetStartRow() {
		assertEquals(startRow, data.getStartRow());
	}

	/**
	 * Test method for {@link org.richfaces.renderkit.html.ScrollableDataTableScrollData#setStartRow(int)}.
	 */
	public final void testSetStartRow() {
		int i = 23;
		data.setStartRow(i);
		assertEquals(i, data.getStartRow());
	}

}
