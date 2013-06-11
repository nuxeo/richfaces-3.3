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
package org.richfaces.renderkit.html.iconimages;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Date;

import javax.faces.context.FacesContext;

import org.ajax4jsf.resource.GifRenderer;
import org.ajax4jsf.resource.InternetResourceBuilder;
import org.ajax4jsf.resource.Java2Dresource;
import org.ajax4jsf.resource.ResourceContext;
import org.ajax4jsf.util.HtmlColor;
import org.ajax4jsf.util.Zipper;
import org.richfaces.skin.Skin;
import org.richfaces.skin.SkinFactory;

public class ScrollableDataTableIconSplit extends Java2Dresource {

	public ScrollableDataTableIconSplit() {
		setRenderer(new GifRenderer());
		setLastModified(new Date(InternetResourceBuilder.getInstance().getStartTime()));
	}
	
	public Dimension calculateDimensions() {
		return new Dimension(2, 13);
	}
	
	protected void paint(ResourceContext context, Graphics2D g2d) {
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		
		Object [] data = (Object[]) restoreData(context);
		Color col1 = (Color)data[0];
		Color col2 = (Color)data[1];
		
		g2d.setColor(col2);
		g2d.drawLine(0, 0, 0, 12);
		g2d.setColor(col1);
		g2d.drawLine(1, 0, 1, 12);
	}
	
	public Dimension getDimensions(FacesContext facesContext, Object data) {
		return calculateDimensions();
	}
	protected Dimension getDimensions(ResourceContext resourceContext) {
		return calculateDimensions();
	}
	
	protected Object deserializeData(byte[] objectArray) {
		if (objectArray == null) {
			return null;
		}
		
		Object [] stored = new Object[2];
		stored[0] = new Color(Zipper.unzip(objectArray, 0));
		stored[1] = new Color(Zipper.unzip(objectArray, 3));
		
		return stored;
	}
	
	protected Object getDataToStore(FacesContext context, Object data) {
		Skin skin = SkinFactory.getInstance().getSkin(context);
		Skin defaultSkin = SkinFactory.getInstance().getDefaultSkin(context);
		
		Color col = null;
		
		String skinParameter = "headerTextColor";
		String headerTextColor = (String) skin.getParameter(context, skinParameter);
		if (null == headerTextColor || "".equals(headerTextColor))
			headerTextColor = (String) defaultSkin.getParameter(context, skinParameter);
		
		if (headerTextColor == null) {
			return null;
		}
		
		col = HtmlColor.decode(headerTextColor);
		
		byte[] ret = new byte[6];
		Zipper.zip(ret, col.getRGB(), 0);
		
		skinParameter = "headerBackgroundColor";
		String headerBackgroundColor = (String) skin.getParameter(context, skinParameter);
		if (null == headerBackgroundColor || "".equals(headerBackgroundColor))
			headerBackgroundColor = (String) defaultSkin.getParameter(context, skinParameter);
		
		if (headerBackgroundColor == null) {
			return null;
		}
		
		col = HtmlColor.decode(headerBackgroundColor);
		
		Zipper.zip(ret, col.getRGB(), 3);
		
		return ret;
	}
	
}
