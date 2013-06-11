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

package org.ajax4jsf.builder.generator;

import java.util.HashMap;
import java.util.Map;

import org.ajax4jsf.builder.config.PropertyBean;

/**
 * @author Administrator
 *
 */
public class TestDataGenerator2 extends TestDataGenerator {

	
	@SuppressWarnings("serial")
	private static final Map<String, String> testData = new HashMap<String, String>() {
		{
			put(java.util.Date.class.getName(), "new Date()");
			put(java.util.Calendar.class.getName(), "Calendar.getInstance()");
		}
	};
	
	public TestDataGenerator2(ClassLoader loader, Logger logger) {
		super(loader, logger);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getTestData(PropertyBean propertyBean) {
		String classname = propertyBean.getClassname();
		if (testData.containsKey(classname)) {
			return testData.get(classname);
		}
		return super.getTestData(propertyBean);
	}
	
	@Override
	public String getTestData1(PropertyBean propertyBean) {
		String classname = propertyBean.getClassname();
		if (testData.containsKey(classname)) {
			return testData.get(classname);
		}
		return super.getTestData1(propertyBean);
	}
	
	@Override
	/**
	 * Disable date types
	 */
	public boolean isNativelySupported(PropertyBean propertyBean) {
		return super.isNativelySupported(propertyBean) && !testData.containsKey(propertyBean.getClassname());
	}
}
