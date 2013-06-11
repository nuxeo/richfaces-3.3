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

package org.ajax4jsf.builder.render;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import org.ajax4jsf.builder.model.Argument;
import org.ajax4jsf.builder.model.JavaMethod;
import org.ajax4jsf.builder.model.MethodBody;

/**
 * @author Maksim Kaszynski
 *
 */
public class JavaMethodRenderer extends JavaLanguageElementRenderer<JavaMethod> {

	@Override
	public void render(JavaMethod javaMethod, PrintWriter out) {
		renderComments(javaMethod, out);
		renderAnnotations(javaMethod, out);
		renderModifiers(javaMethod, out);
		
		Class<?> returnType = javaMethod.getReturnType();
		
		if (returnType != null) {
			out.print(returnType.getSimpleName() + " ");
		}
		
		out.print(javaMethod.getName());

		out.print("(");
		List<Argument> arguments = javaMethod.getArguments();
		if (arguments != null) {
			for (Iterator<Argument> iterator = arguments.iterator(); iterator.hasNext();) {
				Argument argument = iterator.next();
				out.print(argument.getType().getSimpleName());
				out.print(" ");
				out.print(argument.getName());
				if (iterator.hasNext()) {
					out.print(", ");
				}
			}
		}
		out.print(")");
		List<Class<Throwable>> exceptions = javaMethod.getExceptions();
		
		if (exceptions != null && !exceptions.isEmpty()) {
			out.print(" throws ");
			for (Iterator<Class<Throwable>> iterator = exceptions.iterator(); iterator.hasNext();) {
				Class<Throwable> class1 = iterator.next();
				out.print(class1.getSimpleName());
				if (iterator.hasNext()) {
					out.print(", ");
				}
				
			}
		}
		
		out.println("{");
		MethodBody methodBody = javaMethod.getMethodBody();
		if (methodBody != null){
			out.println(methodBody.toCode());
		}
		out.println("}");

	}
}
