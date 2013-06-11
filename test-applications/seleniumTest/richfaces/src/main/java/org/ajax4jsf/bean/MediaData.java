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
import java.io.Serializable;

public class MediaData implements Serializable {

    private static final long serialVersionUID = 1L;

    Integer width;

    Integer height;

    Color background;

    Color drawColor;

    public MediaData() {
	this.width = 100;
	this.height = 100;
	background = Color.BLACK;
	drawColor = Color.WHITE;
    }

    public MediaData(Integer width, Integer height, Color background, Color drawColor) {
	this.width = width;
	this.height = height;
	this.background = background;
	this.drawColor = drawColor;
    }

    /**
     * @return the width
     */
    public Integer getWidth() {
	return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(Integer width) {
	this.width = width;
    }

    /**
     * @return the height
     */
    public Integer getHeight() {
	return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(Integer height) {
	this.height = height;
    }

    /**
     * @return the background
     */
    public Color getBackground() {
	return background;
    }

    /**
     * @param background the background to set
     */
    public void setBackground(Color background) {
	this.background = background;
    }

    /**
     * @return the drawColor
     */
    public Color getDrawColor() {
	return drawColor;
    }

    /**
     * @param drawColor the drawColor to set
     */
    public void setDrawColor(Color drawColor) {
	this.drawColor = drawColor;
    }

}