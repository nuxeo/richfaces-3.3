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

package org.richfaces;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.ajax4jsf.event.AjaxEvent;
import org.ajax4jsf.event.AjaxListener;

/**
 * @author $Autor$
 *
 */
public class Bean implements AjaxListener {
	
	private long processRequestDelay = 300;
	
	private long clientRequestDelay = 300;
	
	private long queueRequestDelay = 0;
	
	private boolean pollEnabled;
	
	private static final String AJAX_REQUESTS_COUNT_ATTRIBUTE = "ajaxRequestsCount";

	public void setProcessRequestDelay(long processRequestDelay) {
		this.processRequestDelay = processRequestDelay;
	}
	
	public long getProcessRequestDelay() {
		return processRequestDelay;
	}
	
	public long getClientRequestDelay() {
		return clientRequestDelay;
	}
	
	public void setClientRequestDelay(long clientRequestDelay) {
		this.clientRequestDelay = clientRequestDelay;
	}
	
	public long getQueueRequestDelay() {
		return queueRequestDelay;
	}
	
	public void setQueueRequestDelay(long queueRequestDelay) {
		this.queueRequestDelay = queueRequestDelay;
	}
	
	public boolean isPollEnabled() {
		return pollEnabled;
	}
	
	public void setPollEnabled(boolean pollEnabled) {
		this.pollEnabled = pollEnabled;
	}
	
	public void processActionListener(ActionEvent event) {
		System.out.println(event.getComponent().getId());

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		System.out.println(externalContext.getRequestParameterMap().get("AJAX:EVENTS_COUNT"));
		
		System.out.println();
	}
	
	public void processAction() {
		if (processRequestDelay != 0) {
			try {
				Thread.sleep(processRequestDelay);
			} catch (InterruptedException e) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				ExternalContext externalContext = facesContext.getExternalContext();

				externalContext.log(e.getMessage(), e);
			}
		}
	}
	
	public void resetAjaxCounter() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		externalContext.getSessionMap().put(AJAX_REQUESTS_COUNT_ATTRIBUTE, Long.valueOf(0));
	}

	public void processAjax(AjaxEvent event) {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		Long count = (Long) sessionMap.get(AJAX_REQUESTS_COUNT_ATTRIBUTE);
		if (count == null) {
			count = Long.valueOf(0);
		}

		sessionMap.put(AJAX_REQUESTS_COUNT_ATTRIBUTE, ++count);
	}
	
}