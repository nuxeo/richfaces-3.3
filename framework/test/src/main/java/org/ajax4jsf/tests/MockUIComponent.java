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
package org.ajax4jsf.tests;

import javax.faces.FacesException;
import javax.faces.component.ContextCallback;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;

/**
 * "probe" component, to chek runs of the component methods during tests.
 * @author asmirnov
 *
 */
public class MockUIComponent extends UIComponentBase {

	private int runsProcessDecodes = 0;
	private int runsDecode = 0;
	private int runsBroadcast=0;
	private int runsInvokeOnComponent=0;
	private int runsProcessUpdates=0;
	private int runsProcessValidators=0;
	
	/**
	 * @return the runsProcessDecodes
	 */
	public int getRunsProcessDecodes() {
		return runsProcessDecodes;
	}

	/**
	 * @return the runsDecode
	 */
	public int getRunsDecode() {
		return runsDecode;
	}

	/**
	 * @return the runsBroadcast
	 */
	public int getRunsBroadcast() {
		return runsBroadcast;
	}

	/**
	 * @return the runsInvokeOnComponent
	 */
	public int getRunsInvokeOnComponent() {
		return runsInvokeOnComponent;
	}

	/**
	 * @return the runsProcessUpdates
	 */
	public int getRunsProcessUpdates() {
		return runsProcessUpdates;
	}

	/**
	 * @return the runsProcessValidators
	 */
	public int getRunsProcessValidators() {
		return runsProcessValidators;
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponent#getFamily()
	 */
	@Override
	public String getFamily() {
		// TODO Auto-generated method stub
		return "org.ajax4jsf.test";
	}
	
	@Override
	public void processDecodes(FacesContext context) {
		runsProcessDecodes++;
		super.processDecodes(context);
	}

	@Override
	public void decode(FacesContext context) {
		runsDecode++;
		super.decode(context);
	}
	
	@Override
	public void broadcast(FacesEvent event) throws AbortProcessingException {
		runsBroadcast++;
		super.broadcast(event);
	}
	
	@Override
	public boolean invokeOnComponent(FacesContext context, String clientId,
			ContextCallback callback) throws FacesException {
		runsInvokeOnComponent++;
		return super.invokeOnComponent(context, clientId, callback);
	}
	
	@Override
	public void processUpdates(FacesContext context) {
		runsProcessUpdates++;
		super.processUpdates(context);
	}
	
	@Override
	public void processValidators(FacesContext context) {
		runsProcessValidators++;
		super.processValidators(context);
	}
}
