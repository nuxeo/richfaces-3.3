/**
 * License Agreement.
 *
 *  JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007  Exadel, Inc.
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

import java.util.Map;

import javax.faces.FactoryFinder;
import javax.faces.component.NamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;

/**
 * @author Nick - mailto:nbelaevski@exadel.com
 * created 01.12.2006
 * 
 * Apply Request Values phase listener setting flag in request scope after phase is processed
 * to handle internal node state modifying events on immediate tree properly. Tree component 
 * creates and registers single listener on instance creation if none listeners weren't created 
 * before.
 */
public class DecodesPhaseNotifiyingListener implements PhaseListener {

	private DecodesPhaseNotifiyingListener() {
		super();
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6092811353196514276L;
	
	/**
	 * Name of request attribute to indicate we've done with decodes phase
	 */
	public final static String DECODE_PHASE_NOTICE_NAME = DecodesPhaseNotifiyingListener.class.getName() + NamingContainer.SEPARATOR_CHAR + "_AFTER_DECODE_PHASE";
	
	/* (non-Javadoc)
	 * @see javax.faces.event.PhaseListener#afterPhase(javax.faces.event.PhaseEvent)
	 */
	public void afterPhase(PhaseEvent event) {
		event.getFacesContext().getExternalContext().getRequestMap().put(DECODE_PHASE_NOTICE_NAME, Boolean.TRUE);
	}

	/* (non-Javadoc)
	 * @see javax.faces.event.PhaseListener#beforePhase(javax.faces.event.PhaseEvent)
	 */
	public void beforePhase(PhaseEvent event) {
	}

	/* (non-Javadoc)
	 * @see javax.faces.event.PhaseListener#getPhaseId()
	 */
	public PhaseId getPhaseId() {
		return PhaseId.APPLY_REQUEST_VALUES;
	}

	public static void registerListenerInstance(FacesContext facesContext) {
		if (facesContext != null) {
			Object context = facesContext.getExternalContext().getContext();
			synchronized (context) {
				Map applicationMap = facesContext.getExternalContext().getApplicationMap();
				
				String keyName = DecodesPhaseNotifiyingListener.class.getName();
				
				if (null == applicationMap.get(keyName)) {
					LifecycleFactory lFactory = (LifecycleFactory)
						FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
					Lifecycle lifecycle = lFactory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);
					lifecycle.addPhaseListener(new DecodesPhaseNotifiyingListener());
					applicationMap.put(keyName, Boolean.TRUE);
				}
			}
		}
	}
}
