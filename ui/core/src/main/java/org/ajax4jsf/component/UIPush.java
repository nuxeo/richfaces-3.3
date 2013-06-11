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

import java.io.IOException;

import javax.el.MethodExpression;
import javax.faces.component.NamingContainer;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.ajax4jsf.webapp.PollEventsManager;
import org.ajax4jsf.webapp.PushEventsCounter;

/**
 * Component for periodically call AJAX events on server ( poll actions )
 * @author shura
 *
 */
public abstract class UIPush extends AjaxActionComponent  {
	
	public static final String COMPONENT_TYPE="org.ajax4jsf.Push";
	

	private transient boolean _submitted = false;

	/**
	 * @return the submitted
	 */
	public boolean isSubmitted() {
		return _submitted;
	}

	public void encodeBegin(FacesContext context) throws IOException {
	    MethodExpression producer = getEventProducer();
	    // Subscribe events producer to push status listener.
	    if(null != producer){
		producer.invoke(context.getELContext(), new Object[]{getListener(context)});
	    }
	    super.encodeBegin(context);
	}
	
	private PushEventsCounter getListener(FacesContext context){
	    PollEventsManager eventsManeger = (PollEventsManager) context.getExternalContext().getApplicationMap().get(PollEventsManager.EVENTS_MANAGER_KEY);
	    return eventsManeger.getListener(getListenerId(context));
	}

	public String getListenerId(FacesContext context){
	    Object session = context.getExternalContext().getSession(false);
	    StringBuffer id = new StringBuffer();
	    if(null != session && session instanceof HttpSession){
		HttpSession httpSession = (HttpSession) session;
		id.append(httpSession.getId());
	    }
	    id.append(context.getViewRoot().getViewId());
	    id.append(NamingContainer.SEPARATOR_CHAR);
	    id.append(getClientId(context));
	    return id.toString();
	}
	/**
	 * @param submitted the submitted to set
	 */
	public void setSubmitted(boolean submitted) {
		_submitted = submitted;
	}

	public abstract MethodExpression getEventProducer();
	
	public abstract void setEventProducer(MethodExpression producer);
	/**
	 * @return time in mc for polling interval.
	 */
	public abstract int getInterval();
	
	/**
	 * @param interval time in mc for polling interval.
	 */
	public abstract void setInterval(int interval);
	
	public abstract boolean isEnabled();
	
	public abstract void setEnabled(boolean enable);
	
	protected void setupReRender(FacesContext facesContext) {
		super.setupReRender(facesContext);
//		AjaxContext.getCurrentInstance(facesContext).addComponentToAjaxRender(this);
		//getListener(facesContext).processed();
	}
	

}
