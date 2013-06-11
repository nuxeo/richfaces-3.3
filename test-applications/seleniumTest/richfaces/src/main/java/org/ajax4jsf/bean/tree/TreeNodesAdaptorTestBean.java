package org.ajax4jsf.bean.tree;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

public class TreeNodesAdaptorTestBean {
	
	public static class Node {
		
		
		private String id;

		private List<Node> children;

		public Node() {
			children = new ArrayList<Node>(3);
		}
		
		public Node(String id) {
			this();
			this.id = id;
		}
				
		public void addChild(Node child) {
			children.add(child);
		}

		public void setChildren(List<Node> children) {
			this.children = children;
		}

		public List<Node> getChildren() {
			return children;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getId() {
			return id;
		}

	}

	private List<Node> nodes;
	private String trace;
	private Boolean ajaxSingle;
	private Boolean includedNode;
	private Boolean includedRoot;
	
	public TreeNodesAdaptorTestBean() {
		init();
	}
	
	public void init() {
		nodes = new ArrayList<Node>(3);
		for (int i = 1; i <= 3; i++) {
			Node node = new Node(Integer.toString(i));
			nodes.add(node);
			for (int j = 1; j <= 3; j++) {
				Node node2 = new Node(Integer.toString(i * 10 + j));
				node.addChild(node2);
				for (int k = 1; k <= 3; k++) {
					node2.addChild(new Node(Integer.toString(i * 100 + j * 10 + k)));
				}
			}
		}
		trace = "";
		ajaxSingle = false;
		includedNode = true;
		includedRoot = true;
	}

	public void submit(ActionEvent event) {
		trace = event.getComponent().getClientId(FacesContext.getCurrentInstance());
	}
	
	public void setTrace(String trace) {
		this.trace = trace;
	}

	public String getTrace() {
		return trace;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void setAjaxSingle(Boolean ajaxSingle) {
		this.ajaxSingle = ajaxSingle;
	}

	public Boolean getAjaxSingle() {
		return ajaxSingle;
	}

	public void setIncludedNode(Boolean includedNode) {
		this.includedNode = includedNode;
	}

	public Boolean getIncludedNode() {
		return includedNode;
	}

	public void setIncludedRoot(Boolean includedRoot) {
		this.includedRoot = includedRoot;
	}

	public Boolean getIncludedRoot() {
		return includedRoot;
	}
}
