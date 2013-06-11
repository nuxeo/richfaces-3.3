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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.faces.context.FacesContext;

import org.ajax4jsf.resource.ResourceContext;


/**
 * @author Maksim Kaszynski
 *
 */
public class LineSeparatorImage extends ToolBarSeparatorImage {
	
	protected Dimension getDimensions(ResourceContext resourceContext) {
		return calculateDimension((SeparatorData) restoreData(resourceContext));
	}
	public Dimension getDimensions(FacesContext facesContext, Object data) {
		return calculateDimension((SeparatorData) getDataToStore(facesContext, data));
	}
	
	protected void paint(ResourceContext context, Graphics2D g2d) {
		SeparatorData separatorData = (SeparatorData) restoreData(context);
		Dimension dimensions = calculateDimension(separatorData);
		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(new Color(separatorData.getHeaderBackgroundColor()));
		g2d.fillRect(-1, -1, dimensions.width + 2, dimensions.height + 2);
		g2d.setColor(new Color(255, 255, 255, 150));
		g2d.drawLine(1, -1, 1, dimensions.height + 2);
	}
	private Dimension calculateDimension(SeparatorData separatorData){
		int h = separatorData.getSeparatorHeight();		
		int w = 2;
		return new Dimension(w, h);
	}
}
