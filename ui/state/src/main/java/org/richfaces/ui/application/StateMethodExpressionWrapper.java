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
package org.richfaces.ui.application;

import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.el.MethodInfo;
import javax.el.MethodNotFoundException;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

/**
 * @author asmirnov
 * 
 */
public class StateMethodExpressionWrapper extends MethodExpression {

	private MethodExpression parent;
	
	private ValueExpression baseObjectExpression = null;

	/**
	 * @param parent
	 */
	public StateMethodExpressionWrapper(MethodExpression parent,ValueExpression baseObject) {
		this.parent = parent;
		this.baseObjectExpression = baseObject;
	}

	/**
	 * @return
	 * @see javax.el.Expression#getExpressionString()
	 */
	public String getExpressionString() {
		return parent.getExpressionString();
	}

	/**
	 * @param context
	 * @return
	 * @see javax.el.MethodExpression#getMethodInfo(javax.el.ELContext)
	 */
	public MethodInfo getMethodInfo(ELContext context) {
		MethodInfo methodInfo = parent.getMethodInfo(context);
		return methodInfo;
	}

	/**
	 * @param context
	 * @param params
	 * @return
	 * @see javax.el.MethodExpression#invoke(javax.el.ELContext,
	 *      java.lang.Object[])
	 */
	public Object invoke(ELContext context, Object[] params) {
		Object result;
		try {
			result = parent.invoke(context, params);
		} catch (MethodNotFoundException e) {
			Object base = baseObjectExpression.getValue(context);
			if (base instanceof MethodExpression) {
				MethodExpression referencedMethod = (MethodExpression) base;
				result = referencedMethod.invoke(context, params);
			} else {
				throw e;
			}
		}
		return result;
	}

	/**
	 * @return
	 * @see javax.el.Expression#isLiteralText()
	 */
	public boolean isLiteralText() {
		return parent.isLiteralText();
	}

	/**
	 * @param obj
	 * @return
	 * @see javax.el.Expression#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		return parent.equals(obj);
	}

	/**
	 * @return
	 * @see javax.el.Expression#hashCode()
	 */
	public int hashCode() {
		return parent.hashCode();
	}

	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return parent.toString();
	}

}
