/**
 * License Agreement.
 *
 *  JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007  Exadel, Inc.
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

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.component.UIDataAdaptor;

/**
 * @author shura
 *
 */
public abstract class AbstractDefinitionListRenderer extends AbstractRowsRenderer {
	
	/* (non-Javadoc)
	 * @see org.richfaces.renderkit.AbstractRowsRenderer#encodeOneRow(javax.faces.context.FacesContext, org.richfaces.renderkit.AbstractRowsRenderer.TableHolder)
	 */
	public void encodeOneRow(FacesContext context, TableHolder holder) throws IOException {
		UIDataAdaptor table = holder.getTable();
		ResponseWriter writer = context.getResponseWriter();
		int currentRow = holder.getRowCounter();
		UIComponent dt = table.getFacet("term");
		if(null != dt){
			writer.startElement("dt", dt);
			String rowClass = holder.getColumnClass(currentRow);
			encodeStyleClass(writer, null, "dr-definition-term rich-definition-term", null, rowClass);
			renderChild(context, dt);
			writer.endElement("dt");			
		}
		writer.startElement("dd", table);
		getUtils().encodeId(context, table);
		String rowClass = holder.getRowClass();
		encodeStyleClass(writer, null, "dr-definition rich-definition", null, rowClass);
		encodeRowEvents(context, table);
		renderChildren(context, table);
		writer.endElement("dd");
	}
}
