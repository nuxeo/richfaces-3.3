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
package org.richfaces.renderkit;

import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.el.MethodNotFoundException;

import org.richfaces.component.ActionPrefixHolder;


public class DialogMethodBinding extends MethodBinding implements StateHolder {
	private static final long serialVersionUID = 1L;

	ActionPrefixHolder prefixHolder;
	MethodBinding binding;
	String clientId = null;
	boolean isTransient = false;

	public DialogMethodBinding() {}
	
	public DialogMethodBinding(MethodBinding binding, ActionPrefixHolder prefixHolder) {
		this.binding = binding;
		this.prefixHolder = prefixHolder;
		if(prefixHolder instanceof UIComponent) {
			clientId = ((UIComponent)prefixHolder).getClientId(FacesContext.getCurrentInstance());
		}
	}

//	@Override
	public Class getType(FacesContext context) throws MethodNotFoundException {
		return binding == null ? Void.class : binding.getType(context);
	}

//	@Override
	public Object invoke(FacesContext context, Object[] params) throws EvaluationException, MethodNotFoundException {
		if(binding == null) {
			setNullPath();
			return null;
		}
		Object outcome = binding.invoke(context, params);
		if(outcome == null || "null".equals(outcome)) {
			setNullPath();
			return null;
		}
		String s = outcome.toString();
		int index = s.indexOf(':');
		if(index >= 0) {
			setPrefix(s.substring(0, index + 1));
			outcome = s.substring(index + 1);
		}
		if(outcome == null || "".equals(outcome) || "null".equals(outcome)) {
			setNullPath();
			return null;
		}
		return outcome;
	}
	
	private void setPrefix(String prefix) {
		getPrefixHolder();
		if(prefixHolder != null) prefixHolder.setPrefix(prefix);
	}
	
	private void setNullPath() {
		getPrefixHolder();
		if(prefixHolder != null) prefixHolder.setNullPath();
	}
	
	void getPrefixHolder() {
		if(prefixHolder != null || clientId == null) return;
		getPrefixHolder(FacesContext.getCurrentInstance());
	}

	void getPrefixHolder(FacesContext context) {
		if(prefixHolder != null || clientId == null) return;
		UIComponent c = context.getViewRoot().findComponent(clientId);
		if(c instanceof ActionPrefixHolder) prefixHolder = (ActionPrefixHolder)c;
	}

	public boolean isTransient() {
		return (binding instanceof StateHolder) ? ((StateHolder)binding).isTransient() : isTransient;
	}

	public void restoreState(FacesContext context, Object data) {
		Object[] os = (Object[])data;
		binding = (MethodBinding)UIComponentBase.restoreAttachedState(context, os[0]);
		clientId = (String)os[1];
//		getPrefixHolder(context);		
	}

	public Object saveState(FacesContext context) {
		Object[] os = new Object[2];
		os[0] = UIComponentBase.saveAttachedState(context, binding);
		os[1] = clientId;
		return os;
	}

	public void setTransient(boolean b) {
		this.isTransient = b;
		if(binding instanceof StateHolder) ((StateHolder)binding).setTransient(b);
	}

}
