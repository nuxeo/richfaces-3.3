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
import java.util.List;
import java.util.Set;

import javax.faces.component.UIComponentBase;

import org.ajax4jsf.builder.model.Argument;
import org.ajax4jsf.builder.model.JavaClass;
import org.ajax4jsf.builder.model.JavaField;
import org.ajax4jsf.builder.model.JavaImport;
import org.ajax4jsf.builder.model.JavaMethod;
import org.ajax4jsf.builder.model.JavaModifier;
import org.ajax4jsf.builder.model.JavaPackage;
import org.ajax4jsf.builder.model.MethodBody;

/**
 * @author Maksim Kaszynski
 *
 */
public class JavaClassRenderer extends JavaLanguageElementRenderer<JavaClass> {
	
	private JavaMethodRenderer methodRenderer = new JavaMethodRenderer();
	
	private JavaFieldRenderer fieldRenderer = new JavaFieldRenderer();
	
	public void render(JavaClass javaClass, PrintWriter out){
		out.println("package " + javaClass.getPackage().getName() + ";");
		out.println();
		Set<JavaImport> imports = javaClass.getImports();
		
		for (JavaImport impord : imports) {
			out.println("import " + impord.getName() + ";");
		}
		
		out.println();

		renderAnnotations(javaClass, out);
		
		renderModifiers(javaClass, out);
		out.print("class " + javaClass.getName() ); 
		
		Class<?> superClass = 
			javaClass.getSuperClass();
		
		if (!Object.class.getName().equals(superClass.getName())) {
			out.print(" extends " + superClass.getSimpleName());
		}
		
		out.println("{");

		out.println();

		List<JavaField> fields = javaClass.getFields();
		for (JavaField javaField : fields) {
			fieldRenderer.render(javaField, out);
			out.println();

		}

		out.println();

		
		List<JavaMethod> methods = javaClass.getMethods();
		for (JavaMethod javaMethod : methods) {
			methodRenderer.render(javaMethod, out);
			out.println();

		}
		
		
		out.println("}");
		out.flush();
		out.close();
	}
	
	@interface Tezt {
		
	}
	
	public static void main(String[] args) {
		JavaClass javaClass = new JavaClass("MyClass", new JavaPackage("mypackage"));
		
		JavaField javaField = new JavaField(int.class, "count");
		javaField.setValue(0);
		javaField.getModifiers().add(JavaModifier.PRIVATE);
		javaClass.addField(javaField);
		
		JavaField field = 
			new JavaField(UIComponentBase.class, "component", "null");
		field.addModifier(JavaModifier.PUBLIC);
		field.addAnnotation(Deprecated.class);
		javaClass.addField(field);
		
		javaClass.addAnnotation(Deprecated.class);
		
		JavaMethod accessor = new JavaMethod("getCount", int.class);
		accessor.setMethodBody(
				new MethodBody(accessor) {
					@Override
					public String toCode() {
						return "return count;";
					}
				}
			);
		accessor.getModifiers().add(JavaModifier.PUBLIC);
		accessor.getModifiers().add(JavaModifier.FINAL);
		javaClass.addMethod(accessor);
		
		JavaMethod mutator = new JavaMethod("setCount", 
				new Argument("i", int.class));
		mutator.setMethodBody(
				new MethodBody(mutator) {
					@Override
					public String toCode() {
						return "count = i;";
					}
				}
			);
		mutator.addAnnotation(Tezt.class);
		mutator.addModifier(JavaModifier.PUBLIC);
		mutator.addModifier(JavaModifier.FINAL);
		javaClass.addMethod(mutator);
		
		PrintWriter printWriter = new PrintWriter(System.out);
		new JavaClassRenderer().render(javaClass, printWriter);
		printWriter.flush();
	}
}
