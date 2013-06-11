/**
 * License Agreement.
 *
 * JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */ 
package org.richfaces.testng;

import junit.framework.Assert;

import org.ajax4jsf.template.Template;
import org.richfaces.SeleniumTestBase;
import org.testng.annotations.Test;

public class AjaxLoadBundleTest extends SeleniumTestBase {
    private static final String URL = "pages/loadBundle/loadBundle.xhtml";

    private static final String BUTTON_ID = "b1";

    private static final String SAMPLE_MESSAGE = "Error was occured.";

    private final static String FORM_ID = "form:";

    private final static String FIRST_LINK_ID = FORM_ID + "error";

    private final static String SECOND_LINK_ID = FORM_ID + "_link";

    /**
     * @see org.richfaces.SeleniumTestBase#getTestUrl()
     */
    @Override
    public String getTestUrl() {
        return URL;
    }

    @Test
    public void testAjaxLoadBundleComponent(Template template) throws Exception {
        renderPage(template);
        writeStatus("Testing loadBundle component");
        String textId = getParentId() + FIRST_LINK_ID;
        AssertPresent(textId);
        AssertNotPresent(getParentId() + SECOND_LINK_ID);
        Assert.assertEquals(getTextById(textId), SAMPLE_MESSAGE);
    }

    @Test
    public void testAjaxReRender(Template template) throws Exception {
        renderPage(template);
        writeStatus("Testing loadBundle component after ajax rerender");
        String buttonId = getParentId() + FORM_ID + BUTTON_ID;
        clickById(buttonId);
        waitForAjaxCompletion();
        AssertPresent(getParentId() + SECOND_LINK_ID);
    }
}
