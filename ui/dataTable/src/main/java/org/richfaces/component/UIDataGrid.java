/**
 * License Agreement.
 *
 *  JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007  Exadel, Inc.
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

import java.util.Iterator;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.el.ValueBinding;

import org.ajax4jsf.component.SequenceDataAdaptor;

/**
 * JSF component class
 *
 */
public abstract class UIDataGrid extends SequenceDataAdaptor {
	
	public static final String COMPONENT_TYPE = "org.richfaces.DataGrid";
	
	public static final String COMPONENT_FAMILY = "org.richfaces.DataGrid";
	
	/**
	 * Number of columns in one table row.
	 * @parameter
	 * @return the acceptClass
	 */
	public abstract int getColumns();

	/**
	 * @param newColumns the value  to set
	 */
	public abstract void setColumns(int newColumns);
	
	public void setElements(int elements) {
		setRows(elements);
	}
	
	public int getElements() {
		return getRows();
	}
	/* (non-Javadoc)
	 * @see org.ajax4jsf.ajax.repeat.UIDataAdaptor#dataChildren()
	 */
	protected Iterator<UIComponent> dataChildren() {
		return getChildren().iterator();
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.ajax.repeat.UIDataAdaptor#fixedChildren()
	 */
	protected Iterator<UIComponent> fixedChildren() {
		return getFacets().values().iterator();
	}
	
	public void setValueExpression(String name, ValueExpression binding) {
		if ("elements".equals(name)) {
			super.setValueExpression("rows", binding);
		} else {
			super.setValueExpression(name, binding);
		}
	}
	
	public ValueExpression getValueExpression(String name) {
		if ("elements".equals(name)) {
			return super.getValueExpression("rows");
		} else {
			return super.getValueExpression(name);
		}
	}

	public void setValueBinding(String name, ValueBinding binding) {
		if ("elements".equals(name)) {
			super.setValueBinding("rows", binding);
		} else {
			super.setValueBinding(name, binding);
		}
	}
	
	public ValueBinding getValueBinding(String name) {
		if ("elements".equals(name)) {
			return super.getValueBinding("rows");
		} else {
			return super.getValueBinding(name);
		}
	}
}
