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

import java.util.ArrayList;

import org.ajax4jsf.templatecompiler.builder.CompilationContext;
import org.ajax4jsf.templatecompiler.builder.CompilationException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Processing f:parameter-tags.
 * 
 * @author ayukhovich@exadel.com (latest modification by $Author:
 *         alexeyyukhovich $)
 * @version $Revision: 1.1.2.3 $ $Date: 2007/02/26 20:48:47 $
 */
public class ParameterProcessor {
	private final static String PARAMETER_NODE_NAME = "f:parameter";

	private final static String PARAMETER_NODE_VALUE = "value";

	private final static String PARAMETER_NODE_TYPE = "type";

	private ArrayList arrayParameters = new ArrayList();

	public ParameterProcessor(final Node element, CompilationContext componentBean)
			throws CompilationException {
		NodeList childNodes = element.getChildNodes();
		if (childNodes != null) {
			for (int iElement = 0; iElement < childNodes.getLength(); iElement++) {
				Node childNode = childNodes.item(iElement);
				if ((childNode.getNodeType() == Node.ELEMENT_NODE)
						&& (childNode.getNodeName().equals(PARAMETER_NODE_NAME))) {

					element.removeChild(childNode);
					iElement--;
					
					NamedNodeMap attributes = childNode.getAttributes();

					if (attributes != null) {
						Node nodeValue = attributes
								.getNamedItem(PARAMETER_NODE_VALUE);
						Node nodeType = attributes
								.getNamedItem(PARAMETER_NODE_TYPE);

						if (nodeValue != null) {
							Parameter param = null;

							if (null != nodeType) {
								Class parameterType;
								try {
									parameterType = componentBean
											.loadClass(nodeType.getNodeValue());
								} catch (ClassNotFoundException e) {
									throw new CompilationException(e.getLocalizedMessage(), e);
								}
								param = new Parameter(nodeValue.getNodeValue(),
										parameterType);
							} else {
								param = new Parameter(nodeValue.getNodeValue());
							}
							this.arrayParameters.add(param);
						}
					}
				}
			}
		}
	}

	public ArrayList getParameters() {
		return this.arrayParameters;
	}
}
