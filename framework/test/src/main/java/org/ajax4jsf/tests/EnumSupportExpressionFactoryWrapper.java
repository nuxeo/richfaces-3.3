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
package org.ajax4jsf.tests;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;

/**
 * @author Maksim Kaszynski
 *
 */
public class EnumSupportExpressionFactoryWrapper extends ExpressionFactory {

	private ExpressionFactory factory;
	/**
	 * 
	 */
	public EnumSupportExpressionFactoryWrapper(ExpressionFactory factory) {
		this.factory = factory;
	}

	/* (non-Javadoc)
	 * @see javax.el.ExpressionFactory#coerceToType(java.lang.Object, java.lang.Class)
	 */
	@Override
	public Object coerceToType(Object obj, Class<?> targetType) {
		if (targetType != null && targetType.isEnum()) {
			return coerceToEnum(obj, (Class<?>) targetType);
		}
		return factory.coerceToType(obj, targetType);
	}
	
	@SuppressWarnings("unchecked")
	private Enum<?> coerceToEnum(Object o, Class clazz) {
		if (o == null || "".equals(o)) {
			return null;
		}
		
		if (clazz.isInstance(o)) {
			return (Enum<?>) o;
		}
		
		if (o instanceof String) {
			return Enum.valueOf(clazz, (String) o);
		}
		
        throw new IllegalArgumentException("Cannot convert " + o + " of class " + o.getClass() + " to type " + clazz);
	}

	/* (non-Javadoc)
	 * @see javax.el.ExpressionFactory#createMethodExpression(javax.el.ELContext, java.lang.String, java.lang.Class, java.lang.Class<?>[])
	 */
	@Override
	public MethodExpression createMethodExpression(ELContext context,
			String expression, Class<?> expectedReturnType,
			Class<?>[] expectedParamTypes) {
		return factory.createMethodExpression(context, expression, expectedReturnType, expectedParamTypes);
	}

	/* (non-Javadoc)
	 * @see javax.el.ExpressionFactory#createValueExpression(java.lang.Object, java.lang.Class)
	 */
	@Override
	public ValueExpression createValueExpression(Object instance,
			Class<?> expectedType) {
		return factory.createValueExpression(instance, expectedType);
	}

	/* (non-Javadoc)
	 * @see javax.el.ExpressionFactory#createValueExpression(javax.el.ELContext, java.lang.String, java.lang.Class)
	 */
	@Override
	public ValueExpression createValueExpression(ELContext context,
			String expression, Class<?> expectedType) {
		return factory.createValueExpression(context, expression, expectedType);
	}

}
