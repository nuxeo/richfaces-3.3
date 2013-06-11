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
import org.ajax4jsf.builder.config.TagBean;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

public class TagTestGenerator extends InnerGenerator{

	private static final String COMPONENT_FILE_TEMPLATE = "tagtest.vm";	
	
	public TagTestGenerator(JSFGeneratorConfiguration config, Logger log) {
		super(config, log);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createFiles(BuilderConfig config) throws GeneratorException {
		// TODO Auto-generated method stub
		System.out.println("Preparing to generate test for tag class... ");
		VelocityContext context = new VelocityContext();
		context.put("generator",this);
		context.put("testDataGenerator", new TestDataGenerator2(getClassLoader(), getLog()));

		Template template = getTemplate();
		// Put common properties
		for (Iterator iter = config.getComponents().iterator(); iter.hasNext();) {
			ComponentBean component = (ComponentBean) iter.next();
			TagBean tag = component.getTag();
			if ( null !=  tag && null !=tag.getClassname() && tag.isGenerate() 
					&& tag.getTest() != null) {
				info("Create test class file for tag " + component.getTag().getSimpleClassName());
				context.put("component", component);
				if (null != component.getRenderer()) {
					context.put("renderer", component.getRenderer());
				} else {
					context.put("renderer",Boolean.FALSE);
				}
				context.put("tag", component.getTag());
				context.put("package", component.getTag().getPackageName());
				Set<String> importClasses = new HashSet<String>();
				for (Iterator<PropertyBean> it = component.getProperties().iterator(); it
						.hasNext();) {
					PropertyBean property = (PropertyBean) it.next();
					// For non-primitive types, add import declaration.
					if (!property.isSimpleType() && !property.isHidden()) {
						importClasses.add(property.getClassname());
					}
				}
				importClasses.add(component.getTag().getTest().getSuperclassname());
				context.put("imports", importClasses);
				String resultPath = component.getTag().getTest().getClassname().replace('.', '/')
						+ ".java";
				//System.out.println("resultPath - " + resultPath);
				//System.out.println("getDestDir() - " + getDestDir());
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
							"Error create new Component JSP Tag Test Java file ", e);
				}
			}
		}
	}

	@Override
	protected String getDefaultTemplateName() {
		// TODO Auto-generated method stub
		return COMPONENT_FILE_TEMPLATE;
	}

	public String getSetterTestName(PropertyBean property){
		String setterName = property.getSetterName();
		String className = property.getSimpleClassName();
		StringBuilder name = new StringBuilder("test"); 
		name.append(Character.toUpperCase(setterName.charAt(0)));
		name.append(setterName.substring(1));
		name.append(Character.toUpperCase(className.charAt(0)));
		name.append(className.substring(1));
		return name.toString();
	}
}
