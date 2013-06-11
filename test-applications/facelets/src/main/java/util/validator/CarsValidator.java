package util.validator;

import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import util.data.Car;

public class CarsValidator implements Validator {

	@SuppressWarnings("unchecked")
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		Object[] objArr = (Object[]) value; 
		ArrayList<Car> carsList = (ArrayList<Car>) objArr[0];
		for (Car car : carsList) {
			System.out.println("***> validator for " + car.getMake() + "-"
					+ car.getModel());

			if (car.getPrice() > 10000000) {
				throw new ValidatorException(new FacesMessage(
						"Too much money!!! " + car.getMake() + "-"
								+ car.getModel()));
			}
		}
	}

}
