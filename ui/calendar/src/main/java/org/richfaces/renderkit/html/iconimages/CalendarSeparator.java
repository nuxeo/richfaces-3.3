/**
 * License Agreement.
 *
 *  JBoss RichFaces 3.0 - Ajax4jsf Component Library
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
import org.ajax4jsf.util.Zipper2;
import org.richfaces.skin.Skin;
import org.richfaces.skin.SkinFactory;

/**
 * @author Siarhej Chalipau
 *
 */
public class CalendarSeparator extends Java2Dresource {
	private final static Dimension DIMENSIONS = new Dimension(1, 15);
	private final static String COLOR_SKIN_PARAM = "headerTextColor";
	private final static String DEFAULT_HTML_COLOR = "#FFFFFF";

	public CalendarSeparator() {
		setRenderer(new GifRenderer());
		setLastModified(new Date(InternetResourceBuilder.getInstance().getStartTime()));
	}
	
	@Override
	public Dimension getDimensions(FacesContext facesContext, Object data) {
		return DIMENSIONS;
	}
	
	@Override
	protected Dimension getDimensions(ResourceContext resourceContext) {
		return DIMENSIONS;
	}
	
	@Override
	protected Object getDataToStore(FacesContext context, Object data) {
		Skin skin = SkinFactory.getInstance().getSkin(context);
		Skin defaultSkin = SkinFactory.getInstance().getDefaultSkin(context);
		
		byte [] ret = new byte[3];
		Color color = null;
		Zipper2 zipper = new Zipper2(ret);
		
		String htmlColor = (String) skin.getParameter(context, COLOR_SKIN_PARAM);
		if (null == htmlColor || "".equals(htmlColor))
			htmlColor = (String) defaultSkin.getParameter(context, COLOR_SKIN_PARAM);
		
		if (htmlColor == null) {
			htmlColor = DEFAULT_HTML_COLOR;
		}
		
		color = HtmlColor.decode(htmlColor);
		
		zipper.addColor(color);

		return ret;
	}
	
	@Override
	protected void paint(ResourceContext context, Graphics2D g2d) {
		Color color = (Color)restoreData(context);
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
		g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		g2d.setColor(color);
		//TODO hans, need to be optimized
		for (int i = 0;i < DIMENSIONS.getHeight(); i += 2 ) {
			g2d.drawLine(0, i, 0, i);
		}
	}
	
	protected Object deserializeData(byte[] objectArray) {
		if (objectArray == null) {
			return null;
		}
		
		Zipper2 zipper = new Zipper2(objectArray);
		
		return zipper.nextColor();
	}
	
	public boolean isCacheable() {
		return true;
	}
}
