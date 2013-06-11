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

package org.richfaces.ui.taglib;

import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;

import org.ajax4jsf.webapp.taglib.HtmlComponentTagBase;
import org.richfaces.ui.component.UIInsert;

/**
 * Abstract base tag class for the <rich:insert> tag.
 * 
 * @author Vladislav Baranov
 */
public abstract class InsertTagBase extends HtmlComponentTagBase {
    
    /** 
     * Defines the String, inserted with this component.
     * This attribute is alternative to "src" attribute.
     */
    private ValueExpression _content;
    
    /**
     * Defines the path to the file with source code
     */
    private ValueExpression _src;

    /**
     * Setter for content
     * 
     * @param content - new value
     */
    public void setContent(ValueExpression __content) {
	if (this._src == null) {
	    this._content = __content;
	} else {
	    throw new FacesException(UIInsert.ILLEGAL_ATTRIBUTE_VALUE_MESSAGE);
	}
    }

    /**
     * Defines the path to the file with source code.
     * Setter for src.
     * 
     * @param src - new value
     */
    public void setSrc(ValueExpression __src) {
	if (this._content == null) {
	    this._src = __src;
	} else {
	    throw new FacesException(UIInsert.ILLEGAL_ATTRIBUTE_VALUE_MESSAGE);
	}
    }
    
    public void release() {
	super.release();
	
	this._content = null;
	this._src = null;
    }
	
    /*
     * (non-Javadoc)
     * 
     * @see org.ajax4jsf.components.taglib.html.HtmlCommandButtonTagBase#setProperties(javax.faces.component.UIComponent)
     */
    protected void setProperties(UIComponent component) {	
	super.setProperties(component);
	UIInsert insertComponent = (UIInsert) component;

	if (this._content != null) {
	    if (this._content.isLiteralText()) {
		try {
		    String __content = (String) getFacesContext().getApplication().getExpressionFactory().coerceToType(
			    this._content.getExpressionString(), String.class);

		    insertComponent.setContent(__content);
		} catch (ELException e) {
		    throw new FacesException(e);
		}
	    } else {
		component.setValueExpression("content", this._content);
	    }
	}

	if (this._src != null) {
	    if (this._src.isLiteralText()) {
		try {
		    String __src = (String) getFacesContext().getApplication().getExpressionFactory().coerceToType(
			    this._src.getExpressionString(), String.class);

		    insertComponent.setSrc(__src);
		} catch (ELException e) {
		    throw new FacesException(e);
		}
	    } else {
		component.setValueExpression("src", this._src);
	    }
	}
    }

}
