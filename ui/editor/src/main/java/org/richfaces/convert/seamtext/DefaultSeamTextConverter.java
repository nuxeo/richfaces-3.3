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

/*
 * SeamTextConverter.java		Date created: 26.09.2008
 * Last modified by: $Author: alevkovsky $
 * $Revision: 11244 $	$Date: 2008-11-19 18:45:36 +0200 (Wed, 19 Nov 2008) $
 */

package org.richfaces.convert.seamtext;

import java.io.Reader;
import java.io.StringReader;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.jboss.seam.text.SeamTextLexer;
import org.jboss.seam.text.SeamTextParser;

/**
 * Seam Text Converter class. Provides converting html to seam text and vice versa.   
 * 
 * @author Alexandr Levkovsky
 * 
 */
public final class DefaultSeamTextConverter implements Converter {

	/** The converter id for this converter. */
	public static final String CONVERTER_ID = DefaultSeamTextConverter.class.getName();


	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {

		if (value == null) {
			return null;
		}
		
		try {
			return HtmlToSeamSAXParser.convertHtmlToSeamText(value);
		
		}	catch (Exception e) {
			FacesMessage message = new FacesMessage(
			        "An error occurred during conversion html to seam text", e.getMessage());
			
			throw new ConverterException(message,e);
		} 
		
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value == null) {
			return "";
		}
		try {
			Reader r = new StringReader(value.toString());
			SeamTextLexer lexer = new SeamTextLexer(r);
			SeamTextParser parser = new SeamTextParser(lexer);
			parser.startRule();
			return parser.toString();

		} catch (Exception e) {
			FacesMessage message = new FacesMessage("An error occurred during conversion seam text to html",e.getMessage());
			throw new ConverterException(message,e);
		} 
	}
}
