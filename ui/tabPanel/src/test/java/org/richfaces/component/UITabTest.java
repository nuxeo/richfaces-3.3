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

import javax.faces.FacesException;
import javax.faces.component.UIForm;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;

/**
 * Unit test for UITab.
 */
public class UITabTest extends AbstractAjax4JsfTestCase {

    private UITab tab;
    private UITab tab2;
    private UITabPanel tabPanel;
    private UIForm form;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public UITabTest(String testName) {
        super(testName);
    }

    /* (non-Javadoc)
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#setUp()
     */
    public void setUp() throws Exception {
        super.setUp();

        tab = (UITab) application.createComponent("org.richfaces.Tab");
        tab.setId("tab");

        tab2 = (UITab) application.createComponent("org.richfaces.Tab");

        tabPanel = (UITabPanel) application.createComponent("org.richfaces.TabPanel");

        form = new HtmlForm();
    }

    /* (non-Javadoc)
     * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
     */
    public void tearDown() throws Exception {
        super.tearDown();
        tab = null;
        tab2 = null;
        tabPanel = null;
        form = null;
    }

    public void testGetPane1() throws Exception {
        try {
        	tab.getPane();
        	
        	fail();
        } catch (FacesException e) {

		}

        tabPanel.getChildren().add(tab);
        assertTrue(tab.getPane() instanceof UITabPanel);
    }

    public void testGetPane2() throws Exception {
        try {
        	tab.getPane();
        	
        	fail();
        } catch (FacesException e) {

		}

        form.getChildren().add(tab);

        try {
            tab.getPane();
            assertTrue(false);
        } catch (Exception ex) {
        }
    }

    public void testEncodeTab() throws Exception {
        try {
            tab.encodeTab(null, false);
            assertTrue(false);
        } catch (NullPointerException ex) {
        }

        try {
            FakeUITab fakeTab = new FakeUITab();
            fakeTab.setRendered(false);
            fakeTab.encodeTab(facesContext, false);
        } catch (NullPointerException ex) {
            assertTrue(false);
        }
    }

    public void testSaveRestore() throws Exception {
        tab.setSwitchType("switchType");
        assertEquals("switchType", tab.getSwitchType());

        Object state = tab.saveState(facesContext);
        tab2.restoreState(facesContext, state);
        assertEquals("switchType", tab2.getSwitchType());
    }

    class FakeUITab extends UITab {
        protected Renderer getRenderer(FacesContext context) {
            throw new NullPointerException("Never executed");
        }

        public Object getValue() {
            return null;
        }

        public void setValue(Object newvalue) {
        }

        public Object getName() {
            return null;
        }

        public void setName(Object newvalue) {
        }

        public boolean isDisabled() {
            return false;
        }

        public void setDisabled(boolean disabled) {
        }

        public String getLabel() {
            return null;
        }

        public void setLabel(String newvalue) {
        }

        public String getLabelWidth() {
            return null;
        }

        public void setLabelWidth(String newvalue) {
        }

        public String getTitle() {
            return null;
        }

        public void setTitle(String newvalue) {
        }

        public void setReRender(Object targetId) {
        }

        public Object getReRender() {
            return null;
        }

        public void setStatus(String status) {
        }

        public String getStatus() {
            return null;
        }

        public void setOncomplete(String oncomplete) {
        }

        public String getOncomplete() {
            return null;
        }

        public void setData(Object data) {
        }

        public Object getData() {
            return null;
        }

        public void setLimitToList(boolean submitForm) {
        }

        public boolean isLimitToList() {
            return false;
        }

        public void setAjaxSingle(boolean single) {
        }

        public boolean isAjaxSingle() {
            return false;
        }

        public boolean isBypassUpdates() {
            return false;
        }

        public void setBypassUpdates(boolean bypass) {
        }

        public String getEventsQueue() {
            return null;
        }

        public void setIgnoreDupResponses(boolean newvalue) {
        }

        public boolean isIgnoreDupResponses() {
            return false;
        }

        public void setEventsQueue(String newvalue) {
        }

        public int getRequestDelay() {
            return 0;
        }

        public void setRequestDelay(int newvalue) {
        }

		public String getSimilarityGroupingId() {
			return null;
		}

		public void setSimilarityGroupingId(String similarityGroupingId) {
		}

        public int getTimeout() {
            return 0;
        }

        public void setTimeout(int timeout) {
        }

		public String getFocus() {
			return null;
		}

		public void setFocus(String arg0) {
		}

		public String getSwitchType() {
			return null;
		}

		public void setSwitchType(String newvalue) {
		}

		public String getOnbeforedomupdate() {
			return null;
		}

		public void setOnbeforedomupdate(String beforeUpdate) {
		}

		public Object getProcess() {
			// TODO Auto-generated method stub
			return null;
		}

		public void setProcess(Object targetId) {
			// TODO Auto-generated method stub
			
		}
    }
}
