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
import java.util.Iterator;

import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.richfaces.component.Column;
import org.richfaces.component.UIScrollableDataTable;

/**
 * @author Anton Belevich
 *
 */
public class ColumnWalker{
	
	static int iterateOverColumns(FacesContext context, UIComponent component, ColumnVisitor visitor, 
								   ResponseWriter writer, ScrollableDataTableRendererState state) throws IOException{
		
		int columnsCount = 0;
		
		if(context == null || component == null){
			throw new NullPointerException();
		}

		if(!component.isRendered()){
			return 0;
		}

		try {
			
			if(component instanceof UIScrollableDataTable){
				for (Iterator<UIComponent> iter = component.getChildren().iterator(); iter.hasNext(); ) {
					UIComponent kid = (UIComponent) iter.next();
					if (kid.isRendered()) {
						if (kid instanceof Column || kid instanceof UIColumn){
							columnsCount += visitor.visit(context, kid, writer, state);
							state.nextCell();
						}
					}
				}
				state.setCellIndex(0);
			}

		} finally {
		}
		

		return columnsCount;
	}
}
