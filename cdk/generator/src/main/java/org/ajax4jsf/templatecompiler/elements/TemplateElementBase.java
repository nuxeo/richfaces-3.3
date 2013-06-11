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

import org.ajax4jsf.templatecompiler.builder.CompilationContext;
import org.ajax4jsf.templatecompiler.builder.CompilationException;
import org.w3c.dom.Node;

/**
 * Abstract base class for tag processors.
 * 
 * @author ayukhovich@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.3 $ $Date: 2007/02/26 20:48:44 $
 */
public abstract class TemplateElementBase implements TemplateElement {
	
	
	private CompilationContext componentBean;
	
	private ElementsArray subElements = new ElementsArray();


	public TemplateElementBase(final Node element,
			final CompilationContext componentBean) {
		this.componentBean = componentBean;
	};

	public boolean isSkipBody() {
		// by default, children process by compiler.
		return false;
	}
	
	public void addSubElement(TemplateElement e) {
		this.subElements.add(e);
	}

	public String toCode() throws CompilationException {
		StringBuffer buf = new StringBuffer();

		String beginElement = this.getBeginElement();
		if (null != beginElement) {
			buf.append(beginElement);
			buf.append("\n");
		}

		if (this.subElements.size() != 0) {
			buf.append(this.subElements.toCode());
		}

		String endElement = this.getEndElement();
		if (endElement != null) {
			buf.append(endElement);
			buf.append("\n");
		}

		return buf.toString();
	}

	/**
	 * @return the componentBean
	 */
	public CompilationContext getComponentBean() {
		return componentBean;
	}

	/**
	 * @return the subElements
	 */
	public ElementsArray getSubElements() {
		return this.subElements;
	}

	
}
