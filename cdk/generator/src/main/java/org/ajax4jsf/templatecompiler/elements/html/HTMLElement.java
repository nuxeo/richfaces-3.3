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

package org.ajax4jsf.templatecompiler.elements.html;

import java.util.Arrays;
import java.util.Collection;
import java.util.Formatter;
import java.util.List;
import java.util.TreeSet;

import org.ajax4jsf.templatecompiler.builder.CompilationContext;
import org.ajax4jsf.templatecompiler.builder.CompilationException;
import org.ajax4jsf.templatecompiler.el.ELParser;
import org.ajax4jsf.templatecompiler.elements.A4JRendererElementsFactory;
import org.ajax4jsf.templatecompiler.elements.TemplateElementBase;
import org.apache.velocity.VelocityContext;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Processing template HTLM-attributes.
 * 
 * @author ayukhovich@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.3 $ $Date: 2007/02/26 20:48:42 $
 * 
 */
public class HTMLElement extends TemplateElementBase {
	final static private String PRE_TAG_NAME = "pre";

	private static final String PASS_THRU_ATTR = "x:passThruWithExclusions";

	private static final List<String> DEFAULT_EXCLUSIONS = Arrays.asList("class", "id");
	
	private static final String TEMPLATE = A4JRendererElementsFactory.TEMPLATES_TEMPLATECOMPILER_PATH
			+ "/HTMLElement.vm";

	private String htmlTag;

	private String htmlText;

	private HTMLAttributes htmlAttributes = new HTMLAttributes();

	private Collection<String> passThruAttributes = null;

	private CompilationContext componentBean;

	/**
	 * 
	 * @param element
	 * @param variables
	 */
	public HTMLElement(final Node element,
			final CompilationContext componentBean) {
		super(element, componentBean);

		this.passThruAttributes = null;
		this.htmlTag = element.getNodeName();
		this.componentBean = componentBean;
		processingAttributes(element.getAttributes());
	}

	/**
	 * @return string contain
	 * @throws CompilationException
	 */
	public String getBeginElement() throws CompilationException {
		VelocityContext context = new VelocityContext();
		context.put("htmlTag", this.htmlTag);
		context.put("htmlAttributes", this.htmlAttributes.getAttributes());
		context.put("passThruAttributes", this.passThruAttributes);
		if (this.htmlText != null) {
			context.put("htmlText", this.htmlText);
		}
		return this.componentBean.processTemplate(getTemplateName(), context);

	}

	/**
	 * @return
	 */
	protected String getTemplateName() {
		// TODO Auto-generated method stub
		return TEMPLATE;
	}

	/**
	 * @return
	 */
	public String getEndElement() {
		String sReturnValue;

		Object[] objects = new Object[1];
		objects[0] = this.htmlTag;

		sReturnValue = new Formatter().format("writer.endElement(\"%s\");",
				objects).toString();
		return sReturnValue;
	}

	/**
	 * 
	 * @param attributes
	 */
	public void processingAttributes(NamedNodeMap attributes) {
		if (attributes == null) {
			return;
		}

		for (int iElement = 0; iElement != attributes.getLength(); iElement++) {
			Node attribute = attributes.item(iElement);
			String attributeName = attribute.getNodeName();
			String attributeValue = attribute.getNodeValue();

			if (attributeName.equals(PASS_THRU_ATTR)) {
				processingPassThruAtrribute(attributeValue);
			} else {
				this.htmlAttributes.addAttribute(attributeName, ELParser
						.compileEL(attributeValue, this.componentBean));
			}

		} // for
	}

	/**
	 * 
	 * @param listPassThruAttributes
	 */
	private void processingPassThruAtrribute(String listPassThruAttributes) {
		
		passThruAttributes = 
			new TreeSet<String>(HTMLTags.getAttributes(this.htmlTag));
		
		String[] excludeAttributes = listPassThruAttributes.split(",");

		passThruAttributes.removeAll(DEFAULT_EXCLUSIONS);
		
		for (String attribute : excludeAttributes) {
			passThruAttributes.remove(attribute);
		}
		
	}

	/**
	 * Set a body text for HTML-tag
	 * 
	 * @param nodeText
	 */
	public void setText(final String nodeText) {
		String tempStr = null;
		// if TAG is not PRE test do not trim
		if (nodeText.trim().length() != 0) {
			if (this.htmlTag.compareToIgnoreCase(PRE_TAG_NAME) == 0) {
				tempStr = nodeText;
			} else {
				tempStr = nodeText.trim();
			}
		}

		if (null != tempStr) {
			if (null == this.htmlText) {
				this.htmlText = ELParser.compileEL(tempStr, this.componentBean);
			} else {
				this.htmlText = this.htmlText
						+ ELParser.compileEL(tempStr, this.componentBean);
			}
		}
	}
}
