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

package org.ajax4jsf.templatecompiler.elements.std;

import org.ajax4jsf.templatecompiler.builder.CompilationContext;
import org.ajax4jsf.templatecompiler.builder.CompilationException;
import org.ajax4jsf.templatecompiler.el.ELParser;
import org.ajax4jsf.templatecompiler.elements.A4JRendererElementsFactory;
import org.ajax4jsf.templatecompiler.elements.TemplateElementBase;
import org.apache.velocity.VelocityContext;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Processing c:set-tag.
 * 
 * @author ayukhovich@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.3 $ $Date: 2007/02/26 20:48:39 $
 */
public class SetTemplateElement extends TemplateElementBase {

	private static final String TEMPLATE = A4JRendererElementsFactory.TEMPLATES_TEMPLATECOMPILER_PATH+"/set.vm";

	private String strThisVariable;

	private String strExpression;


	public SetTemplateElement(final Node element,
			final CompilationContext componentBean) {
		super(element, componentBean);

		String strTempExpression;

		NamedNodeMap nnm = element.getAttributes();
		String sVariableName = nnm.getNamedItem("var").getNodeValue();
		Node value = nnm.getNamedItem("value");

		if (value != null) {
			strTempExpression = value.getNodeValue();
		} else {
			strTempExpression = element.getTextContent();
		}

		this.strThisVariable = sVariableName;

		this.strExpression = ELParser.compileEL(strTempExpression, componentBean);
	}

	public String getBeginElement() throws CompilationException {
		VelocityContext context = new VelocityContext();
			context.put("variable", this.strThisVariable);
			context.put("expression", this.strExpression);
			return this.getComponentBean().processTemplate(getTemplateName(), context);

	}

	/**
	 * @return
	 */
	protected String getTemplateName() {
		return TEMPLATE;
	}

	public String getEndElement() {
		return null;
	}
}