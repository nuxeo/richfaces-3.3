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

import java.util.LinkedHashMap;
import java.util.Map;

import org.ajax4jsf.templatecompiler.elements.Attribute;

/**
 * Processing HTLM-attributes.
 * 
 * @author ayukhovich@exadel.com (latest modification by $Author:
 *         alexeyyukhovich $)
 * @version $Revision: 1.1.2.2 $ $Date: 2007/02/26 20:48:41 $
 * 
 */
public class HTMLAttributes {
	private LinkedHashMap attributesMap = new LinkedHashMap();
	

	public void addAttribute(String nameAttribute, String valueAttribute) {

		Attribute attribute = HtmlAttributesFactory.getProcessor(
				nameAttribute, valueAttribute);

		if (attribute != null) {
			Attribute oldElemet = (Attribute) this.attributesMap.get(attribute
					.getName());

			if (oldElemet != null) {
				oldElemet.copyValues(attribute);
			} else {
				this.attributesMap.put(attribute.getName(), attribute);
			}
		} else {
			System.out.println("attribute is null");
		}
	}

	public Map getAttributes() {
		return this.attributesMap;
	}
}
