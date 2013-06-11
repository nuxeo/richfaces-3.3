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


package org.richfaces.regressionarea.seam.beans;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

/**
 * @author Nick Belaevski
 */

@Name("actionBean")
@Scope(ScopeType.APPLICATION)
public class ActionBean {

	private void addMessage(Severity severity, String messageText) {
		FacesMessage message = new FacesMessage(severity, messageText, messageText);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public void addMessage(String messageText) {
		addMessage(FacesMessage.SEVERITY_INFO, messageText);
	}

	public void addInfoMessage(String messageText) {
		addMessage(FacesMessage.SEVERITY_INFO, messageText);
	}

	public void addWarnMessage(String messageText) {
		addMessage(FacesMessage.SEVERITY_WARN, messageText);
	}
	
	public void addErrorMessage(String messageText) {
		addMessage(FacesMessage.SEVERITY_ERROR, messageText);
	}
	
	public void addFatalMessage(String messageText) {
		addMessage(FacesMessage.SEVERITY_FATAL, messageText);
	}

}
