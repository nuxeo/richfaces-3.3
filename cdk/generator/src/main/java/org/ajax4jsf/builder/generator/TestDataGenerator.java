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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.ajax4jsf.builder.config.PropertyBean;

/**
 * @author Nick Belaevski - nbelaevski@exadel.com
 * created 13.04.2007
 * 
 */
public class TestDataGenerator {

	private ClassLoader loader;
	private Logger logger;

	private static Map<String, String> testData = new HashMap<String, String>();
	static {
		testData.put(Boolean.TYPE.getName(), "true");
		testData.put(Character.TYPE.getName(), "'#'");
		testData.put(Byte.TYPE.getName(), "17");
		testData.put(Short.TYPE.getName(), "18");
		testData.put(Integer.TYPE.getName(), "20");
		testData.put(Long.TYPE.getName(), "26");
		testData.put(Float.TYPE.getName(), "2.05f");
		testData.put(Double.TYPE.getName(), "1.34d");

		testData.put(Boolean.class.getName(), "new Boolean(true)");
		testData.put(Character.class.getName(), "new Character('#')");
		testData.put(Byte.class.getName(), "new Byte(17)");
		testData.put(Short.class.getName(), "new Short(18)");
		testData.put(Integer.class.getName(), "new Integer(20)");
		testData.put(Long.class.getName(), "new Long(26)");
		testData.put(Float.class.getName(), "new Float(2.05f)");
		testData.put(Double.class.getName(), "new Double(1.34d)");

		testData.put(Date.class.getName(), "java.text.DateFormat.getDateInstance(" +
				"java.text.DateFormat.SHORT, java.util.Locale.US).parse(\"09/08/2007\")");
	}

	private static Map<String, String> testData1 = new HashMap<String, String>();
	static {
		testData1.put(Boolean.TYPE.getName(), "false");
		testData1.put(Character.TYPE.getName(), "'*'");
		testData1.put(Byte.TYPE.getName(), "8");
		testData1.put(Short.TYPE.getName(), "26");
		testData1.put(Integer.TYPE.getName(), "15");
		testData1.put(Long.TYPE.getName(), "13");
		testData1.put(Float.TYPE.getName(), "1.05f");
		testData1.put(Double.TYPE.getName(), "1.44d");

		testData1.put(Boolean.class.getName(), "new Boolean(false)");
		testData1.put(Character.class.getName(), "new Character('*')");
		testData1.put(Byte.class.getName(), "new Byte(8)");
		testData1.put(Short.class.getName(), "new Short(26)");
		testData1.put(Integer.class.getName(), "new Integer(15)");
		testData1.put(Long.class.getName(), "new Long(13)");
		testData1.put(Float.class.getName(), "new Float(1.05f)");
		testData1.put(Double.class.getName(), "new Double(1.44d)");

		testData1.put(Date.class.getName(), "java.text.DateFormat.getDateInstance(" +
				"java.text.DateFormat.SHORT, java.util.Locale.US).parse(\"02/29/2008\")");
	}
	
	private static Map<String, Class> primitiveToWrapper = new HashMap<String, Class>();
	static {
		primitiveToWrapper.put(Boolean.TYPE.getName(), Boolean.class);
		primitiveToWrapper.put(Character.TYPE.getName(), Character.class);
		primitiveToWrapper.put(Byte.TYPE.getName(), Byte.class);
		primitiveToWrapper.put(Short.TYPE.getName(), Short.class);
		primitiveToWrapper.put(Integer.TYPE.getName(), Integer.class);
		primitiveToWrapper.put(Long.TYPE.getName(), Long.class);
		primitiveToWrapper.put(Float.TYPE.getName(), Float.class);
		primitiveToWrapper.put(Double.TYPE.getName(), Double.class);
	}

	public TestDataGenerator(ClassLoader loader, Logger logger) {
		super();
		this.loader = loader;
		this.logger = logger;
	}


	public String getTestVeClass(PropertyBean propertyBean) {
		String type = propertyBean.getClassname();
		Class clazz = primitiveToWrapper.get(propertyBean.getClassname());
		if (clazz == null) {
			try {
				clazz = loader.loadClass(propertyBean.getClassname());
			} catch (ClassNotFoundException e) {
				logger.error(e);

				return null;
			}
		}

		return clazz.getName();
	}
	
	public String getTestVeData(PropertyBean propertyBean) {
		String veClass = getTestVeClass(propertyBean);
		if (veClass != null) {
			return getTestData(propertyBean.getName(), veClass, 0);
		}
		
		return "";
	}

	public String getTestVeData1(PropertyBean propertyBean) {
		String veClass = getTestVeClass(propertyBean);
		if (veClass != null) {
			return getTestData(propertyBean.getName(), veClass, 1);
		}
		
		return "";
	}

	private String getTestData(String propertyName, String className, int number) {
		
		String string = number == 0 ? testData.get(className) : testData1.get(className);
		
		if (string != null) {
			return string;
		}
		
		try {
			Class<?> clazz = Class.forName(className, false, loader);
			if (clazz.isAssignableFrom(String.class)) {
				if (number == 0) {
					return "\"" + propertyName + "\"";
				} else {
					return "\"" + propertyName + "_" + propertyName + "\"";
				}
			} else if (clazz.isEnum()) {
				return getEnumData(propertyName, (Class<Enum<?>>)clazz, number);
			}
 			
		} catch(Exception e) {
			
		}
		
		return "createTestData_" + number + "_" + propertyName + "()";
	}	
	
	private String getEnumData(String propertyName, Class<Enum<?>> clazz, int number) {
		if (clazz.isEnum()) {
			Enum<?>[] enumConstants = clazz.getEnumConstants();
			if (enumConstants.length > 0) {
				if (number >= enumConstants.length) {
					return enumToString(enumConstants[enumConstants.length - 1]);
				}
				return enumToString(enumConstants[number]);
			}
		}
		return "null";
	} 
	
	private String enumToString(Enum<?> e) {
		return e.getDeclaringClass().getName() + "." + e;
	}
	
	public String getTestData(PropertyBean propertyBean) {
		return getTestData(propertyBean.getName(), propertyBean.getClassname(), 0);
	}

	public String getTestData1(PropertyBean propertyBean) {
		return getTestData(propertyBean.getName(), propertyBean.getClassname(), 1);
	}
	/**
	 * Returns true if property 
	 * @param propertyBean
	 * @return
	 */	
	public boolean isNativelySupported(PropertyBean propertyBean) {
		String classname = propertyBean.getClassname();
		return testData.containsKey(classname) || isAssignabelFromString(propertyBean) || isEnum(propertyBean);
		
	}
	
	protected boolean isAssignabelFromString(PropertyBean bean) {
		String classname = bean.getClassname();
		Class<?> class1;
		try {
			class1 = Class.forName(classname, false, loader);
		} catch (ClassNotFoundException e) {
			return false;
		}
		return class1.isAssignableFrom(String.class);
	}
	
	protected boolean isEnum(PropertyBean bean) {
		String classname = bean.getClassname();
		Class<?> class1;
		try {
			class1 = Class.forName(classname, false, loader);
		} catch (ClassNotFoundException e) {
			return false;
		}
		return class1.isEnum();
	}
}
