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

package org.ajax4jsf.templatecompiler.elements.html;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import org.ajax4jsf.templatecompiler.elements.Attribute;

/**
 * Factory for processing HTLM-attributes.
 * 
 * @author ayukhovich@exadel.com (latest modification by $Author:
 *         alexeyyukhovich $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/02/26 20:48:42 $
 * 
 */
public class HtmlAttributesFactory {
	private final static String DEFAULT_CLASS_ATTRIBUTE_PROCESSOR = "org.ajax4jsf.templatecompiler.elements.html.attribute.HtmlAttribute";

	private final static Class[] paramClasses = new Class[] { String.class,
			String.class };

	private final static HashMap mapClasses = new HashMap();

	static {
		// mapClasses.put("x:passThruWithExclusions",
		// "org.ajax4jsf.templatecompiler.elements.html.attribute.PassThruWithExclusions");
		mapClasses.put("x:otherParameters", "");
	}

	public static Attribute getProcessor(String attributeName,
			String attributeValue) {
		Attribute returnValue = null;

		String className = (String) mapClasses.get(attributeName);

		if (className == null) {
			className = DEFAULT_CLASS_ATTRIBUTE_PROCESSOR;
		}

		if (!className.equals("")) {
			Class class1;
			try {
				class1 = Class.forName(className);
				try {
					try {
						Object[] objects = new Object[2];
						objects[0] = attributeName;
						objects[1] = attributeValue;
						returnValue = (Attribute) class1.getConstructor(
								paramClasses).newInstance(objects);
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}

		return returnValue;
	}
}
