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

import static org.testng.Assert.assertEquals;

import org.ajax4jsf.template.Template;
import org.richfaces.SeleniumTestBase;
import org.testng.annotations.Test;

public class JSFunctionTest extends SeleniumTestBase {

    @Test
    public void testJSFunctionComponent(Template template) {
        renderPage(template);

        String showNameElemId = getParentId() + "showname";
        selenium.mouseOver("alex");
        waitForAjaxCompletion();
        assertEquals(getTextById(showNameElemId), "Alex");

        selenium.mouseOver("jonh");
        waitForAjaxCompletion();
        assertEquals(getTextById(showNameElemId), "Jonh");

        selenium.mouseOver("roger");
        waitForAjaxCompletion();
        assertEquals(getTextById(showNameElemId), "Roger");
    }

    public String getTestUrl() {
        return "pages/jsFunction/jsFunctionTest.xhtml";
    }

}
