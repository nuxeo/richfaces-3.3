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
package org.richfaces.event.sort;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;

/**
 * @author Maksim Kaszynski
 *
 */
public class SortEvent2 extends FacesEvent{

	private static final long serialVersionUID = -3141067055845421505L;

	//TODO: making ValueExpression here - should redesign SortOrder/SortField?
	private ValueExpression sortExpression;
	
	private boolean force;

	public SortEvent2(UIComponent component, ValueExpression sortExpression,
			boolean force) {
		super(component);
		this.sortExpression = sortExpression;
		this.force = force;
	}
	
	public SortEvent2(UIComponent component, ValueExpression sortExpression) {
		this(component, sortExpression, false);
	}
	
	@Override
	public boolean isAppropriateListener(FacesListener listener) {
		return listener instanceof SortListener2;
	}
	
	@Override
	public void processListener(FacesListener listener) {
		((SortListener2) listener).processSorting(this);
	}
	
	public boolean isForce() {
		return force;
	}
	
	public ValueExpression getSortExpression() {
		return sortExpression;
	}
}
