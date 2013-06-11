package util.componentInfo;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.el.ValueExpression;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import util.parser.Attribute;
import util.parser.AttributesList;
import util.parser.TLDParser;

public class ComponentInfo {
	private static volatile ComponentInfo INSTANCE;
	private ComponentAttribute componentAttribute = new ComponentAttribute();
	private Object component = null;
	private String beanName;
	private String description;
	private String attribute;
	private String property;
	private boolean print;
	
	public ComponentInfo(String beanName) {
		this.beanName = beanName;
		this.attribute = "";
		this.property = "";
		print = true;
	}
	
	public void setComponentInfo(String attribute, String property) {
		this.attribute = attribute;
		this.property = property;
		this.description = "";
		if(print) printInfo();
	}
	
	public void setComponentInfo(String attribute, String property, String description) {
		this.attribute = attribute;
		this.property = property;
		this.description = description;
		if(print) printInfo();
	}
	
	protected ComponentInfo() {
	}

	public static ComponentInfo getInstance() {
		if (INSTANCE == null)
			synchronized (ComponentInfo.class) {
				if (INSTANCE == null)
					INSTANCE = new ComponentInfo();
			}
		return INSTANCE;
	}

	public String getName() {
		if (component == null)
			return null;
		return component.getClass().getName();
	}

	public void addField(Object component) {
		if (component == null)
			return;
		this.component = component;		
		
		componentAttribute.cleanAll();
		String str = null;
		Field[] fields = component.getClass().getDeclaredFields();
		
		String richName = null;
		boolean isAjax = false;
		if(component.getClass().getName().startsWith("org.richfaces.component.html.Html")){
			richName = component.getClass().getName().substring("org.richfaces.component.html.Html".length());
			richName = richName.substring(0, 1).toLowerCase() + richName.substring(1, richName.length());
		} else if(component.getClass().getName().startsWith("org.richfaces.component.UI")){
			richName = component.getClass().getName().substring("org.richfaces.component.UI".length());
			richName = richName.substring(0, 1).toLowerCase() + richName.substring(1, richName.length());			
		}else if(isAjax = component.getClass().getName().startsWith("org.ajax4jsf.component.html.Html")){
			richName = component.getClass().getName().substring("org.ajax4jsf.component.html.Html".length());
			richName = richName.substring(0, 1).toLowerCase() + richName.substring(1, richName.length());
		}else {
			try {
				throw new Exception("Unknown class for component!!!Component class:" + component.getClass().getName());
			} catch (Exception e) {				
				e.printStackTrace();
			}
		}				
		TLDParser parser = new TLDParser(richName);		
		AttributesList allAttributes = parser.getAllAttributes();
		for(Attribute s:allAttributes){
			System.out.println(s.toString());
		}
		
		System.out.println("----------HANDLERS----------");
		for(Attribute attr:allAttributes.getHandlers()){
			System.out.println(attr.getName());
		}
		
		AccessibleObject.setAccessible(fields, true);
		for (Field field : fields) {
			str = field.getName().substring(1);
			if (!Modifier.isStatic(field.getModifiers()) && (allAttributes.getNamesArray().contains(str) || isAjax)) {								
				if (str.startsWith("on")) {
					componentAttribute.putEvent(str,
							"");
				} else if (str.indexOf("tyle") != -1 || str.indexOf("lass") != -1) {
					try {						
						UIComponentBase comp = (UIComponentBase) component;
						ValueExpression ve = comp.getValueExpression(str);
						if(ve != null){
						if(ve.isLiteralText()){
							componentAttribute.putStyle(str, ve.getExpressionString());
						} else{
							componentAttribute.putStyle(str, ve.getValue(FacesContext.getCurrentInstance().getELContext()).toString());
						}
						} else componentAttribute.putStyle(str, "null");
						
					} catch (IllegalArgumentException e) {						
						e.printStackTrace();
					}
					
				} else {
					
					try {
						Class cl = field.getType();						
						if(cl.isPrimitive() || cl.getName().equals("java.lang.String")){
							UIComponentBase comp = (UIComponentBase) component;
							ValueExpression ve = comp.getValueExpression(str);
							if(ve != null){
							if(ve.isLiteralText()){
								componentAttribute.putAttribute(str, ve.getExpressionString());
							} else{
								componentAttribute.putAttribute(str, ve.getValue(FacesContext.getCurrentInstance().getELContext()).toString());
							}
							}else componentAttribute.putAttribute(str, "null");
						} else componentAttribute.putAttribute(str, "---"); 
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

	public static String toString(Object obj) {		
		Class cl = obj.getClass();
		String r = cl.getName() + "[";
		Class sc = cl.getSuperclass();
		if (!sc.equals(Object.class))
			r += sc + ",";
		Field[] fields = cl.getDeclaredFields();
		try {
			AccessibleObject.setAccessible(fields, true);
		} catch (SecurityException e) {
		}
		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			r += f.getName() + "=";
			try {
				Object val = f.get(obj);
				r += val.toString();
			} catch (IllegalAccessException e) {
				r += "???";
			}
			if (i < fields.length - 1)
				r += ",";
			else
				r += "]";
		}
		return r;
	}
	
	public Object invoke(String aMethod, Class[] params, Object[] args) {
		Method m;
		System.out.println("ComponentInfo.invoke()");
		if (component == null) return null;
		try {
			m = component.getClass().getDeclaredMethod(aMethod, params);
			Object obj = m.invoke(component, args);
			return obj;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		finally {
			return "";
		}
	}
	
	private void printInfo() {
		System.out.println("beanName: " + beanName + "[attribute: " +  attribute + "; property: " + property + "; description: " + description + "];");
	}
	
	public String [] getComponentInfo() {
		String [] str = {attribute, property};
		return str;
	}
	
	public ComponentAttribute getComponentAttribute() {
		return componentAttribute;
	}

	public Object getComponent() {
		return component;
	}

	public void setComponent(Object component) {
		this.component = component;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public boolean isPrint() {
		return print;
	}

	public void setPrint(boolean print) {
		this.print = print;
	}

	public void setComponentAttribute(ComponentAttribute componentAttribute) {
		this.componentAttribute = componentAttribute;
	}
}
