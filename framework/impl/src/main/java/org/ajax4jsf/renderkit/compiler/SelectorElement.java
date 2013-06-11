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

package org.ajax4jsf.renderkit.compiler;

import java.io.IOException;

import org.ajax4jsf.Messages;
import org.xml.sax.SAXException;

/**
 * @author Maksim Kaszynski (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:57:46 $
 * CSS selector tag.
 * Works this way
 * <code>
 * 	<u:selector name=".X">
 * 		a bunch of <u:style>
 * 	</u:selector>
 * </code>
 * renders like this
 * <code>
 * 	.X{
 * 		styles...
 * 	}
 * </code>
 *
 */
public class SelectorElement extends ElementBase {

	private String name ;
	
	protected void encodeBegin(TemplateContext context) throws IOException {
		context.getWriter().write("\n" + getName() + "{\n");
	}
	
	protected void encodeEnd(TemplateContext context) throws IOException {
		context.getWriter().write("}");
	}


	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.PreparedTemplate#getTag()
	 */
	public String getTag() {
		// TODO Auto-generated method stub
		return HtmlCompiler.NS_PREFIX+HtmlCompiler.SELECTOR_TAG;
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.ElementBase#setParent(org.ajax4jsf.renderkit.compiler.PreparedTemplate)
	 */
	public void setParent(PreparedTemplate parent) throws SAXException {		
		super.setParent(parent);
		if (getName()==null) {
			throw new SAXException(Messages.getMessage(Messages.NO_NAME_ATTRIBUTE_ERROR, getTag()));
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
