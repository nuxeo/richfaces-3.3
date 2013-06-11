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
	private boolean containerRendered = true;
	
	private boolean resizeable = false;
	private boolean moveable = true;
	public boolean isResizeable() {
		return resizeable;
	}
	public void setResizeable(boolean resizeable) {
		this.resizeable = resizeable;
	}
	public boolean isMoveable() {
		return moveable;
	}
	public void setMoveable(boolean moveable) {
		this.moveable = moveable;
	}
	public boolean isContainerRendered() {
		return containerRendered;
	}
	public void setContainerRendered(boolean containerRendered) {
		this.containerRendered = containerRendered;
	}

	private int counter = 0;
	
	public int getCounter() {
		counter++;
		System.out.println(counter);
		return counter;
	}
	
	private boolean checked;
	
	public boolean isChecked() {
		return checked;
	}
	
	public void setChecked(boolean checked) {
		System.out.println("Bean.setChecked() " + checked);
		this.checked = checked;
	}
	
	private String radio;
	
	public String getRadio() {
		return radio;
	}
	
	public void setRadio(String radio) {
		System.out.println("Bean.setRadio() " + radio);
		this.radio = radio;
	}
}