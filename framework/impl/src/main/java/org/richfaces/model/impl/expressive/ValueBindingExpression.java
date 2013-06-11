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

import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.el.VariableMapper;
import javax.faces.context.FacesContext;

/**
 * 
 * Expression evaluated by invoking EL-expression in context of base object
 * 
 * @author Maksim Kaszynski
 *
 */
final class ValueBindingExpression extends Expression {

	private ELContext context;
	private String var;
	private ValueExpression valueExpression;
	private VariableMapper mapper;
	private ExpressionFactory factory;
	private Map<String, Object> requestMap;
	/**
	 * @param n
	 * @param application
	 * @param requestMap
	 * @param expressionString
	 * @param context
	 * @param var
	 */
	ValueBindingExpression(FacesContext faces, String expressionString, String var) {
		super(expressionString);
		this.context = faces.getELContext();
		this.var = var;
		ExpressionFactory expressionFactory = faces.getApplication().getExpressionFactory();
		valueExpression = expressionFactory.createValueExpression(context, expressionString, Object.class);
		mapper = context.getVariableMapper();
		factory = expressionFactory;
		requestMap = faces.getExternalContext().getRequestMap();
	}
	
	ValueBindingExpression(FacesContext faces, ValueExpression valueExpression, String var) {
		super(valueExpression.getExpressionString());
		this.context = faces.getELContext();
		this.var = var;
		ExpressionFactory expressionFactory = faces.getApplication().getExpressionFactory();
		this.valueExpression = valueExpression;
		mapper = context.getVariableMapper();
		factory = expressionFactory;
		requestMap = faces.getExternalContext().getRequestMap();
	}

	public Object evaluate(Object base) {
		//mapper.setVariable(var, factory.createValueExpression(base, Object.class));
		requestMap.put(var, base);
		return valueExpression.getValue(context);
	}
}