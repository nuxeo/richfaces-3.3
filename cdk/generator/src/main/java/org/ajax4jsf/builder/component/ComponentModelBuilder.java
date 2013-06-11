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

package org.ajax4jsf.builder.component;

import static org.ajax4jsf.builder.model.Argument.arg;

import java.util.Collection;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import org.ajax4jsf.builder.component.state.ComponentStateManager;
import org.ajax4jsf.builder.config.ComponentBean;
import org.ajax4jsf.builder.config.PropertyBean;
import org.ajax4jsf.builder.config.RendererBean;
import org.ajax4jsf.builder.generator.GeneratorException;
import org.ajax4jsf.builder.generator.JSFGeneratorConfiguration;
import org.ajax4jsf.builder.model.JavaClass;
import org.ajax4jsf.builder.model.JavaConstructor;
import org.ajax4jsf.builder.model.JavaField;
import org.ajax4jsf.builder.model.JavaMethod;
import org.ajax4jsf.builder.model.JavaModifier;
import org.ajax4jsf.builder.model.JavaPackage;
import org.ajax4jsf.builder.model.MethodBody;

/**
 * Build component class model from configs
 * @author Maksim Kaszynski
 *
 */
public class ComponentModelBuilder {
	
	private PropertyProcessor[] propertyProcessors = {
			new ActionSource2PropertyProcessor(),
			new ExpressionPropertyProcessor(),
			new PrimitivePropertyProcessor(),
			new ELPropertyProcessor(),
			new ComponentPropertyProcessor()};
	
	public JavaClass build(ComponentBean componentBean, JSFGeneratorConfiguration configuration) {
		
		String superclassname = 
			componentBean.getSuperclass();
		
		JavaClass javaClass = 
			new JavaClass(componentBean.getSimpleClassName(), 
					new JavaPackage(componentBean.getPackageName()));
		
		try {
			Class<?> superClass = 
				Class.forName(superclassname, false, configuration.getClassLoader());
			
			javaClass.setSuperClass(superClass);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			javaClass.setSuperClass(UIComponentBase.class);
		}
		
		javaClass.addModifier(JavaModifier.PUBLIC);
		
		javaClass.addField(getComponentFamily(componentBean));
		javaClass.addField(getComponentType(componentBean));
		
		javaClass.addMethod(getConstructor(componentBean, javaClass));
		
		Collection<PropertyBean> properties = componentBean.getProperties();
		
		for (PropertyBean propertyBean : properties) {
			PropertyProcessor proc = null;
			
			for (int i = 0; i < propertyProcessors.length && proc == null; i++) {
				
				PropertyProcessor processor = 
					propertyProcessors[i];
				
				if (processor.accept(propertyBean, javaClass, configuration)) {
					proc = processor;
				}
			}
			
			if (proc != null) {
				proc.process(propertyBean, javaClass, configuration);
			}
		}

		javaClass.addMethod(getComponentFamilyMethod(componentBean));
		
		ComponentStateManager stateManager = new ComponentStateManager(javaClass);
		
		JavaMethod saveState = getSaveStateTemplate();
		try {
			saveState.setMethodBody(stateManager.getSaveStateMethodBody(configuration));
		} catch (GeneratorException e) {
			e.printStackTrace();
		}
		javaClass.addMethod(saveState);
		
		JavaMethod restoreState = getRestoreStateTemplate();
		try {
			restoreState.setMethodBody(stateManager.getRestoreStateMethodBody(configuration));
		} catch (GeneratorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		javaClass.addMethod(restoreState);
		
		
		
		return javaClass;
		
	}
	
	private JavaMethod getComponentFamilyMethod(ComponentBean componentBean) {
		JavaMethod javaMethod = new JavaMethod("getFamily", String.class);
		javaMethod.addModifier(JavaModifier.PUBLIC);
		javaMethod.setMethodBody(new MethodBody() {
			@Override
			public String toCode() {
				return "return COMPONENT_FAMILY;";
			}
		});
		return javaMethod;
	}
	
	private JavaMethod getRestoreStateTemplate() {
		JavaMethod method = 
			new JavaMethod("restoreState", 
					Void.TYPE, 
					arg("context", FacesContext.class), 
					arg("state", Object.class));
		method.addModifier(JavaModifier.PUBLIC);
		method.addAnnotation(Override.class);
		
		return method;
	}
	
	private JavaMethod getSaveStateTemplate() {
		JavaMethod method = 
			new JavaMethod("saveState", 
					Object.class, 
					arg("context", FacesContext.class));
		method.addModifier(JavaModifier.PUBLIC);
		method.addAnnotation(Override.class);
		method.setMethodBody(new MethodBody() {
			@Override
			public String toCode() {
				return "return super.saveState(context);";
			}
		});
		return method;
	}
	
	private JavaField getComponentType(ComponentBean componentBean) {
		JavaField field = new JavaField(String.class, "COMPONENT_TYPE");
		field.addModifier(JavaModifier.PUBLIC);
		field.addModifier(JavaModifier.STATIC);
		field.addModifier(JavaModifier.FINAL);
		field.setValue("\"" + componentBean.getName() + "\"");
		return field;
	}
	private JavaField getComponentFamily(ComponentBean componentBean) {
		JavaField field = new JavaField(String.class, "COMPONENT_FAMILY");
		field.addModifier(JavaModifier.PUBLIC);
		field.addModifier(JavaModifier.STATIC);
		field.addModifier(JavaModifier.FINAL);
		field.setValue("\"" + componentBean.getFamily() + "\"");
		return field;
	}
	
	private JavaMethod getConstructor(ComponentBean componentBean, JavaClass javaClass) {
		final RendererBean renderer = componentBean.getRenderer();
		JavaMethod method = new JavaConstructor(javaClass);
		method.addModifier(JavaModifier.PUBLIC);
		method.setMethodBody(new MethodBody(method) {
			@Override
			public String toCode() {
				if (renderer != null) {
					return "setRendererType(\"" + renderer.getName() + "\");";
				}
				return super.toCode();
			}
		});
		
		return method;
	}
}	
