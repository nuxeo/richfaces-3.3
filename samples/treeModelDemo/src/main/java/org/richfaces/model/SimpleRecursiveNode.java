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

package org.richfaces.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <br /><br />
 * 
 * Created 22.12.2007
 * @author Nick Belaevski
 * @since 3.2
 */

public class SimpleRecursiveNode {

	private SimpleRecursiveNode parent;
	
	private List children = new ArrayList();

	private String text;

	public SimpleRecursiveNode(SimpleRecursiveNode parent,
			String text) {
		super();
		this.parent = parent;
		if (parent != null) {
			parent.addChild(this);
		}
		this.text = text;
	}
	
	public void addChild(SimpleRecursiveNode node) {
		children.add(node);
	}

	public void removeChild(SimpleRecursiveNode node) {
		children.remove(node);
	}

	public void remove() {
		if (parent != null) {
			parent.removeChild(this);
		}
	}
	
	public SimpleRecursiveNode getParent() {
		return parent;
	}
	
	public List getChildren() {
		return children;
	}
	
	public String getText() {
		return text;
	}
}
