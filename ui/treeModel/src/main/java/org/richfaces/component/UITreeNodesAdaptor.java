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

package org.richfaces.component;

import org.richfaces.model.StackingTreeModel;
import org.richfaces.model.StackingTreeModelDataProvider;
import org.richfaces.model.StackingTreeModelProvider;
import org.richfaces.model.VisualStackingTreeModel;


/**
 * JSF component class
 *
 */
public abstract class UITreeNodesAdaptor extends StackingTreeModelProvider {
	
	public static final String COMPONENT_TYPE = "org.richfaces.TreeNodesAdaptor";
	
	public static final String COMPONENT_FAMILY = "org.richfaces.TreeNodesAdaptor";
	
	public abstract String getVar();
	public abstract void setVar(String var);
	
	public abstract boolean isIncludedNode();
	public abstract void setIncludedNode(boolean includeNode);
	
	protected boolean includeNode() {
		return isIncludedNode();
	}
	
	protected StackingTreeModel createStackingTreeModel() {
		StackingTreeModel treeModel = new VisualStackingTreeModel(getId(), getVar(), new StackingTreeModelDataProvider() {

			public Object getData() {
				return UITreeNodesAdaptor.this.getData();
			}
			
		}, UITreeNodesAdaptor.this) {
			protected boolean isActive() {
				return UITreeNodesAdaptor.this.isRendered();
			}
			
			protected boolean isActiveData() {
				return UITreeNodesAdaptor.this.includeNode();
			}
		};
		
		return treeModel;
	}
}
