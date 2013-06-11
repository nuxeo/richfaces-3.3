package inputNumberSpinner;

import javax.faces.event.ValueChangeEvent;

import org.richfaces.component.html.HtmlInputNumberSpinner;

import util.componentInfo.ComponentInfo;

public class InputNumberSpinner {

	// private String size;
	// private String controlPosition;
	private int inputSize;
	private String tabindex;
	private String max;
	private String min;
	private String value;
	private String step;
	private String inputStyle;
	private String style;
	private String btnLabel = "ON";
	private boolean cycled;
	private boolean disabled;
	private boolean manualInput;
	private boolean rendered;
	private HtmlInputNumberSpinner htmlInputNumberSpinner = null;
 	private String valueChangeListener;
	
	public void addHtmlInputNumberSpinner(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlInputNumberSpinner);
	}

	public InputNumberSpinner() {
		// size= "200";
		inputSize = 2;
		cycled = false;
		disabled = false;
		manualInput = true;
		max = "100";
		min = "10";
		// value="50";
		// controlPosition="outside";
		step = "1";
		rendered = true;
		style = null;
		inputStyle = null;

	}
	
	public void changeListener(ValueChangeEvent event) {
		System.out.println("valueChangeListener " + event.toString());
	}

	public void doStyles() {
		if (getStyle() == null) {
			setBtnLabel("OFF");
			setStyle("style");
			setInputStyle("inputStyle");

		} else {
			setStyle(null);
			setBtnLabel("ON");
			setInputStyle(null);

		}
	}

	/*
	 * public String getControlPosition() { return controlPosition; } public
	 * void setControlPosition(String controlPosition) { this.controlPosition =
	 * controlPosition; }
	 */

	public boolean isCycled() {
		return cycled;
	}

	public void setCycled(boolean cycled) {
		this.cycled = cycled;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isManualInput() {
		return manualInput;
	}

	public void setManualInput(boolean manualInput) {
		this.manualInput = manualInput;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	/*
	 * public String getSize() { return size; } public void setSize(String size) {
	 * this.size = size; }
	 */

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	public String getInputStyle() {
		return inputStyle;
	}

	public void setInputStyle(String inputStyle) {
		this.inputStyle = inputStyle;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getBtnLabel() {
		return btnLabel;
	}

	public void setBtnLabel(String btnLabel) {
		this.btnLabel = btnLabel;
	}

	public int getInputSize() {
		return inputSize;
	}

	public void setInputSize(int inputSize) {
		this.inputSize = inputSize;
	}

	public String getTabindex() {
		return tabindex;
	}

	public void setTabindex(String tabindex) {
		this.tabindex = tabindex;
	}

	public void bTest1(){
		setCycled(true);
		setManualInput(true);
		setInputSize(5);
		setMin("0");
		setMax("20");
		setStep("1");
		setTabindex("2");
		setValue("1");
	}
	
	public void bTest2(){
		setCycled(false);
		setManualInput(false);
		setInputSize(10);
		setMin("1000");
		setMax("99999");
		setStep("255");
		setTabindex("5");
		setValue("5555");
	}
	
	public void bTest3(){
		setCycled(true);
		setManualInput(false);
		setInputSize(3);
		setMin("101");
		setMax("202");
		setStep("2");
		setTabindex("2");
		setValue("102");
	}
	
	public void bTest4(){
		setCycled(false);
		setManualInput(true);
		setInputSize(4);
		setMin("33");
		setMax("66");
		setStep("11");
		setTabindex("3");
		setValue("55");
	}
	
	public void bTest5(){
		setCycled(true);
		setManualInput(true);
		setInputSize(15);
		setMin("-99999");
		setMax("99999");
		setStep("33");
		setTabindex("5");
		setValue("101");
	}

	public HtmlInputNumberSpinner getHtmlInputNumberSpinner() {
		return htmlInputNumberSpinner;
	}

	public void setHtmlInputNumberSpinner(
			HtmlInputNumberSpinner htmlInputNumberSpinner) {
		this.htmlInputNumberSpinner = htmlInputNumberSpinner;
	}
	
	public void valueChangeListener(ValueChangeEvent event) {
		valueChangeListener = (String)event.getNewValue();
		System.out.println("valueChangeListener: " + valueChangeListener);
	}
	
	public String getValueChangeListener() {
		return valueChangeListener;
	}
}
