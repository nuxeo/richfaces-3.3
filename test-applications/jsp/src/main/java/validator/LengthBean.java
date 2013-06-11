/**
 * 
 */
package validator;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

/**
 * @author asmirnov
 *
 */
public class LengthBean implements Validable {
	
	@Length(max=10,min=4,message="{sizeFailure}!!!")	
	private String text;
	
	private int intValue;

	/**
	 * @return the text
	 */
	public LengthBean(){
		this.text = "12345";
	}
	
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the intValue
	 */
	public int getIntValue() {
		return intValue;
	}

	/**
	 * @param intValue the intValue to set
	 */
	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	public String getTextDescription() {
		return "Validate String Length, for a range 4-10 chars";
	}

	public String getIntDescription() {
		// TODO Auto-generated method stub
		return "Integer Value, no restrictions";
	}

}
