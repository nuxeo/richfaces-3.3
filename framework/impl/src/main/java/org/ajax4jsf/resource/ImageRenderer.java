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

package org.ajax4jsf.resource;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.ajax4jsf.resource.InternetResource;
import org.ajax4jsf.resource.ResourceContext;


/**
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:57:06 $
 *
 */
public abstract class ImageRenderer extends BaseResourceRenderer {

	private static final String _tag = "img";
	private static final String _hrefAttr = "src";
	private static final String[][] _commonAttrs = {
			
	};
	/**
	 * @return Returns the commonAttrs.
	 */
	protected String[][] getCommonAttrs() {
		return null ; //_commonAttrs;
	}

	/**
	 * @return Returns the hrefAttr.
	 */
	protected String getHrefAttr() {
		return _hrefAttr;
	}

	/**
	 * @return Returns the tag.
	 */
	protected String getTag() {
		return _tag;
	}

	/**
	 * Send {@link BufferedImage} to response in concrete format (GIF,Jpeg,PNG)
	 * @param context - current faces context.
	 * @param image - imaje to send
	 */
	public abstract void sendImage(ResourceContext context,RenderedImage image) throws IOException;
	
	/**
	 * @return type of image for create {@link BufferedImage}
	 */
	public abstract int getImageType();
	
	/**
	 * Create blank {@link BufferedImage} for paint.
	 * Type of image determined by current renderer.
	 * @param width
	 * @param height
	 * @return new image
	 */
	public BufferedImage createImage(int width,int height){
		return new BufferedImage(width,height,getImageType());	
	}
	
	protected void customEncode(InternetResource resource, FacesContext context, Object data) throws IOException {
		if (resource instanceof Java2Dresource) {
			Java2Dresource j2d = (Java2Dresource) resource;
			Dimension dim = j2d.getDimensions(context, data);
			ResponseWriter writer = context.getResponseWriter();
			writer.writeAttribute("width", String.valueOf(dim.width), "width");
			writer.writeAttribute("height", String.valueOf(dim.height), "height");
		}
	}
}
