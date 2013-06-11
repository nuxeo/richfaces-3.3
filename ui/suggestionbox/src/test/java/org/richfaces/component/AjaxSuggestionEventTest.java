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

/**
 * Unit test for AjaxSuggestionEvent.
 */
public class AjaxSuggestionEventTest extends AbstractAjax4JsfTestCase {

    private AjaxSuggestionEvent event1;
    private AjaxSuggestionEvent event2;
    private UISuggestionBox sb;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AjaxSuggestionEventTest(String testName) {
        super(testName);
    }

    /* (non-Javadoc)
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#setUp()
     */
    public void setUp() throws Exception {
        super.setUp();

        sb = (UISuggestionBox)application.createComponent("org.richfaces.SuggestionBox");
        event1 = new AjaxSuggestionEvent(sb);
        event2 = new AjaxSuggestionEvent(sb, "VALUE2");
    }

    /* (non-Javadoc)
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
     */
    public void tearDown() throws Exception {
        super.tearDown();
        event1 = null;
        event2 = null;
        sb = null;
    }

    public void testEvent() throws Exception {
        assertNull(event1.getSubmittedValue());
        event1.setSubmittedValue("VALUE1");
        assertEquals("VALUE1", event1.getSubmittedValue());

        assertEquals("VALUE2", event2.getSubmittedValue());
    }
}
