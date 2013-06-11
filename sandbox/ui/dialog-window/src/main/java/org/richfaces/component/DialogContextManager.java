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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

public class DialogContextManager implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String DIALOG_CONTEXT_MANAGER = "DIALOG_CONTEXT_MANAGER";
	public static final String ACTIVE_CONTEXT_NAME = "dialog";
	
	public static DialogContextManager getInstance(FacesContext context) {
		DialogContextManager instance = (DialogContextManager)context.getExternalContext().getSessionMap().get(DIALOG_CONTEXT_MANAGER);
		if(instance == null) {
			instance = new DialogContextManager();
			context.getExternalContext().getSessionMap().put(DIALOG_CONTEXT_MANAGER, instance);
		}			
		return instance;
	}
	
//	Map<String, DialogContext> contexts = new HashMap<String, DialogContext>();
	Map contexts = new HashMap();
	DialogContext active = null;
	
	public DialogContext getContext(String dialogId) {
		DialogContext context = (DialogContext)contexts.get(dialogId);
		if(context == null) {
			context = new DialogContext();
			context.setDialogId(dialogId);
			contexts.put(dialogId, context);
		}
		return context;
	}
	
	public void removeContext(String dialogId) {
		contexts.remove(dialogId);		
	}
	
	public void setActiveRequest(String dialogId) {
		if(dialogId == null) {
			active = null;
			return;
		}
		DialogContext context = getContext(dialogId);
		active = context;
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put(ACTIVE_CONTEXT_NAME, context);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(ACTIVE_CONTEXT_NAME, context);
	}
	
	public DialogContext getActiveContext() {
		return active;
	}

}
