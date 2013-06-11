package inputNumberSlider;

import javax.faces.event.ValueChangeEvent;

import org.richfaces.component.html.HtmlInputNumberSlider;

import util.componentInfo.ComponentInfo;

public class InputNumberSlider {

	private boolean showArrows;
	private String orientation;
	private int inputSize;
	private int maxlength;
	private String requiredMessage;
	private String inputPosition;
	private String tabindex;
	private String value;
	private String height;
	private String width;
 	private String minValue;
	private String maxValue;
 	private String step;
 	private String btnLabel;
 	private String barStyle;
 	private String inputStyle;
 	private String tipStyle;
 	private String handleStyle;
 	private String valueChanged;
	private boolean immediate;
	private boolean required;
	private boolean showToolTip;
	private boolean disabled;
	private boolean showInput;
	private boolean rendered;
	private boolean enableManualInput;
	private boolean showBoundaryValues;
	private HtmlInputNumberSlider htmlInputNumberSlider = null;
	
	public void addHtmlInputNumberSlider(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(htmlInputNumberSlider);
	}
	
	public InputNumberSlider() {
		showArrows=true;
		orientation = "";
		immediate = false;
		rendered = false;
		showToolTip = false;
		requiredMessage = "requiredMessage work!";
		inputPosition = "";
		tabindex = "2";
		value = "30";
		inputSize = 5;
		maxlength = 3;
		width = "";
		height = "";
		minValue="10";
		maxValue="100";
		step="5";
		btnLabel="On";
		barStyle=null;
		inputStyle=null;
		tipStyle=null;
		handleStyle=null;
		disabled = false;
		showInput=true;
		rendered=true;
		enableManualInput = true;
		showBoundaryValues=true;
	}

	public void changeListener(ValueChangeEvent event) {
		System.out.println("valueChangeListener " + event.toString());
	}
	
	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isEnableManualInput() {
		return enableManualInput;
	}

	public void setEnableManualInput(boolean enableManualInput) {
		this.enableManualInput = enableManualInput;
	}

	public int getInputSize() {
		return inputSize;
	}

	public void setInputSize(int inputSize) {
		this.inputSize = inputSize;
	}

	public String getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

	public String getMinValue() {
		return minValue;
	}

	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}

	public boolean isShowBoundaryValues() {
		return showBoundaryValues;
	}

	public void setShowBoundaryValues(boolean showBoundaryValues) {
		this.showBoundaryValues = showBoundaryValues;
	}

	public boolean isShowInput() {
		return showInput;
	}

	public void setShowInput(boolean showInput) {
		this.showInput = showInput;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}


	public boolean isRendered() {
		return rendered;
	}


	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}
	
	public void doStyles()
	{
		if (getBarStyle() == null) {
			setBtnLabel("Off");
			setBarStyle("barStyle");
			setInputStyle("inputStyle");
			setTipStyle("tipStyle");
			setHandleStyle("handleStyle");
		} else {
			setBarStyle(null);
			setBtnLabel("On");
			setInputStyle(null);
			setTipStyle(null);
			setHandleStyle(null);
		}
	}


	public String getBarStyle() {
		return barStyle;
	}


	public void setBarStyle(String barStyle) {
		this.barStyle = barStyle;
	}


	public String getBtnLabel() {
		return btnLabel;
	}


	public void setBtnLabel(String btnLabel) {
		this.btnLabel = btnLabel;
	}


	public String getInputStyle() {
		return inputStyle;
	}


	public void setInputStyle(String inputStyle) {
		this.inputStyle = inputStyle;
	}


	public String getTipStyle() {
		return tipStyle;
	}


	public void setTipStyle(String tipStyle) {
		this.tipStyle = tipStyle;
	}


	public String getHandleStyle() {
		return handleStyle;
	}


	public void setHandleStyle(String handleStyle) {
		this.handleStyle = handleStyle;
	}


	public int getMaxlength() {
		return maxlength;
	}


	public void setMaxlength(int maxlength) {
		this.maxlength = maxlength;
	}

	public boolean isImmediate() {
		return immediate;
	}

	public void setImmediate(boolean immediate) {
		this.immediate = immediate;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isShowToolTip() {
		return showToolTip;
	}

	public void setShowToolTip(boolean showToolTip) {
		this.showToolTip = showToolTip;
	}

	public String getRequiredMessage() {
		return requiredMessage;
	}

	public void setRequiredMessage(String requiredMessage) {
		this.requiredMessage = requiredMessage;
	}

	public String getInputPosition() {
		return inputPosition;
	}

	public void setInputPosition(String inputPosition) {
		this.inputPosition = inputPosition;
	}

	public String getTabindex() {
		return tabindex;
	}

	public void setTabindex(String tabindex) {
		this.tabindex = tabindex;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public void handlerMethod(ValueChangeEvent event) {
		valueChanged = (String)event.getNewValue();
		System.out.println("strValueChangeListener: " + valueChanged);
	}
	
	public void bTest1(){
		setMinValue("0");
		setMaxValue("9");
		setInputSize(1);
		setInputPosition("right");
		setEnableManualInput(true);
		setValue("3");
		setShowInput(true);
		setStep("1");
		setShowToolTip(true);
		setShowBoundaryValues(true);
		setShowInput(true);
		setMaxlength(2);
	}
	
	public void bTest2(){
		setMinValue("1000");
		setMaxValue("99999");
		setInputSize(10);
		setInputPosition("left");
		setEnableManualInput(false);
		setValue("99999");
		setShowInput(true);
		setStep("50");
		setShowToolTip(false);
		setShowBoundaryValues(true);
		setMaxlength(5);
	}
	
	public void bTest3(){
		setMinValue("101");
		setMaxValue("203");
		setInputSize(4);
		setInputPosition("left");
		setEnableManualInput(false);
		setValue("102");
		setShowInput(true);
		setStep("2");
		setShowToolTip(false);
		setShowBoundaryValues(true);
		setMaxlength(6);
	}
	
	public void bTest4(){
		setMinValue("10");
		setMaxValue("12");
		setInputSize(2);
		setInputPosition("left");
		setEnableManualInput(false);
		setValue("102");
		setShowInput(false);
		setStep("2");
		setShowToolTip(false);
		setShowBoundaryValues(true);
		setMaxlength(2);
	}
	
	public void bTest5(){
		setMinValue("-99999");
		setMaxValue("99999");
		setInputSize(15);
		setInputPosition("right");
		setEnableManualInput(true);
		setValue("103");
		setShowInput(true);
		setStep("2");
		setShowToolTip(false);
		setShowBoundaryValues(false);	
		setMaxlength(8);
	}




	public HtmlInputNumberSlider getHtmlInputNumberSlider() {
		return htmlInputNumberSlider;
	}

	public void setHtmlInputNumberSlider(HtmlInputNumberSlider htmlInputNumberSlider) {
		this.htmlInputNumberSlider = htmlInputNumberSlider;
	}

	public String getValueChanged() {
		return valueChanged;
	}

	public void setValueChanged(String valueChanged) {
		this.valueChanged = valueChanged;
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public boolean isShowArrows() {
		return showArrows;
	}

	public void setShowArrows(boolean showArrows) {
		this.showArrows = showArrows;
	}
}
