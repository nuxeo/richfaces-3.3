package org.richfaces;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;




public class Action implements ActionListener
{
	public void processAction(ActionEvent event) throws AbortProcessingException 
	{
		System.out.println("wtf");	
	}    
}	