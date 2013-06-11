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

package org.richfaces.component.state.events;

import javax.faces.component.UIComponent;

import org.richfaces.model.TreeRowKey;

/**
 * @author Nick - mailto:nbelaevski@exadel.com
 * created 01.12.2006
 * 
 */
public abstract class TreeStateCommandNodeEvent extends TreeStateCommandEvent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8542148399346285106L;
	private TreeRowKey rowKey;

	public TreeStateCommandNodeEvent(UIComponent component, TreeRowKey rowKey) {
		super(component);
		this.rowKey = rowKey;
	}

	protected TreeRowKey getRowKey() {
		return rowKey;
	}

}
