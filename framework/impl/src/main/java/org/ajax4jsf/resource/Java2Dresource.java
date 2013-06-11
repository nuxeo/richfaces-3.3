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
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;

import org.ajax4jsf.Messages;
import org.ajax4jsf.resource.ResourceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author shura (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.3 $ $Date: 2007/02/01 15:31:57 $
 * 
 */
public class Java2Dresource extends InternetResourceBase {
	private static final Log log = LogFactory.getLog(Java2Dresource.class);

	/**
	 * Primary calculation of image dimensions - used when HTML code is
	 * generated to render IMG's width and height Subclasses should override
	 * this method to provide correct sizes of rendered images
	 * 
	 * @param facesContext
	 * @return dimensions of the image to be displayed on page
	 */
	public Dimension getDimensions(FacesContext facesContext, Object data) {
		return new Dimension(1, 1);
	}

	/**
	 * Secondary calculation is used basically by {@link getImage} method
	 * 
	 * @param resourceContext
	 * @return
	 */
	protected Dimension getDimensions(ResourceContext resourceContext) {
		return new Dimension(1, 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.resource.InternetResourceBase#getResourceAsStream(javax.faces.context.FacesContext,
	 *      java.lang.Object)
	 */
	public InputStream getResourceAsStream(ResourceContext context) {
		// TODO Auto-generated method stub
		return super.getResourceAsStream(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.resource.InternetResourceBase#send(javax.faces.context.FacesContext,
	 *      java.lang.Object)
	 */
	public void send(ResourceContext context) throws IOException {
		ImageRenderer renderer = (ImageRenderer) getRenderer(null);
		try {
			RenderedImage image = getImage(context);
			if (null != image) {
				renderer.sendImage(context, image);

			}
		} catch (Exception e) {
			throw new FacesException(Messages
					.getMessage(Messages.SEND_IMAGE_ERROR_2), e);
		}
	}

	/**
	 * Create {@link RenderedImage} for send to client. can be used as Java2d or
	 * Java Advanced Imaging.
	 * 
	 * @param context
	 * @return image to send.
	 */
	protected RenderedImage getImage(ResourceContext context) {
		ImageRenderer renderer = (ImageRenderer) getRenderer(null);
		Dimension imageDimensions = getDimensions(context);
		BufferedImage image = null;
		if (imageDimensions.getHeight() > 0.0
				&& imageDimensions.getWidth() > 0.0) {
			image = renderer.createImage(imageDimensions.width,
					imageDimensions.height);
			Graphics2D graphics = image.createGraphics();
			paint(context, graphics);
			graphics.dispose();

		}
		return image;
	}

	/**
	 * Template method for create image as Applet-like paint.
	 * 
	 * @param context -
	 *            current resource context.
	 * @param graphics2D -
	 *            graphics to paint.
	 */
	protected void paint(ResourceContext context, Graphics2D graphics2D) {
		// TODO Auto-generated method stub

	}
}
