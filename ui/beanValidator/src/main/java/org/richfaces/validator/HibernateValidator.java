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
package org.richfaces.validator;

import java.beans.FeatureDescriptor;
import java.util.Collection;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

import javax.el.ELResolver;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;

import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;

/**
 * Perform validation by Hibernate Validator annotations
 * 
 * @author asmirnov
 * 
 */
public class HibernateValidator extends ObjectValidator {

	private Map<ValidatorKey, ClassValidator<? extends Object>> classValidators = new ConcurrentHashMap<ValidatorKey, ClassValidator<? extends Object>>();

	HibernateValidator() {
		// This is a "singleton"-like class. Only factory methods allowed.
		// Enforce class to load
		ClassValidator.class.getName();
	}

	@Override
	@SuppressWarnings("unchecked")
	public String[] validateGraph(FacesContext context, Object value,
			Set<String> profiles) {
		if (null == context) {
			throw new FacesException(INPUT_PARAMETERS_IS_NOT_CORRECT);
		}
		String validationMessages[] = null;
		if (null != value) {
			ClassValidator<Object> validator = (ClassValidator<Object>) getValidator(
					value.getClass(), calculateLocale(context));
			if (validator.hasValidationRules()) {
				InvalidValue[] invalidValues = validator
						.getInvalidValues(value);
				if (null != invalidValues && invalidValues.length > 0) {
					validationMessages = new String[invalidValues.length];
					for (int i = 0; i < invalidValues.length; i++) {
						InvalidValue invalidValue = invalidValues[i];
						validationMessages[i] = invalidValue.getMessage();
					}
				}
			}

		}
		return validationMessages;
	}

	/**
	 * Validate bean property in the base class aganist new value.
	 * 
	 * @param beanClass
	 * @param property
	 * @param value
	 * @return
	 */
	protected InvalidValue[] validateClass(Class<? extends Object> beanClass,
			String property, Object value, Locale locale) {
		ClassValidator<? extends Object> classValidator = getValidator(
				beanClass, locale);
		InvalidValue[] invalidValues = classValidator
				.getPotentialInvalidValues(property, value);
		return invalidValues;
	}

	/**
	 * Get ( or create ) {@link ClassValidator} for a given bean class.
	 * 
	 * @param beanClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected ClassValidator<? extends Object> getValidator(
			Class<? extends Object> beanClass, Locale locale) {
		// TODO - localization support.
		ValidatorKey key = new ValidatorKey(beanClass, locale);
		ClassValidator result = classValidators.get(key);
		if (null == result) {
			result = createValidator(beanClass, locale);
			classValidators.put(key, result);
		}
		return result;
	}

	/*
	 * Method for create new instance of ClassValidator, if same not in cache.
	 * 
	 * @param beanClass - Class to validate @param locale - user Locale, used
	 * during validation process @return ClassValidator instance
	 */
	@SuppressWarnings("unchecked")
	protected ClassValidator<? extends Object> createValidator(
			Class<? extends Object> beanClass, Locale locale) {
		ResourceBundle bundle = getCurrentResourceBundle(locale);
		return bundle == null ? new ClassValidator(beanClass)
				: new ClassValidator(beanClass, bundle);
	}

	@Override
	protected String[] validate(Object base, String property, Object value,
			Locale locale, Set<String> profiles) {
				InvalidValue[] invalidValues = validateBean(base, property, value,
						locale);
				if (null == invalidValues) {
					return null;
				} else {
					String[] result = new String[invalidValues.length];
					for (int i = 0; i < invalidValues.length; i++) {
						InvalidValue invalidValue = invalidValues[i];
						result[i] = invalidValue.getMessage();
					}
					return result;
				}
			}

	/**
	 * Validate bean property of the base object aganist new value
	 * 
	 * @param base
	 * @param property
	 * @param value
	 * @return
	 */
	protected InvalidValue[] validateBean(Object base, String property, Object value,
			Locale locale) {
				Class<? extends Object> beanClass = base.getClass();
				InvalidValue[] invalidValues = validateClass(beanClass, property,
						value, locale);
				return invalidValues;
			}

	
}
