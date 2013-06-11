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
package org.richfaces.ui.application;

import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.PropertyResolver;

/**
 * @author asmirnov
 *
 */
public class StatePropertyResolver extends PropertyResolver {

	/* (non-Javadoc)
	 * @see javax.faces.el.PropertyResolver#getType(java.lang.Object, java.lang.Object)
	 */
	
	public Class getType(Object arg0, Object arg1) throws EvaluationException,
			PropertyNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.faces.el.PropertyResolver#getType(java.lang.Object, int)
	 */
	
	public Class getType(Object arg0, int arg1) throws EvaluationException,
			PropertyNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.faces.el.PropertyResolver#getValue(java.lang.Object, java.lang.Object)
	 */
	
	public Object getValue(Object arg0, Object arg1)
			throws EvaluationException, PropertyNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.faces.el.PropertyResolver#getValue(java.lang.Object, int)
	 */
	
	public Object getValue(Object arg0, int arg1) throws EvaluationException,
			PropertyNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see javax.faces.el.PropertyResolver#isReadOnly(java.lang.Object, java.lang.Object)
	 */
	
	public boolean isReadOnly(Object arg0, Object arg1)
			throws EvaluationException, PropertyNotFoundException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.faces.el.PropertyResolver#isReadOnly(java.lang.Object, int)
	 */
	
	public boolean isReadOnly(Object arg0, int arg1)
			throws EvaluationException, PropertyNotFoundException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.faces.el.PropertyResolver#setValue(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	
	public void setValue(Object arg0, Object arg1, Object arg2)
			throws EvaluationException, PropertyNotFoundException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.faces.el.PropertyResolver#setValue(java.lang.Object, int, java.lang.Object)
	 */
	
	public void setValue(Object arg0, int arg1, Object arg2)
			throws EvaluationException, PropertyNotFoundException {
		// TODO Auto-generated method stub

	}

}
