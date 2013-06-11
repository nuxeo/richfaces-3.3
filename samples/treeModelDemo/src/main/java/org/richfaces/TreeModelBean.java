/**
 * 
 */
package org.richfaces;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.richfaces.component.UITree;
import org.richfaces.component.html.HtmlRecursiveTreeNodesAdaptor;
import org.richfaces.model.SimpleRecursiveNode;
import org.richfaces.model.StackingTreeModel;

/**
 * @author Nick Belaevski
 *         mailto:nbelaevski@exadel.com
 *         created 25.07.2007
 *
 */
public class TreeModelBean {
	
	private HtmlRecursiveTreeNodesAdaptor binding = new MyHtmlRecursiveTreeNodesAdaptor();
	
	public HtmlRecursiveTreeNodesAdaptor getBinding() {
		return binding;
	}
	
	public void setBinding(HtmlRecursiveTreeNodesAdaptor binding) {
		this.binding = binding;
	}
	
	public Boolean adviseNodeOpened(UITree tree) {
		return Boolean.TRUE;
	}

	private SimpleRecursiveNode simpleRecursiveNode;
	
	public static class MyHtmlRecursiveTreeNodesAdaptor extends HtmlRecursiveTreeNodesAdaptor {
		@Override
		public StackingTreeModel getStackingModel() {
			StackingTreeModel stackingModel = super.getStackingModel();
			
//			Iterator iterator = stackingModel.getModelsIterator();
//			List<StackingTreeModel> list = new ArrayList<StackingTreeModel>();
//			while (iterator.hasNext()) {
//				list.add((StackingTreeModel) iterator.next());
//				if (iterator.hasNext()) {
//					iterator.remove();
//				}
//			}
//			
//			for (StackingTreeModel stackingTreeModel : list) {
//				stackingModel.addStackingModel(stackingTreeModel);
//			}
			return stackingModel;
		}
	};
	
	public TreeModelBean() {
		simpleRecursiveNode = new SimpleRecursiveNode(null, null);
		for (int i = 0; i < 5; i++) {
			SimpleRecursiveNode node2 = new SimpleRecursiveNode(simpleRecursiveNode, "Node " + i);
		
			for (int j = 0; j < 6; j++) {
				SimpleRecursiveNode node3 = new SimpleRecursiveNode(node2, "Node " + i + " " + j);
			
				for (int k = 0; k < 4; k++) {
					SimpleRecursiveNode node4 = new SimpleRecursiveNode(node3, "Node " + i + " " + j + " " + k);
				}
			}
		}
	}
	
	public List getSimpleResursiveNodes() {
		return simpleRecursiveNode.getChildren();
	}
}
