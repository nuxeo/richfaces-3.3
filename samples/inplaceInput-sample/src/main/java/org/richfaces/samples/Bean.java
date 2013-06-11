package org.richfaces.samples;

import javax.faces.event.ValueChangeEvent;

public class Bean {

	private boolean showControls;
	
	private boolean applyFromControlsOnly;
	
	private boolean selectOnEdit;
	
	private String defaultLabel = "Click...";
	
	private String controlsPosition="center";
	
	private Integer inputMaxLength = 255;
	
	private String width;
	
	private String minInputWidth = "40px";
	
	private String maxInputWidth = "150px" ;
	
	private String controlsHorizontalAlign="left";
	
	private String value = "New York";
	
	private String editEvent = "onclick";
	
	private String oneditactivation;
	
	private String onviewactivation;
	
	private String oneditactivated;
	
	private String onviewactivated;
		
	private String saveControlIcon;
	
	private String cancelControlIcon;
	
	private String onchangeScript;
	
	private int tabindex;

	public String getDefaultLabel() {
		return defaultLabel;
	}

	public void setDefaultLabel(String defaultLabel) {
		this.defaultLabel = defaultLabel;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getMinInputWidth() {
		return minInputWidth;
	}

	public void setMinInputWidth(String minInputWidth) {
		this.minInputWidth = minInputWidth;
	}

	public String getMaxInputWidth() {
		return maxInputWidth;
	}

	public void setMaxInputWidth(String maxInputWidth) {
		this.maxInputWidth = maxInputWidth;
	}

	public String getControlsHorizontalAlign() {
		return controlsHorizontalAlign;
	}

	public void setControlsHorizontalAlign(String controlsHorizontalAlign) {
		this.controlsHorizontalAlign = controlsHorizontalAlign;
	}

	public String getEditEvent() {
		return editEvent;
	}

	public void setEditEvent(String editEvent) {
		this.editEvent = editEvent;
	}

	public boolean isShowControls() {
		return showControls;
	}

	public void setShowControls(boolean showControls) {
		this.showControls = showControls;
	}

	public String getSaveControlIcon() {
		return saveControlIcon;
	}

	public void setSaveControlIcon(String saveControlIcon) {
		this.saveControlIcon = saveControlIcon;
	}

	public String getCancelControlIcon() {
		return cancelControlIcon;
	}

	public void setCancelControlIcon(String cancelControlIcon) {
		this.cancelControlIcon = cancelControlIcon;
	}

	public boolean getSelectOnEdit() {
		return selectOnEdit;
	}

	public void setSelectOnEdit(boolean selectOnEdit) {
		this.selectOnEdit = selectOnEdit;
	}

	public int getTabindex() {
		return tabindex;
	}

	public void setTabindex(int tabindex) {
		this.tabindex = tabindex;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isApplyFromControlsOnly() {
		return applyFromControlsOnly;
	}

	public void setApplyFromControlsOnly(boolean applyFromControlsOnly) {
		this.applyFromControlsOnly = applyFromControlsOnly;
	}

	public String getOneditactivation() {
		return oneditactivation;
	}

	public void setOneditactivation(String oneditactivation) {
		this.oneditactivation = oneditactivation;
	}

	public String getOnviewactivation() {
		return onviewactivation;
	}

	public void setOnviewactivation(String onviewactivation) {
		this.onviewactivation = onviewactivation;
	}

	public String getOneditactivated() {
		return oneditactivated;
	}

	public void setOneditactivated(String oneditactivated) {
		this.oneditactivated = oneditactivated;
	}

	public String getOnviewactivated() {
		return onviewactivated;
	}

	public void setOnviewactivated(String onviewactivated) {
		this.onviewactivated = onviewactivated;
	}

	public String getControlsPosition() {
		return controlsPosition;
	}

	public void setControlsPosition(String controlsPosition) {
		this.controlsPosition = controlsPosition;
	}

	public String getOnchangeScript() {
		return onchangeScript;
	}

	public void setOnchangeScript(String onchangeScript) {
		this.onchangeScript = onchangeScript;
	}

	public Integer getInputMaxLength() {
		return inputMaxLength;
	}

	public void setInputMaxLength(Integer inputMaxLength) {
		this.inputMaxLength = inputMaxLength;
	}
	
	public void valueChange(ValueChangeEvent event) {
		System.out.println("Bean.valueChange() " + event.getComponent());
	}
	
	public void action() {
	    System.out.println("enclosing_type.enclosing_method()");
	}
}
