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

package org.ajax4jsf.gwt.client.ui;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

/**
 * Create widget associated with hidden html input element.
 * @author shura
 *
 */
public class InputHidden extends InputWidget implements HasText {

	/**
	 * 
	 */
	public InputHidden() {
		super();
		Element hidden = DOM.createElement("input");
		setInputType(hidden,"hidden");
		setElement(hidden);
	}

	/**
	 * Create named hidden input field.
	 * @param name
	 */
	public InputHidden(String name){
		this();
		setName(name);
//		DOM.setAttribute(getElement(),"id",name);
	}

	/* (non-Javadoc)
	 * @see com.google.gwt.user.client.ui.HasText#setText(java.lang.String)
	 */
	  public String getText() {
		    return DOM.getAttribute(getElement(), "value");
		  }

	  public void setText(String text) {
		    DOM.setAttribute(getElement(), "value", text);
		  }

	  public String getName() {
			    return DOM.getAttribute(getElement(), "name");
			  }

	  public void setName(String name) {
			    setInputName(getElement(),  name);
			  }
}
