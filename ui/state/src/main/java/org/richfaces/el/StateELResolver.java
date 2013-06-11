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
package org.richfaces.el;

import java.beans.FeatureDescriptor;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.FunctionMapper;
import javax.el.PropertyNotFoundException;
import javax.el.PropertyNotWritableException;
import javax.el.ValueExpression;
import javax.el.VariableMapper;
import javax.faces.context.FacesContext;

import org.richfaces.ui.model.State;

/**
 * @author asmirnov
 *
 */
public class StateELResolver extends ELResolver {
	
	private static final class ELContextWrapper extends ELContext {
		private final ELContext context;
		
		private boolean resolved = false;

		private ELContextWrapper(ELContext context) {
			this.context = context;
		}

		@Override
		public ELResolver getELResolver() {
			return context.getELResolver();
		}

		@Override
		public FunctionMapper getFunctionMapper() {
			return context.getFunctionMapper();
		}

		@Override
		public VariableMapper getVariableMapper() {
			return context.getVariableMapper();
		}

		/**
		 * @param key
		 * @return
		 * @see javax.el.ELContext#getContext(java.lang.Class)
		 */
		public Object getContext(Class key) {
			return context.getContext(key);
		}

		/**
		 * @return
		 * @see javax.el.ELContext#getLocale()
		 */
		public Locale getLocale() {
			return context.getLocale();
		}

		/**
		 * @return
		 * @see javax.el.ELContext#isPropertyResolved()
		 */
		public boolean isPropertyResolved() {
			return resolved;
		}

		/**
		 * @param key
		 * @param contextObject
		 * @see javax.el.ELContext#putContext(java.lang.Class, java.lang.Object)
		 */
		public void putContext(Class key, Object contextObject) {
			context.putContext(key, contextObject);
		}

		/**
		 * @param locale
		 * @see javax.el.ELContext#setLocale(java.util.Locale)
		 */
		public void setLocale(Locale locale) {
			context.setLocale(locale);
		}

		/**
		 * @param resolved
		 * @see javax.el.ELContext#setPropertyResolved(boolean)
		 */
		public void setPropertyResolved(boolean resolved) {
			this.resolved = resolved;
		}
	}

	private static List<FeatureDescriptor> stateFeatureDescriptors;
	static {
		FeatureDescriptor descriptor = new FeatureDescriptor();
		descriptor.setDisplayName("Page state");
		descriptor.setExpert(false);
		descriptor.setName("state");
		descriptor.setHidden(false);
		stateFeatureDescriptors = Collections.singletonList(descriptor);
	}

	/* (non-Javadoc)
	 * @see javax.el.ELResolver#getCommonPropertyType(javax.el.ELContext, java.lang.Object)
	 */
	@Override
	public Class<?> getCommonPropertyType(ELContext context, Object base) {
		if (null != base && base instanceof State) {
			return String.class;			
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.el.ELResolver#getFeatureDescriptors(javax.el.ELContext, java.lang.Object)
	 */
	@Override
	public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context,
			Object base) {
		if (null != base && base instanceof State) {
			return stateFeatureDescriptors.iterator();			
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.el.ELResolver#getType(javax.el.ELContext, java.lang.Object, java.lang.Object)
	 */
	@Override
	public Class<?> getType(ELContext context, Object base, Object property) {
		if (null != base && base instanceof State) {
		      if (property == null) {
		          throw new PropertyNotFoundException("Null property");
		       }
			  State state = (State)base;
			  Object stateProperty = state.get(property.toString());
		      if (stateProperty == null) {
		          throw new PropertyNotFoundException("State Property ["+property+"] not found ");
		      }
		      context.setPropertyResolved(true);
		      if (stateProperty instanceof ValueExpression) {
				ValueExpression propertyExpression = (ValueExpression) stateProperty;
				FacesContext facesContext = FacesContext.getCurrentInstance();
				return propertyExpression.getType(facesContext.getELContext());
			}
			return stateProperty.getClass();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.el.ELResolver#getValue(javax.el.ELContext, java.lang.Object, java.lang.Object)
	 */
	@Override
	public Object getValue(final ELContext context, Object base, Object property) {
		if (null != base && base instanceof State) {
		      if (property == null) {
		          throw new PropertyNotFoundException("Null property");
		       }
			  State state = (State)base;
			  Object stateProperty = state.get(property.toString());
		      if (stateProperty == null) {
		          throw new PropertyNotFoundException("State Property ["+property+"] not found ");
		      }
		      context.setPropertyResolved(true);
		      if (stateProperty instanceof ValueExpression) {
				ValueExpression propertyExpression = (ValueExpression) stateProperty;
				FacesContext facesContext = FacesContext.getCurrentInstance();
				ELContext tempContext = new ELContextWrapper(context);
				return propertyExpression.getValue(tempContext);
			}
			return stateProperty;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.el.ELResolver#isReadOnly(javax.el.ELContext, java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean isReadOnly(ELContext context, Object base, Object property) {
		if (null != base && base instanceof State){
		      if (property == null) {
		          throw new PropertyNotFoundException("Null property");
		       }
			  State state = (State)base;
			  Object stateProperty = state.get(property.toString());
		      if (stateProperty == null) {
		          throw new PropertyNotFoundException("State Property ["+property+"] not found ");
		      }
		      context.setPropertyResolved(true);
		      return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.el.ELResolver#setValue(javax.el.ELContext, java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void setValue(ELContext context, Object base, Object property,
			Object value) {
		if (null != base && base instanceof State){
		      if (property == null) {
		          throw new PropertyNotFoundException("Null property");
		       }
		      throw new PropertyNotWritableException((String) property);
		}
	}

}
