/**
 * License Agreement.
 *
 * Rich Faces - Natural Ajax for Java Server Faces (JSF)
 *
 * Copyright (C) 2007 Exadel, Inc.
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


package org.richfaces.testng.rf5849;

import org.richfaces.SeleniumTestBase;

/**
 * @author Nick Belaevski
 * @since 3.3.1
 */
public class Test extends SeleniumTestBase {

	private static final String STATUS = "status.";
	
	private static final String STATUS_START = STATUS + "start";
	
	private static final String STATUS_STOP = STATUS + "stop";

	@org.testng.annotations.Test
	public void testExecute() throws Exception {
		renderPage();
		
		AssertNotVisible(STATUS_START);
		AssertVisible(STATUS_STOP);
		
		clickById("execute");

		AssertNotVisible(STATUS_STOP);
		AssertVisible(STATUS_START);

		waitForAjaxCompletion(10000);
		
		AssertValueEquals("form:text", "3");

		AssertNotVisible(STATUS_START);
		AssertVisible(STATUS_STOP);
	}
	
	
	@Override
	public String getTestUrl() {
		return "pages/rf5849.xhtml";
	}
}
