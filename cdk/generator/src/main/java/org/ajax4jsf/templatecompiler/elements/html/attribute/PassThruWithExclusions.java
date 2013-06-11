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

package org.ajax4jsf.templatecompiler.elements.html.attribute;

import java.util.Formatter;

import org.ajax4jsf.templatecompiler.elements.Attribute;

/**
 * Processing template PassThruWithExclusions-attributes.
 * 
 * @author ayukhovich@exadel.com (latest modification by $Author:
 *         alexeyyukhovich $)
 * @version $Revision: 1.1.2.2 $ $Date: 2007/02/26 20:48:55 $
 * 
 */
public class PassThruWithExclusions implements Attribute {
	private String name;

	private String value;

	public PassThruWithExclusions(String attributeName, String attributeValue) {
		this.name = attributeName;
		this.value = attributeValue;
	}

	public void copyValues(Attribute src) {
		if (src instanceof PassThruWithExclusions) {
			this.value = ((PassThruWithExclusions) src).getValue();
		}
	}

	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public String getCode() {
		String sReturnValue;

		if (this.value.length() == 0) {
			sReturnValue = "getUtils().encodePassThru(context, component);";
		} else {
			Object[] objects = new Object[1];
			objects[0] = this.value;

			sReturnValue = new Formatter()
					.format(
							"getUtils().encodePassThruWithExclusions(context, component, \"%s\");",
							objects).toString();

		}

		return sReturnValue;
	}

}
