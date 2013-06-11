package org.richfaces.demo.converters;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.richfaces.demo.converters.utils.ConverterUtils;
import org.richfaces.demo.datagrid.model.Priority;

/**
 * @author Anton Belevich
 *
 */
public class PriorityConverter implements Converter{
	
	private final static int PRIORITY_BLOCKER = 1;
	
	private final static int PRIORITY_CRITICAL = 2;
	
	private final static int PRIORITY_MAJOR = 3;
	
	private final static int PRIORITY_MINOR = 4;
	
	private final static int PRIORITY_COSMETIC = 5;
	
	
	public PriorityConverter() {
	}

	
	private List prioritiesList = new ArrayList<Priority>(); 
	
	public List getPrioritiesList() {
		return prioritiesList;
	}

	public void setPrioritiesList(List prioritiesList) {
		this.prioritiesList = prioritiesList;
	}
	
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) throws  ConverterException{
		
		int pr_id = Integer.parseInt(value);
		int id = -1;		
//		System.out.println("PriorityConverter.getAsObject()");
		switch (pr_id) {
			case PRIORITY_BLOCKER:
				id = 0;
				break;
			case PRIORITY_CRITICAL:
				id = 1;
				break;
			case PRIORITY_MAJOR:
				id = 2;
				break;
			case PRIORITY_MINOR:
				id = 3;
				break;
			case PRIORITY_COSMETIC:
				id = 4;
				break;	
		}
		
		Priority convertedValue = null;
		
		try {
			convertedValue = (Priority)prioritiesList.get(id);
		} catch (IndexOutOfBoundsException e) {
			FacesMessage errorMessage = new FacesMessage(e.getMessage());
			errorMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ConverterException(errorMessage);
		}
		
		return convertedValue;
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object value) throws ConverterException{

		Priority priority = null;
//		System.out.println("PriorityConverter.getAsString()");
		
		if(value instanceof Priority){
			priority = (Priority)value;
		}else{
			FacesMessage errorMessage = ConverterUtils.createTypeErrorMessage(value.getClass().getName(), Priority.class.getName(),ConverterUtils.ERROR_1);
			throw new ConverterException(errorMessage);
		}
		
		return Integer.toString(priority.getId());
	}
}
