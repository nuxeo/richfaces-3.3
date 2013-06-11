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
package org.richfaces.component;

import javax.faces.FacesException;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;

import org.ajax4jsf.ajax.ForceRender;
import org.ajax4jsf.component.AjaxActionComponent;
import org.ajax4jsf.event.AjaxListener;

/**
 * 
 * @author glory
 */
public abstract class UIDialogAction extends AjaxActionComponent {
	protected DialogActionState state = new DialogActionState();
	transient private MethodBinding actionListener = null;
	transient private MethodBinding action = null;
    private Object value = null;
    private Object  _reRender = null; /* Default is null*/
	
	public UIDialogAction() {}

    public static ViewHandler getViewHandler(FacesContext context) throws FacesException {
    	return context.getApplication().getViewHandler();
    }

	public void setReRender(Object  __reRender) {
		this._reRender = __reRender;
	}
	public Object getReRender() {
		if (null != this._reRender) {
			return this._reRender;
		}
		ValueBinding vb = getValueBinding("reRender");
		if (null != vb) {
			return (Object)vb.getValue(getFacesContext());
		} else {
			return null;
		}
	}

   	/**
   	 *  MethodBinding pointing at the application action to be invoked,
   	 *  if this UIComponent is activated by the user, during the Apply
   	 *  Request Values or Invoke Application phase of the request
   	 *  processing lifecycle, depending on the value of the immediate
   	 *  property.
	 *  Setter for action
	 *  @param action - new value
	 */
	public void setAction(MethodBinding  action) {
		this.action = action;
	}

	/**
	 *  MethodBinding pointing at the application action to be invoked,
	 *  if this UIComponent is activated by the user, during the Apply
	 *  Request Values or Invoke Application phase of the request
	 *  processing lifecycle, depending on the value of the immediate
	 *  property.
	 *  Getter for action
	 *  @return action value from local variable or value bindings
	 */
	public MethodBinding getAction() {
		return this.action;
    }

    public MethodBinding getActionListener() {
        return this.actionListener;
	}

	public void setActionListener(MethodBinding actionListener) {
		this.actionListener = actionListener;
	}

    public boolean isImmediate() {
    	if (state.isImmediateSet()) {
    		return state.isImmediate();
    	}
    	ValueBinding vb = getValueBinding("immediate");
    	if (vb != null) {
    		return Boolean.TRUE.equals(vb.getValue(getFacesContext()));
    	} else {
    		return state.isImmediate();
    	}
    }

    public void setImmediate(boolean immediate) {
    	state.setImmediate(immediate);
    }

    public Object getValue() {
    	if (value != null) {
    		return value;
    	}
    	ValueBinding vb = getValueBinding("value");
    	if (vb != null) {
    		return vb.getValue(getFacesContext());
    	} else {
    		return null;
    	}
    }

    public void setValue(Object value) {
        this.value = value;
    }

	public String getMode() {
		return state.getMode();
	}
	
	public void setMode(String mode) {
		state.setMode(mode);
	}
	
	public void provideForceRerender() {
		if(isServerMode(this)) return;
		if(findParentUIData() == null) return;
		if(hasForceRender()) return;
		addAjaxListener(new ForceRender());
	}
	
	UIData findParentUIData() {
		UIComponent c = this;
		while(c != null && !(c instanceof UIData)) c = c.getParent();
		return (UIData)c;
	}
	boolean hasForceRender() {
		AjaxListener[] ls = getAjaxListeners();
		if(ls == null || ls.length == 0) return false;
		for (int i = 0; i < ls.length; i++) {
			if(ls[i] instanceof ForceRender) return true;
		}
		return false;
	}

//	public Object getReRender() {
//		return null;
//	}

    public Object saveState(FacesContext context) {
        Object values[] = new Object[6];
        values[0] = super.saveState(context);
        values[1] = saveAttachedState(context, state);
        values[2] = saveAttachedState(context, action);
        values[3] = saveAttachedState(context, actionListener);
        values[4] = value;
        values[5] = _reRender;
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        this.state = (DialogActionState)restoreAttachedState(context, values[1]);
        this.action = (MethodBinding)restoreAttachedState(context, values[2]);
        this.actionListener = (MethodBinding)restoreAttachedState(context, values[3]);
        this.value = values[4];
        this._reRender = values[5];
    }
    
    public static boolean isServerMode(UIComponent component) {
    	if(!(component instanceof UIDialogAction)) return false;
    	UIDialogAction dialogAction = (UIDialogAction)component;
    	return dialogAction.state.isServerMode();    	
    }

	public boolean isIgnoreDupResponses() {
		return true;
	}

	public void setIgnoreDupResponses(boolean newvalue) {
		
	};
	

	int timeout = 10000;

	public int getTimeout() {
		return timeout;
	}
	
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

}
