package tree;



import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.richfaces.model.TreeNode;

public class Library implements TreeNode {

	private Map pathways = null;
	private Object state1;

	private List listPathway;

	public Library() {
	}

	public Library(List l) {
		this.listPathway = l;
	}

	private Map getPathways() {
		if (this.pathways == null) {
			initData();
		}
		return this.pathways;
	}

	public void addPathway(Pathway pw) {
		addChild(Long.toString(pw.getId()), pw);
		pw.setParent(this);
	}

	public void addChild(Object identifier, TreeNode child) {
		getPathways().put(identifier, child);
	}

	public TreeNode getChild(Object id) {
		return (TreeNode) getPathways().get(id);
	}

	public Iterator getChildren() {
		return getPathways().entrySet().iterator();
	}

	public Object getData() {
		return this;
	}

	public TreeNode getParent() {
		return null;
	}

	public boolean isLeaf() {
		return getPathways().isEmpty();
	}

	public void removeChild(Object id) {
		getPathways().remove(id);
	}

	public void setData(Object data) {
	}

	public void setParent(TreeNode parent) {
	}

	public String getType() {
		return "library";
	}

	private long nextId = 0;

	private long getNextId() {
		return nextId++;
	}

	private Map pathCache = new HashMap();

	private Pathway getPathwayByName(String name, Library library) {
		Pathway pathway = (Pathway) pathCache.get(name);
		if (pathway == null) {
			pathway = new Pathway(getNextId());
			pathway.setName(name);
			pathCache.put(name, pathway);
			library.addPathway(pathway);
		}
		return pathway;
	}

	private void initData() {
		pathways = new HashMap();

		for (int i = 0; i < 15; i++) {
			Pathway path = getPathwayByName("PATH_" + i, this);
			for (int j = 0; j < 20; j++) {
				Organism org = new Organism(getNextId());
				org.setName("ORG_" + i + "." + j);
				path.addOrganism(org);
			}
		}

	}

	public Object getState1() {
		return state1;
	}

	public void setState1(Object state1) {
		this.state1 = state1;
	}
}