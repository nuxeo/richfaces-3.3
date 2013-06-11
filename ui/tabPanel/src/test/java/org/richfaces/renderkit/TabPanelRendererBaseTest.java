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

package org.richfaces.renderkit;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;

/**
 * Unit test for TabPanelRenderer.
 */
public class TabPanelRendererBaseTest extends AbstractAjax4JsfTestCase {
    private static String STYLE = "test1:1;test2:2; test3: 3; test4 :4;test5 : 5; test6:6; test7:;test8; test9";

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TabPanelRendererBaseTest(String testName) {
        super(testName);
    }

    /* (non-Javadoc)
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#setUp()
     */
    public void setUp() throws Exception {
        super.setUp();
    }

    /* (non-Javadoc)
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
     */
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testParameterPresent() throws Exception {
        assertFalse(TabPanelRendererBase.parameterPresent(null, "test0"));
        assertTrue(TabPanelRendererBase.parameterPresent(STYLE, "test1"));
        assertTrue(TabPanelRendererBase.parameterPresent(STYLE, "test2"));
        assertTrue(TabPanelRendererBase.parameterPresent(STYLE, "test3"));
        assertTrue(TabPanelRendererBase.parameterPresent(STYLE, "test4"));
        assertTrue(TabPanelRendererBase.parameterPresent(STYLE, "test5"));
        assertTrue(TabPanelRendererBase.parameterPresent(STYLE, "test6"));
        assertTrue(TabPanelRendererBase.parameterPresent(STYLE, "test7"));
        assertTrue(TabPanelRendererBase.parameterPresent(STYLE, "test8"));
        assertTrue(TabPanelRendererBase.parameterPresent(STYLE, "test9"));
    }
}
