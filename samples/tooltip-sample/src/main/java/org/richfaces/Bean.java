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

package org.richfaces;


/**
 * @author $Autor$
 *
 */
public class Bean {
	
	String text;

	String toolTipContent = "ToolTip content";
	
	String text1 = "ToolTip content1";
	String text2 = "ToolTip content2";
	String text3 = "ToolTip content3";
	boolean check = false;
	
	private int counter;
	
	public boolean getCheck() {
		return check;
	}
	
	public void toggleCheck() {
		check = !check;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getToolTipContent() {
//		toolTipContent += "12";
//		System.out.println("start - " + new Date(System.currentTimeMillis()));
//		for(int i=0;i<100000;i++){
//			double a = Math.cos(i+(Math.asin(1.334)));
//		}
//		System.out.println("end - " + new Date(System.currentTimeMillis()));
		return toolTipContent;
	}

	public void setToolTipContent(String toolTipContent) {
		this.toolTipContent = toolTipContent;
	}

	public String getText1() {
		return text1;
	}

	public void setText1(String text1) {
		this.text1 = text1;
	}

	public String getText2() {
		return text2;
	}

	public void setText2(String text2) {
		this.text2 = text2;
	}

	public String getText3() {
		return text3;
	}

	public void setText3(String text3) {
		this.text3 = text3;
	}
	
	public int getCounter() {
		return counter++;
	}
}