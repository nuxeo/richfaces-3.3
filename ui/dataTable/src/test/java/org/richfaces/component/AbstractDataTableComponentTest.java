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

package org.richfaces.component;

import java.util.ArrayList;
import java.util.List;

import org.ajax4jsf.tests.AbstractAjax4JsfTestCase;

/**
 * Created 01.03.2008
 * @author Nick Belaevski
 * @since 3.2
 */

public abstract class AbstractDataTableComponentTest extends AbstractAjax4JsfTestCase {

	/**
	 * @param name
	 */
	public AbstractDataTableComponentTest(String name) {
		super(name);
	}

	protected List<Object> createTestData_0_sortFields() {
		List<Object> list = new ArrayList<Object>();
		list.add("Column1");
		list.add("Column2");
		list.add("Column3");
		
		return list;
	}

	protected List<Object> createTestData_1_sortFields() {
		List<Object> list = new ArrayList<Object>();
		list.add("NameA");
		list.add("NameB");
		
		return list;
	}

	protected List<Object> createTestData_0_filterFields() {
		List<Object> list = new ArrayList<Object>();
		list.add("FColumn0");
		list.add("FColumn1");
		
		return list;
	}
	
	protected List<Object> createTestData_1_filterFields() {
		List<Object> list = new ArrayList<Object>();
		list.add("FNameA");
		list.add("FNameB");
		list.add("FNameC");
		
		return list;
	}
}
