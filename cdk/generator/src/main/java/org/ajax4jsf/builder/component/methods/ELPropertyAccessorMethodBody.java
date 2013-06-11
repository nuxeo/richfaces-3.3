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

package org.ajax4jsf.builder.component.methods;

import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.FacesException;

import org.ajax4jsf.builder.config.PropertyBean;
import org.ajax4jsf.builder.generator.GeneratorException;
import org.ajax4jsf.builder.generator.JSFGeneratorConfiguration;
import org.ajax4jsf.builder.model.JavaField;

/**
 * Render accessor for EL-enabled property
 * @author Maksim Kaszynski
 *
 */
public class ELPropertyAccessorMethodBody extends VelocityMethodBody {

	public ELPropertyAccessorMethodBody(JSFGeneratorConfiguration configuration, 
			PropertyBean propertyBean, 
			JavaField field)
			throws GeneratorException {
		super(configuration);
		
		getContext().put("field", field);
		getContext().put("property", propertyBean);
		addType(ValueExpression.class);
		addType(ELException.class);
		addType(FacesException.class);
	}

	@Override
	public String getTemplate() {
		return "snippets/el-property-accessor.vm";
	}
	
}
