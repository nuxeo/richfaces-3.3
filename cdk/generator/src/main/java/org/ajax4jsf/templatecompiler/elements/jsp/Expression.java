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

package org.ajax4jsf.templatecompiler.elements.jsp;

import org.ajax4jsf.templatecompiler.builder.CompilationContext;
import org.ajax4jsf.templatecompiler.elements.TemplateElementBase;
import org.w3c.dom.Node;

/**
 * Processing jsp:expression-tag.
 * 
 * @author ayukhovich@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.2 $ $Date: 2007/02/21 17:17:41 $
 */
public class Expression extends TemplateElementBase {
	private String expressionText;

	public Expression(Node element, CompilationContext componentBean) {
		super(element, componentBean);

		if (element.getTextContent().length() != 0) {
			this.expressionText = element.getTextContent();
		}
	}

	public String getBeginElement() {
		return this.expressionText;
	}

	public String getEndElement() {
		return null;
	}

	public boolean isSkipBody() {
		// no content must be processed.
		return true;
	}

}
