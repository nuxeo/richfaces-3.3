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

package org.richfaces.taglib;

import javax.faces.component.UIComponent;
import javax.faces.el.MethodBinding;
import javax.faces.event.ActionEvent;

import org.ajax4jsf.webapp.taglib.SimpleActionMethodBinding;
import org.ajax4jsf.webapp.taglib.UIComponentTagBase;
import org.richfaces.component.UIDialogWindow;


/**
 * @author Nick Belaevski - nbelaevski@exadel.com
 * created 08.12.2006
 * 
 */
public abstract class DialogWindowTagBase extends UIComponentTagBase {
	private String  _closeWindowAction = null;
	private String  _closeWindowActionListener = null;

	public void release() {
		super.release();
		this._closeWindowAction = null;
		this._closeWindowActionListener = null;
	}

	public void setCloseWindowAction(String windowAction) {
		_closeWindowAction = windowAction;
	}
	
	public void setCloseWindowActionListener(String windowActionListener) {
		_closeWindowActionListener = windowActionListener;
	}
	
	protected void setProperties(UIComponent component) {
		super.setProperties(component);
		if(null != this._closeWindowActionListener){
			if (isValueReference(this._closeWindowActionListener)) {
				MethodBinding mb = getFacesContext().getApplication().createMethodBinding(this._closeWindowActionListener,
						new Class[]{ActionEvent.class});
				((UIDialogWindow)component).setCloseWindowActionListener(mb);
			} else {
				getFacesContext().getExternalContext().log("Component " + component.getClientId(getFacesContext()) + " has invalid closeWindowActionListener value: " + this._closeWindowActionListener);
			}
		}

		if(null != this._closeWindowAction){
			MethodBinding mb;
			
			if (isValueReference(this._closeWindowAction)) {
				mb = getFacesContext().getApplication().createMethodBinding(this._closeWindowAction,
						new Class[]{});
			} else {
				mb = new SimpleActionMethodBinding(this._closeWindowAction);
			}

			((UIDialogWindow)component).setCloseWindowAction(mb);
		}
	}
}
