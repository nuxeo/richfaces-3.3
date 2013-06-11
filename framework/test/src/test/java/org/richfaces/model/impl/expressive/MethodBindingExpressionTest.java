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
package org.richfaces.model.impl.expressive;

import org.apache.shale.test.base.AbstractJsfTestCase;
import org.apache.shale.test.el.MockMethodExpression;

/**
 * @author Konstantin Mishin
 *
 */
public class MethodBindingExpressionTest extends AbstractJsfTestCase {

	/**
	 * @param name
	 */
	public MethodBindingExpressionTest(String name) {
		super(name);
	}

	/**
	 * Test method for {@link org.richfaces.model.impl.expressive.MethodBindingExpression#MethodBindingExpression(javax.faces.context.FacesContext, javax.el.MethodExpression)}
	 * and {@link org.richfaces.model.impl.expressive.MethodBindingExpression#evaluate(java.lang.Object)}.
	 */
	public final void testMethodBindingExpression() {
		String expression = "expression";
		MethodBindingExpression methodBindingExpression =
			new MethodBindingExpression(facesContext,
					new MockMethodExpression(expression, null, String.class));
		
		assertEquals(methodBindingExpression.evaluate(null), expression);
	}

}
