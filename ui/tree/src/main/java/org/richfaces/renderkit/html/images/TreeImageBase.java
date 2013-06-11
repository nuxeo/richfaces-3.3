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
import org.ajax4jsf.resource.ResourceContext;
import org.ajax4jsf.util.HtmlColor;
import org.ajax4jsf.util.Zipper;
import org.richfaces.skin.Skin;
import org.richfaces.skin.SkinFactory;

public abstract class TreeImageBase extends Java2Dresource {

	public TreeImageBase() {
		setRenderer(new GifRenderer());
		setLastModified(new Date(InternetResourceBuilder.getInstance().getStartTime()));
	}

	protected Object getDataToStore(FacesContext context, Object data) {
		
		TreeImageData dt = new TreeImageData();
		String tmp;
		int intValue;
		Skin skin = SkinFactory.getInstance().getSkin(context);
		Skin defaultSkin = SkinFactory.getInstance().getDefaultSkin(context);
		
		String skinParameter = "generalTextColor";
		tmp = (String) skin.getParameter(context, skinParameter);
		if (null == tmp || "".equals(tmp)) {
			tmp = (String) defaultSkin.getParameter(context, skinParameter);
		}
		if (tmp != null && !"".equals(tmp)) {
			intValue = HtmlColor.decode(tmp).getRGB();
			dt.setGeneralColor(new Integer(intValue));
		} else 
			dt.setGeneralColor(null);

		skinParameter = "controlBackgroundColor";
		tmp = (String) skin.getParameter(context, skinParameter);
		if (null == tmp || "".equals(tmp)) {
			tmp = (String) defaultSkin.getParameter(context, skinParameter);
		}
		if (tmp != null && !"".equals(tmp)) {
			intValue = HtmlColor.decode(tmp).getRGB();
			dt.setControlColor(new Integer(intValue));
		} else 
			dt.setControlColor(null);

		skinParameter = "trimColor";
		tmp = (String) skin.getParameter(context, skinParameter);
		if (null == tmp || "".equals(tmp)) {
			tmp = (String) defaultSkin.getParameter(context, skinParameter);
		}
		if (tmp != null && !"".equals(tmp)) {
			intValue = HtmlColor.decode(tmp).getRGB();
			dt.setTrimColor(new Integer(intValue));
		} else 
			dt.setTrimColor(null);
		
		return dt.toByteArray();
	}
	
	public boolean isCacheable() {
		return true;
	}
	
	public TreeImageData getTreeImageData(ResourceContext resourceContext) {
		return new TreeImageData((byte[])resourceContext.getResourceData());
	}

	protected static class TreeImageData implements Serializable{
		private static final long serialVersionUID = 1732700513743861251L;
		
		private Integer controlColor;
		private Integer trimColor;
		private Integer generalColor;

		public TreeImageData() {
			
		}
		
		public TreeImageData(byte[] data) {
			controlColor = new Integer(Zipper.unzip(data,0));
			trimColor = new Integer(Zipper.unzip(data,3));
			generalColor = new Integer(Zipper.unzip(data,6));
		}
		
		public Integer getControlColor() {
			return controlColor;
		}
		public void setControlColor(Integer controlColor) {
			this.controlColor = controlColor;
		}
		public Integer getTrimColor() {
			return trimColor;
		}
		public void setTrimColor(Integer trimColor) {
			this.trimColor = trimColor;
		}
		public Integer getGeneralColor() {
			return generalColor;
		}
		public void setGeneralColor(Integer generalColor) {
			this.generalColor = generalColor;
		}
		
		public byte[] toByteArray() {
			byte[] ret = new byte[9];
			Zipper.zip(ret,controlColor.intValue(),0);
			Zipper.zip(ret,trimColor.intValue(),3);
			Zipper.zip(ret,generalColor.intValue(),6);
			return ret;			
		}
	
	}
	
}
