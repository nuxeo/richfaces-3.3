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
package org.richfaces.utils;

import javax.faces.FacesException;

import org.ajax4jsf.renderkit.RendererBase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Maksim Kaszynski
 *
 */
public class TemplateLoader {
	
	private static final Log log = LogFactory.getLog(TemplateLoader.class);
	
	public static RendererBase loadTemplate(String className) {
		RendererBase template = null;
		try{
			template = (RendererBase)Class.forName(className).newInstance();
		} catch (ClassNotFoundException e) {
			log.error("class not found: " + className );
			throw new FacesException(e);
		} catch (Exception e) {
			log.error("exception in loading class : " + className);
			throw new FacesException(e);
		}
		return template;
	}

}
