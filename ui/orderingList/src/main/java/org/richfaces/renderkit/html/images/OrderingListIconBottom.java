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

package org.richfaces.renderkit.html.images;

import static org.richfaces.renderkit.html.images.OrderingListIconConstants.SELECT_LIST_BORDER_COLOR;
import static org.richfaces.renderkit.html.images.OrderingListIconConstants.SELECT_LIST_ICON_COLOR;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.faces.context.FacesContext;

import org.ajax4jsf.resource.ResourceContext;

/**
 * @author Siarhej Chalipau
 *
 */
public class OrderingListIconBottom extends TriangleIconBase {

	protected void paintImage(ResourceContext context, Graphics2D g2d,
			Color textColor, Color borderColor) {
		
		g2d.translate(0, -3);

		g2d.setColor(textColor);
		g2d.translate(7, 5);
		paintBaseTriangle(g2d);
		g2d.translate(-7, -5);
		
		g2d.setColor(borderColor);
		g2d.drawLine(4, 5, 10, 5);
		g2d.drawLine(11, 6, 8, 9);
		g2d.drawLine(6, 9, 3, 6);
		
		g2d.translate(0, 4);
		g2d.setColor(textColor);
		g2d.translate(7, 5);
		paintBaseTriangle(g2d);
		g2d.translate(-7, -5);
		
		g2d.setColor(borderColor);
		g2d.drawLine(4, 5, 6, 5);
		g2d.drawLine(8, 5, 10, 5);
		g2d.drawLine(11, 6, 8, 9);
		g2d.drawLine(6, 9, 3, 6);

		g2d.translate(0, 4);

		g2d.setColor(borderColor);
		g2d.drawLine(4, 5, 6, 5);
		g2d.drawLine(8, 5, 10, 5);
		
		g2d.drawLine(11, 6, 10, 7);
		g2d.drawLine(4, 7, 3, 6);
		g2d.drawLine(4, 7, 10, 7);

		g2d.setColor(textColor);
		g2d.drawLine(4, 6, 10, 6);
	}

	protected Object getDataToStore(FacesContext context, Object data) {
		return super.getDataToStore(context, SELECT_LIST_ICON_COLOR, ICON_COLOR, 
				SELECT_LIST_BORDER_COLOR, BORDER_COLOR);
	}
}
