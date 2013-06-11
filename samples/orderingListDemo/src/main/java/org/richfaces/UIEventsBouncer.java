/**
 * 
 */
package org.richfaces;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.event.PhaseId;

import org.ajax4jsf.component.UIDataAdaptor;

public final class UIEventsBouncer extends UIOutput {
	private PhaseId phaseId;
	
	final class Event extends FacesEvent {

		/**
		 * 
		 */
		private static final long serialVersionUID = -1390611902280951049L;

		private Object value;

		private String clientId;
		
		public Event(UIComponent component, Object value, String clientId, PhaseId phaseId) {
			super(component);
			
			this.value = value;
			this.clientId = clientId;
			
			setPhaseId(phaseId);
		}
		
		public boolean isAppropriateListener(FacesListener listener) {
			return false;
		}

		public void processListener(FacesListener listener) {
			throw new UnsupportedOperationException();
		}
		
		public Object getValue() {
			return value;
		}
		
		public String getClientId() {
			return clientId;
		}
	}

	private UIDataAdaptor getList() {
		UIComponent component = getParent();
		while (component != null && !(component instanceof UIDataAdaptor)) {
			component = component.getParent();
		}
		
		return (UIDataAdaptor) component;
	}

	private void queueEvent(PhaseId phaseId) {
		UIDataAdaptor list = getList();
		if (list.isRowAvailable()) {
			new Event(this, list.getRowData(), 
					getList().getClientId(FacesContext.getCurrentInstance()), phaseId).queue();
		}
	}

	public void processDecodes(FacesContext context) {
		super.processDecodes(context);

		this.phaseId = PhaseId.APPLY_REQUEST_VALUES;
		
		queueEvent(PhaseId.PROCESS_VALIDATIONS);
		queueEvent(PhaseId.UPDATE_MODEL_VALUES);
		queueEvent(PhaseId.INVOKE_APPLICATION);
	}

	public void processUpdates(FacesContext context) {
		super.processUpdates(context);

		this.phaseId = PhaseId.UPDATE_MODEL_VALUES;
		
		queueEvent(PhaseId.INVOKE_APPLICATION);
	}

	public void processValidators(FacesContext context) {
		super.processValidators(context);
		
		this.phaseId = PhaseId.PROCESS_VALIDATIONS;

		queueEvent(PhaseId.UPDATE_MODEL_VALUES);
		queueEvent(PhaseId.INVOKE_APPLICATION);
	}

	public void broadcast(FacesEvent event)
			throws AbortProcessingException {
		if (event instanceof Event) {
			String cid = getList().getClientId(FacesContext.getCurrentInstance());
			if (!cid.equals(((Event) event).getClientId())) {
				System.out.println(cid + " !!! " + ((Event) event).getClientId());
				System.out.println(phaseId + " " + event.getPhaseId());
				FacesMessage message = new FacesMessage("Client ids mismatch: " + cid + " !!! " + ((Event) event).getClientId());
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage(cid, message);
			}

			UIDataAdaptor list = getList();
			if (list.isRowAvailable()) {
				Object rd = list.getRowData();
				if (!rd.equals(((Event) event).getValue())) {
					System.out.println(rd + " !!! " + ((Event) event).getValue());
					System.out.println(phaseId + " " + event.getPhaseId());
					FacesMessage message = new FacesMessage("Data mismatch: " + rd + " !!! " + ((Event) event).getValue());
					message.setSeverity(FacesMessage.SEVERITY_ERROR);
					FacesContext.getCurrentInstance().addMessage(cid, message);
				}
			}
		} else {
			super.broadcast(event);
		}
	}
}