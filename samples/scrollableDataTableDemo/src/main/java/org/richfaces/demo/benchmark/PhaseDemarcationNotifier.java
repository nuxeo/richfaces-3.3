/**
 * 
 */
package org.richfaces.demo.benchmark;

import java.util.Map;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Maksim Kaszynski
 *
 */
public class PhaseDemarcationNotifier implements PhaseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6533052212003582848L;
	private final Log log = LogFactory.getLog(PhaseDemarcationNotifier.class);
	
	/* (non-Javadoc)
	 * @see javax.faces.event.PhaseListener#afterPhase(javax.faces.event.PhaseEvent)
	 */
	public void afterPhase(PhaseEvent event) {

		long l = ((Long) getRequestMap(event).get(event.getPhaseId().toString())).longValue();
		if (log.isTraceEnabled()) {
			log.trace("Phase end " + event.getPhaseId());
		}
		
		if (log.isInfoEnabled()) {
			log.info(event.getPhaseId() + " took " + (System.currentTimeMillis() - l) + "ms");
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.event.PhaseListener#beforePhase(javax.faces.event.PhaseEvent)
	 */
	public void beforePhase(PhaseEvent event) {
		if (log.isTraceEnabled()) {
			log.trace("Phase start " + event.getPhaseId());
		}
		getRequestMap(event).put(event.getPhaseId().toString(), new Long(System.currentTimeMillis()));
		
	}

	/* (non-Javadoc)
	 * @see javax.faces.event.PhaseListener#getPhaseId()
	 */
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}
	
	private Map getRequestMap(PhaseEvent event) {
		return event.getFacesContext().getExternalContext().getRequestMap();
	}

}
