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
 * Processing c:object
 * 
 * @author nbelaevski@exadel.com 
 * 
 */
public class ObjectTemplateElement extends TemplateElementBase {

	private static final String TEMPLATE = A4JRendererElementsFactory.TEMPLATES_TEMPLATECOMPILER_PATH+"/object.vm";

	private String strThisVariable;

	private String strExpression;

	//added by Nick - 9/08/07
	private String strType;
	//by Nick
	
	public ObjectTemplateElement(final Node element,
			final CompilationContext componentBean) {
		super(element, componentBean);

		String strTempExpression;

		NamedNodeMap nnm = element.getAttributes();
		String sVariableName = nnm.getNamedItem("var").getNodeValue();
		Node value = nnm.getNamedItem("value");

		Node type = nnm.getNamedItem("type");
		if (type != null) {
			this.strType = type.getNodeValue();
		}
		
		if (value != null) {
			strTempExpression = value.getNodeValue();
		} else {
			strTempExpression = element.getTextContent();
		}

		this.strThisVariable = sVariableName;

		if (strTempExpression != null && strTempExpression.length() != 0) {
			this.strExpression = ELParser.compileEL(strTempExpression, componentBean);
		} else {
		    	if (String.class.getName().equals(this.strType)) {
		    	    this.strExpression = "\"\"";
		    	}
		}

		if (this.strType != null) {
			try {
				this.getComponentBean().addVariable(this.strThisVariable, this.strType);
			} catch (CompilationException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		
	}

	public String getBeginElement() throws CompilationException {
		VelocityContext context = new VelocityContext();
			context.put("variable", this.strThisVariable);
			context.put("expression", this.strExpression);
			context.put("type", this.strType != null ? this.strType.replace('$', '.') : null);
			
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