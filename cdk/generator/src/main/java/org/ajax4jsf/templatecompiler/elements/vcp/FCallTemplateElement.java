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

package org.ajax4jsf.templatecompiler.elements.vcp;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.ajax4jsf.templatecompiler.builder.CompilationContext;
import org.ajax4jsf.templatecompiler.builder.CompilationException;
import org.ajax4jsf.templatecompiler.el.ELParser;
import org.ajax4jsf.templatecompiler.elements.A4JRendererElementsFactory;
import org.ajax4jsf.templatecompiler.elements.TemplateElementBase;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Processing f:call-tag.
 * 
 * @author ayukhovich@exadel.com (latest modification by $Author:
 *         alexeyyukhovich $)
 * @version $Revision: 1.1.2.3 $ $Date: 2007/02/26 20:48:47 $
 */
public class FCallTemplateElement extends TemplateElementBase {
	private static final Log log = LogFactory.getLog(FCallTemplateElement.class);
	
	private String functionName;

	private String variable;

	private String fullFunctionName;

	private boolean useOnlyEnumeratingParaments;

	private final static String FUNCTION_DELIMITER = "\\.";

	private final static String VAR_ATTRIBUTE_NAME = "var";

	private final static String FUNCTION_NAME_ATTRIBUTE_NAME = "name";

	private final static String USE_ONLY_THIS_PARAMETERS = "use_only_this_parameters";

	private final static String[][] PARAM_NAMES = new String[][] {
			{ "context" }, { "context", "component" } };

	private static final String TEMPLATE = A4JRendererElementsFactory.TEMPLATES_TEMPLATECOMPILER_PATH+"/insertCall.vm";


	private ArrayList parameters;

	public FCallTemplateElement(final Node element,
			final CompilationContext componentBean) throws CompilationException {
		super(element, componentBean);

		this.useOnlyEnumeratingParaments = false;

		NamedNodeMap nnm = element.getAttributes();
		Node functionNameNode = nnm.getNamedItem(FUNCTION_NAME_ATTRIBUTE_NAME);

		if (functionNameNode != null) {
			this.functionName = functionNameNode.getNodeValue();
		} else {
			throw new CompilationException("function name is not set");
		}

		Node nodeUseOnlyThisParameters = nnm
				.getNamedItem(USE_ONLY_THIS_PARAMETERS);
		if (nodeUseOnlyThisParameters != null) {
			this.useOnlyEnumeratingParaments = Boolean
					.getBoolean(nodeUseOnlyThisParameters.getNodeValue());
		} // if

		// read name of variable if need
		Node variableName = nnm.getNamedItem(VAR_ATTRIBUTE_NAME);
		if (variableName != null) {
			this.variable = variableName.getNodeValue();
		} // if

		// read name of parameters if need
		ParameterProcessor parameterProcessor = new ParameterProcessor(element,
				componentBean);

		this.parameters = parameterProcessor.getParameters();
		log.debug(this.parameters);

		List decodeFunctionName = null;

		decodeFunctionName = Arrays.asList(this.functionName
				.split(FUNCTION_DELIMITER));
		if (null == decodeFunctionName) {
			decodeFunctionName = new ArrayList();
			decodeFunctionName.add(this.functionName);
		}

		ArrayList functionNames = new ArrayList();
		String lastClassName = componentBean.getFullBaseclass();

		for (Iterator iter = decodeFunctionName.iterator(); iter.hasNext();) {
			String elementFunction = (String) iter.next();

			try {
				log.debug("Try to load class : " + lastClassName);

				Class clazz = componentBean.loadClass(lastClassName);

				if (!iter.hasNext()) {
					String method = getMethod(clazz, elementFunction);
					if (method != null) {
						log.debug(method);
						functionNames.add(method);
					} else {
						log.error("Method  " + elementFunction
								+ " not found in class : " + lastClassName);
						throw new CompilationException();
					}

				} else {
					//
					// Probing properties !!!!
					//

					PropertyDescriptor propertyDescriptor = getPropertyDescriptor(
							clazz, elementFunction);

					if (propertyDescriptor != null) {
						functionNames.add(propertyDescriptor.getReadMethod()
								.getName()
								+ "()");
						log.debug("Property " + elementFunction
								+ " mapped to function  : "
								+ propertyDescriptor.getReadMethod().getName());
						lastClassName = propertyDescriptor.getPropertyType()
								.getName();
					} else {
						log.error("Property " + elementFunction
								+ " not found in class : " + lastClassName);
						throw new CompilationException();
					}
				}

			} catch (Throwable e) {

				log.error("Error load class : " + lastClassName + ", "
						+ e.getLocalizedMessage());
				e.printStackTrace();
				throw new CompilationException("Error load class : "
						+ lastClassName, e);
			}

		}

		StringBuffer tmpbuf = new StringBuffer();
		for (Iterator iter = functionNames.iterator(); iter.hasNext();) {
			String tmpElement = (String) iter.next();
			if (tmpbuf.length() != 0) {
				tmpbuf.append(".");
			}
			tmpbuf.append(tmpElement);
		}
		this.fullFunctionName = tmpbuf.toString();

	}

	/**
	 * 
	 * @param clazz
	 * @param propertyName
	 * @return
	 */
	private PropertyDescriptor getPropertyDescriptor(Class clazz,
			String propertyName) {
		PropertyDescriptor returnValue = null;

		PropertyDescriptor[] propertyDescriptors = PropertyUtils
				.getPropertyDescriptors(clazz);

		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			if (descriptor.getName().equals(propertyName)) {
				returnValue = descriptor;
				break;
			}
		}

		return returnValue;
	}

	/**
	 * 
	 * @param clazz
	 * @param propertyName
	 * @return
	 */
	private String getMethod(Class clazz, String methodName)
			throws ClassNotFoundException, NoSuchMethodException {
		String returnValue = null;

		Class[][] arrayParametersTypes = null;
		if (this.useOnlyEnumeratingParaments) {
			arrayParametersTypes = new Class[1][this.parameters.size()];
			for (int i = 0; i < this.parameters.size(); i++) {
				arrayParametersTypes[0][i] = ((Parameter) this.parameters
						.get(i)).getType();
			}

		} else {

			arrayParametersTypes = new Class[PARAM_NAMES.length + 1][];

			for (int i = 0; i < arrayParametersTypes.length; i++) {
				int addlength = 0;
				if (i < PARAM_NAMES.length) {
					addlength = PARAM_NAMES[i].length;
				}

				arrayParametersTypes[i] = new Class[addlength
						+ this.parameters.size()];
			}

			for (int i = 0; i < PARAM_NAMES.length; i++) {
				for (int j = 0; j < PARAM_NAMES[i].length; j++) {
					arrayParametersTypes[i][j] = this.getComponentBean()
							.getVariableType(PARAM_NAMES[i][j]);
				}
			}

			for (int i = 0; i < arrayParametersTypes.length; i++) {
				int shift = 0;
				if (i < PARAM_NAMES.length) {
					shift = PARAM_NAMES[i].length;
				}

				for (int j = 0; j < this.parameters.size(); j++) {
					Parameter parameter = (Parameter) this.parameters.get(j);
					arrayParametersTypes[i][shift + j] = parameter.getType();
				}
			}

		}

		List<String> methodNotFoundMessagesList = new ArrayList<String>();
		boolean found = false;
		
		for (int i = 0; i < arrayParametersTypes.length && !found; i++) {
		    
		    try {
			this.getComponentBean().getMethodReturnedClass(clazz, methodName,
				arrayParametersTypes[i]);
			StringBuffer buffer = new StringBuffer();

			buffer.append(methodName);
			buffer.append("(");

			int shift = 0;
			if (i < PARAM_NAMES.length) {
			    shift = PARAM_NAMES[i].length;
			}

			for (int j = 0; j < arrayParametersTypes[i].length; j++) {
			    if (j > 0) {
				buffer.append(", ");
			    }
			    if ((i < PARAM_NAMES.length) && (j < PARAM_NAMES[i].length)) {
				buffer.append(PARAM_NAMES[i][j]);
			    } else {
				Parameter parameter = (Parameter) this.parameters.get(j
					- shift);
				String tmp = ELParser.compileEL(parameter.getValue(),
					this.getComponentBean());
				buffer.append(tmp);
			    }
			}
			buffer.append(")");
			returnValue = buffer.toString();
			found = true;

		    } catch (NoSuchMethodException e) {
			methodNotFoundMessagesList.add(e.getLocalizedMessage());
		    }
		} // for

		if (!found) {
		    throw new NoSuchMethodException("Could not find methods: " + methodNotFoundMessagesList);
		}
		
		return returnValue;
	}

	/**
	 * @throws CompilationException
	 * 
	 */
	public String getBeginElement() throws CompilationException {

		VelocityContext context = new VelocityContext();
			context.put("variableName", this.variable);
			context.put("function", this.fullFunctionName);
			return this.getComponentBean().processTemplate(getTemplateName(), context);
	}

	/**
	 * @return
	 */
	protected String getTemplateName() {
		return TEMPLATE;
	}

	/**
	 * 
	 */
	public String getEndElement() {
		return null;
	}
}