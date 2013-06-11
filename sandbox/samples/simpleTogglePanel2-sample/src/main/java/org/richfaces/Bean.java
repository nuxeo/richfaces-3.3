/**
 * License Agreement.
 *
 *  JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007  Exadel, Inc.
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

package org.richfaces;

import javax.faces.event.ActionEvent;


/**
 * @author $Autor$
 */
public class Bean {

    private String textValue1 = "Click here to switch panel.";
    private String canon = "First toggle panel (Client switch type).";
    private String nikon = "Second toggle panel (Ajax switch type).";
    private String olympus = "Third toggle panel (Server switch type).";
    private String width = "80%";
    private String height = "150px";
    private String style = "width: 50%; border: 2px solid gold;";
    
    
    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
    
    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
    
    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getTextValue1() {
        return textValue1;
    }

    public void setTextValue1(String textValue1) {
        this.textValue1 = textValue1;
    }

    public String getCanon() {
        return canon;
    }

    public void setCanon(String canon) {
        this.canon = canon;
    }

    public String getNikon() {
        return nikon;
    }

    public void setNikon(String nikon) {
        this.nikon = nikon;
    }

    public String getOlympus() {
        return olympus;
    }

    public void setOlympus(String olympus) {
        this.olympus = olympus;
    }

    public String action() {
    	System.out.println("Action!!!");
    	return "null";
    }

    public void actionListener(ActionEvent event) {
    	System.out.println("ActionEvent on " + event.getComponent().getId() + " & Phase is "+ event.getPhaseId());
    }


}