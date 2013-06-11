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

package org.richfaces.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import junit.framework.TestCase;

/**
 * @author Nick Belaevski
 * @since 3.3.0
 */

public class ScrollableTableDataRangeSerializationTest extends TestCase {

	private ScrollableTableDataRange readWriteRange(ScrollableTableDataRange range) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(range);
		oos.close();
		
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bais);
	
		return (ScrollableTableDataRange) ois.readObject();
	}
	
	public void testSerialization() throws Exception {
		SortOrder order = new SortOrder(new SortField[] { new SortField("xxx", true) });
		ScrollableTableDataRange range = new ScrollableTableDataRange(10, 300, order);
		
		ScrollableTableDataRange serializedRange = readWriteRange(range);
		
		assertNotSame(range, serializedRange);
		assertEquals(range, serializedRange);
	}
}
