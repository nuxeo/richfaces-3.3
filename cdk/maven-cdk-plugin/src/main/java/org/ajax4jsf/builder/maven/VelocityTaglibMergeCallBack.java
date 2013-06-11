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

package org.ajax4jsf.builder.maven;

import java.io.File;
import java.io.FileWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.codehaus.plexus.velocity.VelocityComponent;

/**
 * @author Maksim Kaszynski
 *
 */
public class VelocityTaglibMergeCallBack implements XMLMergeCallback {

	private File target;
	
	private String template;
	
	private VelocityComponent velocity;
	
	private VelocityContext initialContext;
	
	/* (non-Javadoc)
	 * @see org.ajax4jsf.builder.maven.XMLMergeCallback#onMergeComplete(java.lang.String)
	 */
	public void onMergeComplete(String mergedContent) throws Exception{
		
		VelocityContext velocityContext;
		
		if (initialContext != null) {
			velocityContext = new VelocityContext(initialContext);
		} else {
			velocityContext = new VelocityContext();
		}
		
		VelocityEngine velocityEngine = velocity.getEngine();
		Template template2 = velocityEngine.getTemplate(template);
		
		File parentFile = target.getParentFile();
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}
		
		FileWriter fileWriter = new FileWriter(target);
		
		template2.merge(velocityContext, fileWriter);
		fileWriter.flush();
		fileWriter.close();
	}

}
