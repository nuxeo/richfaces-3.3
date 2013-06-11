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
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Java Class model.
 * Intended for building java classes.
 * @author Maksim Kaszynski
 *
 */
public class JavaClass extends JavaLanguageElement{
	private List<JavaField> fields = new ArrayList<JavaField>();
	private List<JavaMethod> methods = new ArrayList<JavaMethod>();
	private Set<JavaImport> imports = new TreeSet<JavaImport>(
			new Comparator<JavaImport> () {
				public int compare(JavaImport o1, JavaImport o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});
	
	private JavaPackage pakg;
	private Class<?> superClass = Object.class;
	
	public JavaClass(String shortName, JavaPackage pakg, Class<?> superClass) {
		this(shortName, pakg);
		setSuperClass(superClass);
	}
	
	public JavaClass(String shortName, JavaPackage pakg) {
		super(shortName);
		this.pakg = pakg;
	}
	
	public void addImport(String name) {
		imports.add(new RuntimeImport(name));
	}
	
	public void addImport(Class<?> claz) {
		if (shouldAddToImports(claz)) {
			imports.add(new ClassImport(claz));
		}
	}
	
	@Override
	public void addAnnotation(JavaAnnotation annotation) {
		super.addAnnotation(annotation);
		addImport(annotation.getType());
	}
	
	public void addField(JavaField field) {
		fields.add(field);
		addImport(field.getType());

		List<JavaAnnotation> annotations2 = field.getAnnotations();
		if (annotations2 != null) {
			for (JavaAnnotation javaAnnotation : annotations2) {
				addImport(javaAnnotation.getType());
			}
		}
	}
	
	public void addMethod(JavaMethod method) {
		methods.add(method);
		addImport(method.getReturnType());
		
		List<Class<Throwable>> exceptions = method.getExceptions();
		
		for (Class<Throwable> exception : exceptions) {
			addImport(exception);
		}
		
		List<Argument> arguments = 
			method.getArguments();
		
		if (arguments != null) {
			for (Argument argument : arguments) {
				addImport(argument.getType());
			}
		}
		
		List<JavaAnnotation> annotations2 = method.getAnnotations();
		if (annotations2 != null) {
			for (JavaAnnotation javaAnnotation : annotations2) {
				addImport(javaAnnotation.getType());
			}
		}
		
		MethodBody methodBody = method.getMethodBody();
		if (methodBody != null) {
			Set<Class<?>> usedClasses = methodBody.getUsedClasses();
			for (Class<?> class1 : usedClasses) {
				addImport(class1);
			}
		}
		
	}
	
	public JavaPackage getPakg() {
		return pakg;
	}
	public Class<?> getSuperClass() {
		return superClass;
	}
	
	public void setSuperClass(Class<?> superClass) {
		this.superClass = superClass;
		addImport(superClass);
	}
	public void setPackage(JavaPackage s) {
		pakg = s;
	}
	public JavaPackage getPackage() {
		return pakg;
	}
	
	public List<JavaField> getFields() {
		return fields;
	}
	public List<JavaMethod> getMethods() {
		return methods;
	}
	public Set<JavaImport> getImports() {
		return imports;
	}
	
	private boolean shouldAddToImports(Class<?> clas) {
		
		boolean result = false;

		if (clas != null) {
			Package p = clas.getPackage();

			if (!(clas.isPrimitive() || p == null)) {
				String importPackageName = p.getName();
				if (importPackageName != null && importPackageName.length() != 0) {

					result = !(importPackageName.equals("java.lang") || importPackageName
							.equals(getPackage().getName()));
				}
			}
		}

		return result;
	}

}
