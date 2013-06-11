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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ajax4jsf.templatecompiler.elements.Attribute;

/**
 * Processing template simple HTLM-attributes.
 * 
 * @author ayukhovich@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/02/26 20:48:55 $
 * 
 */
public class HtmlAttribute implements Attribute {
	final private static String regexAttributeName = "x:(.*)";

	final private static Pattern patternAttributeName = Pattern
			.compile(regexAttributeName);

	private String name;

	private String value;

	private boolean isNoRewrite;

	/**
	 * 
	 * @param attributeName
	 * @param attributeValue
	 */
	public HtmlAttribute(String attributeName, String attributeValue) {
		Matcher mather = patternAttributeName.matcher(attributeName);

		if (mather.find()) {
			this.name = mather.group(1);
			this.isNoRewrite = true;
		} else {
			this.name = attributeName;
			this.isNoRewrite = false;
		}
		this.value = attributeValue;
	}

	/**
	 * 
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isNoRewrite() {
		return this.isNoRewrite;
	}

	/**
	 * copy values from
	 * 
	 * @param src
	 */
	public void copyValues(final Attribute src) {
		if (src instanceof HtmlAttribute) {

			if (!isNoRewrite()) {
				this.value = ((HtmlAttribute) src).getValue();
				this.isNoRewrite = ((HtmlAttribute) src).isNoRewrite();
			}
		}
	}

	/**
	 * 
	 */
	public String getCode() {
		return null;
	}
}
