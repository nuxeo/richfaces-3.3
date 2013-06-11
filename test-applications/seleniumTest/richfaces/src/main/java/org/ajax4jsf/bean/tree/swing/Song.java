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
package org.ajax4jsf.bean.tree.swing;

import javax.swing.tree.DefaultMutableTreeNode;

public class Song extends DefaultMutableTreeNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6994524192598771802L;
	
	private String title;

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
        setParent(album);
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

    public String getType() {
        return "song";
    }
}
