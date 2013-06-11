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

package org.ajax4jsf.event;

import java.io.Serializable;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * Helper class to keep reference to listener binded as EL-expression.
 * @author shura
 *
 */
public class AjaxListenerHelper implements AjaxListener,StateHolder {
	
	
	private ValueBinding _binding;
	
	
	private boolean _transient = false;

	
	/**
	 * 
	 */
	public AjaxListenerHelper() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param binding
	 */
	public AjaxListenerHelper(ValueBinding binding) {
		super();
		if (null == binding) {
			throw new IllegalArgumentException("Binding expression for AjaxListener helper must be not null");
		}
		_binding = binding;
	}

	private AjaxListener getHandler(FacesContext context) {
		return (AjaxListener) _binding.getValue(context);
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.StateHolder#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public void restoreState(FacesContext context, Object state) {
		State helperState = (State) state;
		_binding = (ValueBinding) UIComponentBase.restoreAttachedState(context,helperState.binding);
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.StateHolder#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext context) {
		State helperState = new State();
		helperState.binding = UIComponentBase.saveAttachedState(context,_binding);
		return helperState;
	}

	/**
	 * @return Returns the transient.
	 */
	public boolean isTransient() {
		return _transient;
	}

	/**
	 * @param transient1 The transient to set.
	 */
	public void setTransient(boolean transient1) {
		_transient = transient1;
	}
	
	/**
	 * @author shura
	 *
	 */
	private static class State implements Serializable {
		
		
		private Object binding;
		

	}

	public void processAjax(AjaxEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		AjaxListener handler = getHandler(context);
		handler.processAjax(event);
	}

}
