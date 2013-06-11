package validator;

import java.util.Calendar;


public interface TestValidable {
	
	public String getText();
		
	public int getIntValue();

	public boolean isBooleanValue();
	
	public Calendar getDateValue();
}
