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

import java.awt.Dimension;

import javax.faces.context.FacesContext;

import org.ajax4jsf.resource.ResourceContext;
import org.ajax4jsf.util.HtmlDimensions;
import org.ajax4jsf.util.Zipper2;
import org.richfaces.renderkit.html.BaseGradient;
import org.richfaces.skin.Skin;
import org.richfaces.skin.SkinFactory;

/**
 * Created 23.02.2008
 * 
 * @author Nick Belaevski
 * @since 3.2
 */

public abstract class BaseControlBackgroundImage extends BaseGradient {

    private static final Dimension DIMENSION = new Dimension(1, 1);

    public BaseControlBackgroundImage(String baseColor, String gradientColor, int width) {
        super(width, -1, baseColor, gradientColor);
    }

    public Dimension getDimensions(FacesContext facesContext, Object data) {
        Data data2 = (Data) data;
        if (data != null) {
            return new Dimension(super.getDimensions(facesContext, data).width, data2.getHeight());
        } else {
            return DIMENSION;
        }
    }
    
    @Override
    protected Dimension getDimensions(ResourceContext resourceContext) {
    	Data data = (Data) restoreData(resourceContext);
        if (data != null) {
            return new Dimension(super.getDimensions(resourceContext).width, data.getHeight());
        } else {
            return DIMENSION;
        }
    }

    protected final Integer getHeight(FacesContext context, String heightParamName) {
        SkinFactory skinFactory = SkinFactory.getInstance();
        Skin skin = skinFactory.getSkin(context);

        String height = (String) skin.getParameter(context, heightParamName);
        if (height == null || height.length() == 0) {
            skin = skinFactory.getDefaultSkin(context);
            height = (String) skin.getParameter(context, heightParamName);
        }

        if (height != null && height.length() != 0) {
            return Integer.valueOf(HtmlDimensions.decode(height).intValue());
        } else {
            return Integer.valueOf(16);
        }
    }

    protected Integer getHeight(FacesContext context) {
        return getHeight(context, Skin.generalSizeFont);
    }


    public boolean isCacheable() {
        return true;
    }
    
    @Override
    protected org.richfaces.renderkit.html.BaseGradient.Data createData() {
        return new Data();
    }
    
    @Override
    protected void saveData(FacesContext context, org.richfaces.renderkit.html.BaseGradient.Data data, Object parameterData) {
        super.saveData(context, data, parameterData);
        Data d = ((Data) data);
        d.setGradientType(GradientType.PLAIN);
        d.setHeight(getHeight(context));
    }

    protected void restoreData(org.richfaces.renderkit.html.BaseGradient.Data data, Zipper2 zipper2) {
	if (zipper2.hasMore()) {
	    ((Data) data).setHeight(zipper2.nextInt());
	    super.restoreData(data, zipper2);
	}
    }
    
    protected static class Data extends BaseGradient.Data {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1499766195614003931L;

	private Integer height;
	
	@Override
	public byte[] toByteArray() {
            byte[] bs = super.toByteArray();
            byte[] result = new byte[(bs != null ? bs.length : 0) + 4];
	    new Zipper2(result).addInt(height).addBytes(bs);

	    return result;
	}

	public Integer getHeight() {
	    return height;
	}

	public void setHeight(Integer height) {
	    this.height = height;
	}
	
    };
}
