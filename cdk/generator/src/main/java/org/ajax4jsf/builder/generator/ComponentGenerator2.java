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
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

import org.ajax4jsf.builder.component.ComponentModelBuilder;
import org.ajax4jsf.builder.config.BuilderConfig;
import org.ajax4jsf.builder.config.ComponentBean;
import org.ajax4jsf.builder.model.JavaClass;
import org.ajax4jsf.builder.render.JavaClassRenderer;

/**
 * @author Maksim Kaszynski
 *
 */
public class ComponentGenerator2 extends InnerGenerator {

	private ComponentModelBuilder componentModelBuilder = new ComponentModelBuilder();
	private JavaClassRenderer classRenderer = new JavaClassRenderer();
	
	public ComponentGenerator2(JSFGeneratorConfiguration task, Logger log) {
		super(task,log);
	}
	/* (non-Javadoc)
	 * @see org.ajax4jsf.builder.generator.InnerGenerator#createFiles(org.ajax4jsf.builder.config.BuilderConfig)
	 */
	@Override
	public void createFiles(BuilderConfig config) throws GeneratorException {
		List<ComponentBean> components = config.getComponents();
		for (ComponentBean componentBean : components) {
			if (componentBean.isGenerate()) {
				JavaClass componentClass = 
					componentModelBuilder.build(componentBean, getConfig());
				
				String resultPath = componentBean.getClassname().replace('.', '/')
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
					PrintWriter printWriter = new PrintWriter(out);
					
					classRenderer.render(componentClass, printWriter);
					
					printWriter.flush();
					out.close();
				} catch (Exception e) {
					throw new GeneratorException(
							"Error create new Component Java file ", e);
				}
			}
		}

	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.builder.generator.InnerGenerator#getDefaultTemplateName()
	 */
	@Override
	protected String getDefaultTemplateName() {
		// TODO Auto-generated method stub
		return null;
	}

}
