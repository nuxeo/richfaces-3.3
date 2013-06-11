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

package org.richfaces.renderkit.html.iconimages;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
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
 * @author Pavel Kotikov
 *
 */
public abstract class PanelMenuIconBasic extends Java2Dresource {
	private static final String TOP_BULLET_COLOR = "headerTextColor";
	private static final String ORDINAL_BULLET_COLOR = "headerBackgroundColor";

	public PanelMenuIconBasic() {
		setRenderer(new GifRenderer());
		setLastModified(new Date(InternetResourceBuilder.getInstance().getStartTime()));
	}

	
	public Dimension getDimensions(FacesContext facesContext, Object data) {
		return calculateDimensions(getDataToStore(facesContext, data));
	}
	protected Dimension getDimensions(ResourceContext resourceContext) {
		return calculateDimensions(restoreData(resourceContext));
	}
	
	protected void paint(ResourceContext context, Graphics2D graphics2D) {
		
		Color color = (Color) restoreData(context);
		
		if(color != null && graphics2D != null) {
			paintImage(context, graphics2D, color);
		}
		
	}
	
	abstract protected void paintImage(ResourceContext context, Graphics2D g2d, Color color);
	
	
	protected Dimension calculateDimensions(Object data){
		return new Dimension(16, 16);
	}

	protected Object deserializeData(byte[] objectArray) {
		
		if (objectArray == null) {
			return null;
		}
		
		return new Zipper2(objectArray).nextColor();
	}
	
	/**
	 * @param data - pass icon color there
	 */
	protected Object getDataToStore(FacesContext context, Object data) {
		Skin skin = SkinFactory.getInstance().getSkin(context);
		Skin defaultSkin = SkinFactory.getInstance().getDefaultSkin(context);
		
		byte[] ret = new byte[3];
		
		Color color = null;
		String skinParameter = ORDINAL_BULLET_COLOR;
		
		if (data != null) {
			if (((Boolean)data).booleanValue()) {
				skinParameter = TOP_BULLET_COLOR;
			}
		}
		String headerTextColor = (String) skin.getParameter(context, skinParameter);
		if (null == headerTextColor || "".equals(headerTextColor))
			headerTextColor = (String) defaultSkin.getParameter(context, skinParameter);
		
		if(headerTextColor == null) {
			return null;
		}
		
		color = HtmlColor.decode(headerTextColor);
		
		new Zipper2(ret).addColor(color);

		return ret;
	}
	
	public boolean isCacheable() {
		return true;
	}
	
}
