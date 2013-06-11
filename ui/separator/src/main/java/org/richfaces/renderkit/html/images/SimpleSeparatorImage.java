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
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.Serializable;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.ajax4jsf.resource.GifRenderer;
import org.ajax4jsf.resource.InternetResourceBuilder;
import org.ajax4jsf.resource.Java2Dresource;
import org.ajax4jsf.resource.ResourceContext;
import org.ajax4jsf.util.HtmlColor;
import org.ajax4jsf.util.HtmlDimensions;
import org.ajax4jsf.util.Zipper2;
import org.richfaces.component.UISeparator;
import org.richfaces.renderkit.html.SeparatorRendererBase;
import org.richfaces.skin.Skin;
import org.richfaces.skin.SkinFactory;

/**
 * @author Konstantin Mishin, Filip Antonov
 */
public class SimpleSeparatorImage extends Java2Dresource {

    //line type may be dotted, dashed, double or solid.
    private static final int LINE_TYPE_DOTTED = 1;
    private static final int LINE_TYPE_DASHED = 2;
    private static final int LINE_TYPE_DOUBLE = 3;
    private static final int LINE_TYPE_SOLID = 4;

    public SimpleSeparatorImage() {
        setRenderer(new GifRenderer());
        setLastModified(new Date(InternetResourceBuilder.getInstance().getStartTime()));
    }

    protected RenderedImage getImage(ResourceContext context) {
        SimpleSeparatorData separatorData = (SimpleSeparatorData) restoreData(context);
        Color color = new Color(separatorData.getColor());
        int height = separatorData.getHeight();
        int lineType = separatorData.getLineType();
        if (height <= 0) height = 1;
        Graphics2D g2d = null;

        //XXX by nick - fantonov - please use switch-case blocks here
        switch (lineType) {
            case LINE_TYPE_DOTTED:
                BufferedImage bufferedImage = new BufferedImage(3 * height, height, BufferedImage.TYPE_INT_ARGB);
                g2d = (Graphics2D) bufferedImage.createGraphics();
                g2d.setColor(color);
                g2d.fillRect(0, 0, height, height);
                return bufferedImage;

            case LINE_TYPE_DASHED:
                bufferedImage = new BufferedImage(4 * height, height, BufferedImage.TYPE_INT_ARGB);
                g2d = (Graphics2D) bufferedImage.createGraphics();
                g2d.setColor(color);
                g2d.fillRect(0, 0, 3 * height, height);
                return bufferedImage;

            case LINE_TYPE_DOUBLE:
                bufferedImage = new BufferedImage(1, height, BufferedImage.TYPE_INT_ARGB);
                int lineHeight = Math.round((float) height / 3);
                g2d = (Graphics2D) bufferedImage.createGraphics();
                g2d.setColor(color);
                g2d.fillRect(0, 0, 1, lineHeight);
                g2d.fillRect(0, height - lineHeight, 1, lineHeight);
//				for (int i = 0; i < lineHeight; i++) bufferedImage.setRGB(0, i, color);
//				for (int i = height-lineHeight; i < height; i++) bufferedImage.setRGB(0, i, color);
                return bufferedImage;

            case LINE_TYPE_SOLID:
                bufferedImage = new BufferedImage(1, height, BufferedImage.TYPE_INT_ARGB);
                g2d = (Graphics2D) bufferedImage.createGraphics();
                g2d.setColor(color);
                g2d.fillRect(0, 0, 1, height);
//				for (int i = 0; i < height; i++) bufferedImage.setRGB(0, i, color);
                return bufferedImage;
        }
        return null;
    }

    protected Object deserializeData(byte[] objectArray) {
    	if(objectArray == null){
    		return null;
    	}
    	
        SimpleSeparatorData separatorData = new SimpleSeparatorData();
    	Zipper2 zipper2 = new Zipper2(objectArray);
        separatorData.setHeight(zipper2.nextShort());
    	separatorData.setColor(zipper2.nextIntColor());
    	separatorData.setLineType(zipper2.nextByte());
    	
        return separatorData;
    }
    
    protected Object getDataToStore(FacesContext context, Object data) {
        Skin skin = SkinFactory.getInstance().getSkin(context);

        byte[] ret = new byte[6];
        
        String tmp = (String) ((UIComponent) data).getAttributes().get("height");
        int height = (tmp == null || "".equals(tmp)) ? SeparatorRendererBase.DEFAULT_HEIGHT : HtmlDimensions.decode(tmp).intValue();
        Zipper2 zipper2 = new Zipper2(ret).addShort((short) height);

        String skinParameter = "headerBackgroundColor";
        tmp = (String) skin.getParameter(context, skinParameter);
        if (null == tmp || "".equals(tmp)) {
			Skin defaultSkin = SkinFactory.getInstance().getDefaultSkin(context);
			tmp = (String) defaultSkin.getParameter(context, skinParameter);
		}
        zipper2.addColor(HtmlColor.decode(tmp == null ? "#4169E1" : tmp).getRGB());

        //XXX by nick - fantonov - ((UISeparator)data).getLineType() ?
        tmp = (String) ((UISeparator) data).getLineType();
        int lineType = LINE_TYPE_SOLID;
        if (tmp.equalsIgnoreCase(SeparatorRendererBase.LINE_TYPE_DOTTED)) {
            lineType = LINE_TYPE_DOTTED;
        } else if (tmp.equalsIgnoreCase(SeparatorRendererBase.LINE_TYPE_DASHED)) {
            lineType = LINE_TYPE_DASHED;
        } else
        if (tmp.equalsIgnoreCase(SeparatorRendererBase.LINE_TYPE_DOUBLE) && height > 2)
        {
            lineType = LINE_TYPE_DOUBLE;
        }
        zipper2.addByte((byte) lineType);
        return ret;
    }

    protected static class SimpleSeparatorData implements Serializable {

        private static final long serialVersionUID = -4420043779436390358L;
        private int height;
        private int color;
        private int lineType;

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public int getLineType() {
            return lineType;
        }

        public void setLineType(int lineType) {
            this.lineType = lineType;
        }
    }
}
