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

package org.ajax4jsf.builder.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.List;

import org.ajax4jsf.builder.config.BuilderConfig;
import org.ajax4jsf.builder.config.ListenerBean;
import org.ajax4jsf.builder.config.TagHandlerBean;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

/**
 * @author Nick - mailto:nbelaevski@exadel.com
 * created 03.12.2006
 * 
 */
//TODO add JSP tags support
public class ListenerGenerator extends InnerGenerator {

	public ListenerGenerator(JSFGeneratorConfiguration config, Logger log) {
		super(config, log);
	}

	@Override
	protected String getDefaultTemplateName() {
		return "listener_taghandler.vm";
	}

	@Override
	public void createFiles(BuilderConfig config) throws GeneratorException {
		VelocityContext context = new VelocityContext();
		Template template = getTemplate();
		// Put common properties
		List<ListenerBean> listeners = config.getListeners();
		for (ListenerBean bean : listeners) {
			TagHandlerBean tagHandler = bean.getTaghandler();
			if ( null != tagHandler && tagHandler.isGenerate() && null !=tagHandler.getClassname()) {
				context.put("tag", tagHandler);
				context.put("listener", bean);

				String resultPath = tagHandler.getClassname().replace('.', '/')
				+ ".java";
				File javaFile = new File(getDestDir(), resultPath);
				File javaDir = javaFile.getParentFile();
				if (!javaDir.exists()) {
					javaDir.mkdirs();
				}
				try {
					if (javaFile.exists()) {
						javaFile.delete();
					}
					Writer out = new BufferedWriter(new FileWriter(javaFile));
					template.merge(context, out);
					out.flush();
					out.close();
				} catch (Exception e) {
					throw new GeneratorException(
							"Error create new Component Facelets Tag Java file ", e);
				}
			}
		}
	}

}
