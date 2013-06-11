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
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.faces.context.FacesContext;

import org.ajax4jsf.resource.ResourceContext;

public class TreeLineExpandedImage extends TreeImageBase {
	private static final Dimension dimensions = new Dimension(32, 512);

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
		if (dataToStore.getTrimColor()!=null) {
			Color trimColor = new Color(dataToStore.getTrimColor().intValue());
			g2d.setColor(trimColor);
			g2d.drawLine(0, 256, 16, 256);
			g2d.drawLine(16, 256, 16, 511);
		}
	}
}
