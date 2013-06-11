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

class FixedChildrenIterator extends DataIterator {

	private Iterator<UIComponent> currentColumnIterator;

	public FixedChildrenIterator(UIComponent dataTable) {
		super(dataTable);
	}

	@Override
	protected UIComponent nextColumn() {
		UIComponent nextColumn = null;
		if (null != currentColumnIterator) {
			nextColumn = currentColumnIterator.next();
			checkNextColumnChild();
		} else {
			while (null == nextColumn && childrenIterator.hasNext()) {
				UIComponent child = childrenIterator.next();
				if (child instanceof UIColumn || child instanceof Column) {
					boolean rendered = true;
					try {
						rendered = child.isRendered();
					} catch (Exception e) {
						// This exception can be thrown for a header/footer
						// facets
						// there column rendered attribute was binded to a row
						// variable.
					}
					if (rendered) {
						Iterator<UIComponent> iterator = getColumnChildrenIterator(child);
						if (iterator.hasNext()) {
							currentColumnIterator = iterator;
							nextColumn = currentColumnIterator.next();
							checkNextColumnChild();
						}

					}
				} else if (checkAjaxComponent(child)) {
					nextColumn = child;
				}
			}
		}
		if (null == nextColumn) {
			nextColumn = getNextFacet();
		}
		return nextColumn;
	}

	/**
	 * @param nextColumn
	 * @return
	 */
	protected UIComponent getNextFacet() {
		UIComponent nextColumn = null;
		while (null == nextColumn && facetsIterator.hasNext()) {
			UIComponent child = facetsIterator.next();
			if (checkAjaxComponent(child)) {
				nextColumn = child;
			}
		}
		return nextColumn;
	}

	@Override
	protected boolean checkAjaxComponent(UIComponent child) {
		return !super.checkAjaxComponent(child);
	}

	@Override
	protected Iterator<UIComponent> getColumnChildrenIterator(UIComponent child) {
		return child.getFacets().values().iterator();
	}

	protected void checkNextColumnChild() {
		if (!currentColumnIterator.hasNext()) {
			currentColumnIterator = null;
		}
	}

}
