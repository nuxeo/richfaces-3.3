package util.converter;

import java.util.Calendar;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

public class DateValueConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component,
			String value) throws ConverterException {
		String[] fields = new String[3];
		fields = value.split("/");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Integer.parseInt(fields[2]), Integer.parseInt(fields[0]), Integer.parseInt(fields[1]));
		return calendar;
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object value) throws ConverterException {
		Calendar calendar = (Calendar)value;		
		return calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.DATE) + "/" + calendar.get(Calendar.YEAR);
	}

}
