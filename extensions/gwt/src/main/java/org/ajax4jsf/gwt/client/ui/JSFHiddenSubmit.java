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
import com.google.gwt.user.client.ui.Widget;

/**
 * Class created hidden html submit button for JSF form submission.
 * @author shura
 *
 */
public class JSFHiddenSubmit extends InputWidget {
	
	private String _id;

	/**
	 * 
	 */
	public JSFHiddenSubmit(String id) {
		super();
		_id = id;
		Element button = DOM.createButton();
		setInputName(button, id);
		setInputType(button,"submit");
		setElement(button);
		setSize("0","0");
		setVisible(false);
	}

	public void submit(String data) throws Exception {
		Element button = getElement();
		if(!isAttached()){
			Element componentElement = DOM.getElementById(_id);
			if(null == componentElement){
				throw new Exception("submit element must be attached to document");
			}
			DOM.appendChild(componentElement,button);
		}
		DOM.setAttribute(button,"value",data);
		click(button);
	}
	
    static native void click(Element button) /*-{
	    button.click();
	  }-*/;

}
