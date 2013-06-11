/**
 * 
 */
package org.richfaces.helloworld.domain.calendar;

import java.util.Calendar;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * @author Nick Belaevski
 *         mailto:nbelaevski@exadel.com
 *         created 21.07.2007
 *
 */
public class CalendarValidator implements Validator {

	/* (non-Javadoc)
	 * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		Date date = (Date) value;
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int d = calendar.get(Calendar.DATE);
			if (d == 13 || d == 17) {
				throw new ValidatorException(new FacesMessage("Test validator: 13th and 17th dates are restricted!"));
			}
		}
	}

}
