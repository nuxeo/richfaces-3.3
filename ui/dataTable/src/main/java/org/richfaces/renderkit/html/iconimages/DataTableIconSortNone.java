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

package org.richfaces.renderkit.html.iconimages;

import static org.richfaces.renderkit.html.iconimages.DataTableIconConstants.SORT_ICON_BORDER_COLOR;
import static org.richfaces.renderkit.html.iconimages.DataTableIconConstants.SORT_ICON_COLOR;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.faces.context.FacesContext;

import org.ajax4jsf.resource.ResourceContext;
import org.richfaces.renderkit.html.images.TriangleIconBase;

public class DataTableIconSortNone extends TriangleIconBase {

	public Dimension calculateDimensions() {
		return new Dimension(13, 4);
	}
	
	protected void paintImage(ResourceContext context, Graphics2D g2d,
			Color textColor, Color borderColor) {
		g2d.translate(4, 3);
		
		g2d.setColor(textColor);
		g2d.drawLine(3, 1, 3, 1);
		g2d.drawLine(2, 2, 4, 2);
		g2d.drawLine(1, 3, 5, 3);
		
		g2d.drawLine(3, 7, 3, 7);
		g2d.drawLine(2, 6, 4, 6);
		g2d.drawLine(1, 5, 5, 5);
		
		g2d.setColor(borderColor);
		g2d.drawLine(0, 3, 3, 0);
		g2d.drawLine(3, 0, 6, 3);
		g2d.drawLine(5, 4, 1, 4);
		
		g2d.drawLine(0, 5, 3, 8);
		g2d.drawLine(3, 8, 6, 5);
	}

	protected Object getDataToStore(FacesContext context, Object data) {
		return super.getDataToStore(context, SORT_ICON_COLOR, ICON_COLOR, 
				SORT_ICON_BORDER_COLOR, BORDER_COLOR);
	}
}
