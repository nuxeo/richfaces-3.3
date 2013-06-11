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
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;

/**
 * @author asmirnov
 *
 */
public abstract class StateExpressionFactory extends ExpressionFactory {
	

	/**
	 * @param obj
	 * @param targetType
	 * @return
	 * @see javax.el.ExpressionFactory#coerceToType(java.lang.Object, java.lang.Class)
	 */
	public Object coerceToType(Object obj, Class<?> targetType) {
		return getDefaultFactory().coerceToType(obj, targetType);
	}

	/**
	 * @param context
	 * @param expression
	 * @param expectedReturnType
	 * @param expectedParamTypes
	 * @return
	 * @see javax.el.ExpressionFactory#createMethodExpression(javax.el.ELContext, java.lang.String, java.lang.Class, java.lang.Class<?>[])
	 */
	public MethodExpression createMethodExpression(ELContext context,
			String expression, Class<?> expectedReturnType,
			Class<?>[] expectedParamTypes) {
		MethodExpression methodExpression = getDefaultFactory().createMethodExpression(context, expression,
						expectedReturnType, expectedParamTypes);
		ValueExpression valueExpression = getDefaultFactory().createValueExpression(context, expression, MethodExpression.class);
		return new StateMethodExpressionWrapper(methodExpression,valueExpression);
	}

	/**
	 * @param context
	 * @param expression
	 * @param expectedType
	 * @return
	 * @see javax.el.ExpressionFactory#createValueExpression(javax.el.ELContext, java.lang.String, java.lang.Class)
	 */
	public ValueExpression createValueExpression(ELContext context,
			String expression, Class<?> expectedType) {
		return getDefaultFactory().createValueExpression(context, expression,
				expectedType);
	}

	/**
	 * @param instance
	 * @param expectedType
	 * @return
	 * @see javax.el.ExpressionFactory#createValueExpression(java.lang.Object, java.lang.Class)
	 */
	public ValueExpression createValueExpression(Object instance,
			Class<?> expectedType) {
		return getDefaultFactory().createValueExpression(instance, expectedType);
	}

	/**
	 * @return the defaultFactory
	 */
	public abstract ExpressionFactory getDefaultFactory() ;

}
