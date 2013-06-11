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

package org.ajax4jsf.builder.component.methods;

import java.io.StringWriter;

import org.ajax4jsf.builder.generator.GeneratorException;
import org.ajax4jsf.builder.generator.JSFGeneratorConfiguration;
import org.ajax4jsf.builder.model.MethodBody;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

/**
 * Render method body by using Velocity
 * @author Maksim Kaszynski
 *
 */
public abstract class VelocityMethodBody extends MethodBody {
	
	private Template template;
	private VelocityContext context;
	
	public abstract String getTemplate();
	
	protected Template loadTemplate(String id, JSFGeneratorConfiguration configuration) throws GeneratorException{
		return configuration.getTemplate(configuration.getTemplatesPath() + "/" + id);
	}
	
	public VelocityMethodBody(JSFGeneratorConfiguration configuration) throws GeneratorException{
		String t = getTemplate();
		try {
			template = loadTemplate(t, configuration);
		} catch (Exception e) {
			
		}
		context = new VelocityContext();
	}
	
	protected VelocityContext getContext() {
		return context;
	}

	@Override
	public String toCode() {
		
		StringWriter stringWriter = new StringWriter();
		
		try {
			template.merge(context, stringWriter);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return stringWriter.toString();
	}

}
