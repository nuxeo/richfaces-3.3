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
package org.richfaces.taglib;

import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.ajax4jsf.webapp.taglib.HtmlComponentTagBase;
import org.richfaces.component.UIDataFltrSlider;

public abstract class DataFilterSliderTagBase extends HtmlComponentTagBase {

    /**
     * Constant String message
     */
    private static String ATTRIBUTE_DEPRECATION_MESSAGE = "Attribute onSlideSubmit is deprecated!";

    /**
     * Flag indicating that attribute 'onSlideSubmit' has already been set
     */
    private boolean isSubmitOnSlideSet = false;

    /*
     * submitOnSlide If the slider value changes must submit a form. Default
     * value is true.
     */
    private ValueExpression _submitOnSlide = null;

    /**
     * DEPRECATED(use submitOnSlide). If the slider value changes must submit a
     * form. Default value is true. Setter for onSlideSubmit
     * 
     * @param onSlideSubmit - new value
     */
    public void setOnSlideSubmit(ValueExpression onSlideSubmit) {
	FacesContext.getCurrentInstance().getExternalContext().log(ATTRIBUTE_DEPRECATION_MESSAGE);
	if (!isSubmitOnSlideSet && (_submitOnSlide != null)) {
	    logValueDeprecation(_submitOnSlide);
	}
	this._submitOnSlide = onSlideSubmit;
	isSubmitOnSlideSet = true;
    }

    /**
     * If the slider value changes must submit a form. Default value is true.
     * Setter for submitOnSlide
     * 
     * @param submitOnSlide - new value
     */
    public void setSubmitOnSlide(ValueExpression submitOnSlide) {
	if (isSubmitOnSlideSet) {
	    logValueDeprecation(this._submitOnSlide);
	}
	this._submitOnSlide = submitOnSlide;
    }

    private void logValueDeprecation(ValueExpression value) {
	StringBuilder builder = new StringBuilder();
	builder.append("submitOnSlide attribute has been already set for DataFilterSlider component with id=");
	builder.append(this.getId());
	builder.append(": [").append(value.getExpressionString()).append("]. ");
	builder.append(ATTRIBUTE_DEPRECATION_MESSAGE);

	FacesContext.getCurrentInstance().getExternalContext().log(builder.toString());
    }

    protected void setProperties(UIComponent component) {
	super.setProperties(component);

	if (this._submitOnSlide != null) {
	    if (this._submitOnSlide.isLiteralText()) {
		UIDataFltrSlider filterSlider = (UIDataFltrSlider) component;
		try {
		    Boolean __onSlideSubmit = (Boolean) getFacesContext().getApplication().getExpressionFactory()
			    .coerceToType(this._submitOnSlide.getExpressionString(), Boolean.class);

		    filterSlider.setSubmitOnSlide(__onSlideSubmit.booleanValue());
		} catch (ELException e) {
		    throw new FacesException(e);
		}
	    } else {
		component.setValueExpression("onSlideSubmit", this._submitOnSlide);
	    }
	}

    }

    public void release() {
	super.release();
	isSubmitOnSlideSet = false;
	_submitOnSlide = null;
    }

}
