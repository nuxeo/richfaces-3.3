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
public class DragDropEvent extends FacesEvent {
	
	private static final long serialVersionUID = 2589493255876683993L;
	
	private Object dragValue;
	private Object dropValue;
	private String dragType;
	private boolean dropBefore;

	public DragDropEvent(UIComponent component) {
		super(component);
	}

	public void processListener(FacesListener listener) {
		((DragDropListener)listener).processDragDrop(this);
	}

	public Object getDragValue() {
		return dragValue;
	}

	public void setDragValue(Object dragValue) {
		this.dragValue = dragValue;
	}

	public Object getDropValue() {
		return dropValue;
	}

	public void setDropValue(Object dropValue) {
		this.dropValue = dropValue;
	}
	
	public String getDragType() {
		return dragType;
	}
	
	public void setDragType(String dragType) {
		this.dragType = dragType;
	}
	
	public boolean isDropBefore() {
		return dropBefore;
	}

	public void setDropBefore(boolean dropBefore) {
		this.dropBefore = dropBefore;
	}

	public boolean isAppropriateListener(FacesListener listener) {
		return (listener instanceof DragDropListener);
	}

}
