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

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.ajax4jsf.templatecompiler.elements.TemplateElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Template compiler.
 * 
 * @author ayukhovich@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.4 $ $Date: 2007/02/26 20:48:43 $
 * 
 */
public class TemplateCompiler {
	final private static String CLASS_ATTRIBUTE = "class";

	final private static String BASECLASS_ATTRIBUTE = "baseclass";

	final private static String COMPONENT_CLASS_ATTRIBUTE = "component";

	public static final String TEMPLATES_TEMPLATECOMPILER_PATH = "META-INF/templates/templatecompiler";

	public static final String TEMPLATES_PATH = "META-INF/templates";

	final private static String TEMPLATE_FILE = "ComponentTemplate.vm";

//	private TreeCollectionElement treeElements = new TreeCollectionElement();

	/**
	 * Processing input, contain a template
	 * 
	 * @param input
	 *            InputStream contain template
	 * @param componentBean
	 * @throws CompilationException
	 */
	public void processing(InputStream input, CompilationContext componentBean)
			throws CompilationException {

		try {
			Document xmlDocument = parseXml(input);

			// Get Root xmlElement
			Element rootElement = xmlDocument.getDocumentElement();

			// Get Attributes
			NamedNodeMap attributes = rootElement.getAttributes();
			if (attributes != null) {
				Node attributeClass = attributes.getNamedItem(CLASS_ATTRIBUTE);
				if (attributeClass != null) {
					componentBean.setFullClassName(attributeClass
							.getNodeValue());
				}
				Node attributeBaseclass = attributes
						.getNamedItem(BASECLASS_ATTRIBUTE);
				if (attributeBaseclass != null) {
					String nodeValue = attributeBaseclass.getNodeValue();
					componentBean.setBaseclass(nodeValue);
				}
				// Name of base class for UIComponent rendered by template
				Node componentClass = attributes
						.getNamedItem(COMPONENT_CLASS_ATTRIBUTE);
				if (componentClass != null) {
					String nodeValue = componentClass.getNodeValue();
					componentBean.setComponentClass(nodeValue);
				}
			}

			componentBean.setTree(parseElement(rootElement,
					componentBean));


		} catch (Exception e) {
			throw new CompilationException(e);
		}
	}

	/**
	 * @param input
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	protected Document parseXml(InputStream input) throws ParserConfigurationException, SAXException, IOException {
		// Create Document Builder Factory
		DocumentBuilderFactory docFactory = DocumentBuilderFactory
				.newInstance();

		// Create Document Builder
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// open and parse XML-file
		Document xmlDocument = docBuilder.parse(input);
		return xmlDocument;
	}

	public void generateCode(CompilationContext componentBean,Writer out) throws CompilationException {
		
		try {
		out.write(componentBean.getTree().toCode());
		} catch (IOException e) {
			throw new CompilationException(e);
		}


	}
	/**
	 * 
	 * @param element
	 * @param context
	 * @return
	 * @throws CompilationException
	 */
	private TemplateElement parseElement(Node element,
			CompilationContext context) throws CompilationException {
		TemplateElement templateElement = null;
		templateElement = context.getProcessor(element);

		if (templateElement == null) {
			return null;
		}


		if (!templateElement.isSkipBody()) {
			NodeList subNodes = element.getChildNodes();

			for (int iElement = 0; iElement != subNodes.getLength(); iElement++) {
				Node subElement = subNodes.item(iElement);
				templateElement.addSubElement(parseElement(subElement,
							context));
			} // for
		}// if
		return templateElement;

	}

}
