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

import javax.faces.component.UIComponent;

import org.ajax4jsf.component.SequenceDataAdaptor;

/**
 * JSF component class
 *	
 */
public abstract class UIDataList extends SequenceDataAdaptor {
	
	public static final String COMPONENT_TYPE = "org.richfaces.DataList";
	
	public static final String COMPONENT_FAMILY = "org.richfaces.DataList";
	
	/* (non-Javadoc)
	 * @see org.ajax4jsf.ajax.repeat.UIDataAdaptor#dataChildren()
	 */
	protected Iterator<UIComponent> dataChildren() {
		// TODO Auto-generated method stub
		return getChildren().iterator();
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.ajax.repeat.UIDataAdaptor#fixedChildren()
	 */
	protected Iterator<UIComponent> fixedChildren() {
		// TODO Auto-generated method stub
		return getFacets().values().iterator();
	}
}
