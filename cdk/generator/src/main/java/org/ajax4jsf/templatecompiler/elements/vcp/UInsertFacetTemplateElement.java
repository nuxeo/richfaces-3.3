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

import java.io.StringWriter;

import org.ajax4jsf.templatecompiler.builder.CompilationContext;
import org.ajax4jsf.templatecompiler.builder.CompilationException;
import org.ajax4jsf.templatecompiler.elements.A4JRendererElementsFactory;
import org.ajax4jsf.templatecompiler.elements.TemplateElementBase;
import org.apache.velocity.VelocityContext;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Processing f:insertFacet-tags.
 * 
 * @author ayukhovich@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.4 $ $Date: 2007/02/26 20:48:48 $
 */
public class UInsertFacetTemplateElement extends TemplateElementBase {
	private static int numberOfFaced = 0;

	private String nameOfElement;
	
	private static final String TEMPLATE = A4JRendererElementsFactory.TEMPLATES_TEMPLATECOMPILER_PATH+"/insertFacet.vm";

	public UInsertFacetTemplateElement(Node element,
			final CompilationContext componentBean) {
		super(element, componentBean);

		NamedNodeMap nnm = element.getAttributes();
		Node value = nnm.getNamedItem("name");
		this.nameOfElement = value.getNodeValue();
	}

	public String getBeginElement() throws CompilationException {

		numberOfFaced++;

		VelocityContext context = new VelocityContext();
		StringWriter strW = new StringWriter();
			context.put("index", new Integer(numberOfFaced));
			context.put("FacetName", this.nameOfElement);
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