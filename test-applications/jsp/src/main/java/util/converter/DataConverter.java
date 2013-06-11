package util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.swing.tree.TreeNode;

import util.data.Data;

public class DataConverter implements Converter {

	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		System.out.println("<<<LS: getAsObject>>>");
		String [] str = value.split(":");
		return new Data(str[0], str[1], str[2], str[3], 
						Integer.parseInt(str[4]), Integer.parseInt(str[5]), Integer.parseInt(str[6]), Integer.parseInt(str[7]), 
						Boolean.parseBoolean(str[8]), Boolean.parseBoolean(str[9]), Boolean.parseBoolean(str[10]), Boolean.parseBoolean(str[11]));
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		System.out.println("<<<LS: getAsString>>>");
		if (value instanceof Data) {  
			Data data = (Data) value;
			return data.toString();
		} if (value instanceof TreeNode) {
			TreeNode treeNode = (TreeNode) value;
			return treeNode.toString();
		} else throw new ConverterException("is not a class util.data.Data");
	}

}
