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
package org.richfaces.component;

import java.util.LinkedHashSet;
import java.util.Set;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;

/**
 * @author Nick Belaevski
 *         mailto:nbelaevski@exadel.com
 *         created 20.06.2008
 *
 */
public class HotKeyComponentScriptsTest extends AbstractAjax4JsfTestCase {

	public HotKeyComponentScriptsTest(String name) {
		super(name);
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		facesContext.getViewRoot().getChildren().add(
				application.createComponent(UIHotKey.COMPONENT_TYPE));
	}
	
	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	private static final String[] SCRIPTS = new String[] {
		"org/richfaces/renderkit/html/scripts/jquery/jquery.js", 
		"org/richfaces/renderkit/html/scripts/jquery.hotkeys.js",
		"org/richfaces/renderkit/html/scripts/hotKey.js"
	};
	
	public void testRegisteredScripts() throws Exception {
		setupWebClient();
		for (String scriptName : SCRIPTS) {
			getResourceIfPresent(scriptName);
		}
	}
	
	
	public void testScripts() throws Exception {
		Set<String> scripts = new LinkedHashSet<String>();
		for (String scriptName : SCRIPTS) {
			scripts.add(scriptName);
		}

		assertEquals(scripts.size(), getCountValidScripts(renderView(), scripts, false).intValue());
	}
}
