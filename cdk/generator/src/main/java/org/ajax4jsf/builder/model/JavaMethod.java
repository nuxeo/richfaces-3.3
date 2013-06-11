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

package org.ajax4jsf.builder.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Maksim Kaszynski
 *
 */
public class JavaMethod extends JavaLanguageElement {
	
	private Class<?> returnType;
	
	private MethodBody methodBody;
	
	private List <Argument> arguments = new ArrayList<Argument>();
	private List<Class<Throwable>> exceptions = new ArrayList<Class<Throwable>>();
	
	public List<Class<Throwable>> getExceptions() {
		return exceptions;
	}

	public void setExceptions(List<Class<Throwable>> exceptions) {
		this.exceptions = exceptions;
	}

	public JavaMethod(String name) {
		super(name);
		this.returnType = Void.TYPE;
	}

	public JavaMethod(String name, Argument ... arguments) {
		this(name);
		this.arguments = Arrays.asList(arguments);
	}

	public JavaMethod(String name, Class<?> returnType, Argument ... arguments) {
		this(name);
		this.returnType = returnType;
		this.arguments = Arrays.asList(arguments);
	}
	public List<Argument> getArguments() {
		return arguments;
	}
	
	public MethodBody getMethodBody() {
		return methodBody;
	}


	public Class<?> getReturnType() {
		return returnType;
	}

	public void setMethodBody(MethodBody methodBody) {
		this.methodBody = methodBody;
		if (methodBody != null) {
			methodBody.setMethod(this);
		}
	}
}
