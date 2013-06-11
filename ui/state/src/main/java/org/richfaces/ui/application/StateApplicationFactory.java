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
package org.richfaces.ui.application;

import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;

/**
 * @author asmirnov
 *
 */
public class StateApplicationFactory extends ApplicationFactory {

	private ApplicationFactory parent;
	
	private Application application;
	
	/**
	 * @param parent
	 */
	public StateApplicationFactory(ApplicationFactory parent) {
		super();
		this.parent = parent;
	}


	/* (non-Javadoc)
	 * @see javax.faces.application.ApplicationFactory#getApplication()
	 */
	@Override
	public Application getApplication() {
		if (application == null) {
			application = new StateApplication(parent.getApplication());
			
		}

		return application;
	}

	/* (non-Javadoc)
	 * @see javax.faces.application.ApplicationFactory#setApplication(javax.faces.application.Application)
	 */
	@Override
	public void setApplication(Application application) {
		parent.setApplication(application);
		this.application = new StateApplication(application);
	}

}
