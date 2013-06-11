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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.ajax4jsf.builder.config.BuilderConfig;
import org.ajax4jsf.builder.config.ComponentBean;
import org.ajax4jsf.builder.config.PropertyBean;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

public class ComponentTestGenerator extends InnerGenerator{

	private static final String COMPONENT_FILE_TEMPLATE = "componenttest.vm";	
	
	public ComponentTestGenerator(JSFGeneratorConfiguration config, Logger log) {
		super(config, log);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createFiles(BuilderConfig config) throws GeneratorException {
		VelocityContext context = new VelocityContext();
		Template template = getTemplate();
		context.put("generator",this);
		
		context.put("testDataGenerator", new TestDataGenerator(getClassLoader(), getLog()));
		
		// Put common properties
		for (Iterator iter = config.getComponents().iterator(); iter.hasNext();) {
			ComponentBean component = (ComponentBean) iter.next();
			if(component.getTest() != null){
				info("Create component test class file for component "+component.getClassname());
				if (component.isGenerate()) {
					context.put("component", component);
					context.put("renderer", component.getRenderer());
					context.put("package", component.getPackageName());
					Set<String> importClasses = new HashSet<String>();
					for (Iterator it = component.getProperties().iterator(); it
							.hasNext();) {
						PropertyBean property = (PropertyBean) it.next();
						// For non-primitive types, add import declaration.
						if (!property.isSimpleType() && !property.isExist()) {
							importClasses.add(property.getClassname());
						}
					}
					importClasses.add(component.getTest().getSuperclassname());
					context.put("imports", importClasses);
					String resultPath = component.getTest().getClassname().replace('.', '/')
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
								"Error create new Component Java file ", e);
					}
				}
				
			}
		}
		
	}

	@Override
	protected String getDefaultTemplateName() {
		// TODO Auto-generated method stub
		return COMPONENT_FILE_TEMPLATE;
	}

	
}
