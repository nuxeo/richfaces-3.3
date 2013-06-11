package org.richfaces;

import org.richfaces.model.TreeNode;

import java.util.Iterator;
import java.util.ArrayList;

public class Organism implements TreeNode {
    private long id;
    private String name;
    private Pathway pathway;

    public Organism(long id) {
        this.id = id;
    }

    public void addChild(Object identifier, TreeNode child) {
        throw new UnsupportedOperationException("Organisms do not have children");
    }

    public TreeNode getChild(Object id) {
        throw new UnsupportedOperationException("Organisms do not have children");
    }

    public Iterator getChildren() {
        // TODO: Fix me!
        return new ArrayList().iterator(); // work around limitation for TreeNode
    }

    public Object getData() {
        return this;
    }

    public TreeNode getParent() {
        return pathway;
    }

    public boolean isLeaf() {
        return true;
    }

    public void removeChild(Object id) {
        throw new UnsupportedOperationException("Organisms do not have children");
    }

    public void setData(Object data) {
    }

    public void setParent(TreeNode parent) {
        this.pathway = (Pathway) parent;
    }

    public Pathway getPathway() {
        return pathway;
    }

    public void setPathway(Pathway artist) {
        this.pathway = artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String title) {
        this.name = title;
    }

    public long getId() {
        return id;
    }
    public String getType() {
        return "organism";
    }
}
