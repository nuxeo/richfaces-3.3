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
import java.util.Set;

import org.ajax4jsf.builder.model.JavaAnnotation;
import org.ajax4jsf.builder.model.JavaComment;
import org.ajax4jsf.builder.model.JavaLanguageElement;
import org.ajax4jsf.builder.model.JavaModifier;
/**
 * 
 * @author Maksim Kaszynski
 *
 * @param <T>
 */
public abstract class JavaLanguageElementRenderer<T extends JavaLanguageElement> {

	public JavaLanguageElementRenderer() {
		super();
	}

	public void renderModifiers(T element, PrintWriter out) {
		Set<JavaModifier> modifiers = element.getModifiers();
		for (JavaModifier javaModifier : modifiers) {
			out.print(javaModifier);
			out.print(" ");
		}
	}
	
	public void renderAnnotations(T element, PrintWriter out) {
		List<JavaAnnotation> annotations = element.getAnnotations();
		if (annotations != null) {
			for (JavaAnnotation javaAnnotation : annotations) {
				out.print("@" + javaAnnotation.getType().getSimpleName());
				List<String> arguments = javaAnnotation.getArguments();
				if (arguments != null && !arguments.isEmpty()) {
					out.print("(");
					for (Iterator<String> iterator = arguments.iterator(); iterator
							.hasNext();) {
						String string = iterator.next();
						out.print(string);
						if (iterator.hasNext()) {
							out.print(", ");
						}
					}
					out.print(")");
				}
				out.println();
			}
		}
		
	}

	public void renderComments(T element, PrintWriter out) {
		List<JavaComment> comments = element.getComments();
		for (JavaComment javaComment : comments) {
			out.println("/*");
			out.println("* " + javaComment.getValue());
			out.println("*/");
		}
	}
	
	public abstract void render(T element, PrintWriter out);
}