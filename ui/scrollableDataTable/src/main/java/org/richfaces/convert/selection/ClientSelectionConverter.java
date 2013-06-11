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
package org.richfaces.convert.selection;

import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.richfaces.model.selection.ClientSelection;
import org.richfaces.model.selection.SelectionRange;

/**
 * @author Maksim Kaszynski
 *
 */
public class ClientSelectionConverter implements Converter {

	public static final String CONVERTER_ID = 
		ClientSelectionConverter.class.getName();
	
	private static final Log log = 
		LogFactory.getLog(ClientSelectionConverter.class);
	
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return getAsSelection(context, component, value == null ? "" : value);
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		if (log.isDebugEnabled()) {
			log.debug("Converting to string " + value);
		}
		
		StringBuffer buffer = new StringBuffer();
		Iterator iterator = ((ClientSelection) value).getRanges().iterator();
		
		while(iterator.hasNext()) {
			
			SelectionRange selectionRange = 
				(SelectionRange) iterator.next();
			
			buffer
				.append(selectionRange.getStartIndex())
				.append(",")
				.append(selectionRange.getEndIndex())
				.append(";");
		}
		
		return buffer.toString();
	}
	
	private ClientSelection getAsSelection(FacesContext context, UIComponent grid, String stringSelection) {
		
		if (log.isDebugEnabled()) {
			log.debug("parsing " + stringSelection);
		}
		
		final ClientSelection clientSelection = new ClientSelection();
		if(stringSelection == null || stringSelection.length() == 0) {
			return clientSelection;
		}
		
		String [] selections = stringSelection.split(";");
		int length = selections.length;
		if (selections[length-1].charAt(0) > '9') {
			clientSelection.setSelectionFlag(selections[length-1]);
			length--;
		}
		clientSelection.setActiveRowIndex(Integer.parseInt(selections[length-1]));
		length--;
		for (int i = 0; i < length; i++) {
			
			String range = selections[i];
			
			if (range.length() != 0) {
				String [] rng = range.split(",");
				
				try {
					int fi = Integer.parseInt(rng[0]);
					int il = Integer.parseInt(rng[1]);
					
					if (log.isDebugEnabled()) {
						log.debug("Parsed range " + fi + " " + il);
					}
					
					clientSelection.addRange(new SelectionRange(fi, il));
					
				} catch (NumberFormatException e) {
					throw new ConverterException(e);
				}
			}
		}

		return clientSelection;
	}

}
