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

import java.util.ArrayList;
import java.util.List;

import org.richfaces.component.Dropzone;
import org.richfaces.event.DragEvent;
import org.richfaces.event.DropEvent;
import org.richfaces.event.DropListener;

/**
 * @author $Autor$
 *
 */
public class Bean {

	private List types = new ArrayList();
	
	private Object dragValue;
	
	private Object testParam;
	
	public Bean() {
		super();
		
		types.add("PHP");
		types.add("JAVA");
	}
	
	public void processDrop(DropEvent event) {
		System.out.println("Bean.processDrop()");
		this.dragValue = event.getDragValue();
	}
	
	public void processDrag(DragEvent dragEvent) {
		System.out.println("Bean.processDrag()");
	}

	public List getTypes() {
		return types;
	}
	
	public String dragAction() {
		System.out.println("Bean.dragAction()");
		return null;
	}

	public String dropAction() {
		System.out.println("Bean.dropAction()");
		return null;
	}
	
	public Object getDragValue() {
		return dragValue;
	}

	public Object getTestParam() {
		return testParam;
	}
	
	public void setTestParam(Object testParam) {
		this.testParam = testParam;
		System.out.println("Bean.setTestParam()" + testParam);
	}
}