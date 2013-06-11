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


package org.ajax4jsf.component;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * @author Nick Belaevski
 * @since 3.3.0
 */
public class QueueRegistry {

	private static final String REGISTRY_ATTRIBUTE_NAME = QueueRegistry.class.getName();
	
	private boolean shouldCreateDefaultGlobalQueue = false;
	
	private Map<String, Object> queuesData = new LinkedHashMap<String, Object>();

	private QueueRegistry() {
	}
	
	public static QueueRegistry getInstance(FacesContext context) {
		ExternalContext externalContext = context.getExternalContext();
		Map<String, Object> requestMap = externalContext.getRequestMap();
		
		QueueRegistry registry = (QueueRegistry) 
			requestMap.get(REGISTRY_ATTRIBUTE_NAME);
		
		if (registry == null) {
			registry = new QueueRegistry();
			requestMap.put(REGISTRY_ATTRIBUTE_NAME, registry);
		}
		
		return registry;
	}
	
	public void registerQueue(FacesContext context, String clientName, Object data) {
		if (!containsQueue(clientName)) {
			queuesData.put(clientName, data);
		} else {
			context.getExternalContext().log("Queue with name '" + clientName + "' has already been registered");
		}
	}
	
	public boolean containsQueue(String name) {
		return queuesData.containsKey(name);
	}
	
	public Map<String, Object> getRegisteredQueues(FacesContext context) {
		return queuesData;
	}

	public void setShouldCreateDefaultGlobalQueue() {
		this.shouldCreateDefaultGlobalQueue = true;
	}
	
	public boolean isShouldCreateDefaultGlobalQueue() {
		return shouldCreateDefaultGlobalQueue;
	}
	
	public boolean hasQueuesToEncode() {
		return shouldCreateDefaultGlobalQueue || !queuesData.isEmpty();
	}
}
