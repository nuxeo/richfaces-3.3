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

package org.ajax4jsf.templatecompiler.elements;

import java.util.ArrayList;
import java.util.Iterator;

import org.ajax4jsf.templatecompiler.builder.CompilationException;

/**
 * @author ayukhovich@exadel.com (latest modification by $Author:
 *         alexeyyukhovich $)
 * @version $Revision: 1.1.2.3 $ $Date: 2007/02/26 20:48:45 $
 * 
 */
class ElementsArray extends ArrayList<TemplateElement> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String toCode() throws CompilationException {
		StringBuffer buf = new StringBuffer();

		Iterator<TemplateElement> i = iterator();
		boolean hasNext = i.hasNext();
		while (hasNext) {
			TemplateElement o = i.next();
			if (o != null) {
					buf.append(o.toCode());					
			}
			hasNext = i.hasNext();
		} // while
		return buf.toString();
	}
}
