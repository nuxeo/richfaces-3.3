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

package org.richfaces.renderkit.html;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.ajax4jsf.resource.InternetResourceBuilder;
import org.ajax4jsf.resource.Java2Dresource;
import org.ajax4jsf.resource.PngRenderer;
import org.ajax4jsf.resource.ResourceContext;
import org.ajax4jsf.util.HtmlColor;
import org.ajax4jsf.util.HtmlDimensions;
import org.ajax4jsf.util.Zipper2;
import org.richfaces.renderkit.html.images.GradientAlignment;
import org.richfaces.renderkit.html.images.GradientType;
import org.richfaces.renderkit.html.images.GradientType.BiColor;
import org.richfaces.skin.Skin;
import org.richfaces.skin.SkinFactory;

/**
 * @author Nick Belaevski - nbelaevski@exadel.com
 * created 02.02.2007
 * 
 */
public class CustomizeableGradient extends Java2Dresource {

    private Dimension dimension = new Dimension(20, 500);

    public CustomizeableGradient() {
	super();

	setRenderer(new PngRenderer());
	setLastModified(new Date(InternetResourceBuilder.getInstance().getStartTime()));
    }
    
    public Dimension getDimensions(FacesContext facesContext, Object data) {
	return dimension;
    }

    protected Dimension getDimensions(ResourceContext resourceContext) {
	return dimension;
    }

    private void drawRectangle(Graphics2D g2d, Rectangle2D rect, BiColor biColor, boolean useTop) {
	if (biColor != null) {
	    Color color = useTop ? biColor.getTopColor() : biColor.getBottomColor();
	    g2d.setColor(color);
	    g2d.fill(rect);
	}
    }

    private void drawGradient(Graphics2D g2d, Rectangle2D rectangle, BiColor colors, int height) {
	if (colors != null) {
	    GradientPaint gragient = new GradientPaint(0, 0, colors.getTopColor(), 0, height, colors.getBottomColor());
	    g2d.setPaint(gragient);
	    g2d.fill(rectangle);
	}
    }

    protected void paint(ResourceContext resourceContext, Graphics2D g2d) {
	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);

	g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
	g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
	g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

	Data dataToStore = (Data) restoreData(resourceContext);
	if (dataToStore != null) {
	    BiColor biColor = new GradientType.BiColor(dataToStore.getGradientColor(), dataToStore.getBaseColor());

	    GradientType type = dataToStore.getGradientType();
	    BiColor firstLayer = type.getFirstLayerColors(biColor);
	    BiColor secondLayer = type.getSecondLayerColors(biColor);

	    Dimension dim = getDimensions(resourceContext);
	    int gradientHeight = dataToStore.getGradientHeight();
	    
	    Rectangle2D rect = 
		new Rectangle2D.Float(
			0, 
			0, 
			dim.width, 
			dim.height);

	    GradientAlignment gradientAlignment = dataToStore.getGradientAlignment();
	    int topRectangleHeight = gradientAlignment.getTopRectangleHeight(dim.height, gradientHeight);
	    int bottomRectangleHeight = gradientAlignment.getBottomRectangleHeight(dim.height, gradientHeight);

	    rect = new Rectangle2D.Float(
		    0, 
		    0, 
		    dim.width, 
		    topRectangleHeight);

	    drawRectangle(g2d, rect, firstLayer, true);
	    drawRectangle(g2d, rect, secondLayer, true);

	    rect = new Rectangle2D.Float(
		    0, 
		    dim.height - bottomRectangleHeight, 
		    dim.width, 
		    dim.height);

	    drawRectangle(g2d, rect, firstLayer, false);
	    drawRectangle(g2d, rect, secondLayer, false);

	    g2d.transform(AffineTransform.getTranslateInstance(0, topRectangleHeight));

	    rect = new Rectangle2D.Float(
		    0, 
		    0, 
		    dim.width, 
		    dim.height);

	    drawGradient(g2d, rect, firstLayer, gradientHeight);

	    rect = new Rectangle2D.Float(
		    0, 
		    0, 
		    dim.width, 
		    gradientHeight / 2);

	    drawGradient(g2d, rect, secondLayer, gradientHeight / 2);
	}
    }

    protected Object deserializeData(byte[] objectArray) {
	Data data = new Data();
	if (objectArray != null) {
	    Zipper2 zipper2 = new Zipper2(objectArray);

	    data.setGradientType(GradientType.values()[zipper2.nextByte()]);
	    data.setGradientAlignment(GradientAlignment.values()[zipper2.nextByte()]);
	    data.setGradientHeight(zipper2.nextInt());
	    data.setBaseColor(Integer.valueOf(zipper2.nextIntColor()));

	    if (zipper2.hasMore()) {
		data.setGradientColor(Integer.valueOf(zipper2.nextIntColor()));
	    } else {
		data.setGradientColor(data.getBaseColor());
	    }
	}

	return data;
    }

    private Integer decodeColor(String value) {
	if (value !=null && value.length() != 0) {
	    return Integer.valueOf(HtmlColor.decode(value).getRGB());
	} else {
	    return null;
	}
    }

    private Integer decodeHeight(String value) {
	if (value !=null && value.length() != 0) {
	    return HtmlDimensions.decode(value).intValue();
	} else {
	    return null;
	}
    }
    
    protected static String safeTrim(String s) {
    	return s != null ? s.trim() : null;
    }

    protected Object getDataToStore(FacesContext context, Object parameterData) {
	Data data = new Data();

	Integer baseIntColor = null;
	Integer gradientIntColor = null;
	Integer gradientHeight = null;

	String gradientTypeString = null;
	String alignmentString = null;

	if (parameterData instanceof Map<?, ?>) {
	    Map<?, ?> map = (Map<?, ?>) parameterData;

	    gradientTypeString = safeTrim((String) map.get(Skin.gradientType));
	    alignmentString = safeTrim((String) map.get("valign"));
	    baseIntColor = decodeColor((String) map.get("baseColor"));
	    gradientIntColor = decodeColor((String) map.get("gradientColor"));
	    gradientHeight = decodeHeight((String) map.get("gradientHeight"));
	}

	data.setBaseColor(baseIntColor);
	if (gradientIntColor != null) {
	    data.setGradientColor(gradientIntColor);
	} else {
	    data.setGradientColor(baseIntColor);
	}
	
	if (gradientHeight == null) {
	    gradientHeight = 22;
	}
	data.setGradientHeight(gradientHeight);
	
	if (gradientTypeString == null || gradientTypeString.length() == 0) {
	    gradientTypeString = safeTrim(getValueParameter(context, Skin.gradientType));
	}

	data.setGradientType(GradientType.getByParameter(gradientTypeString));
	data.setGradientAlignment(GradientAlignment.getByParameter(alignmentString));

	return data.toByteArray();
    }

    public boolean isCacheable() {
	return true;
    }

    protected String getValueParameter(FacesContext context, String name) {
	SkinFactory skinFactory = SkinFactory.getInstance();

	Skin skin = skinFactory.getSkin(context);
	String value = (String) skin.getParameter(context, name);

	if (value == null || value.length() == 0) {
	    skin = skinFactory.getDefaultSkin(context);
	    value = (String) skin.getParameter(context, name);
	}

	return value;
    }

    protected static class Data implements Serializable {
	public Data() {
	}

	/**
	 * 
	 */
	 private static final long serialVersionUID = 1732700513743861250L;
	 private Integer gradientColor;
	 private Integer baseColor;
	 private Integer gradientHeight;
	 
	 private GradientType gradientType;
	 private GradientAlignment gradientAlignment;

	 public byte[] toByteArray() {
	     if (baseColor != null && gradientColor != null && gradientHeight != null && gradientAlignment != null && gradientType != null) {
		 byte[] ret = new byte[12];
		 new Zipper2(ret)
		 	.addByte((byte) gradientType.ordinal())
		 	.addByte((byte) gradientAlignment.ordinal())
		 	.addInt(gradientHeight.intValue())
		 	.addColor(baseColor.intValue())
		 	.addColor(gradientColor.intValue());

		 return ret;
	     } else {
		 return null;
	     }
	 }

	 public Integer getGradientColor() {
	     return gradientColor;
	 }

	 public void setGradientColor(Integer headerBackgroundColor) {
	     this.gradientColor = headerBackgroundColor;
	 }

	 public Integer getBaseColor() {
	     return baseColor;
	 }

	 public void setBaseColor(Integer headerGradientColor) {
	     this.baseColor = headerGradientColor;
	 }

	 public GradientType getGradientType() {
	     return gradientType;
	 }

	 public void setGradientType(GradientType gradientType) {
	     this.gradientType = gradientType;
	 }

	 public GradientAlignment getGradientAlignment() {
	     return gradientAlignment;
	 }

	 public void setGradientAlignment(GradientAlignment gradientAlignment) {
	     this.gradientAlignment = gradientAlignment;
	 }

	public Integer getGradientHeight() {
	    return gradientHeight;
	}

	public void setGradientHeight(Integer gradientHeight) {
	    this.gradientHeight = gradientHeight;
	}

    }

}
