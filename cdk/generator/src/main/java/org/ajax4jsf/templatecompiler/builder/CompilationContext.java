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

package org.ajax4jsf.templatecompiler.builder;

import java.beans.PropertyDescriptor;

import org.ajax4jsf.templatecompiler.elements.TemplateElement;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.w3c.dom.Node;

/**
 * Component Beam.
 * 
 * @author ayukhovich@exadel.com (latest modification by $Author:
 *         alexeyyukhovich $)
 * @version $Revision: 1.1.2.2 $ $Date: 2007/02/26 20:48:42 $
 * 
 */
public interface CompilationContext {
	public void setComponentClass(String componentClass)
			throws CompilationException;

	public void setPackageName(final String packageName);

	public void setClassName(final String className);

	public void setFullClassName(final String fullClassName);

	public void setBaseclass(String baseclassName) throws CompilationException;

	public void setCode(String code);

	public void addToImport(String className);

	public void addToDeclaration(String declaration);

	public ClassLoader getClassLoader();

	public String getPackageName();

	public String getBaseclassPackageName();

	public String getClassName();

	public String getBaseclassName();

	public String getFullClassName();

	public String getFullBaseclass();

	public String getComponentFileName();

	public String getComponentClass();

	public String[] getDeclarations();

	public String[] getImports();

	/**
	 * 
	 * @return
	 */
	public String[] getEncodeBegin();

	/**
	 * 
	 * @return
	 */
	public String[] getEncodeChild();

	/**
	 * 
	 * @return
	 */
	public String[] getEncodeEnd();

	/**
	 * Add variable
	 * 
	 * @param variableName
	 * @throws CompilationException
	 */
	public void addVariable(String variableName) throws CompilationException;

	public void addVariable(String variableName, Class<?> clazz);

	public void addVariable(String variableName, String typeName)
			throws CompilationException;

	public boolean containsVariable(String variableName);

	public Class<?> getVariableType(String variableName);

	public void setDefaultVariables() throws CompilationException;

	public Class<?> loadClass(String className) throws ClassNotFoundException;

	public Class<?> getMethodReturnedClass(Class<?> clazz, String methodName,
			Class<?>[] parametersTypes) throws NoSuchMethodException;

	public PropertyDescriptor getPropertyDescriptor(Class<?> clazz,
			String propertyName);
	
	public TemplateElement getTree();
	
	public void setTree(TemplateElement tree);
	
	
	public abstract TemplateElement getProcessor(final Node nodeElement
			) throws CompilationException;

//	public abstract Attribute getAttributeProcessor(final Node attribute
//	) throws CompilationException;

	public abstract Template getTemplate(String name) throws CompilationException;
	
	public abstract String processTemplate(String name, VelocityContext context) throws CompilationException;
	
	public void debug(String content);
	public void debug(String content,Throwable error);
	public void info(String content);
	public void info(String content,Throwable error);
	public void warn(String content);
	public void warn(String content,Throwable error);
	public void error(String content);
	public void error(String content,Throwable error);

}
