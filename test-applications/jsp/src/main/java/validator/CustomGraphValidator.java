package validator;

import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

public class CustomGraphValidator extends
		org.richfaces.validator.FacesBeanValidator implements
		org.richfaces.validator.GraphValidator {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String[] validateGraph(FacesContext context, UIComponent component,
			Object value, Set<String> profiles) throws ValidatorException {
		System.out.println("======Inside validateGraph method=====");
		return super.validateGraph(context, component, value, profiles);
	}

	public void validate(FacesContext context, UIComponent component, Object convertedValue)
			throws ValidatorException {
		System.out.println("======Inside validate method=====");
		super.validate(context, component, convertedValue);
	}

}
