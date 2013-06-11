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

import org.xml.sax.SAXException;

/**
 * @author shura (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:57:45 $
 *
 */
public class ValueMethodCallElement extends MethodCallElement {

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.MethodCallElement#encode(org.ajax4jsf.renderkit.compiler.TemplateContext)
	 */
	public void encode(TemplateContext context) throws IOException {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.MethodCallElement#setParent(org.ajax4jsf.renderkit.compiler.PreparedTemplate)
	 */
	public void setParent(PreparedTemplate parent) throws SAXException {
		// TODO Auto-generated method stub
		super.setParent(parent);
		if (parent instanceof ElementBase) {
			ElementBase base = (ElementBase) parent;
			base.valueGetter = new ElementBase.ValueGetter(){
				/* (non-Javadoc)
				 * @see org.ajax4jsf.renderkit.compiler.ElementBase.ValueGetter#getValue(org.ajax4jsf.renderkit.compiler.TemplateContext)
				 */
				Object getValue(TemplateContext context) {
					// TODO Auto-generated method stub
					return ValueMethodCallElement.this.getValue(context);
				}
			};
		}
	}
	/* (non-Javadoc)
	 * @see org.ajax4jsf.renderkit.compiler.PreparedTemplate#getTag()
	 */
	public String getTag() {
		return HtmlCompiler.NS_PREFIX+HtmlCompiler.VALUE_CALL_TAG;
	}

}
