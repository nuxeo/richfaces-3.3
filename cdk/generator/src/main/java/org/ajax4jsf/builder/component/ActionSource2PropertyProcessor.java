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

import java.util.Arrays;
import java.util.List;

import javax.faces.component.ActionSource2;
import javax.faces.el.MethodBinding;

import org.ajax4jsf.builder.component.methods.VelocityMethodBody;
import org.ajax4jsf.builder.config.PropertyBean;
import org.ajax4jsf.builder.generator.GeneratorException;
import org.ajax4jsf.builder.generator.JSFGeneratorConfiguration;
import org.ajax4jsf.builder.model.JavaClass;
import org.ajax4jsf.builder.model.JavaField;
import org.ajax4jsf.builder.model.JavaLanguageElement;
import org.ajax4jsf.builder.model.JavaMethod;

/**
 * @author Maksim Kaszynski
 *
 */
@SuppressWarnings("deprecation")
public class ActionSource2PropertyProcessor extends ComponentPropertyProcessor {

	private static final List<String> ACCEPTED_NAMES = Arrays.asList("action", "actionListener", "actionExpression");
	
	@Override
	public boolean accept(PropertyBean propertyBean, JavaClass javaClass,
			JSFGeneratorConfiguration configuration) {
		
		boolean result = !propertyBean.isExist();
		
		if (result) {
			result = ActionSource2.class.isAssignableFrom(javaClass.getSuperClass());
			if (result) {
				result = ACCEPTED_NAMES.contains(propertyBean.getName());
			}
		}
		
		return result;
	}

	@Override
	public void process(PropertyBean propertyBean, JavaClass javaClass,
			JSFGeneratorConfiguration configuration) {

		JavaField field = getField(propertyBean, configuration);
		JavaMethod accessor = getAccessor(configuration, propertyBean, field);
		JavaMethod mutator = getMutator(configuration, propertyBean, field);
		
		if("action".equals(propertyBean.getName())) {
			
			try {
				accessor.setMethodBody(new VelocityMethodBody(configuration) {
					@Override
					public String getTemplate() {
						return "snippets/get-action.vm";
					}
				});
				mutator.setMethodBody(new VelocityMethodBody(configuration) {
					@Override
					public String getTemplate() {
						return "snippets/set-action.vm";
					}
				});
				
			} catch (GeneratorException e) {
				e.printStackTrace();
			}
			
		} else  {
			javaClass.addField(field);
		}

		if (field.getType().getName().equals(MethodBinding.class.getName())) {
			
			for(JavaLanguageElement el : new JavaLanguageElement[] {field, accessor, mutator}) {
				el.addAnnotation(SuppressWarnings.class, "\"deprecation\"");
			}
			
		}

		
		//Do not add variable
		javaClass.addMethod(accessor);
		javaClass.addMethod(mutator);
		
	}
	
}
