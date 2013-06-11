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

package org.ajax4jsf.builder.velocity;

import java.io.InputStream;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;


/**
 * @author asmirnov@exadel.com (latest modification by $Author: ishabalov $)
 * @version $Revision: 1.1.2.4 $ $Date: 2007/02/20 20:58:02 $
 *
 */
public class ClasspathTemplateLoader extends ClasspathResourceLoader {

	/* (non-Javadoc)
	 * @see org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader#getResourceStream(java.lang.String)
	 */
	public synchronized InputStream getResourceStream(String name) throws ResourceNotFoundException {
		if(null == name || name.length()==0) {
			throw new ResourceNotFoundException("Resource name not set for classpath template loading ");
		}
		StringBuffer templatePath = new StringBuffer(BuilderContext.TEMPLATES_PATH);
		if(!name.startsWith("/")) {
			templatePath.append('/');
		}
		templatePath.append(name);
		return super.getResourceStream(templatePath.toString());
	}

	
}
