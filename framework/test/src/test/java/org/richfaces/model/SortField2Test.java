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
package org.richfaces.model;

import javax.el.ValueExpression;

import org.ajax4jsf.tests.MockValueExpression;

import junit.framework.TestCase;

/**
 * @author Konstantin Mishin
 *
 */
public class SortField2Test extends TestCase {

	private SortField2 field;
	private ValueExpression expression;
	private Ordering ordering;
	/**
	 * @param name
	 */
	public SortField2Test(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		expression = new MockValueExpression(null);
		ordering = Ordering.ASCENDING;
		field = new SortField2(expression, ordering);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		field = null;
		ordering = null;
		expression = null;
		super.tearDown();
	}

	/**
	 * Test method for {@link org.richfaces.model.SortField2#hashCode()}.
	 */
	public final void testHashCode() {
		Field sortField = new SortField2(expression, ordering);
		assertEquals(sortField.hashCode(), field.hashCode());
	}

	/**
	 * Test method for {@link org.richfaces.model.SortField2#equals(java.lang.Object)}.
	 */
	public final void testEqualsObject() {
		SortField2 sortField = new SortField2(null);
		assertFalse(sortField.equals(field));
		sortField.setExpression(expression);
		sortField.setOrdering(ordering);
		assertTrue(sortField.equals(field));
	}

	/**
	 * Test method for {@link org.richfaces.model.SortField2#SortField2(javax.el.ValueExpression)}.
	 */
	public final void testSortField2ValueExpression() {
		SortField2 sortField2 = new SortField2(expression);
		field.setOrdering(null);
		assertEquals(sortField2, field);
	}

	/**
	 * Test method for {@link org.richfaces.model.SortField2#SortField2(javax.el.ValueExpression, org.richfaces.model.Ordering)}.
	 */
	public final void testSortField2ValueExpressionOrdering() {
		SortField2 sortField2 = new SortField2(expression, ordering);
		assertEquals(sortField2, field);
	}

	/**
	 * Test method for {@link org.richfaces.model.SortField2#getOrdering()}.
	 */
	public final void testGetOrdering() {
		assertEquals(field.getOrdering(), ordering);
	}

	/**
	 * Test method for {@link org.richfaces.model.SortField2#setOrdering(org.richfaces.model.Ordering)}.
	 */
	public final void testSetOrdering() {
		field.setOrdering(null);
		assertNull(field.getOrdering());
	}

}
