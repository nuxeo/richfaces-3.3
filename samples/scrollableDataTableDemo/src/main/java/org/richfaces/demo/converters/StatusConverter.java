package org.richfaces.demo.converters;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.richfaces.demo.converters.utils.ConverterUtils;
import org.richfaces.demo.datagrid.model.Status;

/**
 * @author Anton Belevich
 *
 */
public class StatusConverter implements Converter{

	private final static int STATUS_OPEN = 1;
	
	private final static int STATUS_INPROGRESS = 3;
	
	private final static int STATUS_RESOLVED = 5;
	
	private final static int STATUS_REOPENED = 4;
	
	private final static int STATUS_CLOSED = 6;
	
	private List statusList = new ArrayList<Status>(); 
	
	
	public List getStatusList() {
		return statusList;
	}

	public void setStatusList(List statusList) {
		this.statusList = statusList;
	}

	public Object getAsObject(FacesContext context, UIComponent component,
			String value) throws ConverterException{
		
		Status convertedValue = null;
//		System.out.println("StatusConverter.getAsObject()");
		int st_id = Integer.parseInt(value);
		int id = -1;
		
		switch (st_id) {
			case STATUS_OPEN:
				id = 0;
				break;
			case STATUS_INPROGRESS:
				id = 1;
				break;
			case STATUS_RESOLVED:
				id = 2;
				break;
			case STATUS_REOPENED:
				id = 3;
				break;
			case STATUS_CLOSED:
				id = 4;
				break;	
		}
		
		try {
			convertedValue = (Status)statusList.get(id);
		} catch (IndexOutOfBoundsException e) {
			FacesMessage errorMessage = new FacesMessage(e.getMessage());
			errorMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ConverterException(errorMessage);
		}
		
		return convertedValue;
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object value) throws ConverterException{
	
		Status status = null;
//		System.out.println("StatusConverter.getAsString()");
		
		if(value instanceof Status){
			status = (Status)value;
		}else{
			FacesMessage errorMessage = ConverterUtils.createTypeErrorMessage(value.getClass().getName(), Status.class.getName(),ConverterUtils.ERROR_1);
			throw new ConverterException(errorMessage);
		}
		
		return Integer.toString(status.getId())  ;
	}
}