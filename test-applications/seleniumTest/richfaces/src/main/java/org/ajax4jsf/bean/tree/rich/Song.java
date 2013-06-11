/**
 * License Agreement.
 *
 * JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */ 
package org.ajax4jsf.bean.tree.rich;

import static org.ajax4jsf.bean.tree.TreeBuilder.nextId;

import org.richfaces.model.TreeNode;
import org.richfaces.model.TreeNodeImpl;

public class Song extends TreeNodeImpl<Object> {

    /**
     * serial version ID
     */
    private static final long serialVersionUID = 3380513023368040508L;

    private long id;
    private String title;

    public Song() {
        setId(nextId());
    }

//
// TreeNode implementation
//

    public void addChild(Object identifier, TreeNode<Object> child) {
        throw new UnsupportedOperationException("Songs do not have children");
    }

    public TreeNode<Object> getChild(Object id) {
        throw new UnsupportedOperationException("Songs do not have children");
    }

    public boolean isLeaf() {
        return true;
    }

    public Object getData() {
        return this;
    }

    public void setData(Object data) {
    }

//
// Song implementation
//

    /**
     * Gets value of id field.
     * @return value of id field
     */
    public long getId() {
        return id;
    }

    /**
     * Set a new value for id field.
     * @param id a new value for id field
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets value of title field.
     * @return value of title field
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set a new value for title field.
     * @param title a new value for title field
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets value of album field.
     * @return value of album field
     */
    public Album getAlbum() {
        return (Album) getParent();
    }

    /**
     * Set a new value for album field.
     * @param album a new value for album field
     */
    public void setAlbum(Album album) {
        setAlbum(album);
    }

    public String getType() {
        return "song";
    }
}
