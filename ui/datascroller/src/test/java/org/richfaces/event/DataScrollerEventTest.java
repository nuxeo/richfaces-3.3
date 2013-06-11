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

package org.richfaces.event;

import javax.faces.component.UIData;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.event.FacesListener;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;

/**
 * Unit test for DataScrollerEvent.
 */
public class DataScrollerEventTest extends AbstractAjax4JsfTestCase {

    private DataScrollerEvent event;
    private UIData data;
    private boolean result;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DataScrollerEventTest(String testName) {
        super(testName);
    }

    /* (non-Javadoc)
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#setUp()
     */
    public void setUp() throws Exception {
        super.setUp();

        data = (UIData) application.createComponent(HtmlDataTable.COMPONENT_TYPE);
        event = new DataScrollerEvent(data, "old", "new", 1);
        result = false;
    }

    /* (non-Javadoc)
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
     */
    public void tearDown() throws Exception {
        super.tearDown();
        event = null;
        data = null;
    }

    public void testEvent() throws Exception {
        assertEquals("old", event.getOldScrolVal());
        assertEquals("new", event.getNewScrolVal());
        assertFalse(event.isAppropriateListener(new TestFacesListener()));
        assertTrue(event.isAppropriateListener(new TestDataScrollerListener()));

        event.processListener(new TestDataScrollerListener());
        assertTrue(result);
    }

    class TestFacesListener implements FacesListener {

    }

    class TestDataScrollerListener implements DataScrollerListener {
        public void processScroller(DataScrollerEvent event) {
            result = true;
        }
    }
}
