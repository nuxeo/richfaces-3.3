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

package org.richfaces;
/**
 * @author $Autor$
 *
 */
public class Bean {
	
	private String width = "100%";	
	private String label = "Content";
	private String separator_type = "line";
	private String style = "border: 3px solid red;";
	private String contentStyle = "color: red; font-size: 20px; font-family: times;";
	private String height = "40px";
	
	public String getContentStyle() {
		return contentStyle;
	}
	public void setContentStyle(String contentStyle) {
		this.contentStyle = contentStyle;
	}	
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getSeparator_type() {
		return separator_type;
	}
	public void setSeparator_type(String separator_type) {
		this.separator_type = separator_type;
	}
	
}