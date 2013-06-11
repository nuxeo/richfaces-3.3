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
package org.richfaces.component.html;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.richfaces.validator.FacesBeanValidator;

/**
 * @author asmirnov
 * 
 */
public class HtmlInputText extends javax.faces.component.html.HtmlInputText {

	@Override
	protected void validateValue(FacesContext context, Object newValue) {
		// If our value is valid, enforce the required property if present
		if (isValid() && isRequired() && isEmpty(newValue)) {
			super.validateValue(context, newValue);
		}
		// If our value is valid and not empty, call all validators
		if (isValid()) {
			Validator[] validators = this.getValidators();
			if (validators != null) {
				for (Validator validator : validators) {
					try {
						if (validator instanceof FacesBeanValidator
								|| !isEmpty(newValue)) {
							validator.validate(context, this, newValue);
						}
					} catch (ValidatorException ve) {
						// If the validator throws an exception, we're
						// invalid, and we need to add a message
						setValid(false);
						FacesMessage message;
						String validatorMessageString = getValidatorMessage();

						if (null != validatorMessageString) {
							message = new FacesMessage(
									FacesMessage.SEVERITY_ERROR,
									validatorMessageString,
									validatorMessageString);
							message.setSeverity(FacesMessage.SEVERITY_ERROR);
						} else {
							message = ve.getFacesMessage();
						}
						if (message != null) {
							context.addMessage(getClientId(context), message);
						}
					}
				}
			}
		}

	}

	private static boolean isEmpty(Object value) {

		if (value == null) {
			return (true);
		} else if ((value instanceof String) && (((String) value).length() < 1)) {
			return (true);
		} else if (value.getClass().isArray()) {
			if (0 == java.lang.reflect.Array.getLength(value)) {
				return (true);
			}
		} else if (value instanceof List) {
			if (((List) value).isEmpty()) {
				return (true);
			}
		}
		return (false);
	}

}
