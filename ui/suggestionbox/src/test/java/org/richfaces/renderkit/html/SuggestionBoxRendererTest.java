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
import org.richfaces.component.UISuggestionBox;

/**
 * Unit test for SuggestionBox renderer.
 */
public class SuggestionBoxRendererTest extends AbstractAjax4JsfTestCase {

    private SuggestionBoxRenderer renderer;
    private UISuggestionBox sb;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SuggestionBoxRendererTest(String testName) {
        super(testName);
    }

    /* (non-Javadoc)
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#setUp()
     */
    public void setUp() throws Exception {
        super.setUp();

        renderer = new SuggestionBoxRenderer();
        sb = (UISuggestionBox)application.createComponent("org.richfaces.SuggestionBox");
    }

    /* (non-Javadoc)
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
     */
    public void tearDown() throws Exception {
        super.tearDown();
        renderer = null;
    }

    public void testOpacityStyle() throws Exception {
        String opacity = renderer.opacityStyle(facesContext, sb);
        assertEquals("opacity:0.1; filter:alpha(opacity=10);", opacity);

        sb.getAttributes().put("shadowOpacity", "5");
        opacity = renderer.opacityStyle(facesContext, sb);
        assertEquals("opacity:0.5; filter:alpha(opacity=50);", opacity);
    }

    public void testBorder() throws Exception {
        String border = renderer.border(facesContext, sb);
        assertEquals("; border-width: null  null  null  null ;", border);

        sb.getAttributes().put("border", "3");
        border = renderer.border(facesContext, sb);
        assertEquals("; border-width: 3px  3px  3px  3px ;", border);
    }

    public void testBgcolor() throws Exception {
        String bgcolor = renderer.bgcolor(facesContext, sb);
        assertEquals(";", bgcolor);

        sb.getAttributes().put("bgcolor", "red");
        bgcolor = renderer.bgcolor(facesContext, sb);
        assertEquals("background-color: red;", bgcolor);
    }

    public void testCellPadding() throws Exception {
        String cellPadding = renderer.cellPadding(facesContext, sb);
        assertEquals(";", cellPadding);

        sb.setCellpadding("10");
        cellPadding = renderer.cellPadding(facesContext, sb);
        assertEquals("padding: 10px;", cellPadding);
    }

    public void testContentId() throws Exception {
        sb.setId("suggestionBox");
        String contentId = renderer.getContentId(facesContext, sb);
        assertEquals("suggestionBox:suggest", contentId);
    }

    public void testOverflowSize() throws Exception {
        String overflowSize = renderer.overflowSize(facesContext, sb);
        assertEquals("width:196px;height:196px;", overflowSize);
    }

    public void testShadowDepth() throws Exception {
        String shadowDepth = renderer.shadowDepth(facesContext, sb);
        assertEquals("top: 4px; left: 4px; ", shadowDepth);

        sb.getAttributes().put("shadowDepth", "2");
        shadowDepth = renderer.shadowDepth(facesContext, sb);
        assertEquals("top: 2px; left: 2px; ", shadowDepth);
    }
}
