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
package org.ajax4jsf.resource;

import java.util.Arrays;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;

public class ParametersEncodingTestCase extends AbstractAjax4JsfTestCase {

    private ResourceBuilderImpl builder;

    private byte[] data = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 };

    public ParametersEncodingTestCase(String arg0) {
	super(arg0);
    }

    public void setUp() throws Exception {
	super.setUp();
	builder = new ResourceBuilderImpl();
    }

    public void tearDown() throws Exception {
	builder = null;
	super.tearDown();
    }

    public final void testEncrypt() {
	byte[] bs = builder.encrypt(data);
	byte[] bs2 = builder.decrypt(bs);
	assertTrue(Arrays.equals(data, bs2));
    }

    public final void testDecryptLeak() {
	byte[] bs = {};
	for (int i = 0; i < 10000; i++) {
	    bs = builder.encrypt(data);

	}
	byte[] bs2={};
	for (int i = 0; i < 10000; i++) {
	    bs2 = builder.decrypt(bs);

	}
	assertTrue(Arrays.equals(data, bs2));
    }

}
