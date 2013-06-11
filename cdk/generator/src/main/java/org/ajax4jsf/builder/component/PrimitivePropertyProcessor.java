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

import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.FacesException;

import org.ajax4jsf.builder.component.methods.VelocityMethodBody;
import org.ajax4jsf.builder.config.PropertyBean;
import org.ajax4jsf.builder.generator.GeneratorException;
import org.ajax4jsf.builder.generator.JSFGeneratorConfiguration;
import org.ajax4jsf.builder.model.JavaClass;
import org.ajax4jsf.builder.model.JavaField;
import org.ajax4jsf.builder.model.JavaMethod;
import org.ajax4jsf.builder.model.JavaModifier;
import org.ajax4jsf.builder.model.JavaPrimitive;
import org.ajax4jsf.builder.model.MethodBody;

/**
 * Handle primitive properties
 * @author Maksim Kaszynski
 *
 */
public class PrimitivePropertyProcessor extends ComponentPropertyProcessor {

	class PrimitiveELPropertyAccessorMethodBody extends VelocityMethodBody {

		public PrimitiveELPropertyAccessorMethodBody(
				JSFGeneratorConfiguration configuration, JavaField field, JavaField field2, PropertyBean property)
				throws GeneratorException {
			super(configuration);
			getContext().put("field1", field);
			getContext().put("field2", field2);
			getContext().put("wrapperClass", JavaPrimitive.wrapperType(field.getType()));
			getContext().put("property", property);
			addType(ValueExpression.class);
			addType(ELException.class);
			addType(FacesException.class);
		}
		
		@Override
		public String getTemplate() {
			return "snippets/primitive-el-property-accessor.vm";
		}
	}
	
	class PrimitivePropertyAccessorMethodBody extends VelocityMethodBody {

		public PrimitivePropertyAccessorMethodBody(
				JSFGeneratorConfiguration configuration, JavaField field, JavaField field2)
				throws GeneratorException {
			super(configuration);
			getContext().put("field1", field);
			getContext().put("field2", field2);
		}
		
		@Override
		public String getTemplate() {
			return "snippets/primitive-property-accessor.vm";
		}
	}
	
	class PrimitivePropertyMutatorMethodBody extends VelocityMethodBody {

		public PrimitivePropertyMutatorMethodBody(
				JSFGeneratorConfiguration configuration, JavaField field, JavaField field2)
				throws GeneratorException {
			super(configuration);
			getContext().put("field1", field);
			getContext().put("field2", field2);
		}
		
		@Override
		public String getTemplate() {
			return "snippets/primitive-property-mutator.vm";
		}
	}

	@Override
	public boolean accept(PropertyBean propertyBean, JavaClass javaClass, JSFGeneratorConfiguration configuration) {
		return propertyBean.isSimpleType() && super.accept(propertyBean, javaClass, configuration);
	}
	
	@Override
	public void process(PropertyBean propertyBean, JavaClass javaClass, JSFGeneratorConfiguration configuration) {
		JavaField field = getField(propertyBean, configuration);
		JavaField field2 = new JavaField(boolean.class, field.getName() + "Set", "false");
		field2.addModifier(JavaModifier.PRIVATE);
		if (propertyBean.isTransient()) {
		    field2.addModifier(JavaModifier.TRANSIENT);
		}
		JavaMethod accessor = getAccessor(configuration, propertyBean, field);
		MethodBody accessorMethodBody;
		
		try {
			if (propertyBean.isEl()) {
				accessorMethodBody = new PrimitiveELPropertyAccessorMethodBody(configuration, field, field2, propertyBean);
			} else {
				accessorMethodBody = new PrimitivePropertyAccessorMethodBody(configuration, field, field2);
			}
			accessor.setMethodBody(accessorMethodBody);
		} catch (GeneratorException e) {
			e.printStackTrace();
		}
		
		JavaMethod mutator = getMutator(configuration, propertyBean, field);
		
		try {
			PrimitivePropertyMutatorMethodBody mutatorBody = new PrimitivePropertyMutatorMethodBody(configuration, field, field2);
			mutator.setMethodBody(mutatorBody);
		} catch (GeneratorException e) {
			e.printStackTrace();
		}
		
		javaClass.addField(field);
		javaClass.addField(field2);
		javaClass.addMethod(accessor);
		javaClass.addMethod(mutator);
	}
	
}
