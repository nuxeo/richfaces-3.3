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
import org.ajax4jsf.templatecompiler.builder.CompilationException;
import org.ajax4jsf.templatecompiler.elements.TemplateElementBase;
import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Processing jsp:directive.page-tag.
 * 
 * @author ayukhovich@exadel.com (latest modification by $Author:
 *         alexeyyukhovich $)
 * @version $Revision: 1.1.2.3 $ $Date: 2007/02/26 20:48:52 $
 */
public class DirectivePage extends TemplateElementBase {
	private static final String EXTENDS_ATTRIBUTE = "extends";

	private static final String IMPORT_ATTRIBUTE = "import";

	public DirectivePage(Node element, CompilationContext componentBean)
			throws DOMException, CompilationException {
		super(element, componentBean);

		NamedNodeMap nnm = element.getAttributes();

		Node nodeExtends = nnm.getNamedItem(EXTENDS_ATTRIBUTE);
		if ((null != nodeExtends) && (nodeExtends.getNodeValue().length() != 0)) {
			componentBean.setBaseclass(nodeExtends.getNodeValue());
		}

		Node nodeImports = nnm.getNamedItem(IMPORT_ATTRIBUTE);
		if ((null != nodeImports) && (nodeImports.getNodeValue().length() != 0)) {
			String[] imports = nodeImports.getNodeValue().split("\\,");
			for (int i = 0; i < imports.length; i++) {
				componentBean.addToImport(imports[i].trim());
			} // for
		} // if
	}

	public String getBeginElement() {
		return null;
	}

	public String getEndElement() {
		return null;
	}

}
