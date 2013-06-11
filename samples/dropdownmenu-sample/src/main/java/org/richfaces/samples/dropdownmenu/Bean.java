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

package org.richfaces.samples.dropdownmenu;

import javax.faces.event.ActionEvent;
import org.richfaces.component.UIMenuItem;

/**
 * @author $Autor$
 */
public class Bean {

    private String width = "200px";
    private String jointPoint = "auto";
    private String direction = "auto";
    private String groupDirection = "auto";
    private int verticalOffset = 10;
    private int horizontalOffset =10;
    private String verticalOffsets = "10";
    private String horizontalOffsets ="10";
    private String event ="onmouseover";

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
    
    public String getJointPoint() {
        return jointPoint;
    }

    public void setJointPoint(String jointPoint) {
        this.jointPoint = jointPoint;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getGroupDirection() {
        return groupDirection;
    }
   
    public void setHorizontalOffset(int horizontalOffset) {
        this.horizontalOffset = horizontalOffset;
    }

    public int getHorizontalOffset() {
        return horizontalOffset;
    }
    
    public void setVerticalOffset(int verticalOffset) {
        this.verticalOffset = verticalOffset;
    }

    public int getVerticalOffset() {
        return verticalOffset;
    }
    
    public void setGroupDirection(String direction) {
        this.groupDirection = direction;
    }


    
    
    
    public void setHorizontalOffsets(String horizontalOffsets) {
        this.horizontalOffsets = horizontalOffsets;
        setHorizontalOffset(new Integer(this.horizontalOffsets).intValue());        
    }

    public String getHorizontalOffsets() {
        return horizontalOffsets;
    }
    
    public void setVerticalOffsets(String verticalOffsets) {
        this.verticalOffsets = verticalOffsets;
        setVerticalOffset(new Integer(this.verticalOffsets).intValue());
    }

    public String getVerticalOffsets() {
        return verticalOffsets;
    }
    
    
    
    
    public void actionListener(ActionEvent event) {
    	System.out.println("ActionEvent on " + event.getComponent().getId() + " & Phase is "+ event.getPhaseId());
    }
    
    public String action() {
    	System.out.println("Action!!!");
    	return "null";
    }
}