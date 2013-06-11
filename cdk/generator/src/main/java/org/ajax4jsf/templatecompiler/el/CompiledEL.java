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

package org.ajax4jsf.templatecompiler.el;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author yukhovich
 * 
 */
public class CompiledEL implements ICompiledEL {

	class containedClass {
		private Class type;

		private String code;

		public containedClass(String code, Class type) {
			this.code = code;
			this.type = type;
		}

		public Class getType() {
			return this.type;
		}

		public String getCode() {
			return this.code;
		}

	}

	private ArrayList childs = new ArrayList();

	private Class type;

	private String code;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ajax4jsf.templatecompiler.el.ICompiledEL#getJavaCode()
	 */
	public String getJavaCode() {
		int i = 0;

		ArrayList tmp = new ArrayList();
		boolean bString = false;

		for (Iterator iter = this.childs.iterator(); iter.hasNext(); i++) {
			ICompiledEL element = (ICompiledEL) iter.next();

			String tmpCode = element.getJavaCode();

			if (null != element.getType()) {
				if (String.class.isAssignableFrom(element.getType())) {
					bString = true;
				}

				tmp.add(new containedClass(tmpCode, element.getType()));
			}
		}

		if (null != this.code) {

			if (String.class.isAssignableFrom(this.type)) {
				bString = true;
			}

			tmp.add(new containedClass(this.code, this.type));
		}

		StringBuffer buf = new StringBuffer();

		for (Iterator iter = tmp.iterator(); iter.hasNext();) {
			containedClass element = (containedClass) iter.next();

			if (String.class.isAssignableFrom(element.getType())) {
				buf.append(element.getCode());
			} else {
				if (bString) {
					buf.append("ConvertToString(").append(element.getCode())
							.append(")");
				}
			}
			if (iter.hasNext()) {
				buf.append(" + ");
			}

		}

		if (bString) {
			this.type = String.class;
		} else {
			this.type = Object.class;
		}

		return buf.toString();
	}

	public void addChild(ICompiledEL child) {
		this.childs.add(child);
	}

	public void setJavaCode(String string) {
		setJavaCode(string, java.lang.String.class);
	}

	public void setJavaCode(String string, Class type) {
		this.code = string;
		this.type = type;

	}

	public void setString(String str) {
		setJavaCode("\"" + str + "\"");
	}

	public Class getType() {
		return this.type;
	}

}
