/**
 * License Agreement.
 *
 * Rich Faces - Natural Ajax for Java Server Faces (JSF)
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */
package org.richfaces.component;

import java.util.Set;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.MethodExpression;
import javax.el.MethodInfo;
import javax.el.PropertyNotFoundException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIForm;
import javax.faces.component.UIMessage;
import javax.faces.component.UIMessages;
import javax.faces.component.html.HtmlForm;
import javax.faces.el.MethodNotFoundException;
import javax.faces.event.ActionEvent;

import org.ajax4jsf.context.AjaxContext;
import org.ajax4jsf.event.AjaxEvent;
import org.ajax4jsf.event.AjaxListener;
import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.ajax4jsf.webapp.taglib.MethodExpressionAjaxListener;
import org.richfaces.component.html.HtmlInputText;
import org.richfaces.event.ValidationEvent;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class BeanValidatorComponentTest extends AbstractAjax4JsfTestCase {

	UIForm form = null;
	UIBeanValidator validator = null;
	HtmlInputText input = null;
	UIMessages messages = null;
	private UIMessage message;
	
	public BeanValidatorComponentTest(String name) {
		super(name);
	}
	
	public void setUp() throws Exception {
    	super.setUp();
    	form = new HtmlForm();
		form.setId("form");
		facesContext.getViewRoot().getChildren().add(form);
		input = (HtmlInputText)application.createComponent(HtmlInputText.COMPONENT_TYPE);
		validator = (UIBeanValidator)application.createComponent(UIBeanValidator.COMPONENT_TYPE);
		validator.setId("validator");
		
		input.setId("input");
		input.getChildren().add(validator);
		form.getChildren().add(input);
		messages = (UIRichMessages)application.createComponent(UIRichMessages.COMPONENT_TYPE);
		messages.setId("messages");
		form.getChildren().add(messages);
		message = (UIMessage)application.createComponent(UIMessage.COMPONENT_TYPE);
		message.setId("msg");
		message.setFor("input");
		form.getChildren().add(message);
		facesContext.getViewRoot().getChildren().add(form);
    }

    public void tearDown() throws Exception {
    	super.tearDown();
    }
    
    public void testValidator() throws Exception {
		
		HtmlPage page = renderView();
		assertNotNull(page);
		assertNotNull(validator.getMessages(facesContext));
	}
	
	public void testAddRemoveListenerValidator() throws Exception {
		
		AjaxListener listener = new MethodExpressionAjaxListener(listenerExpression);
		validator.addAjaxListener(listener);
		
		HtmlPage page = renderView();
		assertNotNull(page);
		assertEquals(1, validator.getAjaxListeners().length);
		assertEquals(listenerExpression, validator.getAjaxListener()); 
		validator.setAjaxListener(listenerExpression2);
		assertEquals(1, validator.getAjaxListeners().length);
	}
	
	public void testBroadcast() throws Exception {
		
		AjaxListener listener = new MethodExpressionAjaxListener(listenerExpression);
		validator.addAjaxListener(listener);
		
		HtmlPage page = renderView();
		assertNotNull(page);
		validator.broadcast(new AjaxEvent(validator));
		assertTrue(facesContext.getMessages(validator.getClientId(facesContext)).hasNext());
		
	}
	
	public void testQueueEvent() throws Exception {
		
		AjaxListener listener = new MethodExpressionAjaxListener(listenerExpression);
		validator.addAjaxListener(listener);
		
		HtmlPage page = renderView();
		assertNotNull(page);
		validator.queueEvent(new ValidationEvent(validator));
	}

	MethodExpression listenerExpression = new MethodExpression(){
		
		public Object invoke(ELContext context, Object[] params) throws PropertyNotFoundException, MethodNotFoundException,
        ELException  {
			facesContext.addMessage(validator.getClientId(facesContext), new FacesMessage("Method invoked!"));
			return "invoked"; 
		}
		
		public MethodInfo getMethodInfo(ELContext context) {
		
			return null;
		}

		public boolean equals(Object obj) {
			 return (obj instanceof MethodExpression && obj.hashCode() == this.hashCode());
		}

		
		public String getExpressionString() {
			return null;
		}

		public int hashCode() {
			 return 0;
		}

		public boolean isLiteralText() {
			return false;
		}
		
	};
	
	MethodExpression listenerExpression2 = new MethodExpression(){
		
		public Object invoke(ELContext context, Object[] params) throws PropertyNotFoundException, MethodNotFoundException,
        ELException  {
			facesContext.addMessage(validator.getClientId(facesContext), new FacesMessage("Method invoked!"));
			return "invoked"; 
		}
		
		public MethodInfo getMethodInfo(ELContext context) {
		
			return null;
		}

		public boolean equals(Object obj) {
			 return (obj instanceof MethodExpression && obj.hashCode() == this.hashCode());
		}

		
		public String getExpressionString() {
			return null;
		}

		public int hashCode() {
			 return 0;
		}

		public boolean isLiteralText() {
			return false;
		}
		
	};
	

}
