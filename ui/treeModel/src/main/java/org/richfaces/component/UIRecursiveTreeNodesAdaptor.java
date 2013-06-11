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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.iterators.IteratorChain;
import org.richfaces.model.StackingTreeModel;
import org.richfaces.model.StackingTreeModelDataProvider;
import org.richfaces.model.VisualStackingTreeModel;


/**
 * JSF component class
 * 
 */
public abstract class UIRecursiveTreeNodesAdaptor extends UITreeNodesAdaptor {

	protected static final String FIRST = "first";
	
	protected static final String LAST = "last";

	public static final String COMPONENT_TYPE = "org.richfaces.RecursiveTreeNodesAdaptor";

	public static final String COMPONENT_FAMILY = "org.richfaces.RecursiveTreeNodesAdaptor";

	public abstract Object getRoots();
	public abstract void setRoots(Object roots);

	public abstract boolean isIncluded();
	public abstract void setIncluded(boolean active);

	public abstract boolean isIncludedRoot();
	public abstract void setIncludedRoot(boolean active);
	
	public abstract String getRecursionOrder();
	public abstract void setRecursionOrder(String recursionOrder);

	protected boolean includeNode() {
		return isIncluded() && isIncludedRoot();
	}
	
	public Object getData() {
		return getRoots();
 	}
	
	public abstract String getVar();
	public abstract void setVar(String var);
	
	protected StackingTreeModel createRecursiveModel() {
		StackingTreeModel recursiveModel = new VisualStackingTreeModel(getId(), getVar(), new StackingTreeModelDataProvider() {

			public Object getData() {
				return UIRecursiveTreeNodesAdaptor.this.getNodes();
			}
			
		}, UIRecursiveTreeNodesAdaptor.this) {

			public StackingTreeModel getModelById(String id) {
				StackingTreeModel model = super.getModelById(id);
				if (model == null) {
					model = this.getParent().getModelById(id);
				}
				
				return model;
			}

			public Iterator<StackingTreeModel> getModelsIterator() {
				IteratorChain chain = new IteratorChain();
				chain.addIterator(super.getModelsIterator());
				chain.addIterator(this.getParent().getModelsIterator());

				return chain;
			}

			protected boolean isActive() {
				return UIRecursiveTreeNodesAdaptor.this.isRendered();
			}
			
			protected boolean isActiveData() {
				return UIRecursiveTreeNodesAdaptor.this.isIncluded() && UIRecursiveTreeNodesAdaptor.this.isIncludedNode();
			}
		};
		
		return recursiveModel;
	}
	
	@Override
	protected void addChildModels(StackingTreeModel model,
			List<StackingTreeModel> childModels) {
		
		String recursionOrder = getRecursionOrder();
		if (recursionOrder == null) {
			recursionOrder = LAST;
		}

		List<StackingTreeModel> newModelsList = new ArrayList<StackingTreeModel>(childModels.size() + 1);
		boolean recursiveModelInserted = false;

		for (StackingTreeModel childModel : childModels) {
			newModelsList.add(childModel);

			if (recursionOrder.equals(childModel.getId())) {
				recursiveModelInserted = true;
				newModelsList.add(createRecursiveModel());
			}
		}
		
		if (!recursiveModelInserted) {
			if (LAST.equals(recursionOrder)) {
				newModelsList.add(createRecursiveModel());
			} else if (FIRST.equals(recursionOrder)) {
				newModelsList.add(0, createRecursiveModel());
			} else {
				throw new IllegalArgumentException("Recursion order: [" + recursionOrder + 
					"] cannot be processed by component " + this.getClientId(getFacesContext()) + "!");
			}
		}

		super.addChildModels(model, newModelsList);
	}
	
}
