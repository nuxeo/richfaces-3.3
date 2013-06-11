package org.richfaces.demo.common;

import javax.faces.context.FacesContext;

public class ComponentDescriptor {
	private String id;
	private String name;
	private String group;
	private String captionImage;
	private String iconImage;
	private String devGuideLocation;
	private String tldDocLocation;
	private String javaDocLocation;
	private String demoLocation;
	private boolean current;
	
	public ComponentDescriptor() {
		this.id = "";
		this.name = "";
		this.captionImage = "";
		this.iconImage = "";
		this.devGuideLocation = "";
		this.tldDocLocation = "";
		this.javaDocLocation = "";
		this.current = false;
	}
	
	public String getCaptionImage() {
		return captionImage;
	}
	public void setCaptionImage(String captionImage) {
		this.captionImage = captionImage;
	}
	public String getDevGuideLocation() {
		return devGuideLocation;
	}
	public void setDevGuideLocation(String devGuideLocation) {
		this.devGuideLocation = devGuideLocation;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getJavaDocLocation() {
		return javaDocLocation;
	}
	public void setJavaDocLocation(String javaDocLocation) {
		this.javaDocLocation = javaDocLocation;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTldDocLocation() {
		return tldDocLocation;
	}
	public void setTldDocLocation(String tldDocLocation) {
		this.tldDocLocation = tldDocLocation;
	}

	public boolean isCurrent() {
		return current;
	}

	public void setCurrent(boolean current) {
		this.current = current;	
	}

	public String getIconImage() {
		return iconImage;
	}

	public void setIconImage(String iconImage) {
		this.iconImage = iconImage;
	}

	public String getDemoLocation() {
		return demoLocation;
	}

	public void setDemoLocation(String demoLocation) {
		this.demoLocation = demoLocation;
	}
	public String getContextRelativeDemoLocation() {
		FacesContext fc = FacesContext.getCurrentInstance();
		return fc.getExternalContext().getRequestContextPath()+getDemoLocation();
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

}
