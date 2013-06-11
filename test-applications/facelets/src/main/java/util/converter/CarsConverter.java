package util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import util.data.Car;

public class CarsConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		String[] str = value.split(":");
		return new Car(str[0], str[1], Integer.parseInt(str[2]));
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object obj) {
		
		if (obj instanceof Car) {
			Car car = (Car) obj;
			return car.toString();
		} else throw new ConverterException("is not a class util.data.Car");
	}
}