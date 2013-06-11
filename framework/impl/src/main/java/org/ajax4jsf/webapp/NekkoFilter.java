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

package org.ajax4jsf.webapp;

import org.ajax4jsf.webapp.nekko.NekkoXMLFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * This class implements a simple Servlet filter that convert basic html
 * output of JSF to valid XML or JavaScript, for parsing different versions
 * of XmlHttpRequest's on client side.
  */
public class NekkoFilter extends BaseFilter implements javax.servlet.Filter {
	static final Log log = LogFactory.getLog(NekkoFilter.class);
	
	/**
	 * 
	 */
	public NekkoFilter() {
		super();
		// default - not force
//		setForcexml(false);
		xmlFilter = new NekkoXMLFilter();
		xmlFilter.setFilter(this);
	}

} 


