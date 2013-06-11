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
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import javax.faces.context.FacesContext;

import org.ajax4jsf.resource.ResourceContext;


/**
 * @author Maksim Kaszynski
 *
 */
public class DotSeparatorImage extends ToolBarSeparatorImage {
	
	private static final Dimension dimensions = new Dimension(9, 9);
	
	protected Dimension getDimensions(ResourceContext resourceContext) {
		return dimensions;
	}
	public Dimension getDimensions(FacesContext facesContext, Object data) {
		return dimensions;
	}

	protected void paint(ResourceContext context, Graphics2D g2d) {
		SeparatorData separatorData = (SeparatorData) restoreData(context);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		Color gradientColorStart = Color.WHITE;
		Color gradientColorEnd = new Color(separatorData.getHeaderBackgroundColor());
		Ellipse2D inSquare = new Ellipse2D.Double(2,2,dimensions.getWidth()-4,dimensions.getHeight()-4);
		GradientPaint paint = new GradientPaint((float)3,(float)3,gradientColorStart,(float)dimensions.getWidth()-2,(float)dimensions.getHeight()-2,gradientColorEnd);
		g2d.setPaint(paint);
		g2d.fill(inSquare);

		Ellipse2D outSquare = new Ellipse2D.Double(0, 0, dimensions.getWidth(), dimensions.getHeight() );
		Ellipse2D midSquare = new Ellipse2D.Double(1,1,dimensions.getWidth()-2,dimensions.getHeight()-2);

		g2d.setColor(new Color(separatorData.getHeaderBackgroundColor()));
		Area area2 = new Area(outSquare);
		area2.subtract(new Area(midSquare));
		g2d.fill(area2);

		g2d.setColor(new Color(separatorData.getHeaderGradientColor()));
		Area area = new Area(midSquare);
		area.subtract(new Area(inSquare)); 
		g2d.fill(area);
	}
}
