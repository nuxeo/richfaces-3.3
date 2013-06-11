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
import org.ajax4jsf.templatecompiler.el.ELParser;
import org.ajax4jsf.templatecompiler.elements.A4JRendererElementsFactory;
import org.ajax4jsf.templatecompiler.elements.TemplateElementBase;
import org.apache.velocity.VelocityContext;
import org.w3c.dom.Node;

/**
 * Add comma separated list of ids to Ajax Context's rendered areas collection.
 * @author Maksim Kaszynski
 *
 */
public class AjaxRenderedAreaElement extends TemplateElementBase {

	private static final String TEMPLATE = A4JRendererElementsFactory.TEMPLATES_TEMPLATECOMPILER_PATH+"/ajaxRenderedArea.vm";
	
	private String values;
	
	public AjaxRenderedAreaElement(Node element,
			CompilationContext componentBean) {
		super(element, componentBean);
		values = ELParser.compileEL(element.getTextContent(), this.getComponentBean());
		componentBean.addToImport("org.ajax4jsf.context.AjaxContext");
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.templatecompiler.elements.TemplateElement#getBeginElement()
	 */
	public String getBeginElement() throws CompilationException {

		VelocityContext context = new VelocityContext();
		context.put("areas", this.values);
		return this.getComponentBean().processTemplate(getTemplateName(), context);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.templatecompiler.elements.TemplateElement#getEndElement()
	 */
	public String getEndElement() throws CompilationException {
		
		return null;
	}

	protected String getTemplateName() {
		return TEMPLATE;
	}
	
	@Override
	public boolean isSkipBody() {
		// TODO Auto-generated method stub
		return true;
	}

}
