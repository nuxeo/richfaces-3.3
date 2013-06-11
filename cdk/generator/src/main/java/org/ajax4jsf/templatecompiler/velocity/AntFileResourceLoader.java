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

package org.ajax4jsf.templatecompiler.velocity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;

/**
 * Load resource from file, name prepared by Ant
 * 
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2006/12/20 18:56:34 $
 * 
 */
public class AntFileResourceLoader extends ResourceLoader {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.velocity.runtime.resource.loader.ResourceLoader#init(org.apache.commons.collections.ExtendedProperties)
	 */
	public void init(ExtendedProperties arg0) {
		this.rsvc.info("AntFileResourceLoader : initialization starting.");
		this.rsvc.info("AntFileResourceLoader : initialization complete.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.velocity.runtime.resource.loader.ResourceLoader#getResourceStream(java.lang.String)
	 */
	public InputStream getResourceStream(String templateName)
			throws ResourceNotFoundException {
		/*
		 * Make sure we have a valid templateName.
		 */
		if ((templateName == null) || (templateName.length() == 0)) {
			/*
			 * If we don't get a properly formed templateName then there's not
			 * much we can do. So we'll forget about trying to search any more
			 * paths for the template.
			 */
			throw new ResourceNotFoundException(
					"Need to specify a file name or file path!");
		}
		try {
			this.rsvc.info("AntFileResourceLoader : attempt to load file "
					+ templateName);
			File file = new File(templateName);

			if (file.canRead()) {
				return new BufferedInputStream(new FileInputStream(file
						.getAbsolutePath()));
			} else {
				String msg = "AntFileResourceLoader Error: can not read file "
						+ templateName;

				throw new ResourceNotFoundException(msg);
			}
		} catch (FileNotFoundException fnfe) {
			/*
			 * log and convert to a general Velocity ResourceNotFoundException
			 */
			String msg = "AntFileResourceLoader Error: file not found "
					+ templateName;

			throw new ResourceNotFoundException(msg);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.velocity.runtime.resource.loader.ResourceLoader#isSourceModified(org.apache.velocity.runtime.resource.Resource)
	 */
	public boolean isSourceModified(Resource arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.velocity.runtime.resource.loader.ResourceLoader#getLastModified(org.apache.velocity.runtime.resource.Resource)
	 */
	public long getLastModified(Resource arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

}
