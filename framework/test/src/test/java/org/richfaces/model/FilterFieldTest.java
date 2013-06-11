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
public class FilterFieldTest extends TestCase {

	private Field field;
	private ValueExpression expression;

	/**
	 * @param name
	 */
	public FilterFieldTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		expression = new MockValueExpression(null);
		field = new FilterField(expression);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		field = null;
		expression = null;
		super.tearDown();
	}

	/**
	 * Test method for {@link org.richfaces.model.FilterField#FilterField(javax.el.ValueExpression)}.
	 */
	public final void testFilterField() {
		Field filterField = new FilterField(expression);
		assertEquals(filterField, field);
	}

	/**
	 * Test method for {@link org.richfaces.model.Field#hashCode()}.
	 */
	public final void testHashCode() {
		Field filterField = new FilterField(expression);
		assertEquals(filterField.hashCode(), field.hashCode());
	}

	/**
	 * Test method for {@link org.richfaces.model.Field#equals(java.lang.Object)}.
	 */
	public final void testEqualsObject() {
		Field filterField = new FilterField(null);
		assertFalse(filterField.equals(field));
		filterField.setExpression(expression);
		assertTrue(filterField.equals(field));
	}

	/**
	 * Test method for {@link org.richfaces.model.Field#getExpression()}.
	 */
	public final void testGetExpression() {
		assertSame(field.getExpression(), expression);
	}

	/**
	 * Test method for {@link org.richfaces.model.Field#setExpression(javax.el.ValueExpression)}.
	 */
	public final void testSetExpression() {
		field.setExpression(null);
		assertNull(field.getExpression());
	}

}
