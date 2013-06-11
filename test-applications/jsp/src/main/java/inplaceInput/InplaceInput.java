package inplaceInput;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.richfaces.component.html.HtmlInplaceInput;

import util.componentInfo.ComponentInfo;

public class InplaceInput {
	private String inputMaxLength;
	private String inputWidth;
	private String maxInputWidth;
	private String minInputWidth;
	private boolean required;
	private String requiredMessage;
	private int tabindex;
	private String editEvent;
	private String defaultLabel;
	private String controlsVerticalPosition;
	private String controlsHorizontalPosition;
	private String value;
	private boolean selectOnEdit;
	private boolean showControls;
	private boolean rendered;
	private boolean immediate;
	private String valueCL;
	private HtmlInplaceInput htmlInplaceInput = null;
	private String bindLabel;
	private String layout;

	private int maxLength;

	public InplaceInput() {
		inputMaxLength = "200";
		inputWidth = "150";
		maxInputWidth = "250";
		minInputWidth = "100";
		required = false;
		requiredMessage = "requiredMessage";
		tabindex = 1;
		editEvent = "onclick";
		defaultLabel = "defaultLabel";
		controlsVerticalPosition = "top";
		controlsHorizontalPosition = "left";
		value = "errors";
		selectOnEdit = false;
		showControls = false;
		rendered = true;
		immediate = false;
		valueCL = "---";
		bindLabel = "Click Binding";
		layout = "inline";

		maxLength = 6;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	public HtmlInplaceInput getHtmlInplaceInput() {
		return htmlInplaceInput;
	}

	public void setHtmlInplaceInput(HtmlInplaceInput myInplaceInput) {
		this.htmlInplaceInput = myInplaceInput;
	}

	public void addHtmlInplaceInput() {
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlInplaceInput);
	}

	public String getBindLabel() {
		return bindLabel;
	}

	public void setBindLabel(String bindLabel) {
		this.bindLabel = bindLabel;
	}

	public void checkBinding(ActionEvent actionEvent) {
		FacesContext context = FacesContext.getCurrentInstance();
		bindLabel = htmlInplaceInput.getClientId(context);
	}

	public void valueChangeListener(ValueChangeEvent event) {
		valueCL = "valueChangeListener work!";
	}

	public String getInputMaxLength() {
		return inputMaxLength;
	}

	public void setInputMaxLength(String inputMaxLength) {
		this.inputMaxLength = inputMaxLength;
	}

	public String getInputWidth() {
		return inputWidth;
	}

	public void setInputWidth(String inputWidth) {
		this.inputWidth = inputWidth;
	}

	public String getMaxInputWidth() {
		return maxInputWidth;
	}

	public void setMaxInputWidth(String maxInputWidth) {
		this.maxInputWidth = maxInputWidth;
	}

	public String getMinInputWidth() {
		return minInputWidth;
	}

	public void setMinInputWidth(String minInputWidth) {
		this.minInputWidth = minInputWidth;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getRequiredMessage() {
		return requiredMessage;
	}

	public void setRequiredMessage(String requiredMessage) {
		this.requiredMessage = requiredMessage;
	}

	public int getTabindex() {
		return tabindex;
	}

	public void setTabindex(int tabindex) {
		this.tabindex = tabindex;
	}

	public String getEditEvent() {
		return editEvent;
	}

	public void setEditEvent(String editEvent) {
		this.editEvent = editEvent;
	}

	public String getDefaultLabel() {
		return defaultLabel;
	}

	public void setDefaultLabel(String defaultLabel) {
		this.defaultLabel = defaultLabel;
	}

	public String getControlsVerticalPosition() {
		return controlsVerticalPosition;
	}

	public void setControlsVerticalPosition(String controlsVerticalPosition) {
		this.controlsVerticalPosition = controlsVerticalPosition;
	}

	public String getControlsHorizontalPosition() {
		return controlsHorizontalPosition;
	}

	public void setControlsHorizontalPosition(String controlsHorizontalPosition) {
		this.controlsHorizontalPosition = controlsHorizontalPosition;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isSelectOnEdit() {
		return selectOnEdit;
	}

	public void setSelectOnEdit(boolean selectOnEdit) {
		this.selectOnEdit = selectOnEdit;
	}

	public boolean isShowControls() {
		return showControls;
	}

	public void setShowControls(boolean showControls) {
		this.showControls = showControls;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public boolean isImmediate() {
		return immediate;
	}

	public void setImmediate(boolean immediate) {
		this.immediate = immediate;
	}

	public String getValueCL() {
		return valueCL;
	}

	public void setValueCL(String valueCL) {
		this.valueCL = valueCL;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}
}
