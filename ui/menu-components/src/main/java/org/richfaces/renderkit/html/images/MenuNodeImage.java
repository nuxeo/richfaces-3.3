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
package org.richfaces.renderkit.html.images;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.Serializable;
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

public class MenuNodeImage extends Java2Dresource {

    private static final Dimension dimensions = new Dimension(16, 32);
    
    protected Dimension getDimensions(ResourceContext resourceContext) {
        return dimensions;
    }
    public Dimension getDimensions(FacesContext facesContext, Object data) {
        return dimensions;
    }

    public MenuNodeImage() {
        setRenderer(new GifRenderer());
        setLastModified(new Date(InternetResourceBuilder.getInstance().getStartTime()));
    }

    protected Object deserializeData(byte[] objectArray) {
    	if (objectArray == null) {
    		return null;
    	}
    	
        MenuNodeImageData mnd = new MenuNodeImageData();
        Zipper2 zipper2 = new Zipper2(objectArray);
        mnd.setGeneralColor(new Integer(zipper2.nextIntColor()));
        mnd.setDisabledColor(new Integer(zipper2.nextIntColor()));
        
    	return mnd;
    }
    
    protected Object getDataToStore(FacesContext context, Object data) {
        Skin skin = SkinFactory.getInstance().getSkin(context);
        Skin defaultSkin = SkinFactory.getInstance().getDefaultSkin(context);
        
        String tmp;
        int intValue;
        
        String skinParameter = "generalTextColor";
        tmp = (String) skin.getParameter(context, skinParameter);
        
        if (null == tmp || "".equals(tmp)) {
			tmp = (String) defaultSkin.getParameter(context, skinParameter);
		}
        
        if (tmp == null || "".equals(tmp))
        	tmp = "#4A75B5";
        
        intValue = HtmlColor.decode(tmp).getRGB();
        
        byte[] ret = new byte[6];
        Zipper2 zipper2 = new Zipper2(ret).addColor(intValue);
        
        skinParameter = "tabDisabledTextColor";
        tmp = (String) skin.getParameter(context, skinParameter);
        if (null == tmp || "".equals(tmp)) {
			tmp = (String) defaultSkin.getParameter(context, skinParameter);
		}
        if (tmp == null || "".equals(tmp))
        	tmp = "#6A92CF";
        intValue = HtmlColor.decode(tmp).getRGB();
        
        zipper2.addColor(intValue);
        
        return ret;
    }
    
    protected void paint(ResourceContext context, Graphics2D g2d) {
        MenuNodeImageData mnd = (MenuNodeImageData) restoreData(context);
        
        if (mnd != null) {
        	
        	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
	        int x0 = 6;
	        int y0 = 8;
	
	        g2d.setColor(new Color(mnd.getGeneralColor().intValue()));
	        g2d.drawLine(x0, y0 - 3, x0, y0 +3);
	        g2d.drawLine(x0 + 1, y0 - 2, x0 + 1, y0 + 2);
	        g2d.drawLine(x0 + 2, y0 - 1, x0 + 2, y0 + 1);
	        g2d.drawLine(x0 + 3, y0, x0 + 3, y0);
	
	        int y1 = 24;
	
	        g2d.setColor(new Color(mnd.getDisabledColor().intValue()));
	        g2d.drawLine(x0, y1 - 3, x0, y1 +3);
	        g2d.drawLine(x0 + 1, y1 - 2, x0 + 1, y1 + 2);
	        g2d.drawLine(x0 + 2, y1 - 1, x0 + 2, y1 + 1);
	        g2d.drawLine(x0 + 3, y1, x0 + 3, y1);
	        
        }
        
    }
    
    
    protected static class MenuNodeImageData implements Serializable{
        private static final long serialVersionUID = 1732700513743861251L;
        
        private Integer disabledColor;
        private Integer generalColor;
        
        public Integer getDisabledColor() {
            return disabledColor;
        }
        public void setDisabledColor(Integer disabledColor) {
            this.disabledColor = disabledColor;
        }
        public Integer getGeneralColor() {
            return generalColor;
        }
        public void setGeneralColor(Integer generalColor) {
            this.generalColor = generalColor;
        }
    }
}
