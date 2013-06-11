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


import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.ajax4jsf.renderkit.HeaderResourcesRendererBase;
import org.ajax4jsf.renderkit.RendererUtils.HTML;
import org.ajax4jsf.util.HtmlDimensions;
import org.ajax4jsf.util.style.CSSFormat;
import org.richfaces.component.UISeparator;
import org.richfaces.renderkit.html.images.BevelSeparatorImage;
import org.richfaces.renderkit.html.images.SimpleSeparatorImage;

public class SeparatorRendererBase extends HeaderResourcesRendererBase {
	public static final String IMAGE_CLASS_BEVEL = "rich-separator-image-bevel";
	public static final String IMAGE_CLASS_SIMPLE = "rich-separator-image-simple";
	
	public static final int DEFAULT_HEIGHT = 6;
	
	public static final String LINE_TYPE_NONE = "none";
	public static final String LINE_TYPE_BEVEL = "beveled";
	public static final String LINE_TYPE_DOUBLE = "double";
	public static final String LINE_TYPE_DOTTED = "dotted";
	public static final String LINE_TYPE_DASHED = "dashed";
	public static final String LINE_TYPE_SOLID = "solid";
	
	

    public static final String[] SUPPORTED_TYPES = {
            LINE_TYPE_BEVEL,
            LINE_TYPE_DASHED,
            LINE_TYPE_DOTTED,
            LINE_TYPE_DOUBLE,
            LINE_TYPE_SOLID
    };

    private String getCSSDimension(UIComponent component, String attributeName) {
        Object hO = component.getAttributes().get(attributeName);
        String height;
        if (hO == null) {
            return null;
        } else if (hO instanceof String) {
            height = (String) hO;
            if (height.trim().length() == 0) {
                return null;
            }
        } else {
            height = hO.toString();
        }
        if (height.trim().length() == 0) {
        	return height;
        } else {
        	return getUtils().encodePctOrPx(height);
        }
    }

    protected Class getComponentClass() {
        return UISeparator.class;
    }

    public boolean getRendersChildren() {
        return true;
    }

    protected boolean isSupportedLineType(String lineType) {
        for (int i = 0; i < SUPPORTED_TYPES.length; i++) {
            if (lineType.equalsIgnoreCase(SUPPORTED_TYPES[i])) return true;
        }
        return false;
    }

    public String backgroundImage(FacesContext context, UIComponent component) throws IOException {
        UISeparator separator = (UISeparator) component;
        String lineType = separator.getLineType();
        if (LINE_TYPE_NONE.equals(lineType)) {
            return "none";
        }
        if (lineType == null || lineType.trim().length() == 0 || !isSupportedLineType(lineType)) {
            lineType = LINE_TYPE_BEVEL;
        }
        
        int h = DEFAULT_HEIGHT;
        String height = getCSSDimension(component, HTML.height_ATTRIBUTE);
        if (null != height) {
	        if (height.trim().endsWith("%")) {
	            throw new FacesException("It is not allowed to set height of separator in percent (component " + component.getId() + ")!");
	        }
	        h = HtmlDimensions.decode(height).intValue();
        }

        //XXX by nick - fantonov - equalsIgnoreCase here?
        if (lineType.equalsIgnoreCase(LINE_TYPE_BEVEL) && h < 3) {
            lineType = LINE_TYPE_SOLID;
        }

        String uri = null;
        if (lineType.equalsIgnoreCase(LINE_TYPE_BEVEL)) {
            uri = getResource(BevelSeparatorImage.class.getName()).getUri(context, component);
        } else {
            uri = getResource(SimpleSeparatorImage.class.getName()).getUri(context, component);
        }
        
        if (uri != null) {
        	uri = CSSFormat.url(uri);
        }
        
        return uri;
    }

    public String getStyle(FacesContext context, UIComponent component) throws IOException{
    	StringBuffer buff = new StringBuffer();
    	
    	String height = getCSSDimension(component, HTML.height_ATTRIBUTE);
    	if (null != height) {
    		buff.append("height: " + height + "; ");
    	}
    	String width = getCSSDimension(component, HTML.width_ATTRIBUTE);
    	if (null != width) {
    		buff.append("width: " + width + "; ");
    	}
    	buff.append("background-image: " + backgroundImage(context, component) + "; ");
    	buff.append(component.getAttributes().get(HTML.style_ATTRIBUTE));
    	
    	return buff.toString();
	}
}
