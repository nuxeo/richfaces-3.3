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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * 
 * @author Maksim Kaszynski
 *
 */
public class JavaLanguageElement {

	private Set<JavaModifier> modifiers = new HashSet<JavaModifier>();
	private List<JavaAnnotation> annotations = new ArrayList<JavaAnnotation>();
	private List<JavaComment> comments = new ArrayList<JavaComment>();
	private String name;

	public JavaLanguageElement(String name) {
		super();
		this.name = name;
	}

	public Set<JavaModifier> getModifiers() {
		return modifiers;
	}

	public List<JavaAnnotation> getAnnotations() {
		return annotations;
	}

	public List<JavaComment> getComments() {
		return comments;
	}

	public String getName() {
		return name;
	}
	
	public void addModifier(JavaModifier modifier) {
		modifiers.add(modifier);
	}
	
	public void addAnnotation(JavaAnnotation annotation) {
		annotations.add(annotation);
	}
	public void addAnnotation(Class<?> annotation, String ... arguments) {
		annotations.add(new JavaAnnotation(annotation, arguments));
	}
	public void addAnnotation(Class<?> annotation) {
		annotations.add(new JavaAnnotation(annotation));
	}

}