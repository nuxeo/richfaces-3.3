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

package org.richfaces.ui.component;

import javax.faces.component.UIComponentBase;

/**
 * JSF component class
 * 
 */
public abstract class UIInsert extends UIComponentBase {

    	/**
    	 * Message of the Exception thrown in case both "content" and "src"
    	 * attributes are set on the page
     	 */
    	public static final String ILLEGAL_ATTRIBUTE_VALUE_MESSAGE = "Only one attribute "
	    + "should be set for the <rich:insert> component: either \"content\" or \"src\"."; 
    
	private static final String COMPONENT_TYPE = "org.richfaces.ui.Insert";

	private static final String COMPONENT_FAMILY = "org.richfaces.ui.Insert";


	public abstract String getSrc();

	public abstract void setSrc(String src);
	
	public abstract String getContent();

	public abstract void setContent(String content);

	public abstract String getHighlight();

	public abstract void setHighlight(String highlight);
	
	public abstract String getErrorContent();
	
	public abstract void setErrorContent(String src);

	public abstract String getEncoding();
	
	public abstract void setEncoding(String encoding);
}
