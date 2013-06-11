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

/*
 * ELBuilderTest.java		Date created: 07.10.2008
 * Last modified by: $Author$
 * $Revision$	$Date$
 */

package org.richfaces.el;

import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.beans.BulkBean;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * JUnit test class for ElBuilder
 * @author Andrey
 *
 */
public class ELBuilderTest extends TestCase {
	
	List<String> origList = null;
	
	List<String> resultList = null;
	
	String var;
	
	String index;
	
	String varR;
	
	String indexR;
	
	public void setUp() throws Exception {
		super.setUp();
		var = "column";
		index = "index";
		varR = "bean.columns[0]";
		indexR = "1";
		
		origList = new ArrayList<String>();
		origList.add("#{}");
		origList.add("#{column}");
		origList.add("#{index}");
		origList.add("#{column.header}");
		origList.add("#{var[index]}");
		origList.add("#{bean.filterValue[index]}");
		origList.add("#{column.index == 'column.index'}");
		origList.add("#{var.column.index == 1}");
		origList.add("#{index + 11 == 12}");
		origList.add("#{var[index*2].index == column[index].index}");
		origList.add("Active column: #{index}");
		origList.add("Active name: #{column.name}");
		origList.add("#{column} text #{index}");
		origList.add("#{column.index} #{bean.name[index] == 'index'}");

		
		
		resultList = new ArrayList<String>();
		resultList.add("#{}");
		resultList.add("#{bean.columns[0]}");
		resultList.add("#{1}");
		resultList.add("#{bean.columns[0].header}");
		resultList.add("#{var[1]}");
		resultList.add("#{bean.filterValue[1]}");
		resultList.add("#{bean.columns[0].index == 'column.index'}");
		resultList.add("#{var.column.index == 1}");
		resultList.add("#{1 + 11 == 12}");
		resultList.add("#{var[1*2].index == bean.columns[0][1].index}");
		resultList.add("Active column: #{1}");
		resultList.add("Active name: #{bean.columns[0].name}");
		resultList.add("#{bean.columns[0]} text #{1}");
		resultList.add("#{bean.columns[0].index} #{bean.name[1] == 'index'}");
	}

	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testELBuilder() {
		for (int i=0; i < origList.size(); i++) {
			String o = origList.get(i);
			ELBuilder builder = new ELBuilder(o, var, index, varR, indexR);
			String r = null;
			try {
				r = builder.parse();
			}catch (Exception e) {
				System.out.println(e);
				Assert.fail(e.getMessage());
			}
			Assert.assertEquals("ValueExpression was parsed incorrectly: " + o, r, resultList.get(i));
		}
	}
	
	

}
