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
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ajax4jsf.builder.config.ClassVisitor;
import org.ajax4jsf.builder.config.ClassWalkingLogic;
import org.ajax4jsf.builder.model.JavaPrimitive;
import org.ajax4jsf.templatecompiler.elements.A4JRendererElementsFactory;
import org.ajax4jsf.templatecompiler.elements.ElementsFactory;
import org.ajax4jsf.templatecompiler.elements.TemplateElement;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.w3c.dom.Node;

/**
 * Component Beam.
 * 
 * @author ayukhovich@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.2 $ $Date: 2007/02/26 20:48:42 $
 * 
 */
public abstract class AbstractCompilationContext implements CompilationContext {
	private static final Log log = LogFactory.getLog(AbstractCompilationContext.class);
	
	final private static String VCPBODY = "VCPBODY";

	final private static String regexComponent = "(.*)" + VCPBODY + "(.*)"
			+ VCPBODY + "(.*)";

	final private static Pattern patternComponent = Pattern.compile(
			regexComponent, Pattern.UNIX_LINES + Pattern.DOTALL);

	private final static String DEFAULT_BASE_CLASS = "org.ajax4jsf.renderkit.RendererBase";

	private String packageName;

	private String className;

	private String baseClassName;

	private ArrayList<String> declarations;

	private ArrayList<String> imports;

	private String[] EncodeBegin;

	private String[] EncodeEnd;

	private String[] EncodeChildren;

	private Map<String, Map<String, PropertyDescriptor>> resolvedProperties =
		new HashMap<String, Map<String,PropertyDescriptor>>();
	
	private HashMap<String, Class<?>> variables = new HashMap<String, Class<?>>();

	private static String[] defaultImports = new String[] {
			"java.util.Iterator", "java.util.Collection",
			"java.util.Map",
			"java.io.IOException", "javax.faces.component.UIComponent",
			"javax.faces.context.FacesContext",
			"javax.faces.context.ResponseWriter",
			"org.ajax4jsf.renderkit.ComponentsVariableResolver",
			"org.ajax4jsf.renderkit.ComponentVariables" };
	
	private TemplateElement tree;
	
	private List<ElementsFactory> elementFactories = new ArrayList<ElementsFactory>();


	/**
	 * Name of UIComponent class for this template - can be used for get properties and methods by introspection.
	 */
	private String componentClass;


	/**
	 * Ant Task related classloader for loat UIComponent and Renderer classes for introspection.
	 */
	private ClassLoader classLoader;


	public AbstractCompilationContext() {
		this.baseClassName = DEFAULT_BASE_CLASS;
		this.declarations = new ArrayList<String>();
		this.imports = new ArrayList<String>();
		this.imports.addAll(Arrays.asList(defaultImports));
		// Init default elements factory.
		this.elementFactories.add(new A4JRendererElementsFactory());
	}

	/**
	 * @param loader
	 * @throws CompilationException
	 */
	public AbstractCompilationContext(ClassLoader loader) throws CompilationException {
		this();
		this.classLoader = loader;
		setDefaultVariables();
	}

	
	public void addElementsFactory(ElementsFactory factory) {
		elementFactories.add(0, factory);
	}
	/**
	 * @param componentClass
	 *            The componentClass to set.
	 * @throws CompilationException
	 */
	public void setComponentClass(String componentClass)
			throws CompilationException {
		this.componentClass = componentClass;
		addVariable("component", componentClass);
	}

	/**
	 * set a package name
	 */
	public void setPackageName(final String packageName) {
		this.packageName = packageName;
	}

	/**
	 * set a class name
	 */
	public void setClassName(final String className) {
		this.className = className;
	}

	/**
	 * 
	 * @param fullClassName
	 */
	public void setFullClassName(final String fullClassName) {
		int dotIndex = fullClassName.lastIndexOf(".");

		String tempPackageName = "";
		String tempClassName = fullClassName;

		if (dotIndex != -1) {
			tempPackageName = fullClassName.substring(0, dotIndex);
			tempClassName = fullClassName.substring(dotIndex + 1);
		} // if

		setPackageName(tempPackageName);
		setClassName(tempClassName);

	}

	public void setBaseclass(String baseclassName) throws CompilationException {
		this.baseClassName = baseclassName;
		addVariable("this", baseclassName);
	}

	// ------

	/**
	 * @return package name
	 */
	public String getPackageName() {
		return this.packageName;
	}

	/**
	 * @return base class package name
	 */
	public String getBaseclassPackageName() {
		String packageName = null;
		int dotIndex = this.baseClassName.lastIndexOf(".");

		if (dotIndex != -1) {
			packageName = this.baseClassName.substring(0, dotIndex);
		}

		return packageName;
	}

	/**
	 * @return class name
	 */
	public String getClassName() {
		return this.className;
	}

	/**
	 * @return base class package name
	 */
	public String getBaseclassName() {
		String className = null;

		if ((this.baseClassName != null) && (this.baseClassName.length() != 0)) {
			int dotIndex = this.baseClassName.lastIndexOf(".");

			if (dotIndex != -1) {
				className = this.baseClassName.substring(dotIndex + 1);
			} else {
				className = this.baseClassName;
			}
		}

		return className;
	}

	/**
	 * @return full class name with package name
	 */
	public String getFullClassName() {
		StringBuffer buf = new StringBuffer();

		if ((this.packageName != null) && (this.packageName.length() != 0)) {
			buf.append(this.packageName);
			buf.append('.');
		}

		buf.append(this.className);

		return buf.toString();
	}

	/**
	 * @return base class package name
	 */
	public String getFullBaseclass() {
		return this.baseClassName;
	}

	/**
	 * @return
	 */
	public String getComponentFileName() {
		return getFullClassName().replace('.', '/');
	}

	/**
	 * @return Returns the componentClass.
	 */
	public String getComponentClass() {
		String returnStr = this.componentClass;

		// if ( (componentClass != null) && ( !
		// componentClass.endsWith(".class")) ) {
		// returnStr = componentClass + ".class";
		// }

		return returnStr;
	}

	/**
	 * return class loader
	 */
	public ClassLoader getClassLoader() {
		return this.classLoader;
	}

	/**
	 * @return string array contain declarations
	 */
	public String[] getDeclarations() {
		return this.declarations.toArray(new String[0]);
	}

	/**
	 * @return string array contain declarations
	 */
	public String[] getImports() {
		return this.imports.toArray(new String[0]);
	}

	public void addToImport(String className) {
		this.imports.add(className);
	}

	public void addToDeclaration(String declaration) {
		this.declarations.add(declaration);
	}

	/**
	 * Add variable with type String
	 * 
	 * @param variableName
	 * @throws CompilationException
	 */
	public void addVariable(String variableName) throws CompilationException {
		addVariable(variableName, "java.lang.String");
	}

	public void addVariable(String variableName, Class<?> clazz) {
		this.variables.put(variableName, clazz);
	}

	public void addVariable(String variableName, String typeName)
			throws CompilationException {
		try {
			Class<?> clazz = loadClass(typeName);
			this.variables.put(variableName, clazz);
		} catch (ClassNotFoundException e) {
//			error("Error create variable "+variableName+" with type "+typeName, e);
			throw new CompilationException("Error create renderer variable "+variableName+" with type "+typeName, e);
		}
	}

	public boolean containsVariable(String variableName) {
		return this.variables.containsKey(variableName);
	}

	public Class<?> getVariableType(String variableName) {
		return this.variables.get(variableName);
	}

	public Class<?> loadClass(String className) throws ClassNotFoundException {
		Class<?> clazz = null;
		try {
		    if (JavaPrimitive.isPrimitive(className)) {
			    clazz = JavaPrimitive.forName(className);
		    } else {
			    clazz = this.classLoader.loadClass(className);
		    }
		} catch (ClassNotFoundException e) {
		    throw e;
		} catch (Throwable e) {
		    log.error(e.getLocalizedMessage(), e);
		}

		if (null == clazz) {
			throw new ClassNotFoundException(className);
		}
		return clazz;
	}

	public void setDefaultVariables() throws CompilationException {
		addVariable("component", "javax.faces.component.UIComponent");
		addVariable("context", "javax.faces.context.FacesContext");
		addVariable("writer", "javax.faces.context.ResponseWriter");
		// addLocalVariable("component", "javax.faces.component.UIComponent" );
		addVariable("variables",
				"org.ajax4jsf.renderkit.ComponentVariables");
	}

	public Class<?> getMethodReturnedClass(Class<?> clazz, String methodName,
			Class<?>[] parametersTypes) throws NoSuchMethodException {
		Class<?> returnedType = null;
		log.debug("class : " + clazz.getName() + "\n\t method : "
				+ methodName + "\n\t paramTypes : "
				+ Arrays.asList(parametersTypes).toString());

		Method method = MethodUtils.getMatchingAccessibleMethod(clazz,
				methodName, parametersTypes);

		if (null != method) {
			returnedType = method.getReturnType();
			log.debug("Method found, return type : "
					+ returnedType.getName());

			return returnedType;
		} else {
		    throw new NoSuchMethodException(clazz  + "#" + methodName + "(" + Arrays.toString(parametersTypes) + ")");
		}

	}

	public PropertyDescriptor getPropertyDescriptor(Class<?> clazz,
			String propertyName) {
		
		Map<String, PropertyDescriptor> descriptors = 
			resolvedProperties.get(clazz.getName());
		
		if (descriptors == null) {
			descriptors = resolveProperties(clazz);
			resolvedProperties.put(clazz.getName(), descriptors);
		}
		
		
		return descriptors.get(propertyName);
	}

	private Map<String, PropertyDescriptor> resolveProperties(Class<?> clazz) {
		final Map<String, PropertyDescriptor> descriptors = 
			new HashMap<String, PropertyDescriptor>();
		
		new ClassWalkingLogic(clazz).walk(new ClassVisitor() {
			public void visit(Class<?> clazz) {
				PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(clazz);
				for (PropertyDescriptor descriptor : pds) {
					descriptors.put(descriptor.getName(), descriptor);
				}
			}
		});
		return descriptors;
	}
	
	/**
	 * 
	 */
	public String[] getEncodeBegin() {
		return this.EncodeBegin;
	}

	/**
	 * 
	 */
	public String[] getEncodeChild() {
		return this.EncodeChildren;
	}

	/**
	 * 
	 */
	public String[] getEncodeEnd() {
		return this.EncodeEnd;
	}

	public void setCode(String code) {

		Matcher matcher = patternComponent.matcher(code);

		String strEncodeBegin;
		String strEncodeChildren;
		String strEncodeEnd;

		if (matcher.find()) {
			strEncodeBegin = matcher.group(1);
			strEncodeChildren = matcher.group(2);
			strEncodeEnd = matcher.group(3);
		} else {
			strEncodeBegin = "";
			strEncodeChildren = "";
			strEncodeEnd = code;
		}

		if ((strEncodeBegin != null) && (strEncodeBegin.length() != 0)) {
			this.EncodeBegin = strEncodeBegin.split(";\n");
		}

		if ((strEncodeChildren != null) && (strEncodeChildren.length() != 0)) {
			this.EncodeChildren = strEncodeChildren.split(";\n");
		}

		if ((strEncodeEnd != null) && (strEncodeEnd.length() != 0)) {
			this.EncodeEnd = strEncodeEnd.split(";\n");
		}

	}

	/**
	 * @return the tree
	 */
	public TemplateElement getTree() {
		return this.tree;
	}

	/**
	 * @param tree the tree to set
	 */
	public void setTree(TemplateElement tree) {
		this.tree = tree;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.templatecompiler.builder.CompilationContext#getProcessor(org.w3c.dom.Node)
	 */
	public TemplateElement getProcessor(Node nodeElement) throws CompilationException {
		for (Iterator<ElementsFactory> iter = elementFactories.listIterator(); iter.hasNext();) {
			ElementsFactory	 factory = iter.next();
			TemplateElement processor = factory.getProcessor(nodeElement, this);
			if(null != processor){
				return processor;
			}
		}
		return null;
	}
	
    /* (non-Javadoc)
     * @see org.ajax4jsf.templatecompiler.builder.CompilationContext#processTemplate(java.lang.String, org.apache.velocity.VelocityContext)
     */
    public String processTemplate(String name, VelocityContext context) throws CompilationException {
    	StringWriter out = new StringWriter();
    	try {
			getTemplate(name).merge(context, out);
		} catch (ResourceNotFoundException e) {
			throw new CompilationException(e.getLocalizedMessage());
		} catch (ParseErrorException e) {
			throw new CompilationException(e.getLocalizedMessage());
		} catch (Exception e) {
			throw new CompilationException(e.getLocalizedMessage());
		}
    	return out.toString();
    }
    
    public Map<String, Map<String, PropertyDescriptor>> getResolvedProperties() {
		return resolvedProperties;
	}
}
