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
package org.richfaces.model.impl.expressive;

import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.faces.context.FacesContext;

/**
 * 
 * @author Konstantin Mishin
 *
 */
public class MethodBindingExpression extends Expression {

	private ELContext context;
	private MethodExpression methodExpression;

	public MethodBindingExpression(FacesContext faces, MethodExpression methodExpression) {
		super(methodExpression.getExpressionString());
		this.context = faces.getELContext();
		this.methodExpression = methodExpression;
	}

	public Object evaluate(Object base) {
		return methodExpression.invoke(context, new Object[]{base});
	}
}