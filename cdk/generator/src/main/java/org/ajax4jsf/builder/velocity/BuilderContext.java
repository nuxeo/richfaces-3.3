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

import java.io.File;
import java.util.Properties;

import org.ajax4jsf.builder.generator.GeneratorException;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.5 $ $Date: 2007/02/26 20:48:51 $
 *
 */
public class BuilderContext extends VelocityContext {

	public static final String TEMPLATES_PATH = "META-INF/templates";

	public static final String TEMPLATES12_PATH = "META-INF/templates12";

	/**
	 * 
	 */
	private static final long serialVersionUID = -4533522269902933084L;
	
	public static void init(File templatesDir) throws GeneratorException{
		try {
			Properties velocityProperties = new Properties();
			try {
				velocityProperties.load(BuilderContext.class.getClassLoader()
						.getResourceAsStream(TEMPLATES12_PATH+"/velocity.properties"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (null != templatesDir) {
				velocityProperties.put("resource.loader", "file,"+velocityProperties.getProperty("resource.loader","classpath,antfile"));
				velocityProperties
						.put("file.resource.loader.class",
								"org.apache.velocity.runtime.resource.loader.FileResourceLoader");
				velocityProperties.put("file.resource.loader.path",
						templatesDir.getAbsolutePath());
			}
			Velocity.init(velocityProperties);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new GeneratorException(
					"Error initialization Velocity engine", e1);
		}

	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.builder.ant.JSFGeneratorConfiguration#getTemplate(java.lang.String)
	 */
	public static Template getTemplate(String templateName) throws GeneratorException {
		try {
			return Velocity.getTemplate(templateName);
		}  catch (Exception e) {
			throw new GeneratorException("Error loading template " + templateName,e);
		}
	}


}
