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

import java.util.Iterator;

import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;

import org.ajax4jsf.component.AjaxSupport;

class DataIterator extends ColumnsIterator {

	protected Iterator<UIComponent> facetsIterator;

	public DataIterator(UIComponent dataTable) {
		super(dataTable);
		facetsIterator = dataTable.getFacets().values().iterator();
	}

	@Override
	protected UIComponent nextColumn() {
		UIComponent nextColumn = null;
		while (null == nextColumn && childrenIterator.hasNext()) {
			UIComponent child = childrenIterator.next();
			if (child.isRendered()) {
				if (child instanceof UIColumn || child instanceof Column) {
					nextColumn = child;
				} else if (checkAjaxComponent(child)) {
					nextColumn = child;
				}
			}
		}
		while (null == nextColumn && facetsIterator.hasNext()) {
			UIComponent child = facetsIterator.next();
			if (checkAjaxComponent(child)) {
				nextColumn = child;
				break;
			}
		}
		return nextColumn;
	}

	/**
	 * @param child
	 * @return
	 */
	protected Iterator<UIComponent> getColumnChildrenIterator(UIComponent child) {
		return child.getChildren().iterator();
	}

	/**
	 * @param child
	 * @return
	 */
	protected boolean checkAjaxComponent(UIComponent child) {
		return child instanceof AjaxSupport || child instanceof Dropzone;
	}

}
