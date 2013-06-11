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

/*
 * DataTableTagMock.java		Date created: 14.12.2007
 * Last modified by: $Author$
 * $Revision$	$Date$
 */

package org.richfaces.jsp.tag;

import javax.faces.component.UIComponent;

import org.richfaces.taglib.DataTableTag;

/**
 * TODO Class description goes here.
 * @author Andrey Markavtsov
 *
 */
public class DataTableTagMock extends DataTableTag {
    
    /** Component to be returned by getComponentInstatnce method  */
    private UIComponent component;
       
    /**
     * TODO Description goes here.
     * @param component
     */
    public DataTableTagMock(UIComponent component) {
	super();
	this.component = component;
    }

    /* (non-Javadoc)
     * @see javax.faces.webapp.UIComponentClassicTagBase#getComponentInstance()
     */
    @Override
    public UIComponent getComponentInstance() {
	return component;
    }
    
    

}
