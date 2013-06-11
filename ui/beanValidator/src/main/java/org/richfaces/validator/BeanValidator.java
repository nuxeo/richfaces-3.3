/**
 * 
 */
package org.richfaces.validator;

import java.util.Locale;
import java.util.Set;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolation;
import javax.validation.MessageInterpolator;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorContext;
import javax.validation.ValidatorFactory;

/**
 * @author asmirnov
 * 
 */
public class BeanValidator extends ObjectValidator {

	private volatile ValidatorFactory validatorFactory = null;

	BeanValidator() {
		// Enforce class to load
		ValidatorFactory.class.getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.richfaces.validator.ObjectValidator#validate(java.lang.Object,
	 * java.lang.String, java.lang.Object, java.util.Locale)
	 */
	@Override
	protected String[] validate(Object base, String property, Object value,
			Locale locale, Set<String> profiles) {
		return extractMessages(getValidator(locale).validateProperty(base,
				property, getGroups(profiles)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.richfaces.validator.ObjectValidator#validateGraph(javax.faces.context
	 * .FacesContext, java.lang.Object, java.util.Set)
	 */
	@Override
	public String[] validateGraph(FacesContext context, Object value,
			Set<String> profiles) {
		Class<?>[] groups = getGroups(profiles);
		Set<ConstraintViolation<Object>> violations = getValidator(
				calculateLocale(context)).validate(value, groups);
		String[] messages = extractMessages(violations);
		return messages;
	}

	private Class<?>[] getGroups(Set<String> profiles) {
		Class<?> groups[] = null;
		if (null != profiles) {
			groups = new Class<?>[profiles.size()];
			int i = 0;
			for (String group : profiles) {
				try {
					groups[i] = Class.forName(group, false, Thread
							.currentThread().getContextClassLoader());
				} catch (ClassNotFoundException e) {
					try {
						groups[i] = Class.forName(group);
					} catch (ClassNotFoundException e1) {
						throw new FacesException(
								"Bean validation group not found " + group, e1);
					}
				}
				i++;
			}

		}
		return groups;
	}

	private String[] extractMessages(Set<ConstraintViolation<Object>> violations) {
		String[] messages = null;
		if (null != violations && violations.size() > 0) {
			messages = new String[violations.size()];
			int i = 0;
			for (ConstraintViolation<Object> constraintViolation : violations) {
				messages[i++] = constraintViolation.getMessage();
			}

		}
		return messages;
	}

	protected Validator getValidator(Locale locale) {
		validatorFactory = null;
		if (null == validatorFactory) {
			synchronized (this) {
				if (null == validatorFactory) {
					try {
						validatorFactory = Validation
								.buildDefaultValidatorFactory();
					} catch (ValidationException e) {
						throw new FacesException(
								"Could not build a default Bean Validator factory",
								e);
					}

				}
			}
		}

		ValidatorContext validatorContext = validatorFactory.usingContext();
		MessageInterpolator jsfMessageInterpolator = new JsfMessageInterpolator(
				locale, validatorFactory.getMessageInterpolator());
		validatorContext.messageInterpolator(jsfMessageInterpolator);
		Validator beanValidator = validatorContext.getValidator();
		return beanValidator;
	}

	private static class JsfMessageInterpolator implements MessageInterpolator {

		private Locale locale;
		private MessageInterpolator delegate;

		public JsfMessageInterpolator(Locale locale,
				MessageInterpolator delegate) {
			this.locale = locale;
			this.delegate = delegate;
		}

		public String interpolate(String messageTemplate, Context context) {
			return delegate.interpolate(messageTemplate, context, this.locale);
		}

		public String interpolate(String messageTemplate, Context context,
				Locale locale) {
			return delegate.interpolate(messageTemplate, context, locale);
		}

	}

}
