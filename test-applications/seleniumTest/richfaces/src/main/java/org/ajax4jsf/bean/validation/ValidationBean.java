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
package org.ajax4jsf.bean.validation;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 * renamed and copied from demo
 */
public class ValidationBean {
	
	public void init() {
		this.rendered = true;
	}

	static final String INPUT_TEXT = "text";
	
	public static final String STATUS_TEXT = "UPDATE_MODEL";
	
	private String input = INPUT_TEXT;
	
	private String status;

    private User user = new User();
    
    private Boolean rendered;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private Admin admin = new Admin();

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public void success() {
        setProgressString(getProgressString() + "(Strored successfully)");
    }

    private String progressString = "Fill the form please";

    public String getProgressString() {
        return progressString;
    }

    public void setProgressString(String progressString) {
        this.progressString = progressString;
    }

    public void storeOrderOfDay(ActionEvent event) {
        FacesContext.getCurrentInstance().addMessage(
                event.getComponent().getClientId(FacesContext.getCurrentInstance()),
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Changes Stored Successfully",
                        "Changes Stored Successfully"));
    }

	/**
	 * @return the input
	 */
	public String getInput() {
		return input;
	}

	/**
	 * @param input the input to set
	 */
	public void setInput(String input) {
		if (input != null && input.equals(INPUT_TEXT)) {
			status = STATUS_TEXT;
		}
		this.input = input;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public void setRendered(Boolean rendered) {
		this.rendered = rendered;
	}

	public Boolean getRendered() {
		return rendered;
	}
	
	public void reset() {
		this.input = INPUT_TEXT;
		this.status = "";
		this.user = new User();
		this.admin = new Admin();
	}
}
