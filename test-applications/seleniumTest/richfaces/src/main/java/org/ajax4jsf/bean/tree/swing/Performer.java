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

public class Performer extends DefaultMutableTreeNode {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1721901324378505571L;
	
	private String name;

    /**
     * Gets value of library field.
     * @return value of library field
     */
    public AudioLibrary getLibrary() {
        return (AudioLibrary) getParent();
    }

    /**
     * Set a new value for library field.
     * @param library a new value for library field
     */
    public void setLibrary(AudioLibrary library) {
        setParent(library);
    }

    /**
     * Gets value of name field.
     * @return value of name field
     */
    public String getName() {
        return name;
    }

    /**
     * Set a new value for name field.
     * @param name a new value for name field
     */
    public void setName(String name) {
        this.name = name;
    }

    public void addAlbum(Album album) {
        add(album);
    }

    public String getType() {
        return "performer";
    }
}
