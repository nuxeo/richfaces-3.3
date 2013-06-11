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
import java.util.List;
import java.util.StringTokenizer;

import org.ajax4jsf.templatecompiler.builder.CompilationContext;
import org.ajax4jsf.templatecompiler.builder.CompilationException;
import org.ajax4jsf.templatecompiler.elements.A4JRendererElementsFactory;
import org.ajax4jsf.templatecompiler.elements.TemplateElementBase;
import org.apache.velocity.VelocityContext;
import org.w3c.dom.Node;

/**
 * @author Maksim Kaszynski Utility element for HeaderResourceRenderer-based
 *         templates Creates getXxx() methods with values set within it body
 * 
 * Example <code>
 * 	&lt;h:styles&gt;/org/ajax4jsf/resource/style.css,/org/ajax4jsf/resource/style2.css&lt;/h:styles&gt;
 * </code>
 * results in <br>
 * <code>
 *  private final InternetResource[] styles = {
 *  	getResource("/org/ajax4jsf/resource/style.css"),
 *  	getResource(/org/ajax4jsf/resource/style2.css")
 *  };
 *  
 *  protected InternetResource [] getStyles() {
 *  	return styles;
 *  }
 *  </code>
 */
public abstract class HeaderResourceElement extends TemplateElementBase {


	private static final String TEMPLATE = A4JRendererElementsFactory.TEMPLATES_TEMPLATECOMPILER_PATH+"/headerResource.vm";

	private static final String NEW = "new ";

	private String[] keys;

	private String[] resourcePaths;
	
	public HeaderResourceElement(Node element, CompilationContext componentBean)
			throws CompilationException {
		super(element, componentBean);
		String resourcesAsString = element.getTextContent();
		if (resourcesAsString != null) {
			resourcesAsString = resourcesAsString.trim();
			StringTokenizer tokenizer = new StringTokenizer(resourcesAsString,
					"\r\n\t,;");
			List paths = new ArrayList(tokenizer.countTokens());
			List keyz = new ArrayList(tokenizer.countTokens());
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken().trim();
				String path;
				
				if (token.startsWith(NEW)) {
					keyz.add(token);
					int idx = token.indexOf('(');
					if (idx != -1) {
						path = token.substring(0, idx).substring(NEW.length()).trim();
					} else {
						path = token.substring(NEW.length()).trim();
					}
				} else {
					keyz.add("getResource(\"" + token + "\")");
					path = token;
				}

				paths.add(path);
			}
			this.keys = (String[]) keyz.toArray(new String[keyz.size()]);
			this.resourcePaths = (String[]) paths.toArray(new String[paths.size()]);
		}
		componentBean
				.addToImport("org.ajax4jsf.resource.InternetResource");
		componentBean.addToDeclaration(getContent());
	}

	private String getterMethodName(String propName) {
		return "get" + Character.toUpperCase(propName.charAt(0))
				+ propName.substring(1);
	}

	protected String getGetterModifier() {
		return "protected";
	}

	protected abstract String getPropertyName();

	public String getBeginElement() {
		// TODO Auto-generated method stub
		return null;
	}

	private String getContent() throws CompilationException {
		VelocityContext context = new VelocityContext();
		String propertyName = getPropertyName();

		if (this.keys != null) {
			context.put("resourceKeys", this.keys);
		}

		context.put("propertyGetterName", getterMethodName(propertyName));
		context.put("property", propertyName);
		context.put("getterModifier", getGetterModifier());
		return this.getComponentBean().processTemplate(getTemplateName(), context);
	}

	/**
	 * @return
	 */
	protected String getTemplateName() {
		return TEMPLATE;
	}

	public String getEndElement() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isSkipBody() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public String[] getResourcePaths() {
		return resourcePaths;
	}
}
