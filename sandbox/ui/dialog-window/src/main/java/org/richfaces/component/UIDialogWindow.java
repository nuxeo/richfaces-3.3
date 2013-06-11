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

import java.util.List;
import java.util.Map;

import javax.faces.application.ViewHandler;
import javax.faces.component.ActionSource;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.UIPanel;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;

import org.ajax4jsf.component.AjaxContainer;
import org.ajax4jsf.component.UIAjaxCommandLink;
import org.ajax4jsf.context.AjaxContext;
import org.ajax4jsf.context.ViewIdHolder;
import org.ajax4jsf.renderkit.AjaxRendererUtils;

/**
 * @author igels
 */
abstract public class UIDialogWindow extends UIDialogAction implements ActionPrefixHolder, ActionSource, ViewIdHolder { //UIAjaxCommandLink {
	public static final String COMPONENT_TYPE = UIAjaxCommandLink.COMPONENT_TYPE;

	public static final String COMPONENT_FAMILY = "javax.faces.Command";

	private transient MethodBinding closeWindowAction = null;
	private transient MethodBinding closeWindowActionListener = null;
	
	private transient boolean closeAction;
	
    transient String prefix = null;
    boolean isNullPath = false;
    DialogOpenListenerImpl listener = new DialogOpenListenerImpl();
    
    public UIDialogWindow() {}

	public String getFamily() {
		return COMPONENT_FAMILY;
	}

/*
	public Object getReRender() {
		return getId();
	}
*/

	protected void setupReRender() {
		FacesContext context = getFacesContext();
		String action = (String)context.getExternalContext().getRequestParameterMap().get("action");
		boolean isClose = action != null && action.startsWith("close");
		UIComponent c = isClose ? getAjaxContainer() : null;
		if(c != null && c != context.getViewRoot()) {
			super.setupReRender();
			AjaxRendererUtils.addRegionByName(context,this,this.getId());
//			addRegionsFromContainer((AjaxContainer)c, c);
		} else {
			AjaxRendererUtils.addRegionByName(context,this,this.getId());
		}
	}
	
	public boolean isClose() {
		FacesContext context = getFacesContext();
		String action = (String)context.getExternalContext().getRequestParameterMap().get("action");
		return action != null && action.startsWith("close");
	}
	
	UIComponent getAjaxContainer() {
		UIComponent c = this;
		while(c != null && !(c instanceof AjaxContainer)) c = c.getParent();
		return c;
	}
	
	void addRegionsFromContainer(AjaxContainer c, UIComponent component) {
		List ls = component.getChildren();
		if(ls == null || ls.size() == 0) return;
		for (int i = 0;i < ls.size(); i++) {
			UIComponent cc = (UIComponent)ls.get(i);
			if(cc instanceof AjaxContainer) {
				addRegionsFromContainer((AjaxContainer)cc, cc);
			} else if(cc instanceof UIData) {
				//nothing
			} else if(cc instanceof UIPanel) {
// If id is not set commented code should work instead of adding region
// but it is an agreed limitation to require for explicit id.
//				addRegionsFromContainer(c, cc);
				AjaxRendererUtils.addRegionByName(getFacesContext(), cc, cc.getId());
			} else {
				AjaxRendererUtils.addRegionByName(getFacesContext(), cc, cc.getId());
			}
		}
	}
	
	private String  _posReferenceId = null; /* Default is null*/
	private String  _posExternalReferenceId = null; /* Default is null*/

	public String getPosReferenceId() {
		if (null != this._posReferenceId) {
			return this._posReferenceId;
		}
		ValueBinding vb = getValueBinding("posReferenceId");
		if (null != vb) {
			return (String)vb.getValue(getFacesContext());
		} else {
			return null;
		}
	}
	public void setPosReferenceId( String  __posReferenceId ){
		this._posReferenceId = __posReferenceId;
	}
	public String getExternalPosReferenceId() {
		return _posExternalReferenceId;
	}
	public void setPosExternalReferenceId( String  _posExternalReferenceId ){
		this._posExternalReferenceId = _posExternalReferenceId;
	}


	public void setPrefix(String s) {
		prefix = s;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setNullPath() {
		isNullPath = true;
	}

	public boolean isNullPath() {
		return isNullPath;
	}

	public void broadcast(FacesEvent event) throws AbortProcessingException {
        if (event instanceof DialogWindowClosedEvent) {
        	boolean closeActionSet = closeAction;
        	try {
        		closeAction = true;
        		super.broadcast(event);
        	} finally {
        		closeAction = closeActionSet;
        	}
        } else {
            super.broadcast(event);
        }

        if (event.isAppropriateListener(listener)) {
            event.processListener(listener);
        }
	}
	
	public boolean skipNavigation(String ViewId) {
		if(isClose()) return true;
		
		if(ActionPrefixHolder.PREFIX_NORMAL.equals(prefix)) {
			if(!isClose()) {
				rejectOpen();
			}
			return false;
		}
		if(isNullPath()) {
			if(!isClose()) {
				rejectOpen();
				return false;
			}
		}
		return true;
	}
	
	String viewId = null;
	
	public void initViewId() {
		viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
	}
	
	public String getViewId() {
		return viewId;
	}
	
	public void setViewId(String newViewId) {
		FacesContext context = FacesContext.getCurrentInstance();
		AjaxContext.getCurrentInstance().setViewIdHolder(null);
		if(!isClose()) {
			String dialogId = getClientId(context);
		    DialogContext dcontext = DialogContextManager.getInstance(context).getContext(dialogId);

			ViewHandler vh = UIDialogAction.getViewHandler(context);
			/*
			 * Child dialog is about to open.
			 * The view id caculated by the navigation handler
			 * is to be saved and passed to child dialog window.
			 */
			String targetActionURL = vh.getActionURL(context, newViewId);
			targetActionURL += getParameterRequest();
			
	        dcontext.setDialogPath(targetActionURL);
		}
	}
	
	public String getParameterRequest() {
		StringBuffer sb = new StringBuffer();
		Map map = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		List c = getChildren();
		int k = 0;
		if(c != null) for (int i = 0; i < c.size(); i++) {
			if(c.get(i) instanceof UIParameter) {
				UIParameter p = (UIParameter)c.get(i);
				String name = p.getName();
				Object value = map.get(name);
				if(value == null) continue;
				if(k == 0) sb.append('?'); else sb.append('&');
				///TODO: encode value
				sb.append(name).append('=').append(value);
				k++;
			}
		}		
		return sb.toString();		
	}

	private void rejectOpen() {
		FacesContext context = FacesContext.getCurrentInstance();
		DialogContext dcontext = DialogContextManager.getInstance(context).getActiveContext();
		if(dcontext == null) return;
		DialogContext parentContext = dcontext.getParentContext();
		dcontext.deactivate();
        if(parentContext != null) { 
			DialogContextManager.getInstance(context).setActiveRequest(parentContext.getDialogId());
		} else {
	    	DialogContextManager.getInstance(context).setActiveRequest(null);
		}		
		AjaxContext.getCurrentInstance().setViewIdHolder(null);
	}

    public Object saveState(FacesContext context) {
        Object values[] = new Object[5];
        values[0] = super.saveState(context);
        values[1] = saveAttachedState(context, _posReferenceId);
        values[2] = saveAttachedState(context, _posExternalReferenceId);
        values[3] = saveAttachedState(context, closeWindowAction);
        values[4] = saveAttachedState(context, closeWindowActionListener);
        return values;
    }

    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        this._posReferenceId = (String)restoreAttachedState(context, values[1]);
        this._posExternalReferenceId = (String)restoreAttachedState(context, values[2]);
        this.closeWindowAction = (MethodBinding) restoreAttachedState(context, values[3]);
        this.closeWindowActionListener = (MethodBinding) restoreAttachedState(context, values[4]);
    }

	public MethodBinding getCloseWindowAction() {
		return this.closeWindowAction;
	}
	
	public void setCloseWindowAction(MethodBinding closeWindowListener) {
		this.closeWindowAction = closeWindowListener;
	}

	public MethodBinding getCloseWindowActionListener() {
		return closeWindowActionListener;
	}

	public void setCloseWindowActionListener(MethodBinding closeWindowActionListener) {
		this.closeWindowActionListener = closeWindowActionListener;
	}
	
	public MethodBinding getAction() {
		if (isCloseAction()) {
			return getCloseWindowAction();
		}

		return super.getAction();
	}
	
	public MethodBinding getActionListener() {
		if (isCloseAction()) {
			return getCloseWindowActionListener();
		}

		return super.getActionListener();
	}
	
	public boolean isCloseAction() {
		return closeAction;
	}
}
