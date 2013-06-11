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

import java.util.Calendar;

import org.ajax4jsf.event.AjaxEvent;
import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;
import org.richfaces.event.CurrentDateChangeEvent;

/**
 * Unit test for CurrentDateChangeEvent.
 */
public class CurrentDateEventTest extends AbstractAjax4JsfTestCase {

    private CurrentDateChangeEvent event1;
    private CurrentDateChangeEvent event2;
    private CurrentDateChangeEvent event4;
    private AjaxEvent event3;
    private UICalendar calendar;   
    private Calendar cal;  

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public CurrentDateEventTest(String testName) {
        super(testName);
    }

    /* (non-Javadoc)
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#setUp()
     */
    public void setUp() throws Exception {
        super.setUp();
        
        calendar = (UICalendar) application
                .createComponent(UICalendar.COMPONENT_TYPE);
        event1 = new CurrentDateChangeEvent(calendar, "11/2001");
        cal = Calendar.getInstance();
        cal.set(2001, 10, 10);
        event2 = new CurrentDateChangeEvent(calendar, cal.getTime());
        event4 = new CurrentDateChangeEvent(calendar, "");
        event3 = new AjaxEvent(calendar);
    }

    /* (non-Javadoc)
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
     */
    public void tearDown() throws Exception {
        super.tearDown();
        event1 = null;
        event2 = null;
        calendar = null;
    }

    public void testEvent() throws Exception {
        assertEquals(cal.getTime(), event2.getCurrentDate());
        assertEquals("11/2001", event1.getCurrentDateString());        
    }
    public void testBrodcastEvent() throws Exception{
    	
 //   	calendar.broadcast(event4);
  //  	calendar.broadcast(event1);
    	calendar.broadcast(event3);
 //     	calendar.broadcast(event2);
    	
    }
 
}
