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

package org.richfaces.renderkit.html;

public class Lifo {
	
	private class Element {
		private Object content;
		private Element next;
		
		public Element(Element next, Object content) {
			super();
			this.next = next;
			this.content = content;
		}
		
	}
	
	private Element top = null;
	
	public void push(Object element) {
		top = new Element(top, element);
	}
	
	public Object pop() {
		if (top == null) {
			return null;
		}
		
		Element e = top;
		top = top.next;
		
		return e.content;
	}
	
	public Object peek() {
		if (top == null) {
			return null;
		}
		
		return top.content;
	}
	
}
