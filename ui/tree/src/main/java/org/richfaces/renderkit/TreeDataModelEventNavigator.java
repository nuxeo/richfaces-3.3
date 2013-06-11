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

package org.richfaces.renderkit;

import java.io.IOException;

import javax.faces.context.FacesContext;

import org.richfaces.component.UITree;
import org.richfaces.model.LastElementAware;
import org.richfaces.model.TreeRowKey;



/**
 * @author Nick Belaevski - nbelaevski@exadel.com
 * created 17.11.2006
 * 
 */
public abstract class TreeDataModelEventNavigator implements LastElementAware{
	private TreeRowKey floatingKey;
	
	protected boolean lastElement = false;
	protected boolean actualLast = false;
	protected Boolean stackedLast = null;

	private TreeRowKey rowKey;
	private UITree tree;
	
	public TreeDataModelEventNavigator(UITree tree, TreeRowKey floatingKey) {
		super();
		this.tree = tree;
		this.rowKey = floatingKey;
		this.floatingKey = floatingKey;
	}
	
	public void followRowKey(FacesContext context, TreeRowKey newRowKey) throws IOException {
		actualLast = lastElement;
		resetLastElement();
		
		if (newRowKey != null) {

			int level = this.rowKey != null ? this.rowKey.depth() : 0;

			int delta = level - newRowKey.depth();
			
			//System.out.println("TreeDataModelEventNavigator.followRowKey() " + delta);
			
			if (delta > 0) {
				beforeUp(delta);
			} else if (delta < 0) {
				if (delta == -1) {
					beforeDown();
				} else {
					throw new IllegalArgumentException("One or more nodes skipped in falldown - maybe illegal RowKey, incorrect model or model handler!");
				}
			}
			
			stackedLast = new Boolean(actualLast);

			this.tree.setRowKey(context, newRowKey);
			
			if (delta > 0) {
				afterUp(delta);
			} else if (delta == -1) {
				afterDown();
			}

		} else {
			stackedLast = null;
			
			int level = 0;
			if (this.rowKey != null) {
				level = this.rowKey.depth();
				if (floatingKey != null) {
					level -= floatingKey.depth();
				}
				
				//if (level != 0) {
					beforeUp(level);
				//}
			} else {
				beforeDown();
				afterDown();
				beforeUp(1);
				afterUp(1);
			}

			this.tree.setRowKey(context, null);

			//if (level != 0) {
				afterUp(level);
			//}
		}
		
		this.rowKey = newRowKey;
	}
	
	public abstract void beforeUp(int levels) throws IOException;
	public abstract void beforeDown() throws IOException;

	public abstract void afterUp(int levels) throws IOException;
	public abstract void afterDown() throws IOException;

	public void setLastElement() {
		lastElement = true;
	}
	
	
	public void resetLastElement() {
		lastElement = false;
	}

	protected TreeRowKey getRowKey() {
		return this.rowKey;
	}
	
	protected TreeRowKey getFloatingKey() {
		return floatingKey;
	}

	protected boolean isStackedLastElement() {
		if (stackedLast != null) {
			return stackedLast.booleanValue();
		}
		
		return false;
	}
}
