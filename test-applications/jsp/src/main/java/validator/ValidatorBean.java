package validator;

import java.util.ArrayList;
import java.util.Calendar;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.ajax4jsf.event.AjaxEvent;
import org.ajax4jsf.model.KeepAlive;
import org.hibernate.validator.AssertFalse;
import org.hibernate.validator.AssertTrue;
import org.hibernate.validator.CreditCardNumber;
import org.hibernate.validator.Digits;
import org.hibernate.validator.EAN;
import org.hibernate.validator.Email;
import org.hibernate.validator.Future;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.Past;
import org.hibernate.validator.Pattern;
import org.hibernate.validator.Range;
import org.hibernate.validator.Size;
import org.richfaces.component.UIBeanValidator;

import util.componentInfo.ComponentInfo;


public class ValidatorBean implements TestValidable{
	@Email(message="Mail validation failed!")
	private String text;	
	@AssertFalse(message="AssertFalse validation failed!")
	private boolean booleanValue;	
	@AssertTrue(message="Asserttrue validation failed!")
	private boolean assertTrue;	
	@Future(message="Future validation failed!")
	private Calendar dateValue;	
	
	private int intValue;	
	@EAN(message="EAN validation failed")
	private String ean;	
	@CreditCardNumber(message="CreditCardNumber validation failed!")
	private String creditValue;	
	@Size(min=1,max=5,message="Size validation failed!")
	private ArrayList<SelectItem> sizeValues;
	private String sizeValue;
	private UIBeanValidator ajaxValidatorComponent = null;
	private boolean ajaxSingle;
	private String bindLabel;
	private boolean disableDefault;
	private String eventsQueue;
	private boolean ignoreDupResponses;
	private boolean immediate;
	private boolean limitToList;
	private UIComponent parentProperties = null;
	private boolean rendered;
	private boolean renderRegionOnly;
	private int requestDelay;
	private boolean selfRendered;
	private boolean submitted;
	private String summary;
	private int timeout;
	@Digits(integerDigits=1,fractionalDigits=3,message="Digits validation failed!")
	private double digit;	
	@Range(min=0,max=15,message="Range validation failed!")
	private String rangeValue;	
	@NotEmpty
	private String newValue;
	@Pattern(regex="^1234*$") //Searh "1234" as a whole word
	private String patternValue;
	private String temp;
	
	public String getTemp() {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getPatternValue() {
		return patternValue;
	}

	public void setPatternValue(String patternValue) {
		this.patternValue = patternValue;
	}

	public String getRangeValue() {
		return rangeValue;
	}

	public void setRangeValue(String rangeValue) {
		this.rangeValue = rangeValue;
	}

	public double getDigit() {
		return digit;
	}

	public void setDigit(double digit) {
		this.digit = digit;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public boolean isSubmitted() {
		return submitted;
	}

	public void setSubmitted(boolean submitted) {
		this.submitted = submitted;
	}

	public boolean isSelfRendered() {
		return selfRendered;
	}

	public void setSelfRendered(boolean selfRendered) {
		this.selfRendered = selfRendered;
	}

	public int getRequestDelay() {
		return requestDelay;
	}

	public void setRequestDelay(int requestDelay) {
		this.requestDelay = requestDelay;
	}

	public boolean isRenderRegionOnly() {
		return renderRegionOnly;
	}

	public void setRenderRegionOnly(boolean renderRegionOnly) {
		this.renderRegionOnly = renderRegionOnly;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

	

	public UIComponent getParentProperties() {
		return parentProperties;
	}

	public void setParentProperties(UIComponent parentProperties) {
		this.parentProperties = parentProperties;
	}

	public boolean isLimitToList() {
		return limitToList;
	}

	public void setLimitToList(boolean limitToList) {
		this.limitToList = limitToList;
	}

	public boolean isImmediate() {
		return immediate;
	}

	public void setImmediate(boolean immediate) {
		this.immediate = immediate;
	}

	public boolean isIgnoreDupResponses() {
		return ignoreDupResponses;
	}

	public void setIgnoreDupResponses(boolean ignoreDupResponses) {
		this.ignoreDupResponses = ignoreDupResponses;
	}

	public String getEventsQueue() {
		return eventsQueue;
	}

	public void setEventsQueue(String eventsQueue) {
		this.eventsQueue = eventsQueue;
	}

	public boolean isDisableDefault() {
		return disableDefault;
	}

	public void setDisableDefault(boolean disableDefault) {
		this.disableDefault = disableDefault;
	}

	public String getBindLabel() {
		return bindLabel;
	}

	public void setBindLabel(String bindLabel) {
		this.bindLabel = bindLabel;
	}

	public boolean isAjaxSingle() {
		return ajaxSingle;
	}

	public void setAjaxSingle(boolean ajaxSingle) {
		this.ajaxSingle = ajaxSingle;
	}

	public String add(){
		ComponentInfo info = ComponentInfo.getInstance();
		info.addField(ajaxValidatorComponent);
		return null;
	}	
	
	public UIBeanValidator getAjaxValidatorComponent() {
		return ajaxValidatorComponent;
	}

	public void setAjaxValidatorComponent(UIBeanValidator ajaxValidatorComponent) {
		this.ajaxValidatorComponent = ajaxValidatorComponent;
	}

	public String getSizeValue() {
		return sizeValue;
	}

	public void setSizeValue(String sizeValue) {
		this.sizeValue = sizeValue;
	}

	public ArrayList<SelectItem> getSizeValues() {
		return sizeValues;
	}

	public void setSizeValues(ArrayList<SelectItem> sizeValues) {
		this.sizeValues = sizeValues;
	}

	public String getCreditValue() {
		return creditValue;
	}

	public void setCreditValue(String creditValue) {
		this.creditValue = creditValue;
	}

	public String getEan() {
		return ean;
	}

	public void setEan(String ean) {
		this.ean = ean;
	}

	public ValidatorBean(){
		this.ajaxSingle = true;
		this.text = "mvitenkov@exadel.com";		
		this.patternValue = "1234";
		this.dateValue = Calendar.getInstance();		
		dateValue.set(Calendar.MONTH,8);
		dateValue.set(Calendar.DATE,5);
		dateValue.set(Calendar.YEAR,2050);
		this.booleanValue = false;
		this.ean = "97 81550 41198 0";
		this.creditValue = "visa maestro";
		this.assertTrue = true;
		this.rangeValue = "3.14159265";
		this.sizeValues = new ArrayList<SelectItem>();
		for(int i=0;i<6;i++){
			sizeValues.add(new SelectItem("value_"+i,"label_"+i));
		}
		this.sizeValue = sizeValues.get(0).getValue().toString();
		this.bindLabel = "click Binding";
		this.disableDefault = false;
		this.eventsQueue = "onchange";
		this.ignoreDupResponses = true;
		this.immediate = false;
		this.limitToList = false;		
		this.rendered = true;
		this.renderRegionOnly = true;
		this.requestDelay = 500;
		this.selfRendered = false;
		this.submitted = false;
		this.timeout = 2000;
		this.rangeValue = "3.14";
		this.newValue = "not empty";
		this.summary = "ajax_validator_summary";
		this.temp = "";
	}	
	
	public int getIntValue() {
		
		return intValue;
	}

	public String getText() {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
		return text;
	}
	
	public Calendar getDateValue() {
		
		return dateValue;
	}
	public boolean isBooleanValue() {
		
		return booleanValue;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setBooleanValue(boolean booleanValue) {
		this.booleanValue = booleanValue;
	}

	public void setDateValue(Calendar dateValue) {
		this.dateValue = dateValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	public boolean isAssertTrue() {
		return assertTrue;
	}

	public void setAssertTrue(boolean assertTrue) {
		this.assertTrue = assertTrue;
	}	

	public void checkBinding(ActionEvent actionEvent){
		FacesContext context = FacesContext.getCurrentInstance();
		bindLabel = ajaxValidatorComponent.getClientId(context);
	}
	//for ajaxListener test
	public void processAjax(AjaxEvent e){
		System.out.println("=========inside processAjax=============");
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
}
