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

package org.richfaces.component;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;

import javax.faces.component.UIForm;
import javax.faces.component.html.HtmlForm;

/**
 * Unit test for UIToolBarGroup.
 */
public class UIToolBarGroupTest extends AbstractAjax4JsfTestCase {

    private UIToolBarGroup toolBarGroup;
    private UIToolBar toolBar;
    private UIForm form;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public UIToolBarGroupTest(String testName) {
        super(testName);
    }

    /* (non-Javadoc)
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#setUp()
     */
    public void setUp() throws Exception {
        super.setUp();

        toolBar = (UIToolBar)application.createComponent("org.richfaces.ToolBar");
        toolBar.setId("toolBar");

        toolBarGroup = (UIToolBarGroup)application.createComponent("org.richfaces.ToolBarGroup");

        form = new HtmlForm();
    }

    /* (non-Javadoc)
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
     */
    public void tearDown() throws Exception {
        super.tearDown();
        toolBarGroup = null;
        toolBar = null;
        form = null;
    }

    public void testGetToolBar1() throws Exception {
        
        toolBar.getChildren().add(toolBarGroup);
        assertTrue(toolBarGroup.getToolBar() instanceof UIToolBar);
    }

    public void testGetToolBar2() throws Exception {
        
        form.getChildren().add(toolBarGroup);

        try {
            toolBarGroup.getToolBar();
            assertTrue(false);
        } catch(Exception ex) {
        }
    }
}
