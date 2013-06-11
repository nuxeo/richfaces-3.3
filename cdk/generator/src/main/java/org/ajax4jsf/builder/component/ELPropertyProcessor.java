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

import org.ajax4jsf.builder.component.methods.ELPropertyAccessorMethodBody;
import org.ajax4jsf.builder.config.PropertyBean;
import org.ajax4jsf.builder.generator.GeneratorException;
import org.ajax4jsf.builder.generator.JSFGeneratorConfiguration;
import org.ajax4jsf.builder.model.JavaClass;
import org.ajax4jsf.builder.model.JavaField;
import org.ajax4jsf.builder.model.JavaMethod;
import org.ajax4jsf.builder.model.MethodBody;

/**
 * Processing of EL-enabled properties
 * @author Maksim Kaszynski
 *
 */
public class ELPropertyProcessor extends ComponentPropertyProcessor {

	@Override
	public boolean accept(PropertyBean propertyBean, JavaClass javaClass, JSFGeneratorConfiguration configuration) {
		return !(propertyBean.isExist() || propertyBean.isSimpleType()) && propertyBean.isEl() ;
	}

	@Override
	protected JavaMethod getAccessor(JSFGeneratorConfiguration config, PropertyBean propertyBean, JavaField field) {
		field.setValue("null");
		
		JavaMethod accessor = super.getAccessor(config, propertyBean, field);
		
		try {
			ELPropertyAccessorMethodBody propertyAccessorMethodBody = 
				new ELPropertyAccessorMethodBody(config, propertyBean, field);
			
			accessor.setMethodBody(propertyAccessorMethodBody);
			
		} catch (GeneratorException e) {
			accessor.setMethodBody(new MethodBody());
			e.printStackTrace();
		}
		
		return accessor;
	}

}
