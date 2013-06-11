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

import javax.faces.component.UIComponent;
import javax.faces.event.FacesListener;

public class DialogOpenEvent extends DialogEvent {
	private static final long serialVersionUID = 1L;
	
	boolean close = false;

	public DialogOpenEvent(UIComponent component) {
		super(component);
	}

//	@Override
	public boolean isAppropriateListener(FacesListener listener) {
		return (listener instanceof DialogOpenListener);
	}

//	@Override
	public void processListener(FacesListener listener) {
		((DialogOpenListener)listener).processDialogOpen(this);
	}
	
	public void setSourceViewId(String viewId) {
		sourceViewId = viewId;
	}
	
	public void setClose(boolean b) {
		close = b;
	}
	
	public boolean isClose() {
		return close;
	}

}
