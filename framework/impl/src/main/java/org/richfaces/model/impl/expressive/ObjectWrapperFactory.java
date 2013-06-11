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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.ajax4jsf.util.ELUtils;
import org.richfaces.model.Field;
import org.richfaces.model.SortField;
import org.richfaces.model.SortOrder;
/**
 * 
 * class responsible for packaging objects with their properties evaluated using EL.
 * 
 * @author Maksim Kaszynski
 *
 */
public class ObjectWrapperFactory {

	interface ObjectConvertor{
		public Object convert(Object o);
	}
	
	private Expression [] expressions;
	private FacesContext context;
	private String var;
	private Object varValue;
	
	public ObjectWrapperFactory(FacesContext context, final String var, SortOrder sortOrder) {
		
		this.context = context;
		
		Application application = context.getApplication();
		ELResolver resolver = application.getELResolver();
		ELContext elContext = context.getELContext();
		this.var = var;
		
		SortField[] sortFields = sortOrder.getFields();
		
		expressions = new Expression[sortFields.length];
		                             
		for (int i = 0; i < sortFields.length; i++) {
			final SortField field = sortFields[i];
			final String name = field.getName();
			
			if (ELUtils.isValueReference(name)) {
				
				expressions[i] = new ValueBindingExpression(context, name, var);
				
			} else if (name.startsWith(UIViewRoot.UNIQUE_ID_PREFIX)) {
				
				expressions[i] = new NullExpression(name);
				
			} else {
				
				expressions[i] = new SimplePropertyExpression(name, elContext, resolver);
			}
		}
		
		
		
	}
	public ObjectWrapperFactory(FacesContext context, final String var, List<? extends Field> sortOrder) {
		
		this.context = context;
		
		Application application = context.getApplication();
		ELResolver resolver = application.getELResolver();
		ELContext elContext = context.getELContext();
		this.var = var;
		
		expressions = new Expression[sortOrder.size()];
		
		int i = 0;
		for (Field field : sortOrder) {
			javax.el.Expression elExpression = field.getExpression();
			Expression expression;
			
			if (elExpression instanceof ValueExpression) {
				ValueExpression valueExpression = (ValueExpression) elExpression;
				
				if (valueExpression.isLiteralText()) {
					String expressionString = valueExpression.getExpressionString();
					
					if (expressionString.startsWith(UIViewRoot.UNIQUE_ID_PREFIX)) {
						expression = new NullExpression(expressionString);
					} else {
						expression = new SimplePropertyExpression(expressionString, elContext, resolver);
					}
				} else {
					expression = new ValueBindingExpression(context, valueExpression, var);
				}
			} else if(elExpression instanceof MethodExpression){
				expression = new MethodBindingExpression(context, (MethodExpression)elExpression);
			} else {
				throw new IllegalArgumentException();
			}
			expressions[i++] = expression;
		}
		
	}
	
	void convertList (List<? super Object> list, ObjectConvertor c) {
		int l = list.size();

		for (int i = 0; i < l; i++) {
			Object o = list.get(i);
			list.set(i, c.convert(o));
		}
	}
	
	public List<? super Object> unwrapList(List<Object> list) {
		
		convertList(list, new ObjectConvertor() {
			public Object convert(Object o) {
				return unwrapObject(o);
			}
		});
		
		Map<String, Object> requestMap = getRequestMap();
		if (varValue == null) {
			requestMap.remove(var);
		} else {
			requestMap.put(var, varValue);
		}
		
		return list;
	}
	/**
	 * 
	 */
	private Map<String, Object>  getRequestMap() {
		ExternalContext externalContext = context.getExternalContext();
		Map<String, Object> requestMap = externalContext.getRequestMap();
		return requestMap;
	}
	
	public Object unwrapObject(Object wrapper) {
		return ((JavaBeanWrapper) wrapper).getWrappedObject();
	}
	
	public List<Object> wrapList(List<Object> list) {
		varValue = getRequestMap().get(var);
		
		convertList(list, new ObjectConvertor() {
			public Object convert(Object o) {
				return wrapObject(o);
			}
		});
		
		return list;
	}
	
	public JavaBeanWrapper wrapObject(Object o) {
		Map<String, Object> props = new HashMap<String, Object>();
		for (int i = 0; i < expressions.length; i++) {
			Expression expression = expressions[i];
			
			props.put(expression.getExpressionString(), expression.evaluate(o));
		}
		
		return new JavaBeanWrapper(o, props);
		
	}
	
}