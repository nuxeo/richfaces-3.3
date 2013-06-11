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

package org.ajax4jsf.templatecompiler.elements;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ajax4jsf.templatecompiler.builder.CompilationContext;
import org.ajax4jsf.templatecompiler.builder.CompilationException;
import org.ajax4jsf.templatecompiler.elements.vcp.VcpBodyTemplateElement;
import org.apache.velocity.VelocityContext;
import org.w3c.dom.Node;

/**
 * @author shura
 * 
 */
public class RootElement extends TemplateElementBase {

	private static final String TEMPLATE = A4JRendererElementsFactory.TEMPLATES_TEMPLATECOMPILER_PATH+"/ComponentTemplate.vm";
	private static final String ENCODE_BEGIN_TEMPLATE = A4JRendererElementsFactory.TEMPLATES_TEMPLATECOMPILER_PATH+"/EncodeBegin.vm";
	private static final String ENCODE_END_TEMPLATE = A4JRendererElementsFactory.TEMPLATES_TEMPLATECOMPILER_PATH+"/EncodeEnd.vm";
	private static final String ENCODE_CHILDREN_TEMPLATE = A4JRendererElementsFactory.TEMPLATES_TEMPLATECOMPILER_PATH+"/EncodeChildren.vm";

	final private static String regexComponent = "(.*)" + VcpBodyTemplateElement.STR_VCB_BODY + "(.*)"
	+ VcpBodyTemplateElement.STR_VCB_BODY + "(.*)";

	final private static Pattern patternComponent = Pattern.compile(
	regexComponent, Pattern.UNIX_LINES + Pattern.DOTALL);

	/**
	 * @param element
	 * @param componentBean
	 */
	public RootElement(Node element, CompilationContext componentBean) {
		super(element, componentBean);
		// TODO apply global attributes
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.templatecompiler.elements.TemplateElement#getBeginElement()
	 */
	public String getBeginElement() throws CompilationException {
		VelocityContext context = new VelocityContext();
		context.put("component", this.getComponentBean());
		return this.getComponentBean().processTemplate(getTemplateName(), context);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.templatecompiler.elements.TemplateElementBase#toCode()
	 */
	public String toCode() throws CompilationException {
		VelocityContext context = new VelocityContext();
		context.put("component", this.getComponentBean());
		StringBuffer code = new StringBuffer();
		String beginElement = this.getBeginElement();
		if (null != beginElement) {
			code.append(beginElement);
			code.append("\n");
		}
		StringBuffer declarationsCode = new StringBuffer();
		StringBuffer methodCode = new StringBuffer();
		for (ListIterator<TemplateElement> iter = getSubElements().listIterator(); iter.hasNext();) {
			TemplateElement element = iter.next();
			String toCode = element.toCode();
			if(element instanceof DeclarationElement){
				declarationsCode.append(toCode);
			} /*else if(element instanceof BodyElement){
				// All collected code put to encodeBegin method.
				if (methodCode.length()>0) {
					context.put("body", methodCode.toString());
					code.append(this.getComponentBean().processTemplate(
							getEncodeBeginTemplateName(), context));
				}
				// If body contain any code, create encodeChildren method.
				if (toCode.length()>0) {
					context.put("body", toCode);
					code.append(this.getComponentBean().processTemplate(
							getEncodeChildrenTemplateName(), context));
				}
				methodCode = new StringBuffer();
			} */
			else {
				methodCode.append(toCode);				
			}
		}

		if (methodCode.length()>0) {
		
		Matcher matcher = patternComponent.matcher(methodCode);

		String strEncodeBegin;
		String strEncodeChildren;
		String strEncodeEnd;

		if (matcher.find()) {
			strEncodeBegin = matcher.group(1);
			// put content before body element to encodeBegin
			if (strEncodeBegin.trim().length()>0) {
				context.put("body", strEncodeBegin);
				code.append(this.getComponentBean().processTemplate(
						getEncodeBeginTemplateName(), context));
			}
			strEncodeChildren = matcher.group(2);
			if (strEncodeChildren.trim().length()>0 ) {
				// put content of body element
				context.put("body", strEncodeChildren);
				code.append(this.getComponentBean().processTemplate(
						getEncodeChildrenTemplateName(), context));
			}
			
			strEncodeEnd = matcher.group(3);
			if(strEncodeEnd.trim().length()>0){
				// All  code after body put to encodeEnd method.
				context.put("body", strEncodeEnd);
				code.append(this.getComponentBean().processTemplate(
						getEncodeEndTemplateName(), context));
			}
		} else {
			// All collected code put to encodeEnd method.
			context.put("body", methodCode.toString());
			code.append(this.getComponentBean().processTemplate(
					getEncodeEndTemplateName(), context));
		}
		}
		if(declarationsCode.length()>0){
			code.append(declarationsCode);
		}
		String endElement = this.getEndElement();
		if (endElement != null) {
			code.append(endElement);
			code.append("\n");
		}
		return code.toString();
	}

	/**
	 * @return
	 */
	protected String getEncodeBeginTemplateName() {
		return ENCODE_BEGIN_TEMPLATE;
	}

	/**
	 * @return
	 */
	protected String getEncodeEndTemplateName() {
		return ENCODE_END_TEMPLATE;
	}
	/**
	 * @return
	 */
	protected String getEncodeChildrenTemplateName() {
		return ENCODE_CHILDREN_TEMPLATE;
	}
	/**
	 * @return
	 */
	protected String getTemplateName() {
		return TEMPLATE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.templatecompiler.elements.TemplateElement#getEndElement()
	 */
	public String getEndElement() throws CompilationException {
		// TODO Auto-generated method stub
		return "\n}";
	}

}
