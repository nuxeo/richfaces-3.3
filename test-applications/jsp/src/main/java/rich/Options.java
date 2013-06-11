package rich;

public class Options {
	private boolean log;
	private boolean reDefault ;
	private boolean reComponent;
	private boolean reProperty;
	private boolean reStraightforward;
	private boolean attribute;
	
	public boolean isAttribute() {
		return attribute;
	}

	public void setAttribute(boolean attribute) {
		this.attribute = attribute;
	}

	public Options() {
		log = false;
		reDefault = true;
		reComponent = true;
		reProperty = true;
		reStraightforward = true;
		attribute = false;
	}
	
	public boolean isReComponent() {
		return reComponent;
	}
	public void setReComponent(boolean reComponent) {
		this.reComponent = reComponent;
	}
	public boolean isReProperty() {
		return reProperty;
	}
	public void setReProperty(boolean reProperty) {
		this.reProperty = reProperty;
	}
	public boolean isReStraightforward() {
		return reStraightforward;
	}
	public void setReStraightforward(boolean reStraightforward) {
		this.reStraightforward = reStraightforward;
	}

	public boolean isReDefault() {
		return reDefault;
	}

	public void setReDefault(boolean reDefault) {
		this.reDefault = reDefault;
	}

	/**
	 * @return the log
	 */
	public boolean isLog() {
		return log;
	}

	/**
	 * @param log the log to set
	 */
	public void setLog(boolean log) {
		this.log = log;
	}
}
