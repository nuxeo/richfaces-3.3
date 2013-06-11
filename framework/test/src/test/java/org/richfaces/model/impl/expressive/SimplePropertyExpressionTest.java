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

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.model.impl.expressive.SimplePropertyExpression;

/**
 * @author Maksim Kaszynski
 *
 */
public class SimplePropertyExpressionTest extends AbstractAjax4JsfTestCase {

	final static String property = "name";
	private SimplePropertyExpression expression;
	
	/**
	 * @param name
	 */
	public SimplePropertyExpressionTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	public void setUp() throws Exception {
		super.setUp();
		expression = new SimplePropertyExpression("name", facesContext.getELContext(), application.getELResolver());
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	public void tearDown() throws Exception {
		super.tearDown();
		expression = null;
	}

	/**
	 * Test method for {@link org.richfaces.model.impl.expressive.SimplePropertyExpression#evaluate(java.lang.Object)}.
	 */
	public final void testEvaluate() {
		TestObj testObj = new TestObj("aaaa");
		Object prop = expression.evaluate(testObj);
		assertNotNull(prop);
		assertEquals("aaaa", prop);
	}

	/**
	 * Test method for {@link org.richfaces.model.impl.expressive.Expression#getExpressionString()}.
	 */
	public final void testGetExpressionString() {
		assertEquals(property, expression.getExpressionString());
	}

}
