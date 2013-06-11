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

import static org.richfaces.renderkit.html.images.ColorUtils.adjustLightness;
import static org.richfaces.renderkit.html.images.ColorUtils.overwriteAlpha;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Date;

import javax.faces.context.FacesContext;

import org.ajax4jsf.resource.InternetResourceBase;
import org.ajax4jsf.resource.InternetResourceBuilder;
import org.ajax4jsf.resource.Java2Dresource;
import org.ajax4jsf.resource.PngRenderer;
import org.ajax4jsf.resource.ResourceContext;
import org.ajax4jsf.util.HtmlColor;
import org.ajax4jsf.util.Zipper2;
import org.richfaces.skin.SkinFactory;

public class ProgressBarBg extends Java2Dresource {

    private Dimension dim = new Dimension(24, 48);

    private Color progressbarBasicColor;

    /**
     * <P>
     * No args constructor.
     * </p>
     */
    public ProgressBarBg() {
        setRenderer(new PngRenderer());
        setLastModified(new Date(InternetResourceBuilder.getInstance().getStartTime()));
    }

    /**
     * @see Java2Dresource#getDimensions(ResourceContext)
     */
    protected Dimension getDimensions(ResourceContext resourceContext) {
        return dim;
    }

    /**
     * @see Java2Dresource#getDimensions(FacesContext, Object)
     */
    public Dimension getDimensions(FacesContext facesContext, Object data) {
        return dim;
    }

    /**
     * @see Java2Dresource#isCacheable(ResourceContext)
     */
    public boolean isCacheable(ResourceContext ctx) {
        return true;
    }

    /**
     * <P>
     * Paints fixed progress bar background.
     * </p>
     */
    protected void paint(ResourceContext context, Graphics2D g2d) {
        restoreData(context);
        Color progressbarBackgroundColor = progressbarBasicColor;
        Color progressbarSpiralColor = adjustLightness(progressbarBasicColor, 0.2f);
        Color progressbarShadowStartColor = overwriteAlpha(adjustLightness(progressbarBasicColor, 0.7f), 0.6f);
        Color progressbarShadowEndColor = overwriteAlpha(adjustLightness(progressbarBasicColor, 0.3f), 0.6f);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(progressbarBackgroundColor);
        g2d.fillRect(0, 0, dim.width, dim.height);
        g2d.setColor(progressbarSpiralColor);
        for (int k : new int[] { -24, 0, 24 }) {
            g2d.fillPolygon(new int[] { 0, 24, 24, 0 }, new int[] { 24 + k, k, 12 + k, 36 + k }, 4);
        }

        // paint a shadow in the form of semi-transparent gradient
        g2d.setPaint(new GradientPaint(0, 0, progressbarShadowStartColor, 0, 7, progressbarShadowEndColor));
        g2d.fillRect(0, 0, dim.width, 7);
    }

    /**
     * @see InternetResourceBase#getDataToStore(FacesContext, Object)
     */
    protected Object getDataToStore(FacesContext context, Object data) {
        byte[] retVal = new byte[3 * 1];
        progressbarBasicColor = getColorValueParameter(context, "selectControlColor");
        new Zipper2(retVal).addColor(progressbarBasicColor);

        return retVal;
    }

    /**
     * @see InternetResourceBase#deserializeData(byte[])
     */
    protected Object deserializeData(byte[] objectArray) {
        if (objectArray != null) {
            Zipper2 zipper2 = new Zipper2(objectArray);
            progressbarBasicColor = zipper2.nextColor();
        }

        return objectArray;
    }

    private Color getColorValueParameter(FacesContext context, String name) {
        Color retVal = null;
        String color = (String) SkinFactory.getInstance().getSkin(context).getParameter(context, name);
        if (null == color || "".equals(color.trim())) {
            color = (String) SkinFactory.getInstance().getDefaultSkin(context).getParameter(context, name);
        }

        if (null != color && !"".equals(color.trim())) {
            retVal = HtmlColor.decode(color);
        }

        return retVal;
    }

}
