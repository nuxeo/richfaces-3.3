/**
* License Agreement.
*
* JBoss RichFaces - Ajax4jsf Component Library
*
* Copyright (C) 2008 CompuGROUP Holding AG
* 
* This library is free software; you can redistribute it and/or
* modify it under the terms of the GNU Lesser General Public
* License version 2.1 as published by the Free Software Foundation.
*
* This library is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
* Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public
* License along with this library; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA

*/ 
package org.richfaces.event.extdt;

import javax.faces.component.UIComponent;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;

/**
 * @author pawelgo
 *
 */
public class ColumnResizeEvent extends FacesEvent {
	
	private static final long serialVersionUID = -8424890931071484456L;
	
	private String columnWidths;

	public ColumnResizeEvent(UIComponent component, String columnWidths) {
		super(component);
		this.columnWidths = columnWidths;
	}

	/* (non-Javadoc)
	 * @see javax.faces.event.FacesEvent#isAppropriateListener(javax.faces.event.FacesListener)
	 */
	public boolean isAppropriateListener(FacesListener listener) {
		return (listener instanceof ColumnResizeListener);
	}

	/* (non-Javadoc)
	 * @see javax.faces.event.FacesEvent#processListener(javax.faces.event.FacesListener)
	 */
	public void processListener(FacesListener listener) {
		((ColumnResizeListener)listener).processColumnResize(this);
	}
	
	public String getColumnWidths() {
		return columnWidths;
	}

	public void setColumnWidths(String columnWidths) {
		this.columnWidths = columnWidths;
	}

}
