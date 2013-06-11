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
 * Processing c:forEach-tag.
 * 
 * @author ayukhovich@exadel.com (latest modification by $Author:
 *         alexeyyukhovich $)
 * @version $Revision: 1.1.2.4 $ $Date: 2007/02/26 20:48:39 $
 */
public class ForEachTemplateElement extends TemplateElementBase {
	private static int indexForCollection = 0;

	private static final String ATTRIBUTE_FROM = "begin";

	private static final String ATTRIBUTE_TO = "end";

	private String strItems;

	private String strFrom;

	private String strTo;

	private String strThisVariable;

	public ForEachTemplateElement(final Node element,
			final CompilationContext componentBean) {
		super(element, componentBean);

		NamedNodeMap nnm = element.getAttributes();

		Node varItem = nnm.getNamedItem("var");
		this.strThisVariable = varItem.getNodeValue();

		Node items = nnm.getNamedItem("items");
		if (items != null) {
			this.strItems = ELParser.compileEL(items.getNodeValue(), componentBean);
		} else {
			Node fromItem = nnm.getNamedItem(ATTRIBUTE_FROM);
			Node toItem = nnm.getNamedItem(ATTRIBUTE_TO);

			this.strFrom = ELParser
					.compileEL(fromItem.getNodeValue(), componentBean);
			this.strTo = ELParser.compileEL(toItem.getNodeValue(), componentBean);
		}
	}

	/**
	 * @throws CompilationException
	 * 
	 */
	public String getBeginElement() throws CompilationException {
		VelocityContext context = new VelocityContext();
		String templateName;
			if (this.strItems != null) {
				indexForCollection++;
				templateName = getTemplate1Name();//"FORElement_1.vm");
				context.put("items", this.strItems);
				context.put("index", new Integer(indexForCollection));
			} else {
				templateName = getTemplate2Name();//"FORElement_2.vm");
				context.put("from", this.strFrom);
				context.put("to", this.strTo);
			}

			context.put("item", this.strThisVariable);
			return this.getComponentBean().processTemplate(templateName, context);
	}

	/**
	 * @return
	 */
	protected String getTemplate2Name() {
		return A4JRendererElementsFactory.TEMPLATES_TEMPLATECOMPILER_PATH+"/FORElement_1.vm";
	}

	/**
	 * @return
	 */
	protected String getTemplate1Name() {
		return A4JRendererElementsFactory.TEMPLATES_TEMPLATECOMPILER_PATH+"/FORElement_2.vm";
	}

	/**
	 * 
	 */
	public String getEndElement() {
		return "}";
	}

}