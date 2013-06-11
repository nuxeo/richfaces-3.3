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

import org.ajax4jsf.builder.config.PropertyBean;
import org.ajax4jsf.builder.generator.JSFGeneratorConfiguration;
import org.ajax4jsf.builder.model.Argument;
import org.ajax4jsf.builder.model.JavaClass;
import org.ajax4jsf.builder.model.JavaComment;
import org.ajax4jsf.builder.model.JavaField;
import org.ajax4jsf.builder.model.JavaLanguageElement;
import org.ajax4jsf.builder.model.JavaMethod;
import org.ajax4jsf.builder.model.JavaModifier;
import org.ajax4jsf.builder.model.JavaPrimitive;
import org.ajax4jsf.builder.model.MethodBody;

/**
 * Default property handler - a fallback one.
 * @author Maksim Kaszynski
 *
 */
public class ComponentPropertyProcessor implements PropertyProcessor {

	public boolean accept(PropertyBean propertyBean, JavaClass javaClass, JSFGeneratorConfiguration configuration) {
		return !propertyBean.isExist();
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.builder.render.PropertyProcessor#process(org.ajax4jsf.builder.config.PropertyBean, org.ajax4jsf.builder.model.JavaClass)
	 */
	public void process(PropertyBean propertyBean, JavaClass javaClass, JSFGeneratorConfiguration configuration) {
		JavaField field = getField(propertyBean, configuration);

		javaClass.addField(field);
		javaClass.addMethod(getAccessor(configuration, propertyBean, field));
		javaClass.addMethod(getMutator(configuration, propertyBean, field));
	}
	
	protected JavaField getField(PropertyBean propertyBean, JSFGeneratorConfiguration configuration) {
		String name = propertyBean.getName();
		String defaultvalue = propertyBean.getDefaultvalue();
		Class<?> propertyClass = getType(propertyBean, configuration.getClassLoader());
		JavaField field = new JavaField(propertyClass, "_" + name, defaultvalue);
		field.getComments().add(new JavaComment(propertyBean.getXmlEncodedDescription()));
		field.getModifiers().add(JavaModifier.PRIVATE);
		
		if (propertyBean.isTransient()) {
		    field.addModifier(JavaModifier.TRANSIENT);
		}
		
		handleDeprecation(field.getType(), field);
		return field;
	}
	
	protected Class<?> getType(PropertyBean propertyBean, ClassLoader classLoader) {
		if (propertyBean.isSimpleType()) {
			try {
				Class<?> clazz = JavaPrimitive.forName(propertyBean.getClassname());
				return clazz;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				Class<?> clazz = Class.forName(propertyBean.getClassname(), false, classLoader);
				return clazz;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return Object.class;
	}
	
	protected JavaMethod getAccessor(JSFGeneratorConfiguration configuration, PropertyBean propertyBean, final JavaField field) {
		JavaMethod accessor = 
			new JavaMethod(propertyBean.getGetterName(), field.getType());
		
		accessor.setMethodBody(new MethodBody() {
			@Override
			public String toCode() {
				return "return " + field.getName() + ";";
			}
		});
		
		accessor.addModifier(JavaModifier.PUBLIC);
		handleDeprecation(field.getType(), accessor);
		
		return accessor;
	}
	
	protected JavaMethod getMutator(JSFGeneratorConfiguration configuration, PropertyBean propertyBean, final JavaField field) {
		JavaMethod mutator = 
			new JavaMethod(propertyBean.getSetterName(), 
					new Argument(field.getName(), field.getType()));
		
		mutator.setMethodBody(new MethodBody(mutator) {
			@Override
			public String toCode() {
				return "this."  + field.getName() + " = " + field.getName() + ";";
			}
		});
		
		handleDeprecation(field.getType(), mutator);
		mutator.addModifier(JavaModifier.PUBLIC);
		return mutator;
	}
	
	protected void handleDeprecation(Class<?> type, JavaLanguageElement element) {
		if (type.isAnnotationPresent(Deprecated.class)) {
			element.addAnnotation(SuppressWarnings.class, "\"deprecation\"");
		}
	}
}
