package org.richfaces.demo;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class SelectValidator implements Validator {
	
	public SelectValidator() {
	}
	
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		System.out.println("SelectValidator.validate()");
		
		String selectedValue = (String) value;
		String clientId = component.getClientId(context);
		int componentNum = componentNum(component.getId());		
		if(selectedValue.equals("warn")){
			
			FacesMessage message = new FacesMessage();
			message.setSummary("warning SUMMARY for: " + componentNum + " input");
			message.setDetail("warning DETAIL for: " + componentNum + " input");
			message.setSeverity(FacesMessage.SEVERITY_WARN);
			context.addMessage(clientId, message);
						
			
		}else if(selectedValue.equals("error")){
			
			FacesMessage message = new FacesMessage();
			message.setSummary("error SUMMARY for: " + componentNum + " input");
			message.setDetail("error DETAIL for: " + componentNum + " input");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			
			context.addMessage(clientId, message);
			
		}else if(selectedValue.equals("info")){
			
			FacesMessage message = new FacesMessage();
			message.setSummary("info SUMMARY for: " + componentNum + " input");
			message.setDetail("info DETAIL for: " + componentNum + " input");
			message.setSeverity(FacesMessage.SEVERITY_INFO);
			
			context.addMessage(clientId, message);
			
		}else if(selectedValue.equals("fatal")){
			
			FacesMessage message = new FacesMessage();
			message.setSummary("fatal SUMMARY for: " + componentNum + " input");
			message.setDetail("fatal DETAIL for:" + componentNum + " input");
			message.setSeverity(FacesMessage.SEVERITY_FATAL);
			
			context.addMessage(clientId, message);
		}
	}
	
	public int componentNum(String baseId){
		
		if(baseId.endsWith("1")){
			return 1;
		}else if(baseId.endsWith("2")){
			return 2;
		}else if (baseId.endsWith("3")){
			return 3;
		}else if(baseId.endsWith("4")){
			return 4;
		}
		
		return -1;
	}	
}
