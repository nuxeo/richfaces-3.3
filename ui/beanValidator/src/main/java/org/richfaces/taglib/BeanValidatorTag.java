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

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.servlet.jsp.JspException;

import org.richfaces.validator.HibernateValidator;
import org.richfaces.validator.FacesBeanValidator;

public class BeanValidatorTag extends javax.faces.webapp.ValidatorELTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5230299574915210593L;
	// Fields

	/*
	 * summary Summary message for a validation errors.
	 */
	private ValueExpression _summary;

    /**
     * <p>The {@link javax.el.ValueExpression} that evaluates to an object that
     * implements {@link HibernateValidator}.</p>
     */
    private ValueExpression binding = null;

    /**
	 * Summary message for a validation errors. Setter for summary
	 * 
	 * @param summary
	 *            - new value
	 */
	public void setSummary(ValueExpression __summary) {
		this._summary = __summary;
	}

	/**
     * <p>Set the expression that will be used to create a
     * {@link javax.el.ValueExpression} that references a backing bean property
     * of the {@link HibernateValidator} instance to be created.</p>
     *
     * @param binding The new expression
	 */
	public void setBinding(ValueExpression binding) {
		this.binding = binding;
	}
	
	protected Validator createValidator() throws JspException {
		ValueExpression ve = this.binding;
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		
		FacesBeanValidator validator = null;
		
		try {
			ELContext elContext = facesContext.getELContext();
			if (ve != null) {
				validator = (FacesBeanValidator) ve.getValue(elContext);
			}

			if (validator == null) {
				validator = (FacesBeanValidator) FacesContext
					.getCurrentInstance().getApplication().createValidator(
					"org.richfaces.BeanValidator");

				if (ve != null && validator != null) {
					ve.setValue(elContext, validator);
				}
			}
		} catch (Exception e) {
			throw new FacesException(e);
		}
		
		_setProperties(validator);
		
		return validator;
	}

	// Support method to wire in properties
	private void _setProperties(FacesBeanValidator validator)
			throws JspException {
		if (_summary != null) {
			if (_summary instanceof ValueExpression) {
				validator.setSummary(_summary);
			}
		}
	}
}
