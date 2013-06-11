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
package org.richfaces.renderkit.html;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * @author Anton Belevich
 *
 */
public abstract class ExtendedColumnVisitor implements ColumnVisitor {

	/* (non-Javadoc)
	 * @see org.richfaces.renderkit.html.ColumnVisitor#visit(javax.faces.context.FacesContext, org.richfaces.component.UIScrollableGridColumn, javax.faces.context.ResponseWriter, org.richfaces.renderkit.html.ScrollableDataTableRendererState)
	 */
	public int visit(FacesContext context, UIComponent column,
			ResponseWriter writer, ScrollableDataTableRendererState state) throws IOException {
		
		if(state.isFrozenColumn()){
			int i = state.getFrozenColumnCount();
			state.setFrozenColumnCount(i-1);
			if(state.isFrozenPart()){
				renderContent(context, column, writer,state);
			}
		}else if(!state.isFrozenPart() && !state.isFrozenColumn()){
			renderContent(context, column, writer,state);
		}
			
		return 1;
	};
	
	 abstract public void renderContent(FacesContext context, UIComponent column,ResponseWriter writer, ScrollableDataTableRendererState state) throws IOException;
}
