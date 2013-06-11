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
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.renderkit.RendererUtils.HTML;
import org.richfaces.component.Column;
import org.richfaces.component.UIColumnGroup;
import org.richfaces.renderkit.AbstractRowsRenderer;
import org.richfaces.renderkit.TableHolder;

/**
 * @author shura
 * 
 */
public class ColgroupRenderer extends AbstractRowsRenderer {

	//private static final String[] STYLE_ATTRS = { "style", "class" };
	public static final String[] EVENT_ATTRS ;

	static {
	    EVENT_ATTRS = new String[AbstractRowsRenderer.TABLE_EVENT_ATTRS.length];
	    for (int i = 0; i < AbstractRowsRenderer.TABLE_EVENT_ATTRS.length; i++) {
		EVENT_ATTRS[i]=AbstractRowsRenderer.TABLE_EVENT_ATTRS[i][0];
	    }
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.renderkit.RendererBase#doEncodeBegin(javax.faces.context.ResponseWriter,
	 *      javax.faces.context.FacesContext, javax.faces.component.UIComponent)
	 */
	protected void doEncodeBegin(ResponseWriter writer, FacesContext context,
			UIComponent colgroup) throws IOException {
		encodeRowStart(context, colgroup, writer, 0);
	}

	public void encodeChildren(FacesContext context, UIComponent component)
			throws IOException {
		UIColumnGroup colgroup = (UIColumnGroup) component;
		ResponseWriter writer = context.getResponseWriter();
		String[] classes = null;
		String columnClasses = (String) component.getAttributes().get(
				"columnClasses");
		if (null != columnClasses) {
			classes = columnClasses.split(",");
		}
		Iterator<UIComponent> iter = colgroup.columns();
		boolean first = true;
		int currentColumn = 0;
		int currentRow = 0;
		UIComponent column = null;
		Map<String, Object> requestMap = context.getExternalContext().getRequestMap();
		while (iter.hasNext()) {
			column = (UIComponent) iter.next();
			if (column instanceof Column) {
				boolean breakBefore = ((Column) column).isBreakBefore();
				if (breakBefore && !first) {
					// close current row
					writer.endElement(HTML.TR_ELEMENT);
					// reset columns counter.
					currentColumn = 0;
					currentRow++;
					// Start new row, expect a case of the detail table, wich
					// will be insert own row.
					encodeRowStart(context, colgroup, writer, currentRow);
				}
				String styleClass = null;
				if (null != classes) {
					styleClass = classes[currentColumn % classes.length];
				}
				encodeCellChildren(context, column, null, null, null,
						(String) requestMap
								.get(AbstractRowsRenderer.SKIN_CELL_CLASS_KEY),
						styleClass);
				// renderChild(context, column);
			} else {
				// UIColumn don't have own renderer
				if (column.isRendered()) {
					writer.startElement(HTML.td_ELEM, column);
					getUtils().encodeId(context, column);
					String styleClass;
					if (null != classes) {
						styleClass = classes[currentColumn % classes.length];
					} else {
						styleClass = (String) column.getAttributes().get(
								"styleClass");
					}
					encodeStyleClass(context.getResponseWriter(), requestMap
							.get(AbstractRowsRenderer.SKIN_CELL_CLASS_KEY),
							"dr-table-cell rich-table-cell", requestMap
									.get(AbstractRowsRenderer.CELL_CLASS_KEY),
							styleClass);
					// TODO - encode column attributes.
					renderChildren(context, column);
					writer.endElement(HTML.td_ELEM);

				}
			}
			currentColumn++;
			first = false;
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
		writer.endElement(HTML.TR_ELEMENT);
	}

	private void encodeRowStart(FacesContext context, UIComponent colspan,
			ResponseWriter writer, int currentRow) throws IOException {
		writer.startElement(HTML.TR_ELEMENT, colspan);
		String styleClass;
		String rowClasses = (String) colspan.getAttributes().get("rowClasses");
		String style = (String) colspan.getAttributes().get("style");
		if (null != rowClasses) {
			String[] classes = rowClasses.split(",");
			styleClass = classes[currentRow % classes.length];
		} else {
			styleClass = (String) colspan.getAttributes().get("styleClass");
		}
		Map<String, Object> requestMap = context.getExternalContext().getRequestMap();
		Object skinRowClass = currentRow == 0 ? requestMap
				.get(AbstractRowsRenderer.SKIN_FIRST_ROW_CLASS_KEY)
				: requestMap.get(AbstractRowsRenderer.SKIN_ROW_CLASS_KEY);
		encodeStyleClass(context.getResponseWriter(), skinRowClass,
				"dr-tablerow rich-tablerow", requestMap
						.get(AbstractRowsRenderer.ROW_CLASS_KEY), styleClass);
		encodeStyle(context.getResponseWriter(),null, null, null, style);
		getUtils().encodePassThruWithExclusionsArray(context, colspan,EVENT_ATTRS);
		// Search for enclosed DataAdaptor.
		UIComponent parent = colspan.getParent();
		// ENCODE event attributes. If component don't have own attribute, search in the parent table.
		boolean inRow = null != parent && parent.getChildren().contains(colspan);
		for (int i = 0; i < AbstractRowsRenderer.TABLE_EVENT_ATTRS.length; i++) {
		    String[] attrs = AbstractRowsRenderer.TABLE_EVENT_ATTRS[i];
		    String event = (String) colspan.getAttributes().get(attrs[0]);
		    if(null == event && inRow){
			event = (String) parent.getAttributes().get(attrs[1]);
		    }
		    if(null != event){
			writer.writeAttribute(attrs[0], event, attrs[0]);
		    }
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.render.Renderer#getRendersChildren()
	 */
	public boolean getRendersChildren() {

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.renderkit.RendererBase#getComponentClass()
	 */
	protected Class<? extends UIComponent> getComponentClass() {
		// TODO Auto-generated method stub
		return UIColumnGroup.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.richfaces.renderkit.AbstractRowsRenderer#encodeOneRow(javax.faces.context.FacesContext,
	 *      org.richfaces.renderkit.TableHolder)
	 */
	public void encodeOneRow(FacesContext context, TableHolder holder)
			throws IOException {
		// this method don't used in columnGroup component.

	}
}
