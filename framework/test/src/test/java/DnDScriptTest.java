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

import java.io.IOException;

import org.ajax4jsf.javascript.DnDScript;
import org.ajax4jsf.resource.FacesResourceContext;
import org.ajax4jsf.resource.ResourceContext;
import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;

/**
 * 
 */

/**
 * @author shura
 *
 */
public class DnDScriptTest extends AbstractAjax4JsfTestCase {

	/**
	 * @param name
	 */
	public DnDScriptTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#setUp()
	 */
	public void setUp() throws Exception {
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.tests.AbstractAjax4JsfTestCase#tearDown()
	 */
	public void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link org.ajax4jsf.resource.InternetResourceBase#send(org.ajax4jsf.resource.ResourceContext)}.
	 */
	public void testSend() {
		DnDScript resource = new DnDScript();
        ResourceContext context = new FacesResourceContext(facesContext);
        try {
			resource.send(context);
		} catch (IOException e) {
			e.printStackTrace();
			assertTrue("error send style",false);
		}
	}

}
