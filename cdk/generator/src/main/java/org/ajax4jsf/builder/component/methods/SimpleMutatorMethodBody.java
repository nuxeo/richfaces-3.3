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

import org.ajax4jsf.builder.model.JavaField;
import org.ajax4jsf.builder.model.JavaMethod;
import org.ajax4jsf.builder.model.MethodBody;

/**
 * @author Maksim Kaszynski
 *
 */
public class SimpleMutatorMethodBody extends MethodBody {

	private JavaField field;

	public SimpleMutatorMethodBody(JavaMethod method, JavaField field) {
		super(method);
		this.field = field;
	}

	protected JavaField getField() {
		return field;
	}

	protected void setField(JavaField field) {
		this.field = field;
	}

	@Override
	public String toCode() {
		return field.getName() + " = " + getMethod().getArguments().get(0).getName();
	}
	
	
}
