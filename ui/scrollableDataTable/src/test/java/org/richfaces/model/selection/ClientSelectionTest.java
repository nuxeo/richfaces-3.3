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

public class ClientSelectionTest extends TestCase {

	ClientSelection selection;
	
	public ClientSelectionTest(String name) {
		super(name);
	}

	public void setUp() throws Exception {
		super.setUp();
		selection = new ClientSelection();
	}

	public void tearDown() throws Exception {
		selection = null;
		super.tearDown();
	}

	public void testRange() {
		SelectionRange range = new SelectionRange(1,1);
		selection.addRange(range);
		assertEquals(1, selection.getRanges().size());
	}

	public void testIsSelected() {
		SelectionRange range = new SelectionRange(1,3);
		selection.addRange(range);
		assertTrue(selection.isSelected(2));
	}

	public void testAddIndex() {
		selection.addIndex(1);
		selection.addIndex(4);
		selection.addIndex(2);
		assertEquals(2, selection.getRanges().size());
	}

	public void testSelectionFlag() {
		selection.setSelectionFlag(ClientSelection.FLAG_RESET);
		assertEquals(ClientSelection.FLAG_RESET, selection.getSelectionFlag());
		assertTrue(selection.isReset());
		selection.setSelectionFlag(ClientSelection.FLAG_ALL);
		assertTrue(selection.isSelectAll());
	}

}
