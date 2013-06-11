package org.ajax4jsf.bean;

public class HotKeyBean {

	private String key;
	
	private String timing;
	
	public void init() {
		key = null;
		timing = "immediate";
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setTiming(String timing) {
		this.timing = timing;
	}

	public String getTiming() {
		return timing;
	}
}
