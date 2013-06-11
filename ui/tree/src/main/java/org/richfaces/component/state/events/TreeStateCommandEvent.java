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

package org.richfaces.component.state.events;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;

/**
 * @author Nick - mailto:nbelaevski@exadel.com
 * created 01.12.2006
 * 
 */
public abstract class TreeStateCommandEvent extends FacesEvent {

	public TreeStateCommandEvent(UIComponent component) {
		super(component);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4866712952285930548L;

	/* (non-Javadoc)
	 * @see javax.faces.event.FacesEvent#isAppropriateListener(javax.faces.event.FacesListener)
	 */
	public final boolean isAppropriateListener(FacesListener listener) {
		return listener instanceof TreeStateCommandsListener;
	}

	/* (non-Javadoc)
	 * @see javax.faces.event.FacesEvent#processListener(javax.faces.event.FacesListener)
	 */
	public final void processListener(FacesListener listener) {
		try {
			execute((TreeStateCommandsListener) listener);
		} catch (IOException e) {
			throw new FacesException(e);
		}
	}

	protected abstract void execute(TreeStateCommandsListener commandsListener) throws IOException;
}
