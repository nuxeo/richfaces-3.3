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

import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.Date;

import javax.faces.context.FacesContext;

import org.ajax4jsf.resource.InternetResourceBuilder;
import org.ajax4jsf.resource.Java2Dresource;
import org.ajax4jsf.resource.PngRenderer;
import org.ajax4jsf.resource.ResourceContext;
import org.ajax4jsf.util.HtmlColor;
import org.ajax4jsf.util.Zipper2;
import org.richfaces.renderkit.html.images.GradientType;
import org.richfaces.renderkit.html.images.GradientType.BiColor;
import org.richfaces.skin.Skin;
import org.richfaces.skin.SkinFactory;

/**
 * @author Nick Belaevski - nbelaevski@exadel.com
 * created 02.02.2007
 * 
 */
public class BaseGradient extends Java2Dresource {

    private final int width;
    private final int height;
    private final int gradientHeight;
    private final String baseColor;
    private final String gradientColor;
    private final boolean horizontal;

    public BaseGradient(int width, int height, int gradientHeight, String baseColor, String gradientColor, boolean horizontal) {
	super();
	this.width = width;
	this.height = height;
	this.gradientHeight = gradientHeight;
        this.baseColor = baseColor != null ? baseColor : Skin.headerBackgroundColor;
        this.gradientColor = gradientColor != null ? gradientColor : Skin.headerGradientColor;
        this.horizontal = horizontal;

	setRenderer(new PngRenderer());
	setLastModified(new Date(InternetResourceBuilder.getInstance().getStartTime()));
    }
    
    public BaseGradient(int width, int height, int gradientHeight) {
	this(width, height, gradientHeight, null, null, false);
    }

    public BaseGradient(int width, int height, int gradientHeight, String baseColor, String gradientColor) {
	this(width, height, gradientHeight, baseColor, gradientColor, false);
    }

    public BaseGradient(int width, int height) {
	this(width, height, height);
    }

    public BaseGradient(int width, int height, String baseColor, String gradientColor) {
	this(width, height, height, baseColor, gradientColor);
    }

    public BaseGradient() {
	this(30, 50, 20);
    }

    public BaseGradient(String baseColor, String gradientColor) {
	this(30, 50, 20, baseColor, gradientColor);
    }
    
    public BaseGradient(int width, int height, int gradientHeight, boolean horizontal) {
        this(width, height, gradientHeight, null, null, horizontal);
    }
    
    public BaseGradient(int width, int height, boolean horizontal) {
        this(width, height, horizontal ? width : height, null, null, horizontal);
    }
    
    public BaseGradient(int width, int height, String baseColor, String gradientColor, boolean horizontal) {
        this(width, height, horizontal ? width : height, baseColor, gradientColor, horizontal);
    }

    public BaseGradient(boolean horizontal) {
        this(30, 50, 20, null, null, horizontal);
    }
    
    public BaseGradient(String baseColor, String gradientColor, boolean horizontal) {
        this(30, 50, 20, baseColor, gradientColor, horizontal);
    }
    
    public Dimension getDimensions(FacesContext facesContext, Object data) {
    	return new Dimension(width, height);
    }

    protected Dimension getDimensions(ResourceContext resourceContext) {
    	return new Dimension(width, height);
    }

    /**
	 * @return the gradientHeight
	 */
	protected int getGradientHeight() {
		return gradientHeight;
	}

	/**
	 * @return the baseColor
	 */
	protected String getBaseColor() {
		return baseColor;
	}

	/**
	 * @return the gradientColor
	 */
	protected String getGradientColor() {
		return gradientColor;
	}

	/**
	 * @return the horizontal
	 */
	protected boolean isHorizontal() {
		return horizontal;
	}

	protected void drawGradient(Graphics2D g2d, Shape shape, BiColor colors, int height) {
	if (colors != null) {
	    GradientPaint gragient = new GradientPaint(0, 0, colors.getTopColor(), 0, height, colors.getBottomColor());
	    g2d.setPaint(gragient);
	    g2d.fill(shape);
	}
    }

    protected void paint(ResourceContext resourceContext, Graphics2D g2d) {
	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);

	g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
	g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
	g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

	Data data = (Data) restoreData(resourceContext);
	if (data != null) {
	    paintGradient(g2d, data);
	}
    }

	/**
	 * @param g2d
	 * @param data
	 */
	protected void paintGradient(Graphics2D g2d, Data data) {
		Integer headerBackgroundColor = data.getHeaderBackgroundColor();
	    Integer headerGradientColor = data.getHeaderGradientColor();

	    if (headerBackgroundColor != null && headerGradientColor != null) {
		BiColor biColor = new GradientType.BiColor(headerBackgroundColor, headerGradientColor);

		GradientType type = data.getGradientType();
		BiColor firstLayer = type.getFirstLayerColors(biColor);
		BiColor secondLayer = type.getSecondLayerColors(biColor);

		Dimension dim = getDimensions(null, data);

		if (horizontal) {
		    //x -> y, y -> x
		    g2d.transform(new AffineTransform(0, 1, 1, 0, 0, 0));
		    dim.setSize(dim.height, dim.width);
		}
		
		int localGradientHeight = this.gradientHeight;
		if (localGradientHeight < 0) {
		    localGradientHeight = dim.height;
		}
		
		Rectangle2D rect = new Rectangle2D.Float(
			0, 
			0, 
			dim.width, 
			dim.height);

		drawGradient(g2d, rect, firstLayer, localGradientHeight);
		
		int smallGradientHeight = localGradientHeight / 2;
		
		rect = new Rectangle2D.Float(
			0, 
			0, 
			dim.width, 
			smallGradientHeight);

		drawGradient(g2d, rect, secondLayer, smallGradientHeight);
	    }
	}

    protected void restoreData(Data data, Zipper2 zipper2) {
	if (zipper2.hasMore()) {
	    data.setHeaderBackgroundColor(Integer.valueOf(zipper2.nextIntColor()));
	    data.setHeaderGradientColor(Integer.valueOf(zipper2.nextIntColor()));
	    data.setGradientType(GradientType.values()[zipper2.nextByte()]);
	}
    }

    protected Object deserializeData(byte[] objectArray) {
	Data data = createData();
	if (objectArray != null) {
	    Zipper2 zipper2 = new Zipper2(objectArray);
	    restoreData(data, zipper2);
	}

	return data;
    }

    protected Data createData() {
	return new Data();
    }

    private Integer decodeColor(String value) {
	if (value !=null && value.length() != 0) {
	    return Integer.valueOf(HtmlColor.decode(value).getRGB());
	} else {
	    return null;
	}
    }

    protected void saveData(FacesContext context, Data data, Object parameterData) {
	Integer baseIntColor = null;
	Integer headerIntColor = null;
	String gradientTypeString = null;

	if (baseIntColor == null) {
	    baseIntColor = getColorValueParameter(context, baseColor, false);
	}

	if (headerIntColor == null) {
	    headerIntColor = getColorValueParameter(context, gradientColor, false);
	}

	if (!(baseIntColor == null && headerIntColor == null)) {
	    if (baseIntColor == null) {
		baseIntColor = getColorValueParameter(context, baseColor, true);
	    }

	    if (headerIntColor == null) {
		headerIntColor = getColorValueParameter(context, gradientColor, true);
	    }
	}

	data.setHeaderBackgroundColor(baseIntColor);
	data.setHeaderGradientColor(headerIntColor);

	if (gradientTypeString == null || gradientTypeString.length() == 0) {
	    gradientTypeString = getValueParameter(context, Skin.gradientType);
	}

	data.setGradientType(GradientType.getByParameter(gradientTypeString));
    }

    protected Object getDataToStore(FacesContext context, Object data) {
	Data dataObject = createData();
	saveData(context, dataObject, data);

	return dataObject.toByteArray();
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

    protected Integer getColorValueParameter(FacesContext context, String name, boolean useDefault) {
	Skin skin;
	if (useDefault) {
	    skin = SkinFactory.getInstance().getDefaultSkin(context);
	} else {
	    skin = SkinFactory.getInstance().getSkin(context);
	}

	return decodeColor((String) skin.getParameter(context,name)); 
    }

    protected static class Data implements Serializable {
	public Data() {
	}

	/**
	 * 
	 */
	 private static final long serialVersionUID = 1732700513743861250L;
	 private Integer headerBackgroundColor;
	 private Integer headerGradientColor;
	 private GradientType gradientType;

	 public byte[] toByteArray() {
	     if (headerBackgroundColor != null && headerGradientColor != null && gradientType != null) {
		 byte[] ret = new byte[7];
		 new Zipper2(ret).addColor(headerBackgroundColor.intValue()).addColor(headerGradientColor.intValue()).
		 addByte((byte) gradientType.ordinal());
		 return ret;
	     } else {
		 return null;
	     }
	 }

	 public Integer getHeaderBackgroundColor() {
	     return headerBackgroundColor;
	 }

	 public void setHeaderBackgroundColor(Integer headerBackgroundColor) {
	     this.headerBackgroundColor = headerBackgroundColor;
	 }

	 public Integer getHeaderGradientColor() {
	     return headerGradientColor;
	 }

	 public void setHeaderGradientColor(Integer headerGradientColor) {
	     this.headerGradientColor = headerGradientColor;
	 }

	 public GradientType getGradientType() {
	     return gradientType;
	 }

	 public void setGradientType(GradientType gradientType) {
	     this.gradientType = gradientType;
	 }

    }

}
