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
package org.ajax4jsf.renderkit;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.Messages;
import org.ajax4jsf.renderkit.RendererUtils.HTML;
import org.richfaces.ui.component.DummyHighLight;
import org.richfaces.ui.component.Highlight;
import org.richfaces.ui.component.HighlightImpl;
import org.richfaces.ui.component.UIInsert;

/**
 * @author asmirnov
 * 
 */
public abstract class AbstractInsertRenderer extends
		HeaderResourcesRendererBase {

	private static final Object ERROR_MESSAGE_CLASS = "dr-insert-error";
	
	

	public void renderContent(FacesContext context, UIInsert component)
			throws IOException {
	    String src = component.getSrc();
		boolean isSrcAvailable = (src != null);
	    String content = component.getContent();
		boolean isContentAvailable = (content != null);
	    if (isSrcAvailable && isContentAvailable) {
		throw new FacesException(UIInsert.ILLEGAL_ATTRIBUTE_VALUE_MESSAGE);
	    }

	    if (isSrcAvailable || isContentAvailable) {
		ExternalContext externalContext = context.getExternalContext();
		InputStream inputStream = null;

		if (isSrcAvailable) {
		    inputStream = externalContext.getResourceAsStream(src);
		} else if (isContentAvailable) {
		    inputStream = new ByteArrayInputStream(content.getBytes());
		}
		if (null != inputStream) {
		    renderStream(context, component, inputStream);
		} else {
		    String errorContent = component.getErrorContent();
		    if ((null != errorContent)
			&& (null != (inputStream = externalContext.getResourceAsStream(errorContent)))) {
		    // Render default content, if src not found.
		    renderStream(context, component, inputStream);
		} else {
		    // Render error message for a not found resource.
		    renderErrorMessage(context, component, "UI_INSERT_RESOURCE_NOT_FOUND");
		}
		}
	    } else {
		throw new FacesException("Attribute 'scr' for a component <rich:insert> " + component.getClientId(context)
			+ " must be set");
	    }

	}

	/**
	 * @param context
	 * @param component
	 * @param message
	 *                TODO
	 * @throws IOException
	 */
	private void renderErrorMessage(FacesContext context, UIInsert component, String message)
			throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		writer.startElement(HTML.SPAN_ELEM, component);
		writer.writeAttribute(HTML.class_ATTRIBUTE,
				ERROR_MESSAGE_CLASS, null);
		writer.write(Messages.getMessage(
				message, new Object[] {
						component.getClientId(context),
						component.getSrc() }));
		writer.endElement(HTML.SPAN_ELEM);
	}

	/**
	 * @param context
	 * @param component
	 * @param inputStream
	 * @throws UnsupportedEncodingException
	 * @throws FacesException
	 * @throws IOException
	 */
	private void renderStream(FacesContext context, UIInsert component,
			InputStream inputStream) throws UnsupportedEncodingException,
			FacesException, IOException {
		ResponseWriter writer = context.getResponseWriter();
		String encoding = component.getEncoding();
		if (null == component.getHighlight()) {
			InputStreamReader in;
			if (null != encoding) {
				in = new InputStreamReader(inputStream, encoding);
			} else {
				in = new InputStreamReader(inputStream);
			}
			char[] temp = new char[1024];
			try {
				int bytes;
				while ((bytes = in.read(temp)) > 0) {
					writer.write(temp, 0, bytes);
				}
			} catch (IOException e) {
				throw new FacesException(e);
			} finally {
				in.close();
			}
		} else {
			Highlight highlighter;
			try {
				highlighter = new HighlightImpl(component.getHighlight());

			} catch (NoClassDefFoundError e) {
				renderErrorMessage(context, component, "HIGHLIGHT_LIBRARY_NOT_FOUND");
				highlighter = new DummyHighLight();
			}
			try {
				highlighter.highlight(component.getSrc(), inputStream, writer,
						encoding);
			} catch (IOException e) {
				throw new FacesException(e);
			} finally {
				inputStream.close();
			}
		}
	}

}
