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
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.Date;

import javax.faces.context.FacesContext;

import org.ajax4jsf.resource.GifRenderer;
import org.ajax4jsf.resource.InternetResourceBuilder;
import org.ajax4jsf.resource.Java2Dresource;
import org.ajax4jsf.resource.ResourceContext;
import org.ajax4jsf.util.HtmlColor;
import org.ajax4jsf.util.HtmlDimensions;
import org.ajax4jsf.util.Zipper2;
import org.richfaces.component.UISeparator;
import org.richfaces.skin.Skin;
import org.richfaces.skin.SkinFactory;

/**
 * @author Maksim Kaszynski, Filip Antonov
 */
public class BevelSeparatorImage extends Java2Dresource {

    public BevelSeparatorImage() {
        setRenderer(new GifRenderer());
        setLastModified(new Date(InternetResourceBuilder.getInstance().getStartTime()));
    }

    public Dimension getDimensions(FacesContext facesContext, Object data) {
        return calculateDimensions(getDataToStore(facesContext, data));
    }

    protected Dimension getDimensions(ResourceContext resourceContext) {
        return calculateDimensions(restoreData(resourceContext));
    }

    protected Dimension calculateDimensions(Object data) {
        return new Dimension(1, ((SeparatorData) data).getHeight());
    }

    protected void paint(ResourceContext context, Graphics2D g2d) {
        SeparatorData stored = (SeparatorData) restoreData(context);
      
        if(stored != null) {
	   
        	Color bg = new Color(stored.getBgColor());
	        Color grad = new Color(stored.getGradColor());
	        float [] grads = grad.getRGBComponents(null);
	        int level3D = (stored.getLevel3D());
	        Dimension dim = calculateDimensions(stored);
	        float height = (float) dim.getHeight();
	        float width = (float) dim.getWidth();
	
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
	        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
	
	        Rectangle2D rect = new Rectangle2D.Double(0.0, 0.0, width, height);
	        g2d.setColor(bg);
	        g2d.fill(rect);
	
	        if (level3D > 0) {
	            g2d.translate(0d, height / 18d);
	            // Vertical cradient.
	            float verticalOpacity = ((float) (level3D + 1) * 0.1f);
	            if (verticalOpacity < 0) {
	                verticalOpacity = 0;
	            } else if (verticalOpacity > 1) {
	                verticalOpacity = 1;
	            }
	            g2d.setPaint(
	                    new GradientPaint(
	                            width / 2,
	                            0f,
	                            new Color(
	                                    grads[0],
	                                    grads[1],
	                                    grads[2],
	                                    verticalOpacity),
	                            width / 2,
	                            height / 2,
	                            new Color(
	                                    1f,
	                                    1f,
	                                    1f,
	                                    0f)));
	            g2d.fill(rect);
	            verticalOpacity = ((float) (level3D - 1) * 0.1f);
	            if (verticalOpacity < 0) {
	                verticalOpacity = 0;
	            } else if (verticalOpacity > 1) {
	                verticalOpacity = 1;
	            }
	            //XXX by nick - fantonov - get rid of get*() - store values in local var
	            g2d.setPaint(new GradientPaint(width / 2, height / 2, new Color(1f, 1f, 1f, 0f),
	                    width / 2, height * 0.75f, new Color(grads[0], grads[1], grads[2], verticalOpacity), true));
	            Shape currentClip = g2d.getClip();
	            Rectangle2D.Double clipRect = new Rectangle2D.Double(0, height / 2, width * 2, height);
	            g2d.setClip(clipRect);
	            g2d.fill(rect);
	            g2d.setClip(currentClip);
	        }     
        
        }

    }
    
    protected Object deserializeData(byte[] objectArray) {
    	if (objectArray == null) {
    		return null;
    	}
    	
        SeparatorData separatorData = new SeparatorData();
        Zipper2 zipper2 = new Zipper2(objectArray);
        separatorData.setHeight(zipper2.nextShort());
        separatorData.setLevel3D(zipper2.nextShort());
        separatorData.setBgColor(zipper2.nextIntColor());
        separatorData.setGradColor(zipper2.nextIntColor());
        
        return separatorData;
    }

    protected Object getDataToStore(FacesContext context, Object data) {
        Skin skin = SkinFactory.getInstance().getSkin(context);

        //XXX by nick - fantonov - ((UISeparator)data).getHeight() ?
        String tmp = (String) ((UISeparator) data).getHeight();
        
        byte[] ret = new byte[10];
        
        Zipper2 zipper2 = new Zipper2(ret).addShort((short) HtmlDimensions.decode(tmp == null ? "6" : tmp).intValue());

        tmp = (String) skin.getParameter(context, "interfaceLevel3D");
        zipper2.addShort((short) HtmlDimensions.decode(tmp == null ? "5" : tmp).intValue());

        String skinParameter = "headerBackgroundColor";
        tmp = (String) skin.getParameter(context, skinParameter);
        if (null == tmp || "".equals(tmp)) {
			Skin defaultSkin = SkinFactory.getInstance().getDefaultSkin(context);
			tmp = (String) defaultSkin.getParameter(context, skinParameter);
		}
        zipper2.addColor(HtmlColor.decode(tmp == null ? "#4169E1" : tmp).getRGB());

        skinParameter = "overAllBackground";
        tmp = (String) skin.getParameter(context, skinParameter);
        if (null == tmp || "".equals(tmp)) {
			Skin defaultSkin = SkinFactory.getInstance().getDefaultSkin(context);
			tmp = (String) defaultSkin.getParameter(context, skinParameter);
		}
        zipper2.addColor(HtmlColor.decode(tmp == null ? "#FFFFFF" : tmp).getRGB());

        return ret;
    }

    protected static class SeparatorData implements Serializable {
        private static final long serialVersionUID = -6368074056069548706L;
        private int height;
        private int level3D;
        private int bgColor;
        private int gradColor;

        public int getBgColor() {
            return bgColor;
        }

        public void setBgColor(int bgColor) {
            this.bgColor = bgColor;
        }

        public int getGradColor() {
            return gradColor;
        }

        public void setGradColor(int gradColor) {
            this.gradColor = gradColor;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getLevel3D() {
            return level3D;
        }

        public void setLevel3D(int level3D) {
            this.level3D = level3D;
        }
    }
}
