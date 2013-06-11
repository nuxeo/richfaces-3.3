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

public class LoadStyleTest extends SeleniumTestBase {

    @Test
    public void testLoadStyleComponent(Template template) {
        renderPage(template);
        String divId = getParentId() + "rectangle";
        assertEquals(50, getWidthById(divId));
        assertEquals(100, getHeightById(divId));
    }

    public String getTestUrl() {
        return "pages/loadStyle/loadStyleTest.xhtml";
    }

}
