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

package org.ajax4jsf.builder.component.state;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.ajax4jsf.builder.component.methods.VelocityMethodBody;
import org.ajax4jsf.builder.config.PropertyBean;
import org.ajax4jsf.builder.generator.GeneratorException;
import org.ajax4jsf.builder.generator.JSFGeneratorConfiguration;
import org.ajax4jsf.builder.model.JavaClass;
import org.ajax4jsf.builder.model.JavaField;
import org.ajax4jsf.builder.model.JavaModifier;
import org.ajax4jsf.builder.model.MethodBody;

/**
 * Class responsible for generating save/restore state methods.
 * @author Maksim Kaszynski
 *
 */
public class ComponentStateManager {
	
	private class SaveStateMethodBody extends VelocityMethodBody {
		
		public SaveStateMethodBody(JSFGeneratorConfiguration configuration)
				throws GeneratorException {
			super(configuration);
			getContext().put("size", componentStates.size() + 1);
			getContext().put("descriptors", componentStates);
		}

		@Override
		public String getTemplate() {
			return "snippets/save-state.vm";
		}
	}
	
	private class RestoreStateMethodBody extends VelocityMethodBody {
		
		public RestoreStateMethodBody(JSFGeneratorConfiguration configuration)
				throws GeneratorException {
			super(configuration);
			getContext().put("size", componentStates.size() + 1);
			getContext().put("descriptors", componentStates);
		}

		@Override
		public String getTemplate() {
			return "snippets/restore-state.vm";
		}
		
	}
	
	
	@SuppressWarnings("serial")
	private static final List<Class<?>> detached = new ArrayList<Class<?>>() {
		{
			add(String.class);
			add(Integer.class);
			add(Boolean.class);
			add(Long.class);
			add(Float.class);
			add(Double.class);
			add(BigDecimal.class);
		}
	};
	
	private List<ComponentStateDescriptor> componentStates = 
		new ArrayList<ComponentStateDescriptor>();
	
	
	public ComponentStateManager(JavaClass componentClass) {
		List<JavaField> fields = componentClass.getFields();
		for (JavaField javaField : fields) {
			registerField(javaField);
		}
	}

	public ComponentStateDescriptor getDesriptor(PropertyBean propertyBean, JavaField field) {
		
		Set<JavaModifier> modifiers = field.getModifiers();
		if (modifiers.contains(JavaModifier.FINAL) 
			|| modifiers.contains(JavaModifier.STATIC)
			|| modifiers.contains(JavaModifier.TRANSIENT)){
			return null;
		}
		
		Class<?> type = field.getType();
		if (type.isPrimitive()) {
			return new PrimitiveStateDescriptor(field);
		} 
		if (detached.contains(type)) {
			return new SimplePropertyDescriptor(field);
		}
		
		return new AttachedStateDescriptor(field);
	}
	
	
	
	public void registerField(JavaField field) {
		ComponentStateDescriptor desriptor = getDesriptor(null, field);
		if (desriptor != null){
			componentStates.add(desriptor);
		}
	}
	
	public MethodBody getSaveStateMethodBody(JSFGeneratorConfiguration configuration) 
		throws GeneratorException{
		return new SaveStateMethodBody(configuration);
	}

	public MethodBody getRestoreStateMethodBody(JSFGeneratorConfiguration configuration) 
	throws GeneratorException{
		return new RestoreStateMethodBody(configuration);
	}
	
}
