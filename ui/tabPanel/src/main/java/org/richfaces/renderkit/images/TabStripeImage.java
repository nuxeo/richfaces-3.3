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

package org.richfaces.renderkit.images;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
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

/**
 * @author Nick - mailto:nbelaevski@exadel.com
 *         created 02.02.2007
 */
public class TabStripeImage extends Java2Dresource {
    private int width;
    private int height;

    public TabStripeImage() {
        super();

        this.width = 1;
        this.height = 200;
        
        setRenderer(new GifRenderer());
        setLastModified(new Date(InternetResourceBuilder.getInstance().getStartTime()));
    }

    protected Object getDataToStore(FacesContext context, Object data) {
        Skin skin = SkinFactory.getInstance().getSkin(context);
        String colorParameterName = "panelBorderColor";
        String color = (String) skin.getParameter(context, colorParameterName);
        if (color == null || color.length() == 0) {
            Skin defaultSkin = SkinFactory.getInstance().getDefaultSkin(context);
        	color = (String) defaultSkin.getParameter(context, colorParameterName);
        }
        
        if (color == null) {
        	return null;
        }
        
        byte[] ret = new byte[3];
        Zipper.zip(ret,HtmlColor.decode(color).getRGB(),0);
        return ret;
    }

    protected Dimension getDimensions(ResourceContext resourceContext) {
        return getDimensions(null, restoreData(resourceContext));
    }

    public Dimension getDimensions(FacesContext facesContext, Object data) {
        return new Dimension(width, height);
    }

    protected Object deserializeData(byte[] objectArray) {
    	if (objectArray == null) {
    		return null;
    	}
    	return new Integer(Zipper.unzip(objectArray, 0));
    }
    
    protected void paint(ResourceContext context, Graphics2D graphics2D) {
        super.paint(context, graphics2D);

        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_DITHERING,
                RenderingHints.VALUE_DITHER_ENABLE);
        graphics2D.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
                RenderingHints.VALUE_COLOR_RENDER_QUALITY);

        Integer tabData = (Integer) restoreData(context);
        if (tabData != null) {
            Dimension dimension = getDimensions(context);
            Rectangle2D region = new Rectangle2D.Double(0, 1, dimension.getWidth(), dimension.getHeight() - 1);

            graphics2D.setColor(new Color(tabData.intValue()));
            graphics2D.fill(region);
        }
    }
}
