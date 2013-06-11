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

import java.io.Serializable;
import java.util.Date;

import javax.faces.context.FacesContext;

import org.ajax4jsf.resource.GifRenderer;
import org.ajax4jsf.resource.InternetResourceBuilder;
import org.ajax4jsf.resource.Java2Dresource;
import org.ajax4jsf.util.HtmlColor;
import org.ajax4jsf.util.HtmlDimensions;
import org.ajax4jsf.util.Zipper;
import org.ajax4jsf.util.Zipper2;
import org.richfaces.component.UIToolBar;
import org.richfaces.component.UIToolBarGroup;
import org.richfaces.skin.Skin;
import org.richfaces.skin.SkinFactory;

/**
 * @author Maksim Kaszynski, Filip Antonov
 *
 */
public abstract class ToolBarSeparatorImage extends Java2Dresource {

	public ToolBarSeparatorImage() {
		setRenderer(new GifRenderer());
		setLastModified(new Date(InternetResourceBuilder.getInstance().getStartTime()));
	}
	
	protected Object deserializeData(byte[] objectArray) {
		if (objectArray == null) {
			return null;
		}
		
		SeparatorData separatorData = new SeparatorData();
		Zipper2 zipper2 = new Zipper2(objectArray);
		separatorData.setHeaderBackgroundColor(zipper2.nextIntColor());
		separatorData.setHeaderGradientColor(zipper2.nextIntColor());
		separatorData.setHeaderTextColor(zipper2.nextIntColor());
		separatorData.setFontSize(zipper2.nextShort());
		separatorData.setSeparatorHeight(zipper2.nextShort());
		
		return separatorData;
	}
	
	protected Object getDataToStore(FacesContext context, Object data) {
		Skin skin = SkinFactory.getInstance().getSkin(context);
		Skin defaultSkin = SkinFactory.getInstance().getDefaultSkin(context);
		
		String skinParameter = "headerBackgroundColor";
		String tmp = (String) skin.getParameter(context, skinParameter);
		if (null == tmp || "".equals(tmp)) {
			tmp = (String) defaultSkin.getParameter(context, skinParameter);
		}
		
		byte[] ret = new byte[13];
		Zipper2 zipper2 = new Zipper2(ret).addColor(HtmlColor.decode( tmp == null ? "#224986" : tmp ).getRGB());
		
		skinParameter = "headerGradientColor";
		tmp = (String) skin.getParameter(context, skinParameter);
		if (null == tmp || "".equals(tmp)) {
			tmp = (String) defaultSkin.getParameter(context, skinParameter);
		}
		zipper2.addColor(HtmlColor.decode( tmp == null ? "#CCCCFF" : tmp).getRGB());
		
		skinParameter = "headerTextColor";
		tmp = (String) skin.getParameter(context, skinParameter);
		if (null == tmp || "".equals(tmp)) {
			tmp = (String) defaultSkin.getParameter(context, skinParameter);
		}
		zipper2.addColor(HtmlColor.decode( tmp == null ? "#000000" : tmp ).getRGB());
		
		skinParameter = "headerSizeFont";
		tmp = (String) skin.getParameter(context, skinParameter);
		if (null == tmp || "".equals(tmp)) {
			tmp = (String) defaultSkin.getParameter(context, skinParameter);
		}
		zipper2.addShort((short) HtmlDimensions.decode( tmp == null ? "11px" : tmp ).intValue());
		zipper2.addShort((short) resolveBarHeight(data));
		
		return ret;
	}
	
	private int resolveBarHeight(Object data){		
		String height = null;
		if (data instanceof UIToolBar) {
			height = (String) ((UIToolBar) data).getHeight();
		} else if (data instanceof UIToolBarGroup) {
			UIToolBar toolBar = ((UIToolBarGroup) data).getToolBar();
			height = (String) toolBar.getHeight();
		}		

		//TODO nick - nick - exception here
		
		return  Math.round(HtmlDimensions.decode( height == null ? "11px" : height ).floatValue()*0.8f);
	}
	
	protected static class SeparatorData implements Serializable{
		private static final long serialVersionUID = -6367074056069548706L;
		private int fontSize;
		private int headerTextColor;
		private int headerBackgroundColor;
		private int separatorHeight;
		private int headerGradientColor;
		
		public int getHeaderBackgroundColor() {
			return headerBackgroundColor;
		}
		public void setHeaderBackgroundColor(int bgColor) {
			this.headerBackgroundColor = bgColor;
		}
		public int getHeaderTextColor() {
			return headerTextColor;
		}
		public void setHeaderTextColor(int textColor) {
			this.headerTextColor = textColor;
		}
		public int getFontSize() {
			return fontSize;
		}
		public void setFontSize(int fontSize) {
			this.fontSize = fontSize;
		}
		public int getSeparatorHeight() {
			return separatorHeight;
		}
		public void setSeparatorHeight(int separatorHeight) {
			this.separatorHeight = separatorHeight;
		}
		public int getHeaderGradientColor() {
			return headerGradientColor;
		}
		public void setHeaderGradientColor(int headerGradientColor) {
			this.headerGradientColor = headerGradientColor;
		}
		
	}
}
