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

import org.ajax4jsf.Messages;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:57:47 $
 *
 */
public class PlainElementCreateRule extends Rule {

	/* (non-Javadoc)
	 * @see org.apache.commons.digester.Rule#begin(java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void begin(String namespace, String name, Attributes attrs) throws Exception {
		if (name.startsWith(HtmlCompiler.NS_PREFIX)) {
			throw new SAXException(Messages.getMessage(Messages.NAMESPACE_NOT_RECOGNIZED_ERROR, name));
		}
		digester.push(new PlainElement(namespace,name,attrs));
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.digester.Rule#body(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void body(String arg0, String arg1, String arg2) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.digester.Rule#end(java.lang.String, java.lang.String)
	 */
	public void end(String arg0, String arg1) throws Exception {
		// TODO Auto-generated method stub
		digester.pop();
	}
	
	

}
