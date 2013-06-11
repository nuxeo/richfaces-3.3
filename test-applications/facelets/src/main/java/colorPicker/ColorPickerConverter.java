package colorPicker;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.swing.tree.TreeNode;
import util.data.*;

public class ColorPickerConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		System.out.println("<<<ColorPicker Converter getAsObject() Called>>>");
		String str = value.toString();
		return str;
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		System.out.println("<<<ColorPicker Converter getAsString() Called>>>");
		if (value instanceof String) {
			String str = value.toString();
			return str;
		} else if (value == null) {
			value = new String(" ");
			String str = value.toString();
			return str;
		} else
			throw new ConverterException(
					"Test Application: Error in custom converted colorPicker.ColorPickerConverter.java");
	}

}
