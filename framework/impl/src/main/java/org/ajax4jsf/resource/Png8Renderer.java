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

import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.ajax4jsf.resource.ResourceContext;

/**
 * @author shura (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:57:04 $
 * 
 */
public class Png8Renderer extends ImageRenderer {

	/**
	 * Default web safe colors color model
	 */
	public static IndexColorModel webColorModel;

	// Build web safe 6x6x6 cube color model.
	static {
		byte[] webLevels = { 0, 51, 102, (byte) 153, (byte) 204, (byte) 255 };
		byte[] r = new byte[256];
		byte[] g = new byte[256];
		byte[] b = new byte[256];
		r[0] = g[0] = b[0] = 0;
		for (int i = 0; i < webLevels.length; i++) {
			for (int j = 0; j < webLevels.length; j++) {
				for (int k = 0; k < webLevels.length; k++) {
					int colorNum = i * webLevels.length * webLevels.length + j
							* webLevels.length + k + 1;
					r[colorNum] = webLevels[i];
					g[colorNum] = webLevels[j];
					b[colorNum] = webLevels[k];
				}
			}
		}
		webColorModel = new IndexColorModel(8, webLevels.length
				* webLevels.length * webLevels.length + 1, r, g, b, 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.resource.ImageRenderer#sendImage(org.ajax4jsf.resource.ResourceContext,
	 *      java.awt.image.RenderedImage)
	 */
	public void sendImage(ResourceContext context, RenderedImage image)
			throws IOException {
		OutputStream outputStream = context.getOutputStream();
		// PNGEncodeParam param = new PNGEncodeParam.Palette();
		// param.setBitDepth(8);
		// param.setInterlacing(true);
		// String[] encoders = ImageCodec.getEncoderNames(image,null);
		// ImageEncoder encoder =
		// ImageCodec.createImageEncoder("png",context.getOutputStream(),param);
		// encoder.encode(image);
		try {
			ImageIO.write(image, "png", outputStream);
			outputStream.flush();
			outputStream.close();

		} catch (Exception e) {
			// IE can unexpected close connection
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.resource.ImageRenderer#getImageType()
	 */
	public int getImageType() {
		return BufferedImage.TYPE_BYTE_INDEXED;
		// return BufferedImage.TYPE_INT_ARGB;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.resource.ImageRenderer#createImage(int, int)
	 */
	public BufferedImage createImage(int width, int height) {

		// return new BufferedImage(width,height,getImageType());//,colorModel);
		return new BufferedImage(width, height, getImageType(), webColorModel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.resource.ResourceRenderer#getContentType()
	 */
	public String getContentType() {
		// TODO Auto-generated method stub
		return "image/png";
	}

}
