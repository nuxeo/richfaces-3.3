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
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;

import javax.faces.context.FacesContext;

import org.ajax4jsf.resource.ResourceContext;


public class GridSeparatorImage extends ToolBarSeparatorImage {
	
	protected Dimension getDimensions(ResourceContext resourceContext) {
		return calculateDimension((SeparatorData) restoreData(resourceContext));
	}
	public Dimension getDimensions(FacesContext facesContext, Object data) {
		return calculateDimension((SeparatorData) getDataToStore(facesContext, data));
	}
	
	protected void paint(ResourceContext context, Graphics2D g2d) {
		SeparatorData separatorData = (SeparatorData) restoreData(context);
		Dimension dimensions = calculateDimension(separatorData);
		
		BufferedImage texture = new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB);
		Graphics2D txG2d = texture.createGraphics();
		txG2d.setColor(new Color(separatorData.getHeaderBackgroundColor()));
		txG2d.fillRect(0, 0, 2, 2);
		txG2d.setColor(new Color(255, 255, 255, 150));
		txG2d.fillRect(0, 0, 1, 1);
		txG2d.dispose();
		g2d.setPaint(new TexturePaint(texture, new Rectangle(1, 1, 3, 3)));
		g2d.fillRect(0, 0, dimensions.width, dimensions.height);
	}
	private Dimension calculateDimension(SeparatorData separatorData){
		int h = (int)(separatorData.getSeparatorHeight() * 0.8);
		h = h - h%3;
		int w = 9;
		return new Dimension(w, h);
	}

}
