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

import org.ajax4jsf.template.Template;
import org.richfaces.AutoTester;
import org.richfaces.SeleniumTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SpacerTest extends SeleniumTestBase {

    @Test
    public void testSimpleTogglePanelComponent(Template template) {
        renderPage(template);
        String parentId = getParentId();
        String spacerId = parentId + "sp";

        writeStatus("Check width ");
        assertWidth(1, spacerId);

        writeStatus("Check height ");
        assertHeight(5, spacerId);

        writeStatus("Check styleClass ");
        assertStyleClass("sp", spacerId);

        writeStatus("Check style ");
    	Assert.assertTrue(selenium.getAttribute("xpath=id('" + spacerId + "')@style")
    			.toLowerCase().indexOf("color: yellow") != -1);
    }

    private void assertWidth(int width, String spacerId) {
        StringBuffer script = new StringBuffer(" document.getElementById('");
        script.append(spacerId);
        script.append("').offsetWidth");

        String w = runScript(script.toString());
        Assert.assertEquals(w, String.valueOf(width));
    }

    private void assertHeight(int height, String spacerId) {
        StringBuffer script = new StringBuffer(" document.getElementById('");
        script.append(spacerId);
        script.append("').offsetHeight");

        String h = runScript(script.toString());
        Assert.assertEquals(h, String.valueOf(height));
    }

    private void assertStyleClass(String styleClass, String spacerId) {
        StringBuffer script = new StringBuffer(" document.getElementById('");
        script.append(spacerId);
        script.append("').className");

        String sc = runScript(script.toString());
        if (sc != null) {
            Assert.assertTrue(sc.endsWith("sp"));
        } else {
            Assert.fail("ClassName is null");
        }
    }

    /**
     *    component with rendered = false is not present on the page,
     *    style and classes, standard HTML event attributes are output to client
     */
	@Test
	public void testRenderedAndEvents(Template template) {
    	AutoTester autoTester = getAutoTester(this);
    	autoTester.renderPage(template, null);
    	autoTester.testRendered();
    	autoTester.testHTMLEvents();
	}

	public String getTestUrl() {
        return "pages/spacer/spacerTest.xhtml";
    }
	
	@Override
	public String getAutoTestUrl() {
		return "pages/spacer/spacerAutoTest.xhtml";
	}
}
