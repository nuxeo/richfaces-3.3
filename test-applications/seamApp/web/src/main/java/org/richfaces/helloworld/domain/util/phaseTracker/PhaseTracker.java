/**
 * 
 */
package org.richfaces.helloworld.domain.util.phaseTracker;

import java.util.Iterator;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

/**
 * @author AYanul
 *
 */
public class PhaseTracker implements PhaseListener {

	/* (non-Javadoc)
	 * @see javax.faces.event.PhaseListener#afterPhase(javax.faces.event.PhaseEvent)
	 */
	public void afterPhase(PhaseEvent arg0) {
		System.out.println("PhaseTracker.afterPhase()" + arg0.getPhaseId());
	}

	/* (non-Javadoc)
	 * @see javax.faces.event.PhaseListener#beforePhase(javax.faces.event.PhaseEvent)
	 */
	public void beforePhase(PhaseEvent arg0) {
		System.out.println("PhaseTracker.beforePhase()" + arg0.getPhaseId());
//		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
//		Iterator names = externalContext.getRequestParameterNames();
//		while (names.hasNext()) {
//			System.out.println(names.next());
//		}
		
	}

	/* (non-Javadoc)
	 * @see javax.faces.event.PhaseListener#getPhaseId()
	 */
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

}
