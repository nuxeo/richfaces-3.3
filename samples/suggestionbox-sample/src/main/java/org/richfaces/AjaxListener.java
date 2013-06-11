/**
 * 
 */
package org.richfaces;

import org.ajax4jsf.event.AjaxEvent;
import org.richfaces.component.AjaxSuggestionEvent;

/**
 * @author Nick Belaevski - nbelaevski@exadel.com
 * created 27.03.2007
 * 
 */
public class AjaxListener implements org.ajax4jsf.event.AjaxListener {

	/* (non-Javadoc)
	 * @see org.ajax4jsf.framework.ajax.AjaxListener#processAjax(org.ajax4jsf.framework.ajax.AjaxEvent)
	 */
	public void processAjax(AjaxEvent event) {
		System.out.println("AjaxListener.processAjax()");
		if (event instanceof AjaxSuggestionEvent) {
			AjaxSuggestionEvent ajaxSuggestionEvent = (AjaxSuggestionEvent) event;
			
			System.out.println("Submitted value: " + ajaxSuggestionEvent.getSubmittedValue());
		}
	}

}
