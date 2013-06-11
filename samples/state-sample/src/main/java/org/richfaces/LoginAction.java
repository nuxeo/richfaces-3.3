package org.richfaces;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;




public class LoginAction implements ActionListener
{
	
	private Bean bean;
	
	public void processAction(ActionEvent event) throws AbortProcessingException 
	{
		System.out.println("login action event");	
	}
	
	public String ok() {
		System.out.println("login action");	
		return "success";
	}

	/**
	 * @return the bean
	 */
	public Bean getBean() {
		return bean;
	}

	/**
	 * @param bean the bean to set
	 */
	public void setBean(Bean bean) {
		this.bean = bean;
	}
	
}	