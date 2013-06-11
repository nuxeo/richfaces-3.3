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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
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
 * @author Maksim Kaszynski
 *
 */
public class CalendarIcon extends Java2Dresource {

	private static Dimension dimension = new Dimension(20, 20);
	
	/**
	 * 
	 */
	public CalendarIcon() {
		setRenderer(new GifRenderer());
		setLastModified(new Date(InternetResourceBuilder.getInstance().getStartTime()));

	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.Java2Dresource#getDimensions(javax.faces.context.FacesContext, java.lang.Object)
	 */
	public Dimension getDimensions(FacesContext facesContext, Object data) {
		// TODO Auto-generated method stub
		return dimension;
	}
	
	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.Java2Dresource#getDimensions(org.ajax4jsf.resource.ResourceContext)
	 */
	protected Dimension getDimensions(ResourceContext resourceContext) {
		// TODO Auto-generated method stub
		return dimension;
	}
	
	protected Object getDataToStore(FacesContext context, Object data) {
		Skin skin = SkinFactory.getInstance().getSkin(context);
		Skin defaultSkin = SkinFactory.getInstance().getDefaultSkin(context);
		
		String skinParameter = "headerBackgroundColor";
		String headerTextColor = (String) skin.getParameter(context, skinParameter);
		if (null == headerTextColor || "".equals(headerTextColor))
			headerTextColor = (String) defaultSkin.getParameter(context, skinParameter);
		
		if (headerTextColor == null) {
			return null;
		}
				
		
		skinParameter = "selectControlColor";
		String headerBackgroundColor = (String) skin.getParameter(context, skinParameter);
		if (null == headerBackgroundColor || "".equals(headerBackgroundColor))
			headerBackgroundColor = (String) defaultSkin.getParameter(context, skinParameter);
		
		if(headerBackgroundColor == null){
			return null;
		}
				
		byte[] ret = new byte[6];
		Zipper2 zipper2 = new Zipper2(ret).addColor(HtmlColor.decode(headerTextColor).getRGB());
		zipper2.addColor(HtmlColor.decode(headerBackgroundColor).getRGB());

		return ret;
	}
	
	protected Object deserializeData(byte[] objectArray) {
		if (objectArray == null) {
			return null;
		}
		
		Object[] colors = new Object[2];
		Zipper2 z = new Zipper2(objectArray);
		colors[0] = z.nextColor();
		colors[1] = z.nextColor();
		
		return colors;
	}
	
	public void paint(ResourceContext context,  Graphics2D graphics) {
		
		Object[] stored = (Object[]) restoreData(context);
		if (stored != null) {
			Dimension dim = getDimensions(context);
			BufferedImage image = paintImage(stored);
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			graphics.drawImage(image, 0, 0, dim.width, dim.height, null);
			
		}
		
	}

	protected BufferedImage paintImage(Object [] colors) {

		BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g2d = image.createGraphics();
		
		Color borderColor = (Color) colors[0];//new Color(((Integer) stored[0]).intValue());
		Color activeColor = (Color) colors[1];//new Color(((Integer) stored[1]).intValue());

		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		
		g2d.setStroke(new BasicStroke(1));
		
		int w = 16;
		int h = 16;
		
		
		//Draw Border
		g2d.setColor(borderColor);
		Rectangle2D border = new Rectangle2D.Double(1, 1, w - 3, h - 3);
		RoundRectangle2D round = new RoundRectangle2D.Double(1, 1, w- 3, h - 3, 2, 2);
		g2d.draw(round);
		
		Color lightBlue = new Color(216, 226, 240);
		Paint gradient1 = new GradientPaint(w-4, h-4, lightBlue, 2, 2, Color.white);
		g2d.setPaint(gradient1);
		border = new Rectangle2D.Double(2, 2, w - 4, h - 4);
		g2d.fill(border);

		border = new Rectangle2D.Double(3, 3, w - 6, h - 6);
		gradient1 = new GradientPaint(3, 3, lightBlue , w - 6, h - 6, borderColor);
		g2d.setPaint(gradient1);
		g2d.fill(border);

		g2d.setColor(Color.white);
		g2d.drawLine(3, 6, 3, 11);
		g2d.drawLine(5, 6, 5, 11);
		g2d.drawLine(7, 6, 7, 11);
		g2d.drawLine(9, 6, 9, 11);
		g2d.drawLine(11, 6, 11, 11);
		
		
		
		
		
		//Draw orange rectangle
		border = new Rectangle2D.Double(3, 3, 10, 3);
		g2d.setColor(Color.white);
		g2d.fill(border);
		
		Color c = new Color(activeColor.getRed(), activeColor.getGreen(), activeColor.getBlue(), 100);
		Color c2 = new Color(activeColor.getRed(), activeColor.getGreen(), activeColor.getBlue(), 200);
		
		gradient1 = new GradientPaint(12, 4, activeColor, 4, 7, c2);
		g2d.setPaint(gradient1);
		g2d.fill(border);
		//g2d.setColor(activeColor);
		
		c = new Color(activeColor.getRed(), activeColor.getGreen(), activeColor.getBlue(), 150);
		c2 = new Color(activeColor.getRed(), activeColor.getGreen(), activeColor.getBlue(), 200);
		border = new Rectangle2D.Double(4, 4, 8, 1);
		
		g2d.setColor(Color.white);
		g2d.fill(border);
		
		gradient1 = new GradientPaint(4, 4, c, 10, 4, c2);
		//g2d.setPaint(gradient1);
		g2d.setColor(c);
		g2d.fill(border);
		
		g2d.dispose();

		return image;
	}
}
