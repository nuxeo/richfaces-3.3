/**
 * License Agreement.
 *
 * JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */ 
package org.ajax4jsf.bean;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author $Autor$
 *
 */
public class Bean {

    private java.lang.String text;
    
    private String text2="<%%%>";
    
    private String[] array = {"one]","two]]>"};

    private String[][] array2 = {{"one","two"},{"three","four"}};
    
    private UIComponent component;
    
    private HtmlOutputText outputComponent;

    /**
	 * @return the component
	 */
	public UIComponent getComponent() {
		return component;
	}

	/**
	 * @param component the component to set
	 */
	public void setComponent(UIComponent component) {
		this.component = component;
	}

	public java.lang.String getText() {
	return text;
    }

	    public void setText(java.lang.String text) {
	this.text = text;
    }
	    
	    public String ok(){
		System.out.println("Button pressed");
		setText("testOk");
		return null;
	    }
	    
	    public String setCookie() {
		    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		    HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
		    Cookie cookie = new Cookie("test", "Setted at time "+System.currentTimeMillis());
		    cookie.setMaxAge(60 * 60 * 24 * 365);
		    response.addCookie(cookie);
		    return "verify_cookie";
		  }
	    
	    public String getTestCookie(){
		    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		    HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		    Cookie[] cookies = request.getCookies();
		    for (int i = 0; i < cookies.length; i++) {
			if("test".equals(cookies[i].getName())){
			    return cookies[i].getValue();
			}
		    }
		    return null;
	    }

	    /**
	     * @return the array
	     */
	    public String[] getArray() {
	        return array;
	    }

	    /**
	     * @param array the array to set
	     */
	    public void setArray(String[] array) {
	        this.array = array;
	    }

	    /**
	     * @return the array2
	     */
	    public String[][] getArray2() {
	        return array2;
	    }

	    /**
	     * @param array2 the array2 to set
	     */
	    public void setArray2(String[][] array2) {
	        this.array2 = array2;
	    }

	    /**
	     * @return the text2
	     */
	    public String getText2() {
	        return text2;
	    }

	    /**
	     * @param text2 the text2 to set
	     */
	    public void setText2(String test2) {
	        this.text2 = test2;
	    }
	    
	    public void validate(FacesContext context, UIComponent input,Object newValue) {
		FacesMessage msg = new FacesMessage("#{bean.validate} called");
		context.addMessage(input.getClientId(context), msg);
		System.out.println("validate");
	    }
	    
	    public void onChange(ValueChangeEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		UIComponent input = event.getComponent();
		FacesMessage msg = new FacesMessage("#{bean.onChange} called");
		context.addMessage(input.getClientId(context), msg);
		System.out.println("onChange");
		
	    }
	
	    public void validate2(FacesContext context, UIComponent input,Object newValue) {
		FacesMessage msg = new FacesMessage("#{bean.validate2} called");
		context.addMessage(input.getClientId(context), msg);
		System.out.println("validate2");

	    }
	    
	    public void onChange2(ValueChangeEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		UIComponent input = event.getComponent();
		FacesMessage msg = new FacesMessage("#{bean.onChange2} called");
		context.addMessage(input.getClientId(context), msg);
		System.out.println("onChange2");

	    }
	    
	    public String getTime(){
	    	return (new Date(System.currentTimeMillis())).toString();
	    }
	    
	    public String clearSession(){
	    	ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	    	HttpSession session = (HttpSession) externalContext.getSession(false);
	    	if(null != session){
	    	    session.setMaxInactiveInterval(5);
	    	}
	    	return null;
	        }

		/**
		 * @return the outputComponent
		 */
		public HtmlOutputText getOutputComponent() {
			outputComponent = new HtmlOutputText();
			return outputComponent;
		}

		/**
		 * @param outputComponent the outputComponent to set
		 */
		public void setOutputComponent(HtmlOutputText outputComponent) {
			this.outputComponent = outputComponent;
		}

}