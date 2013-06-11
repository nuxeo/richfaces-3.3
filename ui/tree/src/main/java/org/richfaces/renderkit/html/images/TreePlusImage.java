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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import javax.faces.context.FacesContext;

import org.ajax4jsf.resource.ResourceContext;

public class TreePlusImage extends TreeImageBase {
	private static final Dimension dimensions = new Dimension(16, 16);

	public Dimension getDimensions(FacesContext facesContext, Object data) {
		return dimensions;
	}
	protected Dimension getDimensions(ResourceContext resourceContext) {
		return dimensions;
	}
	
	protected void paint(ResourceContext resourceContext, Graphics2D g2d) {
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		TreeImageData dataToStore  = getTreeImageData(resourceContext);
		if (dataToStore.getControlColor()!=null && dataToStore.getGeneralColor()!=null && dataToStore.getTrimColor()!=null) {
			Color trimColor = new Color(dataToStore.getTrimColor().intValue());
			Color controlColor = new Color(dataToStore.getControlColor().intValue());
			g2d.setColor(trimColor);
			g2d.drawRect(4, 4, 8, 8);

			Rectangle2D rect = new Rectangle2D.Float(5,5,7,7);
			GradientPaint gragient = new GradientPaint(5, 5, controlColor, 12, 12, trimColor);
			g2d.setPaint(gragient);
			g2d.fill(rect);
			
			Color generalColor = new Color(dataToStore.getGeneralColor().intValue());
			g2d.setColor(generalColor);
			g2d.drawLine(7,6,7,10);
			g2d.drawLine(8,7,8,9);
			g2d.drawLine(9,8,9,8);
		}
	}


}
