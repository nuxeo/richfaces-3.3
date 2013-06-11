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

package org.ajax4jsf.templatecompiler.elements.vcp;

import org.ajax4jsf.templatecompiler.builder.CompilationContext;
import org.ajax4jsf.templatecompiler.elements.BodyElement;
import org.ajax4jsf.templatecompiler.elements.TemplateElementBase;
import org.w3c.dom.Node;

/**
 * Processing vcp:body-tags.
 * 
 * @author ayukhovich@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.3 $ $Date: 2007/02/26 20:48:46 $
 */
public class VcpBodyTemplateElement extends TemplateElementBase implements BodyElement {
	public final static String STR_VCB_BODY = "VCPBODY";

	public VcpBodyTemplateElement(Node element,
			final CompilationContext componentBean) {
		super(element, componentBean);
	}

	public String getBeginElement() {
		return STR_VCB_BODY;
	}

	public String getEndElement() {
		return STR_VCB_BODY;
	}
}