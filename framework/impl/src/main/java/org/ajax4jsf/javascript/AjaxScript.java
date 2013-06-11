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

package org.ajax4jsf.javascript;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.ajax4jsf.resource.ClientScript;

/**
 * Resource for AJAX client-side script. Render one time per page.
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:57:33 $
 *
 */
public class AjaxScript extends ClientScript {
    
        private static final Log _log = LogFactory.getLog(AjaxScript.class);

	/**
	 * Set JavaScript renderer and modification time to application-startup time.
	 */
	public AjaxScript() {
		super();
	if (_log.isDebugEnabled()) {
	    _log.debug("AjaxScript() - Created instance of AjaxScript resource"); //$NON-NLS-1$
	}
	}
	

	/**
	 * @return Returns the javaScript.
	 */
	public String getJavaScript() {
		return "scripts/AJAX.js";
	}

	
}
