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

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;

/**
 * @author asmirnov@exadel.com (latest modification by $Author: alexsmirnov $)
 * @version $Revision: 1.1.2.1 $ $Date: 2007/01/09 18:57:46 $
 *
 */
public class PutAttributesRule extends Rule {

	private List toSkip = Collections.EMPTY_LIST;
	private String methodName = "put";
	/**
	 * @param digestr
	 * @param toSkip
	 */
	public PutAttributesRule(Digester digestr, String[] toSkip) {
		// TODO Auto-generated constructor stub
		this.digester = digestr;
//		Arrays.asList(toSkip);
		this.toSkip = Arrays.asList(toSkip);
	}

	/**
	 * @param digestr
	 * @param methodName
	 */
	public PutAttributesRule(Digester digestr, String methodName) {
		// TODO Auto-generated constructor stub
		this.digester = digestr;
//		Arrays.asList(toSkip);
		this.methodName = methodName;
	}
	
	/**
	 * @param digestr
	 * @param methodName
	 * @param toSkip
	 */
	public PutAttributesRule(Digester digestr, String methodName, String[] toSkip) {
		// TODO Auto-generated constructor stub
		this.digester = digestr;
		this.methodName = methodName;
		this.toSkip = Arrays.asList(toSkip);
	}
	/* (non-Javadoc)
	 * @see org.apache.commons.digester.Rule#begin(java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void begin(String namespace, String element, Attributes attrs) throws Exception {
		Object top = digester.peek();
		if(null != top){
			Method put = top.getClass().getDeclaredMethod(methodName,new Class[]{Object.class,Object.class});
			for (int i = 0; i < attrs.getLength(); i++) {
				String qName = attrs.getQName(i);
				String value = attrs.getValue(i);
				if (!toSkip.contains(qName)) {
					put.invoke(top,new Object[]{qName,value});
				}
			}
		}
	}

}
