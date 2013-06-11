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

import org.ajax4jsf.resource.ClientScript;

/**
 * Resource for Drag ' Drop client javascript
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:57:33 $
 *
 */
public class ImageCacheScript extends ClientScript {

	
	
	/**
	 * 
	 */
	public ImageCacheScript() {
		super();
		usePrototype=true;
//		setRenderer(new ScriptRenderer());
//		setLastModified(new Date(InternetResourceBuilder.getInstance().getStartTime()));
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.framework.ajax.ClientScript#getJavaScript()
	 */
	public String getJavaScript() {
		// TODO Auto-generated method stub
		return "scripts/imagecache.js";
	}


}
