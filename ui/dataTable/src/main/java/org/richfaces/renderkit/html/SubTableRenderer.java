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

package org.richfaces.renderkit.html;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.renderkit.RendererUtils.HTML;
import org.richfaces.component.UIDataTable;
import org.richfaces.component.UISubTable;
import org.richfaces.renderkit.AbstractTableRenderer;

/**
 * @author shura
 * 
 */
public class SubTableRenderer extends AbstractTableRenderer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.renderkit.RendererBase#doEncodeBegin(javax.faces.context.ResponseWriter,
	 *      javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	protected void doEncodeBegin(ResponseWriter writer, FacesContext context,
			UIComponent component) throws IOException {
		encodeHeaderRow(writer, context, component,"header");
	}

	/**
	 * Encode one row with header/footer facets from columns.
	 * @param writer
	 * @param context
	 * @param component
	 * @param facetName
	 * @throws IOException
	 */
	private void encodeHeaderRow(ResponseWriter writer, FacesContext context, UIComponent component,String facetName) throws IOException {
		UIDataTable dataTable = (UIDataTable) component;
		Iterator<UIComponent> columns = dataTable.columns();//columnFacets(dataTable,facetName);
		int colCount = getColumnsCount(dataTable);
		String headerClass = (String) component.getAttributes().get(
				facetName+"Class");
		if (isColumnFacetPresent(dataTable, facetName)) {
			writer.startElement(HTML.TR_ELEMENT, dataTable);
			encodeStyleClass(writer, null, "dr-subtable-"+facetName+" rich-subtable-"+facetName, null, headerClass);	
			encodeHeaderFacets(context, writer, columns, "dr-subtable-"+facetName+"cell rich-subtable-"+facetName+"cell", headerClass,
					facetName, "td",colCount);
			writer.endElement(HTML.TR_ELEMENT);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.renderkit.RendererBase#doEncodeEnd(javax.faces.context.ResponseWriter,
	 *      javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	protected void doEncodeEnd(ResponseWriter writer, FacesContext context,
			UIComponent component) throws IOException {
		encodeHeaderRow(writer, context, component,"footer");
	}

	/**
	 * @return
	 */
	protected String getRowSkinClass() {
		return "dr-subtable-row rich-subtable-row";
	}
	/**
	 * @return
	 */
	protected String getFirstRowSkinClass() {
		return "dr-subtable-firstrow rich-subtable-firstrow";
	}

	/* (non-Javadoc)
	 * @see org.richfaces.renderkit.AbstractTableRenderer#getCellSkinClass()
	 */
	protected String getCellSkinClass() {
		return "dr-subtable-cell rich-subtable-cell";
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.renderkit.RendererBase#getComponentClass()
	 */
	protected Class<? extends UIComponent> getComponentClass() {
		// TODO Auto-generated method stub
		return UISubTable.class;
	}

	@Override
	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
	    	encodeRows(context, (UIDataTable) component);
	}
}
