/**
 * License Agreement.
 *
 * JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */ 
package org.ajax4jsf.bean;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

public class MediaBean implements Media {

    private MediaData data = new MediaData();    
    private MediaData data2 = new MediaData(50, 10, Color.BLACK, Color.WHITE);

    
    /**
     * @return the data2
     */
    public MediaData getData2() {
        return data2;
    }

    /**
     * @param data2 the data2 to set
     */
    public void setData2(MediaData data2) {
        this.data2 = data2;
    }

    /**
     * @return the data
     */
    public MediaData getData() {
	return data;
    }

    /**
     * @param data
     *                the data to set
     */
    public void setData(MediaData data) {
	this.data = data;
    }

    public String reInitData() {
	data = new MediaData(200, 200, Color.WHITE, Color.BLACK);
	return null;
    }
    
    public String resetData() {
	data = new MediaData();
	return null;
    }

    public void paint(OutputStream out, Object data) throws IOException {
	if (data instanceof MediaData) {

	    MediaData paintData = (MediaData) data;
	    BufferedImage img = new BufferedImage(paintData.getWidth(), paintData.getHeight(), BufferedImage.TYPE_INT_RGB);
	    Graphics2D graphics2D = img.createGraphics();
	    graphics2D.setBackground(paintData.getBackground());
	    graphics2D.setColor(paintData.getDrawColor());
	    graphics2D.clearRect(0, 0, paintData.getWidth(), paintData.getHeight());
	    graphics2D.drawChars(new String("mediaOutput").toCharArray(), 0, 11, 5, 35);
	    graphics2D.drawChars(new String("selenium test").toCharArray(), 0, 13, 5, 55);

	    ImageIO.write(img, "jpeg", out);

	}
    }
}