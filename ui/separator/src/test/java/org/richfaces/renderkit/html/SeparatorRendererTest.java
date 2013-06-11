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

package org.richfaces.renderkit.html;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.component.UISeparator;

/**
 * Unit test for SeparatorRenderer.
 */
public class SeparatorRendererTest extends AbstractAjax4JsfTestCase {

    private SeparatorRendererBase renderer;
    private UISeparator ui;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SeparatorRendererTest(String testName) {
        super(testName);
    }

    /* (non-Javadoc)
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#setUp()
     */
    public void setUp() throws Exception {
        super.setUp();

        renderer = new SeparatorRendererBase();
        ui = (UISeparator) application.createComponent("org.richfaces.separator");
    }

    /* (non-Javadoc)
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
     */
    public void tearDown() throws Exception {
        super.tearDown();

        renderer = null;
        ui = null;
    }

    public void testSupportedTypes() throws Exception {
        for (int i = 0; i < SeparatorRendererBase.SUPPORTED_TYPES.length; i++) {
            assertEquals(true, renderer.isSupportedLineType(SeparatorRendererBase.SUPPORTED_TYPES[i]));
        }

        boolean notSupported = renderer.isSupportedLineType("UNDEFINED");
        assertFalse(notSupported);
    }

    public void testBackgroundImage() throws Exception {
        assertEquals(SeparatorRendererBase.LINE_TYPE_BEVEL, ui.getLineType());
        ui.setLineType("UNDEFINED");
        String uri = renderer.backgroundImage(facesContext, ui);
        assertTrue(uri.contains("org.richfaces.renderkit.html.images.BevelSeparatorImage"));

        ui.setLineType(SeparatorRendererBase.LINE_TYPE_BEVEL);
        uri = renderer.backgroundImage(facesContext, ui);
        assertTrue(uri.contains("org.richfaces.renderkit.html.images.BevelSeparatorImage"));

        ui.setLineType(SeparatorRendererBase.LINE_TYPE_DOTTED);
        uri = renderer.backgroundImage(facesContext, ui);
        assertTrue(uri.contains("org.richfaces.renderkit.html.images.SimpleSeparatorImage"));

        ui.setLineType(SeparatorRendererBase.LINE_TYPE_BEVEL);
        ui.setHeight("10%");
        try {
            uri = renderer.backgroundImage(facesContext, ui);
            assertTrue(false);
        } catch(Exception ex) {
        }

        ui.setLineType(SeparatorRendererBase.LINE_TYPE_BEVEL);
        ui.setHeight("2");
        assertTrue(uri.contains("org.richfaces.renderkit.html.images.SimpleSeparatorImage"));
    }
}
