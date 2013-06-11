/**
 * 
 */
package org.richfaces;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

/**
 * @author Nick Belaevski - nbelaevski@exadel.com
 * created 21.03.2007
 * 
 */
public class TabChangeListener implements ValueChangeListener {

	/* (non-Javadoc)
	 * @see javax.faces.event.ValueChangeListener#processValueChange(javax.faces.event.ValueChangeEvent)
	 */
	public void processValueChange(ValueChangeEvent event)
			throws AbortProcessingException {

		System.out.println("TabChangeListener.processValueChange()");
		System.out.println("Old value: " + event.getOldValue());
		System.out.println("New value: " + event.getNewValue());
	}

}
