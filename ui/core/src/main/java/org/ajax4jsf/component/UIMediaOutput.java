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

package org.ajax4jsf.component;

import javax.el.MethodExpression;

import javax.faces.component.UIOutput;
import javax.faces.el.MethodBinding;

import org.ajax4jsf.resource.ResourceComponent2;
import org.richfaces.webapp.taglib.MethodBindingMethodExpressionAdaptor;
import org.richfaces.webapp.taglib.MethodExpressionMethodBindingAdaptor;


/**
 * @author shura
 *
 */
public abstract class UIMediaOutput extends UIOutput implements ResourceComponent2 {
	
    public static final String COMPONENT_TYPE = "org.ajax4jsf.MMedia";

	/**
	 * Get URI attribute for resource ( src for images, href for links etc ).
	 * @return
	 */
	public abstract String getUriAttribute();

	/**
	 * Set URI attribute for resource ( src for images, href for links etc ).
	 * @param newvalue
	 */
	public abstract void setUriAttribute(String newvalue);
	
	/**
	 * Get Element name for rendering ( imj , a , object, applet ).
	 * @return
	 */
	public abstract String getElement();

	/**
	 * Set Element name for rendering ( imj , a , object, applet ).
	 * @param newvalue
	 */
	public abstract void setElement(String newvalue);
	
	/**
	 * Get EL binding to method in user bean to send resource. Method will
	 * called with two parameters - restored data object and servlet output
	 * stream.
	 * 
	 * @return MethodBinding to createContent 
	 */
	public MethodBinding getCreateContent() {
	    MethodBinding result = null;
	    MethodExpression me = getCreateContentExpression();

	    if (me != null) {
		// if the MethodExpression is an instance of our private
		// wrapper class.
		if (me.getClass().equals(MethodExpressionMethodBindingAdaptor.class)) {
		    result = ((MethodExpressionMethodBindingAdaptor) me).getBinding();
		} else {
		    // otherwise, this is a real MethodExpression.  Wrap it
		    // in a MethodBinding.
		    result = new MethodBindingMethodExpressionAdaptor(me);
		}
	    }

	    return result;
	}
	
	/**
	 * Set EL binding to method in user bean to send resource. Method will
	 * called with two parameters - restored data object and servlet output
	 * stream.
	 * 
	 * @param newvalue - new value of createContent method binding
	 */
	public void setCreateContent(MethodBinding newvalue) {
	    MethodExpressionMethodBindingAdaptor adapter;
	    if (newvalue != null) {
		adapter = new MethodExpressionMethodBindingAdaptor(newvalue);
		setCreateContentExpression(adapter);
	    } else {
		setCreateContentExpression(null);
	    }
	}

}
