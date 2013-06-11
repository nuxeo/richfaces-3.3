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

package org.richfaces.skin;

import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;

import org.easymock.MockControl;
import org.easymock.classextension.MockClassControl;

/**
 * @author asmirnov@exadel.com (latest modification by $Author: ishabalov $)
 * @version $Revision: 1.1.2.2 $ $Date: 2007/02/20 20:58:11 $
 *
 */
public class TestApplicationFactory extends ApplicationFactory {
	
	private MockControl applicationControl;
	private Application mockApplication;
	
	public TestApplicationFactory(){
		applicationControl = MockClassControl.createControl(Application.class);
		mockApplication = (Application) applicationControl.getMock();
	}

	/* (non-Javadoc)
	 * @see javax.faces.application.ApplicationFactory#getApplication()
	 */
	public Application getApplication() {
		// TODO Auto-generated method stub
		return mockApplication;
	}

	/* (non-Javadoc)
	 * @see javax.faces.application.ApplicationFactory#setApplication(javax.faces.application.Application)
	 */
	public void setApplication(Application arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return Returns the applicationControl.
	 */
	public MockControl getApplicationControl() {
		return applicationControl;
	}

}
