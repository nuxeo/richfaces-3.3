package org.richfaces;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.context.FacesContext;


public class MiracleBean {

	private UIComponent component;
	
	private UIComponent containerComponent;
	
	private Map<String, Object> values = new HashMap<String, Object>();
	
	public UIComponent getComponent() {
		return component;
	}
	
	public void setComponent(UIComponent component) {
		this.component = component;
	}
	
	public UIComponent getContainerComponent() {
		if (containerComponent == null) {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			containerComponent = new HtmlPanelGrid();
			
			List<UIComponent> children = containerComponent.getChildren();
			children.clear();

			try {
				BeanInfo beanInfo = java.beans.Introspector.getBeanInfo(component.getClass());
				
				for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
					String name = pd.getName();
					
					if ("submittedValue".equals(name) || "id".equals(name) || "transient".equals(name) || "rendered".equals(name) || "rendererType".equals(name) || "localValueSet".equals(name)) {
						continue;
					}
					
					UIInput input = null;
					
					Class<?> type = pd.getPropertyType();
					if (pd.getWriteMethod() != null) {
						if (Boolean.class.isAssignableFrom(type) || Boolean.TYPE.equals(type)) {
							input = new HtmlSelectBooleanCheckbox();
						} else if (type.getName().startsWith("java.lang.") || type.isPrimitive()) {
							input = new HtmlInputText();
						}

						if (input != null) {
							input.setConverter(facesContext.getApplication().createConverter(type));
							
							HtmlOutputLabel label = new HtmlOutputLabel();
							label.setValue(name);
							label.setFor(name);
							label.setId(name + "_lbl");
							
							children.add(label);
							
							input.setId(name);
							input.setValueExpression("value", 
									facesContext.getApplication().getExpressionFactory().createValueExpression(
											facesContext.getELContext(), "#{miracleBean.values['"+name+"']}", type));
						
							children.add(input);
						}
					}
				}
			} catch (IntrospectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			HtmlCommandButton button = new HtmlCommandButton();
			button.setValue("Submit");
			button.setActionExpression(facesContext.getApplication().getExpressionFactory().createMethodExpression(facesContext.getELContext(), "#{miracleBean.applyValues}", Void.TYPE, new Class[0]));
		
			children.add(button);
		}
		
		return containerComponent;
	}
	
	public void setContainerComponent(UIComponent containerComponent) {
		this.containerComponent = containerComponent;
	}
	
	public Map<String, Object> getValues() {
		return values;
	}
	
	public void setValues(Map<String, Object> values) {
		this.values = values;
	}
	
	public void applyValues() {
		Map<String, Object> attributes = component.getAttributes();
		
		for (Map.Entry<String, Object> entry : values.entrySet()) {
			attributes.put(entry.getKey(), entry.getValue());
		}
	}
}
