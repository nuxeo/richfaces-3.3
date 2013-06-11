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

import javax.el.Expression;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;

import org.ajax4jsf.builder.config.PropertyBean;
import org.ajax4jsf.builder.generator.JSFGeneratorConfiguration;
import org.ajax4jsf.builder.model.JavaClass;

/**
 * @author Maksim Kaszynski
 *
 */
@SuppressWarnings("deprecation")
public class ExpressionPropertyProcessor extends ComponentPropertyProcessor {
	
	private static final Class<?>[] acceptedClasses = {
		MethodBinding.class, 
		ValueBinding.class, 
		Expression.class};
	
	@Override
	public boolean accept(PropertyBean propertyBean, JavaClass javaClass,
			JSFGeneratorConfiguration configuration) {
		
		boolean result = 
			!propertyBean.isExist() &&
			propertyBean.isEl() && 
			propertyBean.isElonly();
		
		if (result) {
			Class<?> type = 
				getType(propertyBean, configuration.getClassLoader());
			
			result = false;
			
			for (Class<?> clazz : acceptedClasses) {
				if (clazz.isAssignableFrom(type)) {
					result = true;
					break;
				}
			}
			
			
		}
		
		
		
		return result;
	}
}
