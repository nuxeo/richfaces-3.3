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

import java.util.Formatter;

import org.ajax4jsf.templatecompiler.builder.CompilationContext;
import org.ajax4jsf.templatecompiler.builder.CompilationException;
import org.ajax4jsf.templatecompiler.elements.TemplateElementBase;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Processing f:cliendId-tag.
 * 
 * @author ayukhovich@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.2 $ $Date: 2007/02/21 17:16:20 $
 */
public class FClientIDTemplateElement extends TemplateElementBase {
	private String strThisVariable;

	private Class type;

	public FClientIDTemplateElement(final Node element,
			final CompilationContext componentBean) throws CompilationException {
		super(element, componentBean);
		NamedNodeMap nnm = element.getAttributes();
		String sVariableName = nnm.getNamedItem("var").getNodeValue();

		this.strThisVariable = sVariableName;

		Class[] parameters = new Class[1];
		parameters[0] = componentBean.getVariableType("context");

		try {
		    this.type = componentBean.getMethodReturnedClass(componentBean
		    		.getVariableType("component"), "getClientId", parameters);
		} catch (NoSuchMethodException e) {
		    throw new CompilationException(e.getLocalizedMessage(), e);
		}

		componentBean.addVariable(this.strThisVariable, this.type);
	}

	public String getBeginElement() {
		String sReturnValue;

		// Object[] objects = new Object[1];
		// objects[0] = strThisVariable;

		// sReturnValue = new Formatter().
		// format(
		// "variables.setVariable(\"%s\",component.getClientId(context));",
		// objects).toString();
		Object[] objects = new Object[2];
		objects[0] = this.type.getName();
		objects[1] = this.strThisVariable;
		sReturnValue = new Formatter().format(
				"%s %s = component.getClientId(context);", objects).toString();
		return sReturnValue;
	}

	public String getEndElement() {
		return null;
	}

}
