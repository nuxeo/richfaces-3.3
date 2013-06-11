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

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import org.ajax4jsf.templatecompiler.builder.CompilationContext;
import org.ajax4jsf.templatecompiler.builder.CompilationException;
import org.ajax4jsf.templatecompiler.elements.html.CDATAElement;
import org.ajax4jsf.templatecompiler.elements.html.CommentElement;
import org.ajax4jsf.templatecompiler.elements.html.PIElement;
import org.ajax4jsf.templatecompiler.elements.html.TextElement;
import org.ajax4jsf.templatecompiler.elements.vcp.AjaxRenderedAreaElement;
import org.ajax4jsf.templatecompiler.elements.vcp.HeaderScriptsElement;
import org.ajax4jsf.templatecompiler.elements.vcp.HeaderStylesElement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Node;

/**
 * Tag processors factory.
 * 
 * @author ayukhovich@exadel.com (latest modification by $Author:
 *         maksimkaszynski $)
 * @version $Revision: 1.1.2.2 $ $Date: 2007/02/26 20:48:44 $
 * 
 */
public class A4JRendererElementsFactory implements ElementsFactory {

	private static final Log log = LogFactory.getLog(A4JRendererElementsFactory.class);
	
	public static final String TEMPLATES_PATH = "META-INF/templates";

	public static final String TEMPLATES_TEMPLATECOMPILER_PATH = TEMPLATES_PATH+"/templatecompiler";

	private final static String DEFAULT_CLASS_ELEMENT_PROCESSOR = "org.ajax4jsf.templatecompiler.elements.html.HTMLElement";

	private final static Class[] paramClasses = new Class[] { Node.class,
			CompilationContext.class };

	private final static HashMap<String, String> mapClasses = new HashMap<String, String>();

	static {
		mapClasses
				.put("c:set",
						"org.ajax4jsf.templatecompiler.elements.std.SetTemplateElement");
		mapClasses
				.put("c:object",
						"org.ajax4jsf.templatecompiler.elements.std.ObjectTemplateElement");
		mapClasses.put("c:if",
				"org.ajax4jsf.templatecompiler.elements.std.IFTemplateElement");
		mapClasses
				.put("c:forEach",
						"org.ajax4jsf.templatecompiler.elements.std.ForEachTemplateElement");
		mapClasses
				.put("f:clientid",
						"org.ajax4jsf.templatecompiler.elements.vcp.FClientIDTemplateElement");
		mapClasses
				.put("f:clientId",
						"org.ajax4jsf.templatecompiler.elements.vcp.FClientIDTemplateElement");
		mapClasses.put("f:insertComponent",
				"org.ajax4jsf.templatecompiler.elements.vcp.InsertComponent");

		mapClasses
				.put("f:call",
						"org.ajax4jsf.templatecompiler.elements.vcp.FCallTemplateElement");
		mapClasses
				.put("f:resource",
						"org.ajax4jsf.templatecompiler.elements.vcp.FResourceTemplateElement");

		mapClasses
				.put("u:insertFacet",
						"org.ajax4jsf.templatecompiler.elements.vcp.UInsertFacetTemplateElement");

		mapClasses
				.put("vcp:body",
						"org.ajax4jsf.templatecompiler.elements.vcp.VcpBodyTemplateElement");
		mapClasses.put("vcp:mock", "");

		mapClasses.put("jsp:scriptlet",
				"org.ajax4jsf.templatecompiler.elements.jsp.Scriptlet");
		mapClasses.put("jsp:declaration",
				"org.ajax4jsf.templatecompiler.elements.jsp.Declaration");
		mapClasses.put("jsp:directive.page",
				"org.ajax4jsf.templatecompiler.elements.jsp.DirectivePage");
		mapClasses.put("jsp:expression",
				"org.ajax4jsf.templatecompiler.elements.jsp.Expression");
		mapClasses.put("h:styles", HeaderStylesElement.class.getName());
		mapClasses.put("h:scripts", HeaderScriptsElement.class.getName());
		mapClasses.put("f:template", RootElement.class.getName());
		mapClasses.put("f:root", RootElement.class.getName());
		mapClasses.put("jsp:root", RootElement.class.getName());
		mapClasses.put("ajax:update", AjaxRenderedAreaElement.class.getName());
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.templatecompiler.elements.ElementsFactory#getProcessor(org.w3c.dom.Node, org.ajax4jsf.templatecompiler.builder.CompilationContext)
	 */
	public  TemplateElement getProcessor(final Node nodeElement,
			final CompilationContext componentBean) throws CompilationException {
		TemplateElement returnValue = null;

		short nodeType = nodeElement.getNodeType();
		if (Node.CDATA_SECTION_NODE == nodeType) {
			returnValue =new CDATAElement(nodeElement, componentBean);
		} else if (Node.TEXT_NODE == nodeType) {
			returnValue =new TextElement(nodeElement, componentBean);
		} else if (Node.COMMENT_NODE == nodeType) {
			returnValue =new CommentElement(nodeElement, componentBean);
		} else if (Node.PROCESSING_INSTRUCTION_NODE == nodeType) {
			returnValue =new PIElement(nodeElement, componentBean);
		} else if (Node.ELEMENT_NODE == nodeType) {
			String className = (String) mapClasses.get(nodeElement.getNodeName());

		if (className == null) {
			className = DEFAULT_CLASS_ELEMENT_PROCESSOR;
		}

		if (!className.equals("")) {
			Class class1;
			try {
				log.debug("loading class: " + className);

				class1 = Class.forName(className);
				Object[] objects = new Object[2];
				objects[0] = nodeElement;
				objects[1] = componentBean;

				returnValue = (TemplateElement) class1.getConstructor(
						paramClasses).newInstance(objects);
			} catch (InstantiationException e) {
				throw new CompilationException("InstantiationException: "
						+ e.getLocalizedMessage(), e);
			} catch (IllegalAccessException e) {
				throw new CompilationException("IllegalAccessException: "
						+ e.getLocalizedMessage(), e);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
				throw new CompilationException("InvocationTargetException: "
						+ e.getMessage(), e);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				throw new CompilationException(" error loading class: "
						+ e.getLocalizedMessage());
			}
		}
		}
		return returnValue;
	}
}
