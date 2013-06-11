package org.richfaces;

import org.richfaces.model.TreeNode;

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

public class Pathway implements TreeNode {

    private long id;
    private Map organisms = new HashMap();
    private String name;
    private Library library;


    public Pathway(long id) {
        this.id = id;
    }

    public void addOrganism(Organism org) {
        addChild(Long.toString(org.getId()), org);
        org.setParent(this);
    }

    public void addChild(Object identifier, TreeNode child) {
        organisms.put(identifier, child);
    }

    public TreeNode getChild(Object id) {
        return (TreeNode) organisms.get(id);
    }

    public Iterator getChildren() {
        return organisms.entrySet().iterator();
    }

    public Object getData() {
        return this;
    }

    public TreeNode getParent() {
        return library;
    }

    public boolean isLeaf() {
        return organisms.isEmpty();
    }

    public void removeChild(Object id) {
        organisms.remove(id);
    }

    public void setData(Object data) {
    }

    public void setParent(TreeNode parent) {
        library = (Library) parent;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }
    public String getType() {
        return "pathway";
    }
}