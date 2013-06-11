/**
 * 
 */
package org.richfaces.validator;

import java.util.Locale;
import java.util.Set;

import javax.faces.context.FacesContext;

/**
 * @author asmirnov
 *
 */
public class NullValidator extends ObjectValidator {

	/* (non-Javadoc)
	 * @see org.richfaces.validator.ObjectValidator#validate(java.lang.Object, java.lang.String, java.lang.Object, java.util.Locale)
	 */
	@Override
	protected String[] validate(Object base, String property, Object value,
			Locale locale, Set<String> profiles) {
		// do nothing.
		return null;
	}

	/* (non-Javadoc)
	 * @see org.richfaces.validator.ObjectValidator#validateGraph(javax.faces.context.FacesContext, java.lang.Object, java.util.Set)
	 */
	@Override
	public String[] validateGraph(FacesContext context, Object value,
			Set<String> profiles) {
		// do nothing
		return null;
	}

}
