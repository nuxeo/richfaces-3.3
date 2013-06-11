/**
 * License Agreement.
 *
 *  JBoss RichFaces - Ajax4jsf Component Library
 *
 * Copyright (C) 2007  Exadel, Inc.
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

package org.ajax4jsf.util;

import java.awt.Color;
import java.util.Arrays;

import junit.framework.TestCase;

/**
 * 
 * <br /><br />
 * 
 * Created 21.08.2007
 * @author Nick Belaevski
 * @since 3.1
 */

public class Zipper2Test extends TestCase {

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testByte() throws Exception {
		byte[] b = new byte[3];
		new Zipper2(b).addByte((byte) 0xDF).addByte((byte) 0x90).addByte((byte) 0xAA);
		Zipper2 zipper2 = new Zipper2(b);
		assertEquals((byte) 0xDF, zipper2.nextByte());
		assertEquals((byte) 0x90, zipper2.nextByte());
		assertEquals((byte) 0xAA, zipper2.nextByte());
	}

	public void testShort() throws Exception {
		byte[] b = new byte[6];
		new Zipper2(b).addShort((short) 0xA7DF).addShort((short) 0xFE90).addShort((short) 0x34AA);
		Zipper2 zipper2 = new Zipper2(b);
		assertEquals((short) 0xA7DF, zipper2.nextShort());
		assertEquals((short) 0xFE90, zipper2.nextShort());
		assertEquals((short) 0x34AA, zipper2.nextShort());
	}
	
	public void testColor() throws Exception {
		byte[] b = new byte[9];
		new Zipper2(b).addColor(new Color(0xA7DFE0)).addColor(0xE2349A).addColor(new Color(0x4812F9));
		Zipper2 zipper2 = new Zipper2(b);
		assertEquals(0xA7DFE0, zipper2.nextIntColor());
		assertEquals(new Color(0xE2349A), zipper2.nextColor());
		assertEquals(0x4812F9, zipper2.nextIntColor());
	}
	
	public void testInt() throws Exception {
		byte[] b = new byte[12];
		new Zipper2(b).addInt(0x12A7DFE0).addInt(0x67E2349A).addInt(0xBD4812F9);
		Zipper2 zipper2 = new Zipper2(b);
		assertEquals(0x12A7DFE0, zipper2.nextInt());
		assertEquals(0x67E2349A, zipper2.nextInt());
		assertEquals(0xBD4812F9, zipper2.nextInt());
	}

	public void testBytes() throws Exception {
	    byte[] b = new byte[6];
	    byte[] bs = new byte[] {(byte) 0x98, (byte) 0x63};
	    new Zipper2(b).addInt(0x08FECDB6).addBytes(bs);
	    Zipper2 zipper2 = new Zipper2(b);
	    assertEquals(0x08FECDB6, zipper2.nextInt());
	    assertTrue(Arrays.equals(bs, zipper2.nextBytes()));
	}
}
