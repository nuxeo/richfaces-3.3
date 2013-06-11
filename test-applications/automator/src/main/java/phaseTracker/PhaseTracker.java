package phaseTracker;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

/**
 * @author AYanul
 *
 */
public class PhaseTracker implements PhaseListener {
	/** The Constant serialVersionUID. */ 
    private static final long serialVersionUID = 6533052212003582848L;
    
    /** The Current JSF phase of the application. */ 
	public static PhaseId currentPhase = PhaseId.ANY_PHASE;
	
	/* (non-Javadoc)
	 * @see javax.faces.event.PhaseListener#afterPhase(javax.faces.event.PhaseEvent)
	 */
	public void afterPhase(PhaseEvent arg0) {
		System.out.println("PhaseTracker.afterPhase()" + arg0.getPhaseId());
		if (arg0.getPhaseId().toString().equals("RENDER_RESPONSE 6"))
			System.out.println("********** end lifecycle *************");
	}

	/* (non-Javadoc)
	 * @see javax.faces.event.PhaseListener#beforePhase(javax.faces.event.PhaseEvent)
	 */
	public void beforePhase(PhaseEvent arg0) {
		if (arg0.getPhaseId().toString().equals("RESTORE_VIEW 1"))
			System.out.println("********** begin lifecycle *************");
		currentPhase = arg0.getPhaseId();
		System.out.println("PhaseTracker.beforePhase()" + arg0.getPhaseId());
	}

	/* (non-Javadoc)
	 * @see javax.faces.event.PhaseListener#getPhaseId()
	 */
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}
}
