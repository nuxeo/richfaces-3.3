package colorPicker;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.richfaces.component.html.HtmlColorPicker;

import util.componentInfo.ComponentInfo;

public class ColorPicker implements Validator, Converter {
	private HtmlColorPicker component;
	private String colorMode;// hex, rgb
	private String converterMessage;	
	private boolean flat;
	private boolean immediate;
	private boolean localValueSet;
	private boolean rendered;
	private boolean facets;
	private boolean required;
	private String requiredMessage;
	private boolean valid;
	private String validatorMessage;
	private String value;
	private String facetsValue;
	private String bindLabel;
	private String showEvent;

	public String getShowEvent() {
		return showEvent;
	}

	public void setShowEvent(String showEvent) {
		this.showEvent = showEvent;
	}

	public ColorPicker() {
		facets = false;
		colorMode = "rgb";
		converterMessage = "custom converter message";		
		flat = false;
		immediate = false;
		localValueSet = true;
		rendered = true;
		required = false;
		requiredMessage = "custom required message";
		valid = true;
		validatorMessage = "custom validator message";
		value = new String();
		facetsValue = new String(); 
		bindLabel = "Click Binding";
		showEvent = "onclick";
	}

	public void checkBinding(ActionEvent e){
		FacesContext context = FacesContext.getCurrentInstance();
		bindLabel = component.getClientId(context);
	}	
	
	public void changeValue(ValueChangeEvent e) {
		System.out.println("old value:" + e.getOldValue() + " new value:"
				+ e.getNewValue());
	}

	public void addColorPicker() {
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(component);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		System.out.println("<<<Color Picker Validator Works>>>");
		String str = value.toString();
		if (str.startsWith("rgb")) {
			if (str.indexOf("56") != -1)
				throw new ValidatorException(new FacesMessage(
						"Test validator: 100 is restricted!"));
		}
	}

	public Object getAsObject(FacesContext context, UIComponent component,
			String value) throws ConverterException {
		if (value.indexOf("100") != -1)
			throw new ConverterException(new FacesMessage("Test converter(getAsObject): 100 is restricted!"));
		return new String(value + " converted");
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object value) throws ConverterException {
		if (value.toString().indexOf("100") != -1)
			throw new ConverterException(new FacesMessage("Test converter(getAsString): 100 is restricted!"));
		return value.toString();
	}

	public HtmlColorPicker getComponent() {
		return component;
	}

	public void setComponent(HtmlColorPicker component) {
		this.component = component;
	}

	public String getColorMode() {
		return colorMode;
	}

	public void setColorMode(String colorMode) {
		this.colorMode = colorMode;
	}

	public String getConverterMessage() {
		return converterMessage;
	}

	public void setConverterMessage(String converterMessage) {
		this.converterMessage = converterMessage;
	}	

	public boolean isFlat() {
		return flat;
	}

	public void setFlat(boolean flat) {
		this.flat = flat;
	}

	public boolean isImmediate() {
		return immediate;
	}

	public void setImmediate(boolean immediate) {
		this.immediate = immediate;
	}

	public boolean isLocalValueSet() {
		return localValueSet;
	}

	public void setLocalValueSet(boolean localValueSet) {
		this.localValueSet = localValueSet;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
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

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getValidatorMessage() {
		return validatorMessage;
	}

	public void setValidatorMessage(String validatorMessage) {
		this.validatorMessage = validatorMessage;
	}

	public String getBindLabel() {
		return bindLabel;
	}

	public void setBindLabel(String bindLabel) {
		this.bindLabel = bindLabel;
	}

	public boolean isFacets() {
		return facets;
	}

	public void setFacets(boolean facets) {
		this.facets = facets;
	}

	public String getFacetsValue() {
		return facetsValue;
	}

	public void setFacetsValue(String facetsValue) {
		this.facetsValue = facetsValue;
	}


}
