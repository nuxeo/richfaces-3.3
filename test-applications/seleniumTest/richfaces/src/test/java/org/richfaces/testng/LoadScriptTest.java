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

public class LoadScriptTest extends SeleniumTestBase {

    @Test
    public void testLoadScriptComponent(Template template) throws Exception {
        renderPage(template);
        String jsCall = "summarize(%1$s, %2$s);";
        int opr1 = 1;
        int opr2 = 2;
        String result = runScript(String.format(jsCall, opr1, opr2));
        assertEquals(Integer.parseInt(result), opr1 + opr2);
    }

    public String getTestUrl() {
        return "pages/loadScript/loadScriptTest.xhtml";
    }

}
