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

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

/**
 * @author Maksim Kaszynski
 *
 */
public class UISortableControl extends UIComponentBase{
	
	public static final String COMPONENT_FAMILY = "org.richfaces.SortControl";
	
	public static final String COMPONENT_TYPE = "org.richfaces.SortControl";

	private ValueExpression sortExpression;
	
	private Mode mode;
	
	public ValueExpression getSortExpression() {
		return sortExpression;
	}

	public void setSortExpression(ValueExpression sortExpression) {
		this.sortExpression = sortExpression;
	}
	
	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	public Mode getMode() {
		return mode;
	}
	
	public UIDataTable getTable() {
		UIComponent component = getParent();
		while(component != null && !(component instanceof UIDataTable)) {
			component = component.getParent();
		}
		return (UIDataTable) component;
	}
	
	public void setMode(Mode mode) {
		this.mode = mode;
	}

	@Override
	public Object saveState(FacesContext context) {
		Object [] o = new Object[3];
		o[0] = super.saveState(context);
		o[1] = mode;
		o[2] = saveAttachedState(context, sortExpression);
		return o;
	}
	
	@Override
	public void restoreState(FacesContext context, Object state) {
		Object [] o = (Object[]) state;
		super.restoreState(context, o[0]);
		mode = (Mode) o[1];
		sortExpression = (ValueExpression) restoreAttachedState(context, o[2]);
	}
	
}
