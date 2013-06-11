/**
 * License Agreement.
 *
 * Rich Faces - Natural Ajax for Java Server Faces (JSF)
 *
 * Copyright (C) 2007 Exadel, Inc.
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
 * Backing bean for "\pages\pageFalse.jsp" and "\pages\pageTrue.jsp"
 * to test {@link http://jira.jboss.com/jira/browse/RF-2192} issue.
 * 
 * @author vbaranov
 */
public class MenuBean {

    /**
     * ActionListener method. Prints a string to "System.out" when invoked.
     * @param event - ActionEvent
     */
    public void item1Clicked(ActionEvent event)
    {
      System.out.println("menu item 1 clicked");
    }
    
    /**
     * ActionListener method. Prints a string to "System.out" when invoked.
     * @param event - ActionEvent
     */
    public void item2Clicked(ActionEvent event)
    {
      System.out.println("menu item 2 clicked");
    }
    
    /**
     * ActionListener method. Prints a string to "System.out" when invoked.
     * @param event - ActionEvent
     */
    public void item3Clicked(ActionEvent event)
    {
      System.out.println("menu item 3 clicked");
    }
}
