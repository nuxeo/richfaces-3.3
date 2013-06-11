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
import org.richfaces.model.impl.expressive.ValueBindingExpression;

/**
 * @author Maksim Kaszynski
 *
 */
public class ValueBindingExpressionTest extends AbstractAjax4JsfTestCase {

	private ValueBindingExpression expression;
	static final String var = "obj";
	static final  String el = "#{" + var + ".name}";
	
	public ValueBindingExpressionTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#setUp()
	 */
	public void setUp() throws Exception {
		super.setUp();
		expression = new ValueBindingExpression(facesContext, el, var);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
	 */
	public void tearDown() throws Exception {
		super.tearDown();
		expression = null;
	}

	/**
	 * Test method for {@link org.richfaces.model.impl.expressive.ValueBindingExpression#evaluate(java.lang.Object)}.
	 */
	public final void testEvaluate() {
		TestObj testObj = new TestObj("aaaa");
		Object prop = expression.evaluate(testObj);
		//assertNotNull(prop);
		//assertEquals("aaaa", prop);
	}

	/**
	 * Test method for {@link org.richfaces.model.impl.expressive.Expression#getExpressionString()}.
	 */
	public final void testGetExpressionString() {
		assertEquals(el, expression.getExpressionString());
	}

}
