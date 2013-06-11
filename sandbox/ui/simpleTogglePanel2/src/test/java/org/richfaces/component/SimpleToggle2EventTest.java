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
import org.richfaces.event.ISimpleToggle2Listener;
import org.richfaces.event.SimpleToggle2Event;

import javax.faces.event.FacesListener;

/**
 * Unit test for SuggestionEvent.
 */
public class SimpleToggle2EventTest extends AbstractAjax4JsfTestCase {

    private SimpleToggle2Event event;
    private UISimpleTogglePanel2 stp;
    private boolean result;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SimpleToggle2EventTest(String testName) {
        super(testName);
    }

    /* (non-Javadoc)
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#setUp()
     */
    public void setUp() throws Exception {
        super.setUp();

        stp = (UISimpleTogglePanel2)application.createComponent("org.richfaces.SimpleTogglePanel2");
        stp.setId("simpleTogglePanel1");
        result = false;
    }

    /* (non-Javadoc)
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
     */
    public void tearDown() throws Exception {
        super.tearDown();
        event = null;
        stp = null;
    }

    public void testEvent() throws Exception {
        event = new SimpleToggle2Event(stp, false);
        assertFalse(event.isIsOpen());
        event.setIsOpen(true);
        assertTrue(event.isIsOpen());

        assertFalse(event.isAppropriateListener(new TestFacesListener()));
        assertTrue(event.isAppropriateListener(new TestSimpleToggleListener()));

        event.processListener(new TestSimpleToggleListener());
        assertTrue(result);
    }

    class TestFacesListener implements FacesListener {

    }

    class TestSimpleToggleListener implements ISimpleToggle2Listener {
        public void processToggle(SimpleToggle2Event event) {
            result = true;
        }
    }
}
