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
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Date;

import javax.faces.context.FacesContext;

import org.ajax4jsf.resource.InternetResourceBuilder;
import org.ajax4jsf.resource.Java2Dresource;
import org.ajax4jsf.resource.PngRenderer;
import org.ajax4jsf.util.HtmlColor;
import org.ajax4jsf.util.Zipper2;
import org.richfaces.skin.Skin;
import org.richfaces.skin.SkinFactory;

/**
 * Class with basic methods for drawing skinable icons for tinyMCE editor. 
 * 
 * @author Alexandr Levkovsky
 * 
 */
public abstract class EditorIcons extends Java2Dresource {

	/** Additional background color parameter name in skin */
	public final static String ADDITIONAL_BACKGROUND_COLOR = "additionalBackgroundColor";
	/** SelectControl color parameter name in skin */
	public final static String SELECT_CONTROL_COLOR = "selectControlColor";
	/** Panel border color parameter name in skin */
	public final static String PANEL_BORDER_COLOR = "panelBorderColor";
	/** General text color parameter name in skin */
	public final static String GENERAL_TEXT_COLOR = "generalTextColor";

	/** Icon sub border transparency value */
	private final int SUB_BORDER_TRANSPARENCY = 150;

	/**
	 *  Constructor
	 */
	public EditorIcons() {
		super();

		setRenderer(new PngRenderer());
		setLastModified(new Date(InternetResourceBuilder.getInstance()
				.getStartTime()));
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.InternetResourceBase#getDataToStore(javax.faces.context.FacesContext, java.lang.Object)
	 */
	protected Object getDataToStore(FacesContext context, Object data) {
		Skin skin = SkinFactory.getInstance().getSkin(context);
		Skin defaultSkin = SkinFactory.getInstance().getDefaultSkin(context);

		String additionalBackgroundColor = getSkinParameter(skin, defaultSkin,
				context, ADDITIONAL_BACKGROUND_COLOR);
		String selectControlColor = getSkinParameter(skin, defaultSkin,
				context, SELECT_CONTROL_COLOR);
		String panelBorderColor = getSkinParameter(skin, defaultSkin, context,
				PANEL_BORDER_COLOR);
		String generalTextColor = getSkinParameter(skin, defaultSkin, context,
				GENERAL_TEXT_COLOR);

		byte[] ret = new byte[12];
		Zipper2 zipper2 = new Zipper2(ret);
		zipper2.addColor(HtmlColor.decode(additionalBackgroundColor).getRGB());
		zipper2.addColor(HtmlColor.decode(selectControlColor).getRGB());
		zipper2.addColor(HtmlColor.decode(panelBorderColor).getRGB());
		zipper2.addColor(HtmlColor.decode(generalTextColor).getRGB());

		return ret;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.resource.InternetResourceBase#deserializeData(byte[])
	 */
	protected Object deserializeData(byte[] objectArray) {
		if (objectArray == null) {
			return null;
		}
		Object[] colors = new Object[5];
		Zipper2 z = new Zipper2(objectArray);
		colors[0] = z.nextColor();
		colors[1] = z.nextColor();
		colors[2] = z.nextColor();
		colors[3] = z.nextColor();

		return colors;
	}

	/**
	 * Method to get skin parameter value by parameter name
	 * 
	 * @param skin - current component skin
	 * @param defaultSkin - default richfaces skin
	 * @param context - faces context instance 
	 * @param parameterName - name of the skin parameter
	 * @return string value of parameter
	 */
	private String getSkinParameter(Skin skin, Skin defaultSkin, FacesContext context, String parameterName) {	
		String value = (String) skin.getParameter(context, parameterName);
		if (null == value || "".equals(value)) {
			value = (String) defaultSkin.getParameter(context, parameterName);
		}
		return value;

	}

	/**
	 * Method to paint icon background 
	 * 
	 * @param colors - icon colors
	 * @param selected - flag which defines either main or selected background color should be used in drawing
	 * @param withoutSubBorders - flag which defines either sub borders should be painted in icon
	 * @return buffered image 
	 */
	protected BufferedImage paintMainBlock(Object[] colors, boolean selected, boolean withoutSubBorders) {

		int w = 22;
		int h = 22;

		BufferedImage image = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = prepareImage(image);

		Color additionalBackgroundColor = (Color) colors[0];
		Color selectControlColor = (Color) colors[1];
		Color panelBorderColor = (Color) colors[2];
		Color subBorderColorWithAlpha = new Color(255, 255, 255,
				SUB_BORDER_TRANSPARENCY);

		// Draw body
		Color contentColor;
		if (selected) {
			contentColor = selectControlColor;
		} else {
			contentColor = additionalBackgroundColor;
		}
		fillBodyWithGradient(g2d, w, h, contentColor);

		// Draw Border
		g2d.setColor(panelBorderColor);
		g2d.drawLine(0, 0, w, 0);
		g2d.drawLine(0, h - 1, w, h - 1);

		// Draw subBorder
		Color subBorderColorWithAlpha2 = new Color(contentColor.getRed(), contentColor.getGreen(), contentColor.getBlue(), SUB_BORDER_TRANSPARENCY);	
		if(!withoutSubBorders){
			g2d.setColor(subBorderColorWithAlpha);
			g2d.drawLine(0, 1, 0, h - 2);		
			g2d.drawLine(0, 1, w - 1, 1);	
			g2d.setColor(subBorderColorWithAlpha2);
			g2d.drawLine(0, h - 2, w - 1, h - 2);
			g2d.drawLine(w-1, 1, w-1, h - 2);	
		}

		g2d.dispose();

		return image;
	}

	/**
	 * Method to paint icons background separator
	 * 
	 * @param colors - icon colors
	 * @return buffered image 
	 */
	protected BufferedImage paintSeparatorBlock(Object[] colors) {

		int w = 5;
		int h = 22;

		BufferedImage image = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = prepareImage(image);

		Color additionalBackgroundColor = (Color) colors[0];
		Color panelBorderColor = (Color) colors[2];

		// Draw body
		g2d.setColor(additionalBackgroundColor);
		Rectangle2D border = new Rectangle2D.Double(0, 0, w, h);
		g2d.fill(border);

		// Draw Border
		g2d.setColor(panelBorderColor);
		g2d.drawLine(0, 1, 0, h - 2);
		g2d.drawLine(w - 1, 1, w - 1, h - 2);

		return image;
	}

	/**
	 * Method to paint expand/collapse icon for some control 
	 * 
	 * @param colors - icon colors
	 * @param selected - flag which defines either main or selected background color should be used in drawing
	 * @return buffered image 
	 */
	protected BufferedImage paintFirstTriangleBlock(Object[] colors, boolean selected) {

		int w = 11;
		int h = 22;

		BufferedImage image = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = prepareImage(image);

		Color additionalBackgroundColor = (Color) colors[0];
		Color selectControlColor = (Color) colors[1];
		Color panelBorderColor = (Color) colors[2];
		Color generalTextColor = (Color) colors[3];
		Color subBorderColorWithAlpha = new Color(255, 255, 255,
				SUB_BORDER_TRANSPARENCY);
		

		// Draw body
		Color contentColor;
		if (selected) {
			contentColor = selectControlColor;
			
		} else {
			contentColor = additionalBackgroundColor;
		}
		Color subBorderColorWithAlpha2 = new Color(contentColor.getRed(), contentColor.getGreen(), contentColor.getBlue(), SUB_BORDER_TRANSPARENCY);
		fillBodyWithGradient(g2d, w, h, contentColor);

		// Draw Border
		g2d.setColor(panelBorderColor);
		g2d.drawLine(0, 0, w, 0);
		g2d.drawLine(0, h - 1, w, h - 1);

		// Draw body
		g2d.setColor(subBorderColorWithAlpha);

		g2d.drawLine(0, 1, 0, h - 2);		
		g2d.drawLine(0, 1, w - 1, 1);		
		
		g2d.setColor(subBorderColorWithAlpha2);
		g2d.drawLine(w - 1, 1, w - 1, h - 2);		
		g2d.drawLine(0, h - 2, w - 1, h - 2);
		

		paintTriangle(g2d, 3, 10, generalTextColor, Color.WHITE, true);

		return image;
	}

	/**
	 * Method to paint arrow triangle for expand/collapse icon
	 * 
	 * @param g2d - Graphics2D instance
	 * @param x - x coordinate of the triangle 
	 * @param y - y coordinate of the triangle 
	 * @param main - main color of the triangle
	 * @param shadow - shadow color of the triangle
	 * @param shadowNeeded - defines if needed to draw triangle shadow
	 */
	private void paintTriangle(Graphics2D g2d, int x, int y, Color main,
			Color shadow, boolean shadowNeeded) {
		int w = 4;
		if (shadowNeeded) {
			g2d.setColor(shadow);
			g2d.drawLine(x, y + 1, x + w, y + 1);
			g2d.drawLine(x + 1, y + 2, x + w - 1, y + 2);
			g2d.drawLine(x + 2, y + 3, x + 2, y + 3);
		}
		g2d.setColor(main);
		g2d.drawLine(x, y, x + w, y);
		g2d.drawLine(x + 1, y + 1, x + w - 1, y + 1);
		g2d.drawLine(x + 2, y + 2, x + 2, y + 2);

	}

	/**
	 * Method to paint expand/collapse icon for dropdown list
	 * 
	 * @param colors - icon colors
	 * @param selected - flag which defines either main or selected background color should be used in drawing
	 * @return buffered image 
	 */
	protected BufferedImage paintSecondTriangleBlock(Object[] colors, boolean selected) {

		int w = 14;
		int h = 22;

		BufferedImage image = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = prepareImage(image);

		Color additionalBackgroundColor = (Color) colors[0];
		Color selectControlColor = (Color) colors[1];
		Color panelBorderColor = (Color) colors[2];
		Color generalTextColor = (Color) colors[3];
		Color subBorderColorWithAlpha = new Color(255, 255, 255,
				SUB_BORDER_TRANSPARENCY);

		// Draw body

		Color contentColor;
		if (selected) {
			contentColor = selectControlColor;
		} else {
			contentColor = additionalBackgroundColor;
		}
		fillBodyWithGradient(g2d, w, h, contentColor);

		// Draw Border
		g2d.setColor(panelBorderColor);
		g2d.drawLine(0, 0, w, 0);
		g2d.drawLine(0, h - 1, w, h - 1);
		g2d.drawLine(0, 0, 0, h);
		g2d.drawLine(w - 1, 0, w - 1, h);

		// Draw subBorder
		if(selected){
			g2d.setColor(Color.white);
		}else{
			g2d.setColor(subBorderColorWithAlpha);
		}
		if(selected){
			g2d.drawLine(1, 1, 1, h - 2);
			g2d.drawLine(w - 2, 1, w - 2, h - 2);
		}
		g2d.drawLine(1, 1, w - 2, 1);		
		g2d.drawLine(1, h - 2, w - 2, h - 2);

		paintTriangle(g2d, 4, 10, generalTextColor, Color.WHITE, true);

		return image;
	}
	
	/**
	 * Method to fill icon body with gradient
	 * 
	 * @param g2d - Graphics2D instance
	 * @param w - body width
	 * @param h - body height
	 * @param color - body color
	 */
	private void fillBodyWithGradient(Graphics2D g2d, int w, int h, Color color) {
		Color halfColor = new Color(color.getRed(), color.getGreen(), color
				.getBlue(), 90);
		Color halfColor2 = new Color(color.getRed(), color.getGreen(), color
				.getBlue(), 255);

		Rectangle2D border = new Rectangle2D.Double(0, 0, w, h);
		g2d.setColor(Color.WHITE);
		g2d.fill(border);

		g2d.setColor(halfColor);
		border = new Rectangle2D.Double(0, 0, w, h * 0.4);
		g2d.fill(border);

		Point2D point2 = new Point2D.Double(w * 0.5, h * 0.4);
		Point2D point3 = new Point2D.Double(w * 0.5, h);

		border = new Rectangle2D.Double(0, h * 0.4, w, h);

		Paint gradient = new GradientPaint(point2, halfColor2, point3,
				halfColor);
		g2d.setPaint(gradient);

		g2d.fill(border);

	}

	/**
	 * Method to prepare image for painting
	 * @param image
	 * @return Graphics2D instance for image
	 */
	private Graphics2D prepareImage(BufferedImage image) {

		Graphics2D g2d = image.createGraphics();

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
				RenderingHints.VALUE_COLOR_RENDER_QUALITY);

		g2d.setStroke(new BasicStroke(1));

		return g2d;
	}

}
