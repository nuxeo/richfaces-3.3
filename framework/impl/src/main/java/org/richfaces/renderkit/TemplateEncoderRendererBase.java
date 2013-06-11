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
package org.richfaces.renderkit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;

import org.ajax4jsf.javascript.JSReference;
import org.ajax4jsf.javascript.ScriptUtils;
import org.ajax4jsf.renderkit.HeaderResourcesRendererBase;
import org.ajax4jsf.resource.util.URLToStreamHelper;
import org.ajax4jsf.webapp.tidy.TidyParser;
import org.ajax4jsf.webapp.tidy.TidyXMLFilter;
import org.richfaces.component.TemplateComponent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ContentHandler;

/**
 * @author Nick Belaevski - mailto:nbelaevski@exadel.com
 * created 22.06.2007
 *
 */
public abstract class TemplateEncoderRendererBase extends HeaderResourcesRendererBase {
	private static TransformerFactory transformerFactory;

	private static TransformerFactory getTransformerFactory() {
		synchronized (TemplateEncoderRendererBase.class) {
			if (transformerFactory == null) {
				transformerFactory = TransformerFactory.newInstance();
			}
		}

		return transformerFactory;
	}
	
	public final boolean getRendersChildren() {
		return true;
	}

	public void writeScriptBody(Writer writer, String string) throws IOException {
		if (string != null) {
			Properties tidyProperties = new Properties();
			InputStream propertiesStream = null;
			try {
				propertiesStream = URLToStreamHelper.urlToStreamSafe(
						TidyXMLFilter.class.getResource("tidy.properties"));
				tidyProperties.load(propertiesStream);
			} finally {
				if (propertiesStream != null) {
					propertiesStream.close();
				}
			}
	
			TidyParser tidyParser = new TidyParser(tidyProperties);
			Document parsedHtml = tidyParser.parseHtmlByTidy(new StringReader(string), null);
	
			Element documentElement = null;
			
			if (parsedHtml != null) {
				documentElement = parsedHtml.getDocumentElement();
			}
			
			if (documentElement != null) {
				writer.write("[");
				
				NodeList nodeList = documentElement.getChildNodes();
				Node bodyNode = nodeList.item(nodeList.getLength() - 1);
				NodeList bodyChildren = bodyNode.getChildNodes();
				int bodyChildrenLength = bodyChildren.getLength();
	
				try {
					Transformer transformer;
	
					TransformerFactory factory = getTransformerFactory();
					
					synchronized (factory) {
						transformer = factory.newTransformer();
					}
	
					transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
					transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	
					ContentHandler contentHandler = createContentHandler(writer);
					Result result = new SAXResult(contentHandler);
	
					for (int i = 0; i < bodyChildrenLength; i++) {
						if (i != 0) {
							writer.write(", ");
						}
						transformer.transform(new DOMSource(bodyChildren.item(i)), result);
					}
				} catch (TransformerException e) {
					throw new IOException(e.getMessage());
				}
				
				writer.write("]");
			
			} else {
				writer.write(ScriptUtils.toScript(JSReference.NULL));
			}
		} else {
			writer.write(ScriptUtils.toScript(JSReference.NULL));
		}
	}
	
	protected void writeScriptBody(FacesContext context, UIComponent component, boolean children)
	throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		StringWriter dumpingWriter = new StringWriter();
		ResponseWriter clonedWriter = writer.cloneWithWriter(dumpingWriter);
		context.setResponseWriter(clonedWriter);
		
		TemplateComponent templateComponent = null;
		if (component instanceof TemplateComponent) {
			templateComponent = (TemplateComponent) component;
		}
		
		try {
			if (templateComponent != null) {
				templateComponent.startTemplateEncode();
			}
			
			if (children) {
				this.renderChildren(context, component);
			} else {
				this.renderChild(context, component);
			}
		} finally {
			if (templateComponent != null) {
				templateComponent.endTemplateEncode();
			}

			clonedWriter.flush();
			context.setResponseWriter(writer);
		}

		writeScriptBody(writer, dumpingWriter.toString());
	}
	
	public void encodeChildren(FacesContext context, UIComponent component)
	throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		writer.startElement("script", component);
		writer.write("var evaluator = ");
		writeScriptBody(context, component, true);
		writer.write(";\n new Insertion.Top($('" + component.getClientId(context) + "'), evaluator.invoke('getContent', window).join(''));");
		writer.endElement("script");
	}
	
	
	protected ContentHandler createContentHandler(Writer writer) {
		return new MacroDefinitionJSContentHandler(writer, "Richfaces.evalMacro(\"", "\", context)");
	}

	public static void main(String[] args) throws Exception {
		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String s;
		while ((s = reader.readLine()) != null) {
			buffer.append(s);
		}
		
		new TemplateEncoderRendererBase(){

			protected Class<? extends UIComponent> getComponentClass() {
				return null;
			}
			
		}.writeScriptBody(new PrintWriter(System.out), buffer.toString());
	}
}
