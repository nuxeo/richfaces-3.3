package org.richfaces.demo.converters.utils;

import javax.faces.application.FacesMessage;

public class ConverterUtils {

	private static final String CONVERSATION_ERROR = "Conversation error: ";	
	
	public static final int ERROR_1 = 0;
	
	public static final int ERROR_2 = 1;

	
	public static FacesMessage createTypeErrorMessage(String received, String expected, int type){
		
		String summary = null; 
		
		switch (type) {
		case ERROR_1:
			summary = CONVERSATION_ERROR + " found " + expected + " found " + received;
			break;
		case ERROR_2:
			summary = CONVERSATION_ERROR + " found " +  received;
			break;
		}
				   
		
		FacesMessage errorMessage = new FacesMessage();
		errorMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
		errorMessage.setSummary(summary);
		
		return errorMessage;
	}
}
