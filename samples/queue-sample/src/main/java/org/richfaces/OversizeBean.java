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

import java.util.HashMap;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public class OversizeBean {
	public String[] getQueueNames() {
		return new String[] {
			"dropNext", "dropNew", "fireNext", "fireNew"
		};
	}
	
	private Map<String, String> queueData = new HashMap<String, String>();

	public Map<String, String> getQueueData() {
		return queueData;
	}
	
	public void reset() {
		this.queueData = new HashMap<String, String>();
	}
	
	public void fireDelay() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			throw new FacesException(e);
		}
	}
	
	public void action() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, String> requestParameterMap = externalContext.getRequestParameterMap();

		String queueName = (String) requestParameterMap.get("queueName");
		String sequence = (String) requestParameterMap.get("sequence");
	
		String queueString = queueData.get(queueName);
		if (queueString == null) {
			queueData.put(queueName, sequence);
		} else {
			queueData.put(queueName, queueString + ", " + sequence);
		}
	}
}
