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

import org.ajax4jsf.templatecompiler.builder.CompilationContext;
import org.ajax4jsf.templatecompiler.builder.CompilationException;
import org.ajax4jsf.templatecompiler.elements.A4JRendererElementsFactory;
import org.ajax4jsf.templatecompiler.elements.TemplateElementBase;
import org.apache.velocity.VelocityContext;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Processing f:resource-tag.
 * 
 * @author ayukhovich@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.4 $ $Date: 2007/02/26 20:48:48 $
 */
public class FResourceTemplateElement extends TemplateElementBase {
	private final static String VAR_ATTRIBUTE_NAME = "var";

	private final static String NAME_ATTRIBUTE_NAME = "name";

	private String name;

	private String variable;

	public FResourceTemplateElement(Node element,
			final CompilationContext componentBean) {
		super(element, componentBean);
		NamedNodeMap nnm = element.getAttributes();

		Node classNode = nnm.getNamedItem(NAME_ATTRIBUTE_NAME);
		Node variableNode = nnm.getNamedItem(VAR_ATTRIBUTE_NAME);

		if (classNode != null) {
			this.name = classNode.getNodeValue();
		}

		if (variableNode != null) {
			this.variable = variableNode.getNodeValue();
		}

	}

	public String getBeginElement() throws CompilationException {
		VelocityContext context = new VelocityContext();
			context.put("variable", this.variable);
			context.put("resourceURI", this.name);
			return this.getComponentBean().processTemplate(getTemplateName(), context);
	}

	/**
	 * @return
	 */
	protected String getTemplateName() {
		return A4JRendererElementsFactory.TEMPLATES_TEMPLATECOMPILER_PATH+"/insertResource.vm";
	}

	public String getEndElement() {
		return null;
	}

	public String getName() {
		return name;
	}
	
	public String getVariable() {
		return variable;
	}
}
