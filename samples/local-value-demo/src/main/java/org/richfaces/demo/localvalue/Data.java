package org.richfaces.demo.localvalue;

import javax.faces.component.UIInput;

public class Data {
	private UIInput component;
	private String creditCard;
	private String myCreadtCard = "1234567887654321";
	private String myCreditCardName = "Visa";
	public String getMyCreditCardName() {
		return myCreditCardName;
	}
	public void setMyCreditCardName(String myCreditCardName) {
		this.myCreditCardName = myCreditCardName;
	}
	public UIInput getComponent() {
		return component;
	}
	public void setComponent(UIInput component) {
		this.component = component;
	}
	public String getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}
	public String getMyCreadtCard() {
		return myCreadtCard;
	}
	public void setMyCreadtCard(String myCreadtCard) {
		this.myCreadtCard = myCreadtCard;
	}
	public String useMyCreditCard_incorrect() {
		setCreditCard(getMyCreadtCard());
		return null;
	}
	public String useMyCreditCard_correct() {
		setCreditCard(getMyCreadtCard());
		getComponent().setSubmittedValue(null);
		getComponent().setValue(null);
		return null;
	}
}
