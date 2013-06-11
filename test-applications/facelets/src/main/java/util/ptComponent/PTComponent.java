package util.ptComponent;

import javax.faces.component.UIPanel;

import util.phaseTracker.PhaseTrackerComponent;

public class PTComponent {
	private UIPanel component = new PhaseTrackerComponent();
	
	
	public UIPanel getComponent() {
		return component;
	}

	public void setComponent(UIPanel component) {
		this.component = component;
	}
}
