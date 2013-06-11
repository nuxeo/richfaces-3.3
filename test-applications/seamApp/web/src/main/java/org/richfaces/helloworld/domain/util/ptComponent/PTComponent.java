package org.richfaces.helloworld.domain.util.ptComponent;

import javax.faces.component.UIPanel;

import org.richfaces.helloworld.domain.util.phaseTracker.PhaseTrackerComponent;;

public class PTComponent {
	private UIPanel component = new PhaseTrackerComponent();
	
	
	public UIPanel getComponent() {
		return component;
	}

	public void setComponent(UIPanel component) {
		this.component = component;
	}
}
