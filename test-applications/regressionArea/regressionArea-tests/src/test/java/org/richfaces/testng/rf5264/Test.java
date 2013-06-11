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


package org.richfaces.testng.rf5264;

import org.ajax4jsf.javascript.JSFunction;
import org.richfaces.SeleniumTestBase;

/**
 * @author Nick Belaevski
 * @since 3.3.0
 */
public class Test extends SeleniumTestBase {

	private static final String PROGRESS_BAR_ID = "form:progressBar";
	private static final String PROGRESS_BAR_TEST_ELEMENT_ID = PROGRESS_BAR_ID + ":upload";

	private static final String PROGRESS_BAR_INITIAL_ELEMENT_ID = PROGRESS_BAR_ID + ":initialState";
	private static final String PROGRESS_BAR_COMPLETE_ELEMENT_ID = PROGRESS_BAR_ID + ":completeState";
	
	@org.testng.annotations.Test
	public void testBeyondMinimum() throws Exception {
		renderPage();
		
		assertStyleAttribute(PROGRESS_BAR_TEST_ELEMENT_ID, "width: 50%;");
		AssertNotVisible(PROGRESS_BAR_INITIAL_ELEMENT_ID);
		AssertNotVisible(PROGRESS_BAR_COMPLETE_ELEMENT_ID);

		callComponentOperation(PROGRESS_BAR_ID, new JSFunction("setValue", 0));
		AssertVisible(PROGRESS_BAR_INITIAL_ELEMENT_ID);
		AssertNotVisible(PROGRESS_BAR_COMPLETE_ELEMENT_ID);
		AssertNotVisible(PROGRESS_BAR_TEST_ELEMENT_ID);
	}
	
	@org.testng.annotations.Test
	public void testAboveMaximum() throws Exception {
		renderPage();
		
		assertStyleAttribute(PROGRESS_BAR_TEST_ELEMENT_ID, "width: 50%;");
		AssertNotVisible(PROGRESS_BAR_INITIAL_ELEMENT_ID);
		AssertNotVisible(PROGRESS_BAR_COMPLETE_ELEMENT_ID);

		callComponentOperation(PROGRESS_BAR_ID, new JSFunction("setValue", 1000));
		AssertVisible(PROGRESS_BAR_COMPLETE_ELEMENT_ID);
		AssertNotVisible(PROGRESS_BAR_INITIAL_ELEMENT_ID);
		AssertNotVisible(PROGRESS_BAR_TEST_ELEMENT_ID);
	}

	@Override
	public String getTestUrl() {
		return "pages/rf5264.xhtml";
	}

	
}
