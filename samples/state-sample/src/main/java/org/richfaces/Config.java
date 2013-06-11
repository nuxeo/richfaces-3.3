/**
 * 
 */
package org.richfaces;

import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

import org.richfaces.ui.model.States;

/**
 * @author asmirnov
 *
 */
public class Config {
	
	public States getStates(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		States states = new States();
		states.setCurrentState("register");
		states.put("showConfirm", Boolean.TRUE);
		states.put("link", "To login");
		states.put("okBtn", "Register");
		ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
		ValueExpression beanExpression = expressionFactory.createValueExpression(facesContext.getELContext(), "#{registerbean}", Bean.class);
		states.put("bean", beanExpression);
		beanExpression = expressionFactory.createValueExpression(facesContext.getELContext(), "#{registeraction}", RegisterAction.class);
		states.put("action", beanExpression);
		MethodExpression okExpression = expressionFactory.createMethodExpression(facesContext.getELContext(), "#{registeraction.ok}", String.class, new Class[] {});
		states.put("ok", okExpression);
		states.setNavigation("switch", "login");
		states.setCurrentState("login");
		states.put("showConfirm", Boolean.FALSE);
		states.put("link", "To register");
		states.put("okBtn", "Login");
		beanExpression = expressionFactory.createValueExpression(facesContext.getELContext(), "#{loginbean}", Bean.class);
		states.put("bean", beanExpression);
		beanExpression = expressionFactory.createValueExpression(facesContext.getELContext(), "#{loginaction}", LoginAction.class);
		states.put("action", beanExpression);
		okExpression = expressionFactory.createMethodExpression(facesContext.getELContext(), "#{loginaction.ok}", String.class, new Class[] {});
		states.put("ok", okExpression);
		states.setNavigation("switch", "register");
		return states;
	}

}
